package com.zibbeo.phototrimbree.Database;

/**
 * Created by samchaw on 5/31/16 AD.
 */
public class Signature {
    private String id,lineColor;
    private Float lineSize;

    public Signature(){}

    public Signature(String sID,/*String sLineColor,*/Float sLineSize){
        this.id = sID;
//        this.lineColor = sLineColor;
        this.lineSize = sLineSize;
    }

    public Signature(/*String sLineColor,*/Float sLineSize){
//        this.lineColor = sLineColor;
        this.lineSize = sLineSize;
    }

    public void setID(String id) {
        this.id = id;
    }

    public void setLineColor(String lineColor) {
        this.lineColor = lineColor;
    }

    public void setLineSize(Float lineSize) {
        this.lineSize = lineSize;
    }

    public String getID() {
        return id;
    }

    public String getLineColor() {
        return lineColor;
    }

    public Float getLineSize() {
        return lineSize;
    }
}
