package com.mcredit.mobile.mobile_for_sale.Models.GetInfoNewCase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hiephunbie on 3/22/18.
 */

public class HubKiosk
{
    @SerializedName("ActiveDate")
    @Expose
    private String ActiveDate;

    public String getActiveDate() { return this.ActiveDate; }

    public void setActiveDate(String ActiveDate) { this.ActiveDate = ActiveDate; }
    @SerializedName("Address")
    @Expose
    private String Address;

    public String getAddress() { return this.Address; }

    public void setAddress(String Address) { this.Address = Address; }
    @SerializedName("DeactiveDate")
    @Expose
    private String DeactiveDate;

    public String getDeactiveDate() { return this.DeactiveDate; }

    public void setDeactiveDate(String DeactiveDate) { this.DeactiveDate = DeactiveDate; }
    @SerializedName("DealerGroupCode")
    @Expose
    private String DealerGroupCode;

    public String getDealerGroupCode() { return this.DealerGroupCode; }

    public void setDealerGroupCode(String DealerGroupCode) { this.DealerGroupCode = DealerGroupCode; }
    @SerializedName("DealerGroupName")
    @Expose
    private String DealerGroupName;

    public String getDealerGroupName() { return this.DealerGroupName; }

    public void setDealerGroupName(String DealerGroupName) { this.DealerGroupName = DealerGroupName; }
    @SerializedName("DepartmentCode")
    @Expose
    private String DepartmentCode;

    public String getDepartmentCode() { return this.DepartmentCode; }

    public void setDepartmentCode(String DepartmentCode) { this.DepartmentCode = DepartmentCode; }
    @SerializedName("DepartmentName")
    @Expose
    private String DepartmentName;

    public String getDepartmentName() { return this.DepartmentName; }

    public void setDepartmentName(String DepartmentName) { this.DepartmentName = DepartmentName; }
    @SerializedName("Id")
    @Expose
    private String Id;

    public String getId() { return this.Id; }

    public void setId(String Id) { this.Id = Id; }
    @SerializedName("LocationId")
    @Expose
    private String LocationId;

    public String getLocationId() { return this.LocationId; }

    public void setLocationId(String LocationId) { this.LocationId = LocationId; }
    @SerializedName("LocationPath")
    @Expose
    private String LocationPath;

    public String getLocationPath() { return this.LocationPath; }

    public void setLocationPath(String LocationPath) { this.LocationPath = LocationPath; }
    @SerializedName("ProvinceIsoCode")
    @Expose
    private String ProvinceIsoCode;

    public String getProvinceIsoCode() { return this.ProvinceIsoCode; }

    public void setProvinceIsoCode(String ProvinceIsoCode) { this.ProvinceIsoCode = ProvinceIsoCode; }
    @SerializedName("ProvinceLabel")
    @Expose
    private String ProvinceLabel;

    public String getProvinceLabel() { return this.ProvinceLabel; }

    public void setProvinceLabel(String ProvinceLabel) { this.ProvinceLabel = ProvinceLabel; }
    @SerializedName("ProvinceName")
    @Expose
    private String ProvinceName;

    public String getProvinceName() { return this.ProvinceName; }

    public void setProvinceName(String ProvinceName) { this.ProvinceName = ProvinceName; }
}