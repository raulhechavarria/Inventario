package org.archetype.common.web.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.archetype.common.domain.GrantedAuthorityDeleted;
import org.archetype.common.domain.GrantedAuthorityDeletedEvent;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.concurrent.SessionInformation;
import org.springframework.security.concurrent.SessionRegistry;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.ui.SpringSecurityFilter;
import org.springframework.security.ui.session.HttpSessionDestroyedEvent;
import org.springframework.util.Assert;

/**
 * @author Axel Mendoza Pupo 
 */
public class GrantedAuthorityUpdatesFilter extends SpringSecurityFilter implements ApplicationListener, InitializingBean{

	private SessionRegistry sessionRegistry;
	private Map<GrantedAuthority, Set<String>> sessionsPerAuthorithyToDelete = new HashMap<GrantedAuthority, Set<String>>(); 
	
	/**
	 * listen for two kind of events
	 * GrantedAuthorityDeletedEvent to create a Set of sessions that need to check for GrantedAuthorities updates
	 * HttpSessionDestroyedEvent to call the {@link GrantedAuthorityUpdatesFilter.purgeSession()} with the session id that has been destroyed 
	 */
	public void onApplicationEvent(ApplicationEvent event) {
		if(event instanceof GrantedAuthorityDeletedEvent){
			GrantedAuthority grantedAuthority = (GrantedAuthority)event.getSource();
			Set<String> sessionIds = new HashSet<String>();
			Object[] principals = sessionRegistry.getAllPrincipals();
			
			for (int i = 0; i < principals.length; i++) {
				SessionInformation[] sessionInformations = sessionRegistry.getAllSessions(principals[i], false);
				
				for (int j = 0; j < sessionInformations.length; j++) {
					sessionIds.add(sessionInformations[j].getSessionId());
				}
			}			
			sessionsPerAuthorithyToDelete.put(grantedAuthority, sessionIds);
		}else{
			if(event instanceof HttpSessionDestroyedEvent){
				String sessionId = ((HttpSession) event.getSource()).getId();
				purgeSession(sessionId);
			}
		}
	}
	
	/**
	 * Check if the GrantedAuthorities of the authenticated users has been scheduled for updates
	 */
	public void doFilterHttp(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String sessionId = request.getSession().getId();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth != null){
			GrantedAuthority[] grantedAuthorities = auth.getAuthorities();
			boolean sessionUpdated = false; 
			
			for (int i = 0; i < grantedAuthorities.length; i++) {
				if(sessionsPerAuthorithyToDelete.containsKey(grantedAuthorities[i])){
					grantedAuthorities[i]= new GrantedAuthorityDeleted();
					sessionUpdated = true;
				}
			}
			
			if(sessionUpdated){
				purgeSession(sessionId);			
			}
		}
		chain.doFilter(request, response);
	}

	/**
	 * remove the session from the Set of sessions that remains without the GrantedAuthorities update
	 * to delete the entry when there no remains sessions without the GrantedAuthorities update 
	 */
	protected void purgeSession(String sessionId){

		for (Entry<GrantedAuthority, Set<String>> entry : this.sessionsPerAuthorithyToDelete.entrySet()) {
			if(entry.getValue().contains(sessionId)){
				entry.getValue().remove(sessionId);
				if(entry.getValue().isEmpty()){
					sessionsPerAuthorithyToDelete.remove(entry.getKey());
				}
			}
		}
	}
	
	public int getOrder() {
		return 2;
	}

	public void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(sessionRegistry,"sessionRegistry must be set");		
	}	
}
