package com.mcredit.mobile.mobile_for_sale.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.gson.JsonObject;
import com.mcredit.mobile.mobile_for_sale.Adapter.DraftCaseAdapter;
import com.mcredit.mobile.mobile_for_sale.Api.Service.SOService;
import com.mcredit.mobile.mobile_for_sale.Api.Utils.ApiUtils;
import com.mcredit.mobile.mobile_for_sale.Base.PermissionActivity;
import com.mcredit.mobile.mobile_for_sale.Contants.AppContants;
import com.mcredit.mobile.mobile_for_sale.Database.DatabaseHelper;
import com.mcredit.mobile.mobile_for_sale.Dialog.DialogCall;
import com.mcredit.mobile.mobile_for_sale.Models.CaseNote.AppNotesEntry;
import com.mcredit.mobile.mobile_for_sale.Models.Database.DraftCase;
import com.mcredit.mobile.mobile_for_sale.Models.DeleteDraftCase.DeleteDraftCaseResult;
import com.mcredit.mobile.mobile_for_sale.Models.DraftCase.DraftResult;
import com.mcredit.mobile.mobile_for_sale.Models.SendCaseNote.SendCaseNoteResult;
import com.mcredit.mobile.mobile_for_sale.R;
import com.mcredit.mobile.mobile_for_sale.Utils.DateUtils;
import com.mcredit.mobile.mobile_for_sale.Utils.ItemOffsetDecoration;
import com.mcredit.mobile.mobile_for_sale.Utils.Pref;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hiephunbie on 4/4/18.
 */

public class ListDraftCaseActivity extends PermissionActivity {

    private SOService mService;
    private ProgressBar progressLoading_not_invite,progressLoading_from_device;
    private String authHeader = "";
    private DraftResult draftResult = new DraftResult();
    private List<DraftCase> draftCaseList = new ArrayList<DraftCase>();
    private List<DraftCase> draftCaseListFromDevice = new ArrayList<DraftCase>();
    private RecyclerView rv_answers,rv_answers_from_device;
    private DraftCaseAdapter mAdapter,mAdapterFromDevice;
    private DatabaseHelper databaseHelper;
    private ImageView img_back;
    private ArrayList<String> listMobileAppCode = new ArrayList<String>();
    private String token = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_draft_case_activity);
        p = new Pref(ListDraftCaseActivity.this);
        mService = ApiUtils.getSOService();
        authHeader = p.getString(AppContants.AUTHEN_LOGIN,"");
        databaseHelper = new DatabaseHelper(this);
        progressLoading_not_invite = (ProgressBar)findViewById(R.id.progressLoading);
        rv_answers = (RecyclerView) findViewById(R.id.rv_answers);
        progressLoading_from_device = (ProgressBar)findViewById(R.id.progressLoading_from_device);
        rv_answers_from_device = (RecyclerView) findViewById(R.id.rv_answers_from_device);
        img_back = (ImageView)findViewById(R.id.img_back);
        draftCaseListFromDevice = databaseHelper.getAllDraftCase(p.getString(AppContants.USERNAME_LOGIN,""));
        for (int i = 0;i<draftCaseListFromDevice.size();i++){
            listMobileAppCode.add(draftCaseListFromDevice.get(i).getMobileAppCode().toString());
        }
        progressLoading_from_device.setVisibility(View.GONE);
        mAdapter = new DraftCaseAdapter(this, draftCaseList, rv_answers, new DraftCaseAdapter.PostItemListener() {

            @Override
            public void onPostClick(final long idClick) {
                Intent i = new Intent(ListDraftCaseActivity.this, CreateNewCaseActivity.class);
                i.putExtra(AppContants.UPDATE_DRAFT_CASE, draftCaseList.get((int) idClick));
                i.putExtra(AppContants.IS_NEW_CASE, 1);
                startActivity(i);
            }

            @Override
            public void onLongClick(final long idClick) {
                    DialogCall.showWaringDialog(ListDraftCaseActivity.this, getString(R.string.warning_delete_Case_in_sever), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
            }
        });

        mAdapterFromDevice = new DraftCaseAdapter(this, draftCaseListFromDevice, rv_answers_from_device, new DraftCaseAdapter.PostItemListener() {

            @Override
            public void onPostClick(final long idClick) {
                Intent i = new Intent(ListDraftCaseActivity.this, CreateNewCaseActivity.class);
                i.putExtra(AppContants.UPDATE_DRAFT_CASE, draftCaseListFromDevice.get((int) idClick));
                i.putExtra(AppContants.IS_NEW_CASE, 1);
                startActivity(i);
            }

            @Override
            public void onLongClick(final long idClick) {
                    DialogCall.showConfirmDialog(ListDraftCaseActivity.this,getString(R.string.confirm_delete_Case),new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int id = v.getId();
                            if (id == R.id.btn_ok) {
//                                deleteDraftCase(draftCaseListFromDevice.get((int) idClick).getMobileAppCode().toString().trim(),(int) idClick);
                                if(draftCaseListFromDevice.get((int) idClick).getIsFromDevice().equals("1")){
                                    databaseHelper.deleteADraftCaseByMobileAppCode(draftCaseListFromDevice.get((int)idClick).getMobileAppCode());
                                    databaseHelper.deleteADraftImageByMobileAppCode(draftCaseListFromDevice.get((int)idClick).getMobileAppCode());
                                    draftCaseListFromDevice.remove(draftCaseListFromDevice.get((int)idClick));
                                    mAdapterFromDevice.notifyDataSetChanged();
                                }else {
//                                    DialogCall.showWaringDialog(ListDraftCaseActivity.this, getString(R.string.warning_delete_Case_in_sever), new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//
//                                        }
//                                    });
                                }

                            }
                            else if (id == R.id.btn_cancel) {
                            }
                        }
                    });


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

//        loadData();
        rv_answers.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

//                totalItemCount = manager.getItemCount();
//                lastVisibleItem = manager.findLastVisibleItemPosition();
//                if (!loading && (totalItemCount <= (lastVisibleItem + visibleThreshold))) {
//                    loading=true;
//                    item_progress_bar.setVisibility(View.VISIBLE);
//                    loadMoreItemList();
//
//                }
            }
        });
        final RecyclerView.LayoutManager layoutManagerFromDevice = new LinearLayoutManager(this);
        rv_answers_from_device.setLayoutManager(layoutManagerFromDevice);
        rv_answers_from_device.setAdapter(mAdapterFromDevice);
        rv_answers_from_device.setHasFixedSize(true);

        rv_answers_from_device.addItemDecoration(itemDecoration);
        final GridLayoutManager managerFromDevice = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        rv_answers_from_device.setLayoutManager(managerFromDevice);

        rv_answers_from_device.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

//                totalItemCount = manager.getItemCount();
//                lastVisibleItem = manager.findLastVisibleItemPosition();
//                if (!loading && (totalItemCount <= (lastVisibleItem + visibleThreshold))) {
//                    loading=true;
//                    item_progress_bar.setVisibility(View.VISIBLE);
//                    loadMoreItemList();
//
//                }
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void deleteDraftCase(String mobileAppCode, final int idCase){
        progressLoading_from_device.setVisibility(View.VISIBLE);
        token= p.getString(AppContants.TOKEN_LOGIN,"");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("mobileAppCode",mobileAppCode);
        jsonObject.addProperty("token",token);
        mService.deleteDraftCase(jsonObject,authHeader).enqueue(new Callback<DeleteDraftCaseResult>() {
            @Override
            public void onResponse(Call<DeleteDraftCaseResult> call, Response<DeleteDraftCaseResult> response) {

                if(response.body()!=null){
                    if(response.body().getResult().getHttpCode() == 200){
                        if(response.body().getResult()!=null){
                            p.putString(AppContants.TOKEN_LOGIN,response.body().getResult().getToken());
                            token= response.body().getResult().getToken();
                        }
                        draftCaseListFromDevice.remove(draftCaseListFromDevice.get(idCase));
                        mAdapterFromDevice.notifyDataSetChanged();
                        progressLoading_from_device.setVisibility(View.GONE);
                    }else {
//                        if(response.body().getResult()!=null){
//                            p.putString(AppContants.TOKEN_LOGIN,response.body().getResult().getToken());
//                        }
                        String message = response.body().getResult().getHttpMessage().toString();
                        DialogCall.showWaringDialog(ListDraftCaseActivity.this,message,new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int id = v.getId();
                                if (id == R.id.btn_ok) {

                                }
                            }
                        });
                        progressLoading_from_device.setVisibility(View.GONE);
                    }
                }}

            @Override
            public void onFailure(Call<DeleteDraftCaseResult> call, Throwable t) {
                progressLoading_from_device.setVisibility(View.GONE);
                DialogCall.showWaringDialog(ListDraftCaseActivity.this, getString(R.string.cannot_connect_server), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });
    }
    private void loadData(){
        progressLoading_from_device.setVisibility(View.VISIBLE);
        mService.getDraftCase(authHeader).enqueue(new Callback<DraftResult>() {
            @Override
            public void onResponse(Call<DraftResult> call, Response<DraftResult> response) {
                if(response.body() != null){
                if(response.body().getResult()!=null){
                    if(response.body().getResult().getHttpCode() == 200) {
                        if (response.body().getResult() != null) {
                            p.putString(AppContants.TOKEN_LOGIN, response.body().getResult().getToken());
                        }
//                        draftResult.setResult(response.body().getResult());
                        if (response.body().getResult().getArrCase() != null) {
                            for (int i = 0; i < response.body().getResult().getArrCase().size(); i++) {
                                String fullname = "";
                                if (response.body().getResult().getArrCase().get(i).getMobileCustomerName() != null) {
                                    fullname = response.body().getResult().getArrCase().get(i).getMobileCustomerName().toString();
                                }
                                String card = "";
                                if (response.body().getResult().getArrCase().get(i).getMobileCitizenId() != null) {
                                    card = response.body().getResult().getArrCase().get(i).getMobileCitizenId().toString();
                                }
                                String date_card = "";
                                if (response.body().getResult().getArrCase().get(i).getMobileIssueDateCitizen() != null) {
                                    date_card = response.body().getResult().getArrCase().get(i).getMobileIssueDateCitizen().toString();
                                }
                                String termloan = "";
                                if ((response.body().getResult().getArrCase().get(i).getMobileLoanTenor()+"") != null) {
                                    termloan = response.body().getResult().getArrCase().get(i).getMobileLoanTenor() + "";
                                }
                                String document_type_value = "";
                                if (response.body().getResult().getArrCase().get(i).getMobileSchemaProductName() != null) {
                                    document_type_value = response.body().getResult().getArrCase().get(i).getMobileSchemaProductName().toString();
                                }
                                String document_type_code = "";
                                if (response.body().getResult().getArrCase().get(i).getMobileSchemaProductCode() != null) {
                                    document_type_code = response.body().getResult().getArrCase().get(i).getMobileSchemaProductCode().toString();
                                }
                                String kiosk_value = "";
                                if (response.body().getResult().getArrCase().get(i).getMobileSignContracAt() != null) {
                                    kiosk_value = response.body().getResult().getArrCase().get(i).getMobileSignContracAt().toString();
                                }
                                String kiosk_code = "";
                                if (response.body().getResult().getArrCase().get(i).getMobileSignContracAt() != null) {
                                    kiosk_code = response.body().getResult().getArrCase().get(i).getMobileSignContracAt().toString();
                                }
                                String kiosk_address = "";
                                if (response.body().getResult().getArrCase().get(i).getMobileSignContracAddress() != null) {
                                    kiosk_address = response.body().getResult().getArrCase().get(i).getMobileSignContracAddress().toString();
                                }
                                String cat_type = "";
                                if (response.body().getResult().getArrCase().get(i).getMobileCatResultType() != null) {
                                    cat_type = response.body().getResult().getArrCase().get(i).getMobileCatResultType().toString();
                                }
                                String company_name = "";
                                if (response.body().getResult().getArrCase().get(i).getMobileCatResultCompanyName() != null) {
                                    company_name = response.body().getResult().getArrCase().get(i).getMobileCatResultCompanyName().toString();
                                }
                                String insurance = "";
                                if ((response.body().getResult().getArrCase().get(i).getMobileHasInsurrance() + "") != null) {
                                    insurance = response.body().getResult().getArrCase().get(i).getMobileHasInsurrance() + "";
                                }
                                String mobileAppCode = "";
                                if (response.body().getResult().getArrCase().get(i).getMobileAppCode() != null) {
                                    mobileAppCode = response.body().getResult().getArrCase().get(i).getMobileAppCode().toString();
                                }
                                String money = "";
                                money = response.body().getResult().getArrCase().get(i).getMobileLoanAmount() + "";
                                String tax_code = "";
                                if (response.body().getResult().getArrCase().get(i).getMobileCompanyTaxNumber() != null) {
                                    tax_code = response.body().getResult().getArrCase().get(i).getMobileCompanyTaxNumber().toString();
                                }
                                String signContractAddress = "";
                                if (response.body().getResult().getArrCase().get(i).getMobileTemResidence() != null) {
                                    signContractAddress = response.body().getResult().getArrCase().get(i).getMobileTemResidence().toString();
                                }
//                                if(!listMobileAppCode.contains(mobileAppCode)){
//                                    draftCaseListFromDevice.add(new DraftCase(fullname, card, date_card, termloan, document_type_value,
//                                            document_type_code, kiosk_value, kiosk_code, kiosk_address, cat_type, company_name, insurance, signContractAddress,
//                                            money, tax_code, mobileAppCode, "0", p.getString(AppContants.USERNAME_LOGIN, "")));
//                                }
                                draftCaseList.add(new DraftCase(fullname, card, date_card, termloan, document_type_value,
                                        document_type_code, kiosk_value, kiosk_code, kiosk_address, cat_type, company_name, insurance, signContractAddress,
                                        money, tax_code, mobileAppCode, "0", p.getString(AppContants.USERNAME_LOGIN, "")));
                            }
                        }
                    }
                        mAdapter.notifyDataSetChanged();
//                        mAdapterFromDevice.notifyDataSetChanged();
                    progressLoading_from_device.setVisibility(View.GONE);
                    }else {

//                        if(response.body().getResult()!=null){
//                            p.putString(AppContants.TOKEN_LOGIN,response.body().getResult().getToken());
//                        }
                        DialogCall.showWaringDialog(ListDraftCaseActivity.this,getString(R.string.create_new_case_error),new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int id = v.getId();
                                if (id == R.id.btn_ok) {

                                }
                            }
                        });
                    progressLoading_from_device.setVisibility(View.GONE);
                    }
                }}

            @Override
            public void onFailure(Call<DraftResult> call, Throwable t) {
                progressLoading_from_device.setVisibility(View.GONE);
                DialogCall.showWaringDialog(ListDraftCaseActivity.this, getString(R.string.cannot_connect_server), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });
    }
}
