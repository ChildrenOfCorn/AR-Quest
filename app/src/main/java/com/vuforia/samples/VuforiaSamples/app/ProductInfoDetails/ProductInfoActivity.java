package com.vuforia.samples.VuforiaSamples.app.ProductInfoDetails;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.vuforia.samples.ar.data.beans.ProductInfo;

public class ProductInfoActivity extends Activity {

    private static final String PRODUCT_INFO_URL_EXTRA = "PRODUCT_INFO";

    public static void startActivity(Activity activity, ProductInfo productInfo) {

        Intent intent = new Intent(activity, ProductInfoActivity.class);
        intent.putExtra(PRODUCT_INFO_URL_EXTRA, productInfo.getUrl());
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WebView webView = new WebView(this);
        webView.setWebChromeClient(new ChromeClient());
        webView.setWebViewClient(new ViewClient());
        setContentView(webView);

        String url = getIntent().getStringExtra(PRODUCT_INFO_URL_EXTRA);
        webView.loadUrl(url);
    }

    private class ChromeClient extends WebChromeClient {


    }

    private class ViewClient extends WebViewClient {



    }
}
