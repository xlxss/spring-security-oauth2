package xiao.xss.study.demo.oauth2.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * 登录认证Controller
 *
 * @author xiaoliang
 * @since 2019-07-26 14:25
 */
@RestController
public class AuthController {

    @PostMapping("/auth/login/success")
    public Object loginSuccess(Principal principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(principal);
        return auth;
    }

    @PostMapping("/auth/login/failure")
    public ResponseEntity<Object> loginFailure(HttpServletRequest request) {
        Object msg = request.getAttribute("msg");
        if(msg == null) {
            msg = "登录失败";
        }
        return ResponseEntity.badRequest().body(msg);
    }

    @PostMapping("/auth/logout/success")
    public ResponseEntity logoutSuccess() {
        return ResponseEntity.ok("Logout success");
    }
}
