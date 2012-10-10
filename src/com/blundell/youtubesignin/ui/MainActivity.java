package com.blundell.youtubesignin.ui;

import com.blundell.youtubesignin.FromXML;
import com.blundell.youtubesignin.R;
import com.blundell.youtubesignin.domain.Tokens;
import com.blundell.youtubesignin.util.Log;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * Main Activity holding a sign into YouTube button
 * @author paul.blundell
 *
 */
public class MainActivity extends Activity {

    private static final int REQ_OAUTH = 123;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @FromXML
    public void onSignInClick(View button){
    	startAuthorisationForYouTube();
    }

	private void startAuthorisationForYouTube() {
		Intent intent = new Intent(this, OAuthActivity.class);
    	startActivityForResult(intent, REQ_OAUTH);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REQ_OAUTH){
			if(resultCode == RESULT_OK){
				dealWithResult(data);
			} else
			if(resultCode == RESULT_CANCELED){
				startRefusalActivity();
			}
		}
	}

	private void dealWithResult(Intent data) {
		Tokens tokens = (Tokens) data.getSerializableExtra(OAuthActivity.EXTRA_TOKENS);
		Toast.makeText(this, "Access Token retrieved. See your LogCat.", Toast.LENGTH_SHORT).show();
		logResult(tokens);
	}

	private void startRefusalActivity() {
		Intent intent = new Intent(this, RefusedAuthActivity.class);
		startActivity(intent);
	}

	/**
	 * Instead of logging the result you would probably save it to shared preferences.
	 * Then you can use your access token whenever you call the YouTube API
	 */
	private static void logResult(Tokens tokens) {
		Log.i("Got access token: "+ tokens.getAccessToken());
		Log.i("Got refresh token: "+ tokens.getRefreshToken());
		Log.i("Got token type: "+ tokens.getTokenType());
		Log.i("Got expires in: "+ tokens.getExpiresIn());
	}
}