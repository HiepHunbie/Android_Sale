package com.mcredit.mobile.mobile_for_sale.Models.Database;

import java.io.Serializable;

/**
 * Created by hiephunbie on 5/22/18.
 */

public class DraftImage implements Serializable {
    public String code_id,mobileAppCode,documentGroup,documentCode,linkImage;
    private String variableMapping,documentName;
    public DraftImage(String code_id,String mobileAppCode,String documentGroup,String documentCode,String linkImage,String variableMapping,String documentName){
        this.code_id=code_id;
        this.mobileAppCode = mobileAppCode;
        this.documentGroup = documentGroup;
        this.documentCode = documentCode;
        this.linkImage = linkImage;
        this.variableMapping = variableMapping;
        this.documentName=documentName;
    }
    public DraftImage() {

    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getVariableMapping() {
        return variableMapping;
    }

    public void setVariableMapping(String variableMapping) {
        this.variableMapping = variableMapping;
    }

    public String getCode_id() {
        return code_id;
    }

    public void setCode_id(String code_id) {
        this.code_id = code_id;
    }

    public String getMobileAppCode() {
        return mobileAppCode;
    }

    public void setMobileAppCode(String mobileAppCode) {
        this.mobileAppCode = mobileAppCode;
    }

    public String getDocumentGroup() {
        return documentGroup;
    }

    public void setDocumentGroup(String documentGroup) {
        this.documentGroup = documentGroup;
    }

    public String getDocumentCode() {
        return documentCode;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }
}
