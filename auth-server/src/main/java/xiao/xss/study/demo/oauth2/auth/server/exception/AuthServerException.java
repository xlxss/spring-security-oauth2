package xiao.xss.study.demo.oauth2.auth.server.exception;

import org.springframework.security.core.AuthenticationException;
import xiao.xss.study.demo.oauth2.auth.server.common.AuthServer;

/**
 *
 * @author xiaoliang
 * @since 2019-07-04 16:55
 */
public class AuthServerException extends AuthenticationException {
    private static final long serialVersionUID = AuthServer.SERIAL_VERSION_UID;

    public AuthServerException(String msg, Throwable t) {
        super(msg, t);
    }

    public AuthServerException(String msg) {
        super(msg);
    }
}
