package xiao.xss.study.demo.oauth2.app.auth.entrypoint;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 未登录返回401
 *
 * @author xiaoliang
 * @since 2019-07-25 15:51
 */
public class UnAuthorizedEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        if("OPTIONS".equals(request.getMethod())){
            response.sendError(HttpStatus.NO_CONTENT.value());
            return;
        }
        response.sendError(HttpStatus.UNAUTHORIZED.value(), authException.getMessage());
    }
}
