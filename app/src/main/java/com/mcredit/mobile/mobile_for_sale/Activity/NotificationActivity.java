package com.mcredit.mobile.mobile_for_sale.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.mcredit.mobile.mobile_for_sale.Adapter.NotificationAdapter;
import com.mcredit.mobile.mobile_for_sale.Adapter.ProgressingCaseAdapter;
import com.mcredit.mobile.mobile_for_sale.Api.Service.SOService;
import com.mcredit.mobile.mobile_for_sale.Api.Utils.ApiUtils;
import com.mcredit.mobile.mobile_for_sale.Base.PermissionActivity;
import com.mcredit.mobile.mobile_for_sale.Contants.AppContants;
import com.mcredit.mobile.mobile_for_sale.Database.DatabaseHelper;
import com.mcredit.mobile.mobile_for_sale.Dialog.DialogCall;
import com.mcredit.mobile.mobile_for_sale.Models.Notification.Message;
import com.mcredit.mobile.mobile_for_sale.Models.Notification.NotificationResult;
import com.mcredit.mobile.mobile_for_sale.Models.ProgressingCase.ArrCase;
import com.mcredit.mobile.mobile_for_sale.Models.ProgressingCase.ProgressingCaseArr;
import com.mcredit.mobile.mobile_for_sale.R;
import com.mcredit.mobile.mobile_for_sale.Utils.ItemOffsetDecoration;
import com.mcredit.mobile.mobile_for_sale.Utils.Pref;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hiephunbie on 5/28/18.
 */

public class NotificationActivity extends PermissionActivity {

    private SOService mService;
    private ProgressBar progressLoading_not_invite;
    private String authHeader = "";
    private ArrayList<Message> noticationList = new ArrayList<Message>();
    private RecyclerView rv_answers;
    private NotificationAdapter mAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_notification_activity);
        p = new Pref(NotificationActivity.this);
        mService = ApiUtils.getSOService();
        authHeader = p.getString(AppContants.AUTHEN_LOGIN,"");
        databaseHelper = new DatabaseHelper(this);
        progressLoading_not_invite = (ProgressBar)findViewById(R.id.progressLoading);
        item_progress_bar = (ProgressBar)findViewById(R.id.item_progress_bar);
        rv_answers = (RecyclerView) findViewById(R.id.rv_answers);
        img_back = (ImageView)findViewById(R.id.img_back);
        edt_search = (EditText)findViewById(R.id.edt_search);
        imgSearch = (ImageView)findViewById(R.id.imgSearch);


        mAdapter = new NotificationAdapter(this, noticationList, rv_answers, new NotificationAdapter.PostItemListener() {

            @Override
            public void onPostClick(final long idClick) {
            }

            @Override
            public void onLongClick(final long idClick) {

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
                Log.d("lastVisibleItem "," ss "+totalItemCount + " ss "+ lastVisibleItem + " ss "+loading );
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

    private void loadData(){
        progressLoading_not_invite.setVisibility(View.VISIBLE);
        token= p.getString(AppContants.TOKEN_LOGIN,"");
        String dataSearch = "";
        if(edt_search.getText()!=null){
            dataSearch = edt_search.getText().toString();
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token",token.toString());
        jsonObject.addProperty("firstResult",firstResult);
        jsonObject.addProperty("countResult",AppContants.COUNTRESULT);
        jsonObject.addProperty("mobileSearchString",dataSearch);
        mService.getNotification(jsonObject,authHeader).enqueue(new Callback<NotificationResult>() {
            @Override
            public void onResponse(Call<NotificationResult> call, Response<NotificationResult> response) {
                if(response.body() != null){
                if(response.body().getResult()!=null){
                    if(response.body().getResult().getHttpCode() == 200){
                        if(response.body().getResult()!=null){
                            p.putString(AppContants.TOKEN_LOGIN,response.body().getResult().getToken());
                            token= response.body().getResult().getToken();
                        }
                        noticationList.clear();
                        if(response.body().getResult().getMessages()!=null){
                            for (int i = 0;i<response.body().getResult().getMessages().size();i++){
                                noticationList.add(response.body().getResult().getMessages().get(i));
                            }
                        }

                        mAdapter.notifyDataSetChanged();
                        progressLoading_not_invite.setVisibility(View.GONE);
                    }else {
//                        if(response.body().getResult()!=null){
//                            p.putString(AppContants.TOKEN_LOGIN,response.body().getResult().getToken());
//                        }
                        DialogCall.showWaringDialog(NotificationActivity.this,getString(R.string.get_progressing_case_error),new View.OnClickListener() {
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
            public void onFailure(Call<NotificationResult> call, Throwable t) {
                progressLoading_not_invite.setVisibility(View.GONE);
                DialogCall.showWaringDialog(NotificationActivity.this, getString(R.string.cannot_connect_server), new View.OnClickListener() {
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
        mService.getNotification(jsonObject,authHeader).enqueue(new Callback<NotificationResult>() {
            @Override
            public void onResponse(Call<NotificationResult> call, Response<NotificationResult> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        if (response.body().getResult().getHttpCode() == 200) {
                            if (response.body().getResult() != null) {
                                p.putString(AppContants.TOKEN_LOGIN, response.body().getResult().getToken());
                                token = response.body().getResult().getToken();
                            }
                            if (response.body().getResult().getMessages() != null) {
                                if (response.body().getResult().getMessages().size() > 0) {
                                    for (int i = 0; i < response.body().getResult().getMessages().size(); i++) {
                                        noticationList.add(response.body().getResult().getMessages().get(i));
                                    }
                                    item_progress_bar.setVisibility(View.GONE);
                                    loading = false;
                                    mAdapter.notifyDataSetChanged();
                                } else {
                                    loading = true;
                                    item_progress_bar.setVisibility(View.GONE);
                                    progressLoading_not_invite.setVisibility(View.GONE);
                                }

                            } else {
                                loading = true;
                                item_progress_bar.setVisibility(View.GONE);
                            }


                        } else {
//                        if(response.body().getResult()!=null){
//                            p.putString(AppContants.TOKEN_LOGIN,response.body().getResult().getToken());
//                        }
                            DialogCall.showWaringDialog(NotificationActivity.this, getString(R.string.get_notification_case_error), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int id = v.getId();
                                    if (id == R.id.btn_ok) {

                                    }
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationResult> call, Throwable t) {
                item_progress_bar.setVisibility(View.GONE);
                DialogCall.showWaringDialog(NotificationActivity.this, getString(R.string.cannot_connect_server), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });
    }

}
