package com.dpex.segundata.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.dpex.segundata.Constant.Constants;
import com.dpex.segundata.Handler.CartDatabaseHandler;
import com.dpex.segundata.Object.CartDBObject;
import com.dpex.segundata.R;
import com.shashank.platform.fancyflashbarlib.Flashbar;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;


public class PreLoad extends AppCompatActivity {

    String email,password;
    private static final String TAG = "Flashbar";

    private Flashbar flashbar = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preload);



        final ImageView mtn = findViewById(R.id.mtn);
        final ImageView glo = findViewById(R.id.glo);
        final ImageView airtel = findViewById(R.id.airtel);
        final ImageView nine = findViewById(R.id.nine);
        final ImageView image = findViewById(R.id.image);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Constants.toCamelCase(Constants.ServiceType)

        if("airtime".equalsIgnoreCase(Constants.ServiceType)){
        image.setImageDrawable(getDrawable(R.drawable.airtime));
      mtn.setImageDrawable(getDrawable(R.drawable.mtnair));
         glo.setImageDrawable(getDrawable(R.drawable.gloair));
           airtel.setImageDrawable(getDrawable(R.drawable.airtelair));
    nine.setImageDrawable(getDrawable(R.drawable.nineair));
        }else{
            image.setImageDrawable(getDrawable(R.drawable.data));
            mtn.setImageDrawable(getDrawable(R.drawable.mtndata));
            glo.setImageDrawable(getDrawable(R.drawable.glodata));
            airtel.setImageDrawable(getDrawable(R.drawable.airteldata));
            nine.setImageDrawable(getDrawable(R.drawable.ninedata));
        }

        mtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.COMPANYID = "1" ;
                Constants.NetWorkType = "mtn" ;
                Intent intent = new Intent(PreLoad.this, BuyActivity.class);
                startActivity(intent);


            }
        });


        setTitle((Constants.toCamelCase(Constants.ServiceType)+" Request").toUpperCase());


        Constants.CARTTOTALITEM = 0;
        CartDatabaseHandler localsmsdb = new CartDatabaseHandler(PreLoad.this);
        List<CartDBObject> settingsdata = localsmsdb.getAllContacts();
        for (CartDBObject cn : settingsdata) {
           // id = cn.getID();
            Constants.CARTTOTALITEM = Constants.CARTTOTALITEM + 1 ;
        }


        glo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.COMPANYID = "3" ;
                Constants.NetWorkType = "glo" ;
                Intent intent = new Intent(PreLoad.this, BuyActivity.class);
                startActivity(intent);


            }
        });

        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.COMPANYID = "2" ;
                Constants.NetWorkType = "nine" ;
                Intent intent = new Intent(PreLoad.this, BuyActivity.class);
                startActivity(intent);


            }
        });

      airtel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.COMPANYID = "4" ;
                Constants.NetWorkType = "airtel" ;
                Intent intent = new Intent(PreLoad.this, BuyActivity.class);
                startActivity(intent);


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
