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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import xiao.xss.study.demo.oauth2.app.auth.LocalAuthDetailsSource;
import xiao.xss.study.demo.oauth2.app.auth.entrypoint.UnAuthorizedEntryPoint;
import xiao.xss.study.demo.oauth2.app.auth.filter.AuthTokenCheckFilter;
import xiao.xss.study.demo.oauth2.app.auth.filter.WebCallbackSetFilter;
import xiao.xss.study.demo.oauth2.app.auth.handler.LoginFailureHandler;
import xiao.xss.study.demo.oauth2.app.auth.handler.LoginSuccessHandler;
import xiao.xss.study.demo.oauth2.app.auth.handler.LogoutSuccessHandler;
import xiao.xss.study.demo.oauth2.app.auth.provider.UsernamePasswordProvider;
import xiao.xss.study.demo.oauth2.app.common.auth.api.UserService;
import xiao.xss.study.demo.oauth2.app.common.auth.token.TokenUtil;

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
    @Autowired private TokenUtil tokenUtil;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(new UsernamePasswordProvider(userService, passwordEncoder()));
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
                .requestMatchers().antMatchers("/auth/**", "/3rd/auth/**", "/api/**")
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
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/auth/authorization");

        // 令牌校验过滤器
        http.addFilterBefore(new AuthTokenCheckFilter(tokenUtil), UsernamePasswordAuthenticationFilter.class);
        // 前端回调地址设定过滤器
        http.addFilterBefore(new WebCallbackSetFilter(), OAuth2AuthorizationRequestRedirectFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public RestTemplate rest() {
        return new RestTemplate();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
