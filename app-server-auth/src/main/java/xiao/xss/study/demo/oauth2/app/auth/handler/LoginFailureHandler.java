package xiao.xss.study.demo.oauth2.app.auth.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败
 *
 * @author xiaoliang
 * @since 2019-07-26 11:46
 */
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        request.setAttribute("msg", exception.getMessage());
        request.getRequestDispatcher("/auth/login/failure").forward(request, response);
    }
}
