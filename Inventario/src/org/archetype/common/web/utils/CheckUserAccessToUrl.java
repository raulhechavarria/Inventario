package org.archetype.common.web.utils;


/**
 * @author Axel Mendoza Pupo
 */

import org.springframework.security.AccessDecisionManager;
import org.springframework.security.AccessDeniedException;
import org.springframework.security.Authentication;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.context.SecurityContextHolder;

public class CheckUserAccessToUrl {
	
	private AccessDecisionManager accessDecisionManager;
	private AccessDefinitionSource objectDefinitionSource;
	
	public boolean hasAccess(String url){

		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			
			ConfigAttributeDefinition attributeDefinition = objectDefinitionSource.lookupAttributes(url); 
						
			if (attributeDefinition == null)
				return true;
			if (authentication != null) {
				accessDecisionManager.decide(authentication, null, attributeDefinition);
			} else
				return false;
		} catch (AccessDeniedException e) {
			return false;
		}

		return true;
	}

	public void setAccessDecisionManager(AccessDecisionManager accessDecisionManager) {
		this.accessDecisionManager = accessDecisionManager;
	}

	public void setObjectDefinitionSource(
			AccessDefinitionSource objectDefinitionSource) {
		this.objectDefinitionSource = objectDefinitionSource;
	}
}
