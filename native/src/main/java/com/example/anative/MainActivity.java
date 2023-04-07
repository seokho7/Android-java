package com.example.anative;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends Activity {
    public WebView webView;
    private Context context;
    private boolean bCmdProgress = false;
    private ProgressBar progressBar;
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        webView = (WebView) findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressBar);
        WebSettings ws = webView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.setNetworkAvailable(true);
        webView.setWebChromeClient(new ChromeClient(this));
        webView.setWebViewClient(new MyWebViewClient(this));
//        String url = "https://www.naver.com";
        String url = "file:///android_asset/index.html";
        webView.loadUrl(url);
    }
    private class ChromeClient extends WebChromeClient {
        public Context context;
        public ChromeClient (Context cxt) { context = cxt; }
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            new AlertDialog.Builder(context)
                    .setTitle("확인")
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    result.confirm();
                                }
                            })
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    result.cancel();
                                }
                            })
                    .setCancelable(false).show();
            return true;
        }
    }
    private class MyWebViewClient extends WebViewClient {
        private Context context;

        public MyWebViewClient(Context ctx){
            context = ctx;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            Uri uri = Uri.parse(view.getUrl());
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

            progressBar.setVisibility(View.VISIBLE);
        }

        //onPageFinished 작업 해야함 스타트 밖에 없는 상황
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            try{
                Thread.sleep(3000);
            }catch(Exception ex){}
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
            new AlertDialog.Builder(context)
                    .setTitle("확인")
                    .setMessage("Native Error" + errorResponse.getData().toString())
                    .setCancelable(false)
                    .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .show();
        }
    }

    private class NativeBridge {
        Activity mActivity;

        public NativeBridge(Activity activity){
            mActivity = activity;
        }
        @JavascriptInterface
        public void callPhone(final String strPhoneNumber){
            Log.i("callPhone", "START" + strPhoneNumber);
            if(bCmdProgress) return;
            bCmdProgress = true;
            try{
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent dial = new Intent(Intent.ACTION_DIAL,Uri.parse("tel : " + strPhoneNumber));
                        startActivity(dial);
                    }
                });
            }catch(Exception ex){
                Log.e("Phone Error" , ex.toString());
            } finally {
                bCmdProgress = false;
            }
            Log.i("PHONE", "END");
        }

        @JavascriptInterface
        public void callSms(final String strPhoneNumber, final String strSMS){
            Log.i("callPhone", "START" + strPhoneNumber);
            if(ContextCompat.checkSelfPermission(context , Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(mActivity, new String[] {Manifest.permission.SEND_SMS} , 1);
            }

        }
    }
}