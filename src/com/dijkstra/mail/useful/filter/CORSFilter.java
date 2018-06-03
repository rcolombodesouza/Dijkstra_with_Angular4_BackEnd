package com.dijkstra.mail.useful.filter;

import java.io.IOException;
import static java.util.logging.Level.INFO;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CORSFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(CORSFilter.class.getName());

    public CORSFilter() {
        //
    }

    @Override
    public void destroy() {
        //
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        LOGGER.log(INFO, "CORSFilter HTTP Request: {}", new Object[] {request.getMethod()});

        // Authorize (allow) all domains to consume the content
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Origin", "*");
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, "
                + "PUT, POST");
        ((HttpServletResponse)servletResponse).addHeader("Access-Control-Allow-Headers", "Foo-Header");
        ((HttpServletResponse)servletResponse).addHeader("Access-Control-Max-Age", "86400");

        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        // For HTTP OPTIONS verb/method reply with ACCEPTED status code -- per CORS handshake
        if (request.getMethod().equals("OPTIONS")) {
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            return;
        }

        // pass the request along the filter chain
        chain.doFilter(request, servletResponse);
    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        //
    }

}