package xiao.xss.study.demo.oauth2.app.common.auth.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import xiao.xss.study.demo.oauth2.app.common.constant.AuthProvider;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 获取认证登录服务API
 *
 * @author xiaoliang
 * @since 2019-08-12 15:41
 */
@Component
@Slf4j
public class AuthFactory {
    private Map<AuthProvider, AuthService> authServices = new HashMap<>();

    public AuthService getAuthService(AuthProvider provider) {
        return authServices.get(provider);
    }

    public AuthFactory(Environment env, ApplicationContext context) {
        String[] prov = env.getProperty("auth.providers.active", String[].class);
        if(prov == null || prov.length == 0) {
            log.debug("本系统暂不支持第三方登录");
            return;
        }
        Set<AuthProvider> set = Arrays.stream(prov).map(AuthProvider::of).filter(Objects::nonNull).collect(Collectors.toSet());
        Map<String, AuthService> map = context.getBeansOfType(AuthService.class);
        map.forEach((key, value) -> {
            AuthProvider ap = AuthProvider.of(key);
            if(ap != null) {
                if(set.contains(ap)) {
                    log.debug("添加第三方登录认证主体：{}({})", ap.name(), ap.getDesc());
                    authServices.put(ap, value);
                } else {
                    log.debug("移除未开启的第三方登录认证主体：{}({})", ap.name(), ap.getDesc());
                }
            }
        });
        if(authServices.isEmpty()) {
            log.debug("本系统暂不支持第三方登录");
        }
    }
}
