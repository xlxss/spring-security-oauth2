package xiao.xss.study.demo.oauth2.app.auth.filter;

import org.springframework.web.filter.OncePerRequestFilter;

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
public class AuthenticationTokenCheckFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // TODO START
        // 此处添加令牌校验，根据不同的第三方进行不同的token校验
//        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
//        if(bearerToken != null && bearerToken.length() > 7) {
//            String token = bearerToken.substring(7);
//            System.out.println(token);
//        }
//        SysUser user = new SysUser();
//        AuthUser authUser = new AuthUser(user, new ArrayList<>());
//        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(authUser, null, authUser.getAuthorities());
//        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        // TODO END

        filterChain.doFilter(request, response);
    }
}
