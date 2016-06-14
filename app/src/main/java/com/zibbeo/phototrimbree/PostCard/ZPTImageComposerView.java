package com.zibbeo.phototrimbree.PostCard;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.zibbeo.phototrimbree.Database.Image;
import com.zibbeo.phototrimbree.Database.ImageTemplate;
import com.zibbeo.phototrimbree.Database.databaseClass;
import com.zibbeo.phototrimbree.PostCard.Utility;
import com.zibbeo.phototrimbree.BaseNavigationDrawer;
import com.zibbeo.phototrimbree.CreatePostcard.Massage.ZPTMessageComposerView;
import com.zibbeo.phototrimbree.CreatePostcard.Sign.ZPTSignComposerView;
import com.zibbeo.phototrimbree.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;

/**
 * Created by ZARUN on 28/5/2559.
 */
public class ZPTImageComposerView extends BaseNavigationDrawer {
    View contentView;
    Button nextButton, previousButton;
    ImageButton farme1, farme2, farme3, farme4, farme5, farme6, farme7, farme8;
    ImageView ImgBlock1, ImgBlock2, ImgBlock3, ImgBlock4;
    FrameLayout FrameImg;
    SeekBar seekbar1;
    int FullHeight, FullWidth;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    //KITTI Add Control SeekbarOuter
    SeekBar sOuter,sInner;
    RelativeLayout mLayout;
    LineClass mLineClass;
    MoveLineClass mMove;

    /*Nuii*/

    databaseClass mDatabaseClass;
    String getTpID, tpID, getImageTemplateID, imageTemplate, stickerTemplate;
    //Image
    String imageTemplateID, image_a, image_b, image_c, image_d, marge_one_color, marge_two_color;
    Float marge_one_stroke, marge_two_stroke, top_value, bottom_value, right_value, left_value, center_x, center_y;
    Integer template;
    //Sticker
    String stickerTemplateID;
    //Image Model A
    String aid;
    Float aoffset_x, aoffset_x_original, aoffset_x_max, aoffset_x_min, aoffset_y, aoffset_y_original, aoffset_y_max;
    Float aoffset_y_min, ascale, ascale_original, ascale_max, ascale_min, arotate, arotate_original, arotate_max, arotate_min;
    Boolean aoffset_x_enable, aoffset_y_enable, ascale_enable, arotate_enable, afilter_enable;
    Integer afilter;
    byte[] aurl;
    //Image Model B
    String bid;
    Float boffset_x, boffset_x_original, boffset_x_max, boffset_x_min, boffset_y, boffset_y_original, boffset_y_max;
    Float boffset_y_min, bscale, bscale_original, bscale_max, bscale_min, brotate, brotate_original, brotate_max, brotate_min;
    Boolean boffset_x_enable, boffset_y_enable, bscale_enable, brotate_enable, bfilter_enable;
    Integer bfilter;
    byte[] burl;
    //Image Model C
    String cid;
    Float coffset_x, coffset_x_original, coffset_x_max, coffset_x_min, coffset_y, coffset_y_original, coffset_y_max;
    Float coffset_y_min, cscale, cscale_original, cscale_max, cscale_min, crotate, crotate_original, crotate_max, crotate_min;
    Boolean coffset_x_enable, coffset_y_enable, cscale_enable, crotate_enable, cfilter_enable;
    Integer cfilter;
    byte[] curl;
    //Image Model D
    String did;
    Float doffset_x, doffset_x_original, doffset_x_max, doffset_x_min, doffset_y, doffset_y_original, doffset_y_max;
    Float doffset_y_min, dscale, dscale_original, dscale_max, dscale_min, drotate, drotate_original, drotate_max, drotate_min;
    Boolean doffset_x_enable, doffset_y_enable, dscale_enable, drotate_enable, dfilter_enable;
    Integer dfilter;
    byte[] durl;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     **/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.zpt_image_composer_view, null, false);
        mDrawerLayout.addView(contentView, 0);
        init();

        mLayout = (RelativeLayout) findViewById(R.id.FrameImageView);
        mLineClass = new LineClass(this, mLayout);
        mMove = new MoveLineClass(this, mLayout);

        mDatabaseClass = new databaseClass(contentView.getContext());

        /*Nuii*/
        //get data from previous page
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            getImageTemplateID = bundle.getString("imageID");
        }
        if (getImageTemplateID != null) {
            imageTemplate = mDatabaseClass.getImageComposer(getImageTemplateID).getTemplate();
            stickerTemplate = mDatabaseClass.getImageComposer(getImageTemplateID).getSticker();
        }
        //Get Image Template
        /*imageTemplate = "123456789";*/
        if (imageTemplate != null) {
            int i  = mDatabaseClass.getImageTemplate(imageTemplate).getTemplate();
            GetImageTemplate(imageTemplate);
        }
        //Get Sticker
        if (stickerTemplate != null) {
            // imageTemplate = mDatabaseClass.getImageComposer(getImageTemplateID).getTemplate();
            // stickerTemplate = mDatabaseClass.getImageComposer(getImageTemplateID).getSticker();
        }
        //Get model of image A
        if (image_a != null) {
            GetImageModel(image_a);
        }

        //Get model of image B
        if (image_b != null) {
            GetImageModel(image_b);
        }

        //Get model of image C
        if (image_c != null) {
            GetImageModel(image_c);
        }

        //Get model of image D
        if (image_d != null) {
            GetImageModel(image_d);
        }

    }

    /*Nuii*/
    //Get Image Tamplate
    public ArrayList GetImageTemplate(String imageTemplate) {
        ArrayList getTemplate = new ArrayList();
        getTemplate.add(mDatabaseClass.getImageTemplate(imageTemplate).getImageTemplate_id());
        getTemplate.add(1, mDatabaseClass.getImageTemplate(imageTemplate).getTemplate());
        getTemplate.add(2, mDatabaseClass.getImageTemplate(imageTemplate).getImage_a());
        getTemplate.add(3, mDatabaseClass.getImageTemplate(imageTemplate).getImage_b());
        getTemplate.add(4, mDatabaseClass.getImageTemplate(imageTemplate).getImage_c());
        getTemplate.add(5, mDatabaseClass.getImageTemplate(imageTemplate).getImage_d());
        getTemplate.add(6, mDatabaseClass.getImageTemplate(imageTemplate).getMarge_one_color());
        getTemplate.add(7, mDatabaseClass.getImageTemplate(imageTemplate).getMarge_two_color());
        getTemplate.add(8, mDatabaseClass.getImageTemplate(imageTemplate).getMarge_one_stroke());
        getTemplate.add(9, mDatabaseClass.getImageTemplate(imageTemplate).getMarge_two_stroke());
        getTemplate.add(10, mDatabaseClass.getImageTemplate(imageTemplate).getTop_value());
        getTemplate.add(11, mDatabaseClass.getImageTemplate(imageTemplate).getBottom_value());
        getTemplate.add(12, mDatabaseClass.getImageTemplate(imageTemplate).getMarge_two_color());
        getTemplate.add(13, mDatabaseClass.getImageTemplate(imageTemplate).getMarge_one_stroke());
        getTemplate.add(14, mDatabaseClass.getImageTemplate(imageTemplate).getMarge_two_stroke());
        getTemplate.add(15, mDatabaseClass.getImageTemplate(imageTemplate).getTop_value());
        getTemplate.add(16, mDatabaseClass.getImageTemplate(imageTemplate).getBottom_value());
        return (getTemplate);

    }

    /*Nuii*/
    //Get Image Model
    public ArrayList GetImageModel(String ImageID) {
        ArrayList getModel = new ArrayList();
        getModel.add(0, mDatabaseClass.getImage(ImageID).getUrl());
        getModel.add(1, mDatabaseClass.getImage(ImageID).getUrl());
        getModel.add(2, mDatabaseClass.getImage(ImageID).getX());
        getModel.add(3, mDatabaseClass.getImage(ImageID).getX_enable());
        getModel.add(4, mDatabaseClass.getImage(ImageID).getX_original());
        getModel.add(5, mDatabaseClass.getImage(ImageID).getX_max());
        getModel.add(6, mDatabaseClass.getImage(ImageID).getX_min());
        getModel.add(7, mDatabaseClass.getImage(ImageID).getY());
        getModel.add(8, mDatabaseClass.getImage(ImageID).getY_enable());
        getModel.add(9, mDatabaseClass.getImage(ImageID).getY_original());
        getModel.add(10, mDatabaseClass.getImage(ImageID).getY_max());
        getModel.add(11, mDatabaseClass.getImage(ImageID).getY_min());
        getModel.add(12, mDatabaseClass.getImage(ImageID).getScale());
        getModel.add(13, mDatabaseClass.getImage(ImageID).getScale_enable());
        getModel.add(14, mDatabaseClass.getImage(ImageID).getScale_original());
        getModel.add(15, mDatabaseClass.getImage(ImageID).getScale_max());
        getModel.add(16, mDatabaseClass.getImage(ImageID).getScale_min());
        getModel.add(17, mDatabaseClass.getImage(ImageID).getRotate());
        getModel.add(18, mDatabaseClass.getImage(ImageID).getRotate_enable());
        getModel.add(19, mDatabaseClass.getImage(ImageID).getRotate_original());
        getModel.add(20, mDatabaseClass.getImage(ImageID).getRotate_max());
        getModel.add(21, mDatabaseClass.getImage(ImageID).getRotate_min());
        getModel.add(22, mDatabaseClass.getImage(ImageID).getFilter_enable());
        getModel.add(23, mDatabaseClass.getImage(ImageID).getFilter());
        return (getModel);

    }

    @Override
    protected void onResume() {
        super.onResume();

        sOuter.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float sizeF = new Float(progress);
                mMove.mPaint.setStrokeWidth(sizeF);
                mMove.draw();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sInner.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float sizeF = new Float(progress);
                mMove.mPaintInner.setStrokeWidth(sizeF);
                mMove.draw();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(contentView.getContext(), "Complete", Toast.LENGTH_LONG).show();
                /*Convert Bitmap to Byte Array*/
                RelativeLayout savedImage = (RelativeLayout) findViewById(R.id.FrameImageView);
                savedImage.setDrawingCacheEnabled(true);
                savedImage.buildDrawingCache();
                Bitmap bmp = savedImage.getDrawingCache();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                savedImage.destroyDrawingCache();
                Intent intent = new Intent(contentView.getContext(), ZPTStickerComposerView.class);
                intent.putExtra("picture", byteArray);
                startActivity(intent);
                /*Nuii*/
                aid = image_a;
                bid = image_b;
                cid = image_c;
                did = image_d;

                //Add Values 1
                template = 1;
                marge_one_stroke= 1.00f;
                marge_one_color= "";
                marge_two_stroke= 1.00f;
                marge_two_color= "";
                top_value= 1.00f;
                bottom_value= 1.00f;
                right_value= 1.00f;
                left_value= 1.00f;
                center_x= 1.00f;
                center_y= 1.00f;

                /*Nuii*/
                //Insert Image Template;
                if (getImageTemplateID == null) {
                    getTpID = mDatabaseClass.createID();
                    image_a = mDatabaseClass.createID();
                    image_b = mDatabaseClass.createID();
                    image_c = mDatabaseClass.createID();
                    image_d = mDatabaseClass.createID();
                    /*while (mDatabaseClass.checkUniqueID(tID, "image")) {
                        tID = mDatabaseClass.createID();
                    }*/
                    ImageTemplate tImageTemplate = new ImageTemplate(getTpID, template, image_a, image_b, image_c, image_d
                            , marge_one_stroke, marge_one_color, marge_two_stroke, marge_two_color
                            , top_value, bottom_value, right_value, left_value, center_x, center_y);
                    /*
                    ImageTemplate tImageTemplate = new ImageTemplate(tpID,null,null,null,null,null
                    ,0.00f,null,0.00f,null,0.00f,0.00f,0.00f,0.00f,0.00f,0.00f);
                    */
                    mDatabaseClass.insertImageTemplate(tImageTemplate);

                } else {
                    getTpID = imageTemplateID;
                    ImageTemplate tImageTemplate = new ImageTemplate(template, image_a, image_b, image_c, image_d
                            , marge_one_stroke, marge_one_color, marge_two_stroke, marge_two_color
                            , top_value, bottom_value, right_value, left_value, center_x, center_y);
                    mDatabaseClass.updateImageTemplate(tImageTemplate);
                }
                /*Nuii*/
                //Get value of image A
                aurl = null;
                aoffset_x = 0.00f;
                aoffset_x_enable = false;
                aoffset_x_original = 0.00f;
                aoffset_x_max = 0.00f;
                aoffset_x_min = 0.00f;
                aoffset_y = 0.00f;
                aoffset_y_enable = false;
                aoffset_y_original = 0.00f;
                aoffset_y_max = 0.00f;
                aoffset_y_min = 0.00f;
                ascale = 0.00f;
                ascale_enable = false;
                ascale_original = 0.00f;
                ascale_max = 0.00f;
                ascale_min = 0.00f;
                arotate = 0.00f;
                arotate_enable = false;
                arotate_original = 0.00f;
                arotate_max = 0.00f;
                arotate_min = 0.00f;
                afilter_enable = false;
                afilter = 1;
                if (template >= 1) {
                    if (aid == null) {
                        aid = mDatabaseClass.createID();

                        Image ImageA = new Image(aid, aurl, aoffset_x, aoffset_x_enable, aoffset_x_original, aoffset_x_max, aoffset_x_min
                                , aoffset_y, aoffset_y_enable, aoffset_y_original, aoffset_y_max, aoffset_y_min
                                , ascale, ascale_enable, ascale_original, ascale_max, ascale_min
                                , arotate, arotate_enable, arotate_original, arotate_max, arotate_min
                                , afilter_enable, afilter);

                        mDatabaseClass.insertImage(ImageA);
                    } else {
                        Image ImageA = new Image(aurl, aoffset_x, aoffset_x_enable, aoffset_x_original, aoffset_x_max, aoffset_x_min
                                , aoffset_y, aoffset_y_enable, aoffset_y_original, aoffset_y_max, aoffset_y_min
                                , ascale, ascale_enable, ascale_original, ascale_max, ascale_min
                                , arotate, arotate_enable, arotate_original, arotate_max, arotate_min
                                , afilter_enable, afilter);

                        mDatabaseClass.updateImage(ImageA);
                    }
                }
                if (template >= 2) {
                    /*Nuii*/
                    //Get value of image B
                    burl = null;
                    boffset_x = 0.00f;
                    boffset_x_enable = false;
                    boffset_x_original = 0.00f;
                    boffset_x_max = 0.00f;
                    boffset_x_min = 0.00f;
                    boffset_y = 0.00f;
                    boffset_y_enable = false;
                    boffset_y_original = 0.00f;
                    boffset_y_max = 0.00f;
                    boffset_y_min = 0.00f;
                    bscale = 0.00f;
                    bscale_enable = false;
                    bscale_original = 0.00f;
                    bscale_max = 0.00f;
                    bscale_min = 0.00f;
                    brotate = 0.00f;
                    brotate_enable = false;
                    brotate_original = 0.00f;
                    brotate_max = 0.00f;
                    brotate_min = 0.00f;
                    bfilter_enable = false;
                    bfilter = 1;

                    if (bid == null) {
                        bid = mDatabaseClass.createID();

                        Image ImageB = new Image(bid, burl, boffset_x, boffset_x_enable, boffset_x_original, boffset_x_max, boffset_x_min
                                , boffset_y, boffset_y_enable, boffset_y_original, boffset_y_max, boffset_y_min
                                , bscale, bscale_enable, bscale_original, bscale_max, bscale_min
                                , brotate, brotate_enable, brotate_original, brotate_max, brotate_min
                                , bfilter_enable, bfilter);

                        mDatabaseClass.insertImage(ImageB);
                    } else {
                        Image ImageB = new Image(burl, boffset_x, boffset_x_enable, boffset_x_original, boffset_x_max, boffset_x_min
                                , boffset_y, boffset_y_enable, boffset_y_original, boffset_y_max, boffset_y_min
                                , bscale, bscale_enable, bscale_original, bscale_max, bscale_min
                                , brotate, brotate_enable, brotate_original, brotate_max, brotate_min
                                , bfilter_enable, bfilter);

                        mDatabaseClass.updateImage(ImageB);
                    }
                }
                if (template >= 3) {
                    /*Nuii*/
                    //Get value of image C
                    curl = null;
                    coffset_x = 0.00f;
                    coffset_x_enable = false;
                    coffset_x_original = 0.00f;
                    coffset_x_max = 0.00f;
                    coffset_x_min = 0.00f;
                    coffset_y = 0.00f;
                    coffset_y_enable = false;
                    coffset_y_original = 0.00f;
                    coffset_y_max = 0.00f;
                    coffset_y_min = 0.00f;
                    cscale = 0.00f;
                    cscale_enable = false;
                    cscale_original = 0.00f;
                    cscale_max = 0.00f;
                    cscale_min = 0.00f;
                    crotate = 0.00f;
                    crotate_enable = false;
                    crotate_original = 0.00f;
                    crotate_max = 0.00f;
                    crotate_min = 0.00f;
                    cfilter_enable = false;
                    cfilter = 1;
                    if (cid == null) {
                        cid = mDatabaseClass.createID();
                        Image ImageC = new Image(cid, curl, coffset_x, coffset_x_enable, coffset_x_original, coffset_x_max, coffset_x_min
                                , coffset_y, coffset_y_enable, coffset_y_original, coffset_y_max, coffset_y_min
                                , cscale, cscale_enable, cscale_original, cscale_max, cscale_min
                                , crotate, crotate_enable, crotate_original, crotate_max, crotate_min
                                , cfilter_enable, cfilter);

                        mDatabaseClass.insertImage(ImageC);
                    } else {
                        Image ImageC = new Image(curl, coffset_x, coffset_x_enable, coffset_x_original, coffset_x_max, coffset_x_min
                                , coffset_y, coffset_y_enable, coffset_y_original, coffset_y_max, coffset_y_min
                                , cscale, cscale_enable, cscale_original, cscale_max, cscale_min
                                , crotate, crotate_enable, crotate_original, crotate_max, crotate_min
                                , cfilter_enable, cfilter);

                        mDatabaseClass.updateImage(ImageC);
                    }
                }
                if (template >= 4) {
                    /*Nuii*/
                    //Get value of image D
                    durl = null;
                    doffset_x = 0.00f;
                    doffset_x_enable = false;
                    doffset_x_original = 0.00f;
                    doffset_x_max = 0.00f;
                    doffset_x_min = 0.00f;
                    doffset_y = 0.00f;
                    doffset_y_enable = false;
                    doffset_y_original = 0.00f;
                    doffset_y_max = 0.00f;
                    doffset_y_min = 0.00f;
                    dscale = 0.00f;
                    dscale_enable = false;
                    dscale_original = 0.00f;
                    dscale_max = 0.00f;
                    dscale_min = 0.00f;
                    drotate = 0.00f;
                    drotate_enable = false;
                    drotate_original = 0.00f;
                    drotate_max = 0.00f;
                    drotate_min = 0.00f;
                    dfilter_enable = false;
                    dfilter = 1;
                    if (did == null) {
                        did = mDatabaseClass.createID();
                        Image ImageD = new Image(did, durl, doffset_x, doffset_x_enable, doffset_x_original, doffset_x_max, doffset_x_min
                                , doffset_y, doffset_y_enable, doffset_y_original, doffset_y_max, doffset_y_min
                                , dscale, dscale_enable, dscale_original, dscale_max, dscale_min
                                , drotate, drotate_enable, drotate_original, drotate_max, drotate_min
                                , dfilter_enable, dfilter);

                        mDatabaseClass.insertImage(ImageD);
                    } else {
                        Image ImageD = new Image(durl, doffset_x, doffset_x_enable, doffset_x_original, doffset_x_max, doffset_x_min
                                , doffset_y, doffset_y_enable, doffset_y_original, doffset_y_max, doffset_y_min
                                , dscale, dscale_enable, dscale_original, dscale_max, dscale_min
                                , drotate, drotate_enable, drotate_original, drotate_max, drotate_min
                                , dfilter_enable, dfilter);

                        mDatabaseClass.updateImage(ImageD);
                    }
                }
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        farme1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FullWidth = FrameImg.getMeasuredWidth();
                FullHeight = FrameImg.getMeasuredHeight();
                SetBlockImg(ImgBlock1, FullHeight, FullWidth, (Color.rgb(100, 255, 255)));
                SetBlockImg(ImgBlock2, 0, 0, (Color.rgb(0, 255, 255)));
                SetBlockImg(ImgBlock3, 0, 0, (Color.rgb(0, 255, 255)));
                SetBlockImg(ImgBlock4, 0, 0, (Color.rgb(0, 255, 255)));
            }
        });

        farme2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FullWidth = FrameImg.getMeasuredWidth();
                FullHeight = FrameImg.getMeasuredHeight();
                SetBlockImg(ImgBlock1, (int) (FullHeight / 2), FullWidth, (Color.rgb(100, 255, 255)));
                SetBlockImg(ImgBlock2, 0, 0, (Color.rgb(0, 255, 255)));
                SetBlockImg(ImgBlock3, (int) (FullHeight / 2), FullWidth, (Color.rgb(100, 100, 255)));
                SetBlockImg(ImgBlock4, 0, 0, (Color.rgb(0, 255, 255)));
            }
        });

        farme3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FullWidth = FrameImg.getMeasuredWidth();
                FullHeight = FrameImg.getMeasuredHeight();
                SetBlockImg(ImgBlock1, (int) (FullHeight * (0.7)), FullWidth, (Color.rgb(100, 255, 255)));
                SetBlockImg(ImgBlock2, 0, 0, (Color.rgb(0, 255, 255)));
                SetBlockImg(ImgBlock3, (int) (FullHeight * (0.3)), FullWidth, (Color.rgb(100, 100, 255)));
                SetBlockImg(ImgBlock4, 0, 0, (Color.rgb(0, 255, 255)));
            }
        });
    }

    private void init() {
        nextButton = (Button) findViewById(R.id.nextButton);
        previousButton = (Button) findViewById(R.id.previousButton);
        //KITTI : Link Control
        sOuter = (SeekBar) findViewById(R.id.seekBarOuter);
        sInner = (SeekBar) findViewById(R.id.seekBarInner);

        farme1 = (ImageButton) findViewById(R.id.farme1);
        farme2 = (ImageButton) findViewById(R.id.farme2);
        farme3 = (ImageButton) findViewById(R.id.farme3);
        farme4 = (ImageButton) findViewById(R.id.farme4);
        farme5 = (ImageButton) findViewById(R.id.farme5);
        farme6 = (ImageButton) findViewById(R.id.farme6);
        farme7 = (ImageButton) findViewById(R.id.farme7);
        farme8 = (ImageButton) findViewById(R.id.farme8);

//        FrameImg = (FrameLayout) findViewById(R.id.FrameImageView);
    }

    private void SetBlockImg(ImageView ObjImageView, int height, int width, int codeColor) {
        //Set Size Image
        ObjImageView.getLayoutParams().height = height;
        ObjImageView.getLayoutParams().width = width;
        ObjImageView.setBackgroundColor(codeColor);
        ObjImageView.setPadding(50, 50, 50, 50);
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ZPTImageComposerView.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(ZPTImageComposerView.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ImgBlock1.setImageBitmap(thumbnail);
    }

    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ImgBlock1.setImageBitmap(bm);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }


    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ZPTImageComposerView Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.zibbeo.phototrimbree.PostCard/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
