package com.mcredit.mobile.mobile_for_sale.Utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


import com.mcredit.mobile.mobile_for_sale.Base.PermissionActivity;
import com.mcredit.mobile.mobile_for_sale.R;

import java.io.File;
import java.util.Arrays;
import java.util.List;


/**
 * Created by WilliZ on 16/03/20.
 */
public class PhotoPicker {
    public static final int REQUEST_TAKE_PHOTO = 1100;
    public static final int REQUEST_PICK_PHOTO = 1200;
    public static final int REQUEST_CUT_PHONTO = 1300;

    private PermissionActivity mActivity;
    private android.support.v4.app.Fragment mFragment;
    private String mHintTake, mHintPick;
    private String mTmpPhotoPath;
    AlertDialog dialog;
    private Pref p;

    public PhotoPicker(PermissionActivity activity) {
        mActivity = activity;
        Resources res = activity.getResources();
        mHintTake = res.getString(R.string.hint_upload_photo_take);
        mHintPick = res.getString(R.string.hint_upload_photo_pick);
        p = new Pref(mActivity);
    }

    public PhotoPicker(android.support.v4.app.Fragment fragment, PermissionActivity activity) {
        mFragment = fragment;
        mActivity = activity;
        Resources res = activity.getResources();
        mHintTake = res.getString(R.string.hint_upload_photo_take);
        mHintPick = res.getString(R.string.hint_upload_photo_pick);
        p = new Pref(mActivity);
    }

    public void startPickChoice(boolean isCamera){
        if(isCamera){
            mActivity.checkPermission(Arrays.asList(Manifest.permission.WRITE_EXTERNAL_STORAGE), new PermissionActivity.OnCheckPermissionListener() {
                @Override
                public void checkResult(List<String> listPermission, boolean isSuccess) {
                    if(isSuccess){
                        try {
                            mTmpPhotoPath = takePhoto();
                        } catch (Exception e) {
                            String ss = mActivity.getResources().getString(R.string.hint_sdcard_not_mounted);
                            Utils.toast(mActivity, ss);
                        }
                    }
                }
            });

        }else {
            pickPhoto();
        }
    }
    public void startPick() {
        String[] hints = new String[]{mHintTake, mHintPick};
        final AlertDialog builder = new AlertDialog.Builder(mActivity,R.style.AppThemeBase).create();
        builder.show();
        builder.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE| WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        builder.setCanceledOnTouchOutside(false);
        builder.setCancelable(false);
        if (builder!=null){
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            builder.getWindow().setLayout(width, height);
        }
        Window window = builder.getWindow();
        window.setContentView(R.layout.dialog_popup_get_image);
        ImageView imgNewPick = (ImageView) window.findViewById(R.id.imgNewPick);
        ImageView imgGetPickGalery = (ImageView) window.findViewById(R.id.imgGetPickGalery);
        TextView txtBackGround = (TextView)window.findViewById(R.id.txtBackGround);

        imgNewPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mTmpPhotoPath = takePhoto();
                } catch (Exception e) {
                    String ss = mActivity.getResources().getString(R.string.hint_sdcard_not_mounted);
                    Utils.toast(mActivity, ss);
                }
                builder.dismiss();
            }
        });

        txtBackGround.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });

        imgGetPickGalery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPhoto();
                builder.dismiss();
            }
        });
//        dialog = new AlertDialog.Builder(mActivity).setItems(hints, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                switch (which) {
//                    case 0: // take picture
//                        try {
//                            mTmpPhotoPath = takePhoto();
//                        } catch (Exception e) {
//                            String ss = mActivity.getResources().getString(R.string.hint_sdcard_not_mounted);
//                            Utils.toast(mActivity, ss);
//                        }
//                        dialog.dismiss();
//                        break;
//                    case 1: // pick picture
//                        pickPhoto();
//                        dialog.dismiss();
//                        break;
//                    default:
//                        dialog.dismiss();
//                        break;
//                }
//
//            }
//        }).show();
    }

    Uri takePhotoUri;

    private String takePhoto() {
                    String filePath;

                    if (!FileUtils.isSDMounted()) { // sdcard mounted
                        throw new NullPointerException("sdcard not mounted, cannot take photo.");
                    }
                    File dir = new File(FileUtils.DCIM);
                    if (!dir.exists()) {
                        dir.mkdir();
                    }

                    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES), "MyCameraApp");
                    if (! mediaStorageDir.exists()){
                        mediaStorageDir.mkdirs();
                        if (! mediaStorageDir.mkdirs()){
                            return null;
                        }
                    }
                    File picFile;
                    picFile = new File(mediaStorageDir.getPath() + File.separator +
                            "Mcredit_"+ System.currentTimeMillis() + ".jpg");
//        String file = "jetkeys" + System.currentTimeMillis() + ".jpg";
//        File picFile = new File(dir, file);
//        filePath = picFile.getAbsolutePath();
                    takePhotoUri = Uri.fromFile(picFile);
                    filePath = takePhotoUri.toString();
                    Intent take = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    take.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    take.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                    take.putExtra(MediaStore.EXTRA_OUTPUT, takePhotoUri);
                    if (mFragment != null) {
                        mFragment.startActivityForResult(take, REQUEST_TAKE_PHOTO);
                    } else {
                        mActivity.startActivityForResult(take, REQUEST_TAKE_PHOTO);
                    }
                    return filePath;
    }

    private void pickPhoto() {
        mActivity.checkPermission(Arrays.asList(Manifest.permission.READ_EXTERNAL_STORAGE), new PermissionActivity.OnCheckPermissionListener() {
            @Override
            public void checkResult(List<String> listPermission, boolean isSuccess) {
                if(isSuccess){
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);// ACTION_OPEN_DOCUMENT
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/jpeg");
                    if (mFragment != null) {
                        mFragment.startActivityForResult(intent, REQUEST_PICK_PHOTO);
                    } else {
                        mActivity.startActivityForResult(intent, REQUEST_PICK_PHOTO);
                    }
                }
            }
        });

    }

    private String cutPhoto(Uri uri) {
        if (!FileUtils.isSDMounted()) { // sdcard mounted
            throw new NullPointerException("sdcard not mounted, cannot take photo.");
        }
        File dir = new File(FileUtils.DCIM);
        if (!dir.exists()) {
            dir.mkdir();
        }
        String filePath;
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        if (! mediaStorageDir.exists()){
            mediaStorageDir.mkdirs();
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        File picFile;
        picFile = new File(mediaStorageDir.getPath() + File.separator +
                    "SoKhop_"+ System.currentTimeMillis() + ".jpg");
//        String file = "jetkeys" + System.currentTimeMillis() + ".jpg";
//        File picFile = new File(dir, file);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(new File(uri.getPath()).getAbsolutePath(), options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        Log.d("picFilessss","sss"+uri+"ss"+imageWidth);
//        filePath = picFile.getAbsolutePath();
        Uri uriOut = Uri.fromFile(picFile);
        filePath = uriOut.toString();
        Intent intent = new Intent("com.android.camera.action.CROP");

        intent.setDataAndType(uri, "image/*");

        Log.d("NUMBER_IMAGE_CHANGE","ss"+p.getString(Pref.NUMBER_IMAGE_CHANGE,null));
        if(p.getString(Pref.NUMBER_IMAGE_CHANGE,null)!= null &&p.getString(Pref.NUMBER_IMAGE_CHANGE,null).equals("0")){
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
        }else  if(p.getString(Pref.NUMBER_IMAGE_CHANGE,null)!= null &&p.getString(Pref.NUMBER_IMAGE_CHANGE,null).equals("1")){
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 2);
            intent.putExtra("aspectY", 1);
        }else{

        }
//        intent.putExtra("crop", "true");

        intent.putExtra("scale", true);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriOut);

        intent.putExtra("return-data", false);

        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        intent.putExtra("noFaceDetection", true); // no face detection
        if (mFragment != null) {
            mFragment.startActivityForResult(intent, REQUEST_CUT_PHONTO);
        } else {
            mActivity.startActivityForResult(intent, REQUEST_CUT_PHONTO);
        }
        return filePath;
    }

    public void close() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public String onActivityResult(int requestCode, int resultCode, Intent data) {
//    	Log.d("doubleL", "[PhotoPicker.java]画像データの取得開始");
//        if(data == null){
//            return null;
//        }else{
        if (resultCode != Activity.RESULT_OK && (requestCode == REQUEST_PICK_PHOTO || requestCode == REQUEST_TAKE_PHOTO)) {
            mTmpPhotoPath = null;
        }
        switch (requestCode) {
            case REQUEST_PICK_PHOTO:
                if(data!=null){
                    Uri photoUri = data.getData();
                    mTmpPhotoPath = cutPhoto(photoUri);
                }
//                mTmpPhotoPath = FileUtils.getPath(mActivity, photoUri);
                return "";
            case REQUEST_TAKE_PHOTO:

                mTmpPhotoPath = cutPhoto(takePhotoUri);
                return "";
            case REQUEST_CUT_PHONTO:
                return mTmpPhotoPath;
            default:
                break;
        }
        return mTmpPhotoPath;
//        }
    }
}