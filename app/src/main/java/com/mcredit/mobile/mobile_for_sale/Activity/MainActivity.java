package com.mcredit.mobile.mobile_for_sale.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mcredit.mobile.mobile_for_sale.Api.Service.SOService;
import com.mcredit.mobile.mobile_for_sale.Api.Utils.ApiUtils;
import com.mcredit.mobile.mobile_for_sale.Base.PermissionActivity;
import com.mcredit.mobile.mobile_for_sale.Contants.AppContants;
import com.mcredit.mobile.mobile_for_sale.Database.DatabaseHelper;
import com.mcredit.mobile.mobile_for_sale.Dialog.DialogCall;
import com.mcredit.mobile.mobile_for_sale.Models.DraftCase.DraftResult;
import com.mcredit.mobile.mobile_for_sale.Models.GetCat.GetCatInfo;
import com.mcredit.mobile.mobile_for_sale.Models.Login.LoginSuccess;
import com.mcredit.mobile.mobile_for_sale.Models.MainModel.MainObject;
import com.mcredit.mobile.mobile_for_sale.Models.ProgressingCase.ArrCase;
import com.mcredit.mobile.mobile_for_sale.Models.ProgressingCase.ProgressingCaseArr;
import com.mcredit.mobile.mobile_for_sale.Models.ReturnCase.ReturnCaseResult;
import com.mcredit.mobile.mobile_for_sale.R;
import com.mcredit.mobile.mobile_for_sale.Utils.Pref;
import com.google.gson.JsonObject;

import me.grantland.widget.AutofitTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends PermissionActivity {

    private TextView txtUserName;
    private Button btn_themmoi;
    private TextView btn_dangxuat;
    private SOService mService;
    private ProgressBar progressLoading_not_invite;
    private String authHeader = "";
    private LinearLayout btn_drap,dashboard_paticipated,dashboard_inbox,dashboard_notification;
    private DatabaseHelper databaseHelper;
    private long draftTotal = 0;
    private long returnTotal = 0;
    private long progressingTotal = 0;
    private me.grantland.widget.AutofitTextView dashboard_drap_number,dashboard_inbox_number,dashboard_paticipated_number;
    private String token = "";
    private SwipeRefreshLayout mySwipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        p = new Pref(MainActivity.this);
        String userName = "";
            userName = p.getString(AppContants.USER_NAME,"");
        databaseHelper = new DatabaseHelper(this);
        mService = ApiUtils.getSOService();
        txtUserName = (TextView)findViewById(R.id.txtName);
        btn_themmoi = (Button)findViewById(R.id.btn_themmoi);
        btn_dangxuat = (TextView)findViewById(R.id.btn_dangxuat);
        btn_drap = (LinearLayout)findViewById(R.id.btn_drap);
        dashboard_paticipated = (LinearLayout)findViewById(R.id.dashboard_paticipated);
        dashboard_notification = (LinearLayout)findViewById(R.id.dashboard_notification);

        progressLoading_not_invite = (ProgressBar) findViewById(R.id.progressLoading_not_invite);
        mySwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.mySwipeRefreshLayout);
        progressLoading_not_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dashboard_inbox = (LinearLayout)findViewById(R.id.dashboard_inbox);
        dashboard_drap_number = (AutofitTextView)findViewById(R.id.dashboard_drap_number);
        dashboard_inbox_number = (AutofitTextView)findViewById(R.id.dashboard_inbox_number);
        dashboard_paticipated_number = (AutofitTextView)findViewById(R.id.dashboard_paticipated_number);
        authHeader = p.getString(AppContants.AUTHEN_LOGIN,"");
        getTotalInfo();
        txtUserName.setText(userName);
        btn_themmoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,CreateNewCaseActivity.class);
                i.putExtra(AppContants.IS_NEW_CASE,0);
                startActivity(i);
//                finish();
            }
        });
        dashboard_inbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,ListReturnCaseActivity.class);
                startActivity(i);
            }
        });
        btn_drap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,ListDraftCaseActivity.class);
                startActivity(i);
            }
        });
        dashboard_paticipated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,ListProgressingCaseActivity.class);
                startActivity(i);
            }
        });
        dashboard_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,NotificationActivity.class);
                startActivity(i);
            }
        });
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        getTotalInfo();
                    }
                }
        );
        btn_dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogCall.showConfirmDialog(MainActivity.this,getString(R.string.confirm_logout),new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int id = v.getId();
                        if (id == R.id.btn_ok) {
                            mService.logoutServer(authHeader,"application/json").enqueue(new Callback<LoginSuccess>() {
                                @Override
                                public void onResponse(Call<LoginSuccess> call, Response<LoginSuccess> response) {
                                    if(response.body() != null) {
                                        if (response.body().getResult() != null) {
                                            if (response.body().getResult().getHttpCode() == 200) {
                                                p.putString(AppContants.USERNAME_LOGIN, "");
                                                p.putString(AppContants.EMEI_LOGIN, "");
                                                p.putString(AppContants.PASSWORD_LOGIN, "");
                                                p.putString(AppContants.AUTHEN_LOGIN, "");
                                                p.putString(AppContants.TOKEN_LOGIN, "");
                                                p.putString(AppContants.USER_NAME, "");
                                                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                                                startActivity(i);
                                                finish();
                                                progressLoading_not_invite.setVisibility(View.GONE);
                                            } else {
                                                progressLoading_not_invite.setVisibility(View.GONE);
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<LoginSuccess> call, Throwable t) {
                                    progressLoading_not_invite.setVisibility(View.GONE);
                                    DialogCall.showWaringDialog(MainActivity.this, getString(R.string.cannot_connect_server), new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                        }
                                    });
                                }
                            });
                        }
                        else if (id == R.id.btn_cancel) {
                        }
                    }
                });


            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String userName = "";
        userName = p.getString(AppContants.USER_NAME,"");
        txtUserName.setText(userName);
        getTotalInfo();
    }

    private void getTotalInfo(){
        authHeader = p.getString(AppContants.AUTHEN_LOGIN,"");
        final long draftTotalInDevice = databaseHelper.getDraftCount(p.getString(AppContants.USERNAME_LOGIN,""));
        mService.getTotalInfo(authHeader).enqueue(new Callback<MainObject>() {
            @Override
            public void onResponse(Call<MainObject> call, Response<MainObject> response) {
                if(response.body() != null){
                if(response.body().getResult()!=null){
                    if(response.body().getResult().getHttpCode() == 200){
                        progressLoading_not_invite.setVisibility(View.GONE);
                        mySwipeRefreshLayout.setRefreshing(false);
                        if(response.body().getResult().getData()!=null){
                            draftTotal = (int) (response.body().getResult().getData().getNumcasedrafted()+draftTotalInDevice);
                            dashboard_drap_number.setText(draftTotalInDevice+"");
                            progressingTotal =  (response.body().getResult().getData().getNumCaseProcessed());
                            dashboard_paticipated_number.setText(progressingTotal+"");
                            returnTotal = response.body().getResult().getData().getNumcaseReturned();
                            dashboard_inbox_number.setText(returnTotal+"");
                        }

                    }else {
                        progressLoading_not_invite.setVisibility(View.GONE);
                        mySwipeRefreshLayout.setRefreshing(false);
                    }
                }
            }}
            @Override
            public void onFailure(Call<MainObject> call, Throwable t) {
                progressLoading_not_invite.setVisibility(View.GONE);
                mySwipeRefreshLayout.setRefreshing(false);
                DialogCall.showWaringDialog(MainActivity.this, getString(R.string.cannot_connect_server), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });
    }

}
