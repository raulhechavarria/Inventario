package org.archetype.common.web.utils;

/**
 * @author Axel Mendoza Pupo
 */
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.transaction.CannotCreateTransactionException;

public class JDBCConnectionDownFilter implements Filter{

	private String lostDB =  "./common/error.htm";
	
    public String getLostDB() {
		return lostDB;
	}

	public void setLostDB(String lostDB) {
		this.lostDB = lostDB;
	}

	public void destroy() {}
    
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException {
   
		if (!(request instanceof HttpServletRequest)) {
		    throw new ServletException("HttpServletRequest required");
		}
		
		if (!(response instanceof HttpServletResponse)) {
		    throw new ServletException("HttpServletResponse required");
		}
		
		try {
			chain.doFilter(request, response);
		} catch (CannotCreateTransactionException ex) {
		    ((HttpServletResponse)response).sendRedirect(lostDB);
		} catch (DataAccessResourceFailureException ex) {
		    ((HttpServletResponse)response).sendRedirect(lostDB);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {}
}
