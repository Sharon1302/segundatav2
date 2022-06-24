package com.dpex.segundata.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import com.dpex.segundata.Constant.Constants;
import com.dpex.segundata.R;
public class MessagesDetail extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messagedetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView date = findViewById(R.id.date);
        TextView title = findViewById(R.id.title);
        TextView data = findViewById(R.id.data);


           date.setText(Constants.mdate);
     title.setText(Constants.mtitle);
     data.setText(Constants.mdata);


    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = new Intent(MessagesDetail.this, Messages.class);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }






}
