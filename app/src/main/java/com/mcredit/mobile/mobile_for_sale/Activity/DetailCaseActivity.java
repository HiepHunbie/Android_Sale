package com.mcredit.mobile.mobile_for_sale.Activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.mcredit.mobile.mobile_for_sale.Adapter.CaseNoteAdapter;
import com.mcredit.mobile.mobile_for_sale.Api.Service.SOService;
import com.mcredit.mobile.mobile_for_sale.Api.Utils.ApiUtils;
import com.mcredit.mobile.mobile_for_sale.Base.PermissionActivity;
import com.mcredit.mobile.mobile_for_sale.Contants.AppContants;
import com.mcredit.mobile.mobile_for_sale.Dialog.DialogCall;
import com.mcredit.mobile.mobile_for_sale.Models.CaseNote.AppNotesEntry;
import com.mcredit.mobile.mobile_for_sale.Models.CaseNote.CaseNoteResult;
import com.mcredit.mobile.mobile_for_sale.Models.ProgressingCase.ArrCase;
import com.mcredit.mobile.mobile_for_sale.Models.ReturnCase.CaseReturnArr;
import com.mcredit.mobile.mobile_for_sale.Models.SendCaseNote.SendCaseNoteResult;
import com.mcredit.mobile.mobile_for_sale.R;
import com.mcredit.mobile.mobile_for_sale.Utils.DateUtils;
import com.mcredit.mobile.mobile_for_sale.Utils.Edittext;
import com.mcredit.mobile.mobile_for_sale.Utils.Pref;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hiephunbie on 5/16/18.
 */

public class DetailCaseActivity extends PermissionActivity {

    private TextView txtScheme,txt_citi_id,txtloan_ter,txtInsurance,txt_places_check,txtKiosk,txtMoney,txtMessage,txtFullName,txtMobileId,txtMobileAppCode;
    private ArrCase caseReturnArr;
    private  String message = "";
    private ImageView btnBack;
    private String mobileAppCode = "";
    private TextView txt_case_note;
    private CaseNoteAdapter caseNoteAdapter ;
    private SOService mService;
    private String authHeader = "";
    private ArrayList<AppNotesEntry> appNotesEntryArrayList = new ArrayList<AppNotesEntry>();
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.case_detail_activity);
        p = new Pref(DetailCaseActivity.this);
        mService = ApiUtils.getSOService();
        authHeader = p.getString(AppContants.AUTHEN_LOGIN,"");
        caseReturnArr = (ArrCase) getIntent().getSerializableExtra(AppContants.PROGRESSING_CASE_STATUS);

        txtScheme = (TextView)findViewById(R.id.txtScheme);
        txt_citi_id = (TextView)findViewById(R.id.txt_citi_id);
        txtloan_ter = (TextView)findViewById(R.id.txtloan_ter);
        txtInsurance = (TextView)findViewById(R.id.txtInsurance);
        txt_places_check = (TextView)findViewById(R.id.txt_places_check);
        txtKiosk = (TextView)findViewById(R.id.txtKiosk);
        txtMoney = (TextView)findViewById(R.id.txtMoney);
        txtMessage = (TextView)findViewById(R.id.txtMessage);
        txtFullName = (TextView)findViewById(R.id.txtFullName);
        txtMobileId = (TextView)findViewById(R.id.txtMobileId);
        txtMobileAppCode = (TextView)findViewById(R.id.txtMobileAppCode);
        btnBack = (ImageView)findViewById(R.id.btnBack);
        txt_case_note = (TextView)findViewById(R.id.txt_case_note);

        if(caseReturnArr != null){
            mobileAppCode = caseReturnArr.getMobileAppCode();
            txtFullName.setText(caseReturnArr.getMobileCustomerName().toString());
            txtScheme.setText(caseReturnArr.getMobileSchemaProductName().toString());
            txt_citi_id.setText(caseReturnArr.getMobileCitizenId().toString());
            txtloan_ter.setText(caseReturnArr.getMobileLoanTenor() + " "+getString(R.string.month));
            if(caseReturnArr.getMobileHasInsurrance()==1){
                txtInsurance.setText(getString(R.string.had_insurrance));
            }else {
                txtInsurance.setText(getString(R.string.had_not_insurrance));
            }
            if(caseReturnArr.getMobileTemResidence().equals("1")){
                txt_places_check.setText(getString(R.string.had_same_places));
            }else {
                txt_places_check.setText(getString(R.string.had_not_same_places));
            }
            txtKiosk.setText(caseReturnArr.getMobileSignContracAddress().toString());
            txtMoney.setText(Edittext.convertTextToCommas(caseReturnArr.getMobileLoanAmount()+""));
            txtMessage.setText(caseReturnArr.getCurrentTask()+"");
            txtMobileId.setText(getString(R.string.case_id) + caseReturnArr.getAppNumber()+"");
            txtMobileAppCode.setText(caseReturnArr.getMobileAppCode()+"");

            if(caseReturnArr.getAppId() == null){
                txt_case_note.setVisibility(View.GONE);
            }else {
                if(caseReturnArr.getAppId().toString().length()==0){
                    txt_case_note.setVisibility(View.GONE);
                }else {
                    txt_case_note.setVisibility(View.VISIBLE);
                }
            }
        }
        txt_case_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogCaseNote(DetailCaseActivity.this, appNotesEntryArrayList,caseReturnArr.getAppId().toString(),new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int id = v.getId();
                    }
                });
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnBack = (ImageView)findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void showDialogCaseNote(final Activity mContext, ArrayList<AppNotesEntry> item, String app_id, final View.OnClickListener listener){
        final AlertDialog builder = new AlertDialog.Builder(mContext).create();
        builder.show();
        builder.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        builder.setCanceledOnTouchOutside(false);
        builder.setCancelable(false);

        Window window = builder.getWindow();
        window.setContentView(R.layout.dialog_case_note);

        final RecyclerView item_list = (RecyclerView) window.findViewById(R.id.item_list);
        ImageView img_close = (ImageView)window.findViewById(R.id.img_close);
        final EditText edt_message = (EditText)window.findViewById(R.id.edt_case_note);
        final ProgressBar progressLoading_not_invite = (ProgressBar)window.findViewById(R.id.progressLoading_not_invite);
        final TextView txtSend = (TextView)window.findViewById(R.id.txtSend);
//        edt_message.setFilters(new InputFilter[]{ new MinMaxFilter("1", "4000")});
        Button btn_send = (Button)window.findViewById(R.id.btn_send);
        caseNoteAdapter = new CaseNoteAdapter(this,appNotesEntryArrayList,item_list, new CaseNoteAdapter.PostItemListener() {

            @Override
            public void onPostClick(long id) {
            }

            @Override
            public void onLongClick(long id) {

            }
        });
        item_list.setLayoutManager(new LinearLayoutManager(mContext));
        item_list.setItemAnimator(new DefaultItemAnimator());
        item_list.setAdapter(caseNoteAdapter);
        getCaseNote(app_id,progressLoading_not_invite,item_list);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder.dismiss();
//                String message = Base64.encodeToString(edt_message.getText().toString().getBytes(),Base64.NO_WRAP);
                if(edt_message.getText().toString().trim().length()>0){
                    sendCaseNote(caseReturnArr.getMobileAppCode(),edt_message.getText().toString().trim(),progressLoading_not_invite,txtSend,edt_message,item_list);
                }
            }
        });
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP && !event.isCanceled())
                {
                    builder.dismiss();
                    return true;
                }
                return false;
            }
        });
    }
    private void getCaseNote(String app_id, final ProgressBar progressLoading_not_invite,final RecyclerView item_list){
        progressLoading_not_invite.setVisibility(View.VISIBLE);
        mService.getCaseNote(app_id).enqueue(new Callback<CaseNoteResult>() {
            @Override
            public void onResponse(Call<CaseNoteResult> call, Response<CaseNoteResult> response) {
                if(response.body() != null) {
                    if (response.body().getAppNotesEntries() != null) {
                        if (response.body().getAppNotesEntries().getAppNotesEntry()!= null) {
                            progressLoading_not_invite.setVisibility(View.GONE);
                            appNotesEntryArrayList.clear();
                            for(int i = 0;i<response.body().getAppNotesEntries().getAppNotesEntry().size();i++){
                                appNotesEntryArrayList.add(response.body().getAppNotesEntries().getAppNotesEntry().get(i));
                            }
                            caseNoteAdapter.notifyDataSetChanged();
                            item_list.scrollToPosition(appNotesEntryArrayList.size()-1);
                        } else {
                            progressLoading_not_invite.setVisibility(View.GONE);
//                            String message = "";
//                            if (response.body().get().getError() != null) {
//                                message = response.body().getResult().getRes().getError().getMessage();
//                            }
//                            DialogCall.showWaringDialog(UpdateCaseActivity.this, message, new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//
//                                }
//                            });
                        }
                    }
                }
//                }else {
//                    int statusCode  = response.code();
//                    progressLoading_not_invite.setVisibility(View.GONE);
//                }
            }

            @Override
            public void onFailure(Call<CaseNoteResult> call, Throwable t) {
                progressLoading_not_invite.setVisibility(View.GONE);
                DialogCall.showWaringDialog(DetailCaseActivity.this, getString(R.string.cannot_connect_server), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });
    }

    private void sendCaseNote(String mobileAppCode, final String caseNote, final ProgressBar progressLoading_not_invite,final TextView txtSend,final EditText editText,final RecyclerView item_list){
//        progressLoading_not_invite.setVisibility(View.VISIBLE);
        txtSend.setVisibility(View.VISIBLE);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("mobileAppCode",mobileAppCode);
        jsonObject.addProperty("caseNote",caseNote);
        mService.sendCaseNote(jsonObject,authHeader).enqueue(new Callback<SendCaseNoteResult>() {
            @Override
            public void onResponse(Call<SendCaseNoteResult> call, Response<SendCaseNoteResult> response) {

                if(response.body()!=null){
                    if(response.body().getResult().getHttpCode() == 200){
//                        if(response.body().getResult()!=null){
//                            p.putString(AppContants.TOKEN_LOGIN,response.body().getResult().getToken());
//                            token= response.body().getResult().getToken();
//                        }
                        AppNotesEntry appNotesEntry = new AppNotesEntry();
                        appNotesEntry.setAppUid(caseReturnArr.getAppId().toString());
                        appNotesEntry.setUsrUid(caseReturnArr.getSaleCode().toString());
                        appNotesEntry.setNoteContent(caseNote);
                        appNotesEntry.setNoteDate(DateUtils.getTodayTextWithHour());
                        appNotesEntryArrayList.add(appNotesEntry);
                        caseNoteAdapter.notifyDataSetChanged();
//                        progressLoading_not_invite.setVisibility(View.GONE);
                        txtSend.setVisibility(View.GONE);
                        editText.setText("");
                        item_list.scrollToPosition(appNotesEntryArrayList.size()-1);
                    }else {
//                        if(response.body().getResult()!=null){
//                            p.putString(AppContants.TOKEN_LOGIN,response.body().getResult().getToken());
//                        }
                        String message = response.body().getResult().getHttpMessage().toString();
                        DialogCall.showWaringDialog(DetailCaseActivity.this,message,new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int id = v.getId();
                                if (id == R.id.btn_ok) {

                                }
                            }
                        });
//                        progressLoading_not_invite.setVisibility(View.GONE);
                        txtSend.setVisibility(View.GONE);
                    }
                }}

            @Override
            public void onFailure(Call<SendCaseNoteResult> call, Throwable t) {
//                progressLoading_not_invite.setVisibility(View.GONE);
                txtSend.setVisibility(View.GONE);
                DialogCall.showWaringDialog(DetailCaseActivity.this, getString(R.string.cannot_connect_server), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });
    }
}
