package alfredeperjesi.spike.gwt.server.infrastructure.integration.filter;

/**
 * Created by Alfréd on 19/04/14.
 */

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/AuthenticationFilter")
public class AuthenticationFilter implements Filter {

    private ServletContext context;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.context = filterConfig.getServletContext();
        this.context.log("AuthenticationFilter initialized");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        this.context.log("Requested Resource::" + uri);

        HttpSession session = req.getSession(false);
        System.out.println(req.getRequestURI());
        if (session == null && isAuthenticableUri(uri)) {
            this.context.log("Unauthorized access request");
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            // pass the request along the filter chain
            chain.doFilter(request, response);
        }


    }

    private boolean isAuthenticableUri(String uri) {
        return !uri.endsWith("auth") && !uri.endsWith("log") && uri.contains("rest");
    }


    public void destroy() {
        //close any resources here
    }

}