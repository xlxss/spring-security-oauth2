package xiao.xss.study.demo.oauth2.app.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import xiao.xss.study.demo.oauth2.app.common.InvokeResult;
import xiao.xss.study.demo.oauth2.app.common.auth.token.AuthUser;
import xiao.xss.study.demo.oauth2.app.common.auth.token.TokenInfo;
import xiao.xss.study.demo.oauth2.app.common.auth.token.TokenUtil;

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
    @Autowired private TokenUtil tokenUtil;

    @PostMapping("/auth/login/success")
    public InvokeResult loginSuccess(Principal principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AuthUser user = (AuthUser) auth.getPrincipal();
        TokenInfo tokenInfo = tokenUtil.generate(user, null);
        System.out.println(tokenInfo);
        System.out.println(principal);
        return new InvokeResult(tokenInfo);
    }

    @PostMapping("/auth/login/failure")
    public InvokeResult loginFailure(HttpServletRequest request) {
        Object msg = request.getAttribute("msg");
        if(msg == null) {
            msg = "登录失败";
        }
        return new InvokeResult(400, msg);
    }

    @PostMapping("/auth/logout/success")
    public InvokeResult logoutSuccess() {
        return new InvokeResult("登出成功");
    }

    @GetMapping("/auth/common/error")
    public InvokeResult error(HttpServletRequest request) {
        return new InvokeResult(500, request.getAttribute("error"));
    }
}
