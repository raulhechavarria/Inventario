package org.archetype.common.business.support;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import org.archetype.common.business.IAccionService;
import org.archetype.common.dao.IAccionDao;
import org.archetype.common.domain.Accion;
import org.archetype.common.domain.BaseSearchResult;
import org.archetype.common.domain.ExtGridRequest;
import org.archetype.common.domain.Rol;
import org.archetype.common.web.utils.AccessDefinitionSource;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.intercept.web.RequestKey;

public class AccionService extends CommonService implements IAccionService{
	
	private AccessDefinitionSource securityConfig;
	
	public void adicionar(Object object) {
		Accion accion = (Accion)object;
		LinkedHashMap<RequestKey,ConfigAttributeDefinition> source = securityConfig.getSource();
		RequestKey key = new RequestKey(accion.getNombre());
		
		if(source.containsKey(key)){
			source.remove(key);
		}
		
		String[] svalue = new String[accion.getRoles().size()];
		int i = 0;
		
		for (Iterator<Rol> iterator = accion.getRoles().iterator(); iterator.hasNext(); i++) {
			Rol rol = iterator.next();
			rol = (Rol)getDao().obtener(Rol.class, rol.getId());
			svalue[i] = rol.getNombre();
		}
		
		ConfigAttributeDefinition value = new ConfigAttributeDefinition(svalue);
		source.put(key, value);
		securityConfig.reloadConfig();
		
		super.adicionar(object);
	}
	
	public void actualizar(Object object) {
		Accion accion = (Accion)object;
		LinkedHashMap<RequestKey,ConfigAttributeDefinition> source = securityConfig.getSource();
		RequestKey key = new RequestKey(accion.getNombre());
		
		if(source.containsKey(key)){
			source.remove(key);
		}
		
		String[] svalue = new String[accion.getRoles().size()];
		int i = 0;
		
		for (Iterator<Rol> iterator = accion.getRoles().iterator(); iterator.hasNext(); i++) {
			Rol rol = iterator.next();
			rol = (Rol)getDao().obtener(Rol.class, rol.getId());
			svalue[i] = rol.getNombre();
		}
		
		ConfigAttributeDefinition value = new ConfigAttributeDefinition(svalue);
		source.put(key, value);
		securityConfig.reloadConfig();
		
		super.actualizar(object);
	}

	public AccessDefinitionSource getSecurityConfig() {
		return securityConfig;
	}

	public void setSecurityConfig(AccessDefinitionSource securityConfig) {
		this.securityConfig = securityConfig;
	}
	
	public void adicionarAcciones(Set<Accion> urlPaths){

		Set<Rol> set = new HashSet<Rol>();
		
		ExtGridRequest egr = new ExtGridRequest();
		egr.setLimit(0);
		BaseSearchResult bsr = getDao().listar(Rol.class, egr);
		
		for (Iterator<Object> iterator = bsr.getResults().iterator(); iterator.hasNext();) {
			Rol rol = (Rol) iterator.next();
			set.add(rol);
		}
		
		for (Iterator<Accion> iterator = urlPaths.iterator(); iterator.hasNext();) {
			Accion act = iterator.next();
			
			if(((IAccionDao)getDao()).existeAccion(act.getNombre()) == false){
				act.setRoles(set);
				getDao().adicionar(act);
				
				if(act.getHijos() != null){
					for (Iterator<Accion> iterator2 = act.getHijos().iterator(); iterator2.hasNext();) {
						Accion hija = iterator2.next();
						getDao().adicionar(hija);
					}
				}
			}
		}
	}
}
