package com.dpex.segundata.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;


public class RegisterCustomerActivity extends AppCompatActivity {
ProgressBar progress ;
    String email,password,firstname,lastname,phone,confirmpassword,username;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registercustomer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        final TextInputLayout emailEditText = findViewById(R.id.email);
        final TextInputLayout passwordEditText = findViewById(R.id.password);
        final TextView already_user= findViewById(R.id.already_user);
        final TextInputLayout f = findViewById(R.id.firstname);
        final TextInputLayout u = findViewById(R.id.username);
        final TextInputLayout l = findViewById(R.id.lastname);
        final TextInputLayout p = findViewById(R.id.phone);
        final TextInputLayout cp = findViewById(R.id.cpassword);
        final CheckBox c = findViewById(R.id.confirm);
//        final TextView notice = findViewById(R.id.notice);
//      //  final TextView type = findViewById(R.id.type);
        final TextView confirm = findViewById(R.id.confirm);
        progress= findViewById(R.id.progressBar);
        Constants.LogRole = "customer" ;
        //JUST CUSTOMER
        if("customer".equalsIgnoreCase(Constants.LogRole)) {
//            notice.setText("Welcome to Segun Data. \n" +
//                    " Register and Enjoy Amazing discount on Every Purchase.");
     //  type.setText("Customer Register");
            confirm.setText("I understand that the account i am creating is an ordinary account");
        }else{
//            notice.setText("Resellers buy at much reduced prices. We do not charge you for running a reseller account. Therefore, you can use up all your money (to the last kobo). The only thing we require from you is to always fund your wallet with a minimum of N5,000.");
//         //   type.setText("Agent Register");
            confirm.setText("I understand that the account i am creating is a reseller account, and I agree to always fund my wallet with a minimum of N5,000.");
        }


        final Button loginButton = findViewById(R.id.customer);


        already_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterCustomerActivity.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = u.getEditText().getText().toString();
                email = emailEditText.getEditText().getText().toString();
                password = passwordEditText.getEditText().getText().toString();
                firstname = f.getEditText().getText().toString();
                lastname = l.getEditText().getText().toString();
                phone = p.getEditText().getText().toString();
                confirmpassword = cp.getEditText().getText().toString();



                if (("".equalsIgnoreCase(email))){
              Toast.makeText(RegisterCustomerActivity.this, "All field are compulsory", Toast.LENGTH_LONG).show();

       }
                else if ( ("".equalsIgnoreCase(username))){
                    u.setError("please input username");
                    Toast.makeText(RegisterCustomerActivity.this, "All field are compulsory", Toast.LENGTH_LONG).show();

                }else if ( ("".equalsIgnoreCase(password))){
                    passwordEditText.setError("please input password");
                    Toast.makeText(RegisterCustomerActivity.this, "All field are compulsory", Toast.LENGTH_LONG).show();

                }
               else if ( ("".equalsIgnoreCase(firstname))){
                    f.setError("please enter First name");
                    Toast.makeText(RegisterCustomerActivity.this, "All field are compulsory", Toast.LENGTH_LONG).show();

                }else if ( ("".equalsIgnoreCase(lastname))){
                    l.setError("please enter Last name");
                    Toast.makeText(RegisterCustomerActivity.this, "All field are compulsory", Toast.LENGTH_LONG).show();

                }

                else  if (password.length()< 8){
                    passwordEditText.setError("Password must be 8 character or above");
              Toast.makeText(RegisterCustomerActivity.this, "Password must be 8 character or above", Toast.LENGTH_LONG).show();

          }else  if (phone.length()!= 11){
                    p.setError("Phone number should be 11 digits");
                    Toast.makeText(RegisterCustomerActivity.this, "Phone number must be 11 character or above", Toast.LENGTH_LONG).show();

                }else  if (!isValidEmailId(email)){
                    emailEditText.setError("Please Enter Valid Email");
                    Toast.makeText(RegisterCustomerActivity.this, "Email is not valid", Toast.LENGTH_LONG).show();

                }else if(c.isChecked()) {
                    progress.setVisibility(View.VISIBLE);
                    Register();
                }else{
                    Toast.makeText(RegisterCustomerActivity.this, "Please agree to the terms and condition", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private boolean isValidEmailId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    public void  Register()
    {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.REGISTER,
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

                                //  String id= jsonObject.getString("id");

                                Toast.makeText(RegisterCustomerActivity.this, message, Toast.LENGTH_LONG).show();
                            /*    Intent intent = new Intent(RegisterCustomerActivity.this,MainActivity.class);
                                startActivity(intent); */
                                Intent intent = new Intent(RegisterCustomerActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(RegisterCustomerActivity.this, message, Toast.LENGTH_LONG).show();

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
                params.put("UserName",""+username);
                params.put("Password",""+password);
                params.put("Email",""+email);
                params.put("FirstName",""+firstname);
                params.put("LastName",""+lastname);
                params.put("Phone",""+phone);
                params.put("role",""+Constants.LogRole);
                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(RegisterCustomerActivity.this);
        requestQueue.add(stringRequest);}




    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }


    @Override
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
