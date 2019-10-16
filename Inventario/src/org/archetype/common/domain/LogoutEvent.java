package org.archetype.common.domain;

/**
 * @author Axel Mendoza Pupo
 */
import org.springframework.context.ApplicationEvent;

public class LogoutEvent extends ApplicationEvent{
 
	private static final long serialVersionUID = 1L;

    public LogoutEvent(String sessionId) {
        super(sessionId);
    }
}
