package com.blundell.youtubesignin.oauth;

/**
 * Callback to determine which option the user selected when asking for OAuth
 * @author paul.blundell
 *
 */
public interface OnOAuthListener {
	void onAuthorized(String authCode);
	void onRefused();
}