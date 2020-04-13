package com.mcredit.mobile.mobile_for_sale.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.darsh.multipleimageselect.activities.AlbumSelectActivity;
import com.darsh.multipleimageselect.helpers.Constants;
import com.darsh.multipleimageselect.models.Image;
import com.mcredit.mobile.mobile_for_sale.Adapter.ImageUploadAdapter;
import com.mcredit.mobile.mobile_for_sale.Adapter.ItemPopupSelectedAdapter;
import com.mcredit.mobile.mobile_for_sale.Api.Service.SOService;
import com.mcredit.mobile.mobile_for_sale.Api.Utils.ApiUtils;
import com.mcredit.mobile.mobile_for_sale.Base.PermissionActivity;
import com.mcredit.mobile.mobile_for_sale.Contants.AppContants;
import com.mcredit.mobile.mobile_for_sale.Database.DatabaseHelper;
import com.mcredit.mobile.mobile_for_sale.Dialog.DialogCall;
import com.mcredit.mobile.mobile_for_sale.Models.CreateNewCase.ResultCreateNewCase;
import com.mcredit.mobile.mobile_for_sale.Models.Database.DraftCase;
import com.mcredit.mobile.mobile_for_sale.Models.Database.DraftImage;
import com.mcredit.mobile.mobile_for_sale.Models.ImageUpload.ImageUpload;
import com.mcredit.mobile.mobile_for_sale.Models.PopUpSelect.Item;
import com.mcredit.mobile.mobile_for_sale.Models.UploadFile.UploadResponse;
import com.mcredit.mobile.mobile_for_sale.R;
import com.mcredit.mobile.mobile_for_sale.Utils.ItemOffsetDecoration;
import com.mcredit.mobile.mobile_for_sale.Utils.PhotoPicker;
import com.mcredit.mobile.mobile_for_sale.Utils.Pref;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hiephunbie on 3/19/18.
 */

public class UploadFileActivity extends PermissionActivity implements UploadStatusDelegate{

    public static PhotoPicker mPhotoPicker;
    private LinearLayout caseadd_FABselect,caseadd_FABcapture,caseadd_FABupload,caseadd_drap;
    private SOService mService;
    private Spinner caseadd_document_type;
    private ResultCreateNewCase resultCreateNewCase = new ResultCreateNewCase();
    private DraftCase draftCaseData = new DraftCase();
    private ArrayList<String> checkListArr = new ArrayList<String>();
    private ArrayList <Item> itemList = new ArrayList<Item>();
    private ArrayList<String> schemeCodeList = new ArrayList<String>();
    private RelativeLayout caseadd_FABdelete;
    private RecyclerView mRecyclerView;
    private ImageUploadAdapter mAdapter;
    private ProgressBar progressLoading,progressLoading_not_invite;
    private ArrayList<ImageUpload> imageUploadArrayList = new ArrayList<ImageUpload>();
    private ArrayList<ImageUpload> imageZipFilesArrayList = new ArrayList<ImageUpload>();
    private ArrayList<ImageUpload> imageUploadSelectedArrayList = new ArrayList<ImageUpload>();
    private ArrayList<String> termLoadCodeList = new ArrayList<String>();
    private ArrayList<String> variableMappingList = new ArrayList<String>();
    private String termLoadCode = "";
    private String variableMapping = "";
    private String termLoadNameSelected = "";
    private ArrayList<String> termLoadNameSelectedList = new ArrayList<String>();
    private ArrayList<String> termLoadCodeSelectedList = new ArrayList<String>();
    private ArrayAdapter<String> schemeAdapter ;
    private ImageView btnBack;
    private String imei;
    private Handler handler = new Handler();
    private Runnable runnable;
    private ArrayList<String> arrDocumentValid = new ArrayList<String>();
    private ArrayList<String> arrDocumentGroup = new ArrayList<String>();
    private ArrayList<String> arrDocumentValidChecked = new ArrayList<String>();
    private String documentValidChecked = "";
    private ArrayList<String> fileUpload = new ArrayList<String>();
    private LinearLayout caseadd_error_ll_image;
    private TextView caseadd_error_tv_image_total,txtErrorImage;
    private String zipPath ="";
    private boolean isChangeImage = false;
    private int posImageChange = 0;
    private boolean isCapture = false;
    private static final int CAMERA_REQUEST = 1888;
    String imageFilePath;
    private static final int REQUEST_CAPTURE_IMAGE = 100;
    private String mobileLoanAmount= "", mobileCustomerName= "", mobileSchemeCode= "", mobileSchemeName= "", mobileTem= "", mobileTempLabel= "", mobileIssueDate = "";
    private String mobileCitiID= "",mobileHasIn= "", mobilePlace= "ABC", mobileCode= "", mobileTenor= "", mobileAppCode= "",IMEI_Number_Holder= "",token= "",authHeader = "",tokenCreate="";
    private String saleCode="",saleName="",saleMobile="",salePath="",shopCode="";
    private String mobileCompanyTaxNumber ="",mobileCatResultCompanyName ="",mobileCatResultType ="",signContractAddress="",signContractAt="";
    private String formatText = "[-\\[\\]^/,'*:.!><~@#$%+=?|\"\\\\()]+";
    private int isCreateNewCase = 0;
    public DatabaseHelper databaseHelper;
    private ArrayList<DraftImage> draftImagesList = new ArrayList<DraftImage>();
    private File file;
    private File destFile;
    private ArrayList<String> listImagePathCompress = new ArrayList<String>();
    private int item_document_selected = 0;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_file_activity);
        p = new Pref(UploadFileActivity.this);
        databaseHelper = new DatabaseHelper(UploadFileActivity.this);
        imei = p.getString(AppContants.EMEI_LOGIN,"");
        file = new File(Environment.getExternalStorageDirectory()
                + "/" + getString(R.string.app_name));
        if (!file.exists()) {
            file.mkdirs();
        }

        mService = ApiUtils.getSOService();
        resultCreateNewCase = (ResultCreateNewCase) getIntent().getSerializableExtra(AppContants.CREATE_NEW_CASE);
        Log.d("resultCreateNewCase"," ss "+resultCreateNewCase.toString().length());
        draftCaseData = (DraftCase) getIntent().getSerializableExtra(AppContants.DATA_DRAFT_CASE);
        isCreateNewCase = (int) getIntent().getIntExtra(AppContants.isCreateNewCase,0);
        authHeader = p.getString(AppContants.AUTHEN_LOGIN,"");
        if(resultCreateNewCase!=null){
            for (int i = 0;i<resultCreateNewCase.getResult().getChecklistArr().size();i++){

//            itemList.add(new Item(resultCreateNewCase.getResult().getChecklistArr().get(i).getGroupName(),
//                    Item.ItemType.ONE_ITEM,resultCreateNewCase.getResult().getChecklistArr().get(i).getMandatory()));
                for(int k = 0;k<resultCreateNewCase.getResult().getChecklistArr().get(i).getDocuments().size();k++){
                    if(resultCreateNewCase.getResult().getChecklistArr().get(i).getMandatory() == 1){
                        if(!arrDocumentValid.contains(resultCreateNewCase.getResult().getChecklistArr().get(i).getGroupName())){
                            arrDocumentValid.add(resultCreateNewCase.getResult().getChecklistArr().get(i).getGroupName());
                        }

                    }
                    arrDocumentGroup.add(resultCreateNewCase.getResult().getChecklistArr().get(i).getGroupName());
                    if(k==0){
                        itemList.add(new Item(resultCreateNewCase.getResult().getChecklistArr().get(i).getGroupName(),
                                Item.ItemType.ONE_ITEM ,resultCreateNewCase.getResult().getChecklistArr().get(i).getMandatory()
                                ,resultCreateNewCase.getResult().getChecklistArr().get(i).getDocuments().get(k).getDocumentName()
                                ,resultCreateNewCase.getResult().getChecklistArr().get(i).getDocuments().get(k).getDocumentCode(),0));
                    }else {
                        itemList.add(new Item("",
                                Item.ItemType.TWO_ITEM ,resultCreateNewCase.getResult().getChecklistArr().get(i).getMandatory()
                                ,resultCreateNewCase.getResult().getChecklistArr().get(i).getDocuments().get(k).getDocumentName()
                                ,resultCreateNewCase.getResult().getChecklistArr().get(i).getDocuments().get(k).getDocumentCode(),0));
                    }
                    termLoadCodeList.add(resultCreateNewCase.getResult().getChecklistArr().get(i).getDocuments().get(k).getDocumentCode());
                    checkListArr.add(resultCreateNewCase.getResult().getChecklistArr().get(i).getDocuments().get(k).getDocumentName());
                    variableMappingList.add(resultCreateNewCase.getResult().getChecklistArr().get(i).getDocuments().get(k).getMapBpmVar());
                }
            }
            token = resultCreateNewCase.getResult().getToken().toString();
            if(resultCreateNewCase.getResult().getInfoCase().getMobileAppCode()!=null){
                mobileAppCode = resultCreateNewCase.getResult().getInfoCase().getMobileAppCode().toString();
            }
        }


        caseadd_FABselect = (LinearLayout)findViewById(R.id.caseadd_FABselect);
        caseadd_FABcapture = (LinearLayout)findViewById(R.id.caseadd_FABcapture);
        caseadd_FABupload = (LinearLayout)findViewById(R.id.caseadd_FABupload);
        caseadd_drap = (LinearLayout)findViewById(R.id.caseadd_drap);
        caseadd_document_type = (Spinner)findViewById(R.id.caseadd_document_type);
        caseadd_FABdelete = (RelativeLayout)findViewById(R.id.caseadd_FABdelete);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_answers);
        progressLoading = (ProgressBar)findViewById(R.id.progressLoading);
        progressLoading.setVisibility(View.GONE);
        progressLoading_not_invite = (ProgressBar)findViewById(R.id.progressLoading_not_invite);
        caseadd_error_ll_image = (LinearLayout)findViewById(R.id.caseadd_error_ll_image);
        caseadd_error_tv_image_total = (TextView)findViewById(R.id.caseadd_error_tv_image_total);
        btnBack = (ImageView)findViewById(R.id.btnBack);
        txtErrorImage = (TextView)findViewById(R.id.txtErrorImage);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mPhotoPicker = new PhotoPicker(this);

        if(isCreateNewCase==1){
            draftImagesList = databaseHelper.getADraftImageByDocument(mobileAppCode+"");
            for(int i = 0;i<draftImagesList.size();i++){
                imageUploadArrayList.add(new ImageUpload(draftImagesList.get(i).getLinkImage(),draftImagesList.get(i).getDocumentCode(),
                        draftImagesList.get(i).getVariableMapping(),draftImagesList.get(i).getDocumentGroup(),draftImagesList.get(i).getDocumentName()));
                for (int k = 0;k<itemList.size();k++){
                    if(itemList.get(k).getTermLoanCode().equals(draftImagesList.get(i).getDocumentCode())){
                        itemList.get(k).setStatus(1);
                    }
                }
            }
            for(int i =0;i<imageUploadArrayList.size();i++){
                if(imageUploadArrayList.get(i).getSchemeCode().equals(termLoadCode)){
                    imageUploadSelectedArrayList.add(imageUploadArrayList.get(i));
                }
                if(!termLoadCodeSelectedList.contains(imageUploadArrayList.get(i).getSchemeCode())){
                    termLoadNameSelectedList.add(imageUploadArrayList.get(i).getSchemeName());
                    termLoadCodeSelectedList.add(imageUploadArrayList.get(i).getSchemeCode());
                }
                if(!arrDocumentValidChecked.contains(imageUploadArrayList.get(i).getSchemeGroup())){
                    arrDocumentValidChecked.add(imageUploadArrayList.get(i).getSchemeGroup());
                }
            }
        }
        caseadd_FABselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCapture = false;
                showDialogSelect(UploadFileActivity.this,itemList,new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int id = v.getId();
                    }
                });
//                mPhotoPicker.startPickChoice(false);
            }
        });
        caseadd_FABcapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mPhotoPicker.startPickChoice(true);
                isCapture = true;
                showDialogSelect(UploadFileActivity.this,itemList,new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int id = v.getId();
                    }
                });
            }
        });
        caseadd_FABdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogCall.showConfirmDialog(UploadFileActivity.this, getString(R.string.confirm_delete_all_image), new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(View v) {
                        int id = v.getId();
                        if (id == R.id.btn_ok) {
                            String documentGroup = "";
                            String documentCode = "";
                            boolean isDelete = false;
                            for(int k = 0;k<imageUploadSelectedArrayList.size();k++){
                                documentGroup = imageUploadSelectedArrayList.get((int) k).getSchemeGroup();
                                documentCode = imageUploadSelectedArrayList.get((int) k).getSchemeCode();
                                for(int i = 0;i<imageUploadArrayList.size();i++){
                                    if(imageUploadArrayList.get(i).equals(imageUploadSelectedArrayList.get((int) k))){
                                        imageUploadArrayList.remove(imageUploadArrayList.get(i));
                                    }
                                }
                            }
                            imageUploadSelectedArrayList.clear();
                            mAdapter.notifyDataSetChanged();
                            if(imageUploadSelectedArrayList.size() == 0){
                                for(int i = 0;i<imageUploadArrayList.size();i++){
                                    if(imageUploadArrayList.get(i).getSchemeGroup().equals(documentGroup)){
                                        isDelete = true;
                                    }
                                }
                                if(!isDelete){
                                    for(int i = 0;i<arrDocumentValidChecked.size();i++){
                                        if(arrDocumentValidChecked.get(i).equals(documentGroup)){
                                            arrDocumentValidChecked.remove(arrDocumentValidChecked.get(i));
                                        }
                                    }
                                }
                                for (int i = 0;i<itemList.size();i++){
                                    if(itemList.get(i).getTermLoanCode().equals(documentCode)){
                                        itemList.get(i).setStatus(0);
                                    }
                                }
                            }else {
                            }

                            validateListDocument();
                        } else if (id == R.id.btn_cancel) {
                        }
                    }
                });
            }
        });

        caseadd_drap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogCall.showConfirmDialog(UploadFileActivity.this,getString(R.string.confirm_save_draft),new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int id = v.getId();
                        if (id == R.id.btn_ok) {
                            addNewDraftCase();
                        }
                        else if (id == R.id.btn_cancel) {
                        }
                    }
                });
            }
        });

        mAdapter = new ImageUploadAdapter(this, imageUploadSelectedArrayList, mRecyclerView, new ImageUploadAdapter.PostItemListener() {

            @Override
            public void onPostClick(final long idClick) {
                DialogCall.showImageFull(UploadFileActivity.this, imageUploadSelectedArrayList.get((int) idClick).getUrl(), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        }, new ImageUploadAdapter.PostLongItemListener() {
            @Override
            public void onPostLongClick(long id) {
                String documentGroup = "";
                String documentCode = "";
                boolean isDelete = false;
                documentGroup = imageUploadSelectedArrayList.get((int) id).getSchemeGroup();
                documentCode = imageUploadSelectedArrayList.get((int) id).getSchemeCode();
                for(int i = 0;i<imageUploadArrayList.size();i++){
                    if(imageUploadArrayList.get(i).equals(imageUploadSelectedArrayList.get((int) id))){
                        imageUploadArrayList.remove(imageUploadArrayList.get(i));
                    }
                }
                imageUploadSelectedArrayList.remove(imageUploadSelectedArrayList.get((int) id));
                mAdapter.notifyDataSetChanged();
                if(imageUploadSelectedArrayList.size() == 0){
                    for(int i = 0;i<imageUploadArrayList.size();i++){
                        if(imageUploadArrayList.get(i).getSchemeGroup().equals(documentGroup)){
                            isDelete = true;
                        }
                    }
                    if(!isDelete){
                        for(int i = 0;i<arrDocumentValidChecked.size();i++){
                            if(arrDocumentValidChecked.get(i).equals(documentGroup)){
                                arrDocumentValidChecked.remove(arrDocumentValidChecked.get(i));
                            }
                        }
                    }
                    for (int i = 0;i<itemList.size();i++){
                        if(itemList.get(i).getTermLoanCode().equals(documentCode)){
                            itemList.get(i).setStatus(0);
                        }
                    }
                }else {
                }

                validateListDocument();
            }

            @Override
            public void onChangeImageClick(long id) {
                isChangeImage = true;
                posImageChange = (int )id;
                Intent intent = new Intent(UploadFileActivity.this, AlbumSelectActivity.class);
                intent.putExtra(Constants.INTENT_EXTRA_LIMIT, AppContants.LIMIT_IMAGE_CHANGE);
                startActivityForResult(intent, Constants.REQUEST_CODE);
            }
        });

        schemeAdapter= new ArrayAdapter<String>(UploadFileActivity.this,android.R.layout.simple_spinner_item, termLoadNameSelectedList);
        schemeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        caseadd_document_type.setAdapter(schemeAdapter);
        caseadd_document_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imageUploadSelectedArrayList.clear();
                for(int i =0;i<imageUploadArrayList.size();i++){
                    if(imageUploadArrayList.get(i).getSchemeCode().equals(termLoadCodeSelectedList.get(position))){
                        imageUploadSelectedArrayList.add(imageUploadArrayList.get(i));
                    }
                }
//                termLoadCodeSelected = termLoadCodeSelectedList.get(position);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen._5sdp);
        mRecyclerView.addItemDecoration(itemDecoration);
        final GridLayoutManager manager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        caseadd_FABupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateListDocument()) {
                    DialogCall.showConfirmDialog(UploadFileActivity.this, getString(R.string.confirm_upload_image_Case), new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onClick(View v) {
                            int id = v.getId();
                            if (id == R.id.btn_ok) {

                                progressLoading_not_invite.setVisibility(View.VISIBLE);

                                runnable =
                                        new Runnable() {
                                            @Override
                                            public void run() {

                                                uploadFileToServer();
                                            }
                                        };

                                handler.postDelayed(runnable, 500);
                            } else if (id == R.id.btn_cancel) {
                            }
                        }
                    });
                }
            }
        });
        validateListDocument();
    }

    private void addNewDraftCase(){
        mobileLoanAmount = draftCaseData.getMoney().replaceAll(formatText, "");
        mobileCustomerName = draftCaseData.getFullname().toString();
        mobileIssueDate = draftCaseData.getDate_card().toString();
        mobileCitiID = draftCaseData.getCard().toString();
        if(draftCaseData.getSignContractAddress().equals("1")){
            mobileTem = draftCaseData.getSignContractAddress().toString();
            mobileTempLabel =  getString(R.string.yes);
        }else {
            mobileTem = draftCaseData.getSignContractAddress().toString();
            mobileTempLabel =  getString(R.string.no);
        }
        mobileHasIn = draftCaseData.getInsurance();
        mobileCompanyTaxNumber = draftCaseData.getTax_code().toString();
        mobileCatResultCompanyName = draftCaseData.getCompany_name().toString();
        mobileCatResultType = draftCaseData.getCat_type().toString();
        mobileSchemeName = draftCaseData.getDocument_type_value().toString();
        mobileSchemeCode = draftCaseData.getDocument_type_code().toString();
        shopCode = draftCaseData.getKiosk_code().toString();
        mobileAppCode = draftCaseData.getMobileAppCode().toString();
        mobileTenor = draftCaseData.getTermloan().toString();
        signContractAddress = draftCaseData.getKiosk_address().toString();

        DraftCase draftCase = new DraftCase(mobileCustomerName,mobileCitiID,mobileIssueDate,mobileTenor,mobileSchemeName.toString(),
                mobileSchemeCode.toString(),signContractAddress,shopCode.toString(),signContractAddress,mobileCatResultType.toString(),mobileCatResultCompanyName.toString()
                ,mobileHasIn,mobileTem,mobileLoanAmount.toString(),mobileCompanyTaxNumber.toString(),mobileAppCode.toString(),"1",p.getString(AppContants.USERNAME_LOGIN,""));
        if(isCreateNewCase == 1){
            if(draftCase.isFromDevice.equals("1")){
                if(databaseHelper.hasObject(mobileAppCode.toString())){
                    databaseHelper.updateADraftCase(mobileCustomerName.toString(),mobileCitiID,mobileIssueDate,mobileTenor,mobileSchemeName.toString(),mobileSchemeCode.toString(),
                            signContractAddress,shopCode.toString(),signContractAddress,mobileCatResultType.toString(),mobileCatResultCompanyName.toString(),mobileHasIn,mobileTem,
                            mobileLoanAmount.toString(),mobileCompanyTaxNumber.toString(),mobileAppCode.toString(),"1",p.getString(AppContants.USERNAME_LOGIN,""));
                }else {
                    databaseHelper.insertADraftCase(mobileCustomerName.toString(),mobileCitiID,mobileIssueDate,mobileTenor,mobileSchemeName.toString(),mobileSchemeCode.toString(),
                            signContractAddress,shopCode.toString(),signContractAddress,mobileCatResultType.toString(),mobileCatResultCompanyName.toString(),mobileHasIn,mobileTem,
                            mobileLoanAmount.toString(),mobileCompanyTaxNumber.toString(),mobileAppCode.toString(),"1",p.getString(AppContants.USERNAME_LOGIN,""));
                }
            }else {
                databaseHelper.insertADraftCase(mobileCustomerName.toString(),mobileCitiID,mobileIssueDate,mobileTenor,mobileSchemeName.toString(),mobileSchemeCode.toString(),
                        signContractAddress,shopCode.toString(),signContractAddress,mobileCatResultType.toString(),mobileCatResultCompanyName.toString(),mobileHasIn,mobileTem,
                        mobileLoanAmount.toString(),mobileCompanyTaxNumber.toString(),mobileAppCode.toString(),"1",p.getString(AppContants.USERNAME_LOGIN,""));
            }
        }else {
            databaseHelper.insertADraftCase(mobileCustomerName.toString(),mobileCitiID,mobileIssueDate,mobileTenor,mobileSchemeName.toString(),mobileSchemeCode.toString(),
                    signContractAddress,shopCode.toString(),signContractAddress,mobileCatResultType.toString(),mobileCatResultCompanyName.toString(),mobileHasIn,mobileTem,
                    mobileLoanAmount.toString(),mobileCompanyTaxNumber.toString(),mobileAppCode.toString(),"1",p.getString(AppContants.USERNAME_LOGIN,""));
        }
        databaseHelper.deleteADraftImageByMobileAppCode(mobileAppCode);
        for (int i = 0;i<imageUploadArrayList.size();i++){
            databaseHelper.insertADraftImage(mobileAppCode+"_"+imageUploadArrayList.get(i).getSchemeCode()+"_"+i,mobileAppCode,imageUploadArrayList.get(i).getSchemeGroup(),
                    imageUploadArrayList.get(i).getSchemeCode(),imageUploadArrayList.get(i).getUrl(),imageUploadArrayList.get(i).getVariableMapping(),imageUploadArrayList.get(i).getSchemeName());
        }
        DialogCall.showWaringDialog(UploadFileActivity.this,getString(R.string.save_draft_success),new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                if (id == R.id.btn_ok) {
                    Intent i = new Intent(UploadFileActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        String photoPath = mPhotoPicker.onActivityResult(requestCode, resultCode, data);
//        if(photoPath!=null) {
//            if(photoPath.length() > 1) {
//                Log.d("imagePath "," Ss "+photoPath);
//            }
//        }
        progressLoading_not_invite.setVisibility(View.VISIBLE);
        if (requestCode == Constants.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            if(isChangeImage){
                isChangeImage = false;
                final ArrayList<Image> images = data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);
                StringBuffer stringBuffer = new StringBuffer();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            for (int i = (images.size()-1), l = -1; i > l; i--) {
                                for (String extension : AppContants.okFileExtensions)
                                {
                                    if (images.get(i).path.toLowerCase().endsWith(extension))
                                    {
                                        if((images.get(i).path.substring(images.get(i).path.lastIndexOf("/")+1)).length() <= 100){
                                            for(int k = 0;k<imageUploadArrayList.size();k++){
                                                if(imageUploadArrayList.get(k).equals(imageUploadSelectedArrayList.get((int) posImageChange))){
                                                    File s = new File(images.get(i).path);
//                    decodeFile(s,images.get(i).path.substring(images.get(i).path.lastIndexOf("/")+1),images.get(i).path);
                                                    imageUploadArrayList.get(k).setUrl(decodeFile(s,images.get(i).path.substring(images.get(i).path.lastIndexOf("/")+1),images.get(i).path));
                                                }
                                            }
                                            imageUploadSelectedArrayList.get(posImageChange).setUrl(images.get(i).path);
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    mAdapter.notifyDataSetChanged();
//                                        progressLoading_not_invite.setVisibility(View.GONE);
                                                }
                                            });
                                        }

                                    }
                                }

                            }
                        }finally {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                    mAdapter.notifyDataSetChanged();
                                    progressLoading_not_invite.setVisibility(View.GONE);
                                }
                            });
                        }
                    }
                }).start();

//                for(int i = 0;i<imageUploadArrayList.size();i++){
//                    if(imageUploadArrayList.get(i).equals(imageUploadSelectedArrayList.get((int) id))){
//                        imageUploadArrayList.remove(imageUploadArrayList.get(i));
//                    }
//                }

            }else {
                final ArrayList<Image> images = data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);
                StringBuffer stringBuffer = new StringBuffer();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            for (int i = (images.size()-1), l = -1; i > l; i--) {
                                for (String extension : AppContants.okFileExtensions)
                                {
                                    if (images.get(i).path.toLowerCase().endsWith(extension)) {
                                        if((images.get(i).path.substring(images.get(i).path.lastIndexOf("/")+1)).length() <= 100) {
                                            final int finalI = i;
                                            File s = new File(images.get(finalI).path);
                                            imageUploadArrayList.add(new ImageUpload(decodeFile(s, images.get(finalI).path.substring(images.get(finalI).path.lastIndexOf("/") + 1), images.get(finalI).path)
                                                    , termLoadCode, variableMapping, documentValidChecked, termLoadNameSelected));
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    imageUploadSelectedArrayList.clear();
                                                    for (int i = 0; i < imageUploadArrayList.size(); i++) {
                                                        if (imageUploadArrayList.get(i).getSchemeCode().equals(termLoadCode)) {
                                                            imageUploadSelectedArrayList.add(imageUploadArrayList.get(i));
                                                        }
                                                    }
                                                    mAdapter.notifyDataSetChanged();
                                                }
                                            });
                                        }
                                    }
                                }
                            }
                        }finally {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                    imageUploadSelectedArrayList.clear();
//                                    for(int i =0;i<imageUploadArrayList.size();i++){
//                                        if(imageUploadArrayList.get(i).getSchemeCode().equals(termLoadCode)){
//                                            imageUploadSelectedArrayList.add(imageUploadArrayList.get(i));
//                                        }
//                                    }

                                    if(!termLoadNameSelectedList.contains(termLoadNameSelected)){
                                        termLoadNameSelectedList.add(termLoadNameSelected);
                                        termLoadCodeSelectedList.add(termLoadCode);
                                    }

                                    for(int i = 0;i<termLoadCodeSelectedList.size();i++){
                                        if(termLoadCodeSelectedList.get(i).equals(termLoadCode)){
                                            caseadd_document_type.setSelection(i);
                                        }
                                    }
                                    if(!arrDocumentValidChecked.contains(documentValidChecked)){
                                        arrDocumentValidChecked.add(documentValidChecked);
                                    }
                                    schemeAdapter.notifyDataSetChanged();
                                    mAdapter.notifyDataSetChanged();
                                    progressLoading.setVisibility(View.GONE);
                                    progressLoading_not_invite.setVisibility(View.GONE);
                                    validateListDocument();
                                    progressLoading.setVisibility(View.GONE);
                                    progressLoading_not_invite.setVisibility(View.GONE);
                                    validateListDocument();
                                }
                            });
                        }
                    }
                }).start();


            }
            itemList.get(item_document_selected).setStatus(1);
        }else if (requestCode == REQUEST_CAPTURE_IMAGE && resultCode == RESULT_OK) {
            File imgFile = new  File(imageFilePath);
            if(imgFile.exists()){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            for (String extension : AppContants.okFileExtensions)
                            {
                                if (imageFilePath.toLowerCase().endsWith(extension)) {
                                    File s = new File(imageFilePath);
//                    decodeFile(s,images.get(i).path.substring(images.get(i).path.lastIndexOf("/")+1),images.get(i).path);
                                    imageUploadArrayList.add(new ImageUpload(decodeFile(s, imageFilePath.substring(imageFilePath.lastIndexOf("/") + 1),
                                            imageFilePath), termLoadCode, variableMapping, documentValidChecked, termLoadNameSelected));
                                }
                            }
                        }finally {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    imageUploadSelectedArrayList.clear();

                                    for(int i =0;i<imageUploadArrayList.size();i++){
                                        if(imageUploadArrayList.get(i).getSchemeCode().equals(termLoadCode)){
                                            imageUploadSelectedArrayList.add(imageUploadArrayList.get(i));
                                        }
                                    }

                                    if(!termLoadNameSelectedList.contains(termLoadNameSelected)){
                                        termLoadNameSelectedList.add(termLoadNameSelected);
                                        termLoadCodeSelectedList.add(termLoadCode);
                                    }


                                    for(int i = 0;i<termLoadCodeSelectedList.size();i++){
                                        if(termLoadCodeSelectedList.get(i).equals(termLoadCode)){
                                            caseadd_document_type.setSelection(i);
                                        }
                                    }
                                    if(!arrDocumentValidChecked.contains(documentValidChecked)){
                                        arrDocumentValidChecked.add(documentValidChecked);
                                    }
//            mAdapter.notifyDataSetChanged();
                                    schemeAdapter.notifyDataSetChanged();
                                    mAdapter.notifyDataSetChanged();
                                    progressLoading.setVisibility(View.GONE);
                                    progressLoading_not_invite.setVisibility(View.GONE);
                                    validateListDocument();
                                }
                            });
                        }

                    }
                }).start();
            }
            itemList.get(item_document_selected).setStatus(1);
        }else {
            progressLoading_not_invite.setVisibility(View.GONE);
            progressLoading.setVisibility(View.GONE);
            isChangeImage = false;
//            itemList.get(item_document_selected).setStatus(0);
        }
    }
    private boolean validateListDocument(){
        final ArrayList<String> listCheck = new ArrayList<String>();
        boolean isreturn = false;
        for(int i = 0;i<imageUploadArrayList.size();i++){
            File file = new File(imageUploadArrayList.get(i).getUrl());
            if(file.exists()){
                if(!listCheck.contains(imageUploadArrayList.get(i).getSchemeGroup())){
                    listCheck.add(imageUploadArrayList.get(i).getSchemeGroup());
                }
            }
        }
            String validDocument = "";
            for(int k = 0;k<arrDocumentValid.size();k++){
//                for(int i = 0;i<imageUploadArrayList.size();i++){
//                    if(imageUploadArrayList.get(i).getSchemeGroup().equals(arrDocumentValid.get(k)))
//                    File file = new File(imageUploadArrayList.get(i).getUrl());
//                    if(file.exists()){
//                        if(listCheck.contains(imageUploadArrayList.get(i).getSchemeGroup())){
//                            listCheck.add(imageUploadArrayList.get(i).getSchemeGroup());
//                        }
//                    }
//                }
                if(!listCheck.contains(arrDocumentValid.get(k))){
                    validDocument = validDocument + arrDocumentValid.get(k) + "\n";
                }
            }
            caseadd_error_tv_image_total.setText(validDocument.trim());
            if(listCheck.containsAll(arrDocumentValid)){
                caseadd_error_ll_image.setVisibility(View.GONE);
                isreturn = true;
            }else {
                caseadd_error_ll_image.setVisibility(View.VISIBLE);
                isreturn = false;
            }
          return isreturn;
    }

    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        imageFilePath = image.getAbsolutePath();
        return image;
    }


    private void openCameraIntent() {
        Intent pictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        if(pictureIntent.resolveActivity(getPackageManager()) != null){
            //Create a file to store the image
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, getString(R.string.provinder_pagkage), photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        photoURI);
                startActivityForResult(pictureIntent,
                        REQUEST_CAPTURE_IMAGE);
            }
        }
    }
    public void showDialogSelect(final Activity mContext, final ArrayList<Item> item, final View.OnClickListener listener){
        final AlertDialog builder = new AlertDialog.Builder(mContext).create();
        builder.show();
        builder.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        builder.setCanceledOnTouchOutside(false);
        builder.setCancelable(false);

        Window window = builder.getWindow();
        window.setContentView(R.layout.dialog_select_type_images);

        RecyclerView item_list = (RecyclerView) window.findViewById(R.id.item_list);
        ImageView img_close = (ImageView)window.findViewById(R.id.img_close);
        ItemPopupSelectedAdapter itemArrayAdapter = new ItemPopupSelectedAdapter(this,item,item_list, new ItemPopupSelectedAdapter.PostItemListener() {

            @Override
            public void onPostClick(long id) {
                if(isCapture){
                    openCameraIntent();
                }else {
                    Intent intent = new Intent(UploadFileActivity.this, AlbumSelectActivity.class);
                    intent.putExtra(Constants.INTENT_EXTRA_LIMIT, AppContants.LIMIT_IMAGE_SELECT);
                    startActivityForResult(intent, Constants.REQUEST_CODE);
                }
//                mPhotoPicker.startPickChoice(false);
                item_document_selected = (int) id;
                termLoadCode = termLoadCodeList.get((int) id);
                variableMapping = variableMappingList.get((int)id);
                termLoadNameSelected = checkListArr.get((int)id);
                documentValidChecked = arrDocumentGroup.get((int)id);
                        builder.dismiss();
            }
        });
        item_list.setLayoutManager(new LinearLayoutManager(mContext));
        item_list.setItemAnimator(new DefaultItemAnimator());
        item_list.setAdapter(itemArrayAdapter);

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void uploadFileToServer(){
            JSONObject bodyTxtFile = new JSONObject();
            JSONArray bodyInfo = new JSONArray();
            if(fileUpload!=null){
                fileUpload.clear();
            }
//        listImagePathCompress.clear();
            for (int i = 0;i<imageUploadArrayList.size();i++){
                File file = new File(imageUploadArrayList.get(i).getUrl());
                if(file.exists()) {
                    if (!fileUpload.contains(imageUploadArrayList.get(i).getUrl())) {
                        fileUpload.add(imageUploadArrayList.get(i).getUrl());
                    }
                    String mime = "";
                    mime = getMimeTypeFromPath(imageUploadArrayList.get(i).getUrl());

                    JSONObject jsonObject = new JSONObject();
                    try {
                        String filename = imageUploadArrayList.get(i).getUrl().substring(imageUploadArrayList.get(i).getUrl().lastIndexOf("/") + 1);
                        jsonObject.put("mobileFileName", String.valueOf(filename));
                        jsonObject.put("variableMapping", imageUploadArrayList.get(i).getVariableMapping());
                        jsonObject.put("documentCode", imageUploadArrayList.get(i).getSchemeCode() + "");
                        jsonObject.put("mimetype", mime);
                        bodyInfo.put(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                bodyTxtFile.put("info", bodyInfo);
                bodyTxtFile.put("mobileAppCode",String.valueOf(mobileAppCode));
                bodyTxtFile.put("imei",String.valueOf(imei));
                bodyTxtFile.put("token",String.valueOf(token));

            } catch (JSONException e) {
                e.printStackTrace();
            }
//            for(int i = 0;i<fileUpload.size();i++){
//                File s = new File(fileUpload.get(i).toString());
//                decodeFile(s,fileUpload.get(i).substring(fileUpload.get(i).lastIndexOf("/")+1),fileUpload.get(i));
//                if(i == (fileUpload.size()-1)){
//                    //this is the last iteration of for loop
//                    listImagePathCompress.add(generateNoteOnSD(this,"uploadType.txt",bodyTxtFile.toString()));
//                    File root = new File(Environment.getExternalStorageDirectory(), getString(R.string.app_name));
//                    zipPath = root.getAbsolutePath().toString()+"/uploadFile.zip";
//                    zip(listImagePathCompress,zipPath,zipPath);
//                }
//            }
            fileUpload.add(generateNoteOnSD(this,"uploadType.txt",bodyTxtFile.toString()));
            File root = new File(Environment.getExternalStorageDirectory(), getString(R.string.app_name));
            zipPath = root.getAbsolutePath().toString()+"/uploadFile.zip";
            zip(fileUpload,zipPath,zipPath);

//        Compress compress = new Compress(fileUpload,path);
//        compress.zip();
    }
    private boolean copyFile(File src,File dst)throws IOException{
        if(src.getAbsolutePath().toString().equals(dst.getAbsolutePath().toString())){

            return true;

        }else{
            InputStream is=new FileInputStream(src);
            OutputStream os=new FileOutputStream(dst);
            byte[] buff=new byte[1024];
            int len;
            while((len=is.read(buff))>0){
                os.write(buff,0,len);
            }
            is.close();
            os.close();
        }
        return true;
    }

    public void zip(ArrayList<String> _files, String zipFileName, String fileZip) {
        try {
            ArrayList<String> filesZiped = new ArrayList<String>();
            filesZiped.clear();
            BufferedInputStream origin = null;
            FileOutputStream dest = new FileOutputStream(zipFileName);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
                    dest));
            byte data[] = new byte[204800];

            for (int i = 0; i < _files.size(); i++) {
                Log.v("Compress", "Adding: " + _files.get(i));
                File file = new File(_files.get(i));
                if(file.exists()){
                    if(!filesZiped.contains(_files.get(i).substring(_files.get(i).lastIndexOf("/") + 1))){
                        FileInputStream fi = new FileInputStream(_files.get(i));
                        origin = new BufferedInputStream(fi, 204800);

                        ZipEntry entry = new ZipEntry(_files.get(i).substring(_files.get(i).lastIndexOf("/") + 1));
                        filesZiped.add(_files.get(i).substring(_files.get(i).lastIndexOf("/") + 1));
                        out.putNextEntry(entry);
                        int count;

                        while ((count = origin.read(data, 0, 204800)) != -1) {
                            out.write(data, 0, count);
                        }
                    }else {
                        Log.v("CompressFail", "Addingaaa: " + _files.get(i));
                    }
                    origin.close();
                }else {

                }
            }

            out.close();
            upload(fileZip,authHeader);
//            uploadFileUsingRetrofit(fileZip);
        } catch (Exception e) {
            progressLoading_not_invite.setVisibility(View.GONE);
            DialogCall.showConfirmDialog(UploadFileActivity.this, getString(R.string.confirm_zip_file_error), new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {
                    int id = v.getId();
                    if (id == R.id.btn_ok) {
                        progressLoading_not_invite.setVisibility(View.VISIBLE);
                        zip(fileUpload,zipPath,zipPath);
                    } else if (id == R.id.btn_cancel) {
                    }
                }
            });


            e.printStackTrace();
        }
    }

    private String getMimeTypeFromPath(String path) {
        String extension = path;
        int lastDot = extension.lastIndexOf('.');
        if (lastDot != -1) {
            extension = extension.substring(lastDot + 1);
        }
        // Convert the URI string to lower case to ensure compatibility with MimeTypeMap (see CB-2185).
        extension = extension.toLowerCase(Locale.getDefault());
        if (extension.equals("3ga")) {
            return "audio/3gpp";
        } else if (extension.equals("js")) {
            // Missing from the map :(.
            return "text/javascript";
        }
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }

    public String generateNoteOnSD(Context context, String sFileName, String sBody) {
        String path= "";
        try {
            File root = new File(Environment.getExternalStorageDirectory(), getString(R.string.app_name));
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();

            path = gpxfile.getAbsolutePath().toString();
//            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    public static boolean deleteDirectory(File path) {
        if( path.exists() ) {
            File[] files = path.listFiles();
            if (files == null) {
                return true;
            }
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                }
                else {
                    files[i].delete();
                }
            }
        }
        return( path.delete() );
    }

    private void uploadMultiFiles(ArrayList<String> fileUriPath, final String authHeader){
        String url = AppContants.BaseUrlUpload;

        try {
            String uploadId = UUID.randomUUID().toString();
            //Creating a multi part request
            MultipartUploadRequest request = new MultipartUploadRequest(this, uploadId, url);

            for (int i = 0;i<fileUriPath.size();i++){
                request.addFileToUpload(fileUriPath.get(i), "uploadingFiles");
            }

            request.addHeader("Authorization",authHeader);
            request.setNotificationConfig(new UploadNotificationConfig());
            request.setDelegate(new UploadStatusDelegate() {
                @Override
                public void onProgress(UploadInfo uploadInfo) {

                }

                @Override
                public void onError(UploadInfo uploadInfo, Exception exception) {
                    DialogCall.showWaringDialog(UploadFileActivity.this,getString(R.string.upload_files_error),new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onClick(View v) {
                            int id = v.getId();
                            if (id == R.id.btn_ok) {
                                upload(zipPath,authHeader);
                            }
                            else if (id == R.id.btn_cancel) {
                            }
                        }
                    });
                    progressLoading_not_invite.setVisibility(View.GONE);
                }

                @Override
                public void onCompleted(UploadInfo uploadInfo, ServerResponse serverResponse) {
                    DialogCall.showWaringDialog(UploadFileActivity.this,getString(R.string.upload_files_success),new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onClick(View v) {
                            int id = v.getId();
                            if (id == R.id.btn_ok) {
                                Intent i = new Intent(UploadFileActivity.this,MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                        }
                    });
                    progressLoading_not_invite.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(UploadInfo uploadInfo) {

                }
            });
//            request.setMaxRetries(2);
            request.startUpload(); //Starting the upload
            //progressLoading_not_invite.setVisibility(View.GONE);

        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
    private void upload(String fileUriPath, final String authHeader){
        if(isNetworkConnected()){
            progressLoading_not_invite.setVisibility(View.VISIBLE);
            String url = AppContants.BaseUrlUpload;

            try {
                String uploadId = UUID.randomUUID().toString();
                //Creating a multi part request
                MultipartUploadRequest request = new MultipartUploadRequest(this, uploadId, url);

                request.addFileToUpload(fileUriPath, "uploadingFiles");
                request.addHeader("Authorization",authHeader);
                request.setNotificationConfig(new UploadNotificationConfig());
                request.setDelegate(new UploadStatusDelegate() {
                    @Override
                    public void onProgress(UploadInfo uploadInfo) {

                    }

                    @Override
                    public void onError(UploadInfo uploadInfo, Exception exception) {
                        String message = exception.getMessage().toString();
                        DialogCall.showWaringDialog(UploadFileActivity.this,message,new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onClick(View v) {
                                int id = v.getId();
                                if (id == R.id.btn_ok) {
//                                    upload(zipPath,authHeader);
                                }
                                else if (id == R.id.btn_cancel) {
                                }
                            }
                        });
                        progressLoading_not_invite.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCompleted(UploadInfo uploadInfo, ServerResponse serverResponse) {
                        if(serverResponse.getHttpCode()==200){
                            DialogCall.showWaringDialog(UploadFileActivity.this,getString(R.string.upload_files_success),new View.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                @Override
                                public void onClick(View v) {
                                    int id = v.getId();
                                    if (id == R.id.btn_ok) {
                                        databaseHelper.deleteADraftCaseByMobileAppCode(mobileAppCode);
                                        databaseHelper.deleteADraftImageByMobileAppCode(mobileAppCode);
                                        Intent i = new Intent(UploadFileActivity.this,MainActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                }
                            });
                            progressLoading_not_invite.setVisibility(View.GONE);
                        }else {
                            String message = serverResponse.getBodyAsString().toString();
                            DialogCall.showWaringDialog(UploadFileActivity.this,getString(R.string.upload_files_error),new View.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                @Override
                                public void onClick(View v) {
                                    int id = v.getId();
                                    if (id == R.id.btn_ok) {
//                                    upload(zipPath,authHeader);
                                    }
                                    else if (id == R.id.btn_cancel) {
                                    }
                                }
                            });
                            progressLoading_not_invite.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onCancelled(UploadInfo uploadInfo) {

                    }
                });
//            request.setMaxRetries(2);
                request.startUpload(); //Starting the upload
                //progressLoading_not_invite.setVisibility(View.GONE);

            } catch (Exception exc) {
                Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }else {
            DialogCall.showWaringDialog(UploadFileActivity.this,getString(R.string.internet_not_connected),new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {
                    int id = v.getId();
                    if (id == R.id.btn_ok) {
                        upload(zipPath,authHeader);
                    }
                    else if (id == R.id.btn_cancel) {
                    }
                }
            });
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void uploadFileUsingRetrofit(String fileUriPath) {
        Uri fileUri = Uri.parse(fileUriPath);
        // create upload service client

        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        File file = new File(fileUriPath);

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"),file
                );

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("uploadingFiles", file.getName(), requestFile);

        // add another part within the multipart request
        String descriptionString = "hello, this is description speaking";
        RequestBody description =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, descriptionString);

        // finally, execute the request
        Call<UploadResponse> call = mService.upload(authHeader,description, body);
        call.enqueue(new Callback<UploadResponse>() {
            @Override
            public void onResponse(Call<UploadResponse> call,
                                   Response<UploadResponse> response) {
                Log.v("Upload", "success");
                if(response.body().getResult()!=null){
                    if(response.body().getResult().getHttpCode() == 200){
                        if(response.body().getResult()!=null){
                            p.putString(AppContants.TOKEN_LOGIN,response.body().getResult().getToken());
                        }
                        DialogCall.showWaringDialog(UploadFileActivity.this,getString(R.string.upload_files_success),new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onClick(View v) {
                                int id = v.getId();
                                if (id == R.id.btn_ok) {

                                    finish();
                                }
                            }
                        });
                        progressLoading_not_invite.setVisibility(View.GONE);
                    }else {

                        progressLoading_not_invite.setVisibility(View.GONE);
                    }
//                }else {
//                    int statusCode  = response.code();
//                    progressLoading_not_invite.setVisibility(View.GONE);
//                }
                }

            }

            @Override
            public void onFailure(Call<UploadResponse> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
                DialogCall.showWaringDialog(UploadFileActivity.this,getString(R.string.upload_files_error),new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(View v) {
                        int id = v.getId();
                        if (id == R.id.btn_ok) {

                        }
                    }
                });
                progressLoading_not_invite.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onProgress(UploadInfo uploadInfo) {

    }

    @Override
    public void onError(UploadInfo uploadInfo, Exception exception) {
    }

    @Override
    public void onCompleted(UploadInfo uploadInfo, ServerResponse serverResponse) {
    }

    @Override
    public void onCancelled(UploadInfo uploadInfo) {

    }

    private String decodeFile(File f,String name,String pathBefore) {
        String imagePath = "";
        Bitmap b = null;
                //Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
            BitmapFactory.decodeStream(fis, null, o);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int IMAGE_MAX_SIZE = 1024;
        int scale = 1;
//        if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
//            scale = (int) Math.pow(2, (int) Math.ceil(Math.log(IMAGE_MAX_SIZE /
//                    (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
//        }

        //Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        try {
            fis = new FileInputStream(f);
//            Matrix matrix = new Matrix();
//            matrix.postRotate(90); // anti-clockwise by 90 degrees
//            Bitmap rotatedBitmap = BitmapFactory.decodeStream(fis, null, o2);
//            b= Bitmap.createBitmap(rotatedBitmap , 0, 0, rotatedBitmap .getWidth(), rotatedBitmap .getHeight(), matrix, true);
            Bitmap rotatedBitmap = BitmapFactory.decodeStream(fis, null, o2);
//            b = BitmapFactory.decodeStream(fis, null, o2);
            ExifInterface exif = null;
            try {
                exif = new ExifInterface(f.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            Matrix matrix = new Matrix();
            switch (orientation) {
                case ExifInterface.ORIENTATION_NORMAL:
                    matrix.setRotate(0);
                    break;
                case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                    matrix.setScale(-1, 1);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    matrix.setRotate(180);
                    break;
                case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                    matrix.setRotate(180);
                    matrix.postScale(-1, 1);
                    break;
                case ExifInterface.ORIENTATION_TRANSPOSE:
                    matrix.setRotate(90);
                    matrix.postScale(-1, 1);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    matrix.setRotate(90);
                    break;
                case ExifInterface.ORIENTATION_TRANSVERSE:
                    matrix.setRotate(-90);
                    matrix.postScale(-1, 1);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    matrix.setRotate(-90);
                    break;
                default:
                    matrix.setRotate(0);
                    break;
            }
            b= Bitmap.createBitmap(rotatedBitmap , 0, 0, rotatedBitmap .getWidth(), rotatedBitmap .getHeight(), matrix, true);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        destFile = new File(file,name );
        try {
            FileOutputStream out = new FileOutputStream(destFile);
            b.compress(Bitmap.CompressFormat.JPEG, AppContants.quality_image, out);

            out.flush();
            out.close();
            imagePath = Environment.getExternalStorageDirectory()
                    + "/" + getString(R.string.app_name)+"/"+name;
            listImagePathCompress.add(Environment.getExternalStorageDirectory()
                    + "/" + getString(R.string.app_name)+"/"+name);

        } catch (Exception e) {
            listImagePathCompress.add(pathBefore);
            imagePath = pathBefore;
//            e.printStackTrace();
        }
        return imagePath;
    }
}
