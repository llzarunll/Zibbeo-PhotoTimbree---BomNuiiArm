package com.zibbeo.phototrimbree.Database;

import android.graphics.Path;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by samchaw on 5/29/16 AD.
 */
public class Postcard {

    private String id, massageID, imageID, contactID, userID;
    private String createDate, modifyDate;
    private Boolean isSent;

    public Postcard() {
    }

    public Postcard(String sID, String sMassageID, String sImageID, String sContactID,
                    String sUserID, Boolean sIsSent) {
        Date date = new Date();
        this.id = sID;
        this.massageID = sMassageID;
        this.imageID = sImageID;
        this.contactID = sContactID;
        this.createDate = setDateTime(date);
        this.modifyDate = setDateTime(date);
        this.userID = sUserID;
        this.isSent = sIsSent;
    }

    public Postcard(String sMassageID, String sImageID, String sContactID,
                    String sUserID, Boolean sIsSent) {
        Date date = new Date();
        this.massageID = sMassageID;
        this.imageID = sImageID;
        this.contactID = sContactID;
        this.createDate = setDateTime(date);
        this.modifyDate = setDateTime(date);
        this.userID = sUserID;
        this.isSent = sIsSent;
    }

    public void setID(String id) {
        this.id = id;
    }

    public void setMassageID(String massageID) {
        this.massageID = massageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public void setContactID(String contactID) {
        this.contactID = contactID;
    }

    public void setCreateDate() {
        this.createDate = setDateTime(new Date());
    }

    public void setModifyDate() {
        this.modifyDate = setDateTime(new Date());
    }

    public void setCreateDateString(String dateString) {
        this.createDate = dateString;
    }

    public void setModifyDateString(String dateString) {
        this.modifyDate = dateString;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setIsSent(Boolean sent) {
        isSent = sent;
    }

    public String getID() {
        return id;
    }

    public String getMassageID() {
        return massageID;
    }

    public String getImageID() {
        return imageID;
    }

    public String getContactID() {
        return contactID;
    }

    public String getCreateDateString(){
        return createDate;
    }

    public String getModifyDateString(){
        return modifyDate;
    }

    public Date getCreateDate() throws ParseException {
        return getDate(createDate);
    }

    public Date getModifyDate() throws ParseException {
        return getDate(modifyDate);
    }

    public String getUserID() {
        return userID;
    }

    public Boolean getIsSent() {
        return isSent;
    }

    //set time to string
    private String setDateTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
    }

    //convert string to date
    private Date getDate(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.parse(date);

    }
}
