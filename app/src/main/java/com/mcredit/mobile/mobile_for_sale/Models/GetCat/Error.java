package com.mcredit.mobile.mobile_for_sale.Models.GetCat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hiephunbie on 4/3/18.
 */

public class Error {
    @SerializedName("code")
    @Expose
    private int code;

    public int getCode() { return this.code; }

    public void setCode(int code) { this.code = code; }
    @SerializedName("message")
    @Expose
    private String message;

    public String getMessage() { return this.message; }

    public void setMessage(String message) { this.message = message; }
}
