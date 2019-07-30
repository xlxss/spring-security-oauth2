package xiao.xss.study.demo.oauth2.app.auth;

/**
 * 登录方式
 *
 * @author xiaoliang
 * @since 2019-07-25 17:45
 */
public enum LoginType {
    UP("用户名/密码登录"),
    ;

    private final String value;
    LoginType(String value) {
        this.value = value;
    }

    public static LoginType of(String name) {
        if(name == null || name.length() == 0) {
            return null;
        }
        for(LoginType type : values()) {
            if(type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

    public String getValue() {
        return this.value;
    }
}
