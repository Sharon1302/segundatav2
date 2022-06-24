package com.dpex.segundata.Activity.ui.account;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dpex.segundata.Activity.Dashboard;
import com.dpex.segundata.Constant.Constants;
import com.dpex.segundata.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class updateAccountActivity extends AppCompatActivity {
    EditText phone,firstname,lastname,username;
    TextView i,u ;
    public static final String bankName = "bnameKey";
    public static final String accountName = "accnameKey";
    public static final String accountNo = "acnnoKey";
    public static final String UserName = "unameKey";
    public static final String Name = "nameKey";
    public static final String FirstName = "firstnameKey";
    public static final String Email = "emailKey";
    public static final String UserID = "userKey";
    public static final String ROLE = "roleKey";
    public static final String Phone = "phoneKey";
    public static final String LastID = "lastidKey";
    SharedPreferences app_preferences ;

    private AccountsViewModel notificationsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notificationsViewModel =
                ViewModelProviders.of(this).get(AccountsViewModel.class);
        setContentView(R.layout.activity_update_account);

//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                //         textView.setText(s);
//            }
//        });

        username= findViewById(R.id.username);
        phone = findViewById(R.id.phone);
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        phone.setText(Constants.PhoneNo);
        firstname.setText(Constants.FirstName);
        lastname.setText(Constants.LastName);
        username.setText(Constants.USERNAME);
        final Button update = findViewById(R.id.update);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Update() ;


            }
        });
    }

    public void Update()
    {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.UPDATEMYACCOUNT,
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



                                Toast.makeText(updateAccountActivity.this, message, Toast.LENGTH_LONG).show();


                                Intent intent = new Intent(updateAccountActivity.this, Dashboard.class);
                                startActivity(intent);



                            }else{
                                Toast.makeText(updateAccountActivity.this, message, Toast.LENGTH_LONG).show();

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
                params.put("user",""+username.getText().toString());
                params.put("userid",""+Constants.USERID);
                params.put("first",""+firstname.getText().toString());
                params.put("last",""+lastname.getText().toString());
                params.put("phone",""+phone.getText().toString());
                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(updateAccountActivity.this);
        requestQueue.add(stringRequest);}
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}