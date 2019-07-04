package xiao.xss.study.demo.oauth2.auth.server.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xiao.xss.study.demo.oauth2.auth.server.common.AuthServer;

import javax.persistence.*;
import java.util.List;

/**
 * 系统角色
 *
 * @author xiaoliang
 * @since 2019-07-04 9:02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(schema = "oauth2", name = "sys_role")
public class SysRole extends BaseEntity {
    private static final long serialVersionUID = AuthServer.SERIAL_VERSION_UID;

    @Column(length = 10)
    private String code;
    @Column(length = 15)
    private String name;
    @Column(length = 100)
    private String description;

    @OneToMany
    @JoinTable(name = "sys_role_authority", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "authority_id"),
            foreignKey = @ForeignKey(name = "fk_role_authority_1"), inverseForeignKey = @ForeignKey(name = "fk_role_authority_2"))
    private List<SysAuthority> authorities;
}
