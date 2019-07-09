package xiao.xss.study.demo.oauth2.resource.server.common;

/**
 *
 * @author xiaoliang
 * @since 2019-07-05 17:54
 */
public final class ResourceServer {
    private static final String SERVER_VERSION = "V0.1";
    public static final long SERIAL_VERSION_UID = 1L;

    public static void showVersion() {
        System.out.format("\33[%d;1mResource-Server: %s%n\33[0;39m", 36, SERVER_VERSION).println();
    }
}
