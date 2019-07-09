package xiao.xss.study.demo.oauth2.resource.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import xiao.xss.study.demo.oauth2.resource.server.common.ResourceServer;

/**
 * 资源服务
 *
 * @author xiaoliang
 * @since 2019-07-05 17:53
 */
@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
public class ResourceServerApplication {
    public static void main(String[] args) {
        ResourceServer.showVersion();
        SpringApplication.run(ResourceServerApplication.class, args);
    }
}
