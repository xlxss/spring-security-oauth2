package xiao.xss.study.demo.oauth2.app.auth;

import xiao.xss.study.demo.oauth2.app.dto.AccessToken;

/**
 * 认证服务
 *
 * @author xiaoliang
 * @since 2019-07-24 13:57
 */
public interface AuthService {
    public AppEnum me();
    public AccessToken getAccessToken(String authCode);
}
