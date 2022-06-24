package com.dpex.segundata.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dpex.segundata.Constant.Constants;
import com.dpex.segundata.R;

import androidx.appcompat.app.AppCompatActivity;


public class WebViewActivity extends AppCompatActivity {
//https://github.com/vrdriver/Android-App-WebView/tree/master/src/main/res/layout

    private WebView webView;
    ProgressDialog mProgressDialog;
    ProgressDialog progressDialog;



    private boolean isSSLErrorDialogShown = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Loading ");
        mProgressDialog.show();
        webView = (WebView) findViewById(R.id.webview);

        WebView webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setVerticalScrollBarEnabled(true);
        webView.setHorizontalScrollBarEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.loadUrl(Constants.WEBVIEW);

        webView.setWebViewClient(new HelloWebViewClient());


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

    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(!url.contains("example.com")){
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                return true;
            }
            view.setVisibility(View.GONE);
            mProgressDialog.show();
            mProgressDialog.setMessage("Loading...");
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            mProgressDialog.dismiss();
            view.setVisibility(View.VISIBLE);
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
            if (!isSSLErrorDialogShown) {
                AlertDialog.Builder builder = new AlertDialog.Builder(WebViewActivity.this);
                AlertDialog alertDialog = builder.create();
                String message = "SSL Certificate error.";
                switch (error.getPrimaryError()) {
                    case SslError.SSL_UNTRUSTED:
                        message = "The certificate authority is not trusted.";
                        break;
                    case SslError.SSL_EXPIRED:
                        message = "The certificate has expired.";
                        break;
                    case SslError.SSL_IDMISMATCH:
                        message = "The certificate Hostname mismatch.";
                        break;
                    case SslError.SSL_NOTYETVALID:
                        message = "The certificate is not yet valid.";
                        break;
                }

                message += " Do you want to continue anyway?";
                alertDialog.setTitle("SSL Certificate Error");
                alertDialog.setMessage(message);
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Ignore SSL certificate errors
                        isSSLErrorDialogShown = true;
                        handler.proceed();
                    }
                });

                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        handler.cancel();
                    }
                });
                alertDialog.show();
            }else{
                handler.proceed();
            }
        }
    }


}
