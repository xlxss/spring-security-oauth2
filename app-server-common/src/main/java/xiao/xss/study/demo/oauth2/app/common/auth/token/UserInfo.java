package xiao.xss.study.demo.oauth2.app.common.auth.token;

import lombok.Data;
import xiao.xss.study.demo.oauth2.app.common.Version;

import java.io.Serializable;
import java.util.Map;

/**
 * 用户信息
 *
 * @author xiaoliang
 * @since 2019-07-29 9:29
 */
@Data
public class UserInfo implements Serializable {
    private static final long serialVersionUID = Version.SERIAL_VERSION_UID;
    private String username;
    private String password;
    private String name;
    private String avatar;
    private boolean active;
    private Map<String, String> roles;
}
