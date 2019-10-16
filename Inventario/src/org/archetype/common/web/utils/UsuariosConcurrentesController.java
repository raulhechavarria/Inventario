package org.archetype.common.web.utils;

/**
 * @author Axel Mendoza Pupo
 */
import java.net.InetAddress;

import org.archetype.common.domain.ExtraSessionInformation;
import org.archetype.common.domain.RegistrySessionImpl;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.SpringSecurityMessageSource;
import org.springframework.security.concurrent.ConcurrentLoginException;
import org.springframework.security.concurrent.ConcurrentSessionController;
import org.springframework.security.concurrent.SessionAlreadyUsedException;
import org.springframework.security.concurrent.SessionInformation;
import org.springframework.security.concurrent.SessionRegistryUtils;
import org.springframework.security.ui.WebAuthenticationDetails;

public class UsuariosConcurrentesController implements ConcurrentSessionController{
	
 	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private RegistrySessionImpl sessionRegistry = new RegistrySessionImpl();
	
	public void checkAuthenticationAllowed(Authentication request)
			throws AuthenticationException {
			
		Object principal = SessionRegistryUtils.obtainPrincipalFromAuthentication(request);
		String sessionId = SessionRegistryUtils.obtainSessionIdFromAuthentication(request);
		
		SessionInformation sessions[] =	sessionRegistry.getAllSessions(principal, Boolean.FALSE);
		String ipAddress = ((WebAuthenticationDetails)request.getDetails()).getRemoteAddress();
		boolean permited = false;
		
		if(ipAddress.equals("127.0.0.1")){               				
			try {
				ipAddress = InetAddress.getLocalHost().getHostAddress();
			}catch (Exception e){									
			}
		}
		String idBD = null;
		
		if(sessions == null || sessions.length == 0){			
			if(!sessionRegistry.disponibilidadIP(ipAddress)){
				throw new ConcurrentLoginException("Ya existe una sesion abierta en este equipo");
			}
	    }		
		else{
			for(int i=0;i<sessions.length;i++)
				if( sessionRegistry.getExtraSessionInformation(sessions[i].getSessionId()).getIpAddress().equals(ipAddress) && sessions[i].getPrincipal().equals(principal) && idBD == null){
					//idBD = ((ExtraSessionInformation)sessionRegistry.getExtraSessionInformation(sessions[i].getSessionId())).getExtraID();
					sessionRegistry.setExtraSessionInformation(sessionId, new ExtraSessionInformation(ipAddress));
					permited = true;
					i=sessions.length;
				}
		}
		
		if(sessions != null && sessions.length != 0 && permited == false/*&& idBD == null*/){
			throw new SessionAlreadyUsedException("Session en uso");			
		}	
	}
	
	public void registerSuccessfulAuthentication(Authentication request) {
	
        Object principal = SessionRegistryUtils.obtainPrincipalFromAuthentication(request);
        String sessionId = SessionRegistryUtils.obtainSessionIdFromAuthentication(request);
                       
        ExtraSessionInformation inf =(ExtraSessionInformation)sessionRegistry.getExtraSessionInformation(sessionId);
        if(inf == null){
        	String ipAddress = ((WebAuthenticationDetails)request.getDetails()).getRemoteAddress();
        	if(ipAddress.equals("127.0.0.1")){               				
    			try {
    				ipAddress = InetAddress.getLocalHost().getHostAddress();
    			}catch (Exception e){									
    			}
    		}
        	
        	/*String idBD = servicio.abrirSession((String)principal);
        	ExtraSessionInformation neo = new ExtraSessionInformation(ipAddress);
        	neo.setExtraID(null);
        	sessionRegistry.setExtraSessionInformation(sessionId, neo);*/
        }
        
        sessionRegistry.registerNewSession(sessionId, principal);
	}

	public void setSessionRegistry(RegistrySessionImpl sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}
}
