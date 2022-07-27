package com.dpex.segundata.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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

public class resetPin extends AppCompatActivity {
EditText resetPin, confirmresetPin;
Button resetButton;
    public static final String Pin= "pinKey";
String newPin ="";
    SharedPreferences app_preferences ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pin);
        resetPin = findViewById(R.id.reset_pin);
        confirmresetPin = findViewById(R.id.confirm_reset_pin);
        resetButton = findViewById(R.id.reset_button);
        app_preferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if ("".equalsIgnoreCase(resetPin.getText().toString())) {
                        Toast.makeText(resetPin.this, "Please enter confirm pin", Toast.LENGTH_LONG).show();
                    } else if (!(resetPin.getText().toString().equalsIgnoreCase(confirmresetPin.getText().toString()))) {
                        Toast.makeText(resetPin.this, "Password do not match", Toast.LENGTH_LONG).show();
                    } else {
                        newPin = confirmresetPin.getText().toString();

                        int color = Color.parseColor("#D3D3D3");
                        resetButton.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC));
                        resetButton.setEnabled(false);


                        CHANGE2();
                    }
                }

        });


    }
    private void CHANGE2() {
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
                                    editor.putString(Pin,newPin) ;
                                    editor.commit();
                                    Toast.makeText(resetPin.this, message, Toast.LENGTH_LONG).show();
                                    onBackPressed();

                                }else
                                {
                                    Toast.makeText(resetPin.this, message, Toast.LENGTH_LONG).show();

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
                    params.put("pin",""+newPin);
                    return params;
                }

            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(resetPin.this);
            requestQueue.add(stringRequest);}
    }
}