package com.dpex.segundata.Activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import co.paystack.android.design.widget.PinPadView;

public class PinPad extends AppCompatActivity {
    ProgressBar progress;
    ProgressDialog mProgressDialog;
PinPadView pinpadView;
String Passwor = "" ;
    public static final String bankName = "bnameKey";
    public static final String accountName = "accnameKey";
    public static final String accountNo = "acnnoKey";
    public static final String UserName = "unameKey";
    public static final String Name = "nameKey";
    public static final String FirstName = "firstnameKey";
    public static final String Email = "emailKey";
    public static final String UserID = "userKey";
    public static final String Pin= "pinKey";
    public static final String ROLE = "roleKey";
    public static final String First = "firstKey";
    public static final String Last = "lastKey";
    public static final String Phone = "phoneKey";
    public static final String LastID = "lastidKey";
    SharedPreferences app_preferences ;
    String pinx = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pinpad);

        final TextView title = findViewById(R.id.title);
        if(Constants.PinpAdTitle.equalsIgnoreCase("New")){
            title.setText("Set your 4 digit pin");
        }else{
            title.setText("Confirm Pin");
        }

     pinpadView = findViewById(R.id.pinpadView);
        mProgressDialog = new ProgressDialog(PinPad.this);
        app_preferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (app_preferences.contains(Email)) {


            String emailx = app_preferences.getString(Email, "");
 pinx = app_preferences.getString(Pin, "");

        }
        pinpadView.setOnPinChangedListener(new PinPadView.OnPinChangedListener() {
            @Override
            public void onPinChanged(String oldPin, String newPin) {
              //  appendText("Changed from: '" + oldPin + "' to '" + newPin + "'");
            }

        });

        pinpadView.setOnSubmitListener(new PinPadView.OnSubmitListener() {
            @Override
            public void onCompleted(String pin) {
                if(Constants.PinpAdTitle.equalsIgnoreCase("New")) {
                    appendText(pin);
                }else if(Constants.PinpAdTitle.equalsIgnoreCase("buy")) {
                    appendText2(pin);
                }else{
                    appendText3(pin);
                }
                pinpadView.setAutoSubmit(false);
                pinpadView.clear();
            }

            @Override
            public void onIncompleteSubmit(String pin) {
               // appendText("Submitted Incomplete PIN: " + pin);
                Toast.makeText(PinPad.this, "Submitted Incomplete PIN: " + pin, Toast.LENGTH_LONG).show();

            }
        });
    }
    private void appendText(String text){
        Passwor = text ;
        CHANGE() ;

    }
    private void appendText2(String text) {

        if (pinx.equalsIgnoreCase(text)) {
            Constants.runsubmit = 1;

            if("loading...".equalsIgnoreCase(Constants.Balance)){
                Toast.makeText(PinPad.this,"please wait, your account balance is populating", Toast.LENGTH_LONG).show();
            }
            else if(Double.parseDouble(Constants.UpPrice) > Double.parseDouble(Constants.Balance)){
                Toast.makeText(PinPad.this,"Your account balance is low", Toast.LENGTH_LONG).show();
            }else {

                mProgressDialog.setMessage("Please Wait ");
                mProgressDialog.show();
                Order();
            }



            finish();
        }else{
            Toast.makeText(PinPad.this, "Incorrect Pin. Please try again", Toast.LENGTH_LONG).show();
        }


            finish();
        }

    public void Order()
    {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.ORDER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("our response string",""+response.toString());
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.toString());
                            //  progressDialogMain.dismiss();
                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");
                            if ("True".equals(success))
                            {


                                String balance= jsonObject.getString("balance");
                                String discount= jsonObject.getString("discount");
                                Constants.Balance = balance ;
                                Constants.BalanceText = "N "+balance;
                                Constants.Bonus =discount ;

                                Toast.makeText(PinPad.this, message, Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(PinPad.this,Dashboard.class);
                                startActivity(intent);
                                finish();



                            }else  if ("FalseBalance".equals(success))
                            {
                                mProgressDialog.dismiss();
                                Toast.makeText(PinPad.this, message, Toast.LENGTH_LONG).show();
                            }   else{
                                mProgressDialog.dismiss();
                                Toast.makeText(PinPad.this, message, Toast.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            mProgressDialog.dismiss();
                            e.printStackTrace();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("response string",""+error.toString());
                    }
                }){

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("price",""+Constants.UpPrice);
                params.put("phone",""+Constants.UpPhone);
                params.put("email",""+Constants.UserEmail);
                params.put("bundleid",""+Constants.UpBundleID);

                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(PinPad.this);
        requestQueue.add(stringRequest);

    }

    private void appendText3(String text) {
     /*   SharedPreferences.Editor editor = app_preferences.edit();
        editor.putString(Pin, text);
        editor.commit(); // Very important
        // Toast.makeText(PinPad.this, "Saved", Toast.LENGTH_LONG).show();

      */
        if (pinx.equalsIgnoreCase(text)) {
            Constants.runsubmit = 1;
            String Amount = getIntent().getStringExtra("Amount");
         String  userName = getIntent().getStringExtra("userName");
            Constants.CardFundAmount = Amount;
            //("admin".equalsIgnoreCase(Constants.UserROLE)) &&
            if ("".equalsIgnoreCase(Amount)) {
                Toast.makeText(PinPad.this, "Amount cannot be empty", Toast.LENGTH_LONG).show();
            } else if ("".equalsIgnoreCase(userName)) {
                Toast.makeText(PinPad.this, "Username cannot be empty", Toast.LENGTH_LONG).show();
            } else if  (Integer.parseInt(Amount) < 100) {
                Toast.makeText(PinPad.this, "Amount must be greater than 100", Toast.LENGTH_LONG).show();
            } else if  (Integer.parseInt(Amount) > 10000) {
                Toast.makeText(PinPad.this, "Amount must be lesser than 10000", Toast.LENGTH_LONG).show();
            }  else {

                mProgressDialog.setMessage("Please Wait ");
                mProgressDialog.show();


                BankTransfer() ;
            }
//            Intent intent = new Intent(PinPad.this,Transfer.class);
//            startActivity(intent);
//            finish();
        }else{
            Toast.makeText(PinPad.this, "Incorrect Pin. Please try again", Toast.LENGTH_LONG).show();
        }

    }

    public void  BankTransfer()
    {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.TRANSFERFUND+"/"+Constants.USERID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("our response string",""+response.toString());
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.toString());
                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");
                            if ("True".equals(success))
                            {

                                String balance = jsonObject.getString("balance");
                                Constants.BalanceText = "N " + balance;
                                Constants.Balance = balance;
                                Toast.makeText(PinPad.this, message, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(PinPad.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                mProgressDialog.dismiss();
                                Toast.makeText(PinPad.this, message, Toast.LENGTH_LONG).show();

                            }

                            mProgressDialog.dismiss();
                            int color = Color.parseColor("#0E6251");
//                            fund.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC));
//                            fund.setEnabled(true);
//                            fund.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            mProgressDialog.dismiss();
                            e.printStackTrace();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("response string",""+error.toString());
                    }
                }){

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

                params.put("amount",""+Constants.CardFundAmount);
                String userName = getIntent().getStringExtra("userName");
                params.put("receiveremail",""+userName);

                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(PinPad.this);
        requestQueue.add(stringRequest);}


    public void  CHANGE()
    {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.UPDATEPIN2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("our response string",""+response.toString());
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.toString());
                            //  progressDialogMain.dismiss();
                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");
                            if ("True".equals(success))
                            {

                                SharedPreferences.Editor editor = app_preferences.edit();
                                editor.putString(Pin, Passwor);
                                editor.commit(); // Very important
                                Toast.makeText(PinPad.this, "Saved", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(PinPad.this,MainActivity.class);
                                startActivity(intent);
                                finish() ;

                            }else
                            {
                                Toast.makeText(PinPad.this, message, Toast.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("response string",""+error.toString());
                    }
                }){

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

                params.put("email",""+Constants.UserEmail);
                params.put("pin",""+Passwor);

                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(PinPad.this);
        requestQueue.add(stringRequest);}





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
