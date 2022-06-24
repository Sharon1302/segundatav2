package com.dpex.segundata.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dpex.segundata.Constant.Constants;
import com.dpex.segundata.R;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Transfer extends AppCompatActivity {
    EditText amount,username;
    Button fund ;
    ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        amount = findViewById(R.id.amount);
        username= findViewById(R.id.username);
 fund = findViewById(R.id.fund);


        fund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.CardFundAmount = amount.getText().toString();
                String Amount =  amount.getText().toString();
                String userName = username.getText().toString();
                Intent intent = new Intent(Transfer.this,PinPad.class);
                intent.putExtra("Amount",Amount);
                intent.putExtra("userName",userName);
                startActivity(intent);
                finish();




            }


        });


    }




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