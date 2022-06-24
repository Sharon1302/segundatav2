package com.dpex.segundata.Activity.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dpex.segundata.Activity.Dashboard;
import com.dpex.segundata.Constant.Constants;
import com.dpex.segundata.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;



public class NotificationsFragment extends Fragment {
    EditText amount;
    TextView i,u ;
    private ProgressBar spinner2;
    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
       
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
       //         textView.setText(s);
            }
        });

        amount = root.findViewById(R.id.amount);
        u = root.findViewById(R.id.upper);
        spinner2=(ProgressBar) root.findViewById(R.id.progressBar);
        spinner2.setVisibility(View.GONE);


        final Button fund = root.findViewById(R.id.fund);

        fund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constants.CardFundAmount = amount.getText().toString();
                if ("".equalsIgnoreCase(amount.getText().toString())) {
                    Toast.makeText(getActivity(), "Amount cannot be empty", Toast.LENGTH_LONG).show();
                } else {
                    spinner2.setVisibility(View.VISIBLE);
                    WithdrawBonus() ;

                }
            }
        });


        
        
        return root;
    }



    public void WithdrawBonus()
    {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.WITHDRAWBONUS,
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


                                Intent intent = new Intent(getActivity(), Dashboard.class);
                                startActivity(intent);



                            }else{
                                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

                            }
                            spinner2.setVisibility(View.GONE);

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
                params.put("amount",""+Constants.CardFundAmount);

                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);}



    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
