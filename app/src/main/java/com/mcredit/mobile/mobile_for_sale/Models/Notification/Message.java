package com.mcredit.mobile.mobile_for_sale.Models.Notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hiephunbie on 5/28/18.
 */

public class Message {
    @SerializedName("ssoId")
    @Expose
    private String ssoId;

    public String getSsoId() { return this.ssoId; }

    public void setSsoId(String ssoId) { this.ssoId = ssoId; }
    @SerializedName("createdDate")
    @Expose
    private String createdDate;

    public String getCreatedDate() { return this.createdDate; }

    public void setCreatedDate(String createdDate) { this.createdDate = createdDate; }
    @SerializedName("appNumber")
    @Expose
    private int appNumber;

    public int getAppNumber() { return this.appNumber; }

    public void setAppNumber(int appNumber) { this.appNumber = appNumber; }
    @SerializedName("createdBy")
    @Expose
    private String createdBy;

    public String getCreatedBy() { return this.createdBy; }

    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
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
    @SerializedName("message")
    @Expose
    private String message;

    public String getMessage() { return this.message; }

    public void setMessage(String message) { this.message = message; }
    @SerializedName("deviceId")
    @Expose
    private String deviceId;

    public String getDeviceId() { return this.deviceId; }

    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }
    @SerializedName("status")
    @Expose
    private int status;

    public int getStatus() { return this.status; }

    public void setStatus(int status) { this.status = status; }
}
