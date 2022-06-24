package com.dpex.segundata.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;


public class FinalActivity extends AppCompatActivity {

String Select = "" ;
String BundleID = "" ;
int LoadPinListOnce = 0 ;
ProgressBar progress;
Button add ;
    String Price ;
    String ServiceSubType = "" ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_activity);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle((Constants.NetWorkType+" -> "+Constants.toCamelCase(Constants.ServiceType)+" Request").toUpperCase());
        final TextView mobile = findViewById(R.id.mobile);
        final TextView purchased = findViewById(R.id.purchased);
        final TextView discount = findViewById(R.id.discount);
        final TextView charges= findViewById(R.id.charges);
     add= findViewById(R.id.add);
        progress = findViewById(R.id.progressBar);
mobile.setText(Constants.UpPhone);
purchased.setText("N "+Constants.UpPrice);
        Double Dis = 0.00;

if(Constants.UpDiscount == null || Constants.UpDiscount== "" || ("".equalsIgnoreCase(Constants.UpDiscount)) || ("0".equalsIgnoreCase(Constants.UpDiscount))){
    Dis = 0.00 ;
}else {
    Dis = ((Double.parseDouble(Constants.UpDiscount) / 100) * Double.parseDouble(Constants.UpPrice));
}

       discount.setText("N "+Dis);
charges.setText("N "+(Double.parseDouble(Constants.UpPrice) - Dis) );
        Constants.CardFundAmount = String.valueOf((Double.parseDouble(Constants.UpPrice) - Dis));

        add.setText("Pay N"+(Double.parseDouble(Constants.UpPrice) - Dis) );
        final ImageView image = findViewById(R.id.image);

        if("airtime".equalsIgnoreCase(Constants.ServiceType)) {
            if ("mtn".equalsIgnoreCase(Constants.NetWorkType)) {
                image.setImageDrawable(getDrawable(R.drawable.mtnair));
            } else if ("airtel".equalsIgnoreCase(Constants.NetWorkType)) {
                image.setImageDrawable(getDrawable(R.drawable.airtelair));
            } else if ("glo".equalsIgnoreCase(Constants.NetWorkType)) {
                image.setImageDrawable(getDrawable(R.drawable.gloair));
            } else {
                image.setImageDrawable(getDrawable(R.drawable.nineair));
            }
        }else{
            if ("mtn".equalsIgnoreCase(Constants.NetWorkType)) {
                image.setImageDrawable(getDrawable(R.drawable.mtndata));
            } else if ("airtel".equalsIgnoreCase(Constants.NetWorkType)) {
                image.setImageDrawable(getDrawable(R.drawable.airteldata));
            } else if ("glo".equalsIgnoreCase(Constants.NetWorkType)) {
                image.setImageDrawable(getDrawable(R.drawable.glodata));
            } else {
                image.setImageDrawable(getDrawable(R.drawable.ninedata));
            }
        }


     add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.PinpAdTitle = "buy";
                Intent intent = new Intent(FinalActivity.this, PinPad.class);
                startActivity(intent);

//                if("loading...".equalsIgnoreCase(Constants.Balance)){
//                    Toast.makeText(FinalActivity.this,"please wait, your account balance is populating", Toast.LENGTH_LONG).show();
//                } else if(Double.parseDouble(Constants.UpPrice) > Double.parseDouble(Constants.Balance)){
//                    Toast.makeText(FinalActivity.this,"Your account balance is low", Toast.LENGTH_LONG).show();
//                }else {
//                    progress.setVisibility(View.VISIBLE);
//                    add.setEnabled(false);
//                Order();
//                }

            }
        });



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

                                Toast.makeText(FinalActivity.this, message, Toast.LENGTH_LONG).show();

                                    Intent intent = new Intent(FinalActivity.this,Dashboard.class);
                                    startActivity(intent);
                                    finish();



                            }else  if ("FalseBalance".equals(success))
                            {
                                Toast.makeText(FinalActivity.this, message, Toast.LENGTH_LONG).show();
                            }   else{

                                Toast.makeText(FinalActivity.this, message, Toast.LENGTH_LONG).show();

                            }
                            add.setEnabled(true);
                    progress.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progress.setVisibility(View.GONE);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.setVisibility(View.GONE);
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
        RequestQueue requestQueue = Volley.newRequestQueue(FinalActivity.this);
        requestQueue.add(stringRequest);

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
