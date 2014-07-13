package com.meteorite.core.parser.mobile;

import com.meteorite.core.datasource.DataMap;
import com.meteorite.core.meta.MetaManager;
import com.meteorite.core.meta.model.Meta;

import java.util.List;

/**
 * 手机号码Action类
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MobileNumberAction {
    public void fetchMobileNumber() throws Exception {
        FetchMobileNumber fetchMobileNumber = new FetchMobileNumber();
        List<MobileNumber> result = fetchMobileNumber.fetch();
        Meta meta = MetaManager.getMeta("MobileNumber");
        for (MobileNumber mobileNumber : result) {
            DataMap map = new DataMap();
            map.put("code", mobileNumber.getCode());
            map.put("province", mobileNumber.getProvince());
            map.put("city", mobileNumber.getCity());
            map.put("card_type", mobileNumber.getCardType());
            map.put("operators", mobileNumber.getOperators());
            map.put("code_segment", mobileNumber.getCodeSegment());
            meta.insertRow(map);
        }
        meta.save();
    }
}
