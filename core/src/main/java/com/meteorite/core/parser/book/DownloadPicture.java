package com.meteorite.core.parser.book;

import com.meteorite.core.util.UFile;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 下载图片
 * 
 * @author wei_jc
 * @version 0.0.1
 */
public class DownloadPicture {
	private static final Logger log = Logger.getLogger(DownloadPicture.class);
	
	public static String BASE_DIR = "D:/pic";
	
	static {
		/*String confValue = SystemConfigFactory.getInstance().getUserConfig("downloadPictureDir");
		if(!UString.isEmpty(confValue)) {
			BASE_DIR = confValue;
		}*/
	}
		
	public DownloadPicture(ProductPic productPic) {
		down(productPic);
	}
	
	public DownloadPicture(final List<ProductPic> picList) {
		final int startCount = 10; // 起始线程数
		if(picList.size() < startCount) {
			for(ProductPic pic : picList) {
				down(pic);
			}
		} else { // 多线程下载
			int count = picList.size() / startCount + (picList.size() % startCount > 0 ? 1 : 0);
			final CountDownLatch countDownLatch = new CountDownLatch(count);
			for(int i = 0; i < count; i++) {
				final int ii = i;
				new Thread(new Runnable() {
					@Override
					public void run() {
						for(int j = ii * startCount; j < (ii*startCount + startCount) && j < picList.size(); j++) {
							down(picList.get(j));
						}
						countDownLatch.countDown();
					}
				}).start();
			}
			try {
				countDownLatch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void down(ProductPic productPic) {
		if(productPic.getUrl() == null || productPic.isDownload()) {
			return;
		}
		
		log.debug(String.format("下载图片【%s】", productPic.getUrl().toString()));
		// 取当前日期
		String date = String.format("%tF", new Date());
		File picDir = new File(BASE_DIR, date);
		if(!picDir.exists()) {
			picDir.mkdirs();
		}
		// 下载图片
		InputStream is;
		try {
			URLConnection conn = productPic.getUrl().openConnection();
			is = conn.getInputStream();
			File picFile = new File(picDir, productPic.getFileName());
			FileOutputStream os = new FileOutputStream(picFile);
			// 写入文件
	    	UFile.write(is, os);
	    	// 设置绝对路径
	    	productPic.setFilePath(picFile.getAbsolutePath());
	    	productPic.setDownloadDate(new Date());
	    	productPic.setDownload(true);
		} catch (Exception e) {
			log.error(String.format("下载图片【%s】失败！！！", productPic.getUrl().toString()), e);
			productPic.setDownload(false);
		}
	}
}
