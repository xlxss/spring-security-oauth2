package xiao.xss.study.demo.oauth2.app.service;

import org.springframework.stereotype.Service;
import xiao.xss.study.demo.oauth2.app.dto.AuthUser;
import xiao.xss.study.demo.oauth2.app.entity.SysUser;

import java.util.ArrayList;

/**
 * 用户管理
 *
 * @author xiaoliang
 * @since 2019-07-25 15:20
 */
@Service
public class UserService {

    public AuthUser findUser(String username) {
        return new AuthUser(new SysUser(), new ArrayList<>());
    }
}
