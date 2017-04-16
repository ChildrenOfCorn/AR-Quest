package com.vuforia.samples.VuforiaSamples.app.ProductInfoDetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.vuforia.samples.ar.data.beans.ProductInfo;

/**
 * Date: 4/16/17
 * Time: 9:36 AM
 *
 * @author Andrey Smolyak
 */

public class ProductInfoWebFragment extends Fragment {

	private static final String PRODUCT_INFO_TAG = "product_info";

	public static ProductInfoWebFragment newInstance(final ProductInfo productInfo) {
		ProductInfoWebFragment fragment = new ProductInfoWebFragment();

		Bundle args = new Bundle();
		args.putSerializable(PRODUCT_INFO_TAG, productInfo);
		fragment.setArguments(args);

		return fragment;
	}

	private class ChromeClient extends WebChromeClient {

	}

	private class ViewClient extends WebViewClient {

	}

	@Nullable
	@Override
	public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
							 @Nullable final Bundle savedInstanceState) {

		WebView webView = new WebView(getContext());
		webView.setWebChromeClient(new ChromeClient());
		webView.setWebViewClient(new ViewClient());

		ProductInfo productInfo = (ProductInfo) getArguments().getSerializable(PRODUCT_INFO_TAG);

		String url = productInfo.getUrl();
		webView.loadUrl(url);

		return webView;
	}
}
