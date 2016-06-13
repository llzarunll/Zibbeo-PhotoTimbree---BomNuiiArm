package com.zibbeo.phototrimbree.PostCard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.zibbeo.phototrimbree.BaseNavigationDrawer;
import com.zibbeo.phototrimbree.CreatePostcard.Massage.ZPTMessageComposerView;
import com.zibbeo.phototrimbree.Database.Contact;
import com.zibbeo.phototrimbree.Database.Postcard;
import com.zibbeo.phototrimbree.Database.databaseClass;
import com.zibbeo.phototrimbree.R;

import java.util.ArrayList;
import java.util.List;

public class ZPTPostcardListView extends BaseNavigationDrawer {

    View contentView;
    ListView oldPostcardListView;
    oldPostcardListAdapter postcardListAdapter;
    int mPicID = R.drawable.boston;
    String[] mNameArray, mImageID, mMassageID, mContactID, mPostcardID;
    databaseClass mDatabaseClass;

    //Bom
    Button newPostCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.zpt_postcard_list_view, null, false);
        mDrawerLayout.addView(contentView, 0);

        init();

        mDatabaseClass = new databaseClass(contentView.getContext());

        readData();
        postcardListAdapter = new oldPostcardListAdapter(contentView.getContext(), mPicID, mNameArray);
        oldPostcardListView.setAdapter(postcardListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        oldPostcardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (mImageID.length != 0) {
                    Intent intent = new Intent(contentView.getContext(), ZPTMessageComposerView.class);
                    intent.putExtra("imageID", mImageID[position]);
                    intent.putExtra("massageID", mMassageID[position]);
                    intent.putExtra("contactID", mContactID[position]);
                    intent.putExtra("postcardID", mPostcardID[position]);
                    startActivity(intent);
                }

            }
        });

        newPostCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(contentView.getContext(), "Complete", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(contentView.getContext(), ZPTImageComposerView.class);
                startActivity(intent);
            }
        });
    }

    private void readData() {
        List<Postcard> postcardList;
        postcardList = mDatabaseClass.getAllPostcardRow();
        mImageID = new String[postcardList.size()];
        mMassageID = new String[postcardList.size()];
        mContactID = new String[postcardList.size()];
        mPostcardID = new String[postcardList.size()];
        mNameArray = new String[mContactID.length];
        for (int i = 0; i < postcardList.size(); i++) {
            mImageID[i] = postcardList.get(i).getImageID();
            mMassageID[i] = postcardList.get(i).getMassageID();
            mContactID[i] = postcardList.get(i).getContactID();
            mPostcardID[i] = postcardList.get(i).getID();
            mNameArray[i] = mDatabaseClass.getContact(mContactID[i]).getFirstName();
        }
    }

    private void init() {
        oldPostcardListView = (ListView) findViewById(R.id.oldPostcardListView);
        newPostCard = (Button) findViewById(R.id.newPostcardButton);
    }
}
