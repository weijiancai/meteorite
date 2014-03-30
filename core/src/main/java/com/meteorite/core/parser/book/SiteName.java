package com.meteorite.core.parser.book;

import com.meteorite.core.util.UString;

/**
 * 站点名
 * 
 * @author weijiancai
 * @since 0.0.1
 */
public enum SiteName {
	JING_DONG, DANG_DANG, DOU_BAN, AMAZON, PUB_BEI_DA, OPEN_BOOK_DATA;
	
	public static SiteName[] toArray(String siteNameStr) {
		if(!UString.isEmpty(siteNameStr)) {
			String[] names = siteNameStr.split(",");
			SiteName[] siteNames = new SiteName[names.length];
			
			for(int i = 0; i < names.length; i++) {
				SiteName siteName = null;
				if(JING_DONG.name().equals(names[i])) {
					siteName = JING_DONG;
				} else if(DANG_DANG.name().equals(names[i])) {
					siteName = DANG_DANG;
				} else if(DOU_BAN.name().equals(names[i])) {
					siteName = DOU_BAN;
				} else if(AMAZON.name().equals(names[i])) {
					siteName = AMAZON;
				} else if(PUB_BEI_DA.name().equals(names[i])) {
					siteName = PUB_BEI_DA;
				} else if(OPEN_BOOK_DATA.name().equals(names[i])) {
					siteName = OPEN_BOOK_DATA;
				}
				siteNames[i] = siteName;
			}
			
			return siteNames;
		}
		
		return null;
	}
	
	public static SiteName[] getDefaultSort() {
		return new SiteName[]{SiteName.JING_DONG, SiteName.AMAZON, SiteName.DANG_DANG, SiteName.OPEN_BOOK_DATA, SiteName.DOU_BAN};
	}
}
