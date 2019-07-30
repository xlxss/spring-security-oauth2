package xiao.xss.study.demo.oauth2.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试接口
 *
 * @author xiaoliang
 * @since 2019-07-25 15:53
 */
@RestController
public class LoginController {
    @GetMapping("/test")
    public String test() {
        return "test ok";
    }
}
