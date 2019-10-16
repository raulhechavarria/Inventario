package org.archetype.common.web.utils;

/**
 * @author Axel Mendoza Pupo
 */
import java.util.Date;

import org.archetype.common.domain.ExtraSessionInformation;
import org.archetype.common.domain.RegistrySessionImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.concurrent.SessionInformation;
import org.springframework.util.Assert;

public class ActualizarSessionInformationImpl implements InitializingBean{

	private RegistrySessionImpl sessiones;
	private long timeToExpire = 30000;
	private int sessionTimeout = 1800000;

	public RegistrySessionImpl getSessiones() {
		return sessiones;
	}

	public void setSessiones(RegistrySessionImpl sessiones) {
		this.sessiones = sessiones;
	}

	public void setSessionTimeout(int timeout) {
		this.sessionTimeout = timeout * 1000;
		sessiones.setMaxTimeOut(sessionTimeout);
	}
	
	public long getTimeToExpire() {
		Date now = new Date();
		return now.getTime() - sessionTimeout + timeToExpire;
	}

	public void setTimeToExpire(long timeToExpire) {
		this.timeToExpire = timeToExpire * 1000;		
	}

	public void open(String sessionId) {
		sessiones.refreshLastRequest(sessionId);
		ExtraSessionInformation ext = (ExtraSessionInformation)sessiones.getExtraSessionInformation(sessionId);
		if(ext != null){
			/*if(ext.isCloseSend()){
				sessiones.open(ext.getExtraID());
				ext.setCloseSend(false);
			}*/
			ext.setBrowserClosed(false);
		}
	}

	public void close(String sessionId) {		
		SessionInformation info = sessiones.getSessionInformation(sessionId);
		if(info != null){
			info.getLastRequest().setTime(getTimeToExpire());
			ExtraSessionInformation ext = (ExtraSessionInformation)sessiones.getExtraSessionInformation(sessionId);
			if(ext != null){
				ext.setBrowserClosed(true);
			}
		}
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(sessiones,"sessiones must be set");		
	}



}
