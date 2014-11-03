package com.meteorite.core.codegen;

import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaField;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class CodeUtil {
    private Meta meta;

    public CodeUtil(Meta meta) {
        this.meta = meta;
    }

    public String getDefaultValueString() {
        StringBuilder sb = new StringBuilder();
        for (MetaField field : meta.getFields()) {

        }
        return sb.toString();
    }
}
