package xiao.xss.study.demo.oauth2.app.common.auth.token;

import lombok.Data;
import xiao.xss.study.demo.oauth2.app.common.Version;

import java.io.Serializable;

/**
 * Token信息
 *
 * @author xiaoliang
 * @since 2019-07-29 10:59
 */
@Data
public class TokenInfo implements Serializable {
    private static final long serialVersionUID = Version.SERIAL_VERSION_UID;

    private String provider;
    private String tokenType;
    private String accessToken;
    private String refreshToken;
    private int expiresIn;
    private String scope;
    private long tokenTime;
}
