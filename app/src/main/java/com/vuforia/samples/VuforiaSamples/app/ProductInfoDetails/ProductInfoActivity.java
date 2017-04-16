package com.vuforia.samples.VuforiaSamples.app.ProductInfoDetails;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.vuforia.samples.VuforiaSamples.R;
import com.vuforia.samples.VuforiaSamples.app.ProductInfoDetails.adapters.ProductInfoPagerAdapter;
import com.vuforia.samples.ar.data.beans.ProductInfo;

public class ProductInfoActivity extends AppCompatActivity {

    private static final String PRODUCT_INFO_URL_EXTRA = "PRODUCT_INFO";

    public static void startActivity(Activity activity, ProductInfo productInfo) {

        Intent intent = new Intent(activity, ProductInfoActivity.class);
        intent.putExtra(PRODUCT_INFO_URL_EXTRA, productInfo);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_info);

        ProductInfo productInfo = (ProductInfo) getIntent().getSerializableExtra(PRODUCT_INFO_URL_EXTRA);
        ViewPager viewPager = (ViewPager) findViewById(R.id.product_info_pager);
        PagerAdapter adapter = new ProductInfoPagerAdapter(getSupportFragmentManager(), productInfo);
        viewPager.setAdapter(adapter);
    }
}
