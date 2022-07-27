package com.dpex.segundata.Activity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dpex.segundata.Constant.Constants;
import com.dpex.segundata.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.StringRes;

public class MainActivity extends Activity {
    String email,password;

    ProgressBar progress ;
    public static final String bankName = "bnameKey";
    public static final String accountName = "accnameKey";
    public static final String accountNo = "acnnoKey";
    public static final String UserName = "unameKey";
    public static final String Name = "nameKey";
    public static final String FirstName = "firstnameKey";
    public static final String Email = "emailKey";
    public static final String UserID = "userKey";
    public static final String Pin= "pinKey";
    public static final String Password= "passwordKey";
    public static final String ROLE = "roleKey";
    public static final String First = "firstKey";
    public static final String Last = "lastKey";
    public static final String Phone = "phoneKey";
    public static final String LastID = "lastidKey";

    SharedPreferences app_preferences ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Constants.resetType="";
        SharedPreferences preferences =
                getSharedPreferences("my_preferences", MODE_PRIVATE);
// Caused by: java.lang.IllegalArgumentException: The style on this component requires your app theme to be Theme.MaterialComponents (or a descendant).
//E/AndroidRuntime:     at com.google.android.material.internal.ThemeEnforcement.checkTheme(ThemeEnforcement.java:243)

    if(!preferences.getBoolean("onboarding_complete",false)){

            Intent onboarding = new Intent(this, OnboardingActivity.class);
            startActivity(onboarding);

            finish();
            return;
        }
     progress = findViewById(R.id.progressBar);
        final TextInputLayout emailEditText = findViewById(R.id.email);
        final TextInputLayout passwordEditText = findViewById(R.id.phone);
        final Button loginButton = findViewById(R.id.customer);
        final TextView register = findViewById(R.id.register);
        final TextView forgot = findViewById(R.id.forgot);

        app_preferences =
                PreferenceManager.getDefaultSharedPreferences(this);

        if (app_preferences.contains(Email)) {



            String passswordx = app_preferences.getString(Password,"");
            Constants.UserPassword = passswordx;
            String emailx =  app_preferences.getString(Email, "");
            Constants.RESETEMAIL = emailx;
            if(!"".equalsIgnoreCase(emailx)){
                Intent intent = new Intent(MainActivity.this,Dashboard.class);
                startActivity(intent);
                finish() ;
            }
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
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailEditText.getEditText().getText().toString();
                password = passwordEditText.getEditText().getText().toString();
                progress.setVisibility(View.VISIBLE);
                Login() ;

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,RegisterCustomerActivity.class);
                startActivity(intent);

            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.resetType = "forgotPassword";

                Intent intent = new Intent(MainActivity.this,ResetActivity.class);
                startActivity(intent);

            }
        });






    }


    public void  Login()
    {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.LOGIN,
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
                                String Emaill= jsonObject.getString("Email");
                                String U= jsonObject.getString("UserName");
                                String Nam= jsonObject.getString("FirstName");
                                String Las= jsonObject.getString("LastName");
                                String ID= jsonObject.getString("UserID");
                                String pin= jsonObject.getString("pin");
                                String ROLENAME= jsonObject.getString("rolename");
                                String lastid= jsonObject.getString("lastid");
                                String Phonex= jsonObject.getString("Phone");
                                String bn= jsonObject.getString("bankname");
                                String accname= jsonObject.getString("accountname");
                                String accno= jsonObject.getString("accountno");
                                SharedPreferences.Editor editor = app_preferences.edit();
                                editor.putString(Name, Nam+" "+Las);
                                editor.putString(Email, email);
                                editor.putString(First, Nam);
                                editor.putString(Last,Las);
                                editor.putString(Password,password);
                                editor.putString(UserID, ID);
                                editor.putString(ROLE, ROLENAME);
                                editor.putString(LastID, lastid);
                                editor.putString(Phone, Phonex);
                                editor.putString(UserName, U);
                                editor.putString(FirstName, Nam);
                                editor.putString(bankName, bn);
                                editor.putString(Pin,pin) ;
                                editor.putString(accountName, accname);
                                editor.putString(accountNo, accno);
                                editor.commit(); // Very important




                                Constants.UserEmail =  Emaill;
                                Constants.UserPassword = password;

                                if("".equalsIgnoreCase(pin)) {
                                    Constants.PinpAdTitle = "New";
                                    Intent intent = new Intent(MainActivity.this, PinPad.class);
                                    startActivity(intent);
                                   finish();
                                }else{
                                    Intent intent = new Intent(MainActivity.this,Dashboard.class);
                                    startActivity(intent);
                                    finish() ;

                                }



                                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();




                            }else{
                                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();

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
                params.put("password",""+password);
                params.put("email",""+email);

                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);}




    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}