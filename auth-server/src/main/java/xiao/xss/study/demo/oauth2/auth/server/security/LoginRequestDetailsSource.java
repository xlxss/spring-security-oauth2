package xiao.xss.study.demo.oauth2.auth.server.security;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author xiaoliang
 * @since 2019-07-03 17:08
 */
@Component
public class LoginRequestDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, LoginRequestDetails> {
    @Override
    public LoginRequestDetails buildDetails(HttpServletRequest context) {
        return new LoginRequestDetails(context);
    }
}
