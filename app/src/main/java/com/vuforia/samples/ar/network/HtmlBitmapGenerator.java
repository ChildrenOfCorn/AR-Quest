package com.vuforia.samples.ar.network;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.os.Handler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.vuforia.samples.App;
import com.vuforia.samples.SampleApplication.utils.Texture;
import com.vuforia.samples.ar.data.info.InfoTextureBuilder;
import com.vuforia.samples.ar.data.models.ProductInfo;

/**
 * Date: 15.04.17 Time: 14:10
 *
 * @author Andrey Smolyak
 */

public class HtmlBitmapGenerator implements InfoTextureBuilder {

	private static final int container_size = 500;

	private WebView webView;
	private OnTextureBuildListener listener;

	public HtmlBitmapGenerator() {
		this.webView = new WebView(App.getAppContext());
	}

	String productInfoToHtml(final ProductInfo productInfo) {
		StringBuilder builder = new StringBuilder(1024);
		builder.append("<html><body><h2>")
			.append(productInfo.getName())
			.append("</h2><br>")
			.append(productInfo.getBriefDesc())
			.append("</body></html>");
		return builder.toString();
	}

	public void generate(final int containerWidth, final int containerHeight, final String html) {
		final SimpleWebClient webClient = new SimpleWebClient();
		final Bitmap bitmap = Bitmap.createBitmap(containerWidth, containerHeight, Bitmap.Config.ARGB_8888);


		new Handler(App.getAppContext().getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
				webView.setWebViewClient(webClient);
				webView.layout(0, 0, containerWidth, containerHeight);
				webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);

				webView.setPictureListener(new WebView.PictureListener() {
					@Override
					public void onNewPicture(WebView view, Picture picture) {
						if (webClient.isPageLoaded()) {
							final Canvas c = new Canvas(bitmap);
							view.draw(c);
							webView.setPictureListener(null);
							if (listener != null) {
								Texture texture = Texture.loadTextureFromARGBBitmap(bitmap);//
								listener.onTextureReady(texture);
							}
						}
					}
				});
			}
		});



	}

	@Override
	public void getTextureBitmapFromInfo(final ProductInfo productInfo) {
		generate(container_size, container_size, productInfoToHtml(productInfo));
	}

	@Override
	public void setTextureBuildListener(final OnTextureBuildListener onTextureBuildListener) {
		this.listener = onTextureBuildListener;
	}

class SimpleWebClient extends WebViewClient {

	boolean pageLoaded = false;

	boolean isPageLoaded() {
		return pageLoaded;
	}

	@Override
	public void onPageFinished(final WebView view, final String url) {
		super.onPageFinished(view, url);
		pageLoaded = true;
	}
}
}
