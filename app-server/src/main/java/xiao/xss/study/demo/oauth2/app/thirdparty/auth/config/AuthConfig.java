package xiao.xss.study.demo.oauth2.app.thirdparty.auth.config;

import lombok.Data;

/**
 * 认证服务配置
 *
 * @author xiaoliang
 * @since 2019-07-25 8:59
 */
@Data
public class AuthConfig {
    private String name;
    private String clientId;
    private String clientSecret;
    private String authorizeUri;
    private String accessTokenUri;
    private String redirectUri;
    private String scope = "";
    private String grantType = "authorization_code";
}
