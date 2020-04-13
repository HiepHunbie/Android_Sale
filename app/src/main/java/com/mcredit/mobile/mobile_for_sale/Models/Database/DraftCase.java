package com.mcredit.mobile.mobile_for_sale.Models.Database;

import java.io.Serializable;

/**
 * Created by MSI on 11/7/2016.
 */

public class DraftCase implements Serializable {
    public String fullname,card,date_card,termloan,document_type_value,
            document_type_code,kiosk_value,kiosk_code,kiosk_address,cat_type,company_name,money,tax_code;
    public String insurance,signContractAddress,mobileAppCode,isFromDevice,username;
    public DraftCase(String fullname,String card,String date_card,String termloan,String document_type_value,
                     String document_type_code,String kiosk_value,String kiosk_code,String kiosk_address,String cat_type,
                     String company_name,String insurance,String signContractAddress,String money,String tax_code,String mobileAppCode,String isFromDevice,String username){
        this.fullname = fullname;
        this.card = card;
        this.date_card = date_card;
        this.termloan = termloan;
        this.document_type_value = document_type_value;
        this.document_type_code = document_type_code;
        this.kiosk_value = kiosk_value;
        this.kiosk_code = kiosk_code;
        this.kiosk_address = kiosk_address;
        this.cat_type = cat_type;
        this.company_name = company_name;
        this.insurance = insurance;
        this.signContractAddress = signContractAddress;
        this.money = money;
        this.tax_code = tax_code;
        this.mobileAppCode = mobileAppCode;
        this.isFromDevice = isFromDevice;
        this.username = username;
    }
    public DraftCase() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIsFromDevice() {
        return isFromDevice;
    }

    public void setIsFromDevice(String isFromDevice) {
        this.isFromDevice = isFromDevice;
    }

    public String getMobileAppCode() {
        return mobileAppCode;
    }

    public void setMobileAppCode(String mobileAppCode) {
        this.mobileAppCode = mobileAppCode;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getDate_card() {
        return date_card;
    }

    public void setDate_card(String date_card) {
        this.date_card = date_card;
    }

    public String getTermloan() {
        return termloan;
    }

    public void setTermloan(String termloan) {
        this.termloan = termloan;
    }

    public String getDocument_type_value() {
        return document_type_value;
    }

    public void setDocument_type_value(String document_type_value) {
        this.document_type_value = document_type_value;
    }

    public String getDocument_type_code() {
        return document_type_code;
    }

    public void setDocument_type_code(String document_type_code) {
        this.document_type_code = document_type_code;
    }

    public String getKiosk_value() {
        return kiosk_value;
    }

    public void setKiosk_value(String kiosk_value) {
        this.kiosk_value = kiosk_value;
    }

    public String getKiosk_code() {
        return kiosk_code;
    }

    public void setKiosk_code(String kiosk_code) {
        this.kiosk_code = kiosk_code;
    }

    public String getKiosk_address() {
        return kiosk_address;
    }

    public void setKiosk_address(String kiosk_address) {
        this.kiosk_address = kiosk_address;
    }

    public String getCat_type() {
        return cat_type;
    }

    public void setCat_type(String cat_type) {
        this.cat_type = cat_type;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTax_code() {
        return tax_code;
    }

    public void setTax_code(String tax_code) {
        this.tax_code = tax_code;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getSignContractAddress() {
        return signContractAddress;
    }

    public void setSignContractAddress(String signContractAddress) {
        this.signContractAddress = signContractAddress;
    }
}
