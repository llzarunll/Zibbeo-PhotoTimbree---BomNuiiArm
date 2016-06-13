package com.zibbeo.phototrimbree.Database;

/**
 * Created by Nuii on 6/6/2559.
 */
public class Image {
    private String id;
    private Float offset_x,offset_x_original,offset_x_max,offset_x_min,offset_y,offset_y_original,offset_y_max;
    private Float offset_y_min,scale,scale_original,scale_max,scale_min,rotate,rotate_original,rotate_max,rotate_min;
    private Boolean offset_x_enable,offset_y_enable,scale_enable,rotate_enable,filter_enable ;
    private Integer filter;
    private byte[] url;

    public Image() {
    }

    public Image(String sid,byte[] surl,Float soffset_x,Boolean soffset_x_enable,Float soffset_x_original,Float soffset_x_max
            ,Float soffset_x_min,Float soffset_y,Boolean soffset_y_enable,Float soffset_y_original,Float soffset_y_max,Float soffset_y_min
            ,Float sscale,Boolean sscale_enable,Float sscale_original,Float sscale_max,Float sscale_min
            ,Float srotate,Boolean srotate_enable,Float srotate_original,Float srotate_max,Float srotate_min,Boolean sfilter_enable,Integer sfilter){
        this.id = sid;
        this.url = surl;
        this.offset_x = soffset_x;
        this.offset_x_enable = soffset_x_enable;
        this.offset_x_original = soffset_x_original;
        this.offset_x_max = soffset_x_max;
        this.offset_x_min = soffset_x_min;
        this.offset_y = soffset_y;
        this.offset_y_enable = soffset_y_enable;
        this.offset_y_original = soffset_y_original;
        this.offset_y_max = soffset_y_max;
        this.offset_y_min = soffset_y_min;
        this.scale = sscale;
        this.scale_enable = sscale_enable;
        this.scale_original = sscale_original;
        this.scale_max = sscale_max;
        this.scale_min = sscale_min;
        this.rotate = srotate;
        this.rotate_enable = srotate_enable;
        this.rotate_original = srotate_original;
        this.rotate_max = srotate_max;
        this.rotate_min = srotate_min;
        this.filter_enable = sfilter_enable;
        this.filter = sfilter;

    }

    /*Test*/
    public Image(String sid){
        this.id = sid;
    }

    public Image(byte[] surl,Float soffset_x,Boolean soffset_x_enable,Float soffset_x_original,Float soffset_x_max
            ,Float soffset_x_min,Float soffset_y,Boolean soffset_y_enable,Float soffset_y_original,Float soffset_y_max,Float soffset_y_min
            ,Float sscale,Boolean sscale_enable,Float sscale_original,Float sscale_max,Float sscale_min
            ,Float srotate,Boolean srotate_enable,Float srotate_original,Float srotate_max,Float srotate_min,Boolean sfilter_enable,Integer sfilter){
        this.url = surl;
        this.offset_x = soffset_x;
        this.offset_x_enable = soffset_x_enable;
        this.offset_x_original = soffset_x_original;
        this.offset_x_max = soffset_x_max;
        this.offset_x_min = soffset_x_min;
        this.offset_y = soffset_y;
        this.offset_y_enable = soffset_y_enable;
        this.offset_y_original = soffset_y_original;
        this.offset_y_max = soffset_y_max;
        this.offset_y_min = soffset_y_min;
        this.scale = sscale;
        this.scale_enable = sscale_enable;
        this.scale_original = sscale_original;
        this.scale_max = sscale_max;
        this.scale_min = sscale_min;
        this.rotate = srotate;
        this.rotate_enable = srotate_enable;
        this.rotate_original = srotate_original;
        this.rotate_max = srotate_max;
        this.rotate_min = srotate_min;
        this.filter_enable = sfilter_enable;
        this.filter = sfilter;

    }

    //region SET

    public void setImage_id(String id) {
        this.id = id;
    }

    public void setUrl(byte[] url) {
        this.url = url;
    }

    /*--------------------------------------- X -------------------------------------*/
    public void setX(Float offset_x) {
        this.offset_x = offset_x;
    }

    public void setX_enable(Boolean offset_x_enable) {
        this.offset_x_enable = offset_x_enable;
    }

    public void setX_original(Float offset_x_original) {
        this.offset_x_original = offset_x_original;
    }

    public void setX_max(Float offset_x_max) {
        this.offset_x_max = offset_x_max;
    }
    public void setX_min(Float offset_x_min) {
        this.offset_x_min = offset_x_min;
    }

    /*--------------------------------------- Y -------------------------------------*/

    public void setY(Float offset_y) {
        this.offset_y = offset_y;
    }

    public void setY_enable(Boolean offset_y_enable) {
        this.offset_y_enable = offset_y_enable;
    }

    public void setY_original(Float offset_y_original) {
        this.offset_y_original = offset_y_original;
    }

    public void setY_max(Float offset_y_max) {
        this.offset_y_max = offset_y_max;
    }
    public void setY_min(Float offset_y_min) {
        this.offset_y_min = offset_y_min;
    }

    /*--------------------------------------- Scale -------------------------------------*/

    public void setScale(Float scale) {
        this.scale = scale;
    }

    public void setScale_enable(Boolean scale_enable) {
        this.scale_enable = scale_enable;
    }

    public void setScale_original(Float scale_original) {
        this.scale_original = scale_original;
    }

    public void setScale_max(Float scale_max) {
        this.scale_max = scale_max;
    }
    public void setScale_min(Float scale_min) {
        this.scale_min = scale_min;
    }

    /*--------------------------------------- Rotate -------------------------------------*/

    public void setRotate(Float rotate) {
        this.scale = scale;
    }

    public void setRotate_enable(Boolean rotate_enable) {
        this.rotate_enable = rotate_enable;
    }

    public void setRotate_original(Float rotate_original) {
        this.rotate_original = rotate_original;
    }

    public void setRotate_max(Float rotate_max) {
        this.rotate_max = rotate_max;
    }
    public void setRotate_min(Float rotate_min) {
        this.rotate_min = rotate_min;
    }

    /*--------------------------------------- Rotate -------------------------------------*/

    public void setFilter(Integer filter) {
        this.filter = filter;
    }

    public void setFilter_enable(Boolean filter_enable) {
        this.filter_enable = filter_enable;
    }

    //endregion

    //region GET
    public String getImage_id() {return id;}
    public byte[] getUrl() {return url;}
    public Float getX() {return offset_x;}
    public Boolean getX_enable() {return offset_x_enable;}
    public Float getX_original() {return offset_x_original;}
    public Float getX_max() {return offset_x_max;}
    public Float getX_min() {return offset_x_min;}
    public Float getY() {return offset_y;}
    public Boolean getY_enable() {return offset_y_enable;}
    public Float getY_original() {return offset_y_original;}
    public Float getY_max() {return offset_y_max;}
    public Float getY_min() {return offset_y_min;}
    public Float getScale() {return scale;}
    public Boolean getScale_enable() {return scale_enable;}
    public Float getScale_original() {return scale_original;}
    public Float getScale_max() {return scale_max;}
    public Float getScale_min() {return scale_min;}
    public Float getRotate() {return rotate;}
    public Boolean getRotate_enable() {return rotate_enable;}
    public Float getRotate_original() {return rotate_original;}
    public Float getRotate_max() {return rotate_max;}
    public Float getRotate_min() {return rotate_min;}
    public Boolean getFilter_enable() {return filter_enable;}
    public Integer getFilter() {return filter;}
    //endregion
}
