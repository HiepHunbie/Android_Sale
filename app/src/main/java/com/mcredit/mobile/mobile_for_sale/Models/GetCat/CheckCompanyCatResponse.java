package com.mcredit.mobile.mobile_for_sale.Models.GetCat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hiephunbie on 4/3/18.
 */

public class CheckCompanyCatResponse {
    @SerializedName("companyName")
    @Expose
    private String companyName;

    public String getCompanyName() { return this.companyName; }

    public void setCompanyName(String companyName) { this.companyName = companyName; }
    @SerializedName("catType")
    @Expose
    private String catType;

    public String getCatType() { return this.catType; }

    public void setCatType(String catType) { this.catType = catType; }
    @SerializedName("companyAddress")
    @Expose
    private String companyAddress;

    public String getCompanyAddress() { return this.companyAddress; }

    public void setCompanyAddress(String companyAddress) { this.companyAddress = companyAddress; }
    @SerializedName("companyPhone")
    @Expose
    private String companyPhone;

    public String getCompanyPhone() { return this.companyPhone; }

    public void setCompanyPhone(String companyPhone) { this.companyPhone = companyPhone; }
}
