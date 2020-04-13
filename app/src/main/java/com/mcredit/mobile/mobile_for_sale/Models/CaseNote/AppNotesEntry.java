package com.mcredit.mobile.mobile_for_sale.Models.CaseNote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hiephunbie on 6/6/18.
 */

public class AppNotesEntry {
    @SerializedName("app_uid")
    @Expose
    private String app_uid;

    public String getAppUid() { return this.app_uid; }

    public void setAppUid(String app_uid) { this.app_uid = app_uid; }
    @SerializedName("usr_uid")
    @Expose
    private String usr_uid;

    public String getUsrUid() { return this.usr_uid; }

    public void setUsrUid(String usr_uid) { this.usr_uid = usr_uid; }
    @SerializedName("note_date")
    @Expose
    private String note_date;

    public String getNoteDate() { return this.note_date; }

    public void setNoteDate(String note_date) { this.note_date = note_date; }
    @SerializedName("note_content")
    @Expose
    private String note_content;

    public String getNoteContent() { return this.note_content; }

    public void setNoteContent(String note_content) { this.note_content = note_content; }
}
