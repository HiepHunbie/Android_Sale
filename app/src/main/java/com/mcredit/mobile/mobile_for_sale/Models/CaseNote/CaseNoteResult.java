package com.mcredit.mobile.mobile_for_sale.Models.CaseNote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hiephunbie on 6/8/18.
 */

public class CaseNoteResult {
    @SerializedName("app_notes_entries")
    @Expose
    private AppNotesEntries app_notes_entries;

    public AppNotesEntries getAppNotesEntries() { return this.app_notes_entries; }

    public void setAppNotesEntries(AppNotesEntries app_notes_entries) { this.app_notes_entries = app_notes_entries; }
}
