package xiao.xss.study.demo.oauth2.app.common;

/**
 * 版本号
 *
 * @author xiaoliang
 * @since 2019-08-13 10:22
 */
public class Version {
    public static final long SERIAL_VERSION_UID = 1L;
    private static final String SERVER_VERSION = "V0.1";

    private Version(){}

    public static void showVersion() {
        System.out.format("\33[%d;1mAuth-Server: %s%n\33[0;39m", 36, SERVER_VERSION);
    }
}
