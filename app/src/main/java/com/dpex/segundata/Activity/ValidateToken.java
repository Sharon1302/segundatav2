package com.dpex.segundata.Activity;

import android.content.Intent;
import android.os.Bundle;
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

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;


public class ValidateToken extends AppCompatActivity {
ProgressBar progress ;
  EditText token;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.token);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final Button resend = findViewById(R.id.resend);

        final Button validate= findViewById(R.id.validate);

      token = findViewById(R.id.token);
progress = findViewById(R.id.progressBar);


      /*
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(emailEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };

        emailEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(emailEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });
        */
validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setVisibility(View.VISIBLE);
             VALIDATE();
            }
        });


        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setVisibility(View.VISIBLE);
                RESENDTOKENMAIL();
            }
        });
    }
    public void  RESENDTOKENMAIL()
    {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.RESENDTOKENEMAIL,
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

                                Toast.makeText(ValidateToken.this, message, Toast.LENGTH_LONG).show();

                            }else
                            {
                                Toast.makeText(ValidateToken.this, message, Toast.LENGTH_LONG).show();

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

                params.put("email",""+Constants.RESETEMAIL);
                params.put("token",""+Constants.TOKEN);

                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ValidateToken.this);
        requestQueue.add(stringRequest);}



    public void  VALIDATE()
    {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.VALIDATETOKEN,
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

                                Toast.makeText(ValidateToken.this, message, Toast.LENGTH_LONG).show();

                                if(Constants.resetType.equals("forgotPassword")){
                                    Intent intent = new Intent(ValidateToken.this,ChangePassword.class);
                                    startActivity(intent);
                                    finish() ;
                                }else{
                                    Intent intent = new Intent(ValidateToken.this,ChangePin.class);
                                    startActivity(intent);
                                    finish() ;
                                }



                            }else if ("Invalid".equals(success)) {
                                Toast.makeText(ValidateToken.this, message, Toast.LENGTH_LONG).show();

                            }else
                            {
                                Toast.makeText(ValidateToken.this, message, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ValidateToken.this,ResetActivity.class);
                                startActivity(intent);
                                finish() ;
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

                params.put("email",""+Constants.RESETEMAIL);
                params.put("token",""+token.getText().toString());

                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ValidateToken.this);
        requestQueue.add(stringRequest);}



    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = new Intent(ValidateToken.this,MainActivity.class);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
