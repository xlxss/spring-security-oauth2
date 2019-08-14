package xiao.xss.study.demo.oauth2.app.auth.filter;

import org.springframework.web.filter.OncePerRequestFilter;
import xiao.xss.study.demo.oauth2.app.common.util.CommonUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 设置前端回调地址
 *
 * @author xiaoliang
 * @since 2019-08-13 10:48
 */
public class WebCallbackSetFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String cbUrl = request.getParameter("callback");
        if(request.getServletPath().startsWith("/auth/authorization") && CommonUtil.isURL(cbUrl)) {
            request.getSession().setAttribute("web-callback", cbUrl);
        }
        filterChain.doFilter(request, response);
    }
}
