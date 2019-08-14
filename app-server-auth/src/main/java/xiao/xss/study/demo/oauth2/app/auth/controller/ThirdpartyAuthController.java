package xiao.xss.study.demo.oauth2.app.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xiao.xss.study.demo.oauth2.app.common.InvokeResult;
import xiao.xss.study.demo.oauth2.app.common.auth.api.AuthService;
import xiao.xss.study.demo.oauth2.app.common.auth.api.AuthFactory;
import xiao.xss.study.demo.oauth2.app.common.auth.token.TokenInfo;
import xiao.xss.study.demo.oauth2.app.common.auth.token.UserInfo;
import xiao.xss.study.demo.oauth2.app.common.constant.AuthProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 第三方认证登录
 *
 * @author xiaoliang
 * @since 2019-08-13 11:38
 */
@RestController
public class ThirdpartyAuthController {
    @Autowired private ClientRegistrationRepository clientRegistrationRepository;
    @Autowired private AuthFactory authFactory;

    @GetMapping("/auth/code/{provider}")
    public void code(@RequestParam String code, @PathVariable String provider, String state,
                     HttpServletRequest request, HttpServletResponse response) throws IOException {
        String cb = (String) request.getSession(false).getAttribute("web-callback");
        if(cb != null) {
            cb = String.format("%s?code=%s&state=%s&provider=%s", cb, code, state, provider);
            response.sendRedirect(cb);
        }
    }
    @GetMapping("/auth/clients")
    @SuppressWarnings("unchecked")
    public InvokeResult clients(@RequestParam String callback, HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        Iterable<ClientRegistration> iterable;
        StringBuffer url = request.getRequestURL();
        String tempContextUrl = url.delete(url.length() - request.getServletPath().length(), url.length()).toString();
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository).as(Iterable.class);
        if(type != ResolvableType.NONE && ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            iterable = (Iterable<ClientRegistration>) clientRegistrationRepository;
            iterable.forEach(ite -> map.put(ite.getClientName(), tempContextUrl + "/auth/authorization/" + ite.getRegistrationId() + "?callback=" + callback));
        }
        return new InvokeResult(200, map);
    }

    @GetMapping("/auth/{provider}/token")
    public InvokeResult token(@PathVariable String provider, @RequestParam String authCode) {
        AuthService authApi = authFactory.getAuthService(AuthProvider.of(provider));
        TokenInfo token = authApi.getAccessToken(authCode);
        UserInfo user = authApi.getUserInfo(token.getAccessToken());
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("user", user);
        return new InvokeResult(data);
    }
}
