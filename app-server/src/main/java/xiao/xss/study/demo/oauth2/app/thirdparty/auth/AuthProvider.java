package xiao.xss.study.demo.oauth2.app.thirdparty.auth;

/**
 * 本系统支持的认证登录系统标识
 *
 * @author xiaoliang
 * @since 2019-07-24 15:05
 */
public enum AuthProvider {
    LOCAL("local", "本地认证")
    ;
    private final String name;
    private final String desc;

    AuthProvider(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public static AuthProvider of(String name) {
        AuthProvider appEnum = null;
        for(AuthProvider app : values()) {
            if(app.getName().equalsIgnoreCase(name)) {
                appEnum = app;
                break;
            }
        }
        return appEnum;
    }

    public String getName() {
        return this.name;
    }
    public String getDesc() {
        return this.desc;
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", desc, name);
    }
}
