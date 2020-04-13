package com.mcredit.mobile.mobile_for_sale.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.mcredit.mobile.mobile_for_sale.Adapter.CaseNoteAdapter;
import com.mcredit.mobile.mobile_for_sale.Adapter.ImageUploadAdapter;
import com.mcredit.mobile.mobile_for_sale.Adapter.ItemPopupSelectedAdapter;
import com.mcredit.mobile.mobile_for_sale.Adapter.PdfFileAdapter;
import com.mcredit.mobile.mobile_for_sale.Api.Service.SOService;
import com.mcredit.mobile.mobile_for_sale.Api.Utils.ApiUtils;
import com.mcredit.mobile.mobile_for_sale.Base.PermissionActivity;
import com.mcredit.mobile.mobile_for_sale.Contants.AppContants;
import com.mcredit.mobile.mobile_for_sale.Dialog.DialogCall;
import com.mcredit.mobile.mobile_for_sale.Models.CaseNote.AppNotesEntry;
import com.mcredit.mobile.mobile_for_sale.Models.CaseNote.CaseNoteResult;
import com.mcredit.mobile.mobile_for_sale.Models.GetCat.GetCatInfo;
import com.mcredit.mobile.mobile_for_sale.Models.GetPDF.PDFList;
import com.mcredit.mobile.mobile_for_sale.Models.GetPDF.PdfResult;
import com.mcredit.mobile.mobile_for_sale.Models.GetSchemeUpdate.ResultSchemeUpdate;
import com.mcredit.mobile.mobile_for_sale.Models.ImageUpload.ImageUpload;
import com.mcredit.mobile.mobile_for_sale.Models.PopUpSelect.Item;
import com.mcredit.mobile.mobile_for_sale.Models.ReturnCase.CaseReturnArr;
import com.mcredit.mobile.mobile_for_sale.Models.SendCaseNote.Result;
import com.mcredit.mobile.mobile_for_sale.Models.SendCaseNote.SendCaseNoteResult;
import com.mcredit.mobile.mobile_for_sale.Models.UploadFile.UploadResponse;
import com.mcredit.mobile.mobile_for_sale.R;
import com.mcredit.mobile.mobile_for_sale.Utils.DateUtils;
import com.mcredit.mobile.mobile_for_sale.Utils.Edittext;
import com.mcredit.mobile.mobile_for_sale.Utils.ItemOffsetDecoration;
import com.mcredit.mobile.mobile_for_sale.Utils.PhotoPicker;
import com.mcredit.mobile.mobile_for_sale.Utils.Pref;
import com.google.gson.JsonObject;

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
 * Created by hiephunbie on 4/10/18.
 */

public class UpdateCaseActivity extends PermissionActivity implements UploadStatusDelegate{

    private TextView txtScheme,txt_citi_id,txtloan_ter,txtInsurance,txt_places_check,txtKiosk,txtMoney,txtMessage,txtFullName,txtMobileAppCode;
    private CaseReturnArr caseReturnArr;
    private  String message = "";
    public static PhotoPicker mPhotoPicker;
    private LinearLayout caseadd_FABselect,caseadd_FABcapture,caseadd_FABupload;
    private SOService mService;
    private Spinner caseadd_document_type;
    private ArrayList<String> checkListArr = new ArrayList<String>();
    private ArrayList <Item> itemList = new ArrayList<Item>();
    private ArrayList<String> schemeCodeList = new ArrayList<String>();
    private RelativeLayout caseadd_FABdelete;
    private RecyclerView mRecyclerView;
    private ImageUploadAdapter mAdapter;
    private PdfFileAdapter pdfFileAdapter;
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
    private String token,imei;
    private String authHeader = "";
    private Handler handler = new Handler();
    private Runnable runnable;
    private ArrayList<String> arrDocumentValid = new ArrayList<String>();
    private ArrayList<String> arrDocumentGroup = new ArrayList<String>();
    private ArrayList<String> arrDocumentValidChecked = new ArrayList<String>();
    private String documentValidChecked = "";
    private ArrayList<String> fileUpload = new ArrayList<String>();
    private LinearLayout caseadd_error_ll_image;
    private TextView caseadd_error_tv_image_total;
    private String zipPath ="";
    private ResultSchemeUpdate resultSchemeUpdate = new ResultSchemeUpdate();
    private String mobileAppCode = "";
    private boolean isChangeImage = false;
    private int posImageChange = 0;
    private boolean isCapture = false;
    private static final int CAMERA_REQUEST = 1888;
    String imageFilePath;
    private static final int REQUEST_CAPTURE_IMAGE = 100;
    private RecyclerView rv_pdf;
    private ProgressBar progressLoading_pdf;
    private ArrayList<String> documentPdfList = new ArrayList<String>();
    private ArrayList<PDFList> PdfListResult = new ArrayList<PDFList>();
    private DownloadManager downloadManager;
    private long refid;
    private Uri Download_Uri;
    ArrayList<Long> list = new ArrayList<>();
    private ProgressBar progressLoading_full;
    private String path_pdf_file = "";
    private boolean isClickViewPdf = false;
    private TextView txt_note;
    private ArrayList<AppNotesEntry> appNotesEntryArrayList = new ArrayList<AppNotesEntry>();
    private File file;
    private File destFile;
    private ArrayList<String> listImagePathCompress = new ArrayList<String>();
    private CaseNoteAdapter caseNoteAdapter ;
    private int item_document_selected = 0;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_file_activity);
        p = new Pref(UpdateCaseActivity.this);
        imei = p.getString(AppContants.EMEI_LOGIN,"");
        mService = ApiUtils.getSOService();
        authHeader = p.getString(AppContants.AUTHEN_LOGIN,"");
        caseReturnArr = (CaseReturnArr) getIntent().getSerializableExtra(AppContants.UPDATE_CASE_FAIL);
        file = new File(Environment.getExternalStorageDirectory()
                + "/" + getString(R.string.app_name));
        if (!file.exists()) {
            file.mkdirs();
        }
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        txtScheme = (TextView)findViewById(R.id.txtScheme);
        txt_citi_id = (TextView)findViewById(R.id.txt_citi_id);
        txtloan_ter = (TextView)findViewById(R.id.txtloan_ter);
        txtInsurance = (TextView)findViewById(R.id.txtInsurance);
        txt_places_check = (TextView)findViewById(R.id.txt_places_check);
        txtKiosk = (TextView)findViewById(R.id.txtKiosk);
        txtMoney = (TextView)findViewById(R.id.txtMoney);
        txtMessage = (TextView)findViewById(R.id.txtMessage);
        txtFullName = (TextView)findViewById(R.id.txtFullName);
        txtMobileAppCode = (TextView)findViewById(R.id.txtMobileAppCode);
        btnBack = (ImageView)findViewById(R.id.btnBack);
        caseadd_FABselect = (LinearLayout)findViewById(R.id.caseadd_FABselect);
        caseadd_FABcapture = (LinearLayout)findViewById(R.id.caseadd_FABcapture);
        caseadd_FABupload = (LinearLayout)findViewById(R.id.caseadd_FABupload);
        caseadd_document_type = (Spinner)findViewById(R.id.caseadd_document_type);
        caseadd_FABdelete = (RelativeLayout)findViewById(R.id.caseadd_FABdelete);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_answers);
        progressLoading = (ProgressBar)findViewById(R.id.progressLoading);
        rv_pdf = (RecyclerView) findViewById(R.id.rv_pdf);
        progressLoading_pdf = (ProgressBar)findViewById(R.id.progressLoading_pdf);
        progressLoading_not_invite = (ProgressBar)findViewById(R.id.progressLoading_not_invite);
        progressLoading_full = (ProgressBar)findViewById(R.id.progressLoading_full);
        caseadd_error_ll_image = (LinearLayout)findViewById(R.id.caseadd_error_ll_image);
        caseadd_error_tv_image_total = (TextView)findViewById(R.id.caseadd_error_tv_image_total);
        txt_note = (TextView)findViewById(R.id.txt_case_note);
        registerReceiver(onComplete,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        if(caseReturnArr != null){
            mobileAppCode = caseReturnArr.getDataEntrySales().getMobileAppCode();
            txtFullName.setText(caseReturnArr.getDataEntrySales().getMobileCustomerName().toString());
            txtScheme.setText(caseReturnArr.getDataEntrySales().getMobileSchemaProductName().toString());
            txt_citi_id.setText(caseReturnArr.getDataEntrySales().getMobileCitizenId().toString());
            txtloan_ter.setText(caseReturnArr.getDataEntrySales().getMobileLoanTenor() + " "+getString(R.string.month));
            if(caseReturnArr.getDataEntrySales().getMobileHasInsurrance()==1){
                txtInsurance.setText(getString(R.string.had_insurrance));
            }else {
                txtInsurance.setText(getString(R.string.had_not_insurrance));
            }
            if(caseReturnArr.getDataEntrySales().getMobileTemResidence().equals("1")){
                txt_places_check.setText(getString(R.string.had_same_places));
            }else {
                txt_places_check.setText(getString(R.string.had_not_same_places));
            }
            txtKiosk.setText(caseReturnArr.getDataEntrySales().getMobileSignContracAddress().toString());
            txtMobileAppCode.setText(caseReturnArr.getDataEntrySales().getAppNumber() +"");
            txtMoney.setText(Edittext.convertTextToCommas(caseReturnArr.getDataEntrySales().getMobileLoanAmount()+""));

            if(caseReturnArr.getReturnComment() != null){
                if(!caseReturnArr.getReturnComment().equals("null")){
                    message = message  + caseReturnArr.getReturnComment() + " \n ";
                }
            }
           for (int i = 0;i<caseReturnArr.getDataEntryReturnDetails().size();i++){
               if(i != 0){
                   if(caseReturnArr.getDataEntryReturnDetails().get(i).getReasonCodeLabel().toString().equals
                           (caseReturnArr.getDataEntryReturnDetails().get(i-1).getReasonCodeLabel().toString())){
                       message = message + "  - "+caseReturnArr.getDataEntryReturnDetails().get(i).getReasonDetailCodeDetail().toString()+ "\n ";
                   }else {
                       message = message + caseReturnArr.getDataEntryReturnDetails().get(i).getReasonCodeLabel().toString() + " : \n   - "
                               +caseReturnArr.getDataEntryReturnDetails().get(i).getReasonDetailCodeDetail().toString()+ "\n ";
                   }
               }else {
                   message = message + caseReturnArr.getDataEntryReturnDetails().get(i).getReasonCodeLabel().toString() + " : \n   - "
                           +caseReturnArr.getDataEntryReturnDetails().get(i).getReasonDetailCodeDetail().toString() + "\n ";
               }
           }

            txtMessage.setText(message.trim());
        }
        getSchemeUpdate();
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
        mPhotoPicker = new PhotoPicker(this);
        caseadd_FABselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCapture = false;
                showDialogSelect(UpdateCaseActivity.this,itemList,new View.OnClickListener() {
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
                showDialogSelect(UpdateCaseActivity.this,itemList,new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int id = v.getId();
                    }
                });
            }
        });
        txt_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogCaseNote(UpdateCaseActivity.this, appNotesEntryArrayList,caseReturnArr.getDataEntrySales().getAppId().toString(),new View.OnClickListener() {
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
                DialogCall.showConfirmDialog(UpdateCaseActivity.this, getString(R.string.confirm_delete_all_image), new View.OnClickListener() {
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

        pdfFileAdapter = new PdfFileAdapter(this, documentPdfList, rv_pdf, new PdfFileAdapter.PostItemListener() {
            @Override
            public void onPostClick(final long id_Click) {
//                String url = AppContants.BaseUrlDownloadPdf+PdfListResult.get((int) id_Click).getId();
//                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                startActivity(browserIntent)
                progressLoading_full.setVisibility(View.VISIBLE);
                isClickViewPdf = true;
                String url = AppContants.BaseUrlDownloadPdf+PdfListResult.get((int) id_Click).getId();
                path_pdf_file = caseReturnArr.getDataEntrySales().getAppNumber()+"_"+
                        caseReturnArr.getDataEntrySales().getMobileCustomerName().toString()+"_"+documentPdfList.get((int) id_Click );
                String path = AppContants.sdcard +Environment.DIRECTORY_DOWNLOADS + "/" + getString(R.string.app_name)+ "/" + path_pdf_file + ".pdf";

                File folder = new File(path);
                boolean success = true;
                if (folder.exists()) {
                    success = folder.delete();
                }
                if(success){
                    DownloadFromUrl(url,
                            caseReturnArr.getDataEntrySales().getAppNumber()+"_"+
                                    caseReturnArr.getDataEntrySales().getMobileCustomerName().toString()+"_"+documentPdfList.get((int) id_Click ));
                }
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                startActivity(browserIntent);
            }
        }, new PdfFileAdapter.PostLongItemListener() {
            @Override
            public void onPostLongClick(final long id_Click) {
                DialogCall.showConfirmDialog(UpdateCaseActivity.this, getString(R.string.confirm_download_file_pdf), new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(View v) {
                        int id = v.getId();
                        if (id == R.id.btn_ok) {
                            String url = AppContants.BaseUrlDownloadPdf+PdfListResult.get((int) id_Click).getId();
                            DownloadFromUrl(url,
                                    caseReturnArr.getDataEntrySales().getAppNumber()+"_"+
                                            caseReturnArr.getDataEntrySales().getMobileCustomerName().toString()+"_"+documentPdfList.get((int) id_Click ));
                        } else if (id == R.id.btn_cancel) {
                        }
                    }
                });
            }

            @Override
            public void onChangeImageClick(long id) {

            }
        });
        progressLoading_pdf.setVisibility(View.VISIBLE);
        RecyclerView.LayoutManager layoutManagerPdf= new LinearLayoutManager(this);
        rv_pdf.setLayoutManager(layoutManagerPdf);
        rv_pdf.setAdapter(pdfFileAdapter);
        rv_pdf.setHasFixedSize(true);

        ItemOffsetDecoration itemDecorationPdf = new ItemOffsetDecoration(this, R.dimen._5sdp);
        rv_pdf.addItemDecoration(itemDecorationPdf);
        final GridLayoutManager managerPdf = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        rv_pdf.setLayoutManager(managerPdf);

        rv_pdf.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        mAdapter = new ImageUploadAdapter(this, imageUploadSelectedArrayList, mRecyclerView, new ImageUploadAdapter.PostItemListener() {

            @Override
            public void onPostClick(final long idClick) {
                DialogCall.showImageFull(UpdateCaseActivity.this, imageUploadSelectedArrayList.get((int) idClick).getUrl(), new View.OnClickListener() {
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
                Intent intent = new Intent(UpdateCaseActivity.this, AlbumSelectActivity.class);
                intent.putExtra(Constants.INTENT_EXTRA_LIMIT, AppContants.LIMIT_IMAGE_CHANGE);
                startActivityForResult(intent, Constants.REQUEST_CODE);
            }
        });

        schemeAdapter= new ArrayAdapter<String>(UpdateCaseActivity.this,android.R.layout.simple_spinner_item, termLoadNameSelectedList);
        schemeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        caseadd_document_type.setAdapter(schemeAdapter);
        caseadd_document_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imageUploadSelectedArrayList.clear();
                for(int i =0;i<imageUploadArrayList.size();i++){
                    if(imageUploadArrayList.get(i).
                            getSchemeCode().equals(termLoadCodeSelectedList.get(position))){
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
        progressLoading.setVisibility(View.VISIBLE);
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
//                if (validateListDocument()) {
                    DialogCall.showConfirmDialog(UpdateCaseActivity.this, getString(R.string.confirm_upload_image_Case), new View.OnClickListener() {
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
//                }
            }
        });
        validateListDocument();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(onComplete);
    }
    BroadcastReceiver onComplete = new BroadcastReceiver() {

        public void onReceive(Context ctxt, Intent intent) {




            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);


            Log.e("IN", "" + referenceId);

            list.remove(referenceId);


            if (list.isEmpty())
            {
                Log.e("INSIDE", "" + referenceId);
                @SuppressLint("ResourceAsColor") NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(UpdateCaseActivity.this)
                                .setSmallIcon(R.drawable.ic_stat_onesignal_default)
                                .setContentTitle(getString(R.string.app_name))
                                .setContentText(getString(R.string.download_complete));


                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(455, mBuilder.build());
                if(isClickViewPdf){
                    progressLoading_full.setVisibility(View.GONE);
                    String path = Environment.DIRECTORY_DOWNLOADS + "/" + getString(R.string.app_name)+ "/" + path_pdf_file + ".pdf";
//                    File targetFile = new File(path);
//                    Uri targetUri = Uri.fromFile(targetFile);
                    File file = new File(AppContants.sdcard +path);
                    Intent target = new Intent(Intent.ACTION_VIEW);
                    target.setDataAndType(Uri.fromFile(file),"application/pdf");
                    target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                    Intent intents = Intent.createChooser(target, "Open File");
                    try {
                        startActivity(intents);
                    } catch (ActivityNotFoundException e) {
                        // Instruct the user to install a PDF reader here, or something
                    }
                    isClickViewPdf = false;
                }

            }

        }
    };

    public void DownloadFromUrl(String url,String name){
        list.clear();
        Download_Uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.addRequestHeader("Authorization", authHeader);
        request.setAllowedOverRoaming(false);
        request.setTitle(getString(R.string.app_name) + " "+getString(R.string.Downloading) +" "+ getString(R.string.sample) + ".pdf");
        request.setDescription(getString(R.string.Downloading) + " "+getString(R.string.sample) + ".pdf");
        request.setVisibleInDownloadsUi(true);
        File folder = new File(Environment.DIRECTORY_DOWNLOADS+"/"+getString(R.string.app_name));
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
//        if (success) {
//            // Do something on success
//        }
            // Do something else on failure
//        File root = new File(Environment.getExternalStorageDirectory(), getString(R.string.app_name));
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/"+ getString(R.string.app_name)+ "/" + name + ".pdf");
//        request.setDestinationInExternalPublicDir(Environment.getExternalStorageDirectory().toString(), "/"+ getString(R.string.app_name)+ "/" + name + ".pdf");

        refid = downloadManager.enqueue(request);

        Log.e("OUT", "" + url);

        list.add(refid);


    }

    private void getSchemeUpdate(){
        token= p.getString(AppContants.TOKEN_LOGIN,"");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token",token.toString());
        jsonObject.addProperty("mobileAppCode",mobileAppCode.toString());
        AppContants.AUTHEN = authHeader;
        mService.getCheckListReturn(jsonObject,authHeader).enqueue(new Callback<ResultSchemeUpdate>() {
            @Override
            public void onResponse(Call<ResultSchemeUpdate> call, Response<ResultSchemeUpdate> response) {

//                if(response.isSuccessful()) {
                if(response.body() != null) {
                    if (response.body().getResult() != null) {
                        if (response.body().getResult().getHttpCode() == 200) {
                            if (response.body().getResult() != null) {
                                p.putString(AppContants.TOKEN_LOGIN, response.body().getResult().getToken());
                            }
                            resultSchemeUpdate.setResult(response.body().getResult());
                            updateView();
                            loadListPdf();
                        } else {
                            progressLoading_not_invite.setVisibility(View.GONE);
                            String message = "";
                            if (response.body().getResult() != null) {
                                message = response.body().getResult().getHttpMessage();
                            }
                            DialogCall.showWaringDialog(UpdateCaseActivity.this, message, new View.OnClickListener() {
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
            public void onFailure(Call<ResultSchemeUpdate> call, Throwable t) {
                progressLoading_not_invite.setVisibility(View.GONE);
                DialogCall.showWaringDialog(UpdateCaseActivity.this, getString(R.string.cannot_connect_server), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });
    }

    private void updateView(){
        if(resultSchemeUpdate!=null){
            for (int i = 0;i<resultSchemeUpdate.getResult().getChecklistArr().size();i++){

//            itemList.add(new Item(resultCreateNewCase.getResult().getChecklistArr().get(i).getGroupName(),
//                    Item.ItemType.ONE_ITEM,resultCreateNewCase.getResult().getChecklistArr().get(i).getMandatory()));
                for(int k = 0;k<resultSchemeUpdate.getResult().getChecklistArr().get(i).getDocuments().size();k++){
                    if(resultSchemeUpdate.getResult().getChecklistArr().get(i).getMandatory() == 1){
                        if(!arrDocumentValid.contains(resultSchemeUpdate.getResult().getChecklistArr().get(i).getGroupName())){
                            arrDocumentValid.add(resultSchemeUpdate.getResult().getChecklistArr().get(i).getGroupName());
                        }

                    }
                    arrDocumentGroup.add(resultSchemeUpdate.getResult().getChecklistArr().get(i).getGroupName());
                    if(k==0){
                        itemList.add(new Item(resultSchemeUpdate.getResult().getChecklistArr().get(i).getGroupName(),
                                Item.ItemType.ONE_ITEM ,resultSchemeUpdate.getResult().getChecklistArr().get(i).getMandatory()
                                ,resultSchemeUpdate.getResult().getChecklistArr().get(i).getDocuments().get(k).getDocumentName()
                                ,resultSchemeUpdate.getResult().getChecklistArr().get(i).getDocuments().get(k).getDocumentCode(),0));
                    }else {
                        itemList.add(new Item("",
                                Item.ItemType.TWO_ITEM ,resultSchemeUpdate.getResult().getChecklistArr().get(i).getMandatory()
                                ,resultSchemeUpdate.getResult().getChecklistArr().get(i).getDocuments().get(k).getDocumentName()
                                ,resultSchemeUpdate.getResult().getChecklistArr().get(i).getDocuments().get(k).getDocumentCode(),0));
                    }
                    termLoadCodeList.add(resultSchemeUpdate.getResult().getChecklistArr().get(i).getDocuments().get(k).getDocumentCode());
                    checkListArr.add(resultSchemeUpdate.getResult().getChecklistArr().get(i).getDocuments().get(k).getDocumentName());
                    variableMappingList.add(resultSchemeUpdate.getResult().getChecklistArr().get(i).getDocuments().get(k).getMapBpmVar());
                }


            }
            token = resultSchemeUpdate.getResult().getToken().toString();
        }
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
        progressLoading_full.setVisibility(View.VISIBLE);
        if (requestCode == Constants.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            if(isChangeImage){
                isChangeImage = false;
                final ArrayList<Image> images = data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);
                StringBuffer stringBuffer = new StringBuffer();
               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       try {
                           for (int i = (images.size()-1), l = -1; i > l; i--) {
                               for (String extension : AppContants.okFileExtensions)
                               {
                                   if (images.get(i).path.toLowerCase().endsWith(extension)) {
                                       if((images.get(i).path.substring(images.get(i).path.lastIndexOf("/")+1)).length() <= 100) {
                                           for (int k = 0; k < imageUploadArrayList.size(); k++) {
                                               if (imageUploadArrayList.get(k).equals(imageUploadSelectedArrayList.get((int) posImageChange))) {
                                                   File s = new File(images.get(i).path);
                                                   imageUploadArrayList.get(k).setUrl(decodeFile(s, images.get(i).path.substring(images.get(i).path.lastIndexOf("/") + 1), images.get(i).path));
                                               }
                                           }
                                           imageUploadSelectedArrayList.get(posImageChange).setUrl(images.get(i).path);
                                           runOnUiThread(new Runnable() {
                                               @Override
                                               public void run() {
                                                   mAdapter.notifyDataSetChanged();
//                                       progressLoading_full.setVisibility(View.GONE);
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
//                                   mAdapter.notifyDataSetChanged();
                                   progressLoading_full.setVisibility(View.GONE);
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
                        try {
                            for (int i = (images.size()-1), l = -1; i > l; i--) {
                                for (String extension : AppContants.okFileExtensions)
                                {
                                    if (images.get(i).path.toLowerCase().endsWith(extension)) {
                                        if((images.get(i).path.substring(images.get(i).path.lastIndexOf("/")+1)).length() <= 100) {
//                stringBuffer.append(images.get(i).path);
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
//
//                                    for (int i = 0; i < imageUploadArrayList.size(); i++) {
//                                        if (imageUploadArrayList.get(i).getSchemeCode().equals(termLoadCode)) {
//                                            imageUploadSelectedArrayList.add(imageUploadArrayList.get(i));
//                                        }
//                                    }

                                    if (!termLoadNameSelectedList.contains(termLoadNameSelected)) {
                                        termLoadNameSelectedList.add(termLoadNameSelected);
                                        termLoadCodeSelectedList.add(termLoadCode);
                                    }


                                    for (int i = 0; i < termLoadCodeSelectedList.size(); i++) {
                                        if (termLoadCodeSelectedList.get(i).equals(termLoadCode)) {
                                            caseadd_document_type.setSelection(i);
                                        }
                                    }
                                    if (!arrDocumentValidChecked.contains(documentValidChecked)) {
                                        arrDocumentValidChecked.add(documentValidChecked);
                                    }
                                    schemeAdapter.notifyDataSetChanged();
                                    mAdapter.notifyDataSetChanged();
                                    progressLoading.setVisibility(View.GONE);
                                    progressLoading_full.setVisibility(View.GONE);
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
            if(imgFile.exists())            {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            for (String extension : AppContants.okFileExtensions)
                            {
                                if (imageFilePath.toLowerCase().endsWith(extension)) {
                                    File s = new File(imageFilePath);
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
                                    progressLoading_full.setVisibility(View.GONE);
                                    validateListDocument();
                                }
                            });
                        }
                    }
                }).start();
            }
            itemList.get(item_document_selected).setStatus(1);
        }else {
            isChangeImage = false;
            progressLoading_full.setVisibility(View.GONE);
//            itemList.get(item_document_selected).setStatus(0);
        }
    }

    private boolean validateListDocument(){
        String validDocument = "";
        for(int i = 0;i<arrDocumentValid.size();i++){
            if(!arrDocumentValidChecked.contains(arrDocumentValid.get(i))){
                validDocument = validDocument + arrDocumentValid.get(i) + "\n";
            }
        }
        caseadd_error_tv_image_total.setText(validDocument.trim());
        if(arrDocumentValidChecked.containsAll(arrDocumentValid)){
            caseadd_error_ll_image.setVisibility(View.GONE);
            return true;
        }else {
            caseadd_error_ll_image.setVisibility(View.VISIBLE);
            return false;
        }
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
    public void showDialogSelect(final Activity mContext, ArrayList<Item> item, final View.OnClickListener listener){
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
//                mPhotoPicker.startPickChoice(false);
                if(isCapture){
                    openCameraIntent();
                }else {
                    Intent intent = new Intent(UpdateCaseActivity.this, AlbumSelectActivity.class);
                    intent.putExtra(Constants.INTENT_EXTRA_LIMIT, AppContants.LIMIT_IMAGE_SELECT);
                    startActivityForResult(intent, Constants.REQUEST_CODE);
                }
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

        for (int i = 0;i<imageUploadArrayList.size();i++){
            File file = new File(imageUploadArrayList.get(i).getUrl());
            if(file.exists()){
                if(!fileUpload.contains(imageUploadArrayList.get(i).getUrl())){
                    fileUpload.add(imageUploadArrayList.get(i).getUrl());
                }
                String mime = "";
                mime = getMimeTypeFromPath(imageUploadArrayList.get(i).getUrl());
                JSONObject jsonObject = new JSONObject();
                try {
                    String filename = imageUploadArrayList.get(i).getUrl().substring(imageUploadArrayList.get(i).getUrl().lastIndexOf("/")+1);
                    jsonObject.put("mobileFileName", String.valueOf(filename));
                    jsonObject.put("variableMapping",imageUploadArrayList.get(i).getVariableMapping());
                    jsonObject.put("documentCode",imageUploadArrayList.get(i).getSchemeCode()+"");
                    jsonObject.put("mimetype",mime);
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
        fileUpload.add(generateNoteOnSD(this,"uploadType.txt",bodyTxtFile.toString()));
        File root = new File(Environment.getExternalStorageDirectory(), getString(R.string.app_name));
        zipPath = root.getAbsolutePath().toString()+"/uploadFile.zip";
        zip(fileUpload,zipPath,zipPath);

//        Compress compress = new Compress(fileUpload,path);
//        compress.zip();

    }
    private boolean copyFile(File src,File dst)throws IOException {
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
            DialogCall.showConfirmDialog(UpdateCaseActivity.this, getString(R.string.confirm_zip_file_error), new View.OnClickListener() {
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
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
    private void upload(String fileUriPath, final String authHeader){
        if(isNetworkConnected()){
        String url = AppContants.BaseUrlUploadReturn;

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
                    DialogCall.showWaringDialog(UpdateCaseActivity.this,getString(R.string.upload_files_error),new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onClick(View v) {
                            int id = v.getId();
                            if (id == R.id.btn_ok) {
//                                upload(zipPath,authHeader);
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
                        DialogCall.showWaringDialog(UpdateCaseActivity.this,getString(R.string.upload_files_success),new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onClick(View v) {
                                int id = v.getId();
                                if (id == R.id.btn_ok) {
                                    Intent i = new Intent(UpdateCaseActivity.this,MainActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            }
                        });
                        progressLoading_not_invite.setVisibility(View.GONE);
                    }
                    else {
                        String message = serverResponse.getBodyAsString().toString();
                        DialogCall.showWaringDialog(UpdateCaseActivity.this,getString(R.string.upload_files_error),new View.OnClickListener() {
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
        DialogCall.showWaringDialog(UpdateCaseActivity.this,getString(R.string.internet_not_connected),new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                int id = v.getId();
                if (id == R.id.btn_ok) {
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
        Call<UploadResponse> call = mService.uploadUpdate(authHeader,description, body);
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
                        DialogCall.showWaringDialog(UpdateCaseActivity.this,getString(R.string.upload_files_success),new View.OnClickListener() {
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
                DialogCall.showWaringDialog(UpdateCaseActivity.this,getString(R.string.upload_files_error),new View.OnClickListener() {
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

    private void loadListPdf(){
        progressLoading_pdf.setVisibility(View.VISIBLE);
        token= p.getString(AppContants.TOKEN_LOGIN,"");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token",token.toString());
        jsonObject.addProperty("mobileAppCode",mobileAppCode);
        mService.getListPdf(jsonObject,authHeader).enqueue(new Callback<PdfResult>() {
            @Override
            public void onResponse(Call<PdfResult> call, Response<PdfResult> response) {

                if(response.body()!=null){
                    if(response.body().getResult().getHttpCode() == 200){
                        if(response.body().getResult()!=null){
                            p.putString(AppContants.TOKEN_LOGIN,response.body().getResult().getToken());
                            token= response.body().getResult().getToken();
                        }
                        documentPdfList.clear();
                        PdfListResult.clear();
                        if(response.body().getResult().getPDFList()!=null){
                            for (int i = 0;i<response.body().getResult().getPDFList().size();i++){
                                for (int k = 0;k<itemList.size();k++){
                                    if(itemList.get(k).getTermLoanCode().equals(response.body().getResult().getPDFList().get(i).getDocumentType())){
                                        if(!documentPdfList.contains(itemList.get(k).getValue().toString())){
                                            documentPdfList.add(itemList.get(k).getValue().toString());
                                            PdfListResult.add(response.body().getResult().getPDFList().get(i));
                                        }
                                    }
                                }

                            }
                        }

                        pdfFileAdapter.notifyDataSetChanged();
                        progressLoading_pdf.setVisibility(View.GONE);
                    }else {
                        if(response.body().getResult()!=null){
                            p.putString(AppContants.TOKEN_LOGIN,response.body().getResult().getToken());
                        }
                        DialogCall.showWaringDialog(UpdateCaseActivity.this,getString(R.string.get_dpf_case_error),new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int id = v.getId();
                                if (id == R.id.btn_ok) {

                                }
                            }
                        });
                        progressLoading_pdf.setVisibility(View.GONE);
                    }
                }}

            @Override
            public void onFailure(Call<PdfResult> call, Throwable t) {
                progressLoading_not_invite.setVisibility(View.GONE);
                DialogCall.showWaringDialog(UpdateCaseActivity.this, getString(R.string.cannot_connect_server), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });
    }

    public void showDialogCaseNote(final Activity mContext, ArrayList<AppNotesEntry> item,String app_id, final View.OnClickListener listener){
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
                if(edt_message.getText().toString().trim().length()>0) {
                    sendCaseNote(caseReturnArr.getDataEntrySales().getMobileAppCode(), edt_message.getText().toString().trim(), progressLoading_not_invite, txtSend, edt_message,item_list);
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

    private void getCaseNote(String app_id, final ProgressBar progressLoading_not_invite,final RecyclerView item_list){
        progressLoading_not_invite.setVisibility(View.VISIBLE);
        mService.getCaseNote(app_id).enqueue(new Callback<CaseNoteResult>() {
            @Override
            public void onResponse(Call<CaseNoteResult> call, Response<CaseNoteResult> response) {
                if(response.body() != null) {
                    if (response.body().getAppNotesEntries() != null) {
                        if (response.body().getAppNotesEntries().getAppNotesEntry()!= null) {
                            appNotesEntryArrayList.clear();
                            progressLoading_not_invite.setVisibility(View.GONE);
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
                DialogCall.showWaringDialog(UpdateCaseActivity.this, getString(R.string.cannot_connect_server), new View.OnClickListener() {
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
                        appNotesEntry.setAppUid(caseReturnArr.getDataEntrySales().getAppId().toString());
                        appNotesEntry.setUsrUid(caseReturnArr.getDataEntrySales().getSaleCode().toString());
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
                        DialogCall.showWaringDialog(UpdateCaseActivity.this,message,new View.OnClickListener() {
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
                DialogCall.showWaringDialog(UpdateCaseActivity.this, getString(R.string.cannot_connect_server), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });
    }
}
