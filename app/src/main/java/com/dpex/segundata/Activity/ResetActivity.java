package com.dpex.segundata.Activity;

import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;


public class ResetActivity extends AppCompatActivity {
ProgressBar progress ;
 EditText email ;
 TextView resetType;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        email= findViewById(R.id.email);
        resetType = findViewById(R.id.resetText);

        final Button reset = findViewById(R.id.reset);
        progress= findViewById(R.id.progressBar);

        if(Constants.resetType.equals("forgotPassword")){
            resetType.setText("Reset Password");
        }else{
            resetType.setText("Reset Pin");
        }
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
        reset .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setVisibility(View.VISIBLE);
                GETTOKEN();
            }
        });
    }


    public void  GETTOKEN()
    {

Constants.RESETEMAIL = email.getText().toString() ;
Constants.UserEmail = email.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.GETTOKEN,
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
                               Constants.TOKEN = jsonObject.getString("token");
                                Toast.makeText(ResetActivity.this, message, Toast.LENGTH_LONG).show();


                                Intent intent = new Intent(ResetActivity.this,ValidateToken.class);
                                startActivity(intent);
                                finish() ;


                            }else{
                                Toast.makeText(ResetActivity.this, message, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ResetActivity.this,MainActivity.class);
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

                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ResetActivity.this);
        requestQueue.add(stringRequest);}



    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

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
