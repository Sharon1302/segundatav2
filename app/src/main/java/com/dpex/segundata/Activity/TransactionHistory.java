package com.dpex.segundata.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import com.dpex.segundata.adapter.PaymentHistoryListAdapter;
import com.dpex.segundata.adapter.PaymentHistoryListPogo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.blackbox_vision.datetimepickeredittext.view.DatePickerEditText;


public class TransactionHistory extends AppCompatActivity {
    CheckBox pending,approved,cancel;
    private TextView account,error,paidamount;
    String Select ="Card";
    DatePickerEditText start,end ;
    String statuss = "1" ;
    Spinner spinner ;
    String Type = "" ;
    private ProgressBar spinner2;
    PaymentHistoryListAdapter dataListAdapter ;
    RecyclerView recyclerview;
    ArrayList<PaymentHistoryListPogo>historyListPogos ;
    EditText phone,accountname,accountnumber,bankname;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.transhistory);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            recyclerview=findViewById(R.id.recyclerview);
phone= findViewById(R.id.phone);
            approved = findViewById(R.id.checkapproved);
            pending = findViewById(R.id.checkpending);
    cancel = findViewById(R.id.checkcancel);
            spinner2=(ProgressBar)findViewById(R.id.progressBar);
            spinner2.setVisibility(View.GONE);
            start = (DatePickerEditText) findViewById(R.id.start);
            end = (DatePickerEditText) findViewById(R.id.end);
            android.text.format.DateFormat df = new android.text.format.DateFormat();
            start.setText(df.format("yyyy-MM-dd", new java.util.Date()));
            end.setText(df.format("yyyy-MM-dd", new java.util.Date()));

            setTitle("Transaction History");
            approved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    if ( isChecked )
                    {
                        statuss = "1" ;
                        pending.setChecked(false);
                      cancel.setChecked(false);
                    }

                }
            });

     pending.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    if ( isChecked )
                    {
                        statuss = "0" ;
                       approved.setChecked(false);
                        cancel.setChecked(false);
                    }

                }
            });

      cancel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    if ( isChecked )
                    {
                        statuss = "-1" ;
                        approved.setChecked(false);
                       pending.setChecked(false);
                    }

                }
            });


           spinner = (Spinner) findViewById(R.id.spinner);


            // Spinner Drop down elements
            List<String> categories = new ArrayList<String>();
            categories.add("All Products");
            categories.add("data");
                    categories.add("vtu");

            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinner.setAdapter(dataAdapter);

        final Button fund = findViewById(R.id.search);

        fund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

Type =spinner.getSelectedItem().toString() ;
if("All Products".equalsIgnoreCase(Type)){
    Type = "" ;
}else{
    Type =spinner.getSelectedItem().toString() ;
}

                spinner2.setVisibility(View.VISIBLE);
                OrderHistory() ;

                    


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

    public void  OrderHistory()
    {
        recyclerview.setAdapter(null);
        if(historyListPogos != null) {
            historyListPogos.clear();
        }
        historyListPogos=new ArrayList<>();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.ORDERHISTORY,
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

                                JSONArray arry=jsonObject.getJSONArray("fundingdata");

                                //public $Id,$UserId,$HandlingStaffId,$Amount,$TimeRequested,$Remarks,$TimeFunded,$ConfirmedById,$type,$Status;
                                for (int i=0;i<arry.length();i++) {

                          int id =Integer.parseInt(arry.getJSONObject(i).getString("OrderID"));
                                    String amount =arry.getJSONObject(i).getString("Price");
                                    String time =arry.getJSONObject(i).getString("TimeOrdered");
                                    String name =arry.getJSONObject(i).getString("Phone");
                                    String type =arry.getJSONObject(i).getString("ProductType")+" [ "+arry.getJSONObject(i).getString("BundleName")+" ] ";
                                    String status =arry.getJSONObject(i).getString("Status");
                                    if("1".equalsIgnoreCase(status)){
                                        status = "Delivered" ;
                                    } else if("0".equalsIgnoreCase(status)){
                                        status = "Pending" ;
                                    }else{
                                        status = "Cancel" ;
                                    }


                                 historyListPogos.add(new PaymentHistoryListPogo(id,getResources().getDrawable(R.drawable.logo),name, type, status,amount,time));

                                }


                                dataListAdapter =new PaymentHistoryListAdapter(historyListPogos,TransactionHistory.this);
                                recyclerview.setHasFixedSize(true);
                                recyclerview.setLayoutManager(new LinearLayoutManager(TransactionHistory.this));
                                recyclerview.setAdapter(dataListAdapter);



                                
                            }else{
                                Toast.makeText(TransactionHistory.this, message, Toast.LENGTH_LONG).show();

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

                params.put("userid",""+Constants.USERID);
                params.put("status",""+statuss);
                params.put("type",""+Type);
                params.put("phone",""+phone.getText().toString());
                params.put("start",""+start.getText().toString());
                params.put("end",""+end.getText().toString());
                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(TransactionHistory.this);
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