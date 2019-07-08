package xiao.xss.study.demo.oauth2.auth.server.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xiao.xss.study.demo.oauth2.auth.server.common.AuthServer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 系统权限
 *
 * @author xiaoliang
 * @since 2019-07-04 9:05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(schema = "oauth2", name = "sys_authority")
public class SysAuthority extends BaseEntity {
    private static final long serialVersionUID = AuthServer.SERIAL_VERSION_UID;

    @Column(length = 15)
    private String code;
    @Column(length = 15)
    private String name;
    @Column(length = 100)
    private String description;
}
