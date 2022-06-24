package com.dpex.segundata.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dpex.segundata.Constant.Constants;
import com.dpex.segundata.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;


public class ManualFunding extends AppCompatActivity {
    private ProgressBar spinner2;
    private TextView account,error,paidamount;
    String Select ="Card";
    String BnkAccount = "" ;
    List<String> categories = new ArrayList<String>();
    EditText remark,accountname,accountnumber,bankname;
    EditText spinner ;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.manual_funding);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
account= (TextView) findViewById(R.id.account);
error= (TextView) findViewById(R.id.error);
   paidamount= (TextView) findViewById(R.id.paidamount);
  remark= findViewById(R.id.remark);
            spinner2=(ProgressBar)findViewById(R.id.progressBar);
            spinner2.setVisibility(View.GONE);
          accountname= findViewById(R.id.accountname);
      accountnumber= findViewById(R.id.accountnumber);
bankname= findViewById(R.id.bankname);
     spinner =  findViewById(R.id.spinner);

            // Spinner click listener
           // spinner.setOnItemSelectedListener(ManualFunding.this);
            paidamount.setText("Amount to pay: N"+ Constants.CardFundAmount);
            // Spinner Drop down elements

            GetBankAccounts();
            setTitle("Bank Payment");
                    //here is your code
               account.setText("Please kindly pay N"+Constants.CardFundAmount+" into our Account Number.");
      error.setText("Please do not submit this form until you have paid N"+Constants.CardFundAmount+" into our Account Number.");

        final Button fund = findViewById(R.id.fund);

        fund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                spinner2.setVisibility(View.VISIBLE);
                        BankTransfer() ;

                    


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

    public void  BankTransfer()
    {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.BANKFUND+"/"+Constants.USERID,
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


                                Toast.makeText(ManualFunding.this, message, Toast.LENGTH_LONG).show();
onBackPressed();
                            }else{
                                Toast.makeText(ManualFunding.this, message, Toast.LENGTH_LONG).show();

                            }
                            spinner2.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            spinner2.setVisibility(View.GONE);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        spinner2.setVisibility(View.GONE);
                        Log.d("response string",""+error.toString());
                    }
                }){

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

                params.put("amount",""+Constants.CardFundAmount);
                params.put("remark",""+remark.getText().toString()+" , "+accountname.getText().toString()+" , "+accountnumber.getText().toString()+" , "+bankname.getText().toString());

                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ManualFunding.this);
        requestQueue.add(stringRequest);}


    public void  GetBankAccounts()
    {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.GETBANKACCOUNTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("our response string",""+response.toString());
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.toString());
                            //  progressDialogMain.dismiss();
                            String success = jsonObject.getString("success");

                            if ("True".equals(success))
                            {


                                JSONArray arry=jsonObject.getJSONArray("data");

                                for (int i=0;i<arry.length();i++) {

                                    String content =arry.getJSONObject(i).getString("content");


                                    BnkAccount = BnkAccount + " "+content ;
                                }

                              spinner.setText(BnkAccount);

                            }else{
                                Toast.makeText(ManualFunding.this, success, Toast.LENGTH_LONG).show();

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
                params.put("userid",""+Constants.USERID);


                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ManualFunding.this);
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