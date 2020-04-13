package com.mcredit.mobile.mobile_for_sale.Api.Service;


import com.mcredit.mobile.mobile_for_sale.Models.CancelCase.CancelCase;
import com.mcredit.mobile.mobile_for_sale.Models.CaseNote.CaseNoteResult;
import com.mcredit.mobile.mobile_for_sale.Models.CreateNewCase.ResultCreateNewCase;
import com.mcredit.mobile.mobile_for_sale.Models.DeleteDraftCase.DeleteDraftCaseResult;
import com.mcredit.mobile.mobile_for_sale.Models.DraftCase.DraftResult;
import com.mcredit.mobile.mobile_for_sale.Models.GetCat.GetCatInfo;
import com.mcredit.mobile.mobile_for_sale.Models.GetInfoNewCase.ResultInfoNewCase;
import com.mcredit.mobile.mobile_for_sale.Models.GetPDF.PdfResult;
import com.mcredit.mobile.mobile_for_sale.Models.GetSchemeUpdate.ResultSchemeUpdate;
import com.mcredit.mobile.mobile_for_sale.Models.Login.LoginSuccess;
import com.mcredit.mobile.mobile_for_sale.Models.MainModel.MainObject;
import com.mcredit.mobile.mobile_for_sale.Models.Notification.NotificationResult;
import com.mcredit.mobile.mobile_for_sale.Models.ProgressingCase.ProgressingCaseArr;
import com.mcredit.mobile.mobile_for_sale.Models.ReturnCase.ReturnCaseResult;
import com.mcredit.mobile.mobile_for_sale.Models.SendCaseNote.Result;
import com.mcredit.mobile.mobile_for_sale.Models.SendCaseNote.SendCaseNoteResult;
import com.mcredit.mobile.mobile_for_sale.Models.UploadFile.UploadResponse;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

/**
 * Created by hiepnt on 05/01/2018.
 */

public interface SOService {
//    @GET("/answers?order=desc&sort=activity&site=stackoverflow")
//    Call<SOAnswersResponse> getAnswers();
//
//    @GET("/categories")
//    Call<Category> getCategorys(
//            @Query("page") int page,
//            @Query("per_page") int per_page
//    );

    @POST("rest/auth/login")
    Call<LoginSuccess> loginToServer(@Body JsonObject imei, @Header("Authorization") String AuthorHeader, @Header("Content-Type") String content_type);

    @POST("rest/auth/logout")
    Call<LoginSuccess> logoutServer(@Header("Authorization") String AuthorHeader, @Header("Content-Type") String content_type);


    @POST("rest/case/newcase")
    Call<ResultInfoNewCase> getNewcaseInfo(@Body JsonObject userInfo, @Header("Authorization") String AuthorHeader, @Header("Content-Type") String content_type);

    @POST("rest/case/draft-case")
    Call<ResultInfoNewCase> getDraftcaseInfo(@Body JsonObject userInfo, @Header("Authorization") String AuthorHeader, @Header("Content-Type") String content_type);

    @POST("rest/case/savecase")
    Call<ResultCreateNewCase> createNewCase(@Body JsonObject Info, @Header("Authorization") String AuthorHeader, @Header("Content-Type") String content_type);

    @Multipart
    @POST("rest/case/upload")
    Call<UploadResponse> upload(@Header("Authorization") String AuthorHeader,
                                @Part("uploadingFiles") RequestBody description,
                                @Part MultipartBody.Part file
    );
    @Multipart
    @POST("rest/return-case/upload")
    Call<UploadResponse> uploadUpdate(@Header("Authorization") String AuthorHeader,
                                @Part("uploadingFiles") RequestBody description,
                                @Part MultipartBody.Part file
    );

    @Multipart
    @POST("rest/case/upload")
    Call<UploadResponse> uploadMultiFiles(@Header("Authorization") String AuthorHeader,
                                @Part("uploadingFiles") RequestBody description,
                                @Part List<MultipartBody.Part> file
    );

    @GET("rest/cat/check/{CompanyTaxNumber}")
    Call<GetCatInfo> getCatInfo(@Header("Authorization") String AuthorHeader,@Path("CompanyTaxNumber") String CompanyTaxNumber);

    @POST("rest/case/get-case-draft")
    Call<DraftResult> getDraftCase(@Header("Authorization") String AuthorHeader);

    @POST("rest/return-case/get-case")
    Call<ReturnCaseResult> getReturnCase(@Body JsonObject userInfo, @Header("Authorization") String AuthorHeader, @Header("Content-Type") String content_type);

    @POST("rest/return-case/getChecklist")
    Call<ResultSchemeUpdate> getCheckListReturn(@Body JsonObject userInfo, @Header("Authorization") String AuthorHeader);

    @POST("rest/case-status/get-case")
    Call<ProgressingCaseArr> getProgressingCase(@Body JsonObject userInfo, @Header("Authorization") String AuthorHeader, @Header("Content-Type") String content_type);

    @GET("rest/dashboard/get-info")
    Call<MainObject> getTotalInfo(@Header("Authorization") String AuthorHeader);

    @POST("rest/case-status/abort-process")
    Call<CancelCase> cancelCase(@Body JsonObject caseInfo, @Header("Authorization") String AuthorHeader);

    @POST("rest/return-case/pdf/getbymobileappcode/")
    Call<PdfResult> getListPdf(@Body JsonObject caseInfo, @Header("Authorization") String AuthorHeader);

    @POST("rest/notification/get-message")
    Call<NotificationResult> getNotification(@Body JsonObject caseInfo, @Header("Authorization") String AuthorHeader);

    @GET("rest/case/get-note/{app_id}")
    Call<CaseNoteResult> getCaseNote(@Path("app_id") String app_id);

    @POST("rest/case/create-note")
    Call<SendCaseNoteResult> sendCaseNote(@Body JsonObject caseInfo, @Header("Authorization") String AuthorHeader);

    @POST("rest/case/cancel-case")
    Call<DeleteDraftCaseResult> deleteDraftCase(@Body JsonObject caseInfo, @Header("Authorization") String AuthorHeader);
}