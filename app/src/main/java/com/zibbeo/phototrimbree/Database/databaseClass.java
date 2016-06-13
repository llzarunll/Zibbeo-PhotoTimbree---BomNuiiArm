package com.zibbeo.phototrimbree.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by samchaw on 5/28/16 AD.
 */
public class databaseClass extends SQLiteOpenHelper {

    // Database Name
    private static final String DATABASE_NAME = "databaseManager";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_MASSAGE = "massage";
    private static final String TABLE_CONTACT = "contact";
    private static final String TABLE_POSTCARD = "postcard";
    private static final String TABLE_SIGNATURE = "signature";

    // Common column names
    private static final String KEY_ID = "id";

    // massage Table - column name
    private static final String KEY_MASSAGE = "massage";
    private static final String KEY_FONT_NAME = "font_name";
    private static final String KEY_FONT_SIZE = "font_size";
    private static final String KEY_SIGNATURE_ID = "signature_id";
    private static final String KEY_POSITION_FONT_TYPE = "position_font_type";
    private static final String KEY_POSITION_FONT_SIZE = "position_font_size";

    // contact Table - column name
    private static final String KEY_FIRST_NAME = "first_name";
    private static final String KEY_LINE_ONE = "line_one";
    private static final String KEY_LINE_TWO = "line_two";
    private static final String KEY_ZIP_CODE = "zip_code";
    private static final String KEY_STATE = "state";
    private static final String KEY_COUNTRY = "country";
    private static final String KEY_ENVELOP = "envelop";
    private static final String KEY_POSITION_COUNTRY = "position_country";

    // postcard Table - column name
    private static final String KEY_MASSAGE_ID = "massage_id";
    private static final String KEY_IMAGE_ID = "image_id";
    private static final String KEY_CONTACT_ID = "contact_id";
    private static final String KEY_CREATE_DATE = "create_date";
    private static final String KEY_MODIFY_DATE = "modify_date";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_IS_SENT = "is_sent";

    // signature Table - column name
    private static final String KEY_LINE_SIZE = "line_size";

    /*Nuii Create Table*/
    // Table Name
    private static final String TABLE_IMAGE_COMPOSER = "image_composer";
    private static final String TABLE_IMAGE_TEMPLATE = "image_template";
    private static final String TABLE_IMAGE = "image";
    private static final String TABLE_STICKER_TEMPLATE = "sticker_template";
    // Column name
    private static final String KEY_IMAGECOMPOSER_ID = "imagecomposer_id";
    private static final String KEY_TEMPLATE_MODEL = "template_model";
    private static final String KEY_STICKER_MODEL = "sticker_model";

    // Column name
    private static final String KEY_IMAGETEMPLATE_ID = "imagetemplate_id";
    private static final String KEY_TEMPLATE = "template";
    private static final String KEY_IMAGE_A = "image_a";
    private static final String KEY_IMAGE_B = "image_b";
    private static final String KEY_IMAGE_C = "image_c";
    private static final String KEY_IMAGE_D = "image_d";
    private static final String KEY_MARGE_ONE_STROKE = "marge_one_stroke";
    private static final String KEY_MARGE_ONE_COLOR = "marge_one_color";
    private static final String KEY_MARGE_TWO_STROKE = "marge_two_stroke";
    private static final String KEY_MARGE_TWO_COLOR = "marge_two_color";
    private static final String KEY_TOP_VALUE = "top_value";
    private static final String KEY_BOTTOM_VALUE = "bottom_value";
    private static final String KEY_RIGHT_VALUE = "right_value";
    private static final String KEY_LEFT_VALUE = "left_value";
    private static final String KEY_CENTER_X = "center_x";
    private static final String KEY_CENTER_Y = "center_y";

    // Column name
    private static final String KEY_IMAGE_MODEL_ID = "image_id";
    private static final String KEY_URL = "url";
    private static final String KEY_OFFSET_X = "offset_x";
    private static final String KEY_OFFSET_X_ENABLE = "offset_x_enable";
    private static final String KEY_OFFSET_X_ORIGINAL = "offset_x_original";
    private static final String KEY_OFFSET_X_MAX = "offset_x_max";
    private static final String KEY_OFFSET_X_MIN = "offset_x_min";
    private static final String KEY_OFFSET_Y = "offset_y";
    private static final String KEY_OFFSET_Y_ENABLE = "offset_y_enable";
    private static final String KEY_OFFSET_Y_ORIGINAL = "offset_y_original";
    private static final String KEY_OFFSET_Y_MAX = "offset_y_max";
    private static final String KEY_OFFSET_Y_MIN = "offset_y_min";
    private static final String KEY_SCALE = "scale";
    private static final String KEY_SCALE_ENABLE = "scale_enable";
    private static final String KEY_SCALE_ORIGINAL = "scale_original";
    private static final String KEY_SCALE_MAX = "scale_max";
    private static final String KEY_SCALE_MIN = "scale_min";
    private static final String KEY_ROTATE = "rotate";
    private static final String KEY_ROTATE_ENABLE = "rotate_enable";
    private static final String KEY_ROTATE_ORIGINAL = "rotate_original";
    private static final String KEY_ROTATE_MAX = "rotate_max";
    private static final String KEY_ROTATE_MIN = "rotate_min";
    private static final String KEY_FILTER_ENABLE = "filter_enable";
    private static final String KEY_FILTER = "filter";

    // Column name
    private static final String KEY_STICKER_ARRAY = "sticker_array";


    // Table Create Statements
    // massage table create statement
    private static final String CREATE_TABLE_MASSAGE = "CREATE TABLE " + TABLE_MASSAGE
            + "("
            + KEY_ID + " TEXT PRIMARY KEY,"
            + KEY_MASSAGE + " TEXT,"
            + KEY_FONT_NAME + " TEXT,"
            + KEY_FONT_SIZE + " FLOAT,"
            + KEY_SIGNATURE_ID + " TEXT,"
            + KEY_POSITION_FONT_TYPE + " INTEGER,"
            + KEY_POSITION_FONT_SIZE + " INTEGER"
            + ")";
    // contact table create statement
    private static final String CREATE_TABLE_CONTACT = "CREATE TABLE " + TABLE_CONTACT
            + "("
            + KEY_ID + " TEXT PRIMARY KEY,"
            + KEY_FIRST_NAME + " TEXT,"
            + KEY_LINE_ONE + " TEXT,"
            + KEY_LINE_TWO + " TEXT,"
            + KEY_ZIP_CODE + " TEXT,"
            + KEY_STATE + " TEXT,"
            + KEY_COUNTRY + " TEXT,"
            + KEY_ENVELOP + " BOOLEAN,"
            + KEY_POSITION_COUNTRY + " INTEGER"
            + ")";
    // contact table create statement
    private static final String CREATE_TABLE_POSTCARD = "CREATE TABLE " + TABLE_POSTCARD
            + "("
            + KEY_ID + " TEXT PRIMARY KEY,"
            + KEY_MASSAGE_ID + " TEXT,"
            + KEY_IMAGE_ID + " TEXT,"
            + KEY_CONTACT_ID + " TEXT,"
            + KEY_CREATE_DATE + " TEXT,"
            + KEY_MODIFY_DATE + " TEXT,"
            + KEY_USER_ID + " TEXT,"
            + KEY_IS_SENT + " BOOLEAN"
            + ")";

    // signature table create statement
    private static final String CREATE_TABLE_SIGNATURE = "CREATE TABLE " + TABLE_SIGNATURE
            + "("
            + KEY_ID + " TEXT PRIMARY KEY,"
            + KEY_LINE_SIZE + " FLOAT"
            + ")";


    /*Nuii : Create table IMAGE_COMPOSER */
    private static final String CREATE_TABLE_IMAGE_COMPOSER = "CREATE TABLE " + TABLE_IMAGE_COMPOSER
            + "("
            + KEY_ID + " TEXT PRIMARY KEY,"
            + KEY_TEMPLATE_MODEL + " BYTE[],"
            + KEY_STICKER_MODEL + " BYTE[]"
            + ")";

    private static final String CREATE_TABLE_IMAGE_TEMPLATE = "CREATE TABLE " + TABLE_IMAGE_TEMPLATE
            + "("
            + KEY_ID + " TEXT PRIMARY KEY,"
            + KEY_TEMPLATE + " INTEGER,"
            + KEY_IMAGE_A + " TEXT,"
            + KEY_IMAGE_B + " TEXT,"
            + KEY_IMAGE_C + " TEXT,"
            + KEY_IMAGE_D + " TEXT,"
            + KEY_MARGE_ONE_STROKE + " FLOAT,"
            + KEY_MARGE_ONE_COLOR + " TEXT,"
            + KEY_MARGE_TWO_STROKE + " FLOAT,"
            + KEY_MARGE_TWO_COLOR + " TEXT,"
            + KEY_TOP_VALUE + " FLOAT,"
            + KEY_BOTTOM_VALUE + " FLOAT,"
            + KEY_RIGHT_VALUE + " FLOAT,"
            + KEY_LEFT_VALUE + " FLOAT,"
            + KEY_CENTER_X + " FLOAT,"
            + KEY_CENTER_Y + " FLOAT"
            + ")";

    private static final String CREATE_TABLE_IMAGE = "CREATE TABLE " + TABLE_IMAGE
            + "("
            + KEY_ID + " TEXT PRIMARY KEY,"
            + KEY_URL + " BYTE[],"
            + KEY_OFFSET_X + " FLOAT,"
            + KEY_OFFSET_X_ENABLE + " BOOLEAN,"
            + KEY_OFFSET_X_ORIGINAL + " FLOAT,"
            + KEY_OFFSET_X_MAX + " FLOAT,"
            + KEY_OFFSET_X_MIN + " FLOAT,"
            + KEY_OFFSET_Y + " FLOAT,"
            + KEY_OFFSET_Y_ENABLE + " BOOLEAN,"
            + KEY_OFFSET_Y_ORIGINAL + " FLOAT,"
            + KEY_OFFSET_Y_MAX + " FLOAT,"
            + KEY_OFFSET_Y_MIN + " FLOAT,"
            + KEY_SCALE + " FLOAT,"
            + KEY_SCALE_ENABLE + " BOOLEAN,"
            + KEY_SCALE_ORIGINAL + " FLOAT,"
            + KEY_SCALE_MAX + " FLOAT,"
            + KEY_SCALE_MIN + " FLOAT,"
            + KEY_ROTATE + " FLOAT,"
            + KEY_ROTATE_ENABLE + " BOOLEAN,"
            + KEY_ROTATE_ORIGINAL + " FLOAT,"
            + KEY_ROTATE_MAX + " FLOAT,"
            + KEY_ROTATE_MIN + " FLOAT,"
            + KEY_FILTER_ENABLE + " BOOLEAN,"
            + KEY_FILTER + " INTEGER"
            + ")";

    private static final String CREATE_TABLE_STICKER_TEMPLATE = "CREATE TABLE " + TABLE_STICKER_TEMPLATE
            + "("
            + KEY_STICKER_ARRAY + " ARRAY"
            + ")";

    public databaseClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_MASSAGE);
        sqLiteDatabase.execSQL(CREATE_TABLE_CONTACT);
        sqLiteDatabase.execSQL(CREATE_TABLE_POSTCARD);
        sqLiteDatabase.execSQL(CREATE_TABLE_SIGNATURE);
     /*Nuii*/
        sqLiteDatabase.execSQL(CREATE_TABLE_IMAGE_COMPOSER);
        sqLiteDatabase.execSQL(CREATE_TABLE_IMAGE_TEMPLATE);
        sqLiteDatabase.execSQL(CREATE_TABLE_IMAGE);
        sqLiteDatabase.execSQL(CREATE_TABLE_STICKER_TEMPLATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MASSAGE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_POSTCARD);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SIGNATURE);
        /*Nuii*/
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGE_COMPOSER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGE_TEMPLATE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_STICKER_TEMPLATE);

        // create new tables
        onCreate(sqLiteDatabase);
    }

    //-------------------------Function----------------------------------//

    //region MASSAGE TABLE FUNCTION--------------------------------------
    //insert massage
    public void insertMassage(Massage sMassage) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, sMassage.getID()); // id
        values.put(KEY_MASSAGE, sMassage.getMassage()); // massage
        values.put(KEY_FONT_NAME, sMassage.getFontName()); // font name
        values.put(KEY_FONT_SIZE, sMassage.getFontSize()); // font size
        values.put(KEY_SIGNATURE_ID, sMassage.getSignatureID()); // signature id
        values.put(KEY_POSITION_FONT_TYPE, sMassage.getPositionFontType()); // position font type
        values.put(KEY_POSITION_FONT_SIZE, sMassage.getPositionFontSize()); // position font size

        // Inserting Row
        sqLiteDatabase.insert(TABLE_MASSAGE, null, values);
        sqLiteDatabase.close(); // Closing database connection
    }

    //get massage
    public Massage getMassage(String sID) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(TABLE_MASSAGE,
                null,//get all column
                KEY_ID + "=?",
                new String[]{sID}, null, null, null, null);
        Massage rMassage = new Massage();
        if (cursor != null) {
            cursor.moveToFirst();

            rMassage.setID(cursor.getString(0));
            rMassage.setMassage(cursor.getString(1));
            rMassage.setFontName(cursor.getString(2));
            rMassage.setFontSize(cursor.getFloat(3));
            rMassage.setSignatureID(cursor.getString(4));
            rMassage.setPositionFontType(cursor.getInt(5));
            rMassage.setPositionFontSize(cursor.getInt(6));

            cursor.close();
        }

        // return massage
        return rMassage;
    }

    //put signature id to massage row by id
    public int updateSignID(String sID, String sSignID) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SIGNATURE_ID, sSignID);

        // updating row
        return sqLiteDatabase.update(TABLE_MASSAGE, values, KEY_ID + " = ?",
                new String[]{sID});
    }

    //update data in row by id
    public int updateMassage(Massage sMassage) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MASSAGE, sMassage.getMassage());
        values.put(KEY_FONT_NAME, sMassage.getFontName());
        values.put(KEY_FONT_SIZE, sMassage.getFontSize());
        values.put(KEY_SIGNATURE_ID, sMassage.getSignatureID());
        values.put(KEY_POSITION_FONT_TYPE, sMassage.getPositionFontType());
        values.put(KEY_POSITION_FONT_SIZE, sMassage.getPositionFontSize());

        // updating row
        return sqLiteDatabase.update(TABLE_MASSAGE, values, KEY_ID + " = ?",
                new String[]{sMassage.getID()});
    }

    //delete row by id
    public void deleteMassageRow(Massage sMassage) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_MASSAGE, KEY_ID + " = ?",
                new String[]{sMassage.getID()});
        sqLiteDatabase.close();
    }
    //endregion

    //region CONTACT TABLE FUNCTION--------------------------------------
    //insert contact
    public void insertContact(Contact sContact) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, sContact.getID()); // id
        values.put(KEY_FIRST_NAME, sContact.getFirstName()); // first name
        values.put(KEY_LINE_ONE, sContact.getLineOne()); // line one
        values.put(KEY_LINE_TWO, sContact.getLineTwo()); // line two
        values.put(KEY_ZIP_CODE, sContact.getZipCode()); // zip code
        values.put(KEY_STATE, sContact.getState()); // state
        values.put(KEY_COUNTRY, sContact.getCountry()); // country
        values.put(KEY_ENVELOP, sContact.getEnvelop()); // envelop
        values.put(KEY_POSITION_COUNTRY, sContact.getPositionCountry()); // position country

        // Inserting Row
        sqLiteDatabase.insert(TABLE_CONTACT, null, values);
        sqLiteDatabase.close(); // Closing database connection
    }

    //get contact
    public Contact getContact(String sID) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(TABLE_CONTACT,
                null,//get all column
                KEY_ID + "=?",
                new String[]{sID}, null, null, null, null);
        Contact rContact = new Contact();
        if (cursor != null) {
            cursor.moveToFirst();

            rContact.setID(cursor.getString(0));
            rContact.setFirstName(cursor.getString(1));
            rContact.setLineOne(cursor.getString(2));
            rContact.setLineTwo(cursor.getString(3));
            rContact.setZipCode(cursor.getString(4));
            rContact.setState(cursor.getString(5));
            rContact.setCountry(cursor.getString(6));
            if (cursor.getInt(7) == 0)
                rContact.setEnvelop(false);
            else
                rContact.setEnvelop(true);
            rContact.setPositionCountry(cursor.getInt(8));

            cursor.close();
        }

        // return contact
        return rContact;
    }

    //get all contact row
    public List<Contact> getAllContactRow() {
        List<Contact> contactList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACT;

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact tContact = new Contact();
                tContact.setID(cursor.getString(0));
                tContact.setFirstName(cursor.getString(1));
                tContact.setLineOne(cursor.getString(2));
                tContact.setLineTwo(cursor.getString(3));
                tContact.setZipCode(cursor.getString(4));
                tContact.setState(cursor.getString(5));
                tContact.setCountry(cursor.getString(6));
                if (cursor.getInt(7) == 0)
                    tContact.setEnvelop(false);
                else
                    tContact.setEnvelop(true);
                tContact.setPositionCountry(cursor.getInt(8));
                // Adding contact to list
                contactList.add(tContact);
            } while (cursor.moveToNext());
            cursor.close();
        }

        // return contact list
        return contactList;
    }

    //update data in row by id
    public int updateContact(Contact sContact) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FIRST_NAME, sContact.getFirstName()); // first name
        values.put(KEY_LINE_ONE, sContact.getLineOne()); // line one
        values.put(KEY_LINE_TWO, sContact.getLineTwo()); // line two
        values.put(KEY_ZIP_CODE, sContact.getZipCode()); // zip code
        values.put(KEY_STATE, sContact.getState()); // state
        values.put(KEY_COUNTRY, sContact.getCountry()); // country
        values.put(KEY_ENVELOP, sContact.getEnvelop()); // envelop
        values.put(KEY_POSITION_COUNTRY, sContact.getPositionCountry()); // position country

        // updating row
        return sqLiteDatabase.update(TABLE_CONTACT, values, KEY_ID + " = ?",
                new String[]{sContact.getID()});
    }

    //delete row by id
    public void deleteContactRow(Contact sContact) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_CONTACT, KEY_ID + " = ?",
                new String[]{sContact.getID()});
        sqLiteDatabase.close();
    }
    //endregion

    //region POSTCARD TABLE FUNCTION-------------------------------------
    //insert contact
    public void insertPostcard(Postcard sPostcard) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, sPostcard.getID()); // id
        values.put(KEY_MASSAGE_ID, sPostcard.getMassageID()); // massage id
        values.put(KEY_IMAGE_ID, sPostcard.getImageID()); // image id
        values.put(KEY_CONTACT_ID, sPostcard.getContactID()); // contact id
        values.put(KEY_CREATE_DATE, sPostcard.getCreateDateString()); // create date
        values.put(KEY_MODIFY_DATE, sPostcard.getModifyDateString()); // modify date
        values.put(KEY_USER_ID, sPostcard.getUserID()); // user id
        values.put(KEY_IS_SENT, sPostcard.getIsSent()); // is sent

        // Inserting Row
        sqLiteDatabase.insert(TABLE_POSTCARD, null, values);
        sqLiteDatabase.close(); // Closing database connection
    }

    //get postcard row
    public Postcard getPostcard(String sID) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(TABLE_POSTCARD,
                null,//get all column
                KEY_ID + "=?",
                new String[]{sID}, null, null, null, null);
        Postcard rPostcard = new Postcard();
        if (cursor != null) {
            cursor.moveToFirst();

            rPostcard.setID(cursor.getString(0));
            rPostcard.setMassageID(cursor.getString(1));
            rPostcard.setImageID(cursor.getString(2));
            rPostcard.setContactID(cursor.getString(3));
            rPostcard.setCreateDateString(cursor.getString(4));
            rPostcard.setModifyDateString(cursor.getString(5));
            rPostcard.setUserID(cursor.getString(6));
            if (cursor.getInt(7) == 0)
                rPostcard.setIsSent(false);
            else
                rPostcard.setIsSent(true);

            cursor.close();
        }

        // return contact
        return rPostcard;
    }

    //get all contact row
    public List<Postcard> getAllPostcardRow() {
        List<Postcard> postcardList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_POSTCARD;

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Postcard tPostcard = new Postcard();
                tPostcard.setID(cursor.getString(0));
                tPostcard.setMassageID(cursor.getString(1));
                tPostcard.setImageID(cursor.getString(2));
                tPostcard.setContactID(cursor.getString(3));
                tPostcard.setCreateDateString(cursor.getString(4));
                tPostcard.setModifyDateString(cursor.getString(5));
                tPostcard.setUserID(cursor.getString(6));
                if (cursor.getInt(7) == 0)
                    tPostcard.setIsSent(false);
                else
                    tPostcard.setIsSent(true);
                // Adding contact to list
                postcardList.add(tPostcard);
            } while (cursor.moveToNext());
            cursor.close();
        }

        // return contact list
        return postcardList;
    }

    //update data in row by id
    public int updatePostcard(Postcard sPostcard) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, sPostcard.getID()); // id
        values.put(KEY_MASSAGE_ID, sPostcard.getMassageID()); // massage id
        values.put(KEY_IMAGE_ID, sPostcard.getImageID()); // image id
        values.put(KEY_CONTACT_ID, sPostcard.getContactID()); // contact id
        values.put(KEY_MODIFY_DATE, sPostcard.getModifyDateString()); // modify date
        values.put(KEY_USER_ID, sPostcard.getUserID()); // user id
        values.put(KEY_IS_SENT, sPostcard.getIsSent()); // is sent

        // updating row
        return sqLiteDatabase.update(TABLE_POSTCARD, values, KEY_ID + " = ?",
                new String[]{sPostcard.getID()});
    }

    //delete row by id
    public void deletePostcardRow(Postcard sPostcard) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_POSTCARD, KEY_ID + " = ?",
                new String[]{sPostcard.getID()});
        sqLiteDatabase.close();
    }
    //endregion

    //region SIGNATURE TABLE FUNCTION-------------------------------------
    //insert contact
    public void insertSign(Signature sSign) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, sSign.getID()); // id
        values.put(KEY_LINE_SIZE, sSign.getLineSize()); // line size

        // Inserting Row
        sqLiteDatabase.insert(TABLE_SIGNATURE, null, values);
        sqLiteDatabase.close(); // Closing database connection
    }

    //get postcard row
    public Signature getSign(String sID) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(TABLE_SIGNATURE,
                null,//get all column
                KEY_ID + "=?",
                new String[]{sID}, null, null, null, null);
        Signature rSign = new Signature();
        if (cursor != null) {
            cursor.moveToFirst();

            rSign.setID(cursor.getString(0));
            rSign.setLineSize(cursor.getFloat(1));

            cursor.close();
        }

        // return contact
        return rSign;
    }

    //get all contact row
    public List<Signature> getAllSignRow() {
        List<Signature> signList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SIGNATURE;

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Signature tSign = new Signature();
                tSign.setID(cursor.getString(0));
                tSign.setLineSize(cursor.getFloat(1));
                // Adding contact to list
                signList.add(tSign);
            } while (cursor.moveToNext());
            cursor.close();
        }

        // return contact list
        return signList;
    }

    //update data in row by id
    public int updateSign(Signature sSign) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, sSign.getID()); // id
        values.put(KEY_LINE_SIZE, sSign.getLineSize()); // line size

        // updating row
        return sqLiteDatabase.update(TABLE_SIGNATURE, values, KEY_ID + " = ?",
                new String[]{sSign.getID()});
    }

    //delete row by id
    public void deleteSignRow(Signature sSign) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_SIGNATURE, KEY_ID + " = ?",
                new String[]{sSign.getID()});
        sqLiteDatabase.close();
    }
    //endregion

    //check unique id by table name
    public Boolean checkUniqueID(String sID, String sTableName) {
        //return true if not unique
        //return false if unique
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(sTableName,
                new String[]{KEY_ID},//get id column
                null,//get all row
                null, null, null, null, null);

        String[] idList;

        if (cursor != null) {
            idList = new String[cursor.getCount()];
            cursor.moveToFirst();

            //set data to array
            for (int i = 0; i < cursor.getCount(); i++) {
                idList[i] = cursor.getString(0);
            }
            cursor.close();

            //check unique
            for (String anIdList : idList) {
                if (sID.equals(anIdList))
                    return true;//not unique
            }

        }
        return false;//is unique
    }

    public String createID() {
        Long timeStamp = System.currentTimeMillis() / 1000;
        Random ramdom = new Random();
        int ramdomNum = ramdom.nextInt(1000000 + 1);//0 - 1000000
        return timeStamp.toString() + ramdomNum;
    }


    //region Nuii

    //region IMAGE_COMPOSER TABLE FUNCTION--------------------------------------
    //insert IMAGE_COMPOSER
    public void insertImageComposer(ImageComposer sImageComposer) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, sImageComposer.getImageComposerID());
        values.put(KEY_TEMPLATE_MODEL, sImageComposer.getTemplate());
        values.put(KEY_STICKER_MODEL, sImageComposer.getSticker());

        // Inserting Row
        sqLiteDatabase.insert(TABLE_IMAGE_COMPOSER, null, values);
        sqLiteDatabase.close(); // Closing database connection
    }

    //get ImageComposer
    public ImageComposer getImageComposer(String sID) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(TABLE_IMAGE_COMPOSER,
                null,//get all column
                KEY_ID + "=?",
                new String[]{sID}, null, null, null, null);
        ImageComposer rImageComposer = new ImageComposer();
        if (cursor != null) {
            cursor.moveToFirst();

            rImageComposer.setImageComposerID(cursor.getString(0));
            rImageComposer.setTemplate(cursor.getString(1));
            rImageComposer.setSticker(cursor.getString(2));
            cursor.close();
        }
        // return contact
        return rImageComposer;
    }

    //get all ImageComposer row
    public List<ImageComposer> getAllImageComposerRow() {
        List<ImageComposer> ImageComposerList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_IMAGE_COMPOSER;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ImageComposer tImageComposerList = new ImageComposer();
                tImageComposerList.setImageComposerID(cursor.getString(0));
                tImageComposerList.setTemplate(cursor.getString(1));
                tImageComposerList.setSticker(cursor.getString(2));
                ImageComposerList.add(tImageComposerList);
            } while (cursor.moveToNext());
            cursor.close();
        }
        // return contact list
        return ImageComposerList;
    }

    //update data in row by id
    public int updateImageComposer(ImageComposer sImageComposer) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // values.put(KEY_IMAGECOMPOSER_ID, sImageComposer.getImageComposerID());
        values.put(KEY_TEMPLATE_MODEL, sImageComposer.getTemplate());
        values.put(KEY_STICKER_MODEL, sImageComposer.getSticker());

        // updating row
        return sqLiteDatabase.update(TABLE_IMAGE_COMPOSER, values, KEY_ID + " = ?",
                new String[]{sImageComposer.getImageComposerID()});

    }

    //delete row by id
    public void deleteImageComposerRow(ImageComposer sImageComposer) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_IMAGE_COMPOSER, KEY_ID + " = ?",
                new String[]{sImageComposer.getImageComposerID()});
        sqLiteDatabase.close();
    }
    //endregion

    //region TABLE_IMAGE TABLE FUNCTION--------------------------------------
    //insert TABLE_IMAGE


   /*Test*/
   /* public void insertImage(Image sImageModel) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, sImageModel.getImage_id());

        // Inserting Row
        sqLiteDatabase.insert(TABLE_IMAGE, null, values);
        sqLiteDatabase.close(); // Closing database connection
    }*/

    public void insertImage(Image sImageModel) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, sImageModel.getImage_id());
        values.put(KEY_URL, sImageModel.getUrl());
        values.put(KEY_OFFSET_X, sImageModel.getX());
        values.put(KEY_OFFSET_X_ENABLE, sImageModel.getX_enable());
        values.put(KEY_OFFSET_X_ORIGINAL, sImageModel.getX_original());
        values.put(KEY_OFFSET_X_MAX, sImageModel.getX_max());
        values.put(KEY_OFFSET_X_MIN, sImageModel.getX_min());
        values.put(KEY_OFFSET_Y, sImageModel.getY());
        values.put(KEY_OFFSET_Y_ENABLE, sImageModel.getY_enable());
        values.put(KEY_OFFSET_Y_ORIGINAL, sImageModel.getY_original());
        values.put(KEY_OFFSET_Y_MAX, sImageModel.getY_max());
        values.put(KEY_OFFSET_Y_MIN, sImageModel.getY_min());
        values.put(KEY_SCALE, sImageModel.getScale());
        values.put(KEY_SCALE_ENABLE, sImageModel.getScale_enable());
        values.put(KEY_SCALE_ORIGINAL, sImageModel.getScale_original());
        values.put(KEY_SCALE_MAX, sImageModel.getScale_max());
        values.put(KEY_SCALE_MIN, sImageModel.getScale_min());
        values.put(KEY_ROTATE, sImageModel.getRotate());
        values.put(KEY_ROTATE_ENABLE, sImageModel.getRotate_enable());
        values.put(KEY_ROTATE_ORIGINAL, sImageModel.getRotate_original());
        values.put(KEY_ROTATE_MAX, sImageModel.getRotate_max());
        values.put(KEY_ROTATE_MIN, sImageModel.getRotate_min());
        values.put(KEY_FILTER_ENABLE, sImageModel.getFilter_enable());
        values.put(KEY_FILTER, sImageModel.getFilter());

        // Inserting Row
        sqLiteDatabase.insert(TABLE_IMAGE, null, values);
        sqLiteDatabase.close(); // Closing database connection
    }

    //get Image
    public Image getImage(String sID) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(TABLE_IMAGE,
                null,//get all column
                KEY_ID + "=?",
                new String[]{sID}, null, null, null, null);
        Image rImage = new Image();
        if (cursor != null) {
            cursor.moveToFirst();
            rImage.setImage_id(cursor.getString(0));
            rImage.setUrl(cursor.getBlob(1));
            rImage.setX(cursor.getFloat(2));
            rImage.setX_enable(cursor.isNull(3));
            rImage.setX_original(cursor.getFloat(4));
            rImage.setX_max(cursor.getFloat(5));
            rImage.setX_min(cursor.getFloat(6));
            rImage.setY(cursor.getFloat(7));
            rImage.setY_enable(cursor.isNull(8));
            rImage.setY_original(cursor.getFloat(9));
            rImage.setY_max(cursor.getFloat(10));
            rImage.setY_min(cursor.getFloat(11));
            rImage.setScale(cursor.getFloat(12));
            rImage.setScale_enable(cursor.isNull(13));
            rImage.setScale_original(cursor.getFloat(14));
            rImage.setScale_max(cursor.getFloat(15));
            rImage.setScale_min(cursor.getFloat(16));
            rImage.setRotate(cursor.getFloat(17));
            rImage.setRotate_enable(cursor.isNull(18));
            rImage.setRotate_original(cursor.getFloat(19));
            rImage.setRotate_max(cursor.getFloat(20));
            rImage.setRotate_min(cursor.getFloat(21));
            rImage.setFilter_enable(cursor.isNull(22));
            rImage.setFilter(cursor.getInt(23));

            cursor.close();
        }
        // return contact
        return rImage;
    }

    //get all contact row
    public List<Image> getAllImageRow() {
        List<Image> ImageList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_IMAGE;

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Image tImage = new Image();

                tImage.setImage_id(cursor.getString(0));
                tImage.setUrl(cursor.getBlob(1));
                tImage.setX(cursor.getFloat(2));
                tImage.setX_enable(cursor.isNull(3));
                tImage.setX_original(cursor.getFloat(4));
                tImage.setX_max(cursor.getFloat(5));
                tImage.setX_min(cursor.getFloat(6));
                tImage.setY(cursor.getFloat(7));
                tImage.setY_enable(cursor.isNull(8));
                tImage.setY_original(cursor.getFloat(9));
                tImage.setY_max(cursor.getFloat(10));
                tImage.setY_min(cursor.getFloat(11));
                tImage.setScale(cursor.getFloat(12));
                tImage.setScale_enable(cursor.isNull(13));
                tImage.setScale_original(cursor.getFloat(14));
                tImage.setScale_max(cursor.getFloat(15));
                tImage.setScale_min(cursor.getFloat(16));
                tImage.setRotate(cursor.getFloat(17));
                tImage.setRotate_enable(cursor.isNull(18));
                tImage.setRotate_original(cursor.getFloat(19));
                tImage.setRotate_max(cursor.getFloat(20));
                tImage.setRotate_min(cursor.getFloat(21));
                tImage.setFilter_enable(cursor.isNull(22));
                tImage.setFilter(cursor.getInt(23));

                ImageList.add(tImage);
            } while (cursor.moveToNext());
            cursor.close();
        }
        // return contact list
        return ImageList;
    }

    //update data in row by id
    public int updateImage(Image sImageModel) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // values.put(KEY_IMAGE_MODEL_ID, sImageModel.getImage_id());
        values.put(KEY_URL, sImageModel.getUrl());
        values.put(KEY_OFFSET_X, sImageModel.getX());
        values.put(KEY_OFFSET_X_ENABLE, sImageModel.getX_enable());
        values.put(KEY_OFFSET_X_ORIGINAL, sImageModel.getX_original());
        values.put(KEY_OFFSET_X_MAX, sImageModel.getX_max());
        values.put(KEY_OFFSET_X_MIN, sImageModel.getX_min());
        values.put(KEY_OFFSET_Y, sImageModel.getY());
        values.put(KEY_OFFSET_Y_ENABLE, sImageModel.getY_enable());
        values.put(KEY_OFFSET_Y_ORIGINAL, sImageModel.getY_original());
        values.put(KEY_OFFSET_Y_MAX, sImageModel.getY_max());
        values.put(KEY_OFFSET_Y_MIN, sImageModel.getY_min());
        values.put(KEY_SCALE, sImageModel.getScale());
        values.put(KEY_SCALE_ENABLE, sImageModel.getScale_enable());
        values.put(KEY_SCALE_ORIGINAL, sImageModel.getScale_original());
        values.put(KEY_SCALE_MAX, sImageModel.getScale_max());
        values.put(KEY_SCALE_MIN, sImageModel.getScale_min());
        values.put(KEY_ROTATE, sImageModel.getRotate());
        values.put(KEY_ROTATE_ENABLE, sImageModel.getRotate_enable());
        values.put(KEY_ROTATE_ORIGINAL, sImageModel.getRotate_original());
        values.put(KEY_ROTATE_MAX, sImageModel.getRotate_max());
        values.put(KEY_ROTATE_MIN, sImageModel.getRotate_min());
        values.put(KEY_FILTER_ENABLE, sImageModel.getFilter_enable());
        values.put(KEY_FILTER, sImageModel.getFilter());

        // updating row
        return sqLiteDatabase.update(TABLE_IMAGE, values, KEY_ID + " = ?",
                new String[]{sImageModel.getImage_id()});
    }

    //delete row by id
    public void deleteImageRow(Image sImageModel) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_IMAGE, KEY_ID + " = ?",
                new String[]{sImageModel.getImage_id()});
        sqLiteDatabase.close();
    }

    //endregion

    //region STICKER_TEMPLATE TABLE FUNCTION--------------------------------------
    //insert StickerTemplate
    public void insertStickerTemplate(StickerTemplate ssticker_array) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_STICKER_ARRAY, ssticker_array.getSticker()); // id

        // Inserting Row
        sqLiteDatabase.insert(TABLE_STICKER_TEMPLATE, null, values);
        sqLiteDatabase.close(); // Closing database connection
    }
    //endregion

    //region TABLE_IMAGE_TEMPLATE TABLE FUNCTION--------------------------------------
    //insert TABLE_IMAGE_TEMPLATE

    public void insertImageTemplate(ImageTemplate sImageTemplate) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, sImageTemplate.getImageTemplate_id());
        values.put(KEY_TEMPLATE, sImageTemplate.getTemplate());
        values.put(KEY_IMAGE_A, sImageTemplate.getImage_a());
        values.put(KEY_IMAGE_B, sImageTemplate.getImage_b());
        values.put(KEY_IMAGE_C, sImageTemplate.getImage_c());
        values.put(KEY_IMAGE_D, sImageTemplate.getImage_d());
        values.put(KEY_MARGE_ONE_COLOR, sImageTemplate.getMarge_one_color());
        values.put(KEY_MARGE_ONE_STROKE, sImageTemplate.getMarge_one_stroke());
        values.put(KEY_MARGE_TWO_COLOR, sImageTemplate.getMarge_two_color());
        values.put(KEY_MARGE_TWO_STROKE, sImageTemplate.getMarge_two_stroke());
        values.put(KEY_TOP_VALUE, sImageTemplate.getTop_value());
        values.put(KEY_BOTTOM_VALUE, sImageTemplate.getBottom_value());
        values.put(KEY_RIGHT_VALUE, sImageTemplate.getRight_value());
        values.put(KEY_LEFT_VALUE, sImageTemplate.getLeft_value());
        values.put(KEY_CENTER_X, sImageTemplate.getCenter_x());
        values.put(KEY_CENTER_Y, sImageTemplate.getCenter_y());

        // Inserting Row
        sqLiteDatabase.insert(TABLE_IMAGE_TEMPLATE, null, values);
        sqLiteDatabase.close(); // Closing database connection
    }

    //get ImageTemplate
    public ImageTemplate getImageTemplate(String sID) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(TABLE_IMAGE_TEMPLATE,
                null,//get all column
                KEY_ID + "=?",
                new String[]{sID}, null, null, null, null);

        ImageTemplate rImageTemplate = new ImageTemplate();
        if (cursor != null) {
            cursor.moveToFirst();
            rImageTemplate.setImagetemplate_id(cursor.getString(0));
            rImageTemplate.setTemplate(cursor.getInt(1));
            rImageTemplate.setImage_a(cursor.getString(2));
            rImageTemplate.setImage_b(cursor.getString(3));
            rImageTemplate.setImage_c(cursor.getString(4));
            rImageTemplate.setImage_d(cursor.getString(5));
            rImageTemplate.setMarge_one_color(cursor.getString(6));
            rImageTemplate.setMarge_one_stroke(cursor.getFloat(7));
            rImageTemplate.setMarge_two_color(cursor.getString(8));
            rImageTemplate.setMarge_two_stroke(cursor.getFloat(9));
            rImageTemplate.setTop_value(cursor.getFloat(10));
            rImageTemplate.setBottom_value(cursor.getFloat(11));
            rImageTemplate.setRight_value(cursor.getFloat(12));
            rImageTemplate.setLeft_value(cursor.getFloat(13));
            rImageTemplate.setCenter_x(cursor.getFloat(14));
            rImageTemplate.setCenter_y(cursor.getFloat(15));

            cursor.close();
        }
        // return contact
        return rImageTemplate;
    }


    //get all ImageTemplate row
    public List<ImageTemplate> getAllImageTemplateRow() {
        List<ImageTemplate> ImageTemplateList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_IMAGE_TEMPLATE;

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ImageTemplate tImageTemplate = new ImageTemplate();

                tImageTemplate.setImagetemplate_id(cursor.getString(0));
                tImageTemplate.setTemplate(cursor.getInt(1));
                tImageTemplate.setImage_a(cursor.getString(2));
                tImageTemplate.setImage_b(cursor.getString(3));
                tImageTemplate.setImage_c(cursor.getString(4));
                tImageTemplate.setImage_d(cursor.getString(5));
                tImageTemplate.setMarge_one_color(cursor.getString(6));
                tImageTemplate.setMarge_one_stroke(cursor.getFloat(7));
                tImageTemplate.setMarge_two_color(cursor.getString(8));
                tImageTemplate.setMarge_two_stroke(cursor.getFloat(9));
                tImageTemplate.setTop_value(cursor.getFloat(10));
                tImageTemplate.setBottom_value(cursor.getFloat(11));
                tImageTemplate.setRight_value(cursor.getFloat(12));
                tImageTemplate.setLeft_value(cursor.getFloat(13));
                tImageTemplate.setCenter_x(cursor.getFloat(14));
                tImageTemplate.setCenter_y(cursor.getFloat(15));

                ImageTemplateList.add(tImageTemplate);
            } while (cursor.moveToNext());
            cursor.close();
        }
        // return contact list
        return ImageTemplateList;
    }

    //update data in row by id
    public int updateImageTemplate(ImageTemplate sImageTemplate) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TEMPLATE, sImageTemplate.getTemplate());
        values.put(KEY_IMAGE_A, sImageTemplate.getImage_a());
        values.put(KEY_IMAGE_B, sImageTemplate.getImage_b());
        values.put(KEY_IMAGE_C, sImageTemplate.getImage_c());
        values.put(KEY_IMAGE_D, sImageTemplate.getImage_d());
        values.put(KEY_MARGE_ONE_COLOR, sImageTemplate.getMarge_one_color());
        values.put(KEY_MARGE_ONE_STROKE, sImageTemplate.getMarge_one_stroke());
        values.put(KEY_MARGE_TWO_COLOR, sImageTemplate.getMarge_two_color());
        values.put(KEY_MARGE_TWO_STROKE, sImageTemplate.getMarge_two_stroke());
        values.put(KEY_TOP_VALUE, sImageTemplate.getTop_value());
        values.put(KEY_BOTTOM_VALUE, sImageTemplate.getBottom_value());
        values.put(KEY_RIGHT_VALUE, sImageTemplate.getRight_value());
        values.put(KEY_LEFT_VALUE, sImageTemplate.getLeft_value());
        values.put(KEY_CENTER_X, sImageTemplate.getCenter_x());
        values.put(KEY_CENTER_Y, sImageTemplate.getCenter_y());

        // updating row
        return sqLiteDatabase.update(TABLE_IMAGE_TEMPLATE, values, KEY_ID + " = ?",
                new String[]{sImageTemplate.getImageTemplate_id()});
    }

    //delete row by id
    public void deleteImageTemplateRow(ImageTemplate sImageTemplate) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_IMAGE_TEMPLATE, KEY_ID + " = ?",
                new String[]{sImageTemplate.getImageTemplate_id()});
        sqLiteDatabase.close();
    }

    //endregion

    //endregion


}