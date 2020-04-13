package com.mcredit.mobile.mobile_for_sale.Models.CaseNote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by hiephunbie on 6/8/18.
 */

public class AppNotesEntries {
    @SerializedName("app_notes_entry")
    @Expose
    private ArrayList<AppNotesEntry> app_notes_entry;

    public ArrayList<AppNotesEntry> getAppNotesEntry() { return this.app_notes_entry; }

    public void setAppNotesEntry(ArrayList<AppNotesEntry> app_notes_entry) { this.app_notes_entry = app_notes_entry; }
}
