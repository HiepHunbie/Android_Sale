package com.mcredit.mobile.mobile_for_sale.Models.Notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by hiephunbie on 5/28/18.
 */

public class Result {
    @SerializedName("messages")
    @Expose
    private ArrayList<Message> messages;

    public ArrayList<Message> getMessages() { return this.messages; }

    public void setMessages(ArrayList<Message> messages) { this.messages = messages; }
    @SerializedName("httpCode")
    @Expose
    private int httpCode;

    public int getHttpCode() { return this.httpCode; }

    public void setHttpCode(int httpCode) { this.httpCode = httpCode; }
    @SerializedName("httpMessage")
    @Expose
    private String httpMessage;

    public String getHttpMessage() { return this.httpMessage; }

    public void setHttpMessage(String httpMessage) { this.httpMessage = httpMessage; }
    @SerializedName("token")
    @Expose
    private String token;

    public String getToken() { return this.token; }

    public void setToken(String token) { this.token = token; }
}
