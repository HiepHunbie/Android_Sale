package com.mcredit.mobile.mobile_for_sale.Models.GetCat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hiephunbie on 4/3/18.
 */

public class Res {
    @SerializedName("checkCompanyCatResponse")
    @Expose
    private CheckCompanyCatResponse checkCompanyCatResponse;

    public CheckCompanyCatResponse getCheckCompanyCatResponse() { return this.checkCompanyCatResponse; }

    public void setCheckCompanyCatResponse(CheckCompanyCatResponse checkCompanyCatResponse) { this.checkCompanyCatResponse = checkCompanyCatResponse; }
    @SerializedName("error")
    @Expose
    private Error error;

    public Error getError() { return this.error; }

    public void setError(Error error) { this.error = error; }
}
