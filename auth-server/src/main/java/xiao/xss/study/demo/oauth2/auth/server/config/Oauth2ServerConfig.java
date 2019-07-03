package xiao.xss.study.demo.oauth2.auth.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * OAuth2 Server Configuration
 *
 * @author xiaoliang
 * @since 2019-07-03 10:47
 */
@Configuration
public class Oauth2ServerConfig {
    @Autowired private UserDetailsService userDetailsService;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private AuthorizationCodeServices authorizationCodeServices;
    @Autowired private TokenStore tokenStore;
    @Autowired private DefaultTokenServices defaultTokenServices;

    // 认证授权服务配置
    @Configuration
    @EnableAuthorizationServer
    public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
        @Override
        public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
            // 定义令牌端点上的安全约束
            super.configure(security);
            // 让/oauth/token支持client_id以及client_secret作登录认证
            security.allowFormAuthenticationForClients()
                    .tokenKeyAccess("isAuthenticated()")
                    .checkTokenAccess("permitAll()");
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            // 定义客户端详细信息服务的配置程序。可以初始化客户端详细信息，也可以只引用现有存储
            super.configure(clients);
            clients.inMemory()
                    // 客户端id和密钥
                    .withClient("client")
                    .secret(passwordEncoder.encode("123456"))
                    // 只提供授权码模式授权服务
                    .authorizedGrantTypes("authorization_code", "refresh_token")
                    // 配置允许的回调URL，在获取授权码和获取访问令牌时所传递的回调URL必须在配置的URL范围内
                    .redirectUris("https://www.baidu.com")
                    // 不添加scope项目无法启动
                    .scopes("all")
            ;
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            // 定义授权和令牌端点以及令牌服务
            super.configure(endpoints);
            endpoints.userDetailsService(userDetailsService);
            endpoints.authorizationCodeServices(authorizationCodeServices);
            endpoints.reuseRefreshTokens(true);
            endpoints.tokenStore(tokenStore);
            endpoints.tokenServices(defaultTokenServices);

            // 授权模式配置
            // 1.通过注入AuthenticationManager，可以打开密码授权
            // 2.通过注入UserDetailsService，或者配置了全局服务(例如在GlobalAuthenticationManagerConfigurer中)，
            //   那么客户端模式将包含对用户详细信息的检查，以确保帐户仍然处于活动状态
            // 3.通过注入AuthorizationCodeServices，实现授权码授权模式
            // 4.通过注入implicitGrantService，管理简易模式授予期间的状态
            // 通过注入TokenGranter，自定义授权模式，会忽略上述四种授权模式

            // 端点URL配置，应被Spring Security保护起来以便仅对经过身份验证的用户进行访问
            // endpoints.pathMapping(defaultPath, customPath);
            // 默认URL
            // /oauth/authorize：授权端点
            // /oauth/token：令牌端点
            // /oauth/confirm_access：用户确认授权提交端点
            // /oauth/error：授权服务错误信息端点
            // /oauth/check_token：用于资源服务访问的令牌解析端点
            // /oauth/token_key：如果你使用JWT令牌的话，提供公有密匙的端点
        }
    }
}
