package org.archetype.common.web.utils;

/**
 * @author Axel Mendoza Pupo
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.Authentication;
import org.springframework.security.concurrent.SessionInformation;
import org.springframework.security.concurrent.SessionRegistry;
import org.springframework.security.ui.logout.LogoutHandler;

public class AdminLogoutHandler implements LogoutHandler{

	public static final String ACEGI_SECURITY_FORM_USERNAME_DESLOG_KEY = "j_deslog";
	
	private SessionRegistry sessiones;
		
	public void setSessiones(SessionRegistry sessiones) {
		this.sessiones = sessiones;
	}

	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
				
			String username = request.getParameter(ACEGI_SECURITY_FORM_USERNAME_DESLOG_KEY);
			SessionInformation sessions[] = sessiones.getAllSessions(username, false);
			if(sessions!=null){
				for (int i = 0; i < sessions.length; i++){
					sessions[i].expireNow();
				}
			}				
	}	
}
