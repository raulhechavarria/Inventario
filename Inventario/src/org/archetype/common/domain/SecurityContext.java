package org.archetype.common.domain;

import org.springframework.security.context.SecurityContextImpl;

public class SecurityContext extends SecurityContextImpl{
	
	private static final long serialVersionUID = 1L;
	
	private boolean activeSession = false;
	private boolean sessionMultiple = false;
	private Usuario usuario;
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	private int failureAuthenticationCount = 0;	
		
	public boolean isActiveSession() {
		return activeSession;
	}
	public void setActiveSession(boolean activeSession) {
		this.activeSession = activeSession;
	}
	public boolean isSessionMultiple() {
		return sessionMultiple;
	}
	public void setSessionMultiple(boolean sessionMultiple) {
		this.sessionMultiple = sessionMultiple;
	}
	public int getFailureAuthenticationCount() {
		return failureAuthenticationCount;
	}
	public void setFailureAuthenticationCount(int failureAuthenticationCount) {
		this.failureAuthenticationCount = failureAuthenticationCount;
	}
	
}
