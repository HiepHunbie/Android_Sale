package com.mcredit.mobile.mobile_for_sale.Models.GetInfoNewCase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hiephunbie on 3/20/18.
 */

public class Config
{
    @SerializedName("name")
    @Expose
    private String name;

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }
    @SerializedName("bpmVar")
    @Expose
    private String bpmVar;

    public String getBpmVar() { return this.bpmVar; }

    public void setBpmVar(String bpmVar) { this.bpmVar = bpmVar; }
    @SerializedName("parameterId")
    @Expose
    private int parameterId;

    public int getParameterId() { return this.parameterId; }

    public void setParameterId(int parameterId) { this.parameterId = parameterId; }
    @SerializedName("minValue")
    @Expose
    private int minValue;

    public int getMinValue() { return this.minValue; }

    public void setMinValue(int minValue) { this.minValue = minValue; }
    @SerializedName("maxValue")
    @Expose
    private int maxValue;

    public int getMaxValue() { return this.maxValue; }

    public void setMaxValue(int maxValue) { this.maxValue = maxValue; }
    @SerializedName("expMinValue")
    @Expose
    private String expMinValue;

    public String getExpMinValue() { return this.expMinValue; }

    public void setExpMinValue(String expMinValue) { this.expMinValue = expMinValue; }
    @SerializedName("expMaxValue")
    @Expose
    private String expMaxValue;

    public String getExpMaxValue() { return this.expMaxValue; }

    public void setExpMaxValue(String expMaxValue) { this.expMaxValue = expMaxValue; }
    @SerializedName("required")
    @Expose
    private int required;

    public int getRequired() { return this.required; }

    public void setRequired(int required) { this.required = required; }
}


