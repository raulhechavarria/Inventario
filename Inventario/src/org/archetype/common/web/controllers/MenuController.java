package org.archetype.common.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.archetype.common.web.utils.CheckUserAccessToUrl;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class MenuController extends AbstractController implements InitializingBean{

	private JSONArray menuJSON;
	private CheckUserAccessToUrl checkAccess;
	
	public void setCheckAccess(CheckUserAccessToUrl checkAccess) {
		this.checkAccess = checkAccess;
	}

	public void setMenuJSON(JSONArray menuJSON) {
		this.menuJSON = menuJSON;
	}
		
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONArray data = new JSONArray(menuJSON.toString());
		
		autorizar(data);		
		
		ModelAndView model = new ModelAndView("common/json");
		model.addObject("json", data.toString());
		return model;
	}
		
	protected void autorizar(JSONArray json) throws JSONException{
		
		for (int i = 0; i < json.length(); i++) {
			JSONObject obj = (JSONObject)json.get(i);
			if(!obj.isNull("children")){
				JSONArray childs = (JSONArray)obj.get("children");
				autorizar(childs);
				
				if(childs == null || childs.length() == 0){
					json.del(i);
					i--;
				}
			}			
			else{
				if(!obj.isNull("url")){
					String url = obj.getString("url").substring(2);
					
					if(!checkAccess.hasAccess(url)){
						json.del(i);
						i--;
					}
				}
			}
		}
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(checkAccess);
		Assert.notNull(menuJSON);
	}
}
