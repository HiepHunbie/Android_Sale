package com.mcredit.mobile.mobile_for_sale.Models.GetCat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hiephunbie on 4/3/18.
 */

public class GetCatInfo {
    @SerializedName("result")
    @Expose
    private Result result;

    public Result getResult() { return this.result; }

    public void setResult(Result result) { this.result = result; }
}
