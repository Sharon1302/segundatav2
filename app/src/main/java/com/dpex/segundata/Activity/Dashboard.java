package com.dpex.segundata.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.dpex.segundata.Constant.Constants;
import com.dpex.segundata.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class Dashboard extends AppCompatActivity {
    public static final String bankName = "bnameKey";
    public static final String accountName = "accnameKey";
    public static final String accountNo = "acnnoKey";
    public static final String UserName = "unameKey";
    public static final String Phone = "phoneKey";
    public static final String Name = "nameKey";
    public static final String Email = "emailKey";
    public static final String UserID = "userKey";
    public static final String ROLE = "roleKey";
    public static final String LastID = "lastidKey";
    public static final String First = "firstKey";
    public static final String Last = "lastKey";
    public static final String OPERATINGMODE= "opmodeKey";
    public static final String OPERATINGCODE= "opcodeKey";
    public static final String ORNumber= "ornumberKey";
    public static final String SENDOP= "sendupKey";
    public static final String SYNCDURATION= "syncdurationKey";
    SharedPreferences app_preferences ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_account, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


        app_preferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        Constants.USERNAME =  app_preferences.getString(UserName, "");
        Constants.UserEmail =  app_preferences.getString(Email, "");
        Constants.UserROLE =  app_preferences.getString(ROLE, "");
        Constants.UserNAME =  app_preferences.getString(Name, "");
        Constants.traderorigin =  app_preferences.getString(OPERATINGCODE, "");
        Constants.SYNCDURATION=  app_preferences.getString(SYNCDURATION, "");
        Constants.OPERATINGMODE =   app_preferences.getString(OPERATINGMODE, "");
        Constants.ORNUMBER  =  app_preferences.getString(ORNumber, "");
        Constants.FirstName  =  app_preferences.getString(First, "");
        Constants.LastName  =  app_preferences.getString(Last, "");
        Constants.PhoneNo  =  app_preferences.getString(Phone, "");
        Constants.AccountNo  =  app_preferences.getString(accountNo, "");
        Constants.BankName  =  app_preferences.getString(bankName, "");
        Constants.AccountName  =  app_preferences.getString(accountName, "");
        Constants.USERNAME  =  app_preferences.getString(UserName, "");
    }

}
