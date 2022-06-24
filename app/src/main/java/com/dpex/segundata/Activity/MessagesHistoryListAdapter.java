package com.dpex.segundata.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dpex.segundata.Constant.Constants;
import com.dpex.segundata.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import static com.dpex.segundata.Constant.Constants.mdate;


//import org.jsoup.Jsoup;


public class MessagesHistoryListAdapter extends RecyclerView.Adapter<MessagesHistoryListAdapter.ViewHolder> {



    Context context;

    List<MessagesHistoryListPogo> dataAdapters;



    public MessagesHistoryListAdapter(List<MessagesHistoryListPogo> dataAdapters, Context context) {

        super();
        this.dataAdapters=dataAdapters;
        this.context = context;
    }



    @Override
    public MessagesHistoryListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.messageslist, parent, false);

        MessagesHistoryListAdapter.ViewHolder viewHolder = new MessagesHistoryListAdapter.ViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(MessagesHistoryListAdapter.ViewHolder Viewholder, int position) {


        final MessagesHistoryListPogo dataAdapterOBJ = dataAdapters.get(position);
     //   Viewholder.name.setText(dataAdapterOBJ.getName());
        Viewholder.message.setText(dataAdapterOBJ.getBody());
        String stat = dataAdapterOBJ.getStatus();
        if("0".equalsIgnoreCase(dataAdapterOBJ.getStatus())){
            stat = "unread" ;
            Viewholder.title.setTypeface(Typeface.DEFAULT_BOLD) ;
        }else{
            stat = "read" ;
            Viewholder.title.setTypeface(Typeface.SERIF) ;
        }
       // Viewholder.status.setText(stat);
        Viewholder.title.setText(dataAdapterOBJ.getTitle());
        Viewholder.date.setText(dataAdapterOBJ.getDate());
        /*
        font1 = Typeface.createFromAsset(context.getAssets(),"helveticaneue.ttf");
        font2 = Typeface.createFromAsset(context.getAssets(),"opensans-semibold.ttf");
        font3 = Typeface.createFromAsset(context.getAssets(),"opensans_regular.ttf");

        Viewholder.txt_name.setTypeface(font1);
        Viewholder.txt_saving.setTypeface(font3);
        Viewholder.txt_price.setTypeface(font2);
        Viewholder.txt_time.setTypeface(font2);

         */

       // Glide.with(context).load(dataAdapterOBJ.getImage()).into(Viewholder.img_dialog);
   //     Viewholder.iv_profile.setImageDrawable(dataAdapterOBJ.getImage());
        Viewholder.iv_profile.setText(dataAdapterOBJ.getTitle().substring(0, 1).toUpperCase());

        Viewholder.picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.messageid = dataAdapterOBJ.getId() ;
                MessagesDatabaseHandler sdb = new MessagesDatabaseHandler(context);
                sdb.updateContact(new MessagesDBObject(Constants.messageid,0,"","","","1"));
                mdate = dataAdapterOBJ.getDate() ;
                Constants.mtitle = dataAdapterOBJ.getTitle() ;
                Constants.mdata = dataAdapterOBJ.getBody() ;
                Intent intent = new Intent(context, MessagesDetail.class);
                context.startActivity(intent);


            }
        });



    }

    @Override
    public int getItemCount() {

        return dataAdapters.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView message,title,date;
        public TextView iv_profile;

        public RelativeLayout picker;



        public ViewHolder(View itemView) {

            super(itemView);


     message=itemView.findViewById(R.id.message);
  title=itemView.findViewById(R.id.title);
   date=itemView.findViewById(R.id.date);
            iv_profile=itemView.findViewById(R.id.iv_profile);
          picker=itemView.findViewById(R.id.picker);




            }
            }




}


