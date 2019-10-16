package org.archetype.common.domain;

import org.archetype.common.web.utils.ArcheTypeUtils;

public class ExtGridRequest {
	
	private int start = 0;
	private int limit = 15;
	private String sort = "";
	private String dir = "";
	private Object query; 
	private long _dc = 0;
	
	public long get_dc() {
		return _dc;
	}
	public void set_dc(long _dc) {		
		this._dc = _dc;		
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	public Object getQuery() {
		return query;
	}
	public void setQuery(Object query) {
		try {
			ArcheTypeUtils.cleanHibernateTransientObject(query);
		} catch (Exception e) {	}
		this.query = query;
	}
	
	
}
