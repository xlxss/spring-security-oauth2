package xiao.xss.study.demo.oauth2.app.thirdparty.auth.api;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import xiao.xss.study.demo.oauth2.app.thirdparty.auth.AppEnum;

/**
 * 本地认证系统
 *
 * @author xiaoliang
 * @since 2019-07-24 15:24
 */
@Service("local")
@Slf4j
public class LocalApi extends AbstractAuthApi {
    @Override
    public AppEnum me() {
        return AppEnum.LOCAL;
    }

    @Override
    public Logger logger() {
        return log;
    }
}
