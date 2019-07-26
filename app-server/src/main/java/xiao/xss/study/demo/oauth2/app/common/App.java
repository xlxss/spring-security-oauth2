package xiao.xss.study.demo.oauth2.app.common;

/**
 * TODO
 *
 * @author xiaoliang
 * @since 2019-07-26 9:17
 */
public final class App {
    public static final long SERIAL_VERSION_UID = 1L;
    private static final String SERVER_VERSION = "V0.1";

    private App(){}

    public static void showVersion() {
        System.out.format("\33[%d;1mAuth-Server: %s%n\33[0;39m", 36, SERVER_VERSION);
    }
}
