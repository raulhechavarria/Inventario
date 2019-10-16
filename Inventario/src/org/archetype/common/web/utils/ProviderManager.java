package org.archetype.common.web.utils;

import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.BadCredentialsException;
import org.springframework.security.concurrent.ConcurrentLoginException;
import org.springframework.security.concurrent.SessionAlreadyUsedException;

public class ProviderManager extends org.springframework.security.providers.ProviderManager {
	@Override
	public Authentication doAuthentication(Authentication authentication)
			throws AuthenticationException {
		Authentication result = null;
		try{
			result = super.doAuthentication(authentication);
		}
		catch (AuthenticationException ae) {                        
            if(ae instanceof ConcurrentLoginException){
            	
            }
            else
            	if(ae instanceof SessionAlreadyUsedException){
            		
            	}
            	else
            		if(ae instanceof BadCredentialsException){
            			
            		}
            		
            
            throw ae;
        }
		 return result;
	}
}
