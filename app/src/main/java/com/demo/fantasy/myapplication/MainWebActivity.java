package com.demo.fantasy.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.HashMap;

public class MainWebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_web);

        WebView webview = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webview.getSettings();
        //設置WebView支持JavaScript
        webSettings.setJavaScriptEnabled(true);
        setContentView(webview);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                String android_id = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                Log.d("ola_log", "android_id:" + android_id);
                HashMap<String, String> header = new HashMap<String, String>();
                header.put("device_id", android_id);
                //view.loadUrl("javascript:alert('" + android_id + "')");
                view.loadUrl("javascript:void(0);", header);
                super.onPageFinished(view, url);
            }
        });
        String android_id = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("device_id", android_id);
        webview.loadUrl("http://192.168.2.61:8080/", header);
        //在js中調用本地java方法
        webview.addJavascriptInterface(new JsInterface(webview), "AndroidWebView");
        //添加客戶端支持
        webview.setWebChromeClient(new WebChromeClient());
    }

    private class JsInterface {
        private Context mContext;

        private WebView webview;

        public JsInterface(WebView webview) {
            this.webview = webview;
        }

        @JavascriptInterface
        public void onError(String error) {
            throw new Error(error);
        }

        //在js中調用window.AndroidWebView.showInfoFromJs(name)，便會觸發此方法。
        @JavascriptInterface
        public void showInfoFromJs(String name) {
            Log.d("JsInterface", "showInfoFromJs");
            Toast.makeText(webview.getContext(), name, Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public void openAndroidDialog() {
//            AlertDialog.Builder myDialog = new AlertDialog.Builder(MainWebActivity.this);
//            myDialog.setTitle("DANGER!");
//            myDialog.setMessage("You can do what you want!");
//            myDialog.setPositiveButton("ON", null);
//            myDialog.show();
            Log.d("JsInterface", "testcall");
            //webview.loadUrl("javascript:testcall();");
            webview.post(new Runnable() {
                @Override
                public void run() {
                    webview.loadUrl("javascript:testcall();");
                }
            });
        }
    }
}
