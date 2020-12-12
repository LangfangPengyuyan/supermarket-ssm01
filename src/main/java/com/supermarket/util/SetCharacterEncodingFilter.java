package com.supermarket.util;


import javax.servlet.*;
import java.io.IOException;

/**
 * Filter that sets the character encoding to be used in parsing the
 * incoming request
 */
public class SetCharacterEncodingFilter implements Filter {
    protected String encoding = null;
    protected FilterConfig filterConfig = null;
    public void destroy() {}
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        this.encoding = filterConfig.getInitParameter("encoding");
    }
    /**
     * Select and set(if specified) the character encoding to be used to
     * interpret request parameters for this request
     */
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);

        chain.doFilter(request,response);
    }
}
