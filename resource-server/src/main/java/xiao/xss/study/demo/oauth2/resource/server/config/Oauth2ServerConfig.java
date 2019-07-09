package xiao.xss.study.demo.oauth2.resource.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import java.util.Collections;

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
                    // 对外OAuth授权时认证配置，第三方应用只能访问/api开头的接口地址
                    .and().requestMatchers().antMatchers("/api/**")
                    .and()
                    .anonymous()
                    .and()
                    .authorizeRequests().antMatchers("/api/**").authenticated()
                    .anyRequest().authenticated();
        }
    }

    @Configuration
    @EnableWebSecurity
    public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            auth.inMemoryAuthentication().passwordEncoder(encoder)
                    .withUser("admin")
                    .password(encoder.encode("admin"))
                    .authorities(Collections.emptyList());
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            super.configure(web);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .anonymous().disable()
                    // 本系统内用户使用时认证配置
                    .requestMatchers().antMatchers("/app/**", "/login")
                    .and()
                    .formLogin()
                    .and()
                    .authorizeRequests()
                    .antMatchers("/login").permitAll()
                    .anyRequest().permitAll();
        }
    }
}
