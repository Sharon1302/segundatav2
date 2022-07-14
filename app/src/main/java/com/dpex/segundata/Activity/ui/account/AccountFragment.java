package com.dpex.segundata.Activity.ui.account;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dpex.segundata.Activity.ChangePassword;
import com.dpex.segundata.Activity.ChangePin;
import com.dpex.segundata.Activity.ResetActivity;
import com.dpex.segundata.Constant.Constants;
import com.dpex.segundata.R;


public class AccountFragment extends Fragment {
    Button update, changePassword,changePin,resetPin;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_account, container, false);

        update = root.findViewById(R.id.updateAccount);
        changePassword = root.findViewById(R.id.changePass);
        changePin =root.findViewById(R.id.changePin);
        resetPin = root.findViewById(R.id.resetPin);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), updateAccountActivity.class);
                startActivity(intent);
            }
        });
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChangePassword.class);
                startActivity(intent);
            }
        });
        changePin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(getActivity(), ChangePin.class);
                startActivity(intent);
            }
        });
        resetPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.resetType = "resetPin";
                Intent intent = new Intent(getActivity(), ResetActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}