package org.archetype.common.domain;

import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings("unchecked")
public class BaseSearchResult {
	private Collection<Object> results = new ArrayList();
	private Long totalCount = new Long(0);

	public Collection<Object> getResults() {
		return results;
	}

	public void setResults(Collection results) {
		this.results = results;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
}
