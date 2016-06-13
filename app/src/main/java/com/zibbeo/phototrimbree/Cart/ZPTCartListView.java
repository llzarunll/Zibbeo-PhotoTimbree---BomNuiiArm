package com.zibbeo.phototrimbree.Cart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.zibbeo.phototrimbree.BaseNavigationDrawer;
import com.zibbeo.phototrimbree.Database.Postcard;
import com.zibbeo.phototrimbree.Database.databaseClass;
import com.zibbeo.phototrimbree.PostCard.oldPostcardListAdapter;
import com.zibbeo.phototrimbree.R;
import com.zibbeo.phototrimbree.ZPTHomeView;

import java.util.Arrays;
import java.util.List;

public class ZPTCartListView extends BaseNavigationDrawer {

    View contentView;
    ListView postcardReadyListView;
    readyPostcardListAdapter readyPostcardListAdapter;
    int mPicID = R.drawable.boston;
    String[] mNameArray, mImageID, mMassageID, mContactID, mPostcardID;
    databaseClass mDatabaseClass;
    Boolean[] mReady;
    Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.zpt_cart_list_view, null, false);
        mDrawerLayout.addView(contentView, 0);

        init();

        mDatabaseClass = new databaseClass(contentView.getContext());

        readData();
        readyPostcardListAdapter = new readyPostcardListAdapter(contentView.getContext(), mPicID, mNameArray);
        postcardReadyListView.setAdapter(readyPostcardListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        postcardReadyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(contentView.getContext(), "click", Toast.LENGTH_SHORT).show();
                readyPostcardListAdapter.setReady(!readyPostcardListAdapter.getReadyWithPosition(position),position);

            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mReady = readyPostcardListAdapter.getReady();
                Log.i("check", Arrays.toString(mReady));
                int i = 0;
                while (i < mReady.length) {
                    if (mReady[i] != null && mReady[i]) {
                        Log.i("", "true");
                        Toast.makeText(contentView.getContext(), "sent", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(contentView.getContext(), ZPTHomeView.class);
//                        startActivity(intent);
                        break;
                    } else {
                        if (i == mReady.length - 1) {
                            Log.i("", "false");
                            Toast.makeText(contentView.getContext(), "please select postcard", Toast.LENGTH_SHORT).show();
                        }
                        i++;
                    }
                }
                checkPrice(mReady);
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
        mReady = new Boolean[postcardList.size()];
        mNameArray = new String[mContactID.length];
        for (int i = 0; i < postcardList.size(); i++) {
            mImageID[i] = postcardList.get(i).getImageID();//all image id
            mMassageID[i] = postcardList.get(i).getMassageID();//all massage id
            mContactID[i] = postcardList.get(i).getContactID();//all contact id
            mPostcardID[i] = postcardList.get(i).getID();//all postcard id
            mNameArray[i] = mDatabaseClass.getContact(mContactID[i]).getFirstName();//all first name from contact
        }
    }

    private void checkPrice(Boolean[] sReady) {
        //read data to sent to server
        String[] tPostcardID = new String[sReady.length];
        String[] tCreateDate = new String[sReady.length];
        String[] tModifyDate = new String[sReady.length];
        Boolean[] tEnvelop = new Boolean[sReady.length];
        String[] tCountry = new String[sReady.length];
        String tID;
        for (int i = 0,j = 0; i < sReady.length; i++) {
            if (sReady[i] != null && sReady[i]){
                tPostcardID[j] = mPostcardID[i];
                tCreateDate[j] = mDatabaseClass.getPostcard(tPostcardID[j]).getCreateDateString();
                tModifyDate[j] = mDatabaseClass.getPostcard(tPostcardID[j]).getModifyDateString();
                tID = mDatabaseClass.getPostcard(tPostcardID[j]).getContactID();
                tEnvelop[j] = mDatabaseClass.getContact(tID).getEnvelop();
                tCountry[j] = mDatabaseClass.getContact(tID).getCountry();
                j++;
            }
        }
        //connect server

        Log.i("postcard", Arrays.toString(tPostcardID));
        Log.d("postcard", Arrays.toString(mPostcardID));
    }

    private void init() {
        postcardReadyListView = (ListView) findViewById(R.id.postcardReadyListView);
        sendButton = (Button) findViewById(R.id.sendButton);
    }

}
