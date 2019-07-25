package xiao.xss.study.demo.oauth2.app.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 用户角色
 *
 * @author xiaoliang
 * @since 2019-07-25 15:11
 */
@Data
@Entity
@Table(name = "sys_role")
public class SysRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 15)
    private String code;
    @Column(length = 15)
    private String name;
    @Column(length = 100)
    private String description;
    private boolean status;
}
