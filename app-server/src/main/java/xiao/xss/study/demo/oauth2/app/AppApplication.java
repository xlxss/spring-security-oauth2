package xiao.xss.study.demo.oauth2.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import xiao.xss.study.demo.oauth2.app.common.Version;

/**
 * app application
 *
 * @author xiaoliang
 * @since 2019-07-24 11:06
 */
@SpringBootApplication
public class AppApplication {
    public static void main(String[] args) {
        Version.showVersion();
        SpringApplication.run(AppApplication.class, args);
    }
}
