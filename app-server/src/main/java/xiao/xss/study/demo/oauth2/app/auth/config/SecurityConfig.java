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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import xiao.xss.study.demo.oauth2.app.auth.LocalAuthDetailsSource;
import xiao.xss.study.demo.oauth2.app.auth.entrypoint.UnAuthorizedEntryPoint;
import xiao.xss.study.demo.oauth2.app.auth.filter.AuthenticationTokenCheckFilter;
import xiao.xss.study.demo.oauth2.app.auth.handler.LoginFailureHandler;
import xiao.xss.study.demo.oauth2.app.auth.handler.LoginSuccessHandler;
import xiao.xss.study.demo.oauth2.app.auth.handler.LogoutSuccessHandler;
import xiao.xss.study.demo.oauth2.app.auth.provider.UsernamePasswordProvider;
import xiao.xss.study.demo.oauth2.app.auth.token.JwtTokenUtil;
import xiao.xss.study.demo.oauth2.app.service.UserService;

/**
 * security config
 *
 * @author xiaoliang
 * @since 2019-07-24 11:12
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired private UserService userService;
    @Autowired private PasswordEncoder encoder;
    @Autowired private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(new UsernamePasswordProvider(userService, encoder));
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().and()
                .exceptionHandling().authenticationEntryPoint(new UnAuthorizedEntryPoint())
                .and()
                .requestMatchers()
                .and()
                .formLogin()
                .loginProcessingUrl("/auth/login")
                .authenticationDetailsSource(new LocalAuthDetailsSource())
                .successHandler(new LoginSuccessHandler())
                .failureHandler(new LoginFailureHandler())
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/auth/logout")
                .logoutSuccessHandler(new LogoutSuccessHandler())
                .clearAuthentication(true)
                .and()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/error").permitAll()
                .anyRequest().authenticated();

        // 令牌校验过滤器
        http.addFilterBefore(new AuthenticationTokenCheckFilter(jwtTokenUtil), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
