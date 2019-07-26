package xiao.xss.study.demo.oauth2.app.thirdparty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import xiao.xss.study.demo.oauth2.app.dto.AccessToken;
import xiao.xss.study.demo.oauth2.app.thirdparty.auth.AppEnum;
import xiao.xss.study.demo.oauth2.app.thirdparty.auth.AuthFactory;
import xiao.xss.study.demo.oauth2.app.thirdparty.auth.AuthService;

/**
 * 登录认证回调
 *
 * @author xiaoliang
 * @since 2019-07-24 13:51
 */
@RestController
public class ThirdpartyAuthController {
    @Autowired private AuthFactory factory;

    @GetMapping("/auth/{appName}/authorize")
    public String authorize(@PathVariable String appName) {
        AppEnum app = AppEnum.of(appName);
        AuthService service = factory.getService(app);
        return service.authorizeUrl();
    }

    @PostMapping("/auth/{appName}/token")
    public AccessToken token(@PathVariable String appName, @RequestParam(value = "code") String authCode) {
        AppEnum app = AppEnum.of(appName);
        Assert.notNull(app, String.format("暂不支持%s方式登录", appName));
        AuthService service = factory.getService(app);
        return service.getAccessToken(authCode);
    }

    @PostMapping("/auth/{appName}/refreshToken")
    public AccessToken refreshToken(@PathVariable String appName, String refreshToken) {
        AppEnum app = AppEnum.of(appName);
        Assert.notNull(app, String.format("暂不支持%s方式登录", appName));
        AuthService service = factory.getService(app);
        return service.refreshToken(refreshToken);
    }
}
