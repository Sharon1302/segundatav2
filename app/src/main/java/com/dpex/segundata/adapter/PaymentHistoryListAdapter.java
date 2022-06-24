package com.dpex.segundata.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dpex.segundata.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;



//import org.jsoup.Jsoup;


public class PaymentHistoryListAdapter extends RecyclerView.Adapter<PaymentHistoryListAdapter.ViewHolder> {



    Context context;

    List<PaymentHistoryListPogo> dataAdapters;



    public PaymentHistoryListAdapter(List<PaymentHistoryListPogo> dataAdapters, Context context) {

        super();
        this.dataAdapters=dataAdapters;
        this.context = context;
    }



    @Override
    public PaymentHistoryListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.paymenthistorylist, parent, false);

        PaymentHistoryListAdapter.ViewHolder viewHolder = new PaymentHistoryListAdapter.ViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(PaymentHistoryListAdapter.ViewHolder Viewholder, int position) {


        final PaymentHistoryListPogo dataAdapterOBJ = dataAdapters.get(position);
        Viewholder.name.setText(dataAdapterOBJ.getName());
        Viewholder.amount.setText("N "+dataAdapterOBJ.getAmount());
        Viewholder.status.setText(dataAdapterOBJ.getStatus());
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
        Viewholder.iv_profile.setImageDrawable(dataAdapterOBJ.getImage());


        Viewholder.iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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

        public TextView name,amount,title,status,date;
        public ImageView iv_profile;

        public RelativeLayout picker;



        public ViewHolder(View itemView) {

            super(itemView);

   name=itemView.findViewById(R.id.name);
       amount=itemView.findViewById(R.id.amount);
  title=itemView.findViewById(R.id.title);
   date=itemView.findViewById(R.id.date);
       status=itemView.findViewById(R.id.status);
            iv_profile=itemView.findViewById(R.id.iv_profile);
          picker=itemView.findViewById(R.id.picker);




            }
            }




}


