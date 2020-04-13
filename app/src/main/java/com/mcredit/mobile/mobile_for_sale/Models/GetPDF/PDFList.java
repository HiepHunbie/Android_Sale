package com.mcredit.mobile.mobile_for_sale.Models.GetPDF;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hiephunbie on 5/28/18.
 */

public class PDFList {
    @SerializedName("createdDate")
    @Expose
    private String createdDate;

    public String getCreatedDate() { return this.createdDate; }

    public void setCreatedDate(String createdDate) { this.createdDate = createdDate; }
    @SerializedName("documentType")
    @Expose
    private String documentType;

    public String getDocumentType() { return this.documentType; }

    public void setDocumentType(String documentType) { this.documentType = documentType; }
    @SerializedName("appId")
    @Expose
    private String appId;

    public String getAppId() { return this.appId; }

    public void setAppId(String appId) { this.appId = appId; }
    @SerializedName("id")
    @Expose
    private int id;

    public int getId() { return this.id; }

    public void setId(int id) { this.id = id; }
    @SerializedName("mobileAppCode")
    @Expose
    private String mobileAppCode;

    public String getMobileAppCode() { return this.mobileAppCode; }

    public void setMobileAppCode(String mobileAppCode) { this.mobileAppCode = mobileAppCode; }
}
