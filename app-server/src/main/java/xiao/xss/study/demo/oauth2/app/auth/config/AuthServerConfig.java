package xiao.xss.study.demo.oauth2.app.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import xiao.xss.study.demo.oauth2.app.auth.AppEnum;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 认证服务配置
 *
 * @author xiaoliang
 * @since 2019-07-24 14:00
 */
@Configuration
@ConfigurationProperties(prefix = "auth.server")
public class AuthServerConfig {
    private Map<String, AuthConfig> provider = new HashMap<>();

    public AuthConfig getConfig(AppEnum app) {
        Assert.notNull(app, "必须提供认证服务标识");
        return provider.entrySet().stream()
                .filter(kv -> app.getName().equalsIgnoreCase(kv.getKey()) || app.getName().equalsIgnoreCase(kv.getValue().getName()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("暂不支持%s认证登录", app.getDesc())))
                .getValue();
    }

    @PostConstruct
    public void validate() {
        this.provider.values().forEach(this::checkConfig);
    }

    private void checkConfig(AuthConfig authConfig) {
        if (!StringUtils.hasText(authConfig.getClientId())) {
            throw new IllegalStateException("client-id不能为空");
        }
        if (!StringUtils.hasText(authConfig.getClientSecret())) {
            throw new IllegalStateException("client-secret不能为空.");
        }
        if (!StringUtils.hasText(authConfig.getAccessTokenUri())) {
            throw new IllegalStateException("access-token-uri不能为空.");
        }
        if (!StringUtils.hasText(authConfig.getRedirectUri())) {
            throw new IllegalStateException("redirect-uri不能为空.");
        }
    }

    public void setProvider(Map<String, AuthConfig> provider) {
        this.provider = provider;
    }

    @Override
    public String toString() {
        return provider.toString();
    }
}
