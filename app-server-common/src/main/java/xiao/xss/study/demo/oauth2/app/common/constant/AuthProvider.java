package xiao.xss.study.demo.oauth2.app.common.constant;

/**
 * 登录认证提供者
 *
 * @author xiaoliang
 * @since 2019-08-13 16:18
 */
public enum AuthProvider {
    local("本地认证中心"),
    weixin("微信"),
    qq("QQ"),
    weibo("微博"),
    gitee("码云"),
    github("github"),
    ;
    private final String desc;
    AuthProvider(String desc) {
        this.desc = desc;
    }

    public static AuthProvider of(String name) {
        AuthProvider provider = null;
        if(name != null && name.length() > 0) {
            for(AuthProvider ap : values()) {
                if(ap.name().equalsIgnoreCase(name)) {
                    provider = ap;
                }
            }
        }
        return provider;
    }

    public String getDesc() {
        return this.desc;
    }
}
