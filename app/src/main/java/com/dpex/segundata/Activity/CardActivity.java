package com.dpex.segundata.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dpex.segundata.R;
import com.google.android.material.textfield.TextInputLayout;
import com.teamapt.monnify.sdk.Monnify;
import com.teamapt.monnify.sdk.MonnifyTransactionResponse;
import com.teamapt.monnify.sdk.data.model.TransactionDetails;
import com.teamapt.monnify.sdk.service.ApplicationMode;

import java.math.BigDecimal;

public class CardActivity extends AppCompatActivity {
    private static final int INITIATE_PAYMENT_REQUEST_CODE = 7;
    private static final String KEY_RESULT = "7";
    TextInputLayout cardNumber,expiryDate,cvv;
Button payWithCard;
    private Monnify monnify = Monnify.Companion.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        monnify.setApiKey("MK_PROD_6SGL2QFF9H");
        monnify.setContractCode("184373797471");
        monnify.setApplicationMode(ApplicationMode.LIVE);
        cardNumber = findViewById(R.id.cardNumber);
        expiryDate = findViewById(R.id.expiryYear);
        cvv = findViewById(R.id.cvv);
        payWithCard = findViewById(R.id.payWithCard);

        payWithCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (!verifyCardNumber() | !validateExpiryDate() | !validateCvv()) {
//                    return;
//                }
            initiatePayment();
            }
        });


    }

    private void initiatePayment() {
        TransactionDetails transaction = new TransactionDetails.Builder()
                .amount(new BigDecimal("200"))
                .currencyCode("NGN")
                .customerName("Customer Name")
                .customerEmail("sharonayoola4@gmail.com")
                .paymentReference("PAYMENT_REF")
                .paymentDescription("Description of payment")
                .build();

        monnify.initializePayment(
                CardActivity.this,
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

//            switch (monnifyTransactionResponse.getStatus()) {
//                case PENDING: { message = "Transaction not paid for."; break; }
//                case PAID: { message = "Customer paid exact amount"; break; }
//                case OVERPAID: { message = "Customer paid more than expected amount."; break; }
//                case PARTIALLY_PAID: { message = "Customer paid less than expected amount."; break; }
//                case FAILED: { message = "Transaction completed unsuccessfully. This means no payment came in for Account Transfer method or attempt to charge card failed."; break; }
//                case PAYMENT_GATEWAY_ERROR: { message = "Payment gateway error"; break; }
//            }
                if (monnifyTransactionResponse.getStatus().equals("PENDING")){
                    message = "Transaction not paid for";
                    Toast.makeText(CardActivity.this, message, Toast.LENGTH_LONG).show();
                    return;
                }else if (monnifyTransactionResponse.getStatus().equals("PAID")){
                    message = "Customer paid exact amount";
                    Toast.makeText(CardActivity.this, message, Toast.LENGTH_LONG).show();
                        return;
                }else if (monnifyTransactionResponse.getStatus().equals("OVERPAID")){
                    message = "Customer paid more than expected amount.";
                    Toast.makeText(CardActivity.this, message, Toast.LENGTH_LONG).show();
                            return;
                }else if (monnifyTransactionResponse.getStatus().equals("PARTIALLY_PAID")){
                    message = "Customer paid less than expected amount.";
                    Toast.makeText(CardActivity.this, message, Toast.LENGTH_LONG).show();
                        return;
                }else if (monnifyTransactionResponse.getStatus().equals("FAILED")){
                    message = "Transaction completed unsuccessfully. This means no payment came in for Account Transfer method or attempt to charge card failed.";
                    Toast.makeText(CardActivity.this, message, Toast.LENGTH_LONG).show();
                        return;
                }else if (monnifyTransactionResponse.getStatus().equals("PAYMENT_GATEWAY_ERROR")){
                    message = "Payment gateway error";
                    Toast.makeText(CardActivity.this, message, Toast.LENGTH_LONG).show();
                            return;
                }else {
                    message = "Didnt perform transaction";
                    Toast.makeText(CardActivity.this, message, Toast.LENGTH_LONG).show();
                        return;
                }

        }


    private boolean validateExpiryDate() {
        String val = expiryDate.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            expiryDate.setError("Field can not be empty");
            return false;
        } else {
            expiryDate.setError(null);
            expiryDate.setErrorEnabled(false);
            return true;
        }
    }

    private boolean verifyCardNumber() {
        String val = cardNumber.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            cardNumber.setError("Field can not be empty");
            return false;
        } else {
            cardNumber.setError(null);
            cardNumber.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateCvv() {
        String val = cvv.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            cvv.setError("Field can not be empty");
            return false;
        } else {
            cvv.setError(null);
            cvv.setErrorEnabled(false);
            return true;
        }
    }
    }
