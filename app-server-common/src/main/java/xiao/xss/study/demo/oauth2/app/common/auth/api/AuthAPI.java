package xiao.xss.study.demo.oauth2.app.common.auth.api;

import xiao.xss.study.demo.oauth2.app.common.auth.token.TokenInfo;
import xiao.xss.study.demo.oauth2.app.common.auth.token.UserInfo;
import xiao.xss.study.demo.oauth2.app.common.constant.AuthProvider;

/**
 * 认证服务
 *
 * @author xiaoliang
 * @since 2019-07-24 13:57
 */
interface AuthAPI {
    String AUTHORIZE_URL = "%s?client_id=%s&response_type=code&redirect_uri=%s";
    String TOKEN_URL = "%s?grant_type=%s&client_id=%s&client_secret=%s&redirect_uri=%s&code=%s&scope=%s";
    String REFRESH_TOKEN_URL = "%s?grant_type=refresh_token&refresh_token=%s&client_id=%s&client_secret=%s";

    AuthProvider provider();
    TokenInfo getAccessToken(String authCode);
    TokenInfo refreshToken(String refreshToken);
    UserInfo getUserInfo(String accessToken);
//    TokenAndUser getAccessToken(String authCode);
//    TokenInfo refreshToken(String refreshToken);
//    String authorizeUrl();
//    boolean checkToken(String accessToken);
}
