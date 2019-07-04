package xiao.xss.study.demo.oauth2.auth.server.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xiao.xss.study.demo.oauth2.auth.server.entity.SysRole;

/**
 *
 * @author xiaoliang
 * @since 2019-07-04 9:13
 */
@Repository
public interface SysRoleRepository extends JpaRepository<SysRole, Long> {
}
