package com.mcredit.mobile.mobile_for_sale.Models.ReturnCase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hiephunbie on 4/10/18.
 */

public class Result implements Serializable {
    @SerializedName("caseReturnArr")
    @Expose
    private ArrayList<CaseReturnArr> caseReturnArr;

    public ArrayList<CaseReturnArr> getCaseReturnArr() { return this.caseReturnArr; }

    public void setCaseReturnArr(ArrayList<CaseReturnArr> caseReturnArr) { this.caseReturnArr = caseReturnArr; }
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
