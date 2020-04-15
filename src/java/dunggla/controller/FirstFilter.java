/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dunggla.controller;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
public class FirstFilter implements Filter {

    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    private final List<String> member;
    private final List<String> admin;
    private final List<String> guest;

    public FirstFilter() {
        guest = new ArrayList<>();
        guest.add("");
        guest.add("showAll");
        guest.add("search");
        guest.add("login");
        guest.add("showDetail");
        guest.add("login.jsp");
        guest.add("homePage.jsp");
        guest.add("error.jsp");
        guest.add("pageNotFound.html");
        guest.add("suggessPopular");

        admin = new ArrayList<>();
        admin.add("");
        admin.add("addNewItem");
        admin.add("deleteMultiRow");
        admin.add("deleteItem");
        admin.add("logout");
        admin.add("search");
        admin.add("showAll");
        admin.add("showDetail");
        admin.add("updateDetail");
        admin.add("updateMultiRow");
        admin.add("addItems.jsp");
        admin.add("admin.jsp");
        admin.add("error.jsp");
        admin.add("updateDetailItems.jsp");
        admin.add("pageNotFound.html");

        member = new ArrayList<>();
        member.add("");
        member.add("search");
        member.add("showAll");
        member.add("addToCart");
        member.add("logout");
        member.add("showDetail");
        member.add("updateCart");
        member.add("removeCart");
        member.add("homePage.jsp");
        member.add("error.jsp");
        member.add("pageNotFound.html");
        member.add("viewCart.jsp");
        member.add("multiProcess");
        member.add("confirmCart");
        member.add("shoppingHistory.jsp");
        member.add("searchHistory");
        member.add("showAllHistory");
        member.add("billDetail.jsp");
        member.add("billDetail.jsp");
        member.add("showBillDetail");
        member.add("searchDetailInBill");
        member.add("suggessPopular");

    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("FirstFilter:DoBeforeProcessing");
        }

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log items on the request object,
        // such as the parameters.
        /*
	for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    String values[] = request.getParameterValues(name);
	    int n = values.length;
	    StringBuffer buf = new StringBuffer();
	    buf.append(name);
	    buf.append("=");
	    for(int i=0; i < n; i++) {
	        buf.append(values[i]);
	        if (i < n-1)
	            buf.append(",");
	    }
	    log(buf.toString());
	}
         */
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("FirstFilter:DoAfterProcessing");
        }

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
        /*
	for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    Object value = request.getAttribute(name);
	    log("attribute: " + name + "=" + value.toString());

	}
         */
        // For example, a filter might append something to the response.
        /*
	PrintWriter respOut = new PrintWriter(response.getWriter());
	respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        if (debug) {
            log("FirstFilter:doFilter()");
        }

        doBeforeProcessing(request, response);

        Throwable problem = null;
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            String uri = req.getRequestURI();

            if (uri.contains(";jsessionid=")) {
                int firstIndexJsession = uri.lastIndexOf(";");
                uri = uri.substring(0, firstIndexJsession);
            }
            if (uri.contains(".png") || uri.contains(".jpg")) {
                chain.doFilter(request, response);
            } else {
                int index = uri.lastIndexOf("/");
                String resource = uri.substring(index + 1);

                HttpSession session = req.getSession(false);
                if (session == null || session.getAttribute("ROLE") == null) {
                    if (guest.contains(resource)) {
                        chain.doFilter(request, response);
                    } else {
                        res.sendRedirect("pageNotFound.html");
                    }
                } else {
                    String role = (String) session.getAttribute("ROLE");
                    if (role.equals("admin") && admin.contains(resource)) {
                        chain.doFilter(request, response);
                    } else if (role.equals("member") && member.contains(resource)) {
                        chain.doFilter(request, response);
                    } else {
                        res.sendRedirect("pageNotFound.html");
                    }
                }
            }
        } catch (Throwable t) {
            // If an exception is thrown somewhere down the filter chain,
            // we still want to execute our after processing, and then
            // rethrow the problem after that.
            problem = t;
            log("FirstFilter_Throwable " + t.getMessage());
        }

        doAfterProcessing(request, response);

        // If there was a problem, we want to rethrow it if it is
        // a known type, otherwise log it.
        if (problem != null) {
            if (problem instanceof ServletException) {
                throw (ServletException) problem;
            }
            if (problem instanceof IOException) {
                throw (IOException) problem;
            }
            sendProcessingError(problem, response);
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("FirstFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("FirstFilter()");
        }
        StringBuffer sb = new StringBuffer("FirstFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
