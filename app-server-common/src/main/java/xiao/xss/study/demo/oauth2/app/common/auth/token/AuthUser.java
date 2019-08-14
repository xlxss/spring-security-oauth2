package xiao.xss.study.demo.oauth2.app.common.auth.token;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import xiao.xss.study.demo.oauth2.app.common.Version;

import java.util.Collection;

/**
 * 认证用户
 *
 * @author xiaoliang
 * @since 2019-07-25 15:21
 */
public class AuthUser implements UserDetails {
    private static final long serialVersionUID = Version.SERIAL_VERSION_UID;
    private UserInfo user;
    private Collection<? extends  GrantedAuthority> authorities;

    public AuthUser(UserInfo user, Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    public UserInfo getUser() {
        return this.user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.user.isActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.user.isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.user.isActive();
    }

    @Override
    public boolean isEnabled() {
        return this.user.isActive();
    }
}
