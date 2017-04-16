package com.vuforia.samples.VuforiaSamples.app.ProductInfoDetails.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vuforia.samples.VuforiaSamples.app.ProductInfoDetails.ProductInfoDetailsFragment;
import com.vuforia.samples.VuforiaSamples.app.ProductInfoDetails.ProductInfoWebFragment;
import com.vuforia.samples.ar.data.beans.ProductInfo;

/**
 * Created by grishberg on 16.04.17.
 */

public class ProductInfoPagerAdapter extends FragmentPagerAdapter {

    private static final int PAGES_COUNT = 2;
    final ProductInfo productInfo;

    public ProductInfoPagerAdapter(final FragmentManager fm, ProductInfo productInfo) {
        super(fm);
        this.productInfo = productInfo;
    }

    @Override
    public Fragment getItem(int position) {
        if (position % 2 == 0) {
            return ProductInfoDetailsFragment.newInstance(productInfo);
        }
        return ProductInfoWebFragment.newInstance(productInfo);
    }

    @Override
    public int getCount() {
        return PAGES_COUNT;
    }
}
