package com.example.tim.gentleresolve.api_ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tim.gentleresolve.Constants;
import com.example.tim.gentleresolve.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FindSupportActivity extends AppCompatActivity implements View.OnClickListener {
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Bind(R.id.passionInput) EditText mInterest;
    @Bind(R.id.zipInput) EditText mZip;
    @Bind(R.id.radiusInput) EditText mRadius;
    @Bind(R.id.findMeetupsButton) Button mFindMeetupsButton;
    @Bind(R.id.savedMeetupsButton) Button mSavedMeetupsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_support);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        mFindMeetupsButton.setOnClickListener(this);
        mSavedMeetupsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if(v == mFindMeetupsButton){
            Intent myIntent = new Intent(FindSupportActivity.this, ResultsListActivity.class);

            String passion = mInterest.getText().toString();
            String zip = mZip.getText().toString();
            String radius = mRadius.getText().toString();
            myIntent.putExtra("interest", passion);
            myIntent.putExtra("zip", zip);
            myIntent.putExtra("radius", radius);
            addToSharedPreferences(zip, passion);;

            if(!(zip).equals("") && !(passion).equals("")) {
                addToSharedPreferences(passion, zip);
            }
            startActivity(myIntent);
        } else if (v == mSavedMeetupsButton){
            Intent intent = new Intent(FindSupportActivity.this, SavedMeetupActivity.class);
            startActivity(intent);
        }
    }

    private void addToSharedPreferences(String passion, String zip) {
        Log.d("zip", "zip: " + zip);
        Log.d("passion", "passion: " + passion);
        mEditor.putString(Constants.PREFERENCES_ZIP_KEY, zip);
        mEditor.putString(Constants.PREFERENCE_PASSION_KEY, passion);
        mEditor.apply();
    }

}