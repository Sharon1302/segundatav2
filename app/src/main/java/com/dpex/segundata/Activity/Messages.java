package com.dpex.segundata.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.dpex.segundata.R;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Messages extends AppCompatActivity {
    RecyclerView recyclerview;
    ArrayList<MessagesHistoryListPogo>historyListPogos ;
   MessagesHistoryListAdapter dataListAdapter ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages);

        recyclerview=findViewById(R.id.recyclerview);
        final Button fundwallet = findViewById(R.id.fundwallet);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        recyclerview.setAdapter(null);
        if(historyListPogos != null) {
            historyListPogos.clear();
        }
        historyListPogos=new ArrayList<>();




      MessagesDatabaseHandler localsmsdb = new MessagesDatabaseHandler(Messages.this);
        List<MessagesDBObject> settingsdata = localsmsdb.getAllContacts();
        for (MessagesDBObject cn : settingsdata) {
            historyListPogos.add(new MessagesHistoryListPogo(cn.getID(),getResources().getDrawable(R.drawable.logo),Integer.toString(cn.getHistoryID()), cn.getTitle(),cn.getStatus(),cn.getBody(),cn.getTimeSent()));

        }





        dataListAdapter =new MessagesHistoryListAdapter(historyListPogos,Messages.this);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(Messages.this));
        recyclerview.setAdapter(dataListAdapter);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = new Intent(Messages.this,MainActivity.class);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
