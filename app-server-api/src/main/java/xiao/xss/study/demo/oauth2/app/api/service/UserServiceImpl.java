package xiao.xss.study.demo.oauth2.app.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xiao.xss.study.demo.oauth2.app.common.auth.api.UserService;
import xiao.xss.study.demo.oauth2.app.common.auth.token.AuthUser;
import xiao.xss.study.demo.oauth2.app.common.auth.token.UserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author xiaoliang
 * @since 2019-08-13 14:06
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired private PasswordEncoder encoder;

    @Override
    public AuthUser findUser(String username) {
        AuthUser authUser = new AuthUser(null, null);
        if("admin".equals(username)) {
            UserInfo user = new UserInfo();
            user.setUsername(username);
            user.setPassword(encoder.encode("admin"));
            user.setName("管理员");
            user.setActive(true);
            user.setRoles(roles());
            authUser = new AuthUser(user, new ArrayList<>());
        }
        if("gust".equals(username)) {
            UserInfo user = new UserInfo();
            user.setUsername(username);
            user.setPassword(encoder.encode("gust"));
            user.setName("来宾");
            user.setActive(true);
            user.setRoles(roles());
            authUser = new AuthUser(user, new ArrayList<>());
        }
        if("no-active".equals(username)) {
            UserInfo user = new UserInfo();
            user.setUsername(username);
            user.setPassword(encoder.encode("admin"));
            user.setName("不可用");
            user.setActive(false);
            user.setRoles(roles());
            authUser = new AuthUser(user, new ArrayList<>());
        }
        return authUser;
    }

    private Map<String, String> roles() {
        Map<String, String> roles = new HashMap<>();
        roles.put("admin", "管理员");
        roles.put("user", "用户");
        return roles;
    }
}
