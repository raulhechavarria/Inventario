package org.archetype.common.dao.support;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public abstract class SpringHibernateSessionFactorySupport implements InitializingBean{

	private SessionFactory factory;

	public SessionFactory getFactory() {
		return factory;
	}

	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}
	
	public Session getCurrentSession(){
		return factory.getCurrentSession();
	}
	
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(factory, "hibernate session factory must be set");		
	}
}
