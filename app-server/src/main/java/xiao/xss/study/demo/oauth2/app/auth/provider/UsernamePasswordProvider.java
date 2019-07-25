package xiao.xss.study.demo.oauth2.app.auth.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import xiao.xss.study.demo.oauth2.app.auth.LoginType;
import xiao.xss.study.demo.oauth2.app.service.UserService;

/**
 * 用户名密码登录
 *
 * @author xiaoliang
 * @since 2019-07-25 15:45
 */
@Component
public class UsernamePasswordProvider extends AbstractAuthenticationProvider {
    @Autowired private UserService userService;

    @Override
    public UserDetails userDetails(String username, String password) {
        // TODO
        return userService.findUser(username);
    }

    @Override
    public boolean supports(LoginType type) {
        return LoginType.USERNAME.equals(type);
    }
}
