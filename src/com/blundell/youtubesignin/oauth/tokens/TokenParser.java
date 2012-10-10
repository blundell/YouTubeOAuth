package com.blundell.youtubesignin.oauth.tokens;

import org.json.JSONException;
import org.json.JSONObject;

import com.blundell.youtubesignin.domain.Tokens;
import com.blundell.youtubesignin.util.Log;

/**
 * Parses the JSON recieved from Google when we are swapping our auth code for access
 *
 * Example:
 * {
 *  "access_token" : "ya29.AHES6ZTtm7SuokEB-RGtbBty9IIlNiP9-eNMMQKtXdMP3sfjL1Fc",
 *  "token_type" : "Bearer",
 *  "expires_in" : 3600,
 *  "refresh_token" : "1/HKSmLFXzqP0leUihZp2xUt3-5wkU7Gmu2Os_eBnzw74"
 * }
 * @author paul.blundell
 *
 */
public class TokenParser {

	private final JSONObject jsonObject;
	private Tokens tokens;

	public TokenParser(JSONObject jsonObject) throws JSONException {
		this.jsonObject = jsonObject;
		parse();
	}

	private void parse() throws JSONException{
		Log.d(jsonObject.toString());
		String accessToken = jsonObject.getString("access_token");
		String tokenType = jsonObject.getString("token_type");
		int expiresIn = jsonObject.getInt("expires_in");
		String refreshToken = jsonObject.getString("refresh_token");

		tokens = new Tokens(accessToken, refreshToken, expiresIn, tokenType);
	}

	public Tokens getTokens() {
		return tokens;
	}
}