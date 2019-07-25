package xiao.xss.study.demo.oauth2.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * 系统用户
 *
 * @author xiaoliang
 * @since 2019-07-25 15:11
 */
@Data
@Entity
@Table(name = "sys_user")
public class SysUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    @OneToMany
    @JoinTable(name = "sys_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"),
            foreignKey = @ForeignKey(name = "fk_user_role_1"), inverseForeignKey = @ForeignKey(name = "fk_user_role_2"))
    private List<SysRole> roles = Collections.emptyList();

    @JsonIgnore
    @CreationTimestamp
    private LocalDateTime createTime;

    @JsonIgnore
    @UpdateTimestamp
    private LocalDateTime updateTime;
}
