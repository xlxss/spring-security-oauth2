package xiao.xss.study.demo.oauth2.auth.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xiao.xss.study.demo.oauth2.auth.server.entity.SysUser;
import xiao.xss.study.demo.oauth2.auth.server.entity.repository.SysUserRepository;
import xiao.xss.study.demo.oauth2.auth.server.exception.AccountNotActiveException;
import xiao.xss.study.demo.oauth2.auth.server.exception.PasswordInvalidateException;
import xiao.xss.study.demo.oauth2.auth.server.exception.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author xiaoliang
 * @since 2019-07-03 18:12
 */
@Slf4j
@Service
public class UserService {
    @Autowired private SysUserRepository userRepository;
    @Autowired private PasswordEncoder encoder;

    public UserDetails loadUserByUsername(String username) {
        SysUser user = loadUser(username, null, false);
        return createAuthUser(user);
    }

    public UserDetails loadUser(String username, String password) {
        SysUser user = loadUser(username, password, true);
        return createAuthUser(user);
    }

    private SysUser loadUser(String username, String password, boolean checkPwd) {
        Optional<SysUser> optional = userRepository.findByUsername(username);
        SysUser user = optional.orElseThrow(() -> {
            log.warn("username not found '{}'", username);
            return new UsernameNotFoundException(String.format("username not found '%s'", username));
        });
        if(checkPwd && !encoder.matches(password, user.getPassword())) {
            log.warn("password invalidate");
            throw new PasswordInvalidateException("password invalidate");
        }
        if(!user.isActive()) {
            log.warn("account is not active");
            throw new AccountNotActiveException("account is not active");
        }
        return user;
    }

    private UserDetails createAuthUser(SysUser user) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getAuthorities().forEach(auth -> authorities.add(new SimpleGrantedAuthority(auth.getCode())));
        return new User(user.getUsername(), user.getPassword(), user.isActive(), user.isActive(), user.isActive(), user.isActive(), authorities);
    }
}
