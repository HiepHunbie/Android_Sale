package com.mcredit.mobile.mobile_for_sale.Models.DraftCase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by hiephunbie on 4/4/18.
 */

public class Result {
    @SerializedName("arrCase")
    @Expose
    private ArrayList<ArrCase> arrCase;

    public ArrayList<ArrCase> getArrCase() { return this.arrCase; }

    public void setArrCase(ArrayList<ArrCase> arrCase) { this.arrCase = arrCase; }
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
