package com.mcredit.mobile.mobile_for_sale.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mcredit.mobile.mobile_for_sale.Api.Service.SOService;
import com.mcredit.mobile.mobile_for_sale.Api.Utils.ApiUtils;
import com.mcredit.mobile.mobile_for_sale.Base.PermissionActivity;
import com.mcredit.mobile.mobile_for_sale.Contants.AppContants;
import com.mcredit.mobile.mobile_for_sale.Dialog.DialogCall;
import com.mcredit.mobile.mobile_for_sale.Models.Login.LoginSuccess;
import com.mcredit.mobile.mobile_for_sale.R;
import com.mcredit.mobile.mobile_for_sale.Utils.Pref;
import com.google.gson.JsonObject;
import com.onesignal.OneSignal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hiepnt on 14/03/2018.
 */

public class LoginActivity extends PermissionActivity {

    private EditText login_txt_username, login_txt_password;
    private Button btnLogin;
    private TextView txtError,txtVersion;
    private String IMEI_Number_Holder = "";
    private TelephonyManager telephonyManager;
    private SOService mService;
    private ProgressBar progressLoading_not_invite;
    private JsonObject jsonObjectEmei = new JsonObject();
    private static final String PERMISSIONS_REQUIRED[] = new String[]{
            Manifest.permission.CAMERA,Manifest.permission.READ_PHONE_STATE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    private static final int REQUEST_PERMISSIONS = 100;
    private String playerId_OneSignal = "";
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        p = new Pref(LoginActivity.this);
        checkPermissions();
        mService = ApiUtils.getSOService();
        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                Log.d("debug", "User:" + userId);
                playerId_OneSignal = ""+userId;
                p.putString(AppContants.playerId_OneSignal,playerId_OneSignal.toString());
                if (registrationId != null)
                    Log.d("debug", "registrationId:" + registrationId);

            }
        });
        login_txt_username = (EditText) findViewById(R.id.login_txt_username);
        login_txt_password = (EditText) findViewById(R.id.login_txt_password);
        btnLogin = (Button) findViewById(R.id.login_btn_login);
        txtError = (TextView) findViewById(R.id.txtErrorLogin);
        txtVersion = (TextView)findViewById(R.id.txtVersion);
        progressLoading_not_invite = (ProgressBar) findViewById(R.id.progressLoading_not_invite);
        progressLoading_not_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if(AppContants.BaseUrl.equals("http://172.17.200.127:8080/mcservice/")){
            txtVersion.setText(getString(R.string.dev_server));
        }else if(AppContants.BaseUrl.equals("http://1.55.17.6:6080/mcservice/")){
            txtVersion.setText(getString(R.string.uat_server));
        }else{
            txtVersion.setText(getString(R.string.live_server));
        }

//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                txtError = (TextView) findViewById(R.id.txtErrorLogin);
//                txtError.setText("");
//                login(login_txt_username.getText().toString(),login_txt_password.getText().toString(),jsonObjectEmei);
//            }
//        });

        String username = p.getString(AppContants.USERNAME_LOGIN,"");
        String password = p.getString(AppContants.PASSWORD_LOGIN,"");
        if(username != null && username.length()>1){
            login(username,password,jsonObjectEmei);
        }
//        checkPermissions();
    }

    private void addJson(){
        telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        TelephonyManager telephonyManager;
        telephonyManager = (TelephonyManager) getSystemService(Context.
                TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        String deviceId = telephonyManager.getDeviceId();
        String subscriberId = telephonyManager.getSubscriberId();
        IMEI_Number_Holder = telephonyManager.getDeviceId();
        p.putString(AppContants.EMEI_LOGIN,IMEI_Number_Holder.toString());
        jsonObjectEmei.addProperty("imei",IMEI_Number_Holder.toString());
        if(p.getString(AppContants.playerId_OneSignal,"") != null){
            if(playerId_OneSignal.length()==0){
                playerId_OneSignal = p.getString(AppContants.playerId_OneSignal,"");
            }
        }
        jsonObjectEmei.addProperty("playId",playerId_OneSignal.toString());
    }

    public void onClickLogin(View view){
        txtError = (TextView) findViewById(R.id.txtErrorLogin);
        txtError.setText("");
        login(login_txt_username.getText().toString(),login_txt_password.getText().toString(),jsonObjectEmei);
//        Intent i = new Intent(LoginActivity.this, MainActivity.class);
//        startActivity(i);
//        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermissions();
    }

    private void login(final String userName, final String password, final JsonObject imei){
        progressLoading_not_invite.setVisibility(View.VISIBLE);
        addJson();
        String base = userName + ":"+password;
        final String authHeader = "Basic "+ Base64.encodeToString(base.getBytes(),Base64.NO_WRAP);
        AppContants.AUTHEN = authHeader;
        mService.loginToServer(imei,authHeader,"application/json").enqueue(new Callback<LoginSuccess>() {
            @Override
            public void onResponse(Call<LoginSuccess> call, Response<LoginSuccess> response) {

                if(response.isSuccessful()) {
                    if(response.body() != null) {
                        if (response.body().getResult() != null) {
                            if (response.body().getResult().getHttpCode() == 200) {
                                p.putString(AppContants.USERNAME_LOGIN, userName);
                                p.putString(AppContants.EMEI_LOGIN, IMEI_Number_Holder.toString());
                                p.putString(AppContants.PASSWORD_LOGIN, password);
                                p.putString(AppContants.AUTHEN_LOGIN, authHeader);
                                p.putString(AppContants.TOKEN_LOGIN, response.body().getResult().getInfo().getToken());
                                p.putString(AppContants.USER_NAME, response.body().getResult().getInfo().getFirstName() + " " + response.body().getResult().getInfo().getLastName());

                                progressLoading_not_invite.setVisibility(View.GONE);
                                String notification = getIntent().getStringExtra(AppContants.NOTIFICATION);
                                if (notification != null) {
                                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(i);
                                    finish();
                                    if (notification.length() > 0) {
                                        if (notification.equals("0")) {
                                        } else if (notification.equals("1")) {
                                        } else {
                                        }
                                    } else {
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        intent.putExtra(AppContants.USER_NAME, response.body().getResult().getInfo().getFirstName() + " " + response.body().getResult().getInfo().getLastName());
                                        startActivity(intent);
                                        finish();
                                    }
                                } else {
                                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                    i.putExtra(AppContants.USER_NAME, response.body().getResult().getInfo().getFirstName() + " " + response.body().getResult().getInfo().getLastName());
                                    startActivity(i);
                                    finish();
                                }

                            } else if (response.body().getResult().getHttpCode() == 401){
                                progressLoading_not_invite.setVisibility(View.GONE);
                                if (response.body().getResult() != null) {
                                    txtError.setText(getString(R.string.error_login_wrong_user_pass));
                                    txtError.setVisibility(View.VISIBLE);
                                }
                            }else if (response.body().getResult().getHttpCode() == 406){
                                progressLoading_not_invite.setVisibility(View.GONE);
                                if (response.body().getResult() != null) {
                                    if(response.body().getResult().getHttpMessage().equals(getString(R.string.error_406_1))){
                                        txtError.setText(getString(R.string.error_406_1_status));
                                        txtError.setVisibility(View.VISIBLE);
                                    }else  if(response.body().getResult().getHttpMessage().equals(getString(R.string.error_406_2))){
                                        txtError.setText(getString(R.string.error_406_2_status));
                                        txtError.setVisibility(View.VISIBLE);
                                    }
                                }
                            }else {
                                progressLoading_not_invite.setVisibility(View.GONE);
                                if (response.body().getResult() != null) {
                                    txtError.setText(getString(R.string.error_login) + " (" + response.body().getResult().getHttpCode() + ")");
                                    txtError.setVisibility(View.VISIBLE);
                                }

                            }
                        } else {
                            int statusCode = response.code();
                            progressLoading_not_invite.setVisibility(View.GONE);
                        }
                    }
                }}

            @Override
            public void onFailure(Call<LoginSuccess> call, Throwable t) {
                txtError.setText(getString(R.string.error_login) );
                txtError.setVisibility(View.VISIBLE);
                progressLoading_not_invite.setVisibility(View.GONE);
                DialogCall.showWaringDialog(LoginActivity.this, getString(R.string.cannot_connect_server), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

            }
        });
    }

    private boolean checkPermission(String permissions[]) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
    private void checkPermissions() {
        boolean permissionsGranted = checkPermission(PERMISSIONS_REQUIRED);
        if (permissionsGranted) {
//            Toast.makeText(this, "You've granted all required permissions!", Toast.LENGTH_SHORT).show();
        } else {
            boolean showRationale = true;
            for (String permission: PERMISSIONS_REQUIRED) {
                showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
                if (!showRationale) {
                    break;
                }
            }
            ActivityCompat.requestPermissions(LoginActivity.this, PERMISSIONS_REQUIRED, REQUEST_PERMISSIONS);
            String dialogMsg = showRationale ? "We need some permissions to run this APP!" : "You've declined the required permissions, please grant them from your phone settings";

//            new AlertDialog.Builder(this)
//                    .setTitle("Permissions Required")
//                    .setMessage(dialogMsg)
//                    .setCancelable(false)
//                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            ActivityCompat.requestPermissions(LoginActivity.this, PERMISSIONS_REQUIRED, REQUEST_PERMISSIONS);
//                        }
//                    }).create().show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        Log.d("MainActivity", "requestCode: " + requestCode);
//        Log.d("MainActivity", "Permissions:" + Arrays.toString(permissions));
//        Log.d("MainActivity", "grantResults: " + Arrays.toString(grantResults));

        if (requestCode == REQUEST_PERMISSIONS) {
            boolean hasGrantedPermissions = true;
            for (int i=0; i<grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    hasGrantedPermissions = false;
                    break;
                }
            }

            if (!hasGrantedPermissions) {
//                finish();
            }

        } else {
//            finish();
        }
    }

}
