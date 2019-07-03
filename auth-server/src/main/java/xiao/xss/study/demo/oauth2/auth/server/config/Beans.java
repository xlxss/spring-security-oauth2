package xiao.xss.study.demo.oauth2.auth.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * 创建bean
 *
 * @author xiaoliang
 * @since 2019-07-03 10:49
 */
@Configuration
public class Beans {
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.builder().username("admin").password(encoder.encode("pass")).roles("USER").build());
        return manager;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new InMemoryAuthorizationCodeServices();
    }
    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }
    @Bean
    public DefaultTokenServices defaultTokenServices(TokenStore tokenStore) {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore);
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setAccessTokenValiditySeconds(60 * 60 * 12);//12h
        tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 7);//1W
        return tokenServices;
    }
}
