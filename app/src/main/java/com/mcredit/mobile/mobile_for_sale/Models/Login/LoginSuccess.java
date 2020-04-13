package com.mcredit.mobile.mobile_for_sale.Models.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hiephunbie on 3/16/18.
 */

public class LoginSuccess {
    @SerializedName("result")
    @Expose
    private Result result;

    public Result getResult ()
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
