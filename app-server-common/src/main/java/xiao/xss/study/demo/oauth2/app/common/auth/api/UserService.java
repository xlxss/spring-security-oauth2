package xiao.xss.study.demo.oauth2.app.common.auth.api;

import xiao.xss.study.demo.oauth2.app.common.auth.token.AuthUser;

/**
 * 用户管理
 *
 * @author xiaoliang
 * @since 2019-08-12 14:58
 */
public interface UserService {
    AuthUser findUser(String username);
}
