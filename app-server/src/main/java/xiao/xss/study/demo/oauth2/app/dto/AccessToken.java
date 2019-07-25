package xiao.xss.study.demo.oauth2.app.dto;

import lombok.Data;

/**
 * 令牌信息
 *
 * @author xiaoliang
 * @since 2019-07-24 16:14
 */
@Data
public class AccessToken {
    private String appName;
    private String tokenType;
    private String accessToken;
    private String refreshToken;
    private int expiresIn;
    private String scope;
    private long tokenTime;
}
