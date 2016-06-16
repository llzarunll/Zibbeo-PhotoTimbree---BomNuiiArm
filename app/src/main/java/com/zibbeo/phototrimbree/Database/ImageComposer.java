package com.zibbeo.phototrimbree.Database;

/**
 * Created by Nuii on 6/6/2559.
 */
public class ImageComposer {
    private String id,templateModel,stickerModel1,stickerModel2,stickerModel3,stickerModel4;

    public ImageComposer(){}

    public ImageComposer(String sid,String sTemplateModel,String sStickerModel1,String sStickerModel2,String sStickerModel3,String sStickerModel4){
        this.id = sid;
        this.templateModel = sTemplateModel;
        this.stickerModel1 = sStickerModel1;
        this.stickerModel2 = sStickerModel2;
        this.stickerModel3 = sStickerModel3;
        this.stickerModel4 = sStickerModel4;
    }

    public ImageComposer(String sTemplateModel,String sStickerModel1,String sStickerModel2,String sStickerModel3,String sStickerModel4){
        this.templateModel = sTemplateModel;
        this.stickerModel1 = sStickerModel1;
        this.stickerModel2 = sStickerModel2;
        this.stickerModel3 = sStickerModel3;
        this.stickerModel4 = sStickerModel4;
    }
    public void setImageComposerID(String id) {
        this.id = id;
    }

    public void setTemplate(String templateModel) {
        this.templateModel = templateModel;
    }

    public void setSticker1(String stickerModel1) {
        this.stickerModel1 = stickerModel1;
    }

    public void setSticker2(String stickerModel2) {
        this.stickerModel2 = stickerModel2;
    }

    public void setSticker3(String stickerModel3) {
        this.stickerModel3 = stickerModel3;
    }

    public void setSticker4(String stickerModel4) {
        this.stickerModel4 = stickerModel4;
    }

    public String getImageComposerID() { return id;}
    public String getTemplate() { return templateModel; }
    public String getSticker1() {
        return stickerModel1;
    }
    public String getSticker2() {
        return stickerModel2;
    }
    public String getSticker3() {
        return stickerModel3;
    }
    public String getSticker4() {
        return stickerModel4;
    }
}
