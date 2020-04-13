package com.mcredit.mobile.mobile_for_sale.Models.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hiephunbie on 3/16/18.
 */

public class Info {
    @SerializedName("lastName")
    @Expose
    private String lastName;

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("updatedDate")
    @Expose
    private String updatedDate;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("updatedBy")
    @Expose
    private String updatedBy;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("userProfiles")
    @Expose
    private String userProfiles;
    @SerializedName("userPIN")
    @Expose
    private String userPIN;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("ssoId")
    @Expose
    private String ssoId;
    @SerializedName("token")
    @Expose
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLastName ()
    {
        return lastName;
    }

    public void setLastName (String lastName)
    {
        this.lastName = lastName;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getUpdatedDate ()
    {
        return updatedDate;
    }

    public void setUpdatedDate (String updatedDate)
    {
        this.updatedDate = updatedDate;
    }

    public String getPassword ()
    {
        return password;
    }

    public void setPassword (String password)
    {
        this.password = password;
    }

    public String getUpdatedBy ()
    {
        return updatedBy;
    }

    public void setUpdatedBy (String updatedBy)
    {
        this.updatedBy = updatedBy;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getCreatedBy ()
    {
        return createdBy;
    }

    public void setCreatedBy (String createdBy)
    {
        this.createdBy = createdBy;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getUserProfiles ()
    {
        return userProfiles;
    }

    public void setUserProfiles (String userProfiles)
    {
        this.userProfiles = userProfiles;
    }

    public String getUserPIN ()
    {
        return userPIN;
    }

    public void setUserPIN (String userPIN)
    {
        this.userPIN = userPIN;
    }

    public String getFirstName ()
    {
        return firstName;
    }

    public void setFirstName (String firstName)
    {
        this.firstName = firstName;
    }

    public String getCreatedDate ()
    {
        return createdDate;
    }

    public void setCreatedDate (String createdDate)
    {
        this.createdDate = createdDate;
    }

    public String getSsoId ()
    {
        return ssoId;
    }

    public void setSsoId (String ssoId)
    {
        this.ssoId = ssoId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [lastName = "+lastName+", status = "+status+", updatedDate = "+updatedDate+", password = "+password+", updatedBy = "+updatedBy+", id = "+id+", createdBy = "+createdBy+", email = "+email+", userProfiles = "+userProfiles+", userPIN = "+userPIN+", firstName = "+firstName+", createdDate = "+createdDate+", ssoId = "+ssoId+"]";
    }
}
