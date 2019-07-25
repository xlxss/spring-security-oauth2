package xiao.xss.study.demo.oauth2.app.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import xiao.xss.study.demo.oauth2.app.entity.SysUser;

import java.util.Collection;

/**
 * 认证用户
 *
 * @author xiaoliang
 * @since 2019-07-25 15:21
 */
public class AuthUser implements UserDetails {
    private SysUser sysUser;
    private Collection<? extends  GrantedAuthority> authorities;

    public AuthUser(SysUser sysUser, Collection<? extends GrantedAuthority> authorities) {
        this.sysUser = sysUser;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.sysUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.sysUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.sysUser.isActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.sysUser.isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.sysUser.isActive();
    }

    @Override
    public boolean isEnabled() {
        return this.sysUser.isActive();
    }
}
