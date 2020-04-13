package com.mcredit.mobile.mobile_for_sale.Models.GetInfoNewCase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by hiephunbie on 3/20/18.
 */

public class Result {
    @SerializedName("infoCase")
    @Expose
    private InfoCase infoCase;

    public InfoCase getInfoCase() { return this.infoCase; }

    public void setInfoCase(InfoCase infoCase) { this.infoCase = infoCase; }

    @SerializedName("productArr")
    @Expose
    private ArrayList<ProductArr> productArr;

    public ArrayList<ProductArr> getProductArr() { return this.productArr; }

    public void setProductArr(ArrayList<ProductArr> productArr) { this.productArr = productArr; }

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

    @SerializedName("infoStaff")
    @Expose
    private InfoStaff infoStaff;

    public InfoStaff getInfoStaff() { return this.infoStaff; }

    public void setInfoStaff(InfoStaff infoStaff) { this.infoStaff = infoStaff; }

}
