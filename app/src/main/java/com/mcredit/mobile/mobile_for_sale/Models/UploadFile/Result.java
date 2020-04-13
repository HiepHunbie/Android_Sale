package com.mcredit.mobile.mobile_for_sale.Models.UploadFile;

import com.mcredit.mobile.mobile_for_sale.Models.Login.Info;
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
    @SerializedName("info")
    @Expose
    private Info info;

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

    public Info getInfo ()
    {
        return info;
    }

    public void setInfo (Info info)
    {
        this.info = info;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [httpCode = "+httpCode+", token = "+token+", httpMessage = "+httpMessage+", info = "+info+"]";
    }
}
