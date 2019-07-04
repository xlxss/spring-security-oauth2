package xiao.xss.study.demo.oauth2.auth.server.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import xiao.xss.study.demo.oauth2.auth.server.security.LoginRequestDetailsSource;
import xiao.xss.study.demo.oauth2.auth.server.security.provider.UsernamePasswordProvider;

/**
 * Spring Security Config
 *
 * @author xiaoliang
 * @since 2019-07-03 11:18
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired private UsernamePasswordProvider usernamePasswordProvider;
    @Autowired private LoginRequestDetailsSource loginRequestDetailsSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(usernamePasswordProvider);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().csrf().and()
                .anonymous()
                .authorities("ROLE_DUMMY")
                .and()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/dummy/**").hasRole("DUMMY")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .authenticationDetailsSource(loginRequestDetailsSource)
        ;
    }
}
