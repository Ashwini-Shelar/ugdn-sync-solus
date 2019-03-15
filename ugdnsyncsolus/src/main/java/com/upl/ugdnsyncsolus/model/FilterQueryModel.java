package com.upl.ugdnsyncsolus.model;

public class FilterQueryModel {

	private String filterQuery;

	public final String getFilterQuery() {
		return filterQuery;
	}

	public final void setFilterQuery(String filterQuery) {
		this.filterQuery = filterQuery;
	}

	@Override
	public String toString() {
		return "FilterQueryModel [filterQuery=" + filterQuery + "]";
	}
}
