package org.archetype.common.domain;

import org.springframework.context.ApplicationEvent;
import org.springframework.security.GrantedAuthority;

public class GrantedAuthorityDeletedEvent extends ApplicationEvent{

	private static final long serialVersionUID = 1L;

	public GrantedAuthorityDeletedEvent(GrantedAuthority grantedAuthority) {
		super(grantedAuthority);
	}
}
