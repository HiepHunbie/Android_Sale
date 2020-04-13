package com.mcredit.mobile.mobile_for_sale.Models.GetPDF;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by hiephunbie on 5/28/18.
 */

public class Result {
    @SerializedName("PDFList")
    @Expose
    private ArrayList<PDFList> PDFList;

    public ArrayList<PDFList> getPDFList() { return this.PDFList; }

    public void setPDFList(ArrayList<PDFList> PDFList) { this.PDFList = PDFList; }
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
