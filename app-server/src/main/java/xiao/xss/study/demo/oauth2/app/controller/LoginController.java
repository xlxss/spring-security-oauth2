package xiao.xss.study.demo.oauth2.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * TODO
 *
 * @author xiaoliang
 * @since 2019-07-25 15:53
 */
@RestController
public class LoginController {
    @Autowired private AuthenticationManager authenticationManager;

    @PostMapping("/aa/login")
    public String hello(@RequestParam String username, @RequestParam String password) {
        new String(Base64.getDecoder().decode("password"), StandardCharsets.UTF_8);
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        System.out.println(auth);
        return "Hello";
    }
}
