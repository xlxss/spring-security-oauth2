package xiao.xss.study.demo.oauth2.auth.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import xiao.xss.study.demo.oauth2.auth.server.common.AuthServer;

/**
 *
 * @author xiaoliang
 * @since 2019-07-03 10:42
 */
@SpringBootApplication
public class AuthServerApplication {
    public static void main(String[] args) {
        AuthServer.showVersion();
        SpringApplication.run(AuthServerApplication.class, args);
    }
}
