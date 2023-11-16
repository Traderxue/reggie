package com.example.reggie.filter;

import com.alibaba.druid.support.json.JSONUtils;
import com.example.reggie.common.R;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;

@WebFilter(filterName="loginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response =(HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();

        //定义不需要处理的请求路径
        String[] urls = new String[]{
            "/employee/login",
            "/employee/logout",
            "/backend/**",
            "front/**"
        };

        boolean check = check(urls,requestURI);

        if(check){
            //放行
            filterChain.doFilter(request,response);
            return;
        };
        if(request.getSession().getAttribute("employee")!=null){
          filterChain.doFilter(request,response);
          return;
        };

        response.getWriter().write(JSONUtils.toJSONString(R.error("NOTLOGIN")));
        return;
     }

     public boolean check(String[] urls,String requestURI){
        for(String url : urls){
            boolean match = PATH_MATCHER.match(url,requestURI);

            if(match){
                return true;
            }
         }
        return false;
     }
}
