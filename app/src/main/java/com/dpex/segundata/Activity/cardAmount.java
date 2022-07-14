package com.dpex.segundata.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dpex.segundata.Constant.Constants;
import com.dpex.segundata.R;
import com.teamapt.monnify.sdk.Monnify;
import com.teamapt.monnify.sdk.MonnifyTransactionResponse;
import com.teamapt.monnify.sdk.data.model.TransactionDetails;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class cardAmount extends AppCompatActivity {
EditText cardAmount;
Button payWithCard;
String amount;
    private Monnify monnify = Monnify.Companion.getInstance();
    private static final int INITIATE_PAYMENT_REQUEST_CODE = 7;
    private static final String KEY_RESULT = "7";
    String Price;
    String ServiceSubType = "";
    String transactionResponse = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_amount);
        cardAmount =  findViewById(R.id.cardAmount);
        payWithCard = findViewById(R.id.payWithCard);

        payWithCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyRole();
            }
        });
    }

    private void verifyRole() {
        amount = cardAmount.getText().toString();

        if(Constants.UserROLE.equals("agent")&& Integer.parseInt(amount) < 10000){
            Toast.makeText(cardAmount.this,"As an agent, you cant fund less than 10,000",Toast.LENGTH_LONG).show();
        }else if(amount.equals("")){
            Toast.makeText(cardAmount.this,"Please Enter amount",Toast.LENGTH_LONG).show();
        }else{initiatePayment();}
    }

    private void initiatePayment() {
        Date Time = Calendar.getInstance().getTime();
        String currentTime = String.valueOf(Time);
        TransactionDetails transaction = new TransactionDetails.Builder()
                .amount(new BigDecimal(amount))
                .currencyCode("NGN")
                .customerName(Constants.UserNAME)
                .customerEmail(Constants.UserEmail)
                .paymentReference(currentTime)
                .paymentDescription(currentTime + amount)
                .build();

        monnify.initializePayment(
                cardAmount.this,
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
}