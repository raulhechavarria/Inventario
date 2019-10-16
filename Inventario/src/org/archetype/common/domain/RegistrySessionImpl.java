package org.archetype.common.domain;

/**
 * @author Axel Mendoza Pupo
 */

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.security.concurrent.SessionInformation;
import org.springframework.security.concurrent.SessionRegistryImpl;
import org.springframework.security.ui.session.HttpSessionDestroyedEvent;

public class RegistrySessionImpl extends SessionRegistryImpl implements Runnable, ApplicationEventPublisherAware, InitializingBean{
	
	private ApplicationEventPublisher applicationEventPublisher;
	private Thread t=null;
	private HashMap<String,ExtraSessionInformation> extra = new HashMap<String,ExtraSessionInformation>();
    private long maxTimeOut = 1800000;
	
	public void run() {
		while(true){
			Object principals[]= getAllPrincipals();

			for(int i=0;i<principals.length;i++){
				SessionInformation userSessions[] = getAllSessions((String)principals[i], true);        		
				if ( userSessions != null ) {
					Date now = new Date();
					for (int j = 0; j < userSessions.length; j++){
						if(	expireSession(userSessions[j],now) == true){	   
							if (applicationEventPublisher != null) {
					            applicationEventPublisher.publishEvent(new LogoutEvent(userSessions[j].getSessionId()));
					        }
						}
					}
				}
			}
			try {Thread.sleep(5000);}
			catch (Exception e) {}
		}
	}
	
	public void onApplicationEvent(ApplicationEvent event) {
		if(event instanceof LogoutEvent){
			String sessionId = ((String)event.getSource());	        		
			ExtraSessionInformation inf=getExtraSessionInformation(sessionId);
	   		
    		if(inf != null){
    			removeExtraSessionInformation(sessionId);
				getSessionInformation(sessionId).expireNow();
		   	}		    	
		}
		else {
			if(event instanceof HttpSessionDestroyedEvent){
				String sessionId = ((HttpSession) event.getSource()).getId();
				removeExtraSessionInformation(sessionId);
				super.onApplicationEvent(event);
			}
		}
	}
			
	public ExtraSessionInformation getExtraSessionInformation(String sessionId){
		return extra.get(sessionId);
	}
	
	public void setExtraSessionInformation(String sessionId,ExtraSessionInformation inf){
		extra.put(sessionId, inf);
	}
	
	public void removeExtraSessionInformation(String sessionId) {
		ExtraSessionInformation info = getExtraSessionInformation(sessionId);
		if (info != null) {
		extra.remove(sessionId);
		}
	}
	
	private boolean expireSession(SessionInformation id,Date now) {

		if(id.isExpired() && getExtraSessionInformation(id.getSessionId()) != null ){
			return true;
		}
		else
		if(!id.isExpired()){
			
		long difMilis = now.getTime() - id.getLastRequest().getTime();
		
		if(difMilis >= maxTimeOut){
			id.expireNow();
			return true;
		}
		}
		return false;
	}

	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;		
	}

	public boolean disponibilidadIP(String ip){
		Iterator<ExtraSessionInformation> e = extra.values().iterator();
		while(e.hasNext()){
			ExtraSessionInformation	next = e.next();
			if(next.getIpAddress().equals(ip))
				return false;
		}		
		return true;
	}

	public void afterPropertiesSet() throws Exception {
		if( t == null ){
			t = new Thread(this);		
			t.start();
		}
	}

	public long getMaxTimeOut() {
		return maxTimeOut;
	}

	public void setMaxTimeOut(long maxTimeOut) {
		this.maxTimeOut = maxTimeOut;
	}
}
