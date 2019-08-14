package xiao.xss.study.demo.oauth2.app.auth.provider;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import xiao.xss.study.demo.oauth2.app.auth.exception.AuthException;
import xiao.xss.study.demo.oauth2.app.common.auth.api.UserService;
import xiao.xss.study.demo.oauth2.app.common.auth.token.AuthUser;
import xiao.xss.study.demo.oauth2.app.common.auth.token.UserInfo;
import xiao.xss.study.demo.oauth2.app.common.constant.LoginType;

/**
 * 用户名密码登录
 *
 * @author xiaoliang
 * @since 2019-07-25 15:45
 */
public class UsernamePasswordProvider extends AbstractAuthenticationProvider {
    private UserService userService;
    private PasswordEncoder encoder;

    public UsernamePasswordProvider(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.encoder = passwordEncoder;
    }

    @Override
    public UserDetails userDetails(String username, String password) {
        AuthUser authUser = userService.findUser(username);
        UserInfo user = authUser.getUser();
        if(user == null) {
            throw new AuthException("用户名或密码错误");
        }
        if(!encoder.matches(password, user.getPassword())) {
            throw new AuthException("用户名或密码错误");
        }
        if(!user.isActive()) {
            throw new AuthException("账号不可用");
        }

        return authUser;
    }

    @Override
    public boolean supports(LoginType type) {
        return type == null || LoginType.UP.equals(type);
    }
}
