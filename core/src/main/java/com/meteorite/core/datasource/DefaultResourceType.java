package com.meteorite.core.datasource;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class DefaultResourceType implements ResourceType {
    private String name;
    private String description;
    private String defaultExtension;
    private String icon;
    private boolean isBinary;

    public DefaultResourceType(String name, String description, String icon) {
        this.name = name;
        this.description = description;
        this.icon = icon;
    }

    public DefaultResourceType(String name, String description, String defaultExtension, String icon) {
        this.name = name;
        this.description = description;
        this.defaultExtension = defaultExtension;
        this.icon = icon;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getDefaultExtension() {
        return defaultExtension;
    }

    @Override
    public String getIcon() {
        return icon;
    }

    @Override
    public boolean isBinary() {
        return isBinary;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDefaultExtension(String defaultExtension) {
        this.defaultExtension = defaultExtension;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setBinary(boolean isBinary) {
        this.isBinary = isBinary;
    }
}
