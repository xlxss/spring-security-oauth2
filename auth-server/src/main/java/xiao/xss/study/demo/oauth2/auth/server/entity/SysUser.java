package xiao.xss.study.demo.oauth2.auth.server.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xiao.xss.study.demo.oauth2.auth.server.common.AuthServer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 系统用户
 *
 * @author xiaoliang
 * @since 2019-07-03 17:35
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(schema = "oauth2", name = "sys_user")
public class SysUser extends BaseEntity implements Serializable {
    private static final long serialVersionUID = AuthServer.SERIAL_VERSION_UID;

    @Column(length = 50)
    private String username;
    @Column(length = 64)
    private String password;
    @Column(length = 50)
    private String name;
    @Column(length = 50)
    private String email;
    @Column(length = 13)
    private String mobile;
    private boolean active;

    @Transient
    private List<SysRole> roles;
    @Transient
    private List<SysAuthority> authorities;
}
