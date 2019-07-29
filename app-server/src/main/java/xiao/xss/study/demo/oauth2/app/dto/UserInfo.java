package xiao.xss.study.demo.oauth2.app.dto;

import lombok.Data;
import xiao.xss.study.demo.oauth2.app.common.App;

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
    private static final long serialVersionUID = App.SERIAL_VERSION_UID;
    private String username;
    private String name;
    private String avatar;
    private Map<String, String> roles;
}
