package com.zibbeo.phototrimbree;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.zibbeo.phototrimbree.CreatePostcard.Contact.ZPTContactComposerView;
import com.zibbeo.phototrimbree.CreatePostcard.Massage.ZPTMessageComposerView;
import com.zibbeo.phototrimbree.CreatePostcard.Sign.ZPTSignComposerView;
import com.zibbeo.phototrimbree.PostCard.ZPTImageComposerView;
public class ZPTHomeView extends BaseNavigationDrawer {

    View contentView;

    Button createButton,tutorialButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.zpt_home_view);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.zpt_home_view, null, false);
        mDrawerLayout.addView(contentView, 0);

        createButton = (Button)findViewById(R.id.createNewPostcardButton);
        tutorialButton = (Button)findViewById(R.id.howDoesItWorkButton);

    }

    @Override
    protected void onResume() {
        super.onResume();
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(contentView.getContext(), ZPTImageComposerView.class);
                startActivity(intent);
            }
        });
    }
}
