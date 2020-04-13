package com.mcredit.mobile.mobile_for_sale.Models.CreateNewCase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by hiephunbie on 3/20/18.
 */

public class ResultCreateNewCase implements Serializable{
    @SerializedName("result")
    @Expose
    private Result result;

    public Result getResult() { return this.result; }

    public void setResult(Result result) { this.result = result; }

}
