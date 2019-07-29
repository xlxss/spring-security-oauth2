package xiao.xss.study.demo.oauth2.app.auth.token;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.jwt.crypto.sign.Signer;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import xiao.xss.study.demo.oauth2.app.dto.AuthUser;
import xiao.xss.study.demo.oauth2.app.dto.TokenAndUser;
import xiao.xss.study.demo.oauth2.app.dto.TokenInfo;
import xiao.xss.study.demo.oauth2.app.dto.UserInfo;
import xiao.xss.study.demo.oauth2.app.entity.SysRole;
import xiao.xss.study.demo.oauth2.app.entity.SysUser;
import xiao.xss.study.demo.oauth2.app.service.UserService;
import xiao.xss.study.demo.oauth2.app.thirdparty.auth.AuthFactory;
import xiao.xss.study.demo.oauth2.app.thirdparty.auth.AuthProvider;
import xiao.xss.study.demo.oauth2.app.thirdparty.auth.AuthService;

import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired private AuthFactory authFactory;
    private String verifierKey = new RandomValueStringGenerator().generate();
    private Signer signer = new MacSigner(verifierKey);
    private JsonParser jsonParser = JsonParserFactory.create();

    public TokenAndUser createToken(AuthUser authUser, TokenInfo otherToken) {
        UserInfo user = makeUserInfo(authUser);
        TokenInfo token = makeToken(authUser, otherToken);
        return new TokenAndUser(token, user);
    }

    private UserInfo makeUserInfo(AuthUser authUser) {
        SysUser sysUser = authUser.getSysUser();
        UserInfo user = new UserInfo();
        user.setUsername(sysUser.getUsername());
        user.setName(sysUser.getName());
        Map<String, String> roles = new HashMap<>();
        sysUser.getRoles().forEach(r -> roles.put(r.getCode(), r.getName()));
        user.setRoles(roles);
        return user;
    }
    private TokenInfo makeToken(AuthUser authUser, TokenInfo other) {
        Map<String, Object> map = new HashMap<>();
        addUserProp(map, authUser.getSysUser());
        if(other == null) {
            return generateToken(map);
        } else {
            return generateToken(map, other);
        }
    }

    private void addUserProp(Map<String, Object> map, SysUser user) {
        map.put("userUid", user.getId());
        map.put("name", user.getName());
        map.put("username", user.getUsername());
        List<String> list = user.getRoles().stream().map(SysRole::getCode).collect(Collectors.toList());
        map.put("roles", list);
    }
    private TokenInfo generateToken(Map<String, Object> map) {
        long createTime = System.currentTimeMillis();
        String refresh = UUID.randomUUID().toString();
        map.put("createTime", createTime);
        map.put("expiresIn", 60 * 30);
        map.put("refresh", refresh);
        String accessToken = JwtHelper.encode(jsonParser.formatMap(map), signer).getEncoded();
        TokenInfo token = new TokenInfo();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refresh);
        token.setProvider(null);
        token.setExpiresIn(60 * 30);// 30分钟
        token.setTokenTime(createTime);
        token.setTokenType("Bearer");
        return token;
    }
    private TokenInfo generateToken(Map<String, Object> map, TokenInfo other) {
        long createTime = System.currentTimeMillis();
        String refresh = UUID.randomUUID().toString();
        map.put("createTime", createTime);
        map.put("expiresIn", 60 * 30);
        map.put("refresh", other.getRefreshToken());
        map.put("access", other.getAccessToken());
        map.put("provider", other.getProvider());
        String accessToken = JwtHelper.encode(jsonParser.formatMap(map), signer).getEncoded();
        TokenInfo token = new TokenInfo();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refresh);
        token.setProvider(other.getProvider());
        token.setExpiresIn(other.getExpiresIn());// 30分钟
        token.setTokenTime(createTime);
        token.setTokenType("Bearer");
        return token;
    }

    public AuthUser parseToken(String token) {
        try {
            Map<String, Object> content = jsonParser.parseMap(JwtHelper.decode(token).getClaims());
            if(!content.containsKey("provider")) {
                // local login
                return userService.findUser((String) content.get("username"));
            } else {
                // by thirdparty login
                AuthProvider provider = AuthProvider.of((String) content.get("provider"));
                if(provider != null) {
                    AuthService service = authFactory.getService(provider);
                    Assert.notNull(service, String.format("未提供认证服务实现: %s", provider));
                    if(service.checkToken((String) content.get("access"))) {
                        SysUser user = new SysUser();
                        user.setId(-1L);
                        user.setName((String) content.get("name"));
                        user.setUsername((String) content.get("username"));
                        @SuppressWarnings("unchecked")
                        List<String> roles = (List<String>) content.get("roles");
                        Set<GrantedAuthority> set = new HashSet<>();
                        roles.forEach(r -> set.add(new SimpleGrantedAuthority(r)));
                        return new AuthUser(user, set);
                    }
                }
                return null;
            }
        } catch(Exception e) {
            log.debug("无效的token：{}", token);
            return null;
        }
    }
}
