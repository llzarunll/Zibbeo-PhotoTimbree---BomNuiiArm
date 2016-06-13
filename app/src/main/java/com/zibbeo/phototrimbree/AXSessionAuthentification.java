package com.zibbeo.phototrimbree;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by samchaw on 5/19/16 AD.
 */
public class AXSessionAuthentification {

    public enum AXSessionAuthentificationStatut{
        AXSessionAuthentificationStatutNoConnected,
        AXSessionAuthentificationStatutWaiting,
        AXSessionAuthentificationStatutConnected,
        AXSessionAuthentificationStatutError,

        AXSessionAuthentificationStatutUnknow
    }

    public enum AXSessionAuthentificationType{
        AXSessionAuthentificationTypeLoginPasswordURL,
        AXSessionAuthentificationTypeFacebook,

        AXSessionAuthentificationTypeUnknow
    }

    private final String KEY_PREFS = "AXSessionAuthentification";
    private final String KEY_mStatus = "mStatus";
    private final String KEY_mType = "mType";
    private final String KEY_mToken = "mToken";
    private final String KEY_mEmail = "mEmail";
    private final String KEY_mPassword = "mPassword";
    private final String KEY_mFBToken = "mFBToken";
    private final String KEY_mFBID = "mFBID";
    private final String KEY_firstTime = "firstTime";

    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;

    public AXSessionAuthentification(Context context) {
        mPrefs = context.getSharedPreferences(KEY_PREFS, Context.MODE_PRIVATE);
        checkFirstTime();
        print();
    }

    private void checkFirstTime(){
        if (mPrefs.getBoolean(KEY_firstTime,true)){
            mEditor = mPrefs.edit();
            mEditor.putString(KEY_mStatus, String.valueOf(AXSessionAuthentificationStatut.AXSessionAuthentificationStatutNoConnected));
            mEditor.putString(KEY_mType, "");
            mEditor.putString(KEY_mToken, "");
            mEditor.putString(KEY_mEmail, "");
            mEditor.putString(KEY_mPassword, "");
            mEditor.putString(KEY_mFBToken, "");
            mEditor.putString(KEY_mFBID, "");
            mEditor.apply();
            System.out.println("first");
        } else
            System.out.println("no first");
            mPrefs.edit().putBoolean(KEY_firstTime,false).apply();


    }

    public void setStatus(int sStatus){
        mEditor = mPrefs.edit();
        switch (sStatus){
            case R.string.statusWaiting:
            mEditor.putString(KEY_mStatus, String.valueOf(AXSessionAuthentificationStatut.AXSessionAuthentificationStatutWaiting));
            break;
            case R.string.statusConnected:
                mEditor.putString(KEY_mStatus, String.valueOf(AXSessionAuthentificationStatut.AXSessionAuthentificationStatutConnected));
                break;
            case R.string.statusError:
                mEditor.putString(KEY_mStatus, String.valueOf(AXSessionAuthentificationStatut.AXSessionAuthentificationStatutError));
                break;
            case R.string.statusNoConnect:
                mEditor.putString(KEY_mStatus, String.valueOf(AXSessionAuthentificationStatut.AXSessionAuthentificationStatutNoConnected));
                break;
            case R.string.unknown:
                mEditor.putString(KEY_mStatus, String.valueOf(AXSessionAuthentificationStatut.AXSessionAuthentificationStatutUnknow));
                break;
            default:
                break;
        }
        mEditor.apply();
    }

    public void setType(int sType){
        mEditor = mPrefs.edit();
        switch (sType){
            case R.string.typeLoginPasswordURL:
                mEditor.putString(KEY_mType, String.valueOf(AXSessionAuthentificationType.AXSessionAuthentificationTypeLoginPasswordURL));
                break;
            case R.string.typeFacebook:
                mEditor.putString(KEY_mType, String.valueOf(AXSessionAuthentificationType.AXSessionAuthentificationTypeFacebook));
                break;
            case R.string.unknown:
                mEditor.putString(KEY_mType, String.valueOf(AXSessionAuthentificationType.AXSessionAuthentificationTypeUnknow));
                break;
            default:
                break;
        }
        mEditor.apply();
    }

    public void setToken(String sToken){
        mEditor = mPrefs.edit();
        mEditor.putString(KEY_mToken, sToken);
        mEditor.apply();
    }

    public void setEmail(String sEmail){
        mEditor = mPrefs.edit();
        mEditor.putString(KEY_mEmail, sEmail);
        mEditor.apply();
    }

    public void setPassword(String sPassword){
        mEditor = mPrefs.edit();
        mEditor.putString(KEY_mPassword, sPassword);
        mEditor.apply();
    }

    public void setFBID(String sFBID){
        mEditor = mPrefs.edit();
        mEditor.putString(KEY_mFBID, sFBID);
        mEditor.apply();
    }

    public void setFBToken(String sFBToken){
        mEditor = mPrefs.edit();
        mEditor.putString(KEY_mFBToken, sFBToken);
        mEditor.apply();
    }

    public String getStatus(){
        return mPrefs.getString(KEY_mStatus, null);
    }

    public String getType(){
        return mPrefs.getString(KEY_mType, null);
    }

    public String getToken(){
        return mPrefs.getString(KEY_mToken, "no token");
    }

    public String getEmail(){
        return mPrefs.getString(KEY_mEmail, "no email");
    }

    public String getPassword(){
        return mPrefs.getString(KEY_mPassword, "no password");
    }

    public String getFBID(){
        return mPrefs.getString(KEY_mFBID, "no fbid");
    }

    public String getFBToken(){
        return mPrefs.getString(KEY_mFBToken, "no fbtoken");
    }

    public void clearAllData(){
        mEditor = mPrefs.edit();
        mEditor.clear();
        mEditor.putBoolean(KEY_firstTime,false);
        mEditor.putString(KEY_mStatus, String.valueOf(AXSessionAuthentificationStatut.AXSessionAuthentificationStatutNoConnected));
        mEditor.apply();
    }

    public void print(){
        System.out.println(mPrefs.getAll());
    }
}
