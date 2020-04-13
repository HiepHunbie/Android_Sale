package com.mcredit.mobile.mobile_for_sale.Models.MainModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hiephunbie on 5/22/18.
 */

public class Data {
    @SerializedName("numcasedrafted")
    @Expose
    private int numcasedrafted;

    public int getNumcasedrafted() { return this.numcasedrafted; }

    public void setNumcasedrafted(int numcasedrafted) { this.numcasedrafted = numcasedrafted; }
    @SerializedName("numcaseReturned")
    @Expose
    private int numcaseReturned;

    public int getNumcaseReturned() { return this.numcaseReturned; }

    public void setNumcaseReturned(int numcaseReturned) { this.numcaseReturned = numcaseReturned; }
    @SerializedName("numCaseProcessed")
    @Expose
    private int numCaseProcessed;

    public int getNumCaseProcessed() { return this.numCaseProcessed; }

    public void setNumCaseProcessed(int numCaseProcessed) { this.numCaseProcessed = numCaseProcessed; }
}
