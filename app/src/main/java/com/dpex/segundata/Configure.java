package com.dpex.segundata;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.dpex.segundata.Constant.Constants;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class Configure extends AppCompatActivity {

    private CheckBox noti;
    public static final String Email = "emailKey";
    public static final String OPERATINGMODE= "opmodeKey";
    public static final String OPERATINGCODE= "opcodeKey";
    public static final String ORNumber= "ornumberKey";
    public static final String SENDOP= "sendupKey";
    public static final String SYNCDURATION= "syncdurationKey";
    SharedPreferences app_preferences ;
    EditText responsecode,responseno,duration;
    String mode,code,number,send,sync ;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.cofigure);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
noti= findViewById(R.id.noti);
            responsecode=  findViewById(R.id.responsecode);
            responseno =findViewById(R.id.responseno);
            duration= findViewById(R.id.duration);
            app_preferences =
                    PreferenceManager.getDefaultSharedPreferences(this);

            if (app_preferences.contains(Email)) {
              mode = app_preferences.getString(OPERATINGMODE, "");
                code =app_preferences.getString(OPERATINGCODE, "");
              number =   app_preferences.getString(ORNumber, "");
              send =  app_preferences.getString(SENDOP, "");
              sync =  app_preferences.getString(SYNCDURATION, "");
            }
            responsecode.setText(code);
            duration.setText(sync);
            responseno.setText(number);
            if("0".equalsIgnoreCase(send)) {
                noti.setChecked(false);
            }else{
                noti.setChecked(true);
            }
            final Spinner spinner = (Spinner) findViewById(R.id.spinner);

            // Spinner click listener
           // spinner.setOnItemSelectedListener(ManualFunding.this);

            // Spinner Drop down elements
            List<String> categories = new ArrayList<String>();
if("Manual".equalsIgnoreCase(mode)) {
    categories.add("Manual");
    categories.add("Automatic");
}else {
    categories.add("Automatic");
    categories.add("Manual");
}

            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinner.setAdapter(dataAdapter);

        final Button fund = findViewById(R.id.submit);

        fund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = Color.parseColor("#D3D3D3");
           fund.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC));
      fund.setEnabled(false);
                SharedPreferences.Editor editor = app_preferences.edit();
                editor.putString(OPERATINGMODE,spinner.getSelectedItem().toString()) ;
                editor.putString(OPERATINGCODE,responsecode.getText().toString()) ;
                editor.putString(ORNumber,responseno.getText().toString()) ;
                if(noti.isChecked()) {
                    editor.putString(SENDOP, "1");
                }else{
                    editor.putString(SENDOP, "0");
                }
                editor.putString(SYNCDURATION,duration.getText().toString()) ;
                editor.commit(); // Very important


                Constants.traderorigin = responsecode.getText().toString();
                Constants.SYNCDURATION=  duration.getText().toString();
                Constants.OPERATINGMODE =  spinner.getSelectedItem().toString();



                Toast.makeText(Configure.this, "Success", Toast.LENGTH_LONG).show();
                onBackPressed();
                 finish();
            }
        });


    }
/*
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
       // Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
*/
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



}