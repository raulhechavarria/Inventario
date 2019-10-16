package org.archetype.common.domain;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.archetype.common.web.utils.ArcheTypeUtils;
import org.json.JSONObject;

public class PersistentObject {
	private Long id;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof PersistentObject))
			return false;
		if(id == null && ((PersistentObject)o).id == null)
			return true;
		if(id == null || ((PersistentObject)o).id == null)
			return false;
		
		return id.equals(((PersistentObject)o).id);
	}
	
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder(17,31);
		builder.append(id);
		
		return builder.toHashCode();
	}
	
	public String toString() {
		try {
			JSONObject json = ArcheTypeUtils.getJSONObject(this, null, false);
			json.cleanJson();
			json.put("class", this.getClass().getSimpleName());
			
			return json.toString();
		} catch (Exception e) {	}
		
		return "{class: "+ this.getClass().getSimpleName()+" }";
	}
}
