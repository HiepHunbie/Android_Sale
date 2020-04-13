package com.mcredit.mobile.mobile_for_sale.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mcredit.mobile.mobile_for_sale.Api.Service.SOService;
import com.mcredit.mobile.mobile_for_sale.Api.Utils.ApiUtils;
import com.mcredit.mobile.mobile_for_sale.Base.PermissionActivity;
import com.mcredit.mobile.mobile_for_sale.Contants.AppContants;
import com.mcredit.mobile.mobile_for_sale.Database.DatabaseHelper;
import com.mcredit.mobile.mobile_for_sale.Dialog.DialogCall;
import com.mcredit.mobile.mobile_for_sale.Models.CreateNewCase.ResultCreateNewCase;
import com.mcredit.mobile.mobile_for_sale.Models.Database.DraftCase;
import com.mcredit.mobile.mobile_for_sale.Models.GetCat.GetCatInfo;
import com.mcredit.mobile.mobile_for_sale.Models.GetInfoNewCase.Config;
import com.mcredit.mobile.mobile_for_sale.Models.GetInfoNewCase.ResultInfoNewCase;
import com.mcredit.mobile.mobile_for_sale.R;
import com.mcredit.mobile.mobile_for_sale.Utils.Edittext;
import com.mcredit.mobile.mobile_for_sale.Utils.Pref;
import com.google.gson.JsonObject;
import com.isapanah.awesomespinner.AwesomeSpinner;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hiephunbie on 3/19/18.
 */

public class CreateNewCaseActivity extends PermissionActivity {

    private LinearLayout caseadd_FABupload,caseaddDrap_FAB;
    private SOService mService;
    private ProgressBar progressLoading_not_invite;
    private com.isapanah.awesomespinner.AwesomeSpinner caseadd_termloan,caseadd_scheme,caseadd_places;
    private JsonObject jsonInfoUser = new JsonObject();
    private ArrayList<String> termloanList = new ArrayList<String>();
    private ArrayList<String> schemenList = new ArrayList<String>();
    private ArrayList<String> schemenListCode = new ArrayList<String>();
    private ArrayList<String> placesList = new ArrayList<String>();
    private ArrayList<String> placesListCode = new ArrayList<String>();
    private ArrayList<String> placesListOffice = new ArrayList<String>();
    private ArrayAdapter<String> schemeAdapter;
    private ArrayAdapter<String> placesAdapter;
    private ArrayAdapter<String> termLoanAdapter;
    private EditText caseadd_fullname,caseadd_cmnd,dp_date_cmnd;
    private EditText caseadd_money;
    private TextView caseadd_error_tv_fullname,caseadd_error_tv_cmnd,caseadd_error_tv_places,caseadd_error_tv_cmnd_date,caseadd_error_tv_money,error_address_office,caseadd_error_tv_documment_type;
    private TextView caseadd_error_tv_term_loan,caseadd_error_tv_tax_code,caseadd_error_tv_scheme;
    private RadioGroup radio_group,radio_groupShk;
    private RadioButton radioYes,radioNo,radioYesShk,radioNoShk;
    DatePickerDialog datePickerDialog;
    private JsonObject jsonCreateNewCase = new JsonObject();
    private ResultCreateNewCase resultCreateNewCase = new ResultCreateNewCase();
    private String mobileLoanAmount= "", mobileCustomerName= "", mobileSchemeCode= "", mobileSchemeName= "", mobileTem= "", mobileTempLabel= "", mobileIssueDate = "";
    private String mobileCitiID= "",mobileHasIn= "", mobilePlace= "ABC", mobileCode= "", mobileTenor= "", mobileAppCode= "",IMEI_Number_Holder= "",token= "",authHeader = "",tokenCreate="";
    private String saleCode="",saleName="",saleMobile="",salePath="",shopCode="";
    private TextView txt_office_address;
    private int minValue, maxValue, minMonth, maxMonth;
    private ArrayList<ArrayList<Config>> configArrayList = new ArrayList<ArrayList<Config>>();
    private ImageView btnBack;
    private Button btn_check_cat;
    private LinearLayout layout_company,caseadd_error_ll_cat,layout_tax_code;
    private TextView txt_title_company, txt_title_cat_info;
    private EditText txt_tax_code;
    private String mobileCompanyTaxNumber ="",mobileCatResultCompanyName ="",mobileCatResultType ="",signContractAddress="",signContractAt="",addressOffice = "";
    private String cat_type_company = "";
    private String formatText = "[-\\[\\]^/,'*:.!><~@#$%+=?|\"\\\\()]+";
    public DatabaseHelper databaseHelper;
    private DraftCase draftCase = new DraftCase();
    private int isCreateNewCase = 0;
    private Handler handler = new Handler();
    private Runnable runnable;
    private String defautSchemeDraft="", defautAddressDraft = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_case_activity);
        mService = ApiUtils.getSOService();
        p = new Pref(CreateNewCaseActivity.this);
        databaseHelper = new DatabaseHelper(CreateNewCaseActivity.this);
        if (getIntent().hasExtra(AppContants.IS_NEW_CASE)) {
            isCreateNewCase = getIntent().getIntExtra(AppContants.IS_NEW_CASE,0);
        } else {
        }

        caseadd_FABupload = (LinearLayout)findViewById(R.id.caseadd_FABupload);
        caseaddDrap_FAB = (LinearLayout)findViewById(R.id.caseaddDrap_FAB);
        progressLoading_not_invite = (ProgressBar)findViewById(R.id.progressLoading_not_invite);
        caseadd_termloan = (com.isapanah.awesomespinner.AwesomeSpinner)findViewById(R.id.caseadd_termloan);
        caseadd_scheme = (com.isapanah.awesomespinner.AwesomeSpinner)findViewById(R.id.caseadd_scheme);
        caseadd_places = (com.isapanah.awesomespinner.AwesomeSpinner)findViewById(R.id.caseadd_places);

        caseadd_fullname = (EditText)findViewById(R.id.caseadd_fullname);
        caseadd_fullname.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        caseadd_cmnd = (EditText)findViewById(R.id.caseadd_cmnd);
        caseadd_money = (EditText) findViewById(R.id.caseadd_money);
        Edittext.addCommasToEdittext(caseadd_money);
        dp_date_cmnd = (EditText)findViewById(R.id.dp_date_cmnd);
        caseadd_error_tv_fullname = (TextView)findViewById(R.id.caseadd_error_tv_fullname);
        caseadd_error_tv_cmnd = (TextView)findViewById(R.id.caseadd_error_tv_cmnd);
        caseadd_error_tv_places = (TextView)findViewById(R.id.caseadd_error_tv_places);
        caseadd_error_tv_cmnd_date = (TextView)findViewById(R.id.caseadd_error_tv_cmnd_date);
        caseadd_error_tv_money = (TextView)findViewById(R.id.caseadd_error_tv_money);
        caseadd_error_tv_documment_type = (TextView)findViewById(R.id.caseadd_error_tv_documment_type);
        caseadd_error_tv_tax_code = (TextView)findViewById(R.id.caseadd_error_tv_tax_code);
        caseadd_error_tv_term_loan = (TextView)findViewById(R.id.caseadd_error_tv_term_loan);
        caseadd_error_tv_scheme = (TextView)findViewById(R.id.caseadd_error_tv_scheme);
        radio_group = (RadioGroup)findViewById(R.id.radio_group);
        radio_groupShk = (RadioGroup)findViewById(R.id.radio_groupShk);
        radioYes = (RadioButton)findViewById(R.id.radioYes);
        radioNo = (RadioButton)findViewById(R.id.radioNo);
        radioYesShk = (RadioButton)findViewById(R.id.radioYesShk);
        radioNoShk = (RadioButton)findViewById(R.id.radioNoShk);
        txt_office_address = (TextView)findViewById(R.id.txt_office_address);
        btnBack = (ImageView)findViewById(R.id.btnBack);
        btn_check_cat =(Button)findViewById(R.id.btn_check_cat);
        layout_company = (LinearLayout)findViewById(R.id.layout_company);
        caseadd_error_ll_cat = (LinearLayout)findViewById(R.id.caseadd_error_ll_cat);
        txt_title_company = (TextView)findViewById(R.id.txt_title_company);
        txt_title_cat_info = (TextView)findViewById(R.id.txt_title_cat_info);
        txt_tax_code = (EditText)findViewById(R.id.txt_tax_code);
        error_address_office = (TextView)findViewById(R.id.caseadd_error_tv_address_office);
        layout_tax_code = (LinearLayout)findViewById(R.id.layout_tax_code);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radioYes:
                        mobileHasIn = "1";
                        break;
                    case R.id.radioNo:
                        mobileHasIn = "0";
                        break;
                }
            }
        });

        radio_groupShk.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radioYesShk:
                       mobileTem = "1";
                       mobileTempLabel = getString(R.string.yes);
                        break;
                    case R.id.radioNoShk:
                        mobileTem = "2";
                        mobileTempLabel = getString(R.string.no);
                        break;
                }
            }
        });
         authHeader = p.getString(AppContants.AUTHEN_LOGIN,"");
         if(p.getString(AppContants.EMEI_LOGIN,"") != null){
             IMEI_Number_Holder= p.getString(AppContants.EMEI_LOGIN,"");
         }

         token= p.getString(AppContants.TOKEN_LOGIN,"");
        jsonInfoUser.addProperty("imei",IMEI_Number_Holder.toString());
        jsonInfoUser.addProperty("token",token.toString());
        if(isCreateNewCase == 1){
            draftCase = (DraftCase) getIntent().getSerializableExtra(AppContants.UPDATE_DRAFT_CASE);
            jsonInfoUser.addProperty("mobileAppCode",draftCase.getMobileAppCode().toString());
            getDraftCaseInfo(authHeader,jsonInfoUser);
        }else {
            getNewCaseInfo(authHeader,jsonInfoUser);
        }

        caseadd_FABupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogCall.showConfirmDialog(CreateNewCaseActivity.this,getString(R.string.confirm_create_Case),new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int id = v.getId();
                        if (id == R.id.btn_ok) {
                            if(validateSuccess()) {
                                createNewCase(authHeader, infoCreateJson());
                            }
                        }
                        else if (id == R.id.btn_cancel) {
                        }
                    }
                });

            }
        });
        caseaddDrap_FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogCall.showConfirmDialog(CreateNewCaseActivity.this,getString(R.string.confirm_save_draft),new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int id = v.getId();
                        if (id == R.id.btn_ok) {
                            //addNewDraftCase();
                        }
                        else if (id == R.id.btn_cancel) {
                        }
                    }
                });

            }
        });
        dp_date_cmnd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action){
                    case MotionEvent.ACTION_DOWN:
                        // calender class's instance and get current date , month and year from calender
                        final Calendar c = Calendar.getInstance();
                        int mYear = c.get(Calendar.YEAR); // current year
                        int mMonth = c.get(Calendar.MONTH); // current month
                        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                        // date picker dialog
                        datePickerDialog = new DatePickerDialog(CreateNewCaseActivity.this,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {
                                        // set day of month , month and year value in the edit text
                                        dp_date_cmnd.setText(dayOfMonth + "/"
                                                + (monthOfYear + 1) + "/" + year);
                                        validateDateCMND();

                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.setCancelable(false);
                        datePickerDialog.setCanceledOnTouchOutside(false);
                        datePickerDialog.show();
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }

                return false;
            }
        });
        placesAdapter= new ArrayAdapter<String>(CreateNewCaseActivity.this,android.R.layout.simple_spinner_item, placesList);
        placesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        schemeAdapter= new ArrayAdapter<String>(CreateNewCaseActivity.this,android.R.layout.simple_spinner_item, schemenList);
        schemeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        termLoanAdapter= new ArrayAdapter<String>(CreateNewCaseActivity.this,android.R.layout.simple_spinner_item, termloanList);
        termLoanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        caseadd_places.setAdapter(placesAdapter);
        caseadd_scheme.setAdapter(schemeAdapter);

        caseadd_scheme.setOnSpinnerItemClickListener(new AwesomeSpinner.onSpinnerItemClickListener<String>() {
            @Override
            public void onItemSelected(int position, String s) {
                mobileSchemeCode = schemenListCode.get(position).toString();
                mobileSchemeName = schemenList.get(position).toString();

                for(int i = 0;i<configArrayList.get(position).size();i++){
                    if(configArrayList.get(position).get(i).getName().equals(getString(R.string.loanAmountAfterInsurrance))){
                        minValue = configArrayList.get(position).get(i).getMinValue();
                        maxValue = configArrayList.get(position).get(i).getMaxValue();
                    }else if(configArrayList.get(position).get(i).getName().equals(getString(R.string.loanTenor))){
                        minMonth = configArrayList.get(position).get(i).getMinValue();
                        maxMonth = configArrayList.get(position).get(i).getMaxValue();
                    }
                }
                termloanList.clear();
                mobileTenor = minMonth+"";
                for(int i = minMonth;i<= maxMonth;i+=3){
                    termloanList.add(i + " " + getString(R.string.month));
                }
                termLoanAdapter.notifyDataSetChanged();
                caseadd_error_tv_money.setText(getString(R.string.money_between) + " "+ Edittext.convertTextToCommas(minValue+"") + getString(R.string.vnd)+ " - "+ Edittext.convertTextToCommas(maxValue+"") + getString(R.string.vnd));
                if(isCreateNewCase == 1){
                    for (int i = 0;i<termloanList.size();i++){
                        if(termloanList.get(i).equals(draftCase.getTermloan()+ " " + getString(R.string.month))){
                            caseadd_termloan.setSelection(i);
                        }
                    }
                    String money = caseadd_money.getText().toString().replaceAll(formatText, "");
                    if(money.length()>0){
                        if( Long.parseLong(money) < minValue
                                || Long.parseLong(money) > maxValue){
                            caseadd_error_tv_money.setVisibility(View.VISIBLE);
                        }else {
                            caseadd_error_tv_money.setVisibility(View.GONE);
                        }
                    }else {
                        caseadd_error_tv_money.setVisibility(View.VISIBLE);
                    }
                    if(caseadd_money.getText().toString().length()>0){
                        if( Long.parseLong(caseadd_money.getText().toString().replaceAll(formatText, "")) < minValue
                                || Long.parseLong(caseadd_money.getText().toString().replaceAll(formatText, "")) > maxValue){
                            caseadd_error_tv_money.setVisibility(View.VISIBLE);
                        }else {
                            caseadd_error_tv_money.setVisibility(View.GONE);
                        }
                    }
                }

                if(mobileSchemeCode.equals("C0000003")||mobileSchemeCode.equals("C0000004")||mobileSchemeCode.equals("C0000011")){
                    btn_check_cat.setVisibility(View.VISIBLE);
                    layout_tax_code.setVisibility(View.VISIBLE);
                    layout_company.setVisibility(View.VISIBLE);
                }else {
                    btn_check_cat.setVisibility(View.GONE);
                    layout_tax_code.setVisibility(View.GONE);
                    layout_company.setVisibility(View.GONE);
                    txt_title_company.setText("");
                    txt_title_cat_info.setText("");
                    cat_type_company = "";
                    txt_tax_code.setText("");
                    caseadd_error_tv_tax_code.setVisibility(View.GONE);
                    caseadd_error_ll_cat.setVisibility(View.GONE);
                }
            }
        });

        caseadd_termloan.setAdapter(termLoanAdapter);
//        caseadd_scheme.setSelection(0);
//        caseadd_places.setSelection(0);
//        caseadd_termloan.setSelection(0);
        caseadd_places.setOnSpinnerItemClickListener(new AwesomeSpinner.onSpinnerItemClickListener<String>() {
            @Override
            public void onItemSelected(int position, String s) {
                mobilePlace = placesListCode.get(position).toString();
//                mobilePlace=caseadd_places.getSelectedItem().toString();
                txt_office_address.setText(placesListOffice.get(position).trim());
                shopCode = placesListCode.get(position).trim();
            }
        });
        caseadd_termloan.setOnSpinnerItemClickListener(new AwesomeSpinner.onSpinnerItemClickListener<String>() {
            @Override
            public void onItemSelected(int position, String s) {
                mobileTenor = s.replace(" "+getString(R.string.month),"");
            }
        });

        caseadd_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(caseadd_money.getText().toString().length()>0){
                    if( Long.parseLong(caseadd_money.getText().toString().replaceAll(formatText, "")) < minValue
                            || Long.parseLong(caseadd_money.getText().toString().replaceAll(formatText, "")) > maxValue){
                        caseadd_error_tv_money.setVisibility(View.VISIBLE);
                    }else {
                        caseadd_error_tv_money.setVisibility(View.GONE);
                    }
                }
            }
        });

        caseadd_cmnd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(caseadd_cmnd.getText().toString().length()>0){
                        if(caseadd_cmnd.getText().toString().length()==9||caseadd_cmnd.getText().toString().length()==12){
                            caseadd_error_tv_cmnd.setVisibility(View.GONE);
                        }else{
                            caseadd_error_tv_cmnd.setVisibility(View.VISIBLE);
                            caseadd_error_tv_cmnd.setText(getString(R.string.not_valid_cmnd));
                        }
                }
            }
        });

        btn_check_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String CompanyTaxNumber = txt_tax_code.getText().toString();
                boolean isClicked = false;
                if(!isClicked){
                    isClicked = true;
                    if(CompanyTaxNumber.length()>0){
                        caseadd_error_tv_tax_code.setVisibility(View.GONE);
                        getCatInfo(authHeader,CompanyTaxNumber);
                    }else {
                        caseadd_error_tv_tax_code.setVisibility(View.VISIBLE);
                    }
                    isClicked = false;
                }

            }
        });

        txt_tax_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                txt_title_company.setText("");
                txt_title_cat_info.setText("");
                cat_type_company = "";
            }
        });

    }

    private void addDataToForm(){

        caseadd_fullname.setText(draftCase.getFullname().toString());
        caseadd_cmnd.setText(draftCase.getCard().toString());

        defautSchemeDraft = draftCase.getDocument_type_code();
        defautAddressDraft = draftCase.getKiosk_code();
        for (int i = 0;i<schemenListCode.size();i++){
            if(schemenListCode.get(i).equals(draftCase.getDocument_type_code())){
                caseadd_scheme.setSelection(i);
            }
        }
        for (int i = 0;i<termloanList.size();i++){
            if(termloanList.get(i).equals(draftCase.getTermloan()+ " " + getString(R.string.month))){
                caseadd_termloan.setSelection(i);
            }
        }
        txt_tax_code.setText(draftCase.getTax_code());
        txt_title_company.setText(draftCase.getCompany_name());
        txt_title_cat_info.setText(draftCase.getCat_type());
        for (int i = 0;i<placesList.size();i++){
            if(placesListCode.get(i).equals(draftCase.getKiosk_code())){
                caseadd_places.setSelection(i);
            }
        }
        txt_office_address.setText(draftCase.getKiosk_address());
        if(draftCase.getInsurance().equals("1")){
            radioYes.setChecked(true);
            radioNo.setChecked(false);
        }else {
            radioYes.setChecked(false);
            radioNo.setChecked(true);
        }
        if(draftCase.getSignContractAddress().equals("1")){
            radioYesShk.setChecked(true);
            radioNoShk.setChecked(false);
        }else {
            radioYesShk.setChecked(false);
            radioNoShk.setChecked(true);
        }
        caseadd_money.setText(Edittext.convertTextToCommas(draftCase.getMoney().toString()));

        String longV = draftCase.getDate_card().toString();
        if(longV.length()>0){
            if(longV.contains("/")){
                dp_date_cmnd.setText(longV);
            }else {
                long millisecond = Long.parseLong(longV);
                // or you already have long value of date, use this instead of milliseconds variable.
                String dateString = DateFormat.format("dd/MM/yyyy", new Date(millisecond)).toString();
                dp_date_cmnd.setText(dateString);
            }
        }

        if(caseadd_money.getText().toString().length()>0){
            if( Long.parseLong(caseadd_money.getText().toString().replaceAll(formatText, "")) < minValue
                    || Long.parseLong(caseadd_money.getText().toString().replaceAll(formatText, "")) > maxValue){
                caseadd_error_tv_money.setVisibility(View.VISIBLE);
            }else {
                caseadd_error_tv_money.setVisibility(View.GONE);
            }
        }
        if(draftCase.getDocument_type_code().equals("C0000003")||draftCase.getDocument_type_code().equals("C0000004")||draftCase.getDocument_type_code().equals("C0000011")){
            btn_check_cat.setVisibility(View.VISIBLE);
            layout_tax_code.setVisibility(View.VISIBLE);
            layout_company.setVisibility(View.VISIBLE);
        }else {
            btn_check_cat.setVisibility(View.GONE);
            layout_tax_code.setVisibility(View.GONE);
            layout_company.setVisibility(View.GONE);
        }
        cat_type_company = draftCase.getCat_type();
    }

    private DraftCase addNewDraftCase(){
        mobileLoanAmount = caseadd_money.getText().toString().replaceAll(formatText, "");
        mobileCustomerName = caseadd_fullname.getText().toString();
        mobileIssueDate = dp_date_cmnd.getText().toString();
        mobileCitiID = caseadd_cmnd.getText().toString();
        if(radioYesShk.isChecked()){
            mobileTem = "1";
            mobileTempLabel = getString(R.string.yes);
        }else {
            mobileTem = "2";
            mobileTempLabel = getString(R.string.no);
        }
        if(radioYes.isChecked()){
            mobileHasIn = "1";
        }else {
            mobileHasIn = "0";
        }
        mobileCompanyTaxNumber = txt_tax_code.getText().toString();
        mobileCatResultCompanyName = txt_title_company.getText().toString();
        mobileCatResultType = txt_title_cat_info.getText().toString();

        addressOffice = txt_office_address.getText().toString();

        DraftCase draftCase = new DraftCase(mobileCustomerName,mobileCitiID,mobileIssueDate,mobileTenor,mobileSchemeName.toString(),
                mobileSchemeCode.toString(),addressOffice,shopCode.toString(),addressOffice,mobileCatResultType.toString(),mobileCatResultCompanyName.toString()
                ,mobileHasIn,mobileTem,mobileLoanAmount.toString(),mobileCompanyTaxNumber.toString(),mobileAppCode.toString(),"1",p.getString(AppContants.USERNAME_LOGIN,""));
//        if(isCreateNewCase == 1){
//            if(draftCase.isFromDevice.equals("1")){
//                if(databaseHelper.hasObject(mobileAppCode.toString())){
//                    databaseHelper.updateADraftCase(mobileCustomerName.toString(),mobileCitiID,mobileIssueDate,mobileTenor,mobileSchemeName.toString(),mobileSchemeCode.toString(),
//                            signContractAddress,shopCode.toString(),signContractAddress,mobileCatResultType.toString(),mobileCatResultCompanyName.toString(),mobileHasIn,mobileTem,
//                            mobileLoanAmount.toString(),mobileCompanyTaxNumber.toString(),mobileAppCode.toString(),"1");
//                }else {
//                    databaseHelper.insertADraftCase(mobileCustomerName.toString(),mobileCitiID,mobileIssueDate,mobileTenor,mobileSchemeName.toString(),mobileSchemeCode.toString(),
//                            signContractAddress,shopCode.toString(),signContractAddress,mobileCatResultType.toString(),mobileCatResultCompanyName.toString(),mobileHasIn,mobileTem,
//                            mobileLoanAmount.toString(),mobileCompanyTaxNumber.toString(),mobileAppCode.toString(),"1");
//                }
//            }else {
//                databaseHelper.insertADraftCase(mobileCustomerName.toString(),mobileCitiID,mobileIssueDate,mobileTenor,mobileSchemeName.toString(),mobileSchemeCode.toString(),
//                        signContractAddress,shopCode.toString(),signContractAddress,mobileCatResultType.toString(),mobileCatResultCompanyName.toString(),mobileHasIn,mobileTem,
//                        mobileLoanAmount.toString(),mobileCompanyTaxNumber.toString(),mobileAppCode.toString(),"1");
//            }
//        }else {
//            databaseHelper.insertADraftCase(mobileCustomerName.toString(),mobileCitiID,mobileIssueDate,mobileTenor,mobileSchemeName.toString(),mobileSchemeCode.toString(),
//                    signContractAddress,shopCode.toString(),signContractAddress,mobileCatResultType.toString(),mobileCatResultCompanyName.toString(),mobileHasIn,mobileTem,
//                    mobileLoanAmount.toString(),mobileCompanyTaxNumber.toString(),mobileAppCode.toString(),"1");
//        }
//        DialogCall.showWaringDialog(CreateNewCaseActivity.this,getString(R.string.save_draft_success),new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int id = v.getId();
//                if (id == R.id.btn_ok) {
//                    Intent i = new Intent(CreateNewCaseActivity.this,MainActivity.class);
//                    startActivity(i);
//                    finish();
//                }
//            }
//        });
        return draftCase;

    }
    public static String getFormatedCurrency(String value) {
        try {
            NumberFormat formatter = new DecimalFormat("##,##,##,###");
            return formatter.format(Double.parseDouble(value));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    private JsonObject infoCreateJson(){

        JsonObject jsonObject = new JsonObject();
        mobileLoanAmount = caseadd_money.getText().toString().replaceAll(formatText, "");
        mobileCustomerName = caseadd_fullname.getText().toString();
        mobileIssueDate = dp_date_cmnd.getText().toString();
        mobileCitiID = caseadd_cmnd.getText().toString();
        if(radioYesShk.isChecked()){
            mobileTem = "1";
            mobileTempLabel = getString(R.string.yes);
        }else {
            mobileTem = "2";
            mobileTempLabel = getString(R.string.no);
        }
        if(radioYes.isChecked()){
            mobileHasIn = "1";
        }else {
            mobileHasIn = "0";
        }
        mobileCompanyTaxNumber = txt_tax_code.getText().toString();
        mobileCatResultCompanyName = txt_title_company.getText().toString();
        mobileCatResultType = txt_title_cat_info.getText().toString();

        signContractAddress = txt_office_address.getText().toString();

        jsonObject.addProperty("mobileLoanAmount",mobileLoanAmount.toString());
        jsonObject.addProperty("mobileCustomerName",mobileCustomerName.toString());
        jsonObject.addProperty("mobileSchemaProductCode",mobileSchemeCode.toString());
        jsonObject.addProperty("mobileSchemaProductName",mobileSchemeName.toString());
        jsonObject.addProperty("mobileTemResidence",mobileTem.toString());
        jsonObject.addProperty("mobileTempResidenceLabel",mobileTempLabel.toString());
        jsonObject.addProperty("mobileIssueDateCitizen",mobileIssueDate.toString());
        jsonObject.addProperty("mobileCitizenId",mobileCitiID.toString());
        jsonObject.addProperty("mobileHasInsurrance",mobileHasIn.toString());
        jsonObject.addProperty("mobileIssuePlace",mobilePlace.toString());
        jsonObject.addProperty("mobileCode",mobileCode.toString());
        jsonObject.addProperty("mobileLoanTenor",mobileTenor.toString());
        jsonObject.addProperty("mobileAppCode",mobileAppCode.toString());
        jsonObject.addProperty("imei",IMEI_Number_Holder.toString());
        jsonObject.addProperty("token",tokenCreate.toString());
        jsonObject.addProperty("SaleName",saleName.toString());
        jsonObject.addProperty("SaleCode",saleCode.toString());
        jsonObject.addProperty("SaleMobile",saleMobile.toString());
        jsonObject.addProperty("SalePath",salePath.toString());
        jsonObject.addProperty("shopCode",shopCode.toString());
        jsonObject.addProperty("mobileCompanyTaxNumber",mobileCompanyTaxNumber.toString());
        jsonObject.addProperty("mobileCatResultCompanyName",mobileCatResultCompanyName.toString());
        jsonObject.addProperty("mobileCatResultType",mobileCatResultType.toString());
        jsonObject.addProperty("signContractAddress",signContractAddress.toString());
        return jsonObject;
    }

    private boolean validateTermLoan(){
        if(caseadd_termloan.getSelectedItem() != null){
            if(!caseadd_termloan.getSelectedItem().toString().contains(getString(R.string.month))){
                caseadd_error_tv_term_loan.setVisibility(View.VISIBLE);
                return false;
            }else {
                caseadd_error_tv_term_loan.setVisibility(View.GONE);
                return true;
            }
        }else {
            caseadd_error_tv_term_loan.setVisibility(View.GONE);
            return true;
        }

    }
    private boolean validateScheme(){
        if(mobileSchemeName.length()>0){
            caseadd_error_tv_scheme.setVisibility(View.GONE);
            return true;
        }else {
            caseadd_error_tv_scheme.setVisibility(View.VISIBLE);
            return false;
        }
    }
    private boolean validateCat(){
        if(cat_type_company.equals("CAT A")){
            if(mobileSchemeCode.equals("C0000003")||mobileSchemeCode.equals("C0000004")||mobileSchemeCode.equals("C0000011")){
                caseadd_error_tv_documment_type.setVisibility(View.GONE);
                return true;
            }else {
                caseadd_error_tv_documment_type.setVisibility(View.VISIBLE);
                return false;
            }
        }else  if(cat_type_company.equals("CAT B")){
            if(mobileSchemeCode.equals("C0000004")||mobileSchemeCode.equals("C0000011")){
                caseadd_error_tv_documment_type.setVisibility(View.GONE);
                return true;
            }else {
                caseadd_error_tv_documment_type.setVisibility(View.VISIBLE);
                return false;
            }
        }else  if(cat_type_company.equals("CAT C")){
            if(mobileSchemeCode.equals("C0000011")){
                caseadd_error_tv_documment_type.setVisibility(View.GONE);
                return true;
            }else {
                caseadd_error_tv_documment_type.setVisibility(View.VISIBLE);
                return false;
            }
        }else if(cat_type_company.contains(getString(R.string.not_cat))){
            if(mobileSchemeCode.equals("C0000003")||mobileSchemeCode.equals("C0000004")||mobileSchemeCode.equals("C0000011")){
                caseadd_error_tv_documment_type.setVisibility(View.VISIBLE);
                return false;
            }else {
                caseadd_error_tv_documment_type.setVisibility(View.GONE);
                return true;
            }
        } else {
            caseadd_error_tv_documment_type.setVisibility(View.GONE);
            return true;
        }
    }
    private boolean validateFullName(){
        if(caseadd_fullname.getText().toString().length()==0){
            caseadd_error_tv_fullname.setVisibility(View.VISIBLE);
            caseadd_fullname.setFocusable(true);
            return false;
        }else {
            caseadd_error_tv_fullname.setVisibility(View.GONE);
            return true;
        }
    };
    private boolean validateCMND(){
        if(caseadd_cmnd.getText().toString().length()==0){
            caseadd_error_tv_cmnd.setVisibility(View.VISIBLE);
            caseadd_cmnd.setFocusable(true);
            return false;
        }else {
            if(caseadd_cmnd.getText().toString().length()==9||caseadd_cmnd.getText().toString().length()==12){
                caseadd_error_tv_cmnd.setVisibility(View.GONE);
                return true;
            }else{
                caseadd_error_tv_cmnd.setVisibility(View.VISIBLE);
                caseadd_error_tv_cmnd.setText(getString(R.string.not_valid_cmnd));
                caseadd_cmnd.setFocusable(true);
                return false;
            }

        }
    };
    private boolean validateDateCMND(){
        if(dp_date_cmnd.getText().toString().length()==0){
            caseadd_error_tv_cmnd_date.setVisibility(View.VISIBLE);
            return false;
        }else {
            try {
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy");
                Date dateCheck = null;

                dateCheck = sdf.parse(dp_date_cmnd.getText().toString());
                if(dateCheck.after(date)){
                    caseadd_error_tv_cmnd_date.setVisibility(View.VISIBLE);
                    caseadd_error_tv_cmnd_date.setText(getString(R.string.date_not_real));
                    return false;
                }else {
                    caseadd_error_tv_cmnd_date.setVisibility(View.GONE);
                    return true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
                caseadd_error_tv_cmnd_date.setText(getString(R.string.date_not_real));
                return false;
            }
        }
    };
    private boolean validateMoney(){
        String money = caseadd_money.getText().toString().replaceAll(formatText, "");
        if(money.length()>0){
            if( Long.parseLong(money) < minValue
                    || Long.parseLong(money) > maxValue){
                caseadd_error_tv_money.setVisibility(View.VISIBLE);
                return false;
            }else {
                caseadd_error_tv_money.setVisibility(View.GONE);
                return true;
            }
        }else {
            caseadd_error_tv_money.setVisibility(View.VISIBLE);
            return false;
        }
    };
    private boolean validatePlaces(){
        if(caseadd_places.getSelectedItem() != null){
            if(caseadd_places.getSelectedItem().toString().length()==0){
                caseadd_error_tv_places.setVisibility(View.VISIBLE);
                return false;
            }else {
                caseadd_error_tv_places.setVisibility(View.GONE);
                return true;
            }
        }else {
            caseadd_error_tv_places.setVisibility(View.VISIBLE);
            return false;
        }

    }
    private boolean validateCheckCat(){
        if(btn_check_cat.getVisibility() == View.VISIBLE){
            if(txt_tax_code.getText().toString().length()==0){
                caseadd_error_tv_tax_code.setVisibility(View.VISIBLE);
                txt_tax_code.setFocusable(true);
            }else {
                caseadd_error_tv_tax_code.setVisibility(View.GONE);
            }
            if(txt_title_cat_info.getText().toString().length()==0){
                caseadd_error_ll_cat.setVisibility(View.VISIBLE);
                txt_tax_code.setFocusable(true);
                return false;
            }else {
                caseadd_error_ll_cat.setVisibility(View.GONE);
                return true;
            }
        }else {
            return true;
        }

    }
    private boolean validateOfficePlaces(){
        if(txt_office_address.getText().toString().length()==0){
            error_address_office.setVisibility(View.VISIBLE);
            return false;
        }else {
            error_address_office.setVisibility(View.GONE);
            return true;
        }
    }

    private boolean validateSuccess(){
        validateMoney();
        validatePlaces();
        validateScheme();
        validateCat();
        validateCheckCat();
        validateTermLoan();
        validateCMND();
        validateFullName();
        validateOfficePlaces();
        if(validateMoney()&&validatePlaces()&&validateScheme()&&validateCat()&&validateCheckCat()&&validateTermLoan()&&validateCMND()&&validateDateCMND()&&validateFullName()&&validateOfficePlaces()){
            return true;
        }else {
            return false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void createNewCase(final String authHeader, final JsonObject infoUser){
            infoCreateJson();
            progressLoading_not_invite.setVisibility(View.VISIBLE);
            mService.createNewCase(infoUser,authHeader,"application/json").enqueue(new Callback<ResultCreateNewCase>() {
                @Override
                public void onResponse(Call<ResultCreateNewCase> call, Response<ResultCreateNewCase> response) {

//                if(response.isSuccessful()) {
                    if(response.body() != null){
                    if(response.body().getResult()!=null) {
                        if (response.body().getResult().getHttpCode() == 200) {
                            if (response.body().getResult() != null) {
                                p.putString(AppContants.TOKEN_LOGIN, response.body().getResult().getToken());
                                tokenCreate = response.body().getResult().getToken();
                                token = response.body().getResult().getToken();
                            }
                            if (isCreateNewCase == 1) {
//                            if(draftCase.getIsFromDevice().equals("1")){
//                                databaseHelper.deleteADraftCaseByMobileAppCode(draftCase.getMobileAppCode());
//                            }
                                //databaseHelper.deleteADraftCaseByMobileAppCode(draftCase.getMobileAppCode());
                                defautSchemeDraft = draftCase.getDocument_type_code();
                                defautAddressDraft = draftCase.getSignContractAddress();
                                String scheme = schemenListCode.get(caseadd_scheme.getSelectedItemPosition()).toString();
                                String address = placesListCode.get(caseadd_places.getSelectedItemPosition()).toString();
                                if (!defautSchemeDraft.equals(scheme) || !defautAddressDraft.equals(mobileTem)) {
                                    databaseHelper.deleteADraftImageByMobileAppCode(draftCase.getMobileAppCode());
                                }
                            }
                            Intent i = new Intent(CreateNewCaseActivity.this, UploadFileActivity.class);
                            i.putExtra(AppContants.CREATE_NEW_CASE, response.body());
                            i.putExtra(AppContants.DATA_DRAFT_CASE, addNewDraftCase());
                            i.putExtra(AppContants.isCreateNewCase, isCreateNewCase);
                            startActivity(i);
//                        finish();
                            progressLoading_not_invite.setVisibility(View.GONE);

                        } else {
                            String message = "";
                            if (response.body().getResult() != null) {
                                message = response.body().getResult().getHttpMessage();
                            } else {
                                message = response.code() + "";
                            }
                            DialogCall.showWaringDialog(CreateNewCaseActivity.this, message, new View.OnClickListener() {
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
//                }else {
//                    int statusCode  = response.code();
//                    progressLoading_not_invite.setVisibility(View.GONE);
//                }
                }}

                @Override
                public void onFailure(Call<ResultCreateNewCase> call, Throwable t) {
                    progressLoading_not_invite.setVisibility(View.GONE);
                    DialogCall.showWaringDialog(CreateNewCaseActivity.this, getString(R.string.cannot_connect_server), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }
            });
    }

private void getCatInfo(String authHeader,String CompanyTaxNumber){
    progressLoading_not_invite.setVisibility(View.VISIBLE);
    mService.getCatInfo(authHeader,CompanyTaxNumber.replaceAll(" ","")).enqueue(new Callback<GetCatInfo>() {
        @Override
        public void onResponse(Call<GetCatInfo> call, Response<GetCatInfo> response) {
            if(response.body() != null) {
                if (response.body().getResult() != null) {
                    if (response.body().getResult().getRes().getCheckCompanyCatResponse() != null) {
                        txt_title_company.setText(response.body().getResult().getRes().getCheckCompanyCatResponse().getCompanyName().toString());
                        txt_title_cat_info.setText(response.body().getResult().getRes().getCheckCompanyCatResponse().getCatType().toString());
                        cat_type_company = response.body().getResult().getRes().getCheckCompanyCatResponse().getCatType().toString();
                        layout_company.setVisibility(View.VISIBLE);
                        progressLoading_not_invite.setVisibility(View.GONE);
                    } else {
                        progressLoading_not_invite.setVisibility(View.GONE);
                        txt_title_company.setText("");
                        txt_title_cat_info.setText("");
                        cat_type_company = "";
                        String message = "";
                        if (response.body().getResult().getRes().getError() != null) {
                            message = response.body().getResult().getRes().getError().getMessage();
                        }
                        DialogCall.showWaringDialog(CreateNewCaseActivity.this, message, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                    }
                }
            }
//                }else {
//                    int statusCode  = response.code();
//                    progressLoading_not_invite.setVisibility(View.GONE);
//                }
        }

        @Override
        public void onFailure(Call<GetCatInfo> call, Throwable t) {
            progressLoading_not_invite.setVisibility(View.GONE);
            DialogCall.showWaringDialog(CreateNewCaseActivity.this, getString(R.string.cannot_connect_server), new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    });
}
    private void getNewCaseInfo(final String authHeader, final JsonObject infoUser){
        AppContants.AUTHEN = authHeader;
        mService.getNewcaseInfo(infoUser,authHeader,"application/json").enqueue(new Callback<ResultInfoNewCase>() {
            @Override
            public void onResponse(Call<ResultInfoNewCase> call, Response<ResultInfoNewCase> response) {

//                if(response.isSuccessful()) {
                if(response.body() != null) {
                    if (response.body().getResult() != null) {
                        if (response.body().getResult().getHttpCode() == 200) {
                            if (response.body().getResult() != null) {
                                p.putString(AppContants.TOKEN_LOGIN, response.body().getResult().getToken());
                            }
                            String placeOffice = "";
                            for (int i = 0; i < response.body().getResult().getProductArr().size(); i++) {
                                schemenList.add(response.body().getResult().getProductArr().get(i).getProductName());
                                schemenListCode.add(response.body().getResult().getProductArr().get(i).getProductCode());
                                configArrayList.add(response.body().getResult().getProductArr().get(i).getConfigs());
                            }
                            if (response.body().getResult().getInfoStaff().getHubKiosk() != null) {
                                for (int i = 0; i < response.body().getResult().getInfoStaff().getHubKiosk().size(); i++) {
//                            if(i<(response.body().getResult().getInfoStaff().getHubKiosk().size() - 1)){
//                                if(response.body().getResult().getInfoStaff().getHubKiosk().get(i).getDepartmentName().equals
//                                        (response.body().getResult().getInfoStaff().getHubKiosk().get(i+1).getDepartmentName())){
//                                    placeOffice = placeOffice + "\n"+response.body().getResult().getInfoStaff().getHubKiosk().get(i).getAddress();
//                                }else {
//                                    placeOffice = placeOffice + "\n"+response.body().getResult().getInfoStaff().getHubKiosk().get(i).getAddress();
//                                    placesListOffice.add(placeOffice);
//                                    placesList.add(response.body().getResult().getInfoStaff().getHubKiosk().get(i).getDepartmentName());
//                                    placesListCode.add(response.body().getResult().getInfoStaff().getHubKiosk().get(i).getDepartmentCode());
//                                    placeOffice = "";
//                                }
//
//                            }else {
//                                placeOffice = response.body().getResult().getInfoStaff().getHubKiosk().get(i).getAddress();
//                                if(response.body().getResult().getInfoStaff().getHubKiosk().get(i).getDepartmentName().equals
//                                        (response.body().getResult().getInfoStaff().getHubKiosk().get(i-1).getDepartmentName())){
//                                    placeOffice = placeOffice + "\n"+response.body().getResult().getInfoStaff().getHubKiosk().get(i).getAddress();
//                                    placesListOffice.add(placeOffice);
//                                    placesList.add(response.body().getResult().getInfoStaff().getHubKiosk().get(i).getDepartmentName());
//                                    placesListCode.add(response.body().getResult().getInfoStaff().getHubKiosk().get(i).getDepartmentCode());
//                                }else {
//                                    placesListOffice.add(response.body().getResult().getInfoStaff().getHubKiosk().get(i).getAddress());
//                                    placesList.add(response.body().getResult().getInfoStaff().getHubKiosk().get(i).getDepartmentName());
//                                    placesListCode.add(response.body().getResult().getInfoStaff().getHubKiosk().get(i).getDepartmentCode());
//                                }
//                            }
                                    placesListOffice.add(response.body().getResult().getInfoStaff().getHubKiosk().get(i).getAddress());
                                    placesList.add(response.body().getResult().getInfoStaff().getHubKiosk().get(i).getDepartmentCode() + " "
                                            + response.body().getResult().getInfoStaff().getHubKiosk().get(i).getProvinceName());
                                    placesListCode.add(response.body().getResult().getInfoStaff().getHubKiosk().get(i).getDepartmentCode());
                                }
                            }

                            mobileAppCode = response.body().getResult().getInfoCase().getMobileAppCode();
                            tokenCreate = response.body().getResult().getToken();
                            schemeAdapter.notifyDataSetChanged();
                            placesAdapter.notifyDataSetChanged();
                            progressLoading_not_invite.setVisibility(View.GONE);
                            saleName = response.body().getResult().getInfoStaff().getName();
                            saleCode = response.body().getResult().getInfoStaff().getStaffCode();
                            saleMobile = response.body().getResult().getInfoStaff().getMobilePhone();
                            salePath = response.body().getResult().getInfoStaff().getDepartmentPath();

                        } else {
                            progressLoading_not_invite.setVisibility(View.GONE);
                            String message = "";
                            if (response.body().getResult() != null) {
                                message = response.body().getResult().getHttpMessage();
                            }
                            DialogCall.showWaringDialog(CreateNewCaseActivity.this, message, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                        }
                    }
                }
//                }else {
//                    int statusCode  = response.code();
//                    progressLoading_not_invite.setVisibility(View.GONE);
//                }
            }

            @Override
            public void onFailure(Call<ResultInfoNewCase> call, Throwable t) {
                progressLoading_not_invite.setVisibility(View.GONE);
                DialogCall.showWaringDialog(CreateNewCaseActivity.this, getString(R.string.cannot_connect_server), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });
    }
    private void getDraftCaseInfo(final String authHeader, final JsonObject infoUser){
        AppContants.AUTHEN = authHeader;
        mService.getDraftcaseInfo(infoUser,authHeader,"application/json").enqueue(new Callback<ResultInfoNewCase>() {
            @Override
            public void onResponse(Call<ResultInfoNewCase> call, Response<ResultInfoNewCase> response) {

//                if(response.isSuccessful()) {
                if(response.body() != null) {
                    if (response.body().getResult() != null) {
                        if (response.body().getResult().getHttpCode() == 200) {
                            if (response.body().getResult() != null) {
                                p.putString(AppContants.TOKEN_LOGIN, response.body().getResult().getToken());
                            }
                            String placeOffice = "";
                            if (response.body().getResult().getProductArr() != null) {
                                for (int i = 0; i < response.body().getResult().getProductArr().size(); i++) {
                                    schemenList.add(response.body().getResult().getProductArr().get(i).getProductName());
                                    schemenListCode.add(response.body().getResult().getProductArr().get(i).getProductCode());
                                    configArrayList.add(response.body().getResult().getProductArr().get(i).getConfigs());
                                }
                            }

                            if (response.body().getResult().getInfoStaff().getHubKiosk() != null) {
                                for (int i = 0; i < response.body().getResult().getInfoStaff().getHubKiosk().size(); i++) {
//                            if(i<(response.body().getResult().getInfoStaff().getHubKiosk().size() - 1)){
//                                if(response.body().getResult().getInfoStaff().getHubKiosk().get(i).getDepartmentName().equals
//                                        (response.body().getResult().getInfoStaff().getHubKiosk().get(i+1).getDepartmentName())){
//                                    placeOffice = placeOffice + "\n"+response.body().getResult().getInfoStaff().getHubKiosk().get(i).getAddress();
//                                }else {
//                                    placeOffice = placeOffice + "\n"+response.body().getResult().getInfoStaff().getHubKiosk().get(i).getAddress();
//                                    placesListOffice.add(placeOffice);
//                                    placesList.add(response.body().getResult().getInfoStaff().getHubKiosk().get(i).getDepartmentName());
//                                    placesListCode.add(response.body().getResult().getInfoStaff().getHubKiosk().get(i).getDepartmentCode());
//                                    placeOffice = "";
//                                }
//
//                            }else {
//                                placeOffice = response.body().getResult().getInfoStaff().getHubKiosk().get(i).getAddress();
//                                if(response.body().getResult().getInfoStaff().getHubKiosk().get(i).getDepartmentName().equals
//                                        (response.body().getResult().getInfoStaff().getHubKiosk().get(i-1).getDepartmentName())){
//                                    placeOffice = placeOffice + "\n"+response.body().getResult().getInfoStaff().getHubKiosk().get(i).getAddress();
//                                    placesListOffice.add(placeOffice);
//                                    placesList.add(response.body().getResult().getInfoStaff().getHubKiosk().get(i).getDepartmentName());
//                                    placesListCode.add(response.body().getResult().getInfoStaff().getHubKiosk().get(i).getDepartmentCode());
//                                }else {
//                                    placesListOffice.add(response.body().getResult().getInfoStaff().getHubKiosk().get(i).getAddress());
//                                    placesList.add(response.body().getResult().getInfoStaff().getHubKiosk().get(i).getDepartmentName());
//                                    placesListCode.add(response.body().getResult().getInfoStaff().getHubKiosk().get(i).getDepartmentCode());
//                                }
//                            }
                                    placesListOffice.add(response.body().getResult().getInfoStaff().getHubKiosk().get(i).getAddress());
                                    placesList.add(response.body().getResult().getInfoStaff().getHubKiosk().get(i).getDepartmentCode() + " "
                                            + response.body().getResult().getInfoStaff().getHubKiosk().get(i).getProvinceName());
                                    placesListCode.add(response.body().getResult().getInfoStaff().getHubKiosk().get(i).getDepartmentCode());
                                }
                            }

                            mobileAppCode = response.body().getResult().getInfoCase().getMobileAppCode();
                            tokenCreate = response.body().getResult().getToken();
                            schemeAdapter.notifyDataSetChanged();
                            placesAdapter.notifyDataSetChanged();
                            progressLoading_not_invite.setVisibility(View.GONE);
                            saleName = response.body().getResult().getInfoStaff().getName();
                            saleCode = response.body().getResult().getInfoStaff().getStaffCode();
                            saleMobile = response.body().getResult().getInfoStaff().getMobilePhone();
                            salePath = response.body().getResult().getInfoStaff().getDepartmentPath();
                            runnable =
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            if (isCreateNewCase == 1) {
                                                addDataToForm();
                                            }
                                        }
                                    };

                            handler.postDelayed(runnable, 500);

                        } else {
                            progressLoading_not_invite.setVisibility(View.GONE);
                            String message = "";
                            if (response.body().getResult() != null) {
                                message = response.body().getResult().getHttpMessage();
                            }
                            DialogCall.showWaringDialog(CreateNewCaseActivity.this, message, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                        }
                    }
                }
//                }else {
//                    int statusCode  = response.code();
//                    progressLoading_not_invite.setVisibility(View.GONE);
//                }
            }

            @Override
            public void onFailure(Call<ResultInfoNewCase> call, Throwable t) {
                progressLoading_not_invite.setVisibility(View.GONE);
                DialogCall.showWaringDialog(CreateNewCaseActivity.this, getString(R.string.cannot_connect_server), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });
    }
}
