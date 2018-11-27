package com.wbg.test01;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@WebFilter(filterName = "CorsFilter")
public class CorsFilter implements Filter {
    String[] originAll = null;

    @Override
    public void init(FilterConfig config) throws ServletException {
      /*  String origin=config.getInitParameter("origin");
        if(origin!=null && !origin.isEmpty()){
            originAll = origin.split(",");
        }*/
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        //new一个读取配置文件
        Properties properties=new Properties();
        //获取文件路径
        String path=request.getServletContext().getRealPath("/dom.properties");
        //读取文件内容
        properties.load(new FileInputStream(path));
        //获取origin的value
        String originValue=properties.getProperty("origin");
        if(originValue!=null && !originValue.isEmpty()){
            originAll = originValue.split(",");
        }
       String origin = request.getHeader("Origin");
        if (origin != null && !origin.isEmpty()){
            for (String originAlls : originAll) {
                    if(originAlls.equals("*")||originAlls.equals(origin)){
                        response.setHeader("Access-Control-Allow-Origin",origin);
                    }
            }
        }
            chain.doFilter(req, resp);
    }

    public void destroy() {
    }
}
