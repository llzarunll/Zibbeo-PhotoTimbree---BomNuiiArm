package com.zibbeo.phototrimbree.CreatePostcard.Contact;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.zibbeo.phototrimbree.BaseNavigationDrawer;
import com.zibbeo.phototrimbree.CreatePostcard.Preview.ZPTPreviewComposerView;
import com.zibbeo.phototrimbree.CreatePostcard.Sign.ZPTSignComposerView;
import com.zibbeo.phototrimbree.Database.Contact;
import com.zibbeo.phototrimbree.Database.Massage;
import com.zibbeo.phototrimbree.Database.databaseClass;
import com.zibbeo.phototrimbree.R;

public class ZPTContactComposerView extends BaseNavigationDrawer {

    View contentView;
    EditText lineOneEditText, lineTwoEditText, firstNameEditText, zipCodeEditText, stateEditText;
    Spinner countryListSpinner;
    Button nextButton, previousButton;
    Switch envelopeSwitch;
    String[] mCountryArray;
    TextInputLayout mFirstnameLayout, line1Layout, line2Layout, zipCodeLayout, stateLayout;
    String massageID, imageID, contactID, postcardID;
    databaseClass mDatabaseClass;
    Boolean envelop = false;
    int positionCountry = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.zpt_contact_composer_view, null, false);
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
        //if edit set data to fields
        if (contactID != null) {
            Contact tContact = mDatabaseClass.getContact(contactID);
            firstNameEditText.setText(tContact.getFirstName());
            lineOneEditText.setText(tContact.getLineOne());
            lineTwoEditText.setText(tContact.getLineTwo());
            zipCodeEditText.setText(tContact.getZipCode());
            stateEditText.setText(tContact.getState());
            positionCountry = tContact.getPositionCountry();
            envelop = tContact.getEnvelop();
        }
        //region set up country spinner
        mCountryArray = getResources().getStringArray(R.array.countryArray);

        ArrayAdapter<String> countryLIstAdepter = new ArrayAdapter<>(contentView.getContext(),
                android.R.layout.simple_spinner_dropdown_item, mCountryArray);
        countryListSpinner.setAdapter(countryLIstAdepter);

        countryListSpinner.setSelection(positionCountry);
        //endregion

        envelopeSwitch.setChecked(envelop);
    }

    @Override
    protected void onResume() {
        super.onResume();

        envelopeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                envelop = isChecked;
            }
        });

        countryListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                positionCountry = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validateFirstName() && !validateLineOne() && !validateLineTwo() && !validateState() && !validateZipCode())
                    Toast.makeText(contentView.getContext(), "Please enter empty field", Toast.LENGTH_LONG).show();
                else {
                    //save data to database
                    String tID, tFirstName, tLineOne, tLineTwo, tZipCode, tState, tCountry;

                    tFirstName = firstNameEditText.getText().toString();
                    tLineOne = lineOneEditText.getText().toString();
                    tLineTwo = lineTwoEditText.getText().toString();
                    tZipCode = zipCodeEditText.getText().toString();
                    tState = stateEditText.getText().toString();
                    tCountry = mCountryArray[countryListSpinner.getSelectedItemPosition()];

                    if (contactID == null) {//if create new
                        tID = mDatabaseClass.createID();
                        //check unique
                        while (mDatabaseClass.checkUniqueID(tID, "contact")) {
                            tID = mDatabaseClass.createID();
                        }
                        Contact tContact = new Contact(tID, tFirstName, tLineOne, tLineTwo, tZipCode, tState, tCountry, envelop, positionCountry);
                        mDatabaseClass.insertContact(tContact);
                    } else {//if edit
                        tID = contactID;
                        Contact tContact = new Contact(tID, tFirstName, tLineOne, tLineTwo, tZipCode, tState, tCountry, envelop, positionCountry);
                        mDatabaseClass.updateContact(tContact);
                    }

                    Toast.makeText(contentView.getContext(), "complete", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(contentView.getContext(), ZPTPreviewComposerView.class);
                    intent.putExtra("imageID", imageID);
                    intent.putExtra("massageID", massageID);
                    intent.putExtra("contactID", tID);
                    intent.putExtra("postcardID", postcardID);
                    startActivity(intent);
                }
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    //region error edit text function
    private boolean validateFirstName() {
        if (firstNameEditText.getText().toString().trim().isEmpty()) {
            mFirstnameLayout.setError("enter first name");
            requestFocus(firstNameEditText);
            return false;
        } else {
            mFirstnameLayout.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateLineOne() {
        if (lineOneEditText.getText().toString().trim().isEmpty()) {
            line1Layout.setError("enter empty field");
            requestFocus(lineOneEditText);
            return false;
        } else {
            line1Layout.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateLineTwo() {
        if (lineTwoEditText.getText().toString().trim().isEmpty()) {
            line2Layout.setError("enter empty field");
            requestFocus(lineTwoEditText);
            return false;
        } else {
            line2Layout.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateZipCode() {
        if (zipCodeEditText.getText().toString().trim().isEmpty()) {
            zipCodeLayout.setError("enter zip code");
            requestFocus(zipCodeEditText);
            return false;
        } else {
            zipCodeLayout.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateState() {
        if (stateEditText.getText().toString().trim().isEmpty()) {
            stateLayout.setError("enter state");
            requestFocus(stateEditText);
            return false;
        } else {
            stateLayout.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.firstNameEditText:
                    validateFirstName();
                    break;
                case R.id.line1EditText:
                    validateLineOne();
                    break;
                case R.id.line2EditText:
                    validateLineTwo();
                    break;
                case R.id.zipCodeEditText:
                    validateZipCode();
                    break;
                case R.id.stateEditText:
                    validateState();
                    break;
            }
        }
    }
    //endregion

    private void init() {
        lineOneEditText = (EditText) findViewById(R.id.line1EditText);
        lineTwoEditText = (EditText) findViewById(R.id.line2EditText);
        firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
        stateEditText = (EditText) findViewById(R.id.stateEditText);
        zipCodeEditText = (EditText) findViewById(R.id.zipCodeEditText);
        countryListSpinner = (Spinner) findViewById(R.id.countryListSpinner);
        nextButton = (Button) findViewById(R.id.nextButton);
        previousButton = (Button) findViewById(R.id.previousButton);
        envelopeSwitch = (Switch) findViewById(R.id.envelopeSwitch);

        mFirstnameLayout = (TextInputLayout) findViewById(R.id.firstnameLayout);
        line1Layout = (TextInputLayout) findViewById(R.id.line1Layout);
        line2Layout = (TextInputLayout) findViewById(R.id.line2Layout);
        zipCodeLayout = (TextInputLayout) findViewById(R.id.zipCodeLayout);
        stateLayout = (TextInputLayout) findViewById(R.id.stateLayout);

        lineOneEditText.addTextChangedListener(new MyTextWatcher(lineOneEditText));
        lineTwoEditText.addTextChangedListener(new MyTextWatcher(lineTwoEditText));
        firstNameEditText.addTextChangedListener(new MyTextWatcher(firstNameEditText));
        zipCodeEditText.addTextChangedListener(new MyTextWatcher(zipCodeEditText));
        stateEditText.addTextChangedListener(new MyTextWatcher(stateEditText));
    }
}
