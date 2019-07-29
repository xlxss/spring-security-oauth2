package xiao.xss.study.demo.oauth2.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import xiao.xss.study.demo.oauth2.app.auth.token.JwtTokenUtil;
import xiao.xss.study.demo.oauth2.app.dto.AuthUser;
import xiao.xss.study.demo.oauth2.app.dto.TokenAndUser;

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
    @Autowired private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/auth/login/success")
    public Object loginSuccess(Principal principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AuthUser user = (AuthUser) auth.getPrincipal();
        TokenAndUser tokenAndUser = jwtTokenUtil.creatToken(user);
        System.out.println(tokenAndUser);
        System.out.println(principal);
        return tokenAndUser;
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

    @GetMapping("/auth/common/error")
    public Object error(HttpServletRequest request) {
        return request.getAttribute("error");
    }
}
