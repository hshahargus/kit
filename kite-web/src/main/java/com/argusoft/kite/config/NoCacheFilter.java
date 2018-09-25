package com.argusoft.kite.config;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Completely disable browser caching.
 *
 * @author 
 */
@Component
public class NoCacheFilter implements Filter {
    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(NoCacheFilter.class);

    /**
     * Place this filter into service.
     *
     * @param filterConfig the filter configuration object used by a servlet
     * container to pass information to a filter during initialization
     * @throws ServletException to inform the container to not place the filter
     * into service
     */
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * Set cache header directives.
     *
     * @param servletRequest provides data including parameter name and values,
     * attributes, and an input stream
     * @param servletResponse assists a servlet in sending a response to the
     * client
     * @param filterChain gives a view into the invocation chain of a filtered
     * request
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
//        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

//        String requestUrl = httpServletRequest.getRequestURL().toString();
//        LOGGER.debug("\t\tfiltering url " + requestUrl);
        //this is used to return 200 status for options request in firefox browser
//        String method = httpServletRequest.getMethod();

//        if (method.equalsIgnoreCase("options") && httpServletResponse != null) {
//            httpServletResponse.setStatus(200);
//        }
//        //set cross origin domain name to allow access.
//        if (httpServletRequest.getHeader("Origin") != null) {
//            httpServletResponse.addHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));
//        } else {
//            httpServletResponse.addHeader("Access-Control-Allow-Origin", "http://localhost");
//        }

        // set cache directives
        httpServletResponse.setHeader("Cache-Control", "no-cache");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", -1);
        //set cors headers
//        httpServletResponse.addHeader("Access-Control-Allow-Origin", domainName);
//        httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
//        httpServletResponse.addHeader("Access-Control-Allow-Methods", "ACL, CANCELUPLOAD, CHECKIN, CHECKOUT, COPY, DELETE, GET, HEAD, LOCK, MKCALENDAR, MKCOL, MOVE, OPTIONS, POST, PROPFIND, PROPPATCH, PUT, REPORT, SEARCH, UNCHECKOUT, UNLOCK, UPDATE, VERSION-CONTROL");
//        httpServletResponse.addHeader("Access-Control-Allow-Headers", "Overwrite, Destination, Content-Type, Depth, User-Agent, Translate, Range, Content-Range, Timeout, X-File-Size, X-Requested-With, If-Modified-Since, X-File-Name, Cache-Control, Location, Lock-Token, If");
//        httpServletResponse.addHeader("Access-Control-Expose-Headers", "DAV, content-length, Allow");

        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * Take this filter out of service.
     */
    public void destroy() {
    }
}
