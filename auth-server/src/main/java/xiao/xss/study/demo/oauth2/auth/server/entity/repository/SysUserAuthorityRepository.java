package xiao.xss.study.demo.oauth2.auth.server.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import xiao.xss.study.demo.oauth2.auth.server.entity.SysAuthority;
import xiao.xss.study.demo.oauth2.auth.server.entity.SysUser;
import xiao.xss.study.demo.oauth2.auth.server.entity.SysUserAuthority;

import java.util.List;

/**
 *
 * @author xiaoliang
 * @since 2019-07-04 10:49
 */
@Repository
public interface SysUserAuthorityRepository extends JpaRepository<SysUserAuthority, Long> {
    @Query(value = "select sua.authority from SysUserAuthority sua where sua.user = ?1")
    List<SysAuthority> findAuthorityByUser(SysUser user);
}
