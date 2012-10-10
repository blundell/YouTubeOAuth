package com.blundell.youtubesignin.oauth;

import static com.blundell.youtubesignin.oauth.Constants.CALLBACK_URL;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Our webview client takes care of capturing any URL that is loaded,
 * it keeps a lookout for the redirect url and when this is loaded we
 * inform the listener (our ParamChecker) that either
 * "allow access" or "no thanks" has been clicked ( we don't know which yet)
 * @author paul.blundell
 *
 */
public class OAuthWebViewClient extends WebViewClient {

	private final OnOAuthCallbackListener oAuthCallbackListener;

	public OAuthWebViewClient(OnOAuthCallbackListener oAuthCallbackListener) {
		this.oAuthCallbackListener = oAuthCallbackListener;
	}

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		if(weHaveReceivedAnOAuthCallback(url)){
			// Because the oAuthCallback is our redirect url
			// and this url is not a real webpage
			// the webview shows 'page not found' for a split second,
			// to hide this, we hide the webview (we've finished with it anyway)
			view.setVisibility(View.GONE);
			String reply = retrieveParamaters(url);
			oAuthCallbackListener.onOAuthCallback(reply);
		}
		return false;
	}

	private static boolean weHaveReceivedAnOAuthCallback(String url) {
		return url.startsWith(CALLBACK_URL);
	}

	private static String retrieveParamaters(String url) {
		return url.replace(CALLBACK_URL, "");
	};
}