package com.dpex.segundata.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.teamapt.monnify.sdk.Monnify;
import com.teamapt.monnify.sdk.MonnifyTransactionResponse;
import com.teamapt.monnify.sdk.data.model.TransactionDetails;
import com.teamapt.monnify.sdk.model.PaymentMethod;
import com.teamapt.monnify.sdk.rest.data.request.SubAccountDetails;
import com.teamapt.monnify.sdk.service.ApplicationMode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;


public class PaymentActivity extends AppCompatActivity {

    String Select = "";
    String BundleID = "";
    ProgressDialog mProgressDialog;
    int LoadPinListOnce = 0;
    private Monnify monnify = Monnify.Companion.getInstance();
    private static final int INITIATE_PAYMENT_REQUEST_CODE = 7;
    private static final String KEY_RESULT = "7";
    String Price;
    String ServiceSubType = "";
    String transactionResponse = "";
    public static final MediaType MEDIA_TYPE = MediaType.parse("application/json");

    Button instant, manual, payWithCard;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Payment");

        monnify.setApiKey("MK_PROD_6SGL2QFF9H");
        monnify.setContractCode("184373797471");
        monnify.setApplicationMode(ApplicationMode.LIVE);
        instant = findViewById(R.id.instant);
        manual = findViewById(R.id.manual);
        payWithCard = findViewById(R.id.payWithCard);
        mProgressDialog = new ProgressDialog(PaymentActivity.this);
        instant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.PAYTYPE = "instant";
                Intent intent = new Intent(PaymentActivity.this, InstantFunding.class);
                startActivity(intent);

            }
        });


        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constants.PAYTYPE = "manual";
                Intent intent = new Intent(PaymentActivity.this, InstantFunding.class);
                startActivity(intent);


            }
        });
        payWithCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent cardIntent = new Intent(PaymentActivity.this,cardAmount.class);
                startActivity(cardIntent);


            }
        });

    }

    private void initiatePayment() {
        Date Time = Calendar.getInstance().getTime();
        String currentTime = String.valueOf(Time);
        String amount = "50";
        TransactionDetails transaction = new TransactionDetails.Builder()
                .amount(new BigDecimal(amount))
                .currencyCode("NGN")
                .customerName(Constants.UserEmail)
                .customerEmail("sharonayoola4@gmail.com")
                .paymentReference(currentTime)
                .paymentDescription(currentTime + amount)
                .build();

        monnify.initializePayment(
                PaymentActivity.this,
                transaction,
                INITIATE_PAYMENT_REQUEST_CODE,
                KEY_RESULT);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        MonnifyTransactionResponse monnifyTransactionResponse = (MonnifyTransactionResponse) data.getParcelableExtra(KEY_RESULT);
        if (monnifyTransactionResponse == null)
            return;
        String message = "";
        transactionResponse = monnifyTransactionResponse.getTransactionReference();

        switch (monnifyTransactionResponse.getStatus()) {
            case PENDING: {
                message = "Transaction not paid for.";
                break;
            }
            case PAID: {
                message = "Customer paid exact amount";
                getTransactionStatus();
                break;
            }
            case OVERPAID: {
                message = "Customer paid more than expected amount.";
                break;
            }
            case PARTIALLY_PAID: {
                message = "Customer paid less than expected amount.";
                break;
            }
            case FAILED: {
                message = "Transaction completed unsuccessfully. This means no payment came in for Account Transfer method or attempt to charge card failed.";
                break;
            }
            case PAYMENT_GATEWAY_ERROR: {
                message = "Payment gateway error";
                break;
            }
        }

    }
    TransactionDetails transaction = new TransactionDetails.Builder()
            //...
            .incomeSplitConfig(new ArrayList<SubAccountDetails>() {{
                add(new SubAccountDetails("MFY_SUB_319452883968", 10.5f, new BigDecimal("500"), true));
                add(new SubAccountDetails("MFY_SUB_259811283666", 10.5f, new BigDecimal("1000"), false));
            }})
            .metaData(new HashMap<String, String>() {{
                put("deviceType", "mobile_android");
                put("ip", "127.168.22.98");
                // any other info
            }})
            .paymentMethods(new ArrayList<PaymentMethod>() {{
                add(PaymentMethod.CARD);
                add(PaymentMethod.ACCOUNT_TRANSFER);
            }})
            .build();


    private void getTransactionStatus() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.ORDER,
                new com.android.volley.Response.Listener<String>() {
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


                                String balance= jsonObject.getString("balance");
                                String discount= jsonObject.getString("discount");
                                Constants.Balance = balance ;
                                Constants.BalanceText = "N "+balance;
                                Constants.Bonus =discount ;

                                Toast.makeText(PaymentActivity.this, message, Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(PaymentActivity.this,Dashboard.class);
                                startActivity(intent);
                                mProgressDialog.dismiss();
                                finish();



                            }else  if ("FalseBalance".equals(success))
                            {
                                mProgressDialog.dismiss();
                                Toast.makeText(PaymentActivity.this, message, Toast.LENGTH_LONG).show();
                            }   else{
                                mProgressDialog.dismiss();
                                Toast.makeText(PaymentActivity.this, message, Toast.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            mProgressDialog.dismiss();
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
                params.put("price",""+Constants.UpPrice);
                params.put("phone",""+Constants.UpPhone);
                params.put("email",""+Constants.UserEmail);
                params.put("bundleid",""+Constants.UpBundleID);

                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(PaymentActivity.this);
        requestQueue.add(stringRequest);


    }
    public void Order() {


    }
}
