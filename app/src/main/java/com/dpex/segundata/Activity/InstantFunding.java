package com.dpex.segundata.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dpex.segundata.Constant.Constants;
import com.dpex.segundata.R;

import androidx.appcompat.app.AppCompatActivity;

public class InstantFunding extends AppCompatActivity {
    EditText amount;
    TextView i,u ,acc,aname,bname ;
    private ProgressBar spinner2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instant_funding);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        amount = findViewById(R.id.amount);
        u = findViewById(R.id.upper);
        spinner2=(ProgressBar)findViewById(R.id.progressBar);
        spinner2.setVisibility(View.GONE);
       i = findViewById(R.id.i);

        aname = findViewById(R.id.accountname);
        acc = findViewById(R.id.accountno);
        bname = findViewById(R.id.bankname);

        aname.setText("Account Name: "+Constants.AccountName);

        acc.setText(Constants.AccountNo);


        acc.setTextIsSelectable(true);
        bname.setText("Bank Name: "+Constants.BankName);




        final Button fund = findViewById(R.id.fund);
        if(Constants.PAYTYPE.equalsIgnoreCase("instant")) {
            setTitle("Instant Payment");
            i.setText("Instant Funding");
            u.setText("Make payment into the following bank account detail and you will be funded instantly.");
amount.setVisibility(View.GONE);
     fund.setVisibility(View.GONE);
            aname.setVisibility(View.VISIBLE);
            bname.setVisibility(View.VISIBLE);
            acc.setVisibility(View.VISIBLE);
        }else{
            setTitle("Manual Payment");
            i.setText("Manual Funding");
            u.setText("Please note that payment below #5000 will attract #50 Charges. Maximum payment per transaction is #9999.");
            amount.setVisibility(View.VISIBLE);
            fund.setVisibility(View.VISIBLE);
            aname.setVisibility(View.GONE);
            bname.setVisibility(View.GONE);
            acc.setVisibility(View.GONE);
        }



        fund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constants.CardFundAmount = amount.getText().toString();

                if ("".equalsIgnoreCase(amount.getText().toString())) {
                    Toast.makeText(InstantFunding.this, "Amount cannot be empty", Toast.LENGTH_LONG).show();
                } if (Integer.parseInt(amount.getText().toString()) < 100) {
                    Toast.makeText(InstantFunding.this, "Amount must be greater than 100", Toast.LENGTH_LONG).show();
                } else if ((Constants.UserROLE.equalsIgnoreCase("agent")) && (Integer.parseInt(amount.getText().toString()) < Integer.parseInt(Constants.RESELLERMINIMUM))){
                    Toast.makeText(InstantFunding.this, "As a reseller, You are expected to fund your account with N"+Constants.RESELLERMINIMUM+" naira", Toast.LENGTH_LONG).show();
                } else {

                    if(Constants.PAYTYPE.equalsIgnoreCase("instant")) {

                        if(Integer.parseInt(Constants.CardFundAmount) > 2450){
                            Toast.makeText(InstantFunding.this, "You cannot make an Instant Funding that is greater than N2450, ", Toast.LENGTH_LONG).show();

                        }else {
                            spinner2.setVisibility(View.VISIBLE);
                            spinner2.setVisibility(View.GONE);
                            int amount = ((Integer.parseInt(Constants.CardFundAmount) * (Integer.parseInt(Constants.InTSANTFUNDINGCOMMISION)) / 100) + (Integer.parseInt(Constants.CardFundAmount)));

                            Constants.CardFundAmount = Integer.toString(amount);
                            Constants.WEBVIEW = Constants.PAYMENT + Constants.USERID + "/" + Constants.CardFundAmount;
                            Intent intent = new Intent(InstantFunding.this, WebViewActivity.class);
                            startActivity(intent);


                            finish();
                        }
                    }else{

                        if(Integer.parseInt(Constants.CardFundAmount) > 9999){
                            Toast.makeText(InstantFunding.this, "You cannot make a Manual Funding that is greater than N9999, ", Toast.LENGTH_LONG).show();

                        }else {
                            spinner2.setVisibility(View.VISIBLE);
                            spinner2.setVisibility(View.GONE);
                            Intent intent = new Intent(InstantFunding.this, ManualFunding.class);
                            startActivity(intent);
                            finish();
                        }




                    }

                }


            }


        });


    }


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