package com.mcredit.mobile.mobile_for_sale.Models.CreateNewCase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hiephunbie on 3/20/18.
 */

public class Result implements Serializable
{
    @SerializedName("checklistArr")
    @Expose
    private ArrayList<ChecklistArr> checklistArr;

    public ArrayList<ChecklistArr> getChecklistArr() { return this.checklistArr; }

    public void setChecklistArr(ArrayList<ChecklistArr> checklistArr) { this.checklistArr = checklistArr; }
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
    @SerializedName("infoCase")
    @Expose
    private InfoCase infoCase;

    public InfoCase getInfoCase() { return this.infoCase; }

    public void setInfoCase(InfoCase infoCase) { this.infoCase = infoCase; }
}
