package xiao.xss.study.demo.oauth2.app.common.auth.token;

/**
 * TODO
 *
 * @author xiaoliang
 * @since 2019-08-12 15:09
 */
public interface TokenUtil {
    TokenInfo generate(Object obj1, Object obj2);
    AuthUser parse(String token);
}
