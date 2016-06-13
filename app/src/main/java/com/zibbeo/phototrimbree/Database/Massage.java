package com.zibbeo.phototrimbree.Database;

/**
 * Created by samchaw on 5/28/16 AD.
 */
public class Massage {

    private String id,massage,fontName,signatureID;
    private float fontSize;
    private int positionFontType,positionFontSize;

    public Massage(){
    }

    public Massage(String sID,String sMassage,String sFontName,float sFontSize,
                   String sSignatureID,int sPositionFontType,int sPositionFontSize){
        this.id = sID;
        this.massage = sMassage;
        this.fontName = sFontName;
        this.fontSize = sFontSize;
        this.signatureID = sSignatureID;
        this.positionFontType = sPositionFontType;
        this.positionFontSize = sPositionFontSize;
    }

    public Massage(String sID,String sMassage,String sFontName,float sFontSize,int sPositionFontType,int sPositionFontSize){
        this.id = sID;
        this.massage = sMassage;
        this.fontName = sFontName;
        this.fontSize = sFontSize;
        this.positionFontType = sPositionFontType;
        this.positionFontSize = sPositionFontSize;
    }

    public Massage(String sMassage,String sFontName,float sFontSize,String sSignatureID,
                   int sPositionFontType,int sPositionFontSize){
        this.massage = sMassage;
        this.fontName = sFontName;
        this.fontSize = sFontSize;
        this.signatureID = sSignatureID;
        this.positionFontType = sPositionFontType;
        this.positionFontSize = sPositionFontSize;
    }

    public void setID(String sID){
        this.id = sID;
    }

    public void setMassage(String sMassage){
        this.massage = sMassage;
    }

    public void setFontName(String sFontName){
        this.fontName = sFontName;
    }

    public void setFontSize(float sFontSize){
        this.fontSize = sFontSize;
    }

    public void setSignatureID(String sSignatureID){
        this.signatureID = sSignatureID;
    }

    public void setPositionFontType(int sPositionFontType) {
        this.positionFontType = sPositionFontType;
    }

    public void setPositionFontSize(int sPositionFontSize) {
        this.positionFontSize = sPositionFontSize;
    }

    public String getID(){
        return id;
    }

    public String getMassage() {
        return massage;
    }

    public String getFontName() {
        return fontName;
    }

    public float getFontSize() {
        return fontSize;
    }

    public String getSignatureID() {
        return signatureID;
    }

    public int getPositionFontType() {
        return positionFontType;
    }

    public int getPositionFontSize() {
        return positionFontSize;
    }
}
