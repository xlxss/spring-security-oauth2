package xiao.xss.study.demo.oauth2.auth.server.entity;

import lombok.Data;
import xiao.xss.study.demo.oauth2.auth.server.common.AuthServer;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * @author xiaoliang
 * @since 2019-07-04 9:19
 */
@Data
@Entity
@Table(schema = "oauth2", name = "sys_user_role", uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_role_1", columnNames = {"user_id", "role_id"})
})
public class SysUserRole implements Serializable {
    private static final long serialVersionUID = AuthServer.SERIAL_VERSION_UID;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_role_2"))
    private SysUser user;

    @ManyToOne
    @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "fk_user_role_3"))
    private SysRole role;
}
