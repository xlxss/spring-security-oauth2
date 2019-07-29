package xiao.xss.study.demo.oauth2.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xiao.xss.study.demo.oauth2.app.dto.AuthUser;
import xiao.xss.study.demo.oauth2.app.entity.SysUser;

import java.util.ArrayList;

/**
 * 用户管理
 *
 * @author xiaoliang
 * @since 2019-07-25 15:20
 */
@Service
public class UserService {
    @Autowired private PasswordEncoder encoder;

    public AuthUser findUser(String username) {
        AuthUser authUser = new AuthUser(null, null);
        if("admin".equals(username)) {
            SysUser user = new SysUser();
            user.setUsername(username);
            user.setPassword(encoder.encode("admin"));
            user.setName("管理员");
            user.setActive(true);
            authUser = new AuthUser(user, new ArrayList<>());
        }
        if("gust".equals(username)) {
            SysUser user = new SysUser();
            user.setUsername(username);
            user.setPassword(encoder.encode("gust"));
            user.setName("来宾");
            user.setActive(true);
            authUser = new AuthUser(user, new ArrayList<>());
        }
        if("no-active".equals(username)) {
            SysUser user = new SysUser();
            user.setUsername(username);
            user.setPassword(encoder.encode("admin"));
            user.setName("不可用");
            user.setActive(false);
            authUser = new AuthUser(user, new ArrayList<>());
        }
        return authUser;
    }
}
