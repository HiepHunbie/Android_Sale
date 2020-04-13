package com.mcredit.mobile.mobile_for_sale.Utils;


/**
 * Created by WilliZ on 16/03/28.
 */
public class PhotoUtils {
    public static final String UPLOAD_PREFIX = "file://";

    public static String genUploadPhotoParam(String path) {
        return UPLOAD_PREFIX + path;
    }

    public static String getUploadPhotoPath(String param) {
        return param.substring(UPLOAD_PREFIX.length(), param.length());
    }

    public static boolean isPhotoParam(String param) {
        return param.startsWith(UPLOAD_PREFIX);
    }

//    public static String getPhotoUrl(String hashedPhoto) {
//        try {
//            String p1 = hashedPhoto.substring(0, 1);
//            String p2 = hashedPhoto.substring(1, 2);
//            String p3 = hashedPhoto.substring(2, 3);
//            String hash = hashedPhoto.substring(3, hashedPhoto.length());
//
//            StringBuilder sb = new StringBuilder();
//            sb.append(ProtocolConstant.IMAGE_URL);
//            sb.append("media/images/");
//            sb.append(p1);
//            sb.append("/");
//            sb.append(p2);
//            sb.append("/");
//            sb.append(p3);
//            sb.append("/");
//            sb.append(hash);
//
//            String url = sb.toString();
//            return url;
//        } catch (Exception e) {
//            return null;
//        }
//    }
}
