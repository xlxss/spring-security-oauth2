package xiao.xss.study.demo.oauth2.app.auth.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import xiao.xss.study.demo.oauth2.app.auth.LocalAuthDetails;
import xiao.xss.study.demo.oauth2.app.common.constant.LoginType;

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

        Optional principal = Optional.ofNullable(authentication.getPrincipal());
        Optional credentials = Optional.ofNullable(authentication.getCredentials());
        if (!principal.isPresent() || !credentials.isPresent()) {
            throw new BadCredentialsException("Invalid user credentials");
        }
        if(!(authentication.getDetails() instanceof LocalAuthDetails)) {
            return null;
        }
        LocalAuthDetails details = (LocalAuthDetails) authentication.getDetails();
        if(!supports(details.getLoginType())) {
            return null;
        }
        String username = (String) principal.get();
        String password = (String) credentials.get();
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
