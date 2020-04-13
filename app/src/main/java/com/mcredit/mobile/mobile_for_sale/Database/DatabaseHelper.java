package com.mcredit.mobile.mobile_for_sale.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mcredit.mobile.mobile_for_sale.Models.Database.DraftCase;
import com.mcredit.mobile.mobile_for_sale.Models.Database.DraftImage;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by hiep on 10/12/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "case.sqlite";
    private static String TABLE_WORD = "case";
    private String ImageLink;

    private static String TABLE_IMAGE = "imageCase";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createDraftCase(db);
        createDraftImage(db);
    }

    private void createDraftCase(SQLiteDatabase db) {
        String createQuery = "CREATE TABLE IF NOT EXISTS " +
                "draftCase(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "fullname TEXT ,"+
                "card TEXT ,"+
                "date_card TEXT , " +
                "termloan TEXT,"+
                "document_type_value TEXT,"+
                "document_type_code TEXT,"+
                "kiosk_value TEXT,"+
                "kiosk_code TEXT,"+
                "kiosk_address TEXT,"+
                "cat_type TEXT,"+
                "company_name TEXT,"+
                "insurance TEXT,"+
                "signContractAddress TEXT,"+
                "money TEXT,"+
                "tax_code TEXT,"+
                "mobileAppCode TEXT,"+
                "isFromDevice TEXT,"+
                "username TEXT)";
        db.execSQL(createQuery);
    }

    private void createDraftImage(SQLiteDatabase db) {
        String createQuery = "CREATE TABLE IF NOT EXISTS " +
                TABLE_IMAGE+"(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "code_id TEXT,"+
                "mobileAppCode TEXT ,"+
                "documentGroup TEXT ,"+
                "documentCode TEXT , " +
                "linkImage TEXT , " +
                "variableMapping TEXT , " +
                "documentName TEXT)";
        db.execSQL(createQuery);
    }

    public void insertADraftImage(String code_id,String mobileAppCode,String documentGroup,String documentCode,String linkImage,String variableMapping,String documentName) {

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Insert into "+ TABLE_IMAGE+"(code_id,mobileAppCode,documentGroup,documentCode,linkImage,variableMapping,documentName) values " +
                "( "
                +"'"+ code_id +"', "
                +"'"+ mobileAppCode +"', "
                +"'"+  documentGroup + "', "
                +"'"+  documentCode + "', "
                +"'"+  linkImage + "', "
                +"'"+  variableMapping + "', "
                +"'"+  documentName + "');";
        db.execSQL(query);
        close();
    }
    public ArrayList<DraftImage> getAllDraftImage() {
        ArrayList<DraftImage> draftImages = new ArrayList<DraftImage>();
        // Select All Query
        String selectQuery = "Select * from "+ TABLE_IMAGE+" ORDER BY _id DESC ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        try {
            if (cursor.moveToFirst()) {
                do {
                    DraftImage draftImage = new DraftImage();
                    draftImage.setCode_id(cursor.getString(1));
                    draftImage.setMobileAppCode(cursor.getString(2));
                    draftImage.setDocumentGroup(cursor.getString(3));
                    draftImage.setDocumentCode(cursor.getString(4));
                    draftImage.setLinkImage(cursor.getString(5));
                    draftImage.setVariableMapping(cursor.getString(6));
                    draftImage.setDocumentName(cursor.getString(7));
                    // Adding contact to list
                    draftImages.add(draftImage);
                } while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cursor.close();
        }
        close();
        return draftImages;
    }
    public ArrayList<DraftImage> getADraftImageByDocument(String mobileAppCode) {
        ArrayList<DraftImage> draftImages = new ArrayList<DraftImage>();
        // Select All Query
        String selectQuery = "Select * from "+ TABLE_IMAGE+" WHERE mobileAppCode = " + mobileAppCode + " ORDER BY _id ASC ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        try {
            if (cursor.moveToFirst()) {
                do {
                    DraftImage draftImage = new DraftImage();
                    draftImage.setCode_id(cursor.getString(1));
                    draftImage.setMobileAppCode(cursor.getString(2));
                    draftImage.setDocumentGroup(cursor.getString(3));
                    draftImage.setDocumentCode(cursor.getString(4));
                    draftImage.setLinkImage(cursor.getString(5));
                    draftImage.setVariableMapping(cursor.getString(6));
                    draftImage.setDocumentName(cursor.getString(7));
                    // Adding contact to list
                    draftImages.add(draftImage);
                } while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cursor.close();
        }
        close();
        return draftImages;
    }
    public void deleteADraftImageByMobileAppCode(String mobileAppCode){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "Delete from "+ TABLE_IMAGE+" WHERE mobileAppCode = " + mobileAppCode + ";";
        db.execSQL(sql);
        close();
    }
    public void updateADraftImage(String code_id,String mobileAppCode,String documentGroup,String documentCode,String linkImage,String variableMapping,String documentName) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE "+ TABLE_IMAGE+" SET "+
                "code_id="+"'"+ code_id +"', "+
                "mobileAppCode="+"'"+ mobileAppCode +"', "+
                "documentGroup="+"'"+ documentGroup +"', "+
                "documentCode="+"'"+ documentCode +"', "+
                "linkImage="+"'"+ documentCode +"', "+
                "variableMapping="+"'"+ variableMapping +"', "+
                "documentName="+"'"+ documentName +"', "+
                "WHERE linkImage="+"'"+ linkImage +"';";
        db.execSQL(query);
        close();
    }

    public void insertADraftCase(String fullname,String card,String date_card,String termloan,String document_type_value,
                                   String document_type_code,String kiosk_value,String kiosk_code,String kiosk_address,String cat_type,
                                   String company_name,String insurance,String signContractAddress,String money,String tax_code,String mobileAppCode,String isFromDevice,String username) {

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Insert into draftCase(fullname,card,date_card,termloan,document_type_value," +
                "document_type_code,kiosk_value,kiosk_code,kiosk_address,cat_type,company_name,insurance,signContractAddress,money,tax_code,mobileAppCode,isFromDevice,username) values " +
                "( "
                +"'"+ fullname +"', "
                +"'"+  card + "', "
                +"'"+  date_card + "', "
                +"'"+  termloan + "', "
                +"'"+ document_type_value +"', "
                +"'"+  document_type_code + "', "
                +"'"+  kiosk_value + "', "
                +"'"+  kiosk_code + "', "
                +"'"+ kiosk_address +"', "
                +"'"+  cat_type + "', "
                +"'"+  company_name + "', "
                +"'"+  insurance + "', "
                +"'"+ signContractAddress +"', "
                +"'"+  money + "', "
                +"'"+  tax_code + "', "
                +"'"+  mobileAppCode + "', "
                +"'"+  isFromDevice + "', "
                +"'"+  username + "');";
        db.execSQL(query);
        close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORD);
        onCreate(db);
    }

    public boolean hasObject(String mobileAppCode) {
        SQLiteDatabase db = getWritableDatabase();
        String selectString = "SELECT * FROM draftCase WHERE mobileAppCode" + " =?";

        // Add the String you are searching by here.
        // Put it in an array to avoid an unrecognized token error
        Cursor cursor = db.rawQuery(selectString, new String[] {mobileAppCode});

        boolean hasObject = false;
        if(cursor.moveToFirst()){
            hasObject = true;

            //region if you had multiple records to check for, use this region.

            int count = 0;
            while(cursor.moveToNext()){
                count++;
            }

        }

        cursor.close();          // Dont forget to close your cursor
        db.close();              //AND your Database!
        return hasObject;
    }
    public List<DraftCase> getAllDraftCase(String username) {
        ArrayList<DraftCase> draftCases = new ArrayList<DraftCase>();
        // Select All Query
        String selectQuery = "Select * from draftCase WHERE username = '" + username +  "' ORDER BY mobileAppCode DESC ";

        SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        try {
            if (cursor.moveToFirst()) {
                do {
                    DraftCase draftCase = new DraftCase();
                    draftCase.setFullname(cursor.getString(1));
                    draftCase.setCard(cursor.getString(2));
                    draftCase.setDate_card(cursor.getString(3));
                    draftCase.setTermloan(cursor.getString(4));
                    draftCase.setDocument_type_value(cursor.getString(5));
                    draftCase.setDocument_type_code(cursor.getString(6));
                    draftCase.setKiosk_value(cursor.getString(7));
                    draftCase.setKiosk_code(cursor.getString(8));
                    draftCase.setKiosk_address(cursor.getString(9));
                    draftCase.setCat_type(cursor.getString(10));
                    draftCase.setCompany_name(cursor.getString(11));
                    draftCase.setInsurance(cursor.getString(12));
                    draftCase.setSignContractAddress(cursor.getString(13));
                    draftCase.setMoney(cursor.getString(14));
                    draftCase.setTax_code(cursor.getString(15));
                    draftCase.setMobileAppCode(cursor.getString(16));
                    draftCase.setIsFromDevice(cursor.getString(17));
                    draftCase.setUsername(cursor.getString(18));
                    // Adding contact to list
                    draftCases.add(draftCase);
                } while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cursor.close();
        }
        close();
        return draftCases;
    }


    public List<DraftCase> getADraftCaseByTaxCode(String tax_code) {
        ArrayList<DraftCase> draftCases = new ArrayList<DraftCase>();
        // Select All Query
        String selectQuery = "Select * from draftCase WHERE tax_code = " + tax_code + "ORDER BY _id DESC ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        try {
            if (cursor.moveToFirst()) {
                do {
                    DraftCase draftCase = new DraftCase();
                    draftCase.setFullname(cursor.getString(1));
                    draftCase.setCard(cursor.getString(2));
                    draftCase.setDate_card(cursor.getString(3));
                    draftCase.setTermloan(cursor.getString(4));
                    draftCase.setDocument_type_value(cursor.getString(5));
                    draftCase.setDocument_type_code(cursor.getString(6));
                    draftCase.setKiosk_value(cursor.getString(7));
                    draftCase.setKiosk_code(cursor.getString(8));
                    draftCase.setKiosk_address(cursor.getString(9));
                    draftCase.setCat_type(cursor.getString(10));
                    draftCase.setCompany_name(cursor.getString(11));
                    draftCase.setInsurance(cursor.getString(12));
                    draftCase.setSignContractAddress(cursor.getString(13));
                    draftCase.setMoney(cursor.getString(14));
                    draftCase.setTax_code(cursor.getString(15));
                    draftCase.setMobileAppCode(cursor.getString(16));
                    draftCase.setIsFromDevice(cursor.getString(17));
                    draftCase.setUsername(cursor.getString(18));
                    // Adding contact to list
                    draftCases.add(draftCase);
                } while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cursor.close();
        }
        close();
        return draftCases;
    }

    public int getTotalDraftCase(String tax_code) {
        ArrayList<Integer> CaseId = new ArrayList<>();
        // Select All Query
        String selectQuery = "Select _id from draftCase where tax_code = "+tax_code;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        try {
            if (cursor.moveToFirst()) {
                do {
                    int a = cursor.getInt(0);
                    CaseId.add(a);
                } while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cursor.close();
        }
        close();
        return CaseId.size();
    }

    public long getDraftCount(String username) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        long count = DatabaseUtils.queryNumEntries(db, "draftCase");
//        db.close();
        String countQuery = "SELECT * FROM draftCase WHERE username = '" + username +"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
    public void deleteAllDraftCase(){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "Delete from draftCase";
        db.execSQL(sql);
        close();
    }
    public void deleteADraftCaseByMobileAppCode(String mobileAppCode){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "Delete from draftCase WHERE mobileAppCode = " + mobileAppCode + ";";
        db.execSQL(sql);
        close();
    }

        public void updateADraftCase(String fullname,String card,String date_card,String termloan,String document_type_value,
                                     String document_type_code,String kiosk_value,String kiosk_code,String kiosk_address,String cat_type,
                                     String company_name,String insurance,String signContractAddress,String money,String tax_code,String mobileAppCode,String isFromDevice,String username) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE draftCase SET "+
                "fullname="+"'"+ fullname +"', "+
                "card="+"'"+ card +"', "+
                "date_card="+"'"+ date_card +"', "+
                "termloan="+"'"+ termloan +"', "+
                "document_type_value="+"'"+ document_type_value +"', "+
                "document_type_code="+"'"+ document_type_code +"', "+
                "kiosk_value="+"'"+ kiosk_value +"', " +
                "kiosk_code="+"'"+ kiosk_code +"', "+
                "kiosk_address="+"'"+ kiosk_address +"', "+
                "cat_type="+"'"+ cat_type +"', "+
                "company_name="+"'"+ company_name +"', "+
                "insurance="+"'"+ insurance +"', "+
                "signContractAddress="+"'"+ signContractAddress +"', "+
                "money="+"'"+ money +"', " +
                "tax_code="+"'"+ tax_code +"', "+
                "mobileAppCode="+"'"+ mobileAppCode +"', "+
                "isFromDevice="+"'"+ isFromDevice +"' ,"+
                "username="+"'"+ username +"' "+
                "WHERE mobileAppCode="+"'"+ mobileAppCode +"';";
        db.execSQL(query);
        close();
    }

}
