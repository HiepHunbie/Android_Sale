package com.mcredit.mobile.mobile_for_sale.Models.GetSchemeUpdate;

import com.mcredit.mobile.mobile_for_sale.Models.CreateNewCase.Result;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hiephunbie on 4/10/18.
 */

public class ResultSchemeUpdate {
    @SerializedName("result")
    @Expose
    private Result result;

    public Result getResult() { return this.result; }

    public void setResult(Result result) { this.result = result; }
}
