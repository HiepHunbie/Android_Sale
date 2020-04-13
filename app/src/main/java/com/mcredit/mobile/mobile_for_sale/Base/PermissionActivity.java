package com.mcredit.mobile.mobile_for_sale.Base;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.mcredit.mobile.mobile_for_sale.Contants.AppContants;
import com.mcredit.mobile.mobile_for_sale.Utils.Pref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class PermissionActivity extends FragmentActivity {

    private static final String TAG = "Permission";
    public SharedPreferences sharedPreferences;
    public Pref p;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        p = new Pref(this);

        String notification = getIntent().getStringExtra(AppContants.NOTIFICATION);
        if(notification != null){
            if(notification.length()>0){
                if(notification.equals("0")){
                }else if(notification.equals("1")){
                }else{
                }
            }else {
            }
        }else {
        }

    }

    //Multi permission
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    protected List<String> listPermission;

    private OnCheckPermissionListener onCheckPermissionListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public void checkPermission(List<String> listPermission, OnCheckPermissionListener onCheckPermissionListener) {
        this.onCheckPermissionListener = onCheckPermissionListener;
        if (checkAndRequestPermissions(listPermission)) {
            this.onCheckPermissionListener.checkResult(listPermission,true);
        }
    }

    private boolean checkAndRequestPermissions(List<String> listPermission) {
        this.listPermission = new ArrayList<>();
        this.listPermission.addAll(listPermission);
        List<String> listPermissionsNeeded = new ArrayList<>();

        for (String permission : listPermission) {
            int permissionRequest = ContextCompat.checkSelfPermission(this, permission);
            if (permissionRequest != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    public interface OnCheckPermissionListener{
        void checkResult(List<String> listPermission, boolean isSuccess);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                if (listPermission != null) {
                    for (String permission : listPermission) {
                        perms.put(permission, PackageManager.PERMISSION_GRANTED);
                    }

                    if (grantResults.length > 0) {
                        boolean per_not_granted = false;
                        for (int i = 0; i < permissions.length; i++)
                            perms.put(permissions[i], grantResults[i]);
                        // Check for both permissions
                        for (String per : listPermission) {
                            if (perms.get(per) != PackageManager.PERMISSION_GRANTED) {
                                Log.e(TAG, per + " are not granted ask again ");
                                per_not_granted = true;
                            }
                        }
                        if (per_not_granted) {
                            Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                    .show();
                            onCheckPermissionListener.checkResult(listPermission,false);
                        } else {
                            //Todo - do something
                            onCheckPermissionListener.checkResult(listPermission,true);
                        }

                    }
                }
            }
        }

    }


}
