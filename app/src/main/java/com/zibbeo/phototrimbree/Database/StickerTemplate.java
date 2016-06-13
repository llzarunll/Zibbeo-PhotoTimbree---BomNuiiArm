package com.zibbeo.phototrimbree.Database;

/**
 * Created by Nuii on 6/6/2559.
 */
public class StickerTemplate {
    private String sticker_array;
    public StickerTemplate() {
    }

    public StickerTemplate(String ssticker_array){
        this.sticker_array = ssticker_array;
    }

    public void setSticker(String sticker_array) {this.sticker_array = sticker_array;    }
    public String getSticker() {return sticker_array;}
}
