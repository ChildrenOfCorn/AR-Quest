package com.vuforia.samples.VuforiaSamples.app.ProductInfoDetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.vuforia.samples.VuforiaSamples.R;
import com.vuforia.samples.VuforiaSamples.app.ImageTargets.adapters.CommentRecyclerAdapter;
import com.vuforia.samples.ar.data.beans.Comment;
import com.vuforia.samples.ar.data.beans.ProductInfo;
import com.vuforia.samples.ar.data.beans.UserInfo;
import com.vuforia.samples.ar.di.DiContainer;
import com.vuforia.samples.ar.repository.AuthTokenRepository;
import com.vuforia.samples.ar.repository.SimpleCallback;
import com.vuforia.samples.ar.repository.info.ProductInfoRepository;

import javax.inject.Inject;

/**
 * Date: 4/16/17
 * Time: 9:36 AM
 *
 * @author Andrey Smolyak
 */

public class ProductInfoDetailsFragment extends Fragment implements SimpleCallback<Float> {

	private static final String PRODUCT_INFO_TAG = "product_info";
	ProductInfo productInfo;
	TextView productNameTextView;
	RecyclerView recyclerView;
	Button postCommentButton;
	EditText commentEditText;
	RatingBar commonRatingBar;
	RatingBar commentRatingBar;
	boolean postInProgress = false;
	CommentRecyclerAdapter commentsAdapter;

	@Inject
	ProductInfoRepository productInfoRepository;

	@Inject
	AuthTokenRepository authTokenRepository;

	public static ProductInfoDetailsFragment newInstance(final ProductInfo productInfo) {
		ProductInfoDetailsFragment fragment = new ProductInfoDetailsFragment();

		Bundle args = new Bundle();
		args.putSerializable(PRODUCT_INFO_TAG, productInfo);
		fragment.setArguments(args);

		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
							 @Nullable final Bundle savedInstanceState) {

		DiContainer.getAppComponent().inject(this);

		this.productInfo = (ProductInfo) getArguments().getSerializable(PRODUCT_INFO_TAG);
		View rootView = inflater.inflate(R.layout.product_details, null);
		productNameTextView = (TextView) rootView.findViewById(R.id.product_details_product_name);
		productNameTextView.setText(productInfo.getName());
		postCommentButton = (Button) rootView.findViewById(R.id.product_details_addCommentButton);
		commentEditText = (EditText) rootView.findViewById(R.id.product_details_addCommentEditText);
		commonRatingBar = (RatingBar) rootView.findViewById(R.id.product_details_ratingBar);
		commentRatingBar = (RatingBar) rootView.findViewById(R.id.product_details_addCommentRating);

		recyclerView = (RecyclerView) rootView.findViewById(R.id.product_details_recycler_view);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		commentsAdapter = new CommentRecyclerAdapter();
		commentsAdapter.setComments(productInfo.getComments());
		recyclerView.setAdapter(commentsAdapter);

		commonRatingBar.setRating(productInfo.getRating());

		postCommentButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				if (commentEditText.getText().length() > 0) {
					postInProgress = true;
					productInfoRepository.postComment(productInfo.getId(),
													  commentEditText.getText().toString(),
													  commentRatingBar.getRating(),
													  ProductInfoDetailsFragment.this);
					commentEditText.setEnabled(false);
					commentRatingBar.setEnabled(false);

				}
			}
		});

		return rootView;
	}

	@Override
	public void onSuccess(final Float rating) {
		postInProgress = false;
		UserInfo userInfo = new UserInfo(0, authTokenRepository.getLogin());
		Comment comment = new Comment(commentEditText.getText().toString(), userInfo, commentRatingBar.getRating());
		commentsAdapter.addComment(comment);

		commentEditText.setText("");
		commentRatingBar.setRating(0);

		commentEditText.setEnabled(true);
		commentRatingBar.setEnabled(true);
		commonRatingBar.setRating(rating);

	}

	@Override
	public void onFail(final Throwable error) {
		postInProgress = false;
		commentEditText.setEnabled(true);
	}
}
