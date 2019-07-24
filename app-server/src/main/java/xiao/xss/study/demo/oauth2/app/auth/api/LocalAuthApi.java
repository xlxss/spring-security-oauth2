package xiao.xss.study.demo.oauth2.app.auth.api;

import org.springframework.stereotype.Service;
import xiao.xss.study.demo.oauth2.app.dto.AccessToken;
import xiao.xss.study.demo.oauth2.app.auth.AppEnum;
import xiao.xss.study.demo.oauth2.app.auth.AuthService;

/**
 * 本地认证系统
 *
 * @author xiaoliang
 * @since 2019-07-24 15:24
 */
@Service("local")
public class LocalAuthApi implements AuthService {
    @Override
    public AppEnum me() {
        return AppEnum.LOCAL;
    }

    @Override
    public AccessToken getAccessToken(String authCode) {
        AccessToken token = new AccessToken();
        token.setApp(me());

        // 从认证系统获取token


        return token;
    }
}
