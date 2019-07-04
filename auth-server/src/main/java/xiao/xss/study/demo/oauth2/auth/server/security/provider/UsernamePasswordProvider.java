package xiao.xss.study.demo.oauth2.auth.server.security.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import xiao.xss.study.demo.oauth2.auth.server.service.UserService;

/**
 *
 * @author xiaoliang
 * @since 2019-07-03 16:44
 */
@Component
public class UsernamePasswordProvider extends AbstractAuthenticationProvider {
    @Autowired private UserService userService;

    @Override
    public UserDetails userDetails(String username, String password) {
         return userService.loadUser(username, password);
    }
}
