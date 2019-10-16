package org.archetype.common.web.controllers;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.archetype.common.domain.ExtGridRequest;
import org.archetype.common.domain.LogoutEvent;
import org.archetype.common.web.utils.ActualizarSessionInformationImpl;
import org.archetype.common.web.utils.ArcheTypeUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.security.AuthenticationException;
import org.springframework.security.BadCredentialsException;
import org.springframework.security.DisabledException;
import org.springframework.security.SpringSecurityException;
import org.springframework.security.concurrent.ConcurrentLoginException;
import org.springframework.security.concurrent.SessionAlreadyUsedException;
import org.springframework.security.concurrent.SessionInformation;
import org.springframework.security.concurrent.SessionRegistry;
import org.springframework.security.ui.AbstractProcessingFilter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.view.RedirectView;

public class SecurityController extends MultiActionController implements ApplicationEventPublisherAware{

	private String listarView;
	private String loginView;
	private String logoutView;
	private String defaultUrl;
	private SessionRegistry sessionRegistry;
	private ActualizarSessionInformationImpl updater;
	public void setUpdater(ActualizarSessionInformationImpl updater) {
		this.updater = updater;
	}
	private ApplicationEventPublisher applicationEventPublisher;
	
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response){
		
		Map<String, Object> datos = new HashMap<String, Object>();

		if (sessionRegistry.getSessionInformation(request.getSession().getId()) != null) {
			return new ModelAndView(new RedirectView(defaultUrl));
		} else {
			SpringSecurityException lastexception = (SpringSecurityException) request.getSession().getAttribute(AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY);
			
			boolean sessionActiva = false;
			boolean loginError = false;
			boolean sessionMultiple = false;
			boolean accountDisabled = false;
			accountDisabled = lastexception instanceof DisabledException;
			if (!accountDisabled) {
				sessionActiva = lastexception instanceof SessionAlreadyUsedException;
				if (!sessionActiva) {
					sessionMultiple = lastexception instanceof ConcurrentLoginException;
					if (!sessionMultiple) {					
						loginError = lastexception instanceof BadCredentialsException;
					}
				}			
			}
			
			datos.put("sessionActiva", sessionActiva);
			datos.put("loginError", loginError);
			datos.put("sessionMultiple", sessionMultiple);
			datos.put("accountDisabled", accountDisabled);
			
			if(lastexception instanceof AuthenticationException){
				datos.put("user", ((AuthenticationException)lastexception).getAuthentication().getPrincipal().toString());
			}
			return new ModelAndView(loginView, "errors", datos);			
		}
	}
	
	public ModelAndView logout(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		applicationEventPublisher.publishEvent(new LogoutEvent(request.getSession().getId()));
		
		request.getSession().invalidate();
		ModelAndView model = new ModelAndView(new RedirectView(logoutView));
		return model;
	}

	public ModelAndView mostrarLista(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(listarView);
	}
	
	public ModelAndView listar(HttpServletRequest request,
			HttpServletResponse response, ExtGridRequest egr) throws Exception {
		
		Object principals[] = sessionRegistry.getAllPrincipals();
		
		JSONObject json = new JSONObject();
		JSONArray data = new JSONArray();
		
		for (int i = egr.getStart(); i < egr.getLimit() && i < principals.length; i++) {
			SessionInformation []sessions = sessionRegistry.getAllSessions(principals[i], false);
			if(sessions != null && sessions.length != 0){
				SessionInformation session = sessions[0]; 
				JSONObject obj = new JSONObject();	
				
				obj.put("idsession",session.getSessionId());
				obj.put("nombre",session.getPrincipal());
				obj.put("lastRequest",session.getLastRequest());
							
				data.put(obj);
			}
		}
		
		json.put("totalCount", principals.length);
		json.put("data", data);
			
		ModelAndView mv = new ModelAndView("common/json");
		mv.addObject("json",json.toString());		
		return mv;
	}
	
	public ModelAndView desconectarSecurity(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject resp;
		String sessionId = request.getParameter("id");
		
		if(sessionId.isEmpty()){			
			resp = ArcheTypeUtils.getFailure(null);
			ModelAndView mv = new ModelAndView("common/json");
			mv.addObject("json",resp.toString());		
			return mv;
		}
		
		sessionRegistry.getSessionInformation(sessionId).expireNow();
		resp = ArcheTypeUtils.getSuccess();
		
		ModelAndView mv = new ModelAndView("common/json");
		mv.addObject("json",resp.toString());
		return mv;
	}
	@SuppressWarnings("all")
	public void init() {
		Enumeration enumer = getServletContext().getInitParameterNames();
		while (enumer.hasMoreElements()) {
			Object object = (Object) enumer.nextElement();
			String txt = object.toString();
			
		}
		
		//updater.setSessionTimeout(request.getSession().getMaxInactiveInterval());
	}
	
	public ModelAndView checkOpen(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String sessionId = request.getSession().getId();
    	updater.open(sessionId);
    	
    	JSONObject resp = ArcheTypeUtils.getSuccess();
    	ModelAndView mv = new ModelAndView("common/json");
		mv.addObject("json",resp.toString());		
		return mv;
	}
	
	public ModelAndView checkClose(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String sessionId = request.getSession().getId();
		updater.close(sessionId);
    	
    	JSONObject resp = ArcheTypeUtils.getSuccess();
    	ModelAndView mv = new ModelAndView("common/json");
		mv.addObject("json",resp.toString());		
		return mv;
	}
	
	public void setListarView(String listarView) {
		this.listarView = listarView;
	}
	public void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}
	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}
	public void setLoginView(String loginView) {
		this.loginView = loginView;
	}
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher; 
	}	
	public void setLogoutView(String logoutView) {
		this.logoutView = logoutView;
	}

}
