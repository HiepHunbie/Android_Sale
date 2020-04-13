package com.mcredit.mobile.mobile_for_sale.Models.CancelCase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mcredit.mobile.mobile_for_sale.Models.Login.Result;

/**
 * Created by hiephunbie on 3/16/18.
 */

public class CancelCase {
    @SerializedName("result")
    @Expose
    private com.mcredit.mobile.mobile_for_sale.Models.Login.Result result;

    public com.mcredit.mobile.mobile_for_sale.Models.Login.Result getResult ()
    {
        return result;
    }

    public void setResult (Result result)
    {
        this.result = result;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [result = "+result+"]";
    }
}
