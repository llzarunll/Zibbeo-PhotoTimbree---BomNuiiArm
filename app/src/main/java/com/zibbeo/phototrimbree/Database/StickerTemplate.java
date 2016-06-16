package com.zibbeo.phototrimbree.Database;

/**
 * Created by Nuii on 6/6/2559.
 */
public class StickerTemplate {
    private String stickerID;
    private  float x,y;
    byte[] sticker ;
    public StickerTemplate() {
    }

    public StickerTemplate(String stickerID,byte[] ssticker,Float sx,float sy){
        this.stickerID = stickerID;
        this.sticker = ssticker;
        this.x = sx;
        this.y = sy;
    }
    public StickerTemplate(byte[] ssticker,Float sx,float sy){
        this.sticker = ssticker;
        this.x = sx;
        this.y = sy;
    }
    public void setStickerID(String stickerID) {this.stickerID = stickerID;    }

    public void setSticker(byte[] sticker) {
        this.sticker = sticker;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public void setY(Float y) {
        this.y = y;
    }

    public String getStickerID() {return stickerID;}
    public byte[] getSticker() {return sticker;}
    public Float getX() {return x;}
    public Float getY() {return y;}
}
