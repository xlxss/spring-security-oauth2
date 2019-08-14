package xiao.xss.study.demo.oauth2.app.common.auth.api;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.client.RestTemplate;
import xiao.xss.study.demo.oauth2.app.common.auth.token.TokenInfo;
import xiao.xss.study.demo.oauth2.app.common.auth.token.UserInfo;
import xiao.xss.study.demo.oauth2.app.common.constant.AuthProvider;
import xiao.xss.study.demo.oauth2.app.common.util.CommonUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

/**
 * 认证API
 *
 * @author xiaoliang
 * @since 2019-08-12 16:44
 */
public abstract class AuthService implements AuthAPI {
    @Autowired private RestTemplate rest;
    @Autowired private ClientRegistrationRepository clientRegistrationRepository;
    @Autowired private HttpServletRequest request;

    @Override
    public TokenInfo getAccessToken(String authCode) {
        AuthProvider provider = provider();
        logger().debug("获取token，授权码：{}，认证主体：{}({})", authCode, provider.name(), provider.getDesc());
        ClientRegistration client = clientRegistrationRepository.findByRegistrationId(provider.name());
        String tokenUrl = String.format(TOKEN_URL, client.getProviderDetails().getTokenUri(), client.getAuthorizationGrantType().getValue(),
                client.getClientId(), client.getClientSecret(), CommonUtil.expandRedirectUri(request, client, null),
                authCode, expandScope(client.getScopes()));

        HttpHeaders headers = new HttpHeaders();
        ParameterizedTypeReference<Map<String, String>> ref = new ParameterizedTypeReference<Map<String, String>>() {};
        ResponseEntity<Map<String, String>> res = rest.exchange(tokenUrl, HttpMethod.POST, new HttpEntity<>(headers), ref);
        System.out.println(res.getBody());
        Map<String, String> body = res.getBody();
        TokenInfo token = new TokenInfo();
        token.setProvider(provider.name());
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
    public TokenInfo refreshToken(String refreshToken) {
        return null;
    }

    @Override
    public UserInfo getUserInfo(String accessToken) {
        return null;
    }

    private String expandScope(Set<String> scopes) {
        StringBuilder sb = new StringBuilder();
        if(scopes == null || scopes.size() == 0) {
            return "";
        } else {
            scopes.forEach(s -> sb.append(s).append(","));
            sb.delete(sb.length() - 1, sb.length());
        }
        return sb.toString();
    }

    @Override
    public abstract AuthProvider provider();

    public abstract Logger logger();
}
