package xiao.xss.study.demo.oauth2.app.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import xiao.xss.study.demo.oauth2.app.auth.entrypoint.UnAuthorizedEntryPoint;
import xiao.xss.study.demo.oauth2.app.auth.filter.AuthenticationTokenCheckFilter;
import xiao.xss.study.demo.oauth2.app.auth.provider.UsernamePasswordProvider;

/**
 * security config
 *
 * @author xiaoliang
 * @since 2019-07-24 11:12
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired private UsernamePasswordProvider usernamePasswordProvider;
    @Autowired private UnAuthorizedEntryPoint unAuthorizedEntryPoint;
    @Autowired private AuthenticationTokenCheckFilter authenticationTokenCheckFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(usernamePasswordProvider);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().and()
                .exceptionHandling().authenticationEntryPoint(unAuthorizedEntryPoint)
                .and()
                .requestMatchers()
                .and()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .anyRequest().authenticated();

        // 令牌校验过滤器
        http.addFilterBefore(authenticationTokenCheckFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
