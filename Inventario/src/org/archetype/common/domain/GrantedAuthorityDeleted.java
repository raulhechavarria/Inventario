package org.archetype.common.domain;

import org.springframework.security.GrantedAuthority;

public class GrantedAuthorityDeleted implements GrantedAuthority{

	private static final long serialVersionUID = 1L;

	public String getAuthority() {
		return null;
	}

	public int compareTo(Object o) {
		return 0;
	}

}
