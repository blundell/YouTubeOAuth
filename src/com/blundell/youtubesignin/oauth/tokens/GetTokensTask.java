package com.blundell.youtubesignin.oauth.tokens;

import static com.blundell.youtubesignin.oauth.Constants.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.blundell.youtubesignin.domain.Tokens;
import com.blundell.youtubesignin.util.Log;
import com.blundell.youtubesignin.util.StreamConverter;

/**
 * This task exchanges the authorization code for an Access Token and a Refresh Token,
 * when complete it will Log these out to the console, in a development environment you would
 * save them to the shared preferences so you can use them for other calls to the YouTube API
 *
 * @author paul.blundell
 */
public class GetTokensTask implements Runnable {
	// The authcode you obtained when the user granted your app access to there YouTube account
	private final String authCode;
	private final TokenRetrievedListener retrievedListener;

	public GetTokensTask(String authCode, TokenRetrievedListener retrievedListener) {
		this.authCode = authCode;
		this.retrievedListener = retrievedListener;
	}

	@Override
	public void run() {
		try {
			HttpResponse response = requestYouTubeAccessTokens();
			Tokens tokens = parseYouTubeAccessTokens(response);

			retrievedListener.onTokensRetrieved(tokens);
		} catch (ClientProtocolException e) {
			Log.e("ClientProtocolException", e);
		} catch (IOException e) {
			Log.e("IOException", e);
		} catch (IllegalStateException e) {
			Log.e("IllegalStateException", e);
		} catch (JSONException e) {
			Log.e("JSONException", e);
		}
	}

	/**
	 * Fires off the request for an access token to the Google servers
	 * @return the response which should contain JSON holding the access token
	 */
	private HttpResponse requestYouTubeAccessTokens() throws IOException, ClientProtocolException {
		HttpPost post = createTokenRetrievalPost();
		DefaultHttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(post);
		return response;
	}

	/**
	 * To gain an access token we have to send google our auth code and client credential's,
	 * these are passed into this task found in your API console https://code.google.com/apis/console respectively
	 *
	 * @return returns the Post request that we can then execute
	 */
	private HttpPost createTokenRetrievalPost() throws UnsupportedEncodingException {
		HttpPost post = new HttpPost(TOKENS_URL);
		post.setHeader("content-type", "application/x-www-form-urlencoded");

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(4);
		nameValuePair.add(new BasicNameValuePair("code", authCode));
		nameValuePair.add(new BasicNameValuePair("client_id", CLIENT_ID));
		nameValuePair.add(new BasicNameValuePair("redirect_uri", CALLBACK_URL));
		nameValuePair.add(new BasicNameValuePair("grant_type", "authorization_code"));
		post.setEntity(new UrlEncodedFormEntity(nameValuePair));

		return post;
	}

	/**
	 * @param response The response from the YouTUbe post request we just made
	 * @return Our TokenParser so we can read the fields off it (just for logging)
	 */
	private static Tokens parseYouTubeAccessTokens(HttpResponse response) throws JSONException, IOException {
		JSONObject jsonObject = StreamConverter.convertStreamToJsonObject(response.getEntity().getContent());

		return new TokenParser(jsonObject).getTokens();
	}
}
