package com.vuforia.samples.VuforiaSamples.app.ProductInfoDetails;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.vuforia.samples.ar.data.beans.ProductInfo;

/**
 * Date: 4/16/17
 * Time: 9:36 AM
 *
 * @author Andrey Smolyak
 */

public class ProductInfoDetailsFragment extends Fragment {

	private static final String PRODUCT_INFO_TAG = "product_info";

	public static ProductInfoDetailsFragment newInstance(final ProductInfo productInfo) {
		ProductInfoDetailsFragment fragment = new ProductInfoDetailsFragment();

		Bundle args = new Bundle();
		args.putSerializable(PRODUCT_INFO_TAG, productInfo);
		fragment.setArguments(args);

		return fragment;
	}
}
