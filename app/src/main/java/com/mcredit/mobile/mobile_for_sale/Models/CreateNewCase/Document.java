package com.mcredit.mobile.mobile_for_sale.Models.CreateNewCase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by hiephunbie on 3/20/18.
 */

public class Document implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;

    public int getId() { return this.id; }

    public void setId(int id) { this.id = id; }
    @SerializedName("documentCode")
    @Expose
    private String documentCode;

    public String getDocumentCode() { return this.documentCode; }

    public void setDocumentCode(String documentCode) { this.documentCode = documentCode; }
    @SerializedName("documentName")
    @Expose
    private String documentName;

    public String getDocumentName() { return this.documentName; }

    public void setDocumentName(String documentName) { this.documentName = documentName; }
    @SerializedName("inputDocUid")
    @Expose
    private String inputDocUid;

    public String getInputDocUid() { return this.inputDocUid; }

    public void setInputDocUid(String inputDocUid) { this.inputDocUid = inputDocUid; }
    @SerializedName("mapBpmVar")
    @Expose
    private String mapBpmVar;

    public String getMapBpmVar() { return this.mapBpmVar; }

    public void setMapBpmVar(String mapBpmVar) { this.mapBpmVar = mapBpmVar; }
}
