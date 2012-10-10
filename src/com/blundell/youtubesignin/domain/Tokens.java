package com.blundell.youtubesignin.domain;

import java.io.Serializable;

/**
 * Wrapper for holding the access and other tokens
 * @author paul.blundell
 *
 */
public class Tokens implements Serializable {
	private final String accessToken;
	private final String refreshToken;
	private final int expiresIn;
	private final String tokenType;

	public Tokens(String accessToken, String refreshToken, int expiresIn, String tokenType) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.expiresIn = expiresIn;
		this.tokenType = tokenType;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public String getTokenType() {
		return tokenType;
	};
}