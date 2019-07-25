package xiao.xss.study.demo.oauth2.app.thirdparty.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取认证登录服务API
 *
 * @author xiaoliang
 * @since 2019-07-24 15:26
 */
@Component
@Slf4j
public class AuthFactory implements ApplicationContextAware {
    private final Map<AppEnum, AuthService> services = new HashMap<>();

    public AuthService getService(AppEnum app) {
        Assert.notNull(app, "必须提供认证服务标识");
        AuthService service = services.get(app);
        Assert.notNull(service, String.format("未提供认证服务实现: %s", app));
        return service;
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        Map<String, AuthService> map = context.getBeansOfType(AuthService.class);
        map.forEach((key, value) -> {
            AppEnum app = AppEnum.of(key);
            if(app != null) {
                services.put(app, value);
                log.debug("添加登录认证服务，认证主体: {}, name: {}, type: {}", app.getDesc(), app.getName(), value.getClass().getTypeName());
            } else {
                log.warn("移除不支持的登录认证服务, name: {}, type: {}", key, value.getClass().getTypeName());
            }
        });
    }
}
