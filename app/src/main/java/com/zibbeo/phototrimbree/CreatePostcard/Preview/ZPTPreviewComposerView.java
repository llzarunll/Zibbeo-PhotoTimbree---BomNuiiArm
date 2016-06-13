package com.zibbeo.phototrimbree.CreatePostcard.Preview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zibbeo.phototrimbree.BaseNavigationDrawer;
import com.zibbeo.phototrimbree.CreatePostcard.Contact.ZPTContactComposerView;
import com.zibbeo.phototrimbree.Database.Postcard;
import com.zibbeo.phototrimbree.Database.databaseClass;
import com.zibbeo.phototrimbree.PostCard.ZPTPostcardListView;
import com.zibbeo.phototrimbree.R;

public class ZPTPreviewComposerView extends BaseNavigationDrawer {

    View contentView;
    ImageView frontPreviewLayout;
    RelativeLayout backPreviewLayout;
    TextView priceTextView;
    Button nextButton, previousButton;
    databaseClass mDatabaseClass;
    String massageID, contactID, imageID, postcardID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.zpt_preview_composer_view, null, false);
        mDrawerLayout.addView(contentView, 0);

        init();

        mDatabaseClass = new databaseClass(contentView.getContext());

        //get data from previous page
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            imageID = bundle.getString("imageID");
            massageID = bundle.getString("massageID");
            contactID = bundle.getString("contactID");
            postcardID = bundle.getString("postcardID");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tID;
                if (postcardID == null) {
                    tID = mDatabaseClass.createID();
                    while (mDatabaseClass.checkUniqueID(tID, "postcard")) {
                        tID = mDatabaseClass.createID();
                    }
                    Postcard tPostcard = new Postcard(tID, massageID, null, contactID, null, false);
                    mDatabaseClass.insertPostcard(tPostcard);
                } else {
                    tID = postcardID;
                    Postcard tPostcard = new Postcard(tID, massageID, null, contactID, null, false);
                    mDatabaseClass.updatePostcard(tPostcard);
                }

                Toast.makeText(ZPTPreviewComposerView.this, "complete", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(contentView.getContext(), ZPTPostcardListView.class);
                startActivity(intent);
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
        frontPreviewLayout = (ImageView) findViewById(R.id.frontPreviewLayout);
        backPreviewLayout = (RelativeLayout) findViewById(R.id.backPreviewLayout);
        priceTextView = (TextView) findViewById(R.id.priceTextView);
        nextButton = (Button) findViewById(R.id.nextButton);
        previousButton = (Button) findViewById(R.id.previousButton);
    }
}
