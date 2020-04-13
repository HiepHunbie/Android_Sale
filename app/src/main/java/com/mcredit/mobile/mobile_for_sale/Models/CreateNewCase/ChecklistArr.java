package com.mcredit.mobile.mobile_for_sale.Models.CreateNewCase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hiephunbie on 3/20/18.
 */

public class ChecklistArr implements Serializable {
    @SerializedName("groupId")
    @Expose
    private int groupId;

    public int getGroupId() { return this.groupId; }

    public void setGroupId(int groupId) { this.groupId = groupId; }
    @SerializedName("groupName")
    @Expose
    private String groupName;

    public String getGroupName() { return this.groupName; }

    public void setGroupName(String groupName) { this.groupName = groupName; }
    @SerializedName("mandatory")
    @Expose
    private int mandatory;

    public int getMandatory() { return this.mandatory; }

    public void setMandatory(int mandatory) { this.mandatory = mandatory; }
    @SerializedName("hasAlternate")
    @Expose
    private int hasAlternate;

    public int getHasAlternate() { return this.hasAlternate; }

    public void setHasAlternate(int hasAlternate) { this.hasAlternate = hasAlternate; }
    @SerializedName("documents")
    @Expose
    private ArrayList<Document> documents;

    public ArrayList<Document> getDocuments() { return this.documents; }

    public void setDocuments(ArrayList<Document> documents) { this.documents = documents;}
    @SerializedName("alternateGroups")
    @Expose
    private ArrayList<Integer> alternateGroups;

    public ArrayList<Integer> getAlternateGroups() { return this.alternateGroups; }

    public void setAlternateGroups(ArrayList<Integer> alternateGroups) { this.alternateGroups = alternateGroups; }
}
