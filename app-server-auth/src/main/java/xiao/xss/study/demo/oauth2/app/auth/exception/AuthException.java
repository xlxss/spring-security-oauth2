package xiao.xss.study.demo.oauth2.app.auth.exception;

import org.springframework.security.core.AuthenticationException;
import xiao.xss.study.demo.oauth2.app.common.Version;

/**
 * 认证异常
 *
 * @author xiaoliang
 * @since 2019-07-26 10:16
 */
public class AuthException extends AuthenticationException {
    private static final long serialVersionUID = Version.SERIAL_VERSION_UID;

    public AuthException(String msg, Throwable t) {
        super(msg, t);
    }

    public AuthException(String msg) {
        super(msg);
    }
}
