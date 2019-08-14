package xiao.xss.study.demo.oauth2.app.thirdparty.local;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import xiao.xss.study.demo.oauth2.app.common.auth.api.AuthService;
import xiao.xss.study.demo.oauth2.app.common.constant.AuthProvider;

/**
 * TODO
 *
 * @author xiaoliang
 * @since 2019-08-13 17:15
 */
@Service("local")
@Slf4j
public class LocalAuthService extends AuthService {
    @Override
    public AuthProvider provider() {
        return AuthProvider.local;
    }

    @Override
    public Logger logger() {
        return log;
    }
}
