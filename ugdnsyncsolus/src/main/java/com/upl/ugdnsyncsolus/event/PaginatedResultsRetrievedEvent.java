package com.upl.ugdnsyncsolus.event;

import java.io.Serializable;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;
import org.springframework.web.util.UriComponentsBuilder;

import com.upl.ugdnsyncsolus.model.EmployeeDetailsModel;

// public final class PaginatedResultsRetrievedEvent<T extends Serializable> extends ApplicationEvent {
public final class PaginatedResultsRetrievedEvent extends ApplicationEvent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final UriComponentsBuilder uriBuilder;
	private final HttpServletResponse response;
	private final int page;
	private final int totalPages;
	private final int pageSize;
	private final String filterQuery;

	public PaginatedResultsRetrievedEvent(final Class<EmployeeDetailsModel> clazz,
			final UriComponentsBuilder uriBuilderToSet, final HttpServletResponse responseToSet, final int pageToSet,
			final int totalPagesToSet, final int pageSizeToSet, final String filterQueryToSet) {
		super(clazz);

		this.uriBuilder = uriBuilderToSet;
		this.response = responseToSet;
		this.page = pageToSet;
		this.totalPages = totalPagesToSet;
		this.pageSize = pageSizeToSet;
		this.filterQuery = filterQueryToSet;
	}

	// API

	public final UriComponentsBuilder getUriBuilder() {
		return this.uriBuilder;
	}

	public final HttpServletResponse getResponse() {
		return this.response;
	}

	public final int getPage() {
		return this.page;
	}

	public final int getTotalPages() {
		return this.totalPages;
	}

	public final int getPageSize() {
		return this.pageSize;
	}

	@SuppressWarnings("unchecked")
	public final Class<EmployeeDetailsModel> getClazz() {
		return (Class<EmployeeDetailsModel>) getSource();
	}

	public final String getFilterQuery() {
		return filterQuery;
	}

}
