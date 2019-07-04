package xiao.xss.study.demo.oauth2.auth.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import xiao.xss.study.demo.oauth2.auth.server.common.AuthServer;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author xiaoliang
 * @since 2019-07-03 17:48
 */
@Data
@EqualsAndHashCode(of = {"id"})
@MappedSuperclass
class BaseEntity implements Serializable {
    private static final long serialVersionUID = AuthServer.SERIAL_VERSION_UID;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @CreationTimestamp
    private LocalDateTime createTime;

    @JsonIgnore
    @UpdateTimestamp
    private LocalDateTime updateTime;
}
