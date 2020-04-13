package com.mcredit.mobile.mobile_for_sale.Models.ReturnCase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by hiephunbie on 4/10/18.
 */

public class DataEntryReturnDetail implements Serializable {
    @SerializedName("reasonCodeLabel")
    @Expose
    private String reasonCodeLabel;

    public String getReasonCodeLabel() { return this.reasonCodeLabel; }

    public void setReasonCodeLabel(String reasonCodeLabel) { this.reasonCodeLabel = reasonCodeLabel; }
    @SerializedName("createdDate")
    @Expose
    private String createdDate;

    public String getCreatedDate() { return this.createdDate; }

    public void setCreatedDate(String createdDate) { this.createdDate = createdDate; }
    @SerializedName("createdBy")
    @Expose
    private String createdBy;

    public String getCreatedBy() { return this.createdBy; }

    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    @SerializedName("returnId")
    @Expose
    private int returnId;

    public int getReturnId() { return this.returnId; }

    public void setReturnId(int returnId) { this.returnId = returnId; }
    @SerializedName("id")
    @Expose
    private int id;

    public int getId() { return this.id; }

    public void setId(int id) { this.id = id; }
    @SerializedName("reasonCode")
    @Expose
    private String reasonCode;

    public String getReasonCode() { return this.reasonCode; }

    public void setReasonCode(String reasonCode) { this.reasonCode = reasonCode; }
    @SerializedName("history")
    @Expose
    private String history;

    public String getHistory() { return this.history; }

    public void setHistory(String history) { this.history = history; }
    @SerializedName("reasonDetailCode")
    @Expose
    private String reasonDetailCode;

    public String getReasonDetailCode() { return this.reasonDetailCode; }

    public void setReasonDetailCode(String reasonDetailCode) { this.reasonDetailCode = reasonDetailCode; }
    @SerializedName("reasonDetailCodeDetail")
    @Expose
    private String reasonDetailCodeDetail;

    public String getReasonDetailCodeDetail() { return this.reasonDetailCodeDetail; }

    public void setReasonDetailCodeDetail(String reasonDetailCodeDetail) { this.reasonDetailCodeDetail = reasonDetailCodeDetail; }
}
