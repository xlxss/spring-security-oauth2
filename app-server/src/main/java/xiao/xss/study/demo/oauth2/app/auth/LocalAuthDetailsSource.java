package xiao.xss.study.demo.oauth2.app.auth;

import org.springframework.security.authentication.AuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求参数包装器
 *
 * @author xiaoliang
 * @since 2019-07-26 9:13
 */
public class LocalAuthDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, LocalAuthDetails> {
    @Override
    public LocalAuthDetails buildDetails(HttpServletRequest context) {
        return new LocalAuthDetails(context);
    }
}
