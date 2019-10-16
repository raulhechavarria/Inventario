package org.archetype.common.web.controllers;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.archetype.common.domain.AuditTrailLog;
import org.archetype.common.domain.BaseSearchResult;
import org.archetype.common.domain.ExtGridRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;

public class AuditController extends CommonController{
	
	public ModelAndView listar(HttpServletRequest request,
			HttpServletResponse response, ExtGridRequest egr) throws Exception {
		BaseSearchResult bsr = getServicio().listar(egr);
		Collection<Object> lista = bsr.getResults();
		
		JSONObject json = new JSONObject();
		JSONArray data = new JSONArray();
		
		for (Iterator<Object> iterator = lista.iterator(); iterator.hasNext();) {
			AuditTrailLog item = (AuditTrailLog) iterator.next();				
			JSONObject obj = new JSONObject();
			
			obj.put("id",item.getId());
			obj.put("action",item.getActionPerformed());
			obj.put("clientIp",item.getClientIpAddress());
			obj.put("serverIp",item.getServerIpAddress());
			obj.put("principal",item.getPrincipal());
			obj.put("data",item.getResourceOperatedUpon());
			obj.put("date",item.getWhenActionWasPerformed());
			data.put(obj);
		}
		
		json.put("totalCount", bsr.getTotalCount());
		json.put("data", data);
			
		ModelAndView mv = new ModelAndView("common/json");
		mv.addObject("json",json.toString());		
		return mv;
	}
}
