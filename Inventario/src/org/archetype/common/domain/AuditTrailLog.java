package org.archetype.common.domain;

import java.io.Serializable;
import java.util.Date;

public class AuditTrailLog extends PersistentObject implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String principal;
    private String resourceOperatedUpon;
    private String actionPerformed;
    private Date whenActionWasPerformed;
    private String clientIpAddress;
    private String serverIpAddress;
    
    public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public String getResourceOperatedUpon() {
		return resourceOperatedUpon;
	}
	public void setResourceOperatedUpon(String resourceOperatedUpon) {
		this.resourceOperatedUpon = resourceOperatedUpon;
	}
	public String getActionPerformed() {
		return actionPerformed;
	}
	public void setActionPerformed(String actionPerformed) {
		this.actionPerformed = actionPerformed;
	}
	public Date getWhenActionWasPerformed() {
		return whenActionWasPerformed;
	}
	public void setWhenActionWasPerformed(Date whenActionWasPerformed) {
		this.whenActionWasPerformed = whenActionWasPerformed;
	}
	public String getClientIpAddress() {
		return clientIpAddress;
	}
	public void setClientIpAddress(String clientIpAddress) {
		this.clientIpAddress = clientIpAddress;
	}
	public String getServerIpAddress() {
		return serverIpAddress;
	}
	public void setServerIpAddress(String serverIpAddress) {
		this.serverIpAddress = serverIpAddress;
	}
}
