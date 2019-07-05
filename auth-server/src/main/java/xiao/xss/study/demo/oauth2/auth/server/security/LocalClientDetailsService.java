package xiao.xss.study.demo.oauth2.auth.server.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

/**
 *
 * @author xiaoliang
 * @since 2019-07-05 11:34
 */
@Slf4j
@Service
public class LocalClientDetailsService extends JdbcClientDetailsService implements ClientDetailsService, ClientRegistrationService {
    @Autowired
    public LocalClientDetailsService(DataSource dataSource) {
        super(dataSource);
    }
}
