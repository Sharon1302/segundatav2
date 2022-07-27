package com.dpex.segundata.Activity.ui.home;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dpex.segundata.Activity.ContactActivity;
import com.dpex.segundata.Activity.MainActivity;
import com.dpex.segundata.Activity.Messages;
import com.dpex.segundata.Activity.MessagesDBObject;
import com.dpex.segundata.Activity.MessagesDatabaseHandler;
import com.dpex.segundata.Activity.MyApplication;
import com.dpex.segundata.Activity.PaymentActivity;
import com.dpex.segundata.Activity.PreLoad;
import com.dpex.segundata.Activity.PriceListActivity;
import com.dpex.segundata.Activity.ResetActivity;
import com.dpex.segundata.Activity.SendMessage;
import com.dpex.segundata.Activity.Transfer;
import com.dpex.segundata.Activity.Upgrade;
import com.dpex.segundata.Activity.ChangePin;
import com.dpex.segundata.Configure;
import com.dpex.segundata.Constant.Constants;
import com.dpex.segundata.Handler.BundleDatabaseHandler;
import com.dpex.segundata.Object.BundleDBObject;
import com.dpex.segundata.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import static android.view.View.GONE;


public class HomeFragment extends Fragment {
    int notofy = 0 ;
    private HomeViewModel homeViewModel;
int a = 0 ;
String phoneNo="",message="";
    View include ;
    String OrderID = "";
    private NotificationManager mNotificationManager;
    ImageView menu ;
    boolean Menu = true ;
    LinearLayout data,airtime,fundtransfer,linviewmessages, upgrade ;
//    RelativeLayout tacct =root.findViewById(R.id.tacct);
    public static final String UserName = "unameKey";
    public static final String FirstName = "firstnameKey";
    public static final String Name = "nameKey";
    public static final String Email = "emailKey";
    public static final String UserID = "userKey";
    public static final String ROLE = "roleKey";
    public static final String LastID = "lastidKey";
    SharedPreferences app_preferences ;
    int Checkifbundle = 0 ;
    int Millisec = 10000 ;
    TextView firstedittext,accountType ;
    String s1 ;
    TextView txtbalance,     viewmessages ;
    TextView txtbonus;
    FloatingActionButton whatsapp ;
    ImageView imageprogressBar ;
    ProgressBar progressBar ;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               // textView.setText(s);
            }
        });
          Constants.resetType="";

        firstedittext=root.findViewById(R.id.firstedittext);
        include =root.findViewById(R.id.include);
        menu =root.findViewById(R.id.menu);
        accountType = root.findViewById(R.id.accountType);
        txtbalance =root.findViewById(R.id.txtbalance);
        txtbonus =root.findViewById(R.id.txtbonus);
        imageprogressBar=root.findViewById(R.id.imageprogressBar);
        imageprogressBar.setVisibility(GONE);
        fundtransfer=root.findViewById(R.id.fundtransfer);
        progressBar=root.findViewById(R.id.progressBar);
        upgrade = root.findViewById(R.id.upgrade);
        imageprogressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetHistoryUpdate();
            }
        });

        txtbalance.setText("0");
        txtbonus.setText("0");

        whatsapp= root.findViewById(R.id.whatsapp);
whatsapp.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String url = "https://api.whatsapp.com/send?phone=+2348169260427";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
});


        linviewmessages = root.findViewById(R.id.linviewmessages);
        viewmessages = root.findViewById(R.id.viewmessages);

        MessagesDatabaseHandler localsmsdb = new MessagesDatabaseHandler(getActivity());
        Constants.unread  = localsmsdb.getunread();

        viewmessages.setText(""+Constants.unread);

        if(Constants.unread  > 0) {
            linviewmessages.setVisibility(View.VISIBLE);
        }else{
            linviewmessages.setVisibility(GONE);
        }
//        if (("customer".equalsIgnoreCase(Constants.UserROLE))) {
//            accountType.setText("Account Type: customer");
//        } else if (("agent".equalsIgnoreCase(Constants.UserROLE))) {
//            accountType.setText("Account Type: Agent");
//        }else{
//            accountType.setText("Account Type: Admin");
//        }
        linviewmessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Messages.class);
                startActivity(intent);
            }
        });


        fundtransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Transfer.class);
                startActivity(intent);
            }
        });

        ImageView logout =root.findViewById(R.id.logout);

       // View sendmessagev =root.findViewById(R.id.sendmessagev);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteCache(getContext()) ;
                FileUtils.deleteQuietly(getActivity().getCacheDir());
                clearApplicationData();

                SharedPreferences.Editor editor = app_preferences.edit();
                editor.putString(Email, "");
                editor.commit(); // Very important


                startActivity(new Intent(getActivity(), MainActivity.class));
      getActivity().finish() ;
            }
        });

        upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Upgrade.class);
                startActivity(intent);
            }
        });
        RelativeLayout tmessage =root.findViewById(R.id.tmessage);
        tmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Messages.class);
                startActivity(intent);
            }
        });
    RelativeLayout tconfigure = root.findViewById(R.id.tconfigure);
        tconfigure.setVisibility(GONE);
        if("admin".equalsIgnoreCase(Constants.UserROLE)) {
            tconfigure.setVisibility(View.VISIBLE);
        }
        tconfigure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Configure.class);
                startActivity(intent);
            }
        });
        RelativeLayout rateus =root.findViewById(R.id.trateus);
  rateus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName =getContext().getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });

        RelativeLayout tcontactus =root.findViewById(R.id.tcontactus);
        tcontactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ContactActivity.class);
                startActivity(intent);
            }
        });







        RelativeLayout tchangepi =root.findViewById(R.id.tchangepi);
        tchangepi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangePin.class);
                startActivity(intent);
            }
        });



        RelativeLayout tresetpi =root.findViewById(R.id.tresetpi);
        tresetpi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ResetActivity.class);
                startActivity(intent);
            }
        });




        RelativeLayout tpricelist =root.findViewById(R.id.tpricelist);
        tpricelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getActivity(), PriceListActivity.class);
                startActivity(intent2);
            }
        });


    LinearLayout fundwallet =root.findViewById(R.id.fundwallet);
       fundwallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(getActivity(), PaymentActivity.class);
                startActivity(intent2);
            }
        });




        RelativeLayout sendmessage =root.findViewById(R.id.sendmessage);

   sendmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getActivity(), SendMessage.class);
                startActivity(intent2);
            }
        });
        RelativeLayout taddfunds =root.findViewById(R.id.taddfunds);
        taddfunds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                include.setVisibility(GONE);
                menu.setColorFilter(getActivity().getResources().getColor(R.color.white));
                Menu = true ;
                Intent intent2 = new Intent(getActivity(), PaymentActivity.class);
                startActivity(intent2);
            }
        });


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Menu){
                    include.setVisibility(View.VISIBLE);
                    menu.setColorFilter(getActivity().getResources().getColor(R.color.grey));
                    Menu = false ;
                }else{
                    include.setVisibility(GONE);
                    menu.setColorFilter(getActivity().getResources().getColor(R.color.white));
                    Menu = true ;
                }

            }
        });

        // Get the app's shared preferences
        app_preferences =
                PreferenceManager.getDefaultSharedPreferences(getActivity());



        s1 =  app_preferences.getString(Name, "");
        Constants.UserEmail =  app_preferences.getString(Email, "");
        Constants.UserROLE =  app_preferences.getString(ROLE, "");
        Constants.UserNAME =  app_preferences.getString(Name, "");
        Constants.LastIID  =  app_preferences.getString(LastID, "");

        if(!"Shigo02@gmail.com".equalsIgnoreCase(Constants.UserEmail)){
            sendmessage.setVisibility(GONE);
        //    sendmessagev.setVisibility(GONE);
        }else{
            sendmessage.setVisibility(View.VISIBLE);
           // sendmessagev.setVisibility(View.VISIBLE);
        }


        Constants.USERID =  app_preferences.getString(UserID, "");

        if(("".equalsIgnoreCase(Constants.USERNAME)) || (Constants.USERNAME == null)){
            firstedittext.setText("Welcome: " + app_preferences.getString(FirstName, ""));
        }else {
            firstedittext.setText("Welcome: " + Constants.USERNAME);
        }
        GetHistoryUpdate();
        GetBundle();







        data =root.findViewById(R.id.data);
        airtime=root.findViewById(R.id.airtime);



        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.ServiceType = "data" ;
                Intent intent2 = new Intent(getActivity(), PreLoad.class);
                startActivity(intent2);
            }
        });

        airtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.ServiceType = "airtime" ;
                Intent intent2 = new Intent(getActivity(),PreLoad.class);
                startActivity(intent2);
            }
        });

    
    
    
    
        return root;
    }







    public void ReloadHistory() {


        //25000
        new CountDownTimer(Millisec, 1000) {

            public void onTick(long millisUntilFinished) {
                //this is still counting in millseconds

            }

            public void onFinish() {
                //The first time Millisec is for 5000...subsequent is 60,000 miseconds
                Millisec = 60000 ;
a++ ;
if(a == 1) {
    GetHistoryUpdate();
    if("Automatic".equalsIgnoreCase(Constants.OPERATINGMODE)) {
        SelectOrderStatus();
    }
}else{
    a = 0 ;
    ReloadHistory();
}



            }
        }.start();
    }


    public void  UPDATEORDERSTATUS()
    {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.UPDATEORDERSTATUS,
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

                                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();


                            }else
                            {
                                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

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

                params.put("newstatus","1");
                params.put("orderid",""+OrderID);

                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);}



    public void  SelectOrderStatus()
    {

      //  Toast.makeText(getActivity(),"Bisi", Toast.LENGTH_LONG).show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.SeLECTORDERSTATUS+"0",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("our response string",""+response.toString());
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.toString());
                            //  progressDialogMain.dismiss();
                            String success = jsonObject.getString("success");

                            if ("True".equals(success))
                            {

                            //    Toast.makeText(getActivity(),"kola", Toast.LENGTH_LONG).show();
                                JSONArray arry=jsonObject.getJSONArray("orderdata");

                                for (int i=0;i<arry.length();i++) {
                                    //percitant=percitant+Integer.valueOf(arry.get(i)) ;
                                    int bundleid = Integer.parseInt(arry.getJSONObject(i).getString("BundleID"));

                                    String price =arry.getJSONObject(i).getString("Price");
                                    String bundletype =arry.getJSONObject(i).getString("ProductType");
                                    String bundlesubtype =arry.getJSONObject(i).getString("ProductSubtype");
                                    String Codex =arry.getJSONObject(i).getString("Codex");
                                    String OperatorNumberx =arry.getJSONObject(i).getString("OperatorNumberx");
                                    String OrderIDx =arry.getJSONObject(i).getString("OrderID");
                                    String Traderx =arry.getJSONObject(i).getString("Trader");
                                    String OrderingEmployeeID = arry.getJSONObject(i).getString("OrderingEmployeeID");
                                    Constants.ORDERID = OrderIDx ;
                                    Constants.cusid = OrderingEmployeeID;
                       //     Toast.makeText(getActivity(), Uri.encode(Codex), Toast.LENGTH_LONG).show();

                                    if(Constants.traderorigin.equalsIgnoreCase(Traderx) ) {
                                        if ("".equalsIgnoreCase(OperatorNumberx)) {
                                            //USSD

                                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Uri.encode(Codex)));
                                            startActivity(intent);
                                            OrderID = OrderIDx;
                                            UPDATEORDERSTATUS();
                                            Toast.makeText(getActivity(), Codex + Traderx+"Sentussd" + OperatorNumberx, Toast.LENGTH_LONG).show();
                                   } else {
                                            //SMS
                                            Toast.makeText(getActivity(), Codex + "Sentsms" + OperatorNumberx, Toast.LENGTH_LONG).show();
                                            phoneNo = OperatorNumberx;
                                            message = Codex;

                                            SmsManager smsManager = SmsManager.getDefault();
                                            smsManager.sendTextMessage(phoneNo, null, message, null, null);
                                            Toast.makeText(getActivity(), "SMS sent.",
                                                    Toast.LENGTH_LONG).show();

                                            OrderID = OrderIDx;
                                            UPDATEORDERSTATUS();
                                        }
//i dont need you again
                                        break;
                                    }


                                }

                                //  Toast.makeText(getActivity(),OrderID+"HH"+arry.length(), Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getActivity(), success, Toast.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            //   Toast.makeText(getActivity(),"J"+e.getMessage().toString(), Toast.LENGTH_LONG).show();
                            e.printStackTrace();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  Toast.makeText(getActivity(),"F"+error.toString(), Toast.LENGTH_LONG).show();
                        Log.d("response string",""+error.toString());
                    }
                }){

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("userid",""+Constants.USERID);
                params.put("traderorigin",""+Constants.traderorigin);

                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);}

    public void  GetBundle()
    {
        Checkifbundle = 0 ;
        BundleDatabaseHandler localsmsdb = new BundleDatabaseHandler(getActivity());
        List<BundleDBObject> settingsdata = localsmsdb.getAllContacts();
        for (BundleDBObject cn : settingsdata) {
            Checkifbundle =1 ;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.GETBUNDLE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("our response string",""+response.toString());
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.toString());
                            //  progressDialogMain.dismiss();
                            String success = jsonObject.getString("success");

                            if ("True".equals(success))
                            {

                                if( Checkifbundle  == 1){
                                    BundleDatabaseHandler sdb = new BundleDatabaseHandler(getActivity());
                                    sdb.delete(new BundleDBObject(1,"","","","","","",""));

                                }

                                String ROLENAME= jsonObject.getString("rolename");
                                SharedPreferences.Editor editor = app_preferences.edit();
                                Constants.UserROLE =  ROLENAME ;
                                editor.putString(ROLE, ROLENAME);
                                editor.commit();
                                JSONArray arry=jsonObject.getJSONArray("bundledata");

                                for (int i=0;i<arry.length();i++) {
                                    //percitant=percitant+Integer.valueOf(arry.get(i)) ;
                                    int bundleid = Integer.parseInt(arry.getJSONObject(i).getString("BundleID"));
                                    String bundlename =arry.getJSONObject(i).getString("BundleName");
                                    String companyid =arry.getJSONObject(i).getString("CompanyID");
                                    String price =arry.getJSONObject(i).getString("Price");
                                    String bundletype =arry.getJSONObject(i).getString("ProductType");
                                    String bundlesubtype =arry.getJSONObject(i).getString("ProductSubtype");
                                    String discount =arry.getJSONObject(i).getString("discount");
                                    BundleDatabaseHandler sdb = new BundleDatabaseHandler(getActivity());
                                    sdb.addContact(new BundleDBObject(1,bundleid,bundlename,price,discount,companyid,bundletype,bundlesubtype,""));


                                    //   Toast.makeText(getActivity(), discount, Toast.LENGTH_LONG).show();

                                }


                            }else{
                                Toast.makeText(getActivity(), success, Toast.LENGTH_LONG).show();

                            }

                            ReloadHistory();
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


                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);}




    public void  GetHistoryUpdate() {
        progressBar.setVisibility(View.VISIBLE);
        imageprogressBar.setVisibility(GONE);
        Constants.USERID = app_preferences.getString(UserID, "");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.CRON,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("our response string", "" + response.toString());
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.toString());
                            //  progressDialogMain.dismiss();
                            String success = jsonObject.getString("success");

                            if ("True".equals(success)) {

                                String balance = jsonObject.getString("balance");
                                String bonus= jsonObject.getString("bonus");
                                Constants.BalanceText = "N " + balance;
                                Constants.Balance = balance;
                                Constants.Bonus = bonus;
                                txtbalance.setText(Constants.Balance);
                                txtbonus.setText(Constants.Bonus);
                                if (("customer".equalsIgnoreCase(Constants.UserROLE))) {

                                    accountType.setText("Account Type: customer");
                                } else if (("agent".equalsIgnoreCase(Constants.UserROLE))) {
                                    accountType.setText("Account Type: Agent");
                                }else{
                                    accountType.setText("Account Type: Admin");
                                }

                                //  Toast.makeText(getActivity(), success, Toast.LENGTH_LONG).show();


                                JSONArray arry=jsonObject.getJSONArray("messagedata");

                                //public $Id,$UserId,$HandlingStaffId,$Amount,$TimeRequested,$Remarks,$TimeFunded,$ConfirmedById,$type,$Status;
                                for (int i=0;i<arry.length();i++) {

                                    String MID = arry.getJSONObject(i).getString("MessageID");
                                    String Mt = arry.getJSONObject(i).getString("Title");
                                    String Mb = arry.getJSONObject(i).getString("Body");
                                    String MTi = arry.getJSONObject(i).getString("TimeSent");
                                    int save = Integer.parseInt(arry.getJSONObject(i).getString("save"));

                                    if (save == 1) {
                                        //be saved
                                   MessagesDatabaseHandler sdb = new MessagesDatabaseHandler(getActivity());
                                   sdb.addContact(new MessagesDBObject(1, Integer.parseInt(MID), Mt, Mb, MTi, "0"));
                                    }


                                    showNotification(Mt,Mb) ;
                                    Constants.LastIID = MID;
                                    SharedPreferences.Editor editor = app_preferences.edit();
                                    editor.putString(LastID, MID);
                                    editor.commit(); // Very important





                                }




                                } else {
                                // Toast.makeText(getActivity(), success, Toast.LENGTH_LONG).show();

                            }
                            progressBar.setVisibility(GONE);
                            imageprogressBar.setVisibility(View.VISIBLE);
                            Millisec = 40000;
                         ReloadHistory();
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("response string", "" + error.toString());
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userid", "" + Constants.USERID);
                params.put("lastid", "" + Constants.LastIID);

                return params;
            }

        };
        if (getActivity() != null) {
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
        }
    }

    private void showNotification(String Title,String Message) {
/*
        // intent triggered, you can add other intent for other actions
        Intent i = new Intent(getActivity(), Messages.class);
        PendingIntent pIntent = PendingIntent.getActivity(getActivity(), 0, i, 0);

        //Notification sound
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // this is it, we'll build the notification!
        // in the addAction method, if you don't want any icon, just set the first param to 0
        Notification mNotification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {

            mNotification = new Notification.Builder(getActivity())

                    .setContentTitle(Title)
                    .setContentText(Message)
                    .setSmallIcon(R.drawable.icon)
                    .setContentIntent(pIntent)
                    .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                    .addAction(R.drawable.icon, "Goto App", pIntent)
                    .build();

        }

        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);

        // If you want to hide the notification after it was selected, do the code below
        // myNotification.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, mNotification);
        */

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getActivity(), "notify_002");
        Intent ii = new Intent(getActivity(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, ii, 0);


        //Notification sound
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }


        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(Message);
        bigText.setBigContentTitle(Title);
        bigText.setSummaryText(Message);

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.icon);
        mBuilder.setContentTitle(Title);
        mBuilder.setContentText(Message);
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL);
        mBuilder.setNumber(notofy);
        mBuilder.setStyle(bigText);

        mNotificationManager =
                (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);

// === Removed some obsoletes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "Your_channel_ids";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Segun Data",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }
        notofy++;
        mNotificationManager.notify(notofy, mBuilder.build());


        MessagesDatabaseHandler localsmsdb = new MessagesDatabaseHandler(getActivity());
        Constants.unread  = localsmsdb.getunread();

        viewmessages.setText(""+Constants.unread);

        if(Constants.unread  > 0) {
            linviewmessages.setVisibility(View.VISIBLE);
        }else{
            linviewmessages.setVisibility(GONE);
        }


    }
    @Override
    public void onResume() {
        super.onResume();
        MyApplication.activityResumed();
        //   Toast.makeText(getActivity(), "on resume", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        MyApplication.activityPaused();
        // Toast.makeText(getActivity(), "on paused", Toast.LENGTH_LONG).show();
    }


    public void clearApplicationData() {
        File cache = getActivity().getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));
                    Log.i("EEEEEERRRRRROOOOOOORRRR", "**************** File /data/data/APP_PACKAGE/" + s + " DELETED *******************");
                }
            }
        }
    }
    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) { e.printStackTrace();}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }


    }
}
