package xiao.xss.study.demo.oauth2.app.thirdparty.auth;

/**
 * 本系统支持的认证登录系统标识
 *
 * @author xiaoliang
 * @since 2019-07-24 15:05
 */
public enum AppEnum {
    LOCAL("local", "本地认证")
    ;
    private final String name;
    private final String desc;

    AppEnum(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public static AppEnum of(String name) {
        AppEnum appEnum = null;
        for(AppEnum app : values()) {
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
