package xiao.xss.study.demo.oauth2.app.auth.entrypoint;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 未登录返回401
 *
 * @author xiaoliang
 * @since 2019-07-25 15:51
 */
public class UnAuthorizedEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Map<String, Object> map = new HashMap<>();
        if("OPTIONS".equals(request.getMethod())){
            map.put("status", 204);
        } else {
            map.put("status", "401");
            map.put("message", authException.getMessage());
        }
        request.setAttribute("error", map);
        request.getRequestDispatcher("/auth/common/error").forward(request, response);
    }
}
