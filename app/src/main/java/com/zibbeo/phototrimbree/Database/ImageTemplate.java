package com.zibbeo.phototrimbree.Database;

/**
 * Created by  Nuii on 6/6/2559.
 */
public class ImageTemplate {
    private String id,marge_one_color,marge_two_color;
    private Float marge_one_stroke,marge_two_stroke,top_value,bottom_value,right_value,left_value,center_x,center_y;
    private String image_a,image_b,image_c,image_d;
    private Integer template;

    public ImageTemplate() {}

    public ImageTemplate(String sid,Integer stemplate,String simage_a,String simage_b,String simage_c,String simage_d
            ,Float smarge_one_stroke,String smarge_one_color,Float smarge_two_stroke,String smarge_two_color
            ,Float stop_value,Float sbottom_value,Float sright_value,Float sleft_value,Float scenter_x,Float scenter_y){
        this.id = sid;
        this.template = stemplate;
        this.image_a = simage_a;
        this.image_b = simage_b;
        this.image_c = simage_c;
        this.image_d = simage_d;
        this.marge_one_color = smarge_one_color;
        this.marge_one_stroke = smarge_one_stroke;
        this.marge_two_color = smarge_two_color;
        this.marge_two_stroke = smarge_two_stroke;
        this.top_value = stop_value;
        this.bottom_value = sbottom_value;
        this.right_value = sright_value;
        this.left_value = sleft_value;
        this.center_x = scenter_x;
        this.center_y = scenter_y;
    }

    public ImageTemplate(Integer stemplate,String simage_a,String simage_b,String simage_c,String simage_d
            ,Float smarge_one_stroke,String smarge_one_color,Float smarge_two_stroke,String smarge_two_color
            ,Float stop_value,Float sbottom_value,Float sright_value,Float sleft_value,Float scenter_x,Float scenter_y){
        this.template = stemplate;
        this.image_a = simage_a;
        this.image_b = simage_b;
        this.image_c = simage_c;
        this.image_d = simage_d;
        this.marge_one_color = smarge_one_color;
        this.marge_one_stroke = smarge_one_stroke;
        this.marge_two_color = smarge_two_color;
        this.marge_two_stroke = smarge_two_stroke;
        this.top_value = stop_value;
        this.bottom_value = sbottom_value;
        this.right_value = sright_value;
        this.left_value = sleft_value;
        this.center_x = scenter_x;
        this.center_y = scenter_y;
    }

    //region SET
    public void setImagetemplate_id(String id) {
        this.id = id;
    }

    public void setTemplate(Integer template) {
        this.template = template;
    }

    public void setImage_a(String image_a) {
        this.image_a = image_a;
    }

    public void setImage_b(String image_b) {
        this.image_b = image_b;
    }

    public void setImage_c(String image_c) {
        this.image_c = image_c;
    }

    public void setImage_d(String image_d) {
        this.image_d = image_d;
    }

    public void setMarge_one_color(String marge_one_color) {
        this.marge_one_color = marge_one_color;
    }

    public void setMarge_one_stroke(Float marge_one_stroke) {
        this.marge_one_stroke = marge_one_stroke;
    }

    public void setMarge_two_color(String marge_two_color) {
        this.marge_two_color = marge_two_color;
    }

    public void setMarge_two_stroke(Float marge_two_stroke) {
        this.marge_two_stroke = marge_two_stroke;
    }

    public void setTop_value(Float top_value) {
        this.top_value = top_value;
    }

    public void setBottom_value(Float bottom_value) {
        this.bottom_value = bottom_value;
    }

    public void setRight_value(Float right_value) {
        this.right_value = right_value;
    }

    public void setLeft_value(Float left_value) {
        this.left_value = left_value;
    }

    public void setCenter_x(Float center_x) {
        this.center_x = center_x;
    }

    public void setCenter_y(Float center_y) {
        this.center_y = center_y;
    }
    //endregion

    //region GET
    public String getImageTemplate_id() {return id;}
    public Integer getTemplate() {return template;}
    public String getImage_a() {return image_a;}
    public String getImage_b() {return image_b;}
    public String getImage_c() {return image_c;}
    public String getImage_d() {return image_d;}
    public String getMarge_one_color() {return marge_one_color;}
    public Float getMarge_one_stroke() {return marge_one_stroke;}
    public String getMarge_two_color() {return marge_two_color;}
    public Float getMarge_two_stroke() {return marge_two_stroke;}
    public Float getTop_value() {return top_value;}
    public Float getBottom_value() {return bottom_value;}
    public Float getRight_value() {return right_value;}
    public Float getLeft_value() {return left_value;}
    public Float getCenter_x() {return center_x;}
    public Float getCenter_y() {return center_y;}
    //endregion

}
