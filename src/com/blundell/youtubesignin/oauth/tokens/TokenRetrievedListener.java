package com.blundell.youtubesignin.oauth.tokens;

import com.blundell.youtubesignin.domain.Tokens;

/**
 * @author paul.blundell
 *
 */
public interface TokenRetrievedListener {
	void onTokensRetrieved(Tokens tokens);
}
