package com.example.webviewclient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.net.URI;
import java.net.URL;

public class MainActivity extends Activity {
    public WebView webView;
    private Context context;
    private boolean bCmdProgress = false;
    private ProgressBar progressBar;
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
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            super.onPageFinished(view, url);
//            try{
//
//            }catch(){
//
//            }
//            progressBar.setVisibility(View.GONE);
//        }
    }
}