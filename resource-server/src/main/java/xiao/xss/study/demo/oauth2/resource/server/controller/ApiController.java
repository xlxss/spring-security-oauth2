package xiao.xss.study.demo.oauth2.resource.server.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * 对外开放接口
 *
 * @author xiaoliang
 * @since 2019-07-05 18:14
 */
@RestController
public class ApiController {
    @GetMapping("/api/hello")
    public String hello() {
        return "Hello";
    }

    @Secured(value = "auth_5")
    @GetMapping("/api/hello2")
    public String hello2() {
        return "Hello2";
    }

    @PreAuthorize(value = "hasAnyAuthority('AUTH_5')")
    @GetMapping("/api/hello3")
    public String hello3() {
        return "Hello3";
    }

    @GetMapping("/api/me")
    public Principal me(Principal principal) {
        return principal;
    }
}
