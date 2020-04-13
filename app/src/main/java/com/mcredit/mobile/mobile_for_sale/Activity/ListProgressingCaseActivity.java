package com.mcredit.mobile.mobile_for_sale.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.isapanah.awesomespinner.AwesomeSpinner;
import com.mcredit.mobile.mobile_for_sale.Adapter.ProgressingCaseAdapter;
import com.mcredit.mobile.mobile_for_sale.Api.Service.SOService;
import com.mcredit.mobile.mobile_for_sale.Api.Utils.ApiUtils;
import com.mcredit.mobile.mobile_for_sale.Base.PermissionActivity;
import com.mcredit.mobile.mobile_for_sale.Contants.AppContants;
import com.mcredit.mobile.mobile_for_sale.Database.DatabaseHelper;
import com.mcredit.mobile.mobile_for_sale.Dialog.DialogCall;
import com.mcredit.mobile.mobile_for_sale.Models.CancelCase.CancelCase;
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
 * Created by hiephunbie on 5/16/18.
 */

public class ListProgressingCaseActivity extends PermissionActivity {

    private SOService mService;
    private ProgressBar progressLoading_not_invite;
    private String authHeader = "";
    private ArrayList<ArrCase> returnCaseList = new ArrayList<ArrCase>();
    private RecyclerView rv_answers;
    private ProgressingCaseAdapter mAdapter;
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
        setContentView(R.layout.list_progressing_case_activity);
        p = new Pref(ListProgressingCaseActivity.this);
        mService = ApiUtils.getSOService();
        authHeader = p.getString(AppContants.AUTHEN_LOGIN,"");
        databaseHelper = new DatabaseHelper(this);
        progressLoading_not_invite = (ProgressBar)findViewById(R.id.progressLoading);
        item_progress_bar = (ProgressBar)findViewById(R.id.item_progress_bar);
        rv_answers = (RecyclerView) findViewById(R.id.rv_answers);
        img_back = (ImageView)findViewById(R.id.img_back);
        edt_search = (EditText)findViewById(R.id.edt_search);
        imgSearch = (ImageView)findViewById(R.id.imgSearch);


        mAdapter = new ProgressingCaseAdapter(this, returnCaseList, rv_answers, new ProgressingCaseAdapter.PostItemListener() {

            @Override
            public void onPostClick(final long idClick) {
                Intent i = new Intent(ListProgressingCaseActivity.this, DetailCaseActivity.class);
                i.putExtra(AppContants.PROGRESSING_CASE_STATUS, returnCaseList.get((int) idClick));
                startActivity(i);
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
                Intent intent = new Intent(ListProgressingCaseActivity.this,MainActivity.class);
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
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ListProgressingCaseActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
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
        mService.getProgressingCase(jsonObject,authHeader,"application/json").enqueue(new Callback<ProgressingCaseArr>() {
            @Override
            public void onResponse(Call<ProgressingCaseArr> call, Response<ProgressingCaseArr> response) {
                if(response.body() != null){
                if(response.body().getResult()!=null){
                    if(response.body().getResult().getHttpCode() == 200){
                        if(response.body().getResult()!=null){
                            p.putString(AppContants.TOKEN_LOGIN,response.body().getResult().getToken());
                            token= response.body().getResult().getToken();
                        }
                        returnCaseList.clear();
                        if(response.body().getResult().getArrCase()!=null){
                            for (int i = 0;i<response.body().getResult().getArrCase().size();i++){
                                returnCaseList.add(response.body().getResult().getArrCase().get(i));
                            }
                        }}

                        mAdapter.notifyDataSetChanged();
                        progressLoading_not_invite.setVisibility(View.GONE);
                    }else {
//                        if(response.body().getResult()!=null){
//                            p.putString(AppContants.TOKEN_LOGIN,response.body().getResult().getToken());
//                        }
                        DialogCall.showWaringDialog(ListProgressingCaseActivity.this,getString(R.string.get_progressing_case_error),new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int id = v.getId();
                                if (id == R.id.btn_ok) {

                                }
                            }
                        });
                        progressLoading_not_invite.setVisibility(View.GONE);
                    }
                }}

            @Override
            public void onFailure(Call<ProgressingCaseArr> call, Throwable t) {
                progressLoading_not_invite.setVisibility(View.GONE);
                DialogCall.showWaringDialog(ListProgressingCaseActivity.this, getString(R.string.cannot_connect_server), new View.OnClickListener() {
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
        mService.getProgressingCase(jsonObject,authHeader,"application/json").enqueue(new Callback<ProgressingCaseArr>() {
            @Override
            public void onResponse(Call<ProgressingCaseArr> call, Response<ProgressingCaseArr> response) {
                if(response.body() != null){
                if(response.body().getResult()!=null){
                    if(response.body().getResult().getHttpCode() == 200) {
                        if (response.body().getResult() != null) {
                            p.putString(AppContants.TOKEN_LOGIN, response.body().getResult().getToken());
                            token = response.body().getResult().getToken();
                        }
                        if (response.body().getResult().getArrCase() != null) {
                            if (response.body().getResult().getArrCase().size() > 0) {
                                for (int i = 0; i < response.body().getResult().getArrCase().size(); i++) {
                                    returnCaseList.add(response.body().getResult().getArrCase().get(i));
                                }
                                item_progress_bar.setVisibility(View.GONE);
                                loading = false;
                                mAdapter.notifyDataSetChanged();
                                progressLoading_not_invite.setVisibility(View.GONE);
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
                        DialogCall.showWaringDialog(ListProgressingCaseActivity.this,getString(R.string.get_progressing_case_error),new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int id = v.getId();
                                if (id == R.id.btn_ok) {

                                }
                            }
                        });
                        item_progress_bar.setVisibility(View.GONE);
                    }
                }}

            @Override
            public void onFailure(Call<ProgressingCaseArr> call, Throwable t) {
                item_progress_bar.setVisibility(View.GONE);
                DialogCall.showWaringDialog(ListProgressingCaseActivity.this, getString(R.string.cannot_connect_server), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });
    }

}
