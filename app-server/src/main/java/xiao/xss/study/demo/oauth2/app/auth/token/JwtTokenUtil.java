package xiao.xss.study.demo.oauth2.app.auth.token;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.jwt.crypto.sign.Signer;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import xiao.xss.study.demo.oauth2.app.dto.AuthUser;
import xiao.xss.study.demo.oauth2.app.dto.TokenAndUser;
import xiao.xss.study.demo.oauth2.app.dto.TokenInfo;
import xiao.xss.study.demo.oauth2.app.dto.UserInfo;
import xiao.xss.study.demo.oauth2.app.entity.SysUser;
import xiao.xss.study.demo.oauth2.app.service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Token管理
 *
 * @author xiaoliang
 * @since 2019-07-26 16:24
 */
@Slf4j
@Component
public class JwtTokenUtil {
    @Autowired private UserService userService;
    private String verifierKey = new RandomValueStringGenerator().generate();
    private Signer signer = new MacSigner(verifierKey);
    private JsonParser jsonParser = JsonParserFactory.create();

    public TokenAndUser creatToken(AuthUser authUser) {
        TokenInfo token = new TokenInfo();
        UserInfo user = new UserInfo();
        SysUser sysUser = authUser.getSysUser();
        user.setName(sysUser.getName());
        user.setUsername(sysUser.getUsername());
        Map<String, String> roles = new HashMap<>();
        sysUser.getRoles().forEach(r -> roles.put(r.getCode(), r.getName()));
        user.setRoles(roles);

        String accessToken = JwtHelper.encode(parseContent(authUser), signer).getEncoded();
        token.setAccessToken(accessToken);
        token.setRefreshToken(UUID.randomUUID().toString());
        token.setProvider(null);
        token.setExpiresIn(10*1000);
        token.setScope("");
        token.setTokenTime(System.currentTimeMillis());
        token.setTokenType("Bearer");

        return new TokenAndUser(token, user);
    }

    public AuthUser parseToken(String token) {
        try {
            Map<String, Object> content = jsonParser.parseMap(JwtHelper.decode(token).getClaims());
            if(!content.containsKey("appName") || StringUtils.isEmpty(content.get("appName"))) {
                // local login
                return userService.findUser((String) content.get("username"));
            } else {
                // by thirdparty login
                // TODO
                return null;
            }
        } catch(Exception e) {
            log.debug("无效的token：{}", token);
            return null;
        }
    }

    private String parseContent(AuthUser authUser) {
        Map<String, Object> map = new HashMap<>();
        SysUser user = authUser.getSysUser();
        map.put("name", user.getName());
        map.put("username", user.getUsername());
//        map.put("appName", "appName");
        return jsonParser.formatMap(map);
    }
}
