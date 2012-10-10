package com.blundell.youtubesignin.oauth;

/**
 *
 * @author paul.blundell
 *
 */
public class Constants {

	// Client ID from https://code.google.com/apis/console API Access
	public static final String CLIENT_ID = "448370772679.apps.googleusercontent.com";
	// Callback URL from https://code.google.com/apis/console API Access
	public static final String CALLBACK_URL = "http://localhost";

	public static final String OAUTH_URL =
										"https://accounts.google.com/o/oauth2/auth?" +
										"client_id=" + CLIENT_ID + "&" +
										"redirect_uri=" + CALLBACK_URL + "&" +
										"scope=https://gdata.youtube.com&" +
										"response_type=code&" +
										"access_type=offline";

	public static final String AUTH_CODE_PARAM = "?code=";
	// This is the url used to exchange your auth code for an access token
	public static final String TOKENS_URL = "https://accounts.google.com/o/oauth2/token";

}
