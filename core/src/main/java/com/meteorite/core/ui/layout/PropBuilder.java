package com.meteorite.core.ui.layout;

import com.meteorite.core.ui.model.LayoutProperty;

/**
 * 属性Builder
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class PropBuilder {
    private LayoutProperty property;

    private PropBuilder(LayoutProperty property) {
        this.property = property;
    }

    public static PropBuilder create() {
        return new PropBuilder(new LayoutProperty());
    }

    public LayoutProperty build() {
        return property;
    }

    public PropBuilder name(String name) {
        property.setName(name);
        return this;
    }

    public PropBuilder displayName(String displayName) {
        property.setDisplayName(displayName);
        return this;
    }

    public PropBuilder desc(String desc) {
        property.setDescription(desc);
        return this;
    }

    public PropBuilder sortNum(int sortNum) {
        property.setSortNum(sortNum);
        return this;
    }

    public PropBuilder layoutType(LayoutType layoutType) {
        property.setLayoutType(layoutType);
        return this;
    }

    public PropBuilder propType(PropertyType propertyType) {
        property.setPropType(propertyType);
        return this;
    }

    public PropBuilder defaultValue(String defaultValue) {
        property.setDefaultValue(defaultValue);
        return this;
    }
}
