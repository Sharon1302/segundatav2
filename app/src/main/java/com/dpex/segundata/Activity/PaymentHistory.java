package com.dpex.segundata.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
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
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.blackbox_vision.datetimepickeredittext.view.DatePickerEditText;

public class PaymentHistory extends AppCompatActivity {
    private DatePickerEditText start;
    private DatePickerEditText end;
String startx,endx ;
    private ProgressBar spinner2;
  PaymentHistoryListAdapter dataListAdapter ;
    RecyclerView recyclerview;
    ArrayList<PaymentHistoryListPogo>historyListPogos ;
private Button search;
String statuss = "1" ;
    String typee = "All" ;
    CheckBox pending,approved,all,bank,card,admin ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payhistory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinner2=(ProgressBar)findViewById(R.id.progressBar);
        spinner2.setVisibility(View.GONE);
start = (DatePickerEditText) findViewById(R.id.start);
end = (DatePickerEditText) findViewById(R.id.end);
        setTitle("Payment History");
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        start.setText(df.format("yyyy-MM-dd", new java.util.Date()));
end.setText(df.format("yyyy-MM-dd", new java.util.Date()));


 approved = findViewById(R.id.checkapproved);
     pending = findViewById(R.id.checkpending);
   all = findViewById(R.id.checkall);
      bank = findViewById(R.id.checkbank);
   card= findViewById(R.id.checkcard);
  admin= findViewById(R.id.checkadmin);
search = findViewById(R.id.search);
        recyclerview=findViewById(R.id.recyclerview);
search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startx = start.getText().toString() ;
                endx = end.getText().toString() ;
                spinner2.setVisibility(View.VISIBLE);
                PaymentHistory() ;

            }
        });



   approved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    statuss = "1" ;
          pending.setChecked(false);
                }else{
                    statuss = "0" ;
                  approved.setChecked(false);

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
                }else{
                    statuss = "1" ;
                pending.setChecked(false);

                }

            }
        });



     all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                  typee = "All" ;
                 bank.setChecked(false);
                 card.setChecked(false);
                 admin.setChecked(false);
                }

            }
        });

   card.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    typee = "ATM" ;
                    bank.setChecked(false);
                   admin.setChecked(false);
                    all.setChecked(false);
                }

            }
        });

        bank.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    typee = "BANK" ;
                    card.setChecked(false);
                    admin.setChecked(false);
                    all.setChecked(false);
                }

            }
        });

        admin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    typee = "CREDIT" ;
                    card.setChecked(false);
                    bank.setChecked(false);
                    all.setChecked(false);
                }

            }
        });

    }




    public void  PaymentHistory()
    {

        recyclerview.setAdapter(null);
        if(historyListPogos != null) {
            historyListPogos.clear();
        }
        historyListPogos=new ArrayList<>();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.PAYMENTHISTORY+"/"+startx+"/"+endx,
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


                                JSONArray arry=jsonObject.getJSONArray("fundingdata");

                                //public $Id,$UserId,$HandlingStaffId,$Amount,$TimeRequested,$Remarks,$TimeFunded,$ConfirmedById,$type,$Status;
                                for (int i=0;i<arry.length();i++) {

                              int id =Integer.parseInt(arry.getJSONObject(i).getString("Id"));
                                    String amount =arry.getJSONObject(i).getString("Amount");
                                    String time =arry.getJSONObject(i).getString("TimeRequested");
                                    String type =arry.getJSONObject(i).getString("type");
                                    String status =arry.getJSONObject(i).getString("Status");
if("1".equalsIgnoreCase(status)){
    status = "Funded" ;
}else{
    status = "Pending" ;
}
                                    historyListPogos.add(new PaymentHistoryListPogo(id,getResources().getDrawable(R.drawable.logo), Constants.UserNAME, type, status,amount,time));

                                }


                                dataListAdapter =new PaymentHistoryListAdapter(historyListPogos,PaymentHistory.this);
                                recyclerview.setHasFixedSize(true);
                                recyclerview.setLayoutManager(new LinearLayoutManager(PaymentHistory.this));
                                recyclerview.setAdapter(dataListAdapter);
                                spinner2.setVisibility(View.GONE);
                            }else{
                                Toast.makeText(PaymentHistory.this, message, Toast.LENGTH_LONG).show();
                                spinner2.setVisibility(View.GONE);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            spinner2.setVisibility(View.GONE);
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
                params.put("type",""+typee);
                params.put("status",""+statuss);
                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(PaymentHistory.this);
        requestQueue.add(stringRequest);}



        
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
               onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
