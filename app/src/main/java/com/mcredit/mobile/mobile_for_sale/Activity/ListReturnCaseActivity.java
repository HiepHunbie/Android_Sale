package com.mcredit.mobile.mobile_for_sale.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.isapanah.awesomespinner.AwesomeSpinner;
import com.mcredit.mobile.mobile_for_sale.Adapter.ReturnCaseAdapter;
import com.mcredit.mobile.mobile_for_sale.Api.Service.SOService;
import com.mcredit.mobile.mobile_for_sale.Api.Utils.ApiUtils;
import com.mcredit.mobile.mobile_for_sale.Base.PermissionActivity;
import com.mcredit.mobile.mobile_for_sale.Contants.AppContants;
import com.mcredit.mobile.mobile_for_sale.Database.DatabaseHelper;
import com.mcredit.mobile.mobile_for_sale.Dialog.DialogCall;
import com.mcredit.mobile.mobile_for_sale.Models.CancelCase.CancelCase;
import com.mcredit.mobile.mobile_for_sale.Models.ReturnCase.CaseReturnArr;
import com.mcredit.mobile.mobile_for_sale.Models.ReturnCase.ReturnCaseResult;
import com.mcredit.mobile.mobile_for_sale.R;
import com.mcredit.mobile.mobile_for_sale.Utils.ItemOffsetDecoration;
import com.mcredit.mobile.mobile_for_sale.Utils.Pref;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hiephunbie on 4/10/18.
 */

public class ListReturnCaseActivity extends PermissionActivity{

        private SOService mService;
        private ProgressBar progressLoading_not_invite;
        private String authHeader = "";
        private ArrayList<CaseReturnArr> returnCaseList = new ArrayList<CaseReturnArr>();
        private RecyclerView rv_answers;
        private ReturnCaseAdapter mAdapter;
        private DatabaseHelper databaseHelper;
        private ImageView img_back;
        private String token = "";
        private EditText edt_search;
        private ImageView imgSearch;
        private int firstResult = 0;
    private int lastVisibleItem, totalItemCount;
    private boolean loading = false;
    private ProgressBar item_progress_bar;
    private int visibleThreshold = AppContants.visibleThreshold;
    private ArrayAdapter<String> cancelAdapter;
    private ArrayList<String> cancelList = new ArrayList<String>();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_return_case_activity);
        p = new Pref(ListReturnCaseActivity.this);
        mService = ApiUtils.getSOService();
        authHeader = p.getString(AppContants.AUTHEN_LOGIN,"");
        databaseHelper = new DatabaseHelper(this);
        progressLoading_not_invite = (ProgressBar)findViewById(R.id.progressLoading);
            item_progress_bar = (ProgressBar)findViewById(R.id.item_progress_bar);
        rv_answers = (RecyclerView) findViewById(R.id.rv_answers);
        img_back = (ImageView)findViewById(R.id.img_back);
            edt_search = (EditText)findViewById(R.id.edt_search);
            imgSearch = (ImageView)findViewById(R.id.imgSearch);
            cancelList.add(getString(R.string.not_wanted));
            cancelList.add(getString(R.string.difrence));
            cancelAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, cancelList);
            cancelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAdapter = new ReturnCaseAdapter(this, returnCaseList, rv_answers, new ReturnCaseAdapter.PostItemListener() {

            @Override
            public void onPostClick(final long idClick) {
                Intent i = new Intent(ListReturnCaseActivity.this, UpdateCaseActivity.class);
                i.putExtra(AppContants.UPDATE_CASE_FAIL, returnCaseList.get((int) idClick));
                startActivity(i);
            }

            @Override
            public void onLongClick(final long idClick) {
              showConfirmCancelDialog(ListReturnCaseActivity.this,returnCaseList.get((int) idClick).getDataEntrySales().getMobileAppCode().toString());
            }
        });

        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv_answers.setLayoutManager(layoutManager);
        rv_answers.setAdapter(mAdapter);
        rv_answers.setHasFixedSize(true);

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen._2sdp);
        rv_answers.addItemDecoration(itemDecoration);
        final GridLayoutManager manager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        rv_answers.setLayoutManager(manager);

        loadData();
        rv_answers.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = manager.getItemCount();
                lastVisibleItem = manager.findLastVisibleItemPosition();
                if (!loading && (totalItemCount <= (lastVisibleItem + visibleThreshold))) {
                    loadMoreData();
                    loading=true;
                    item_progress_bar.setVisibility(View.VISIBLE);
                }
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListReturnCaseActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstResult = 0;
                loadData();
            }
        });
            edt_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                            actionId == EditorInfo.IME_ACTION_DONE ||
                            event != null &&
                                    event.getAction() == KeyEvent.ACTION_DOWN &&
                                    event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                        if (event == null || !event.isShiftPressed()) {
                            // the user is done typing.
                            firstResult = 0;
                            loadData();
                            return true; // consume.
                        }
                    }
                    return false;
                }
            });
    }

        @Override
        protected void onResume() {
        super.onResume();
    }

    private void cancelCase(String abortProcessComment,String abortProcessReason,String mobileAppCode){
        token= p.getString(AppContants.TOKEN_LOGIN,"");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token",token.toString());
        jsonObject.addProperty("mobileAppCode",mobileAppCode);
        jsonObject.addProperty("abortProcessComment",abortProcessComment);
        jsonObject.addProperty("abortProcessReason",abortProcessReason);
        mService.cancelCase(jsonObject,authHeader).enqueue(new Callback<CancelCase>() {
            @Override
            public void onResponse(Call<CancelCase> call, Response<CancelCase> response) {
                if(response.body() != null){
                if(response.body().getResult()!=null){
                    if(response.body().getResult().getHttpCode() == 200){
                        if(response.body().getResult()!=null){
                            p.putString(AppContants.TOKEN_LOGIN,response.body().getResult().getToken());
                            token= response.body().getResult().getToken();
                        }
                        loadData();
                        DialogCall.showWaringDialog(ListReturnCaseActivity.this,getString(R.string.cancel_case_success),new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int id = v.getId();
                                if (id == R.id.btn_ok) {

                                }
                            }
                        });
                        progressLoading_not_invite.setVisibility(View.GONE);
                    }else {
//                        if(response.body().getResult()!=null){
//                            p.putString(AppContants.TOKEN_LOGIN,response.body().getResult().getToken());
//                        }
                        DialogCall.showWaringDialog(ListReturnCaseActivity.this,getString(R.string.cancel_case_error),new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int id = v.getId();
                                if (id == R.id.btn_ok) {

                                }
                            }
                        });
                        progressLoading_not_invite.setVisibility(View.GONE);
                    }
                }}}

            @Override
            public void onFailure(Call<CancelCase> call, Throwable t) {
                progressLoading_not_invite.setVisibility(View.GONE);
                DialogCall.showWaringDialog(ListReturnCaseActivity.this, getString(R.string.cannot_connect_server), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ListReturnCaseActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void showConfirmCancelDialog(final Activity mContext, final String mobileAppCode){
        final AlertDialog builder = new AlertDialog.Builder(mContext).create();
        builder.show();
        builder.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        builder.setCanceledOnTouchOutside(false);
        builder.setCancelable(false);

        Window window = builder.getWindow();
        window.setContentView(R.layout.dialog_confirm_cancel_case);

        final AwesomeSpinner caseadd_cancel = (AwesomeSpinner) window.findViewById(R.id.caseadd_cancel);

        final EditText edt_detail = (EditText) window.findViewById(R.id.edt_detail);
        Button btnYes = (Button)window.findViewById(R.id.btn_ok);
        Button btnNo = (Button)window.findViewById(R.id.btn_cancel);

        caseadd_cancel.setAdapter(cancelAdapter);
        caseadd_cancel.setOnSpinnerItemClickListener(new AwesomeSpinner.onSpinnerItemClickListener<String>() {
            @Override
            public void onItemSelected(int position, String s) {
            }
        });
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if(caseadd_cancel.getSelectedItem() != null){
                    cancelCase(caseadd_cancel.getSelectedItem().toString(),edt_detail.getText().toString(),mobileAppCode);
                }else {
                    cancelCase("",edt_detail.getText().toString(),mobileAppCode);
                }

                builder.dismiss();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                builder.dismiss();
            }
        });

    }
    private void loadData(){
        progressLoading_not_invite.setVisibility(View.VISIBLE);
        String dataSearch = "";
        if(edt_search.getText()!=null){
            dataSearch = edt_search.getText().toString();
        }
        token= p.getString(AppContants.TOKEN_LOGIN,"");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token",token.toString());
        jsonObject.addProperty("firstResult",firstResult);
        jsonObject.addProperty("countResult",AppContants.COUNTRESULT);
        jsonObject.addProperty("mobileSearchString",dataSearch);
        mService.getReturnCase(jsonObject,authHeader,"application/json").enqueue(new Callback<ReturnCaseResult>() {
            @Override
            public void onResponse(Call<ReturnCaseResult> call, Response<ReturnCaseResult> response) {
                if(response.body()!= null){

                if(response.body().getResult()!=null){
                    if(response.body().getResult().getHttpCode() == 200){
                        if(response.body().getResult()!=null){
                            p.putString(AppContants.TOKEN_LOGIN,response.body().getResult().getToken());
                            token= response.body().getResult().getToken();
                        }
                            returnCaseList.clear();
                        if(response.body().getResult().getCaseReturnArr()!=null){
                            for (int i = 0;i<response.body().getResult().getCaseReturnArr().size();i++){
                                returnCaseList.add(response.body().getResult().getCaseReturnArr().get(i));
                            }
                        }

                        mAdapter.notifyDataSetChanged();
                        progressLoading_not_invite.setVisibility(View.GONE);
                    }else {
//                        if(response.body().getResult()!=null){
//                            p.putString(AppContants.TOKEN_LOGIN,response.body().getResult().getToken());
//                        }
                        loading=false;
                        DialogCall.showWaringDialog(ListReturnCaseActivity.this,getString(R.string.get_return_case_error),new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int id = v.getId();
                                if (id == R.id.btn_ok) {

                                }
                            }
                        });
                        progressLoading_not_invite.setVisibility(View.GONE);
                    }
                }}else {
                    loading=false;
                    DialogCall.showWaringDialog(ListReturnCaseActivity.this,getString(R.string.get_return_case_error),new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int id = v.getId();
                            if (id == R.id.btn_ok) {

                            }
                        }
                    });
                    progressLoading_not_invite.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<ReturnCaseResult> call, Throwable t) {
                progressLoading_not_invite.setVisibility(View.GONE);
                DialogCall.showWaringDialog(ListReturnCaseActivity.this, getString(R.string.cannot_connect_server), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });
    }

    private void loadMoreData(){
        item_progress_bar.setVisibility(View.VISIBLE);
        firstResult = firstResult + AppContants.COUNTRESULT;
        String dataSearch = "";
        if(edt_search.getText()!=null){
            dataSearch = edt_search.getText().toString();
        }
        token= p.getString(AppContants.TOKEN_LOGIN,"");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token",token.toString());
        jsonObject.addProperty("firstResult",firstResult);
        jsonObject.addProperty("countResult",AppContants.COUNTRESULT);
        jsonObject.addProperty("mobileSearchString",dataSearch);
        mService.getReturnCase(jsonObject,authHeader,"application/json").enqueue(new Callback<ReturnCaseResult>() {
            @Override
            public void onResponse(Call<ReturnCaseResult> call, Response<ReturnCaseResult> response) {
                if(response.body() != null){
                if(response.body().getResult()!=null){
                    if(response.body().getResult().getHttpCode() == 200) {
                        if (response.body().getResult() != null) {
                            p.putString(AppContants.TOKEN_LOGIN, response.body().getResult().getToken());
                            token = response.body().getResult().getToken();
                        }
                        if (response.body().getResult().getCaseReturnArr() != null) {
                            if (response.body().getResult().getCaseReturnArr().size() > 0) {
                                for (int i = 0; i < response.body().getResult().getCaseReturnArr().size(); i++) {
                                    returnCaseList.add(response.body().getResult().getCaseReturnArr().get(i));
                                }
                                item_progress_bar.setVisibility(View.GONE);
                                loading = false;
                                mAdapter.notifyDataSetChanged();
                            } else {
                                loading = true;
                                item_progress_bar.setVisibility(View.GONE);
                            }

                        } else {
                            loading = true;
                            item_progress_bar.setVisibility(View.GONE);
                        }
                    }
                    }else {
//                        if(response.body().getResult()!=null){
//                            p.putString(AppContants.TOKEN_LOGIN,response.body().getResult().getToken());
//                        }
                        DialogCall.showWaringDialog(ListReturnCaseActivity.this,getString(R.string.get_return_case_error),new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int id = v.getId();
                                if (id == R.id.btn_ok) {

                                }
                            }
                        });
                    }
                }}

            @Override
            public void onFailure(Call<ReturnCaseResult> call, Throwable t) {
                item_progress_bar.setVisibility(View.GONE);
                DialogCall.showWaringDialog(ListReturnCaseActivity.this, getString(R.string.cannot_connect_server), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });
    }
}
