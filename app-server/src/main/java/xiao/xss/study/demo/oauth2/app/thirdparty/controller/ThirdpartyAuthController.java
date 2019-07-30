package xiao.xss.study.demo.oauth2.app.thirdparty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import xiao.xss.study.demo.oauth2.app.dto.TokenAndUser;
import xiao.xss.study.demo.oauth2.app.thirdparty.auth.AuthProvider;
import xiao.xss.study.demo.oauth2.app.thirdparty.auth.AuthFactory;
import xiao.xss.study.demo.oauth2.app.thirdparty.auth.AuthService;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录认证回调
 *
 * @author xiaoliang
 * @since 2019-07-24 13:51
 */
@RestController
public class ThirdpartyAuthController {
    @Autowired private AuthFactory factory;

    @GetMapping("/auth/{provider}/authorize")
    public String authorize(@PathVariable String provider) {
        AuthProvider app = AuthProvider.of(provider);
        AuthService service = factory.getService(app);
        return service.authorizeUrl();
    }

    @PostMapping("/auth/{provider}/token")
    public TokenAndUser token(@PathVariable String provider, HttpServletRequest request) {
        String authCode = request.getParameter("authCode");
        AuthProvider app = AuthProvider.of(provider);
        Assert.notNull(app, String.format("暂不支持%s方式登录", provider));
        AuthService service = factory.getService(app);
        return service.getAccessToken(authCode);
    }
}
