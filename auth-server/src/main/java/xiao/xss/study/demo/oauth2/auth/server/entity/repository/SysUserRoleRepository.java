package xiao.xss.study.demo.oauth2.auth.server.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xiao.xss.study.demo.oauth2.auth.server.entity.SysUserRole;

/**
 *
 * @author xiaoliang
 * @since 2019-07-04 10:36
 */
@Repository
public interface SysUserRoleRepository extends JpaRepository<SysUserRole, Long> {
}
