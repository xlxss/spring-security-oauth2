package xiao.xss.study.demo.oauth2.app.auth.exception;

import xiao.xss.study.demo.oauth2.app.common.App;

/**
 * 无效Token异常
 *
 * @author xiaoliang
 * @since 2019-07-29 13:55
 */
public class InvalidTokenException extends AuthException {
    private static final long serialVersionUID = App.SERIAL_VERSION_UID;

    public InvalidTokenException(String msg, Throwable t) {
        super(msg, t);
    }

    public InvalidTokenException(String msg) {
        super(msg);
    }
}
