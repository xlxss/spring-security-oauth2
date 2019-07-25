package xiao.xss.study.demo.oauth2.app.auth.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import xiao.xss.study.demo.oauth2.app.auth.LoginType;

import java.util.Optional;

/**
 * 登录方式适配器
 *
 * @author xiaoliang
 * @since 2019-07-25 15:44
 */
@Slf4j
public abstract class AbstractAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Object details = authentication.getDetails();
        log.debug("details：{}", details);
        Optional principal = Optional.ofNullable(authentication.getPrincipal());
        Optional credentials = Optional.ofNullable(authentication.getCredentials());
        if (!principal.isPresent() || !credentials.isPresent()) {
            throw new BadCredentialsException("Invalid user credentials");
        }
        // TODO
//        if(!supports(LoginType)) {
//            return null;
//        }
        String username = (String) principal.get();
        String password = (String) credentials.get();
        // 当登录时密码用Base64编码后传到服务端时，需要解码
        // password = new String(Base64.getDecoder().decode(password), StandardCharsets.UTF_8);
        UserDetails user = userDetails(username, password);
        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    }

    public abstract UserDetails userDetails(String username, String password);
    public abstract boolean supports(LoginType type);

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
