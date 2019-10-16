package org.archetype.common.dao.support;

import java.util.HashMap;
import java.util.Map;

import org.archetype.common.dao.IAccionDao;
import org.archetype.common.domain.BaseSearchResult;
import org.springframework.stereotype.Repository;

@Repository
public class AccionDao extends CommonDao implements IAccionDao{
	
	@SuppressWarnings("all")
	public Boolean existeAccion(String nombre){
		String query = "from Accion a where a.nombre = :nombre";
		
		Map properties = new HashMap();
		properties.put("nombre", nombre);
		
		BaseSearchResult bsr = listarHQL(query, properties, null);
		if(bsr.getTotalCount() == 0){
			return false;
		}
		return true;
	}
}
