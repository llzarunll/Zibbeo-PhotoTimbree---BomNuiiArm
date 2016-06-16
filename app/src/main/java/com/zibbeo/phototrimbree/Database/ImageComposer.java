package com.zibbeo.phototrimbree.Database;

/**
 * Created by Nuii on 6/6/2559.
 */
public class ImageComposer {
    private String id,templateModel,stickerModel;

    public ImageComposer(){}

    public ImageComposer(String sid,String sTemplateModele,String sStickerModel){
        this.id = sid;
        this.templateModel = sTemplateModele;
        this.stickerModel = sStickerModel;
    }

    public ImageComposer(String sTemplateModele,String sStickerModel){
        this.templateModel = sTemplateModele;
        this.stickerModel = sStickerModel;
    }
    public void setImageComposerID(String id) {
        this.id = id;
    }

    public void setTemplate(String templateModel) {
        this.templateModel = templateModel;
    }

    public void setSticker(String stickerModel) {
        this.stickerModel = stickerModel;
    }

    public String getImageComposerID() { return id;}
    public String getTemplate() { return templateModel; }
    public String getSticker() {
        return stickerModel;
    }

}
