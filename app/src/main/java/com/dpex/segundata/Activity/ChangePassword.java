package com.dpex.segundata.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;


public class ChangePassword extends AppCompatActivity {
    public static final String Password = "passwordKey";
ProgressBar progress ;
String Passwor = "" ;
String oldPassword1,ppassword;
public static final String Email = "emailKey";
    SharedPreferences app_preferences ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passwordchange);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final Button change = findViewById(R.id.change);

        final EditText password = findViewById(R.id.phone);

        final EditText cpassword = findViewById(R.id.cpassword);

        progress= findViewById(R.id.progressBar);

        app_preferences =
                PreferenceManager.getDefaultSharedPreferences(this);
          ppassword = Constants.UserPassword;




        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(oldPassword1);
                System.out.println(ppassword);

if("".equalsIgnoreCase(password.getText().toString())){
    password.setError("please enter password");
    Toast.makeText(ChangePassword.this, "Please enter password", Toast.LENGTH_LONG).show();
} else if("".equalsIgnoreCase(cpassword.getText().toString())){
    password.setError("please enter confirm password");
    Toast.makeText(ChangePassword.this, "Please enter confirm password", Toast.LENGTH_LONG).show();
}else if(!(password.getText().toString().equalsIgnoreCase(cpassword.getText().toString()))){
    password.setError("password do not match");
    Toast.makeText(ChangePassword.this, "Password do not match", Toast.LENGTH_LONG).show();
}else  if (password.length()< 8){
    password.setError("Password must be 8 character or above");
    Toast.makeText(ChangePassword.this, "Password must be 8 character or above", Toast.LENGTH_LONG).show();

}
else {
    Passwor = password.getText().toString() ;
    progress.setVisibility(View.VISIBLE);
    CHANGE();
}
            }
        });




    }


    public void  CHANGE()
    {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.CHANGEPASSWORD,
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

                                Toast.makeText(ChangePassword.this, message, Toast.LENGTH_LONG).show();

                                SharedPreferences.Editor editor = app_preferences.edit();
                                editor.putString(Email,"");
                                editor.commit();
                                Intent intent = new Intent(ChangePassword.this,MainActivity.class);
                                startActivity(intent);
                                finish() ;


                            }else
                            {
                                Toast.makeText(ChangePassword.this, message, Toast.LENGTH_LONG).show();

                            }

                            progress.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            progress.setVisibility(View.GONE);
                            e.printStackTrace();

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

                params.put("email",""+ Constants.UserEmail);
                params.put("password",""+Passwor);

                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ChangePassword.this);
        requestQueue.add(stringRequest);}




    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = new Intent(ChangePassword.this,MainActivity.class);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
