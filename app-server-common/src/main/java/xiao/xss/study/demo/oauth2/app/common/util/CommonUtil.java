package xiao.xss.study.demo.oauth2.app.common.util;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用方法
 *
 * @author xiaoliang
 * @since 2019-08-13 10:45
 */
public final class CommonUtil {
    public static String expandRedirectUri(HttpServletRequest request, ClientRegistration clientRegistration, String action) {
        // Supported URI variables -> baseUrl, action, registrationId
        // Used in -> CommonOAuth2Provider.DEFAULT_REDIRECT_URL = "{baseUrl}/{action}/oauth2/code/{registrationId}"
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("registrationId", clientRegistration.getRegistrationId());
        String baseUrl = UriComponentsBuilder.fromHttpUrl(UrlUtils.buildFullRequestUrl(request))
                .replaceQuery(null)
                .replacePath(request.getContextPath())
                .build()
                .toUriString();
        uriVariables.put("baseUrl", baseUrl);
        if (action != null) {
            uriVariables.put("action", action);
        }
        return UriComponentsBuilder.fromUriString(clientRegistration.getRedirectUriTemplate())
                .buildAndExpand(uriVariables)
                .toUriString();
    }

    public static boolean isURL(String url) {
        // TODO check url
        return url != null && url.length() > 0;
    }
}
