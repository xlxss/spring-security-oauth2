package xiao.xss.study.demo.oauth2.auth.server.entity;

import lombok.Data;
import xiao.xss.study.demo.oauth2.auth.server.common.AuthServer;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * @author xiaoliang
 * @since 2019-07-04 9:43
 */
@Data
@Entity
@Table(schema = "oauth2", name = "sys_user_authority", uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_authority_1", columnNames = {"user_id", "authority_id"})
})
public class SysUserAuthority implements Serializable {
    private static final long serialVersionUID = AuthServer.SERIAL_VERSION_UID;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_authority_2"))
    private SysUser user;

    @ManyToOne
    @JoinColumn(name = "authority_id", foreignKey = @ForeignKey(name = "fk_user_authority_3"))
    private SysAuthority authority;
}
