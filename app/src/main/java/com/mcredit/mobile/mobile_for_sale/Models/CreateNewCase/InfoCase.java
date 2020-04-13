package com.mcredit.mobile.mobile_for_sale.Models.CreateNewCase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by hiephunbie on 3/20/18.
 */

public class InfoCase implements Serializable {
    @SerializedName("mobileLoanAmount")
    @Expose
    private String mobileLoanAmount;

    public String getMobileLoanAmount() { return this.mobileLoanAmount; }

    public void setMobileLoanAmount(String mobileLoanAmount) { this.mobileLoanAmount = mobileLoanAmount; }
    @SerializedName("mobileCustomerName")
    @Expose
    private String mobileCustomerName;

    public String getMobileCustomerName() { return this.mobileCustomerName; }

    public void setMobileCustomerName(String mobileCustomerName) { this.mobileCustomerName = mobileCustomerName; }
    @SerializedName("currentTask")
    @Expose
    private String currentTask;

    public String getCurrentTask() { return this.currentTask; }

    public void setCurrentTask(String currentTask) { this.currentTask = currentTask; }
    @SerializedName("saleCode")
    @Expose
    private String saleCode;

    public String getSaleCode() { return this.saleCode; }

    public void setSaleCode(String saleCode) { this.saleCode = saleCode; }
    @SerializedName("updatedDate")
    @Expose
    private String updatedDate;

    public String getUpdatedDate() { return this.updatedDate; }

    public void setUpdatedDate(String updatedDate) { this.updatedDate = updatedDate; }
    @SerializedName("isSync")
    @Expose
    private String isSync;

    public String getIsSync() { return this.isSync; }

    public void setIsSync(String isSync) { this.isSync = isSync; }
    @SerializedName("saleName")
    @Expose
    private String saleName;

    public String getSaleName() { return this.saleName; }

    public void setSaleName(String saleName) { this.saleName = saleName; }
    @SerializedName("mobileUpdatedDate")
    @Expose
    private String mobileUpdatedDate;

    public String getMobileUpdatedDate() { return this.mobileUpdatedDate; }

    public void setMobileUpdatedDate(String mobileUpdatedDate) { this.mobileUpdatedDate = mobileUpdatedDate; }
    @SerializedName("appId")
    @Expose
    private String appId;

    public String getAppId() { return this.appId; }

    public void setAppId(String appId) { this.appId = appId; }
    @SerializedName("mobileSchemaProductName")
    @Expose
    private String mobileSchemaProductName;

    public String getMobileSchemaProductName() { return this.mobileSchemaProductName; }

    public void setMobileSchemaProductName(String mobileSchemaProductName) { this.mobileSchemaProductName = mobileSchemaProductName; }
    @SerializedName("mobileTempResidenceLabel")
    @Expose
    private String mobileTempResidenceLabel;

    public String getMobileTempResidenceLabel() { return this.mobileTempResidenceLabel; }

    public void setMobileTempResidenceLabel(String mobileTempResidenceLabel) { this.mobileTempResidenceLabel = mobileTempResidenceLabel; }
    @SerializedName("mobileIssueDateCitizen")
    @Expose
    private String mobileIssueDateCitizen;

    public String getMobileIssueDateCitizen() { return this.mobileIssueDateCitizen; }

    public void setMobileIssueDateCitizen(String mobileIssueDateCitizen) { this.mobileIssueDateCitizen = mobileIssueDateCitizen; }
    @SerializedName("mobileCitizenId")
    @Expose
    private String mobileCitizenId;

    public String getMobileCitizenId() { return this.mobileCitizenId; }

    public void setMobileCitizenId(String mobileCitizenId) { this.mobileCitizenId = mobileCitizenId; }
    @SerializedName("mobileHasInsurrance")
    @Expose
    private String mobileHasInsurrance;

    public String getMobileHasInsurrance() { return this.mobileHasInsurrance; }

    public void setMobileHasInsurrance(String mobileHasInsurrance) { this.mobileHasInsurrance = mobileHasInsurrance; }
    @SerializedName("id")
    @Expose
    private int id;

    public int getId() { return this.id; }

    public void setId(int id) { this.id = id; }
    @SerializedName("mobileCreatedDate")
    @Expose
    private String mobileCreatedDate;

    public String getMobileCreatedDate() { return this.mobileCreatedDate; }

    public void setMobileCreatedDate(String mobileCreatedDate) { this.mobileCreatedDate = mobileCreatedDate; }
    @SerializedName("mobileIssuePlace")
    @Expose
    private String mobileIssuePlace;

    public String getMobileIssuePlace() { return this.mobileIssuePlace; }

    public void setMobileIssuePlace(String mobileIssuePlace) { this.mobileIssuePlace = mobileIssuePlace; }
    @SerializedName("mobileCode")
    @Expose
    private String mobileCode;

    public String getMobileCode() { return this.mobileCode; }

    public void setMobileCode(String mobileCode) { this.mobileCode = mobileCode; }
    @SerializedName("appNumber")
    @Expose
    private String appNumber;

    public String getAppNumber() { return this.appNumber; }

    public void setAppNumber(String appNumber) { this.appNumber = appNumber; }
    @SerializedName("updatedBy")
    @Expose
    private String updatedBy;

    public String getUpdatedBy() { return this.updatedBy; }

    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }
    @SerializedName("mobileUpdatedBy")
    @Expose
    private String mobileUpdatedBy;

    public String getMobileUpdatedBy() { return this.mobileUpdatedBy; }

    public void setMobileUpdatedBy(String mobileUpdatedBy) { this.mobileUpdatedBy = mobileUpdatedBy; }
    @SerializedName("saleMobile")
    @Expose
    private String saleMobile;

    public String getSaleMobile() { return this.saleMobile; }

    public void setSaleMobile(String saleMobile) { this.saleMobile = saleMobile; }
    @SerializedName("salePath")
    @Expose
    private String salePath;

    public String getSalePath() { return this.salePath; }

    public void setSalePath(String salePath) { this.salePath = salePath; }
    @SerializedName("mobileLoanTenor")
    @Expose
    private String mobileLoanTenor;

    public String getMobileLoanTenor() { return this.mobileLoanTenor; }

    public void setMobileLoanTenor(String mobileLoanTenor) { this.mobileLoanTenor = mobileLoanTenor; }
    @SerializedName("mobileCreatedBy")
    @Expose
    private String mobileCreatedBy;

    public String getMobileCreatedBy() { return this.mobileCreatedBy; }

    public void setMobileCreatedBy(String mobileCreatedBy) { this.mobileCreatedBy = mobileCreatedBy; }
    @SerializedName("createdDate")
    @Expose
    private long createdDate;

    public long getCreatedDate() { return this.createdDate; }

    public void setCreatedDate(long createdDate) { this.createdDate = createdDate; }
    @SerializedName("mobileSchemaProductCode")
    @Expose
    private String mobileSchemaProductCode;

    public String getMobileSchemaProductCode() { return this.mobileSchemaProductCode; }

    public void setMobileSchemaProductCode(String mobileSchemaProductCode) { this.mobileSchemaProductCode = mobileSchemaProductCode; }
    @SerializedName("createdBy")
    @Expose
    private String createdBy;

    public String getCreatedBy() { return this.createdBy; }

    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    @SerializedName("mobileAppCode")
    @Expose
    private String mobileAppCode;

    public String getMobileAppCode() { return this.mobileAppCode; }

    public void setMobileAppCode(String mobileAppCode) { this.mobileAppCode = mobileAppCode; }
    @SerializedName("mobileCodeUpdate")
    @Expose
    private String mobileCodeUpdate;

    public String getMobileCodeUpdate() { return this.mobileCodeUpdate; }

    public void setMobileCodeUpdate(String mobileCodeUpdate) { this.mobileCodeUpdate = mobileCodeUpdate; }
    @SerializedName("mobileTemResidence")
    @Expose
    private String mobileTemResidence;

    public String getMobileTemResidence() { return this.mobileTemResidence; }

    public void setMobileTemResidence(String mobileTemResidence) { this.mobileTemResidence = mobileTemResidence; }
}

