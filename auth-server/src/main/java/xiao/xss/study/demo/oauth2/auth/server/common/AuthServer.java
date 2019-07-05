package xiao.xss.study.demo.oauth2.auth.server.common;

/**
 * 常量类
 *
 * @author xiaoliang
 * @since 2019-07-03 17:12
 */
public final class AuthServer {
    public static final String SERVER_VERSION = "V0.1";
    public static final long SERIAL_VERSION_UID = 1L;

    public static void showVersion() {
        System.out.format("\33[%d;1mAuth-Server: %s%n\33[0;39m", 36, SERVER_VERSION);
    }
}
