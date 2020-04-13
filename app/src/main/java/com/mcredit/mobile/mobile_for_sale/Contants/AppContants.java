package com.mcredit.mobile.mobile_for_sale.Contants;

/**
 * Created by hiepnt on 05/03/2018.
 */

public class AppContants {

//    public static String BaseUrl = "http://172.17.200.127:8880/mcservice/"; //test server
//  public static String BaseUrl = "http://172.17.200.127:8080/mcservice/";
   // public static String BaseUrl = "http://172.17.200.239:80/mcservice/"; //uat server
//    public static String BaseUrl = "http://1.55.17.6:6080/mcservice/"; //uat public server
    public static String BaseUrl = "https://mfs.mcredit.com.vn:6443/mcservice/"; //public server
//    public static String BaseUrl = "https://dev-bpm.mcredit.com.vn/mcservice/"; //https server
//    public static String BaseUrl = "https://172.17.200.153/mcservice/"; //test trust server
    public static String BaseUrlUploadReturn = BaseUrl + "rest/return-case/upload";

    public static String BaseUrlUpload = BaseUrl + "rest/case/upload";

    public static String BaseUrlDownloadPdf = BaseUrl + "rest/return-case/pdf/downloadbyid/";
    public static String KEY_SHARE = "KEY_SHARE_APP";
    public static String USER_NAME = "USER_NAME";
    public static String AUTHEN = "";
    public static String USERNAME_LOGIN = "USERNAME_LOGIN";
    public static String PASSWORD_LOGIN = "PASSWORD_LOGIN";
    public static String AUTHEN_LOGIN = "AUTHEN_LOGIN";
    public static String TOKEN_LOGIN = "TOKEN_LOGIN";
    public static String EMEI_LOGIN = "EMEI_LOGIN";
    public static String playerId_OneSignal = "playerId_OneSignal";
    public static String CREATE_NEW_CASE="CREATE_NEW_CASE";
    public static String DATA_DRAFT_CASE="DATA_DRAFT_CASE";
    public static String UPDATE_DRAFT_CASE="UPDATE_DRAFT_CASE";
    public static String IS_NEW_CASE="IS_NEW_CASE";
    public static String UPDATE_CASE_FAIL = "UPDATE_CASE_FAIL";
     public static String PROGRESSING_CASE_STATUS = "PROGRESSING_CASE_STATUS";
    public static int LIMIT_IMAGE_SELECT = 10;
    public static int LIMIT_IMAGE_CHANGE = 1;
    public static int COUNTRESULT = 10;
    public static int visibleThreshold = 1;
    public static int quality_image = 50;
    public static String NOTIFICATION="NOTIFICATION";
    public static String isCreateNewCase = "isCreateNewCase";
    public static String sdcard = "/sdcard/";
     public static final String[] okFileExtensions =  new String[] {"BMP","GIF","IMG","JPE","JPEG","JPG","PNG","bmp","gif","img","jpe","jpeg","jpg","png"};
}
