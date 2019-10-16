package org.archetype.common.web.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.archetype.common.domain.PersistentObject;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.collection.PersistentCollection;
import org.hibernate.criterion.Example;
import org.hibernate.proxy.HibernateProxy;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@SuppressWarnings("all")
public class ArcheTypeUtils {
	
	/*	json region*/
	public static JSONObject getSuccess() throws JSONException{
		JSONObject resp = new JSONObject();
		resp.put("success", true);		
		return resp;
	}

	public static JSONObject getFailure(JSONObject error) throws JSONException{
		JSONObject resp = new JSONObject();
		resp.put("success", false);
		if(error != null){
			resp.put("errors", error);
		}else{
			resp.put("errors", "[]");
		}
		return resp;
	}
	
	public static JSONArray getJSONArrayNames(Class clazz) throws Exception{
		JSONArray array = new JSONArray();
		List<Field> list = new LinkedList<Field>();
		allfields(clazz, list);
		
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Field field = (Field) iterator.next();
			if(field.getName().equals("serialVersionUID") == false){
				array.put(field.getName());
			}
		}
		
		return array;
	}
	
	public static JSONArray getJSONArray(Object collection, Object padre, boolean allowNulls) throws Exception{
		JSONArray map = new JSONArray();
				
		if(collection instanceof Collection){
			if(collection != null && collection instanceof PersistentCollection && Hibernate.isInitialized(collection) == true) {
				for (Iterator iter = ((Collection)collection).iterator(); iter.hasNext();) {
					map.put(getJSONObject(iter.next(), padre, allowNulls));
				}
			}
		} else if(collection.getClass().isArray()){
			int length = Array.getLength(collection);
            for (int i = 0; i < length; i += 1) {
                map.put(Array.get(collection, i));
            }
		}
		
		return map;
	}
	
	public static JSONObject getJSONObject(Object obj, Object padre, boolean allowNulls) throws Exception{
		JSONObject map = new JSONObject();
		
		LinkedList list = new LinkedList();

		allfields(obj.getClass(), list);
		
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Field field = (Field) iterator.next();
			try {
				field.setAccessible(true);
				Object result = field.get(obj);
				String key = field.getName();

				if(result != null){
					if(padre != null && padre instanceof Field == false && (result.getClass().equals(padre.getClass()) && result.equals(padre) == true)){
						 continue;
					} else{
						if (result.getClass().isArray()	|| result instanceof Collection) {
							map.put(key, getJSONArray(result, obj, allowNulls));
						} else if (result instanceof Map) {
							map.put(key, new JSONObject((Map) result, true));
						} else if (isStandardProperty(result.getClass())) { // Primitives, String, Enums and Wrappers
							map.put(key, result);
						} else {
							if (result.getClass().getPackage().getName().startsWith("java") || result.getClass().getClassLoader() == null) {
								map.put(key, result.toString());
							} else { // User defined Objects
								if(result instanceof HibernateProxy){
									if(Hibernate.isInitialized(result) == true){
										map.put(key, getJSONObject(result, obj, allowNulls));	
									}
								} else{
									map.put(key, getJSONObject(result, obj, allowNulls));
								}
							}
						}
					}
				} else {
					if(allowNulls == true){
						if(padre instanceof Field && (field.getType().equals(((Field)padre).getType()))){
							continue;
						}else{
							if(field.getType().isArray() || field.getType().isInterface() || field.getType().isAssignableFrom(Collection.class)){
								continue;
							} else {
								map.put(key, "");
							}
						}
					}
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		return map;
	}
	
	public static void bindJSONObject(JSONObject json, Object obj) throws Exception{
		Class c = obj.getClass();
		for (Iterator iterator = json.keys(); iterator.hasNext();) {
			String prop = (String) iterator.next();
			Object value = json.get(prop);
			Field field = getField(c, prop);
			field.setAccessible(true);
			field.set(obj, value);
		}
	}
	
	public static void bindJSONArray(JSONArray array, Collection collection, Class clazz) throws Exception{
		for (int i = 0; i < array.length(); i++) {
			Object obj = clazz.newInstance();
			bindJSONObject(array.getJSONObject(i), obj);
			collection.add(obj);
		}
	}
	
	public static HttpServletRequest createHttpServletRequestJSON(JSONObject query){
		MockHttpServletRequest request = new MockHttpServletRequest();
		
		for (Iterator<String> iterator = query.keys(); iterator.hasNext();) {
				String name = iterator.next();
				String value = query.optString(name);
				request.setParameter(name, value);
		}
		
		return request;
	}
	
	/*	criteria by example region*/
	private void addExampleAsociations(Criteria crit, Object obj){
		LinkedList<Field> list = new LinkedList<Field>();

		allfields(obj.getClass(), list);
		
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Field field = (Field) iterator.next();
			try {
				if(!isStandardProperty(field.getType())){
					field.setAccessible(true);
					if(field.get(obj) != null){
						crit.createCriteria(field.getName()).add( Example.create(field.get(obj)));
					}
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*	controllers region*/
	public static Long getIdParameter(HttpServletRequest request, String paramName){
		String param = request.getParameter(paramName);
		if(param == null || param.isEmpty()){
			return null;
		}else{
			return Long.parseLong(param);
		}
	}
	
	public static BindException validate(Object object, Validator[] validators) throws Exception{
		BindException errors = new BindException(object,"command");
				
		for (int i = 0; i < validators.length; i++) {
			if(validators[i].supports(object.getClass())){
				ValidationUtils.invokeValidator(validators[i], object, errors);
			}
		}
		return errors;
	}
	
	public static void fileDownload(String folder, String filename, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ServletContext servletContext = request.getSession().getServletContext();
		
		File uFile = new File(servletContext.getRealPath(folder + filename));
		int fSize = (int) uFile.length();

		BufferedInputStream in = new BufferedInputStream(new FileInputStream(uFile));
		String mimetype = servletContext.getMimeType(filename);

		response.setBufferSize(fSize);
		response.setContentType(mimetype);
		response.setHeader("Content-Disposition", "attachment; filename=\""	+ filename + "\"");
		response.setContentLength(fSize);

		FileCopyUtils.copy(in, response.getOutputStream());
		in.close();
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
	
	public static JSONArray listarEnums(Class enumerativo){
		if(enumerativo.isEnum() == true){
			Field fields[] = enumerativo.getDeclaredFields();
			List<String> list = new ArrayList<String>();
			
			for (int i = 0; i < fields.length-1; i++) {
				list.add(fields[i].getName());
			}
			
			JSONArray data = new JSONArray();
			
			for (int i = 0; i < list.size(); i++) {
				String enu = (String) list.get(i);
				JSONArray elem = new JSONArray();
				elem.put(i);
				elem.put(enu);
				data.put(elem);
			}
			
			return data;
		}
		return null;
	}
	
	/*	reflection region*/
	public static boolean isStandardProperty(Class clazz) {
    	return clazz.isPrimitive()                  ||
    		clazz.isAssignableFrom(Byte.class)      ||
    		clazz.isAssignableFrom(Short.class)     ||
    		clazz.isAssignableFrom(Integer.class)   ||
    		clazz.isAssignableFrom(Long.class)      ||
    		clazz.isAssignableFrom(Float.class)     ||
    		clazz.isAssignableFrom(Double.class)    ||
    		clazz.isAssignableFrom(Character.class) ||
    		clazz.isAssignableFrom(String.class)    ||
    		clazz.isAssignableFrom(Boolean.class)	||
    		clazz.isAssignableFrom(Date.class)		||
    		clazz.isEnum();
    }
	
	public static void allfields(Class c, List list){
		if(c.getSuperclass() != null){
			allfields(c.getSuperclass(), list);
		}
		Field fields[] = c.getDeclaredFields();
		if(fields.length != 0){
			for (int i = 0; i < fields.length; i++) {
				if(fields[i].getName().equals("serialVersionUID") == false){
					list.add(fields[i]);
				}
			}
		}
		
	}
	
	public static Field getField(Class c, String prop){
		Field fields[] = c.getDeclaredFields();
		if(fields.length != 0){
			for (int i = 0; i < fields.length; i++) {
				if(fields[i].getName().equals(prop)){
					return fields[i];
				}
			}
		}
		return getField(c.getSuperclass(), prop);
	}
	
	public static void cleanHibernateTransientObject(Object obj) throws Exception{
		LinkedList<Field> list = new LinkedList<Field>();
		allfields(obj.getClass(), list);
		
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Field field = (Field) iterator.next();
			if(!isStandardProperty(field.getType())){
				if(PersistentObject.class.isAssignableFrom(field.getType())){
					field.setAccessible(true);
					PersistentObject res = (PersistentObject)field.get(obj);
					if(res != null && res.getId() == null){
						field.set(obj, null);
					}
				}
			}
		}
	}
}
