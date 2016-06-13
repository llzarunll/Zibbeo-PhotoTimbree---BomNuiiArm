package com.zibbeo.phototrimbree.CreatePostcard.Massage;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by samchaw on 5/24/16 AD.
 */
public class fontList {

    private Context mContext;
    Typeface[] mFont = new Typeface[4];
    String[] mFontNameList = new String[4];

    public fontList(Context context){
        mContext = context;
        setFont();
        setFontName();
    }

    private void setFont(){
        mFont[0] = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Regular.ttf");
        mFont[1] = Typeface.createFromAsset(mContext.getAssets(), "fonts/Windsong.ttf");
        mFont[2] = Typeface.createFromAsset(mContext.getAssets(), "fonts/blackjack.otf");
        mFont[3] = Typeface.createFromAsset(mContext.getAssets(), "fonts/Pacifico.ttf");
    }

    private void setFontName(){
        mFontNameList[0] = "Roboto";
        mFontNameList[1] = "Windsong";
        mFontNameList[2] = "blackjack";
        mFontNameList[3] = "Pacifico";
    }

    public Typeface[] getMFont(){
        return mFont;
    }

    public String[] getMFontNameList(){
        return mFontNameList;
    }
}
