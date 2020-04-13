package com.mcredit.mobile.mobile_for_sale.Models.GetInfoNewCase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by hiephunbie on 3/20/18.
 */
public class ProductArr
{
    @SerializedName("productId")
    @Expose
    private int productId;

    public int getProductId() { return this.productId; }

    public void setProductId(int productId) { this.productId = productId; }

    @SerializedName("productName")
    @Expose
    private String productName;

    public String getProductName() { return this.productName; }

    public void setProductName(String productName) { this.productName = productName; }

    @SerializedName("productCode")
    @Expose
    private String productCode;

    public String getProductCode() { return this.productCode; }

    public void setProductCode(String productCode) { this.productCode = productCode; }

    @SerializedName("PTI")
    @Expose
    private int PTI;

    public int getPTI() { return this.PTI; }

    public void setPTI(int PTI) { this.PTI = PTI; }
    @SerializedName("monthInterest")
    @Expose
    private double monthInterest;

    public double getMonthInterest() { return this.monthInterest; }

    public void setMonthInterest(double monthInterest) { this.monthInterest = monthInterest; }
    @SerializedName("yearInterest")
    @Expose
    private int yearInterest;

    public int getYearInterest() { return this.yearInterest; }

    public void setYearInterest(int yearInterest) { this.yearInterest = yearInterest; }
    @SerializedName("latePenaltyInterest")
    @Expose
    private int latePenaltyInterest;

    public int getLatePenaltyInterest() { return this.latePenaltyInterest; }

    public void setLatePenaltyInterest(int latePenaltyInterest) { this.latePenaltyInterest = latePenaltyInterest; }
    @SerializedName("latePenaltyFee")
    @Expose
    private int latePenaltyFee;

    public int getLatePenaltyFee() { return this.latePenaltyFee; }

    public void setLatePenaltyFee(int latePenaltyFee) { this.latePenaltyFee = latePenaltyFee; }
    @SerializedName("earlySettlementCondition")
    @Expose
    private String earlySettlementCondition;

    public String getEarlySettlementCondition() { return this.earlySettlementCondition; }

    public void setEarlySettlementCondition(String earlySettlementCondition) { this.earlySettlementCondition = earlySettlementCondition; }
    @SerializedName("earlySettlementFee")
    @Expose
    private int earlySettlementFee;

    public int getEarlySettlementFee() { return this.earlySettlementFee; }

    public void setEarlySettlementFee(int earlySettlementFee) { this.earlySettlementFee = earlySettlementFee; }
    @SerializedName("maxOfGoods")
    @Expose
    private int maxOfGoods;

    public int getMaxOfGoods() { return this.maxOfGoods; }

    public void setMaxOfGoods(int maxOfGoods) { this.maxOfGoods = maxOfGoods; }
    @SerializedName("configs")
    @Expose
    private ArrayList<Config> configs;

    public ArrayList<Config> getConfigs() { return this.configs; }

    public void setConfigs(ArrayList<Config> configs) { this.configs = configs; }
}