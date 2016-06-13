package com.zibbeo.phototrimbree.CreatePostcard.Massage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.zibbeo.phototrimbree.BaseNavigationDrawer;
import com.zibbeo.phototrimbree.CreatePostcard.Contact.ZPTContactComposerView;
import com.zibbeo.phototrimbree.CreatePostcard.Sign.ZPTSignComposerView;
import com.zibbeo.phototrimbree.Database.Massage;
import com.zibbeo.phototrimbree.Database.databaseClass;
import com.zibbeo.phototrimbree.PostCard.ZPTStickerComposerView;
import com.zibbeo.phototrimbree.R;


public class ZPTMessageComposerView extends BaseNavigationDrawer {

    View contentView;
    Spinner fontListSpinner;
    String[] mFontSizeList, mFontNameList;
    EditText massageEditText;
    int positionFontType = 0, positionFontSize = 5;
    Button nextButton, previousButton, upSizeTextButton, downSizeTextButton;
    fontList mFontList;
    Typeface[] mFontTypeArray;
    databaseClass mDatabaseClass;
    String massageID, imageID, contactID, postcardID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.zpt_message_composer_view, null, false);
        mDrawerLayout.addView(contentView, 0);

        init();

        //get data from previous page
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            imageID = bundle.getString("imageID");
            massageID = bundle.getString("massageID");
            contactID = bundle.getString("contactID");
            postcardID = bundle.getString("postcardID");
        }

        mDatabaseClass = new databaseClass(contentView.getContext());
        if (massageID != null) {
            positionFontType = mDatabaseClass.getMassage(massageID).getPositionFontType();
            positionFontSize = mDatabaseClass.getMassage(massageID).getPositionFontSize();
            massageEditText.setText(mDatabaseClass.getMassage(massageID).getMassage());
        }
        //set up font size
        mFontSizeList = getResources().getStringArray(R.array.fontSizeArray);
        massageEditText.setTextSize(Float.parseFloat(mFontSizeList[positionFontSize]));

        //region set up parameter for font spinner
        mFontList = new fontList(contentView.getContext());
        mFontTypeArray = mFontList.getMFont();
        mFontNameList = mFontList.getMFontNameList();

        //set font for each item in spinner
        ArrayAdapter<String> fontTypeAdatper = new ArrayAdapter<String>(contentView.getContext(),
                android.R.layout.simple_spinner_dropdown_item, mFontNameList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTypeface(mFontTypeArray[position]);
                textView.setTextSize(25);
                return textView;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTypeface(mFontTypeArray[position]);
                textView.setTextSize(25);
                return textView;
            }
        };
        fontListSpinner.setAdapter(fontTypeAdatper);
        //endregion


    }

    @Override
    protected void onResume() {
        super.onResume();

        fontListSpinner.setSelection(positionFontType);

        //region when select spinner item
        fontListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                positionFontType = position;
                massageEditText.setTypeface(mFontTypeArray[positionFontType]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //endregion

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (massageEditText.length() <= 200 && massageEditText.length() > 0) {
                    //save data to database
                    String tID, tMassageText, tFontName;
                    Float tFontSize;
                    Massage tMassage;

                    tMassageText = massageEditText.getText().toString();
                    tFontName = mFontNameList[positionFontType];
                    tFontSize = Float.parseFloat(mFontSizeList[positionFontSize]);

                    if (massageID == null) {//if create new
                        tID = mDatabaseClass.createID();
                        //check unique
                        while (mDatabaseClass.checkUniqueID(tID, "massage")) {
                            tID = mDatabaseClass.createID();
                        }
                        tMassage = new Massage(tID, tMassageText, tFontName, tFontSize, null, positionFontType, positionFontSize);
                        mDatabaseClass.insertMassage(tMassage);
                        //go sige
                        Intent intent = new Intent(contentView.getContext(), ZPTSignComposerView.class);
                        intent.putExtra("imageID", imageID);
                        intent.putExtra("massageID", tID);
                        intent.putExtra("contactID", contactID);
                        intent.putExtra("postcardID", postcardID);
                        startActivity(intent);
                    } else {//if edit
                        tID = massageID;
                        tMassage = new Massage(tID, tMassageText, tFontName, tFontSize, positionFontType, positionFontSize);
                        mDatabaseClass.updateMassage(tMassage);
                        //go contact
                        Intent intent = new Intent(contentView.getContext(), ZPTContactComposerView.class);
                        intent.putExtra("imageID", imageID);
                        intent.putExtra("massageID", tID);
                        intent.putExtra("contactID", contactID);
                        intent.putExtra("postcardID", postcardID);
                        startActivity(intent);
                    }

                } else if (massageEditText.length() == 0)
                    Toast.makeText(contentView.getContext(), "Please write massage", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(contentView.getContext(), "Massage is too long", Toast.LENGTH_LONG).show();
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Nuii*/
                Intent intent = new Intent(contentView.getContext(), ZPTStickerComposerView.class);
                intent.putExtra("imageID", imageID);

                onBackPressed();
            }
        });

        //region edit font size
        upSizeTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (positionFontSize < mFontSizeList.length - 1 && positionFontSize >= 0)
                    positionFontSize++;
                massageEditText.setTextSize(Float.parseFloat(mFontSizeList[positionFontSize]));
            }
        });
        downSizeTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (positionFontSize <= mFontSizeList.length - 1 && positionFontSize > 0)
                    positionFontSize--;
                massageEditText.setTextSize(Float.parseFloat(mFontSizeList[positionFontSize]));
            }
        });
        //endregion
    }

    private void init() {
        fontListSpinner = (Spinner) findViewById(R.id.fontListSpinner);
        upSizeTextButton = (Button) findViewById(R.id.upSizeTextbutton);
        downSizeTextButton = (Button) findViewById(R.id.downSizeTextButton);
        nextButton = (Button) findViewById(R.id.nextButton);
        previousButton = (Button) findViewById(R.id.previousButton);
        massageEditText = (EditText) findViewById(R.id.massageEditText);
    }
}
