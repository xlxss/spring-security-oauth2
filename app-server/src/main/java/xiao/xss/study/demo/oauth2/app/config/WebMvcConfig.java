package xiao.xss.study.demo.oauth2.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web mvc config
 *
 * @author xiaoliang
 * @since 2019-07-24 11:13
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD")
                .allowedHeaders("X-PINGOTHER","Origin","X-Requested-With","Content-Type","Accept","Authorization","X-Login-Type")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
