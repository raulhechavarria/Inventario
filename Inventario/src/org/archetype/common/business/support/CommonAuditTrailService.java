package org.archetype.common.business.support;

import org.archetype.common.dao.ICommonDao;
import org.archetype.common.domain.AuditTrailLog;
import org.inspektr.audit.AuditActionContext;
import org.inspektr.audit.AuditTrailManager;
import org.inspektr.audit.Cleanable;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class CommonAuditTrailService implements AuditTrailManager, Cleanable{

	public ICommonDao dao;
	
	public void record(AuditActionContext auditActionContext) {
		AuditTrailLog log = new AuditTrailLog();
		log.setActionPerformed(auditActionContext.getActionPerformed());
		log.setPrincipal(auditActionContext.getPrincipal());
		log.setResourceOperatedUpon(auditActionContext.getResourceOperatedUpon());
		log.setWhenActionWasPerformed(auditActionContext.getWhenActionWasPerformed());
		log.setClientIpAddress(auditActionContext.getClientIpAddress());
		log.setServerIpAddress(auditActionContext.getServerIpAddress());
		getDao().adicionar(log);
	}

	public void clean() {
		//limpiar la tabla en base a parametros
	}

	public ICommonDao getDao() {
		return dao;
	}

	public void setDao(ICommonDao dao) {
		this.dao = dao;
	}

	
}
