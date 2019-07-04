package xiao.xss.study.demo.oauth2.auth.server.security;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import xiao.xss.study.demo.oauth2.auth.server.common.AuthServer;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录信息包装器
 *
 * @author xiaoliang
 * @since 2019-07-03 16:30
 */
@Data
@EqualsAndHashCode(callSuper = true)
class LoginRequestDetails extends WebAuthenticationDetails {
    private static final long serialVersionUID = AuthServer.SERIAL_VERSION_UID;
    private String username;
    private String password;

    LoginRequestDetails(HttpServletRequest request) {
        super(request);
        username = request.getParameter("username");
        password = request.getParameter("password");
        // 可以实现定制化内容获取，例如从header中获取登录的方式：手机、用户名密码、邮箱等
    }
}
