package com.upl.ugdnsyncsolus.event;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;


@Component
public class PaginationDiscoverabilityListener implements ApplicationListener<PaginatedResultsRetrievedEvent> {

	public static final Logger logger = LoggerFactory.getLogger(PaginationDiscoverabilityListener.class);

	@Override
	public void onApplicationEvent(PaginatedResultsRetrievedEvent ev) {
		logger.debug("PaginationDiscoverabilityListener - onApplicationEvent");
		addLinkHeaderOnPagedResourceRetrieval(ev.getUriBuilder(), ev.getResponse(), ev.getClazz(), ev.getPage(),
				ev.getTotalPages(), ev.getPageSize());

	}

	void addLinkHeaderOnPagedResourceRetrieval(final UriComponentsBuilder uriBuilder,
			final HttpServletResponse response, final Class clazz, final int page, final int totalPages,
			final int pageSize) {
		// final String resourceName = clazz.getSimpleName().toString().toLowerCase();
		uriBuilder.path("/api/ugdnsync/pages");

		final StringBuilder linkHeader = new StringBuilder();
		if (hasNextPage(page, totalPages)) {
			final String uriForNextPage = constructNextPageUri(uriBuilder, page, pageSize);
			logger.trace("uriForNextPage : {}", uriForNextPage);
			linkHeader.append(createLinkHeader(uriForNextPage, "next"));
		}
		logger.info("linkHeader : {}", linkHeader.toString());
		response.addHeader("Link", linkHeader.toString());
	}

	String constructNextPageUri(final UriComponentsBuilder uriBuilder, final int page, final int size) {
		return uriBuilder.replaceQueryParam("page", page + 1).replaceQueryParam("size", size).build().encode()
				.toUriString();
	}

	boolean hasNextPage(final int page, final int totalPages) {
		logger.trace("hasNextPage = true");
		return page < totalPages;
	}

	public static String createLinkHeader(final String uri, final String rel) {
		logger.trace("Creating link header");
		return "<" + uri + ">; rel=\"" + rel + "\"";
	}
}
