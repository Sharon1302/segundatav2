package com.dpex.segundata.Activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ChangePin extends AppCompatActivity {
    public static final String UserName = "unameKey";
    public static final String FirstName = "firstnameKey";
    public static final String Name = "nameKey";
    public static final String Email = "emailKey";
    public static final String UserID = "userKey";
    public static final String ROLE = "roleKey";
    public static final String Pin= "pinKey";
    public static final String LastID = "lastidKey";
    SharedPreferences app_preferences ;

String Passwor = "" ,pPasswor = "" ;
    Button change ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pinchange);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    change = findViewById(R.id.change);

        final EditText cpin= findViewById(R.id.cphone);
        final EditText password = findViewById(R.id.phone);

        final EditText cpassword = findViewById(R.id.cpassword);


        app_preferences =
                PreferenceManager.getDefaultSharedPreferences(this);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
if("".equalsIgnoreCase(password.getText().toString())){
    Toast.makeText(ChangePin.this, "Please enter pin", Toast.LENGTH_LONG).show();
} else if("".equalsIgnoreCase(cpassword.getText().toString())){
    Toast.makeText(ChangePin.this, "Please enter confirm pin", Toast.LENGTH_LONG).show();
}else if(!(password.getText().toString().equalsIgnoreCase(cpassword.getText().toString()))){
    Toast.makeText(ChangePin.this, "Password do not match", Toast.LENGTH_LONG).show();
}else {
    Passwor = password.getText().toString() ;
    pPasswor = cpin.getText().toString() ;

    int color = Color.parseColor("#D3D3D3");
change.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC));
change.setEnabled(false);


    CHANGE() ;
}
            }
        });




    }




    public void  CHANGE()
    {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.UPDATEPIN,
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
                                editor.putString(Pin,Passwor) ;
                                editor.commit();
                                Toast.makeText(ChangePin.this, message, Toast.LENGTH_LONG).show();
                                onBackPressed();

                            }else
                            {
                                Toast.makeText(ChangePin.this, message, Toast.LENGTH_LONG).show();

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
                params.put("oldpin",""+pPasswor);
                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ChangePin.this);
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
