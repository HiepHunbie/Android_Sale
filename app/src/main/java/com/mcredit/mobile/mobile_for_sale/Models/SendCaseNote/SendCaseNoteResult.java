package com.mcredit.mobile.mobile_for_sale.Models.SendCaseNote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mcredit.mobile.mobile_for_sale.Models.Login.*;

/**
 * Created by hiephunbie on 6/8/18.
 */

public class SendCaseNoteResult {
    @SerializedName("result")
    @Expose
    private com.mcredit.mobile.mobile_for_sale.Models.Login.Result result;

    public com.mcredit.mobile.mobile_for_sale.Models.Login.Result getResult ()
    {
        return result;
    }

    public void setResult (com.mcredit.mobile.mobile_for_sale.Models.Login.Result result)
    {
        this.result = result;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [result = "+result+"]";
    }
}
