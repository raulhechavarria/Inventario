package org.archetype.common.web.utils;

/**
 * @author Axel Mendoza Pupo
 */
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


public class CheckLeavePageServletClient extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ActualizarSessionInformationImpl updater;
	private String openUrl = "/Open"; 
	private String closeUrl = "/Close";
	private boolean init = false;
	
	protected ApplicationContext getContext(ServletContext servletContext)
	{
		return WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
	}
	
	public void setUpdater(ActualizarSessionInformationImpl updater) {
		this.updater = updater;
	}

	public  void doGet(HttpServletRequest request, HttpServletResponse  response)
    throws IOException, ServletException {
		
		String uri = request.getRequestURI();
				
	    // strip everything after the first semi-colon
	    int pathParamIndex = uri.indexOf(';');
	    if( pathParamIndex > 0 )
	    {
	      uri = uri.substring(0, pathParamIndex);
	    }
	    
	    if(!init){			
			updater.setSessionTimeout(request.getSession().getMaxInactiveInterval());
			init = true;
		}
	    
	    if(uri.endsWith(openUrl)){
	    	String sessionId = request.getSession().getId();
	    	updater.open(sessionId);    	
	    }else
	    	if(uri.endsWith(closeUrl)){
	    		String sessionId = request.getSession().getId();
	    		updater.close(sessionId);
	    	}
	    return;
	}

	public  void doPost(HttpServletRequest request, HttpServletResponse  response)
    throws IOException, ServletException {

    doGet(request, response);
    return;
    }

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		
		final String targetBean = servletConfig.getInitParameter("targetBean");
	    final ApplicationContext ctx = getContext(servletConfig.getServletContext());

	    if(targetBean == null || !ctx.containsBean(targetBean)) {
	      throw new ServletException("targetBean '" + targetBean + "' not found in context.");
	    }
	    
	    updater = (ActualizarSessionInformationImpl)ctx.getBean(targetBean);
		
	}

}

