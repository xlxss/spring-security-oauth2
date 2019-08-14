package xiao.xss.study.demo.oauth2.app.common.auth.token;

import lombok.Data;
import xiao.xss.study.demo.oauth2.app.common.Version;

import java.io.Serializable;

/**
 * 令牌和用户信息
 *
 * @author xiaoliang
 * @since 2019-07-29 9:28
 */
@Data
public class TokenAndUser implements Serializable {
    private static final long serialVersionUID = Version.SERIAL_VERSION_UID;
    private TokenInfo token;
    private UserInfo user;

    public TokenAndUser(TokenInfo token, UserInfo user) {
        this.token = token;
        this.user = user;
    }
}
