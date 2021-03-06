package com.mcredit.mobile.mobile_for_sale.Models.GetCat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hiephunbie on 4/3/18.
 */

public class Result {
    @SerializedName("res")
    @Expose
    private Res res;

    public Res getRes() { return this.res; }

    public void setRes(Res res) { this.res = res; }
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
