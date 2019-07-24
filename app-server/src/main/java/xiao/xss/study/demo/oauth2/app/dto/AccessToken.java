package xiao.xss.study.demo.oauth2.app.dto;

import lombok.Data;
import xiao.xss.study.demo.oauth2.app.auth.AppEnum;

/**
 * TODO
 *
 * @author xiaoliang
 * @since 2019-07-24 16:14
 */
@Data
public class AccessToken {
    private AppEnum app;
    private String accessToken;
    private String refreshToken;
    private int expiredIn;
}
