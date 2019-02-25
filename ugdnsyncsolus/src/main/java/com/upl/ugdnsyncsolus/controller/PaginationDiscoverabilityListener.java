package com.upl.ugdnsyncsolus.controller;

import javax.servlet.http.HttpServletResponse;

import org.assertj.core.util.Preconditions;
import org.springframework.context.ApplicationListener;
import org.springframework.web.util.UriComponentsBuilder;

import com.upl.ugdnsyncsolus.event.PaginatedResultsRetrievedEvent;

public  class PaginationDiscoverabilityListener implements ApplicationListener<PaginatedResultsRetrievedEvent> {

	@Override
	public void onApplicationEvent(final PaginatedResultsRetrievedEvent ev) {
		Preconditions.checkNotNull(ev);
		addLinkHeaderOnPagedResourceRetrieval(ev.getUriBuilder(), ev.getResponse(), ev.getClazz(), ev.getPage(),
				ev.getTotalPages(), ev.getPageSize());

	}

	void addLinkHeaderOnPagedResourceRetrieval(final UriComponentsBuilder uriBuilder,
			final HttpServletResponse response, final Class clazz, final int page, final int totalPages,
			final int pageSize) {
		final String resourceName = clazz.getSimpleName().toString().toLowerCase();
		uriBuilder.path("/admin/" + resourceName);

		final StringBuilder linkHeader = new StringBuilder();
		if (hasNextPage(page, totalPages)) {
			final String uriForNextPage = constructNextPageUri(uriBuilder, page, pageSize);
			linkHeader.append(createLinkHeader(uriForNextPage, "next"));
		}
		response.addHeader("Link", linkHeader.toString());
	}

	String constructNextPageUri(final UriComponentsBuilder uriBuilder, final int page, final int size) {
		return uriBuilder.replaceQueryParam("page", page + 1).replaceQueryParam("size", size).build().encode()
				.toUriString();
	}

	boolean hasNextPage(final int page, final int totalPages) {
		return page < totalPages - 1;
	}

	void appendCommaIfNecessary(final StringBuilder linkHeader) {
		if (linkHeader.length() > 0) {
			linkHeader.append(", ");
		}
	}

	public static String createLinkHeader(final String uri, final String rel) {
		return "<" + uri + ">; rel=\"" + rel + "\"";
	}
}
