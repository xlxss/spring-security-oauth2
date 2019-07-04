package xiao.xss.study.demo.oauth2.auth.server.exception;

import xiao.xss.study.demo.oauth2.auth.server.common.AuthServer;

/**
 *
 * @author xiaoliang
 * @since 2019-07-04 17:06
 */
public class AccountNotActiveException extends AuthServerException {
    private static final long serialVersionUID = AuthServer.SERIAL_VERSION_UID;

    public AccountNotActiveException(String msg) {
        super(msg);
    }
}
