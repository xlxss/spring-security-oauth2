package xiao.xss.study.demo.oauth2.auth.server.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xiao.xss.study.demo.oauth2.auth.server.entity.SysUserAuthority;

/**
 *
 * @author xiaoliang
 * @since 2019-07-04 10:49
 */
@Repository
public interface SysUserAuthorityRepository extends JpaRepository<SysUserAuthority, Long> {
}
