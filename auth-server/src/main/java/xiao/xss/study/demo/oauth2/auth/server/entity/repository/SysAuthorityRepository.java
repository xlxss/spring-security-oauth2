package xiao.xss.study.demo.oauth2.auth.server.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xiao.xss.study.demo.oauth2.auth.server.entity.SysAuthority;

/**
 *
 * @author xiaoliang
 * @since 2019-07-04 9:14
 */
@Repository
public interface SysAuthorityRepository extends JpaRepository<SysAuthority, Long> {
}
