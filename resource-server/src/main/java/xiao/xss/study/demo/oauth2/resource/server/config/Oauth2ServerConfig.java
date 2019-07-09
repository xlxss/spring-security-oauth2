package xiao.xss.study.demo.oauth2.resource.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

/**
 *
 * @author xiaoliang
 * @since 2019-07-05 17:59
 */
@Configuration
public class Oauth2ServerConfig {

    @Configuration
    @EnableResourceServer
    public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            super.configure(resources);
            resources.resourceId("r1");
            resources.tokenServices(tokenService());
        }
        @Bean
        @Primary
        public ResourceServerTokenServices tokenService() {
            RemoteTokenServices tokenServices = new RemoteTokenServices();
            tokenServices.setCheckTokenEndpointUrl("http://auth-server:8000/oauth/check_token");
            tokenServices.setClientId("weixin");
            tokenServices.setClientSecret("111");
            return tokenServices;
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .and().requestMatchers().anyRequest()
                    .and()
                    .anonymous()
                    .and()
                    .authorizeRequests().antMatchers("/api/**").authenticated();
        }
    }
}
