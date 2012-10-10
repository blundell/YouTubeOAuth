package com.blundell.youtubesignin.ui;

import static com.blundell.youtubesignin.oauth.Constants.OAUTH_URL;

import com.blundell.youtubesignin.R;
import com.blundell.youtubesignin.domain.Tokens;
import com.blundell.youtubesignin.oauth.OAuthWebViewClient;
import com.blundell.youtubesignin.oauth.OnOAuthListener;
import com.blundell.youtubesignin.oauth.ParamChecker;
import com.blundell.youtubesignin.oauth.tokens.GetTokensTask;
import com.blundell.youtubesignin.oauth.tokens.TokenRetrievedListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * Activity incorporates signing into YouTube and Retrieving the access_token for YouTube API access in the future
 * @author paul.blundell
 *
 */
public class OAuthActivity extends Activity implements OnOAuthListener, TokenRetrievedListener {

	public static final String EXTRA_TOKENS = "com.blundell.youtubesignin.ui.OAuthActivity.EXTRA_TOKENS";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_oauth);
		setResult(RESULT_CANCELED);

		WebView webview = (WebView) findViewById(R.id.webview);
		webview.setWebViewClient(new OAuthWebViewClient(new ParamChecker(this)));
		webview.loadUrl(OAUTH_URL);
		Toast.makeText(this, "Loading .. just wait", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onAuthorized(String authCode) {
		dealWithAccessGranted(authCode);
	}

	@Override
	public void onRefused() {
		dealWithRefusal();
	}

	private void dealWithAccessGranted(String authCode) {
		// You'd probably want to call this in a service http://blog.blundell-apps.com/tut-networking-off-the-ui-thread-solid-architecture/
		new Thread(new GetTokensTask(authCode, this)).start();
	}

	private void dealWithRefusal() {
		setResult(RESULT_CANCELED);
		finish();
	}

	@Override
	public void onTokensRetrieved(Tokens tokens) {
		Intent intent = createSendableBundle(tokens);
		setResult(RESULT_OK, intent);
		finish();
	}

	private static Intent createSendableBundle(Tokens tokens) {
		Intent intent = new Intent();
		intent.putExtra(EXTRA_TOKENS, tokens);
		return intent;
	}
}