package org.archetype.common.web.utils;

/**
 * @author Axel Mendoza Pupo
 */

import java.util.Iterator;
import java.util.Set;

import org.archetype.common.business.IUsuarioService;
import org.archetype.common.domain.Rol;
import org.archetype.common.domain.SecurityContext;
import org.archetype.common.domain.Usuario;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.User;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

public class SeguridadUserDetails implements UserDetailsService,
		InitializingBean {

	private IUsuarioService servicio;

	public IUsuarioService getServicio() {
		return servicio;
	}

	public void setServicio(IUsuarioService servicio) {
		this.servicio = servicio;
	}

	public UserDetails loadUserByUsername(String nombreUsuario)
			throws UsernameNotFoundException, DataAccessException {
		
		if("".equals(nombreUsuario)){
			throw new UsernameNotFoundException("User not found");
		}
				
		Usuario user = servicio.obtenerUsuario(nombreUsuario);
		
		if(user == null)
			throw new UsernameNotFoundException("User not found");
		
		String contrasenna = user.getPassword();
		
		Set<Rol> rls = user.getRoles();
		GrantedAuthority[] roles = new GrantedAuthority[rls.size()];
		int pos = 0;
		
		for (Iterator<Rol> iterator = rls.iterator(); iterator.hasNext();pos++) {
			Rol rol = (Rol) iterator.next();
			roles[pos] = new GrantedAuthorityImpl(rol.getNombre());
		}
		
		if (roles.length == 0) {
			throw new UsernameNotFoundException("User has no GrantedAuthority");
		}
		Usuario u = new Usuario();
		u.setId(user.getId());
		u.setNombre(user.getNombre());
		
		((SecurityContext)SecurityContextHolder.getContext()).setUsuario(u);
		return new User(nombreUsuario,contrasenna,!user.isDeshabilitado(),true,true,true,roles);		
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.servicio, "A seguridadFachada must be set");
	}

}
