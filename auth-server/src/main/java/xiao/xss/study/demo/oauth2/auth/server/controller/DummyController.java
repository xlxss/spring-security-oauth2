package xiao.xss.study.demo.oauth2.auth.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xiao.xss.study.demo.oauth2.auth.server.entity.*;
import xiao.xss.study.demo.oauth2.auth.server.entity.repository.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 测试
 * @author xiaoliang
 * @since 2019-07-04 11:05
 */
@RestController
@EnableGlobalMethodSecurity(securedEnabled = true)
@Secured(value = {"ROLE_DUMMY"})
@Slf4j
public class DummyController {
    @Autowired private SysUserRepository userRepository;
    @Autowired private SysRoleRepository roleRepository;
    @Autowired private SysAuthorityRepository authorityRepository;
    @Autowired private SysUserRoleRepository userRoleRepository;
    @Autowired private SysUserAuthorityRepository userAuthorityRepository;
    @Autowired private PasswordEncoder encoder;

    @GetMapping("/dummy/user/all")
    public List<SysUser> getUsers() {
        log.debug("Find all users");
        return userRepository.findAll();
    }
    @GetMapping("/dummy/user/add")
    public String addUser(String username, String password, String name, String email, String mobile, boolean active) {
        SysUser user = new SysUser();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setName(name);
        user.setEmail(email);
        user.setMobile(mobile);
        user.setActive(active);
        userRepository.save(user);
        log.debug("User added: {}", user);
        return "OK";
    }

    @GetMapping("/dummy/role/all")
    public List<SysRole> getRoles() {
        log.debug("Find all roles");
        return roleRepository.findAll();
    }
    @GetMapping("/dummy/role/add")
    public String addRole(String code, String name, String authorityIds) {
        SysRole role = new SysRole();
        role.setCode(code);
        role.setName(name);
        List<SysAuthority> authorities = new ArrayList<>();
        Arrays.stream(authorityIds.split(",")).forEach(id -> {
            SysAuthority authority = new SysAuthority();
            authority.setId(Long.parseLong(id));
            authorities.add(authority);
        });
        role.setAuthorities(authorities);
        roleRepository.save(role);
        log.debug("Role added: {}", role);
        return "OK";
    }

    @GetMapping("/dummy/authority/all")
    public List<SysAuthority> getAuthorities() {
        log.debug("Find all authorities");
        return authorityRepository.findAll();
    }
    @GetMapping("/dummy/authority/add")
    public String addAuthority(String code, String name) {
        SysAuthority authority = new SysAuthority();
        authority.setCode(code);
        authority.setName(name);
        authorityRepository.save(authority);
        log.debug("Authority added: {}", authority);
        return "OK";
    }

    @GetMapping("/dummy/userRole/add")
    public String addUserRole(long userId, long roleId) {
        SysUserRole userRole = new SysUserRole();
        SysUser user = new SysUser();
        user.setId(userId);
        SysRole role = new SysRole();
        role.setId(roleId);
        userRole.setRole(role);
        userRole.setUser(user);
        userRole = userRoleRepository.save(userRole);
        log.debug("Grant role '{}:{}' to user '{}:{}'", userRole.getUser().getUsername(), userRole.getUser().getName(),
                userRole.getRole().getCode(), userRole.getRole().getName());
        return "OK";
    }
    @GetMapping("/dummy/userAuthority/add")
    public String addUserAuthority(long userId, long authorityId) {
        SysUserAuthority userAuthority = new SysUserAuthority();
        SysUser user = new SysUser();
        user.setId(userId);
        SysAuthority authority = new SysAuthority();
        authority.setId(authorityId);
        userAuthority.setUser(user);
        userAuthority.setAuthority(authority);
        userAuthority = userAuthorityRepository.save(userAuthority);
        log.debug("Grant authorities '{}:{}' to user '{}:{}'", userAuthority.getUser().getUsername(), userAuthority.getUser().getName(),
                userAuthority.getAuthority().getCode(), userAuthority.getAuthority().getName());
        return "OK";
    }
}
