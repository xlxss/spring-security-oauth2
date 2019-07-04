package xiao.xss.study.demo.oauth2.auth.server.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xiao.xss.study.demo.oauth2.auth.server.service.UserService;

/**
 *
 * @author xiaoliang
 * @since 2019-07-04 16:43
 */
@Slf4j
@Service
public class LocalUserDetailsService implements UserDetailsService {
    @Autowired private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.loadUserByUsername(username);
    }
}
