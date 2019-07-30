package xiao.xss.study.demo.oauth2.app.auth.provider;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import xiao.xss.study.demo.oauth2.app.auth.LoginType;
import xiao.xss.study.demo.oauth2.app.auth.exception.AuthException;
import xiao.xss.study.demo.oauth2.app.dto.AuthUser;
import xiao.xss.study.demo.oauth2.app.entity.SysUser;
import xiao.xss.study.demo.oauth2.app.service.UserService;

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
        SysUser sysUser = authUser.getSysUser();
        if(sysUser == null) {
            throw new AuthException("用户名或密码错误");
        }
        if(!encoder.matches(password, sysUser.getPassword())) {
            throw new AuthException("用户名或密码错误");
        }
        if(!sysUser.isActive()) {
            throw new AuthException("账号不可用");
        }

        return authUser;
    }

    @Override
    public boolean supports(LoginType type) {
        return LoginType.UP.equals(type);
    }
}
