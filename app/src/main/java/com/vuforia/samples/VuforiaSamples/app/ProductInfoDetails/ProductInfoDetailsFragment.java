package com.vuforia.samples.VuforiaSamples.app.ProductInfoDetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vuforia.samples.VuforiaSamples.R;
import com.vuforia.samples.VuforiaSamples.app.ImageTargets.adapters.CommentRecyclerAdapter;
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

	ProductInfo productInfo;
	TextView productNameTextView;
	RecyclerView recyclerView;

	@Nullable
	@Override
	public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
							 @Nullable final Bundle savedInstanceState) {


		this.productInfo = (ProductInfo) getArguments().getSerializable(PRODUCT_INFO_TAG);
		View rootView = inflater.inflate(R.layout.product_details, container);
		productNameTextView = (TextView) rootView.findViewById(R.id.product_details_product_name);
		productNameTextView.setText(productInfo.getName());

		recyclerView = (RecyclerView) rootView.findViewById(R.id.product_details_recycler_view);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		CommentRecyclerAdapter adapter = new CommentRecyclerAdapter();
		adapter.setComments(productInfo.getComments());
		recyclerView.setAdapter(adapter);

		return rootView;
	}
}
