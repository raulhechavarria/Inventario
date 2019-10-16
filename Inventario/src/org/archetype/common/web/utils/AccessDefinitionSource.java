package org.archetype.common.web.utils;

/**
 * @author Axel Mendoza Pupo
 */

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.archetype.common.business.IAccionService;
import org.archetype.common.business.ICommonService;
import org.archetype.common.domain.Accion;
import org.archetype.common.domain.BaseSearchResult;
import org.archetype.common.domain.ExtGridRequest;
import org.archetype.common.domain.Rol;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.intercept.web.DefaultFilterInvocationDefinitionSource;
import org.springframework.security.intercept.web.FilterInvocationDefinitionSource;
import org.springframework.security.intercept.web.RequestKey;
import org.springframework.security.util.AntUrlPathMatcher;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.mvc.multiaction.InternalPathMethodNameResolver;
import org.springframework.web.servlet.mvc.multiaction.MethodNameResolver;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.mvc.multiaction.ParameterMethodNameResolver;
import org.springframework.web.servlet.mvc.multiaction.PropertiesMethodNameResolver;

@SuppressWarnings("all")
public class AccessDefinitionSource implements ApplicationContextAware, FilterInvocationDefinitionSource, InitializingBean{
	
	private FilterInvocationDefinitionSource objectDefinitionSource;
	private ICommonService servicio;
	private LinkedHashMap<RequestKey,ConfigAttributeDefinition> source = new LinkedHashMap<RequestKey,ConfigAttributeDefinition>();
	private ApplicationContext context;
	
	public void afterPropertiesSet() throws Exception {

		Set<Accion> urlPaths = new HashSet<Accion>();
		
		Collection handlerMappings;
		Map matchingBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(
				context, HandlerMapping.class, true, false);
		if (!matchingBeans.isEmpty()) {
			handlerMappings = matchingBeans.values();
			for (Iterator iter = handlerMappings.iterator(); iter.hasNext();) {
				HandlerMapping handler = (HandlerMapping) iter.next();
				if(handler instanceof SimpleUrlHandlerMapping){
					SimpleUrlHandlerMapping simpleHandler = (SimpleUrlHandlerMapping)handler;
					Map urlmap = simpleHandler.getUrlMap();
					Iterator<String> urliter = urlmap.keySet().iterator();
					while(urliter.hasNext()){
						String url = urliter.next();
						if(url.equals("/common/login.htm") == false || 
								url.equals("/common/logout.htm") == false || 
								url.equals("/common/checkOpenSecurity.htm") == false ||
								url.equals("/common/checkCloseSecurity.htm") == false ||
								url.equals("/common/accessDenied.htm") == false){
						
							Accion padre = new Accion();
							padre.setNombre(url);
							padre.setRoles(null);
							
							String controller = (String)urlmap.get(url);
							Object controllerBean = context.getBean(controller);
							if(controllerBean instanceof MultiActionController){
								MultiActionController multiController = (MultiActionController)controllerBean;
								MethodNameResolver methodResolver = multiController.getMethodNameResolver();
								if(methodResolver instanceof InternalPathMethodNameResolver){
									InternalPathMethodNameResolver resolver = (InternalPathMethodNameResolver)methodResolver;
									
								} else if(methodResolver instanceof ParameterMethodNameResolver){
									ParameterMethodNameResolver resolver = (ParameterMethodNameResolver)methodResolver;
									
								} else if(methodResolver instanceof PropertiesMethodNameResolver){
									PropertiesMethodNameResolver resolver = (PropertiesMethodNameResolver)methodResolver;
									Field field = resolver.getClass().getDeclaredField("mappings");
									field.setAccessible(true);
									Properties mappings = (Properties)field.get(resolver);
									
									/*Set hijos = new HashSet<Accion>();
									padre.setHijos(hijos);
									
									for (Iterator iterator = mappings.keySet().iterator(); iterator.hasNext();) {
										String key = (String) iterator.next();
										if(key.equals("/common/login.htm") == false && 
												key.equals("/common/logout.htm") == false && 
												key.equals("/common/checkOpenSecurity.htm") == false &&
												key.equals("/common/checkCloseSecurity.htm") == false &&
												key.equals("/common/accessDenied.htm") == false){
											Accion hija = new Accion();
											hija.setNombre(key);
											hija.setRoles(null);
											hija.setPadre(padre);
											hijos.add(hija);
										}
									}
									
									urlPaths.add(padre);*/
								}
							}
						}
					}
				}
			}
		}
	
		((IAccionService)servicio).adicionarAcciones(urlPaths);
		
		ExtGridRequest egr = new ExtGridRequest();
		egr.setLimit(0);
		BaseSearchResult bsr = servicio.listar(egr);
		
		for (Iterator iterator = bsr.getResults().iterator(); iterator.hasNext();) {
			Accion item = (Accion) iterator.next();
			RequestKey key = new RequestKey(item.getNombre());
			String[] svalue = new String[item.getRoles().size()];
			int i = 0;
			
			for (Iterator iterator2 = item.getRoles().iterator(); iterator2.hasNext();i++) {
				Rol rol = (Rol) iterator2.next();
				svalue[i] = rol.getNombre();
			}
			ConfigAttributeDefinition def = new ConfigAttributeDefinition(svalue);
			source.put(key, def);
			
		}
		
		DefaultFilterInvocationDefinitionSource definitionSource = new DefaultFilterInvocationDefinitionSource(new AntUrlPathMatcher(),source);
		definitionSource.setStripQueryStringFromUrls(true);
		setObjectDefinitionSource(definitionSource);
	}
	
	public void reloadConfig(){
		DefaultFilterInvocationDefinitionSource definitionSource = new DefaultFilterInvocationDefinitionSource(new AntUrlPathMatcher(),source);
		definitionSource.setStripQueryStringFromUrls(true);
		setObjectDefinitionSource(definitionSource);
	}
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.context = context;
	}
	public LinkedHashMap<RequestKey, ConfigAttributeDefinition> getSource() {
		return source;
	}
	public ICommonService getServicio() {
		return servicio;
	}
	public void setServicio(ICommonService servicio) {
		this.servicio = servicio;
	}
	public FilterInvocationDefinitionSource getObjectDefinitionSource() {
		return objectDefinitionSource;
	}
	public void setObjectDefinitionSource(FilterInvocationDefinitionSource objectDefinitionSource) {
		this.objectDefinitionSource = objectDefinitionSource;
	}
	public boolean supports(Class clazz) {
		return objectDefinitionSource.supports(clazz);
	}
	public Collection getConfigAttributeDefinitions() {
		return objectDefinitionSource.getConfigAttributeDefinitions();
	}
	public ConfigAttributeDefinition getAttributes(Object object)
			throws IllegalArgumentException {
		return objectDefinitionSource.getAttributes(object);
	}
	public ConfigAttributeDefinition lookupAttributes(String url){
		return ((DefaultFilterInvocationDefinitionSource)objectDefinitionSource).lookupAttributes(url,null);
	}
}
