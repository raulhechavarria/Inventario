package org.archetype.common.domain;


/**
 * @author Axel Mendoza Pupo
 */

public class ExtraSessionInformation {
	private String ipAddress;
	private String extraID;	
	private boolean browserClosed = false;
	private boolean closeSend = false;
	
	public boolean isCloseSend() {
		return closeSend;
	}

	public void setCloseSend(boolean closeSend) {
		this.closeSend = closeSend;
	}

	public ExtraSessionInformation(String ipAddress) {		
		this.ipAddress = ipAddress;		
	}

	public String getExtraID() {
		return extraID;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public void setExtraID(String extraID) {
		this.extraID = extraID;
	}

	public boolean isBrowserClosed() {
		return browserClosed;
	}

	public void setBrowserClosed(boolean browserClosed) {
		this.browserClosed = browserClosed;
	}	
}
