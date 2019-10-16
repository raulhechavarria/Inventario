package org.archetype.common.business;

import java.util.Set;

import org.archetype.common.domain.Accion;

public interface IAccionService extends ICommonService{

	public void adicionarAcciones(Set<Accion> urlPaths);
}
