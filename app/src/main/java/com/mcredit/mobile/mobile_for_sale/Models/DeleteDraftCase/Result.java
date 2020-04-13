package com.mcredit.mobile.mobile_for_sale.Models.DeleteDraftCase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hiephunbie on 3/16/18.
 */

public class Result {
    @SerializedName("httpCode")
    @Expose
    private int httpCode;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("httpMessage")
    @Expose
    private String httpMessage;

    public int getHttpCode ()
    {
        return httpCode;
    }

    public void setHttpCode (int httpCode)
    {
        this.httpCode = httpCode;
    }

    public String getToken ()
    {
        return token;
    }

    public void setToken (String token)
    {
        this.token = token;
    }

    public String getHttpMessage ()
    {
        return httpMessage;
    }

    public void setHttpMessage (String httpMessage)
    {
        this.httpMessage = httpMessage;
    }

}
