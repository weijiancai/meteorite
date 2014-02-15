/**
 * 
 */
package com.meteorite.core.parser.capub;

import com.meteorite.core.util.UDate;
import com.meteorite.core.util.UNumber;
import com.meteorite.core.util.UString;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wei_jc
 *
 */
public class CapubBO {
	public static File capubDir = new File("", "capub");
	private static List<CapubParser> list;
	
	static {
		if(!capubDir.exists()) {
			capubDir.mkdirs();
		}
	}
	
	/***
	 * 初始化CIP数据
	 * 
	 * @param maxNo
	 * @param minNo
	 * @param base
	 * @throws Exception
	 */
	public static void initCipData(int maxNo, int minNo, int base) throws Exception {
		int mod = (maxNo - minNo) % base;
		int count = (maxNo - minNo) / base + (mod == 0 ? 0 : 1) ;
		list = new ArrayList<CapubParser>();
		
		for(int i = 0; i < count; i++) {
			int start = minNo + i * base;
			int end = start + base - 1;
			if(start >= maxNo || start < minNo) {
				break;
			}
			if(end > maxNo) {
				end = maxNo;
			}
			final CapubBean bean = new CapubBean(start, end);
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						CapubParser parser = new CapubParser(bean);
						list.add(parser);
						parser.parseByEndStart();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}

	public static void stop() {
		for(CapubParser parser : list) {
			parser.setStop(true);
		}
	}

	/**
	 * 根据核字号初始化CIP数据
	 * 
	 * @param approveNo
	 * @throws Exception 
	 */
	public static String initCipData(String approveNo) throws Exception {
		CapubParser parser = new CapubParser();
		String pici = "CIP数据" + UDate.dateToString(new Date(), "yyyyMMddHHss");
		ProductUploadInfo prod = null;
		String no = approveNo;
		int count = UNumber.toInt(approveNo.substring(4));
		int loseCount = 0;
		int addCount = 0;
		
		while(true) {
			try {
				no = approveNo.substring(0, 4) + UString.leftPadding((count++) + "", '0', 6);
				/*if(ProductUploadBO.existsCipId(no)) {
					loseCount = 0;
					continue;
				}*/
				prod = parser.checkApproveNo(no);
				parser.addProductUpload(prod, pici);
				addCount++;
				loseCount = 0;
			} catch (CipNotFoundException e) {
                System.out.println("没有找到CIP核字号：" + no);
				loseCount++;
			}
			
			if(loseCount  == 50) {
				break;
			}
		}
		
		// 记录批次信息
		if(addCount > 0) {
			/*ImpProdPiciBO piciBO = new ImpProdPiciBO(null);
			ImpProdPiciInfo piciInfo = new ImpProdPiciInfo();
			piciInfo.setIsImported("F");
			piciInfo.setPici(pici);
			piciInfo.setPubId(PubDictMappingBO.PUB_CAPUB_ID);
			piciInfo.setPubName("中国新闻出版信息网");
			piciInfo.setUploadDate(new Date());
			piciBO.addPici(piciInfo);*/
		}
		
		return approveNo.substring(0, 4) + UString.leftPadding((count - 10) + "", '0', 6);
	}
}
