package com.mcredit.mobile.mobile_for_sale.Models.PopUpSelect;

/**
 * Created by hiephunbie on 3/21/18.
 */

public class Item {
    public enum ItemType {
        ONE_ITEM, TWO_ITEM;
    }

    private String title;
    private ItemType type;
    private int mandory;
    private String value;
    private String termLoanCode;
    private int status;

    public Item(String n, ItemType type,int mandory,String value,String termLoanCode,int status) {
        this.title = n;
        this.type = type;
        this.mandory = mandory;
        this.value = value;
        this.termLoanCode = termLoanCode;
        this.status = status;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMandory() {
        return mandory;
    }

    public void setMandory(int mandory) {
        this.mandory = mandory;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTermLoanCode() {
        return termLoanCode;
    }

    public void setTermLoanCode(String termLoanCode) {
        this.termLoanCode = termLoanCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
