package com.mcredit.mobile.mobile_for_sale.Models.ReturnCase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hiephunbie on 4/10/18.
 */

public class CaseReturnArr implements Serializable {
    @SerializedName("appNumber")
    @Expose
    private int appNumber;

    public int getAppNumber() { return this.appNumber; }

    public void setAppNumber(int appNumber) { this.appNumber = appNumber; }
    @SerializedName("createdDate")
    @Expose
    private long createdDate;

    public long getCreatedDate() { return this.createdDate; }

    public void setCreatedDate(long createdDate) { this.createdDate = createdDate; }
    @SerializedName("updatedBy")
    @Expose
    private String updatedBy;

    public String getUpdatedBy() { return this.updatedBy; }

    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }
    @SerializedName("processStatus")
    @Expose
    private int processStatus;

    public int getProcessStatus() { return this.processStatus; }

    public void setProcessStatus(int processStatus) { this.processStatus = processStatus; }
    @SerializedName("createdBy")
    @Expose
    private String createdBy;

    public String getCreatedBy() { return this.createdBy; }

    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    @SerializedName("fromUser")
    @Expose
    private String fromUser;

    public String getFromUser() { return this.fromUser; }

    public void setFromUser(String fromUser) { this.fromUser = fromUser; }
    @SerializedName("returnComment")
    @Expose
    private String returnComment;

    public String getReturnComment() { return this.returnComment; }

    public void setReturnComment(String returnComment) { this.returnComment = returnComment; }
    @SerializedName("appId")
    @Expose
    private String appId;

    public String getAppId() { return this.appId; }

    public void setAppId(String appId) { this.appId = appId; }
    @SerializedName("returnId")
    @Expose
    private int returnId;

    public int getReturnId() { return this.returnId; }

    public void setReturnId(int returnId) { this.returnId = returnId; }
    @SerializedName("dataEntryReturnDetails")
    @Expose
    private ArrayList<DataEntryReturnDetail> dataEntryReturnDetails;

    public ArrayList<DataEntryReturnDetail> getDataEntryReturnDetails() { return this.dataEntryReturnDetails; }

    public void setDataEntryReturnDetails(ArrayList<DataEntryReturnDetail> dataEntryReturnDetails) { this.dataEntryReturnDetails = dataEntryReturnDetails; }
    @SerializedName("updatedDate")
    @Expose
    private String updatedDate;

    public String getUpdatedDate() { return this.updatedDate; }

    public void setUpdatedDate(String updatedDate) { this.updatedDate = updatedDate; }
    @SerializedName("dataEntrySales")
    @Expose
    private DataEntrySales dataEntrySales;

    public DataEntrySales getDataEntrySales() { return this.dataEntrySales; }

    public void setDataEntrySales(DataEntrySales dataEntrySales) { this.dataEntrySales = dataEntrySales; }
}
