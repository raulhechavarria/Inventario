package org.archetype.common.web.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.archetype.common.domain.LogoutEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.event.authentication.LoggerListener;
import org.springframework.util.ClassUtils;

public class LogoutLoggerListener extends LoggerListener{
	private static final Log logger = LogFactory.getLog(LoggerListener.class);
	
	public void onApplicationEvent(ApplicationEvent event) {
		if(event instanceof LogoutEvent){
			String message = "Authentication event " + ClassUtils.getShortName(event.getClass()) + ": Session Id" + ((LogoutEvent)event).getSource();
			logger.warn(message);
		}
		super.onApplicationEvent(event);
	}
}
