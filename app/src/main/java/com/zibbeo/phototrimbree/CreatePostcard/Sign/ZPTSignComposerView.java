package com.zibbeo.phototrimbree.CreatePostcard.Sign;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.zibbeo.phototrimbree.BaseNavigationDrawer;
import com.zibbeo.phototrimbree.CreatePostcard.Contact.ZPTContactComposerView;
import com.zibbeo.phototrimbree.Database.Signature;
import com.zibbeo.phototrimbree.Database.databaseClass;
import com.zibbeo.phototrimbree.R;

public class ZPTSignComposerView extends BaseNavigationDrawer {

    View contentView;
    RelativeLayout signView;
    signatureFunction mSign;
    Button resetButton, nextButton, previousButton;
    SeekBar sizeLineSeekbar;
    int mSizeLine;
    databaseClass mDatabaseClass;
    String massageID,imageID,contactID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.zpt_sign_composer_view, null, false);
        mDrawerLayout.addView(contentView, 0);

        init();

        //get data from previous page
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            imageID = bundle.getString("imageID");
            massageID = bundle.getString("massageID");
            contactID = bundle.getString("contactID");
        }

        mDatabaseClass = new databaseClass(contentView.getContext());

        //region set up sign view
        mSign = new signatureFunction(contentView.getContext());
        signView.addView(mSign);
        //set up seek bar
        mSizeLine = sizeLineSeekbar.getMax() / 5;
        sizeLineSeekbar.setProgress(mSizeLine);
        //endregion
    }

    @Override
    protected void onResume() {
        super.onResume();

        //region clear sign
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSign.clearCanvas();
            }
        });
        //endregion

        //region edit line size
        sizeLineSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                mSizeLine = progress;
                mSign.setStroke(mSizeLine);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mSign.setStroke(mSizeLine);
            }
        });
        //endregion

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mSign.isEmpty()) {
                    //save data to database
                    String tID;
                    Float tLineSize;
                    tID = mDatabaseClass.createID();
                    while (mDatabaseClass.checkUniqueID(tID,"signature")){
                        tID = mDatabaseClass.createID();
                    }
                    tLineSize = (float) mSizeLine;
                    Signature tSignature = new Signature(tID, tLineSize);
                    Log.i("update",""+mDatabaseClass.updateSignID(massageID,tID));
                    mDatabaseClass.insertSign(tSignature);

                    Toast.makeText(contentView.getContext(), "Complete", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(contentView.getContext(), ZPTContactComposerView.class);
                    intent.putExtra("imageID",imageID);
                    intent.putExtra("massageID",massageID);
                    intent.putExtra("contactID",contactID);
                    startActivity(intent);
                } else
                    Toast.makeText(contentView.getContext(), "Please sign", Toast.LENGTH_LONG).show();
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void init() {
        signView = (RelativeLayout) findViewById(R.id.canvasView);
        resetButton = (Button) findViewById(R.id.resetButton);
        nextButton = (Button) findViewById(R.id.nextButton);
        previousButton = (Button) findViewById(R.id.previousButton);
        sizeLineSeekbar = (SeekBar) findViewById(R.id.sizeLineSeekbar);
    }
}
