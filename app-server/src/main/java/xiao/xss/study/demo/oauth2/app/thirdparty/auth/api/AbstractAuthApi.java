package xiao.xss.study.demo.oauth2.app.thirdparty.auth.api;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import xiao.xss.study.demo.oauth2.app.thirdparty.auth.AuthProvider;
import xiao.xss.study.demo.oauth2.app.thirdparty.auth.AuthService;
import xiao.xss.study.demo.oauth2.app.thirdparty.auth.config.AuthConfig;
import xiao.xss.study.demo.oauth2.app.thirdparty.auth.config.AuthServerConfig;
import xiao.xss.study.demo.oauth2.app.dto.AccessToken;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * 认证API
 *
 * @author xiaoliang
 * @since 2019-07-25 8:55
 */
public abstract class AbstractAuthApi implements AuthService {
    @Autowired RestTemplate rest;
    @Autowired AuthServerConfig authServerConfig;

    @Override
    public AccessToken getAccessToken(String authCode) {
        logger().debug("获取令牌，授权码: {}，认证主体: {}", authCode, me());
        AuthConfig config = getConfig();
        Assert.notNull(config, "认证服务配置缺失");
        String tokenUrl = String.format(TOKEN_URL, config.getAccessTokenUri(), config.getGrantType(),
                config.getClientId(), config.getClientSecret(), config.getRedirectUri(), authCode,
                StringUtils.isEmpty(config.getScope()) ? "" : config.getScope());

        HttpHeaders headers = new HttpHeaders();
        ParameterizedTypeReference<Map<String, String>> ref = new ParameterizedTypeReference<Map<String, String>>() {};
        ResponseEntity<Map<String, String>> res = rest.exchange(tokenUrl, HttpMethod.POST, new HttpEntity<>(headers), ref);
        System.out.println(res.getBody());
        Map<String, String> body = res.getBody();
        AccessToken token = new AccessToken();
        token.setAppName(me().getName());
        if(body != null) {
            token.setTokenType(body.get("token_type"));
            token.setAccessToken(body.get("access_token"));
            token.setExpiresIn(Integer.parseInt(body.get("expires_in")));
            token.setRefreshToken(body.get("refresh_token"));
            token.setScope(body.get("scope"));
            token.setTokenTime(System.currentTimeMillis());
        } else {
            throw new IllegalStateException("无法获取token");
        }
        return token;
    }

    @Override
    public AccessToken refreshToken(String refreshToken) {
        logger().info("刷新令牌，刷新令牌: {}, 认证主体: {}", refreshToken, me());
        return null;
    }

    @Override
    public String authorizeUrl() {
        AuthConfig config = getConfig();
        Assert.notNull(config, "认证服务配置缺失");
        return String.format(AUTHORIZE_URL, config.getAuthorizeUri(), config.getClientId(), config.getRedirectUri());
    }

    @Override
    public abstract AuthProvider me();

    public abstract Logger logger();

    private AuthConfig getConfig() {
        return authServerConfig.getConfig(me());
    }

    @PostConstruct
    public void done() {
        System.out.println("done......" + me());
    }
}
