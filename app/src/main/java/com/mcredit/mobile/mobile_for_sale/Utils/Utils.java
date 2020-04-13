package com.mcredit.mobile.mobile_for_sale.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.ClipboardManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Utils {

    public static void toast(Context context, CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
    public static String readFile(File f) {
        StringBuilder b = new StringBuilder();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(f));
            String line;

            while ((line = br.readLine()) != null) {
                b.append(line);
                b.append('\n');
            }
            br.close();
            br = null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return b.toString();
    }
    public static void writeFile(File f, String data) {
        try {
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(data.getBytes("UTF-8"));
            fos.flush();
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void silentCloseInputStream(InputStream is) {
        try {
            if (is != null) {
                is.close();
            }
        } catch (IOException e) {
        }
    }
    public static boolean hasNetworkConnection(Context c) {
        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) c
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null
                    && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            } else {
                netInfo = cm.getNetworkInfo(1);
                if (netInfo != null
                        && netInfo.getState() == NetworkInfo.State.CONNECTED)
                    status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return status;
    }
    public static String readInputStream(InputStream is) {
        byte[] buff = new byte[512];

        StringBuilder b = new StringBuilder();
        try {
            int read = is.read(buff);
            while (read != -1) {
                String chunk = new String(buff, 0, read);
                b.append(chunk);
                read = is.read(buff);
            }

            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return b.toString().trim();
    }

    public static String arrayToString(String[] ids) {
        String string = "";
        for (String id : ids) {
            string += (id + ",");
        }

        return string.substring(0, string.length() - 1);
    }

    public static String listToString(List<String> ids) {
        String string = "";
        for (String id : ids) {
            string += (id + ",");
        }

        return string.substring(0, string.length() - 1);
    }

    public static void copyToClipBoard(Context context, CharSequence s) {
        ClipboardManager clip = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        clip.getText(); // 粘贴
        clip.setText(s); // 复制

//        toast(context, context.getString(R.string.success_copy));
    }

    public static void disableKeyBoard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void imageLoadFromSdcard(String path, ImageView imageView){
        File imgFile = new File(path);

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());


            imageView.setImageBitmap(myBitmap);

        }
    }

    public final boolean isInternetOn(Activity activity) {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager)activity.getSystemService(activity.getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED ) {

            // if connected with internet

            Toast.makeText(activity, " Connected ", Toast.LENGTH_LONG).show();
            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED  ) {

            Toast.makeText(activity, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }
}