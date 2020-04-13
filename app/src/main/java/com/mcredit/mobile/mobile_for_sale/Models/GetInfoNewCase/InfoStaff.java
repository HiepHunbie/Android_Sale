package com.mcredit.mobile.mobile_for_sale.Models.GetInfoNewCase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by hiephunbie on 3/22/18.
 */

public class InfoStaff
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
    @SerializedName("AdCode")
    @Expose
    private String AdCode;

    public String getAdCode() { return this.AdCode; }

    public void setAdCode(String AdCode) { this.AdCode = AdCode; }
    @SerializedName("CreatedBy")
    @Expose
    private String CreatedBy;

    public String getCreatedBy() { return this.CreatedBy; }

    public void setCreatedBy(String CreatedBy) { this.CreatedBy = CreatedBy; }
    @SerializedName("CreatedDate")
    @Expose
    private String CreatedDate;

    public String getCreatedDate() { return this.CreatedDate; }

    public void setCreatedDate(String CreatedDate) { this.CreatedDate = CreatedDate; }
    @SerializedName("DateOfBirth")
    @Expose
    private String DateOfBirth;

    public String getDateOfBirth() { return this.DateOfBirth; }

    public void setDateOfBirth(String DateOfBirth) { this.DateOfBirth = DateOfBirth; }
    @SerializedName("DeactiveDate")
    @Expose
    private String DeactiveDate;

    public String getDeactiveDate() { return this.DeactiveDate; }

    public void setDeactiveDate(String DeactiveDate) { this.DeactiveDate = DeactiveDate; }
    @SerializedName("DepartmentCode")
    @Expose
    private String DepartmentCode;

    public String getDepartmentCode() { return this.DepartmentCode; }

    public void setDepartmentCode(String DepartmentCode) { this.DepartmentCode = DepartmentCode; }
    @SerializedName("DepartmentId")
    @Expose
    private String DepartmentId;

    public String getDepartmentId() { return this.DepartmentId; }

    public void setDepartmentId(String DepartmentId) { this.DepartmentId = DepartmentId; }
    @SerializedName("DepartmentPath")
    @Expose
    private String DepartmentPath;

    public String getDepartmentPath() { return this.DepartmentPath; }

    public void setDepartmentPath(String DepartmentPath) { this.DepartmentPath = DepartmentPath; }
    @SerializedName("ExtPhone")
    @Expose
    private String ExtPhone;

    public String getExtPhone() { return this.ExtPhone; }

    public void setExtPhone(String ExtPhone) { this.ExtPhone = ExtPhone; }
    @SerializedName("Gender")
    @Expose
    private String Gender;

    public String getGender() { return this.Gender; }

    public void setGender(String Gender) { this.Gender = Gender; }
    @SerializedName("HrCode")
    @Expose
    private String HrCode;

    public String getHrCode() { return this.HrCode; }

    public void setHrCode(String HrCode) { this.HrCode = HrCode; }
    @SerializedName("Id")
    @Expose
    private String Id;

    public String getId() { return this.Id; }

    public void setId(String Id) { this.Id = Id; }
    @SerializedName("LeaveDate")
    @Expose
    private String LeaveDate;

    public String getLeaveDate() { return this.LeaveDate; }

    public void setLeaveDate(String LeaveDate) { this.LeaveDate = LeaveDate; }
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
    @SerializedName("MaritalStatus")
    @Expose
    private String MaritalStatus;

    public String getMaritalStatus() { return this.MaritalStatus; }

    public void setMaritalStatus(String MaritalStatus) { this.MaritalStatus = MaritalStatus; }
    @SerializedName("MobilePhone")
    @Expose
    private String MobilePhone;

    public String getMobilePhone() { return this.MobilePhone; }

    public void setMobilePhone(String MobilePhone) { this.MobilePhone = MobilePhone; }
    @SerializedName("Name")
    @Expose
    private String Name;

    public String getName() { return this.Name; }

    public void setName(String Name) { this.Name = Name; }
    @SerializedName("OnBoardDate")
    @Expose
    private String OnBoardDate;

    public String getOnBoardDate() { return this.OnBoardDate; }

    public void setOnBoardDate(String OnBoardDate) { this.OnBoardDate = OnBoardDate; }
    @SerializedName("StaffCode")
    @Expose
    private String StaffCode;

    public String getStaffCode() { return this.StaffCode; }

    public void setStaffCode(String StaffCode) { this.StaffCode = StaffCode; }
    @SerializedName("HubKiosk")
    @Expose
    private ArrayList<HubKiosk> HubKiosk;

    public ArrayList<HubKiosk> getHubKiosk() { return this.HubKiosk; }

    public void setHubKiosk(ArrayList<HubKiosk> HubKiosk) { this.HubKiosk = HubKiosk; }
}