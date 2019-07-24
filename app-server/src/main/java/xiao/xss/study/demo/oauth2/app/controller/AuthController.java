package xiao.xss.study.demo.oauth2.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xiao.xss.study.demo.oauth2.app.dto.AccessToken;
import xiao.xss.study.demo.oauth2.app.auth.AppEnum;
import xiao.xss.study.demo.oauth2.app.auth.AuthFactory;
import xiao.xss.study.demo.oauth2.app.auth.AuthService;

/**
 * 登录认证回调
 *
 * @author xiaoliang
 * @since 2019-07-24 13:51
 */
@RestController
public class AuthController {
    @Autowired private AuthFactory factory;

    @GetMapping("/auth/{appName}")
    public AccessToken thirdpartyLogin(@PathVariable String appName, @RequestParam(value = "code") String authCode) {
        AppEnum app = AppEnum.of(appName);
        Assert.notNull(app, String.format("暂不支持%s方式登录", appName));
        AuthService service = factory.getService(app);
        return service.getAccessToken(authCode);
    }
}
