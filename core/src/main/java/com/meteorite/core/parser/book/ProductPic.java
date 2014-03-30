/**
 * 
 */
package com.meteorite.core.parser.book;

import com.meteorite.core.util.UUIDUtil;

import java.net.URL;
import java.util.Date;

/**
 * 商品图片
 * 
 * @author wei_jc
 * @version 0.0.1
 */
public class ProductPic {
	private String productId;
	private String picId;
	private URL url;
	private String filePath;
	private Date downloadDate;
	private boolean isTitlePageFile;  // 是否是封面
	private boolean isDownload;  // 是否下载
	
	public ProductPic(URL url, boolean isTitlePageFile) {
		this.url = url;
		this.picId = UUIDUtil.getUUID();
		this.isTitlePageFile = isTitlePageFile;
	}
	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getPicId() {
		return picId;
	}
	public void setPicId(String picId) {
		this.picId = picId;
	}
	public URL getUrl() {
		return url;
	}
	public void setUrl(URL url) {
		this.url = url;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Date getDownloadDate() {
		return downloadDate;
	}
	public void setDownloadDate(Date downloadDate) {
		this.downloadDate = downloadDate;
	}

	public boolean isTitlePageFile() {
		return isTitlePageFile;
	}

	public void setTitlePageFile(boolean isTitlePageFile) {
		this.isTitlePageFile = isTitlePageFile;
	}

	public boolean isDownload() {
		return isDownload;
	}

	public void setDownload(boolean isDownload) {
		this.isDownload = isDownload;
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @return
	 */
	public String getFileExtName() {
		String str = url.toString();
		return str.substring(str.lastIndexOf("."));
	}
	
	/**
	 * 获取文件名
	 * 
	 * @return
	 */
	public String getFileName() {
		return getPicId() + getFileExtName();
	}
}
