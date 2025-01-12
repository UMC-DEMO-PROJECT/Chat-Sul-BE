package com.chatsul.oauth.userInfo;

public interface OAuth2UserInfo {
    String getProviderId();
    String getProvider();
    String getName();
}
