package com.dpex.segundata.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.shashank.platform.fancyflashbarlib.Flashbar;
import com.shashank.platform.fancyflashbarlib.anim.FlashAnim;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;


public class Upgrade extends AppCompatActivity {

    public static final String Name = "nameKey";
    public static final String Email = "emailKey";
    public static final String UserID = "userKey";
    public static final String ROLE = "roleKey";
    public static final String LastID = "lastidKey";
    SharedPreferences app_preferences ;
    private static final String TAG = "Flashbar";
String newrole="" ;
TextView balancex,messagetxt ;
    private Flashbar flashbar = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_upgrade);


        final Button fundwallet = findViewById(R.id.fundwallet);
        final Button upgrade = findViewById(R.id.upgrade);
        fundwallet.setVisibility(View.GONE);
        balancex = findViewById(R.id.balance);
        messagetxt = findViewById(R.id.message);
        balancex.setText(Constants.BalanceText);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(("customer".equalsIgnoreCase(Constants.UserROLE)) && ("loading...".equalsIgnoreCase(Constants.Balance))){
            Toast.makeText(Upgrade.this,"please wait, your account balance is populating", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(Upgrade.this,MainActivity.class);
            startActivity(intent);
            finish() ;

        } else  if (("customer".equalsIgnoreCase(Constants.UserROLE))) {
            upgrade.setText("UPGRADE MY ACCOUNT TO AGENT");
      messagetxt.setText("Your account would be upgraded from customer to agent. You will now be able to purchase our products at much reduced prices. but you will not be able to fund your wallet below N10,000.");

            upgrade.setEnabled(true);
            if((flashbar == null) && (Double.parseDouble(Constants.Balance) < Double.parseDouble(Constants.RESELLERMINIMUM))){
                flashbar = fancyFlashbar("Your wallet balance is insufficient to upgrade your account to agent, you need a minimum to N10,000 in your balance to enable this features");
                flashbar.show();
                fundwallet.setVisibility(View.VISIBLE);
                upgrade.setEnabled(false);
            }


        }else{
            upgrade.setText("UPGRADE MY ACCOUNT TO CUSTOMER");
           messagetxt.setText("Your Account would be Downgraded from Agent to Customer. You would no longer be able to Purchase our products at much reduced prices. But you will now be able to fund your wallet below N10,000");
            fundwallet.setVisibility(View.GONE);
        }


        // Get the app's shared preferences
           app_preferences =
                PreferenceManager.getDefaultSharedPreferences(Upgrade.this);



        fundwallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               onBackPressed();
               finish();

            }
        });
 upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (("customer".equalsIgnoreCase(Constants.UserROLE))) {
                    newrole = "agent";
                } else {
                    newrole = "customer";
                }

                if ("agent".equalsIgnoreCase(newrole) && ("loading...".equalsIgnoreCase(Constants.Balance))) {
                    Toast.makeText(Upgrade.this,"please wait, your account balance is populating", Toast.LENGTH_LONG).show();

                } else {
                    Upgrade();
                }
            }
        });





}


    public void  Upgrade()
    {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.UPGRADE,
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
                                editor.putString(ROLE, newrole);
                                editor.commit(); // Very important

                                Constants.UserROLE = newrole ;
                                Toast.makeText(Upgrade.this, message, Toast.LENGTH_LONG).show();


                                Intent intent = new Intent(Upgrade.this,MainActivity.class);
                                startActivity(intent);
                                finish() ;


                            }else{
                                Toast.makeText(Upgrade.this, message, Toast.LENGTH_LONG).show();

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
                params.put("userid",""+Constants.USERID);
                params.put("minimumagentamount",""+Constants.RESELLERMINIMUM);
                params.put("newrolename",""+newrole);

                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(Upgrade.this);
        requestQueue.add(stringRequest);}






    private Flashbar fancyFlashbar(String Message) {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.BOTTOM)
                .enableSwipeToDismiss() //By default this feature is disabled
                .icon(R.drawable.email)
                .title("Change Account Type")
                .message(Message)
                .showIcon()
                .messageColor(getResources().getColor(R.color.black))
                .messageColorRes(R.color.black)
                .positiveActionText("Go Back & Fund Account")
                //.negativeActionText("No, other time")
                .positiveActionTapListener(new Flashbar.OnActionTapListener() {
                    @Override
                    public void onActionTapped(@NotNull Flashbar bar) {
                        onBackPressed();
                        finish();


                    }
                })
                /* .negativeActionTapListener(new Flashbar.OnActionTapListener() {
                      @Override
                      public void onActionTapped(@NotNull Flashbar bar) {
                          bar.dismiss();
                      }
                  })*/
                .enterAnimation(FlashAnim.with(this)
                        .animateBar()
                        .duration(550)
                        .alpha()
                        .overshoot())
                .exitAnimation(FlashAnim.with(this)
                        .animateBar()
                        .duration(500)
                        .alpha()
                        .overshoot())
                .duration(360000)
                //.vibrateOn(Flashbar.Vibration.SHOW, Flashbar.Vibration.DISMISS)
                //.showOverlay()
                //.titleColorRes(R.color.white)
                //.titleSizeInSp(28)
                //.titleTypeface(Typeface.createFromAsset(getAssets(), "BeautifulAndOpenHearted.ttf"))
                //.messageColor(ContextCompat.getColor(this, R.color.white))
                //.messageSizeInSp(24)
                //.messageTypeface(Typeface.createFromAsset(getAssets(), "BeautifulAndOpenHearted.ttf"))
                //.overlayColorRes(R.color.modal)
                //.positiveActionTextColorRes(R.color.black)
                //.negativeActionTextColorRes(R.color.yellow)
                /*.iconAnimation(FlashAnim.with(this)
                        .animateIcon()
                        .pulse()
                        .alpha()
                        .duration(750)
                        .accelerate())*/
                /*.enterAnimation(FlashAnim.with(this)
                        .animateBar()
                        .duration(400)
                        .slideFromLeft()
                        .overshoot())
                .exitAnimation(FlashAnim.with(this)
                        .animateBar()
                        .duration(250)
                        .slideFromLeft()
                        .accelerate())*/
                .build();
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
