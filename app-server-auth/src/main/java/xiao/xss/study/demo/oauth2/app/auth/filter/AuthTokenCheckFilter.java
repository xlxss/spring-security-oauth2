package xiao.xss.study.demo.oauth2.app.auth.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.OncePerRequestFilter;
import xiao.xss.study.demo.oauth2.app.auth.entrypoint.UnAuthorizedEntryPoint;
import xiao.xss.study.demo.oauth2.app.auth.exception.InvalidTokenException;
import xiao.xss.study.demo.oauth2.app.common.auth.token.AuthUser;
import xiao.xss.study.demo.oauth2.app.common.auth.token.TokenUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token校验filter
 *
 * @author xiaoliang
 * @since 2019-07-25 16:06
 */
@Slf4j
public class AuthTokenCheckFilter extends OncePerRequestFilter {
    private AuthenticationEntryPoint entryPoint = new UnAuthorizedEntryPoint();
    private TokenUtil tokenUtil;
    public AuthTokenCheckFilter(TokenUtil tokenUtil) {
        this.tokenUtil = tokenUtil;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(bearerToken != null && bearerToken.length() > 0) {
            if(bearerToken.startsWith("Bearer ")) {
                AuthUser authUser = tokenUtil.parse(bearerToken.substring(7));
                if(authUser != null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(authUser, null, authUser.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    entryPoint.commence(request, response, new InvalidTokenException("无效的token"));
                    return;
                }
            } else {
                entryPoint.commence(request, response, new InvalidTokenException("无效的token"));
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
