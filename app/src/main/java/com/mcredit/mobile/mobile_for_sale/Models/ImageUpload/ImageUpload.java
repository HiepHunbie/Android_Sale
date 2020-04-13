package com.mcredit.mobile.mobile_for_sale.Models.ImageUpload;

/**
 * Created by hiephunbie on 3/22/18.
 */

public class ImageUpload {

    private String url;
    private String schemeCode;
    private String variableMapping;
    private String schemeGroup;
    private String schemeName;

    public ImageUpload(String url, String schemeCode,String variableMapping,String schemeGroup,String schemeName) {
        this.url = url;
        this.schemeCode = schemeCode;
        this.variableMapping = variableMapping;
        this.schemeGroup = schemeGroup;
        this.schemeName = schemeName;
    }

    public String getSchemeGroup() {
        return schemeGroup;
    }

    public void setSchemeGroup(String schemeGroup) {
        this.schemeGroup = schemeGroup;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getVariableMapping() {
        return variableMapping;
    }

    public void setVariableMapping(String variableMapping) {
        this.variableMapping = variableMapping;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }
}
