package com.dpex.segundata.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class SendMessage extends AppCompatActivity {


    EditText title,recipient,message;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.send_message);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      title= findViewById(R.id.title);

        recipient= findViewById(R.id.recipient);

        message= findViewById(R.id.message);

        final Button fund = findViewById(R.id.fund);

        fund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            
                      SendMessage() ;

                    


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

    public void  SendMessage()
    {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.SEND_MESSAGE,
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


                                Toast.makeText(SendMessage.this, message, Toast.LENGTH_LONG).show();
onBackPressed();
                            }else{
                                Toast.makeText(SendMessage.this, message, Toast.LENGTH_LONG).show();

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

                params.put("title",""+title.getText().toString());
                params.put("recipient",""+recipient.getText().toString());
                params.put("message",""+message.getText().toString());
                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(SendMessage.this);
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