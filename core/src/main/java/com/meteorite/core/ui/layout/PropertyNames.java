package com.meteorite.core.ui.layout;

/**
 * 属性名常量
 * 
 * @author wei_jc
 * @since 1.0.0
 */
public interface PropertyNames {
    interface FORM {
        String NAME = "FORM.name";
        String DISPLAY_NAME = "FORM.displayName";
        String FORM_TYPE = "FORM.formType";
        String COL_COUNT = "FORM.colCount";
        String COL_WIDTH = "FORM.colWidth";
        String LABEL_GAP = "FORM.labelGap";
        String FIELD_GAP = "FORM.fieldGap";
        String HGAP = "FORM.hgap";
        String VGAP = "FORM.vgap";
    }
    
    interface FORM_FIELD {
        String NAME = "FORM_FIELD.name";
        String DISPLAY_NAME = "FORM_FIELD.displayName";
        String IS_SINGLE_LINE = "FORM_FIELD.isSingleLine";
        String IS_DISPLAY = "FORM_FIELD.isDisplay";
        String WIDTH = "FORM_FIELD.width";
        String HEIGHT = "FORM_FIELD.height";
        String DISPLAY_STYLE = "FORM_FIELD.displayStyle";
        String DATA_TYPE = "FORM_FIELD.dataType";
        String DICT_ID = "FORM_FIELD.dictId";
        String VALUE = "FORM_FIELD.value";
        String SORT_NUM = "FORM_FIELD.sortNum";
    }
    
    interface TABLE {
        
    }
    
    interface TABLE_FIELD {
        String NAME = "TABLE_FIELD.name";
        String DISPLAY_NAME = "TABLE_FIELD.displayName";
        String IS_DISPLAY = "TABLE_FIELD.isDisplay";
        String WIDTH = "TABLE_FIELD.width";
        String DISPLAY_STYLE = "TABLE_FIELD.displayStyle";
        String ALIGN = "TABLE_FIELD.align";
        String DICT_ID = "TABLE_FIELD.dictId";
        String SORT_NUM = "TABLE_FIELD.sortNum";
    }
}
