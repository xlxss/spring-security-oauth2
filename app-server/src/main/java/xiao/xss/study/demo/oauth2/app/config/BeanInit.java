package xiao.xss.study.demo.oauth2.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 初始化Bean
 *
 * @author xiaoliang
 * @since 2019-07-25 9:23
 */
@Configuration
public class BeanInit {

    @Bean
    public RestTemplate rest() {
        return new RestTemplate();
    }
}
