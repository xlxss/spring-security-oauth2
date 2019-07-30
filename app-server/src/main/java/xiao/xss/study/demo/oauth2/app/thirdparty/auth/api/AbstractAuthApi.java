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
import xiao.xss.study.demo.oauth2.app.auth.token.JwtTokenUtil;
import xiao.xss.study.demo.oauth2.app.dto.*;
import xiao.xss.study.demo.oauth2.app.entity.SysUser;
import xiao.xss.study.demo.oauth2.app.thirdparty.auth.AuthProvider;
import xiao.xss.study.demo.oauth2.app.thirdparty.auth.AuthService;
import xiao.xss.study.demo.oauth2.app.thirdparty.auth.config.AuthConfig;
import xiao.xss.study.demo.oauth2.app.thirdparty.auth.config.AuthServerConfig;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Map;

/**
 * 认证API
 *
 * @author xiaoliang
 * @since 2019-07-25 8:55
 */
public abstract class AbstractAuthApi implements AuthService {
    @Autowired private RestTemplate rest;
    @Autowired private AuthServerConfig authServerConfig;
    @Autowired private JwtTokenUtil jwtTokenUtil;

    @Override
    public TokenAndUser getAccessToken(String authCode) {
        logger().debug("获取令牌，授权码: {}，认证主体: {}", authCode, provider());
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
        TokenInfo token = new TokenInfo();
        token.setProvider(provider().getName());
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
        SysUser user = new SysUser();
        AuthUser authUser = new AuthUser(user, new ArrayList<>());
        return jwtTokenUtil.createToken(authUser, token);
    }

    @Override
    public TokenInfo refreshToken(String refreshToken) {
        logger().info("刷新令牌，刷新令牌: {}, 认证主体: {}", refreshToken, provider());
        return null;
    }

    @Override
    public String authorizeUrl() {
        AuthConfig config = getConfig();
        Assert.notNull(config, "认证服务配置缺失");
        return String.format(AUTHORIZE_URL, config.getAuthorizeUri(), config.getClientId(), config.getRedirectUri());
    }

    @Override
    public boolean checkToken(String accessToken) {
        return true;
    }

    @Override
    public abstract AuthProvider provider();

    public abstract Logger logger();

    private AuthConfig getConfig() {
        return authServerConfig.getConfig(provider());
    }

    @PostConstruct
    public void done() {
        System.out.println("done......" + provider());
    }
}
