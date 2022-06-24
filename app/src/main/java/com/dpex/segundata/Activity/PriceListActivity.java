package com.dpex.segundata.Activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dpex.segundata.Constant.Constants;
import com.dpex.segundata.R;

import androidx.appcompat.app.AppCompatActivity;


public class PriceListActivity extends AppCompatActivity {

    private WebView wv1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pricelist);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        wv1=(WebView)findViewById(R.id.webview);
        wv1.setWebViewClient(new MyBrowser());
        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv1.loadUrl(Constants.PRICELIST);
        /*
          <com.levitnudi.legacytableview.LegacyTableView
        android:id="@+id/legacy_table_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
        //set table title labels
        LegacyTableView.insertLegacyTitle("", "RESELLER", "CUSTOMER");
        //set table contents as string arrays
        LegacyTableView.insertLegacyContent(
                "MTN VTU", "4% discount", "no discount",
                "GLO E-Top", "5% discount", "no discount",
                "Airtel ERC", "2% discount", "no discount",
                "9Mobile E_Top", "5% discount", "no discount",
                "MTN Data", "1GB = N420", "1GB = N450",
                "", "2GB = N840", "2GB = N900",
                "", "3GB = N1260", "3GB = N1250",
                "", "5GB = N2100", "5GB = N2200",
                "GLO Data", "1.6GB = N850", "1.6GB = N900",
                "", "3.6GB = N1700", "3.6GB = N1800",
                "Airtel Data", "1.5GB = N830", "1.5GB = N950",
                "", "4.5GB = N1650", "4.5GB = N1900"

        );

        LegacyTableView legacyTableView = (LegacyTableView)findViewById(R.id.legacy_table_view);
        legacyTableView.setTitle(LegacyTableView.readLegacyTitle());
        legacyTableView.setContent(LegacyTableView.readLegacyContent());

        //depending on the phone screen size default table scale is 100
        //you can change it using this method
        //legacyTableView.setInitialScale(100);//default initialScale is zero (0)

        //if you want a smaller table, change the padding setting
        legacyTableView.setTablePadding(12);

        //to enable users to zoom in and out:
        legacyTableView.setZoomEnabled(true);
        legacyTableView.setShowZoomControls(true);

        //remember to build your table as the last step
        legacyTableView.build();

         */
       // final EditText emailEditText = findViewById(R.id.email);
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
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
