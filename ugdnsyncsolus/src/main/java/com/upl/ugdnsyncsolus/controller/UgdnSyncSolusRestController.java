package com.upl.ugdnsyncsolus.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.upl.ugdnsyncsolus.event.PaginatedResultsRetrievedEvent;
import com.upl.ugdnsyncsolus.model.EmployeeDetailsModel;
import com.upl.ugdnsyncsolus.service.EmployeeService;
import com.upl.ugdnsyncsolus.service.EmployeeServiceImpl;

@RestController
@RequestMapping("/api")
public class UgdnSyncSolusRestController {
	public static final Logger logger = LoggerFactory.getLogger(UgdnSyncSolusRestController.class);

	@Autowired
	EmployeeService empService;

	@Autowired
	EmployeeServiceImpl empServiceImpl;

	/*
	 * @Autowired PaginatedEventPublisher paginatedEventPublisher;
	 */

	@Autowired
	ApplicationEventPublisher applicationEventPublisher;

	/*
	 * @Value("${pageNumber}") int page;
	 * 
	 * @Value("${pageSize}") int size;
	 */

	/*
	 * @RequestMapping(value = "/ugdn", method = RequestMethod.GET) public
	 * ResponseEntity<List<EmployeeDetailsModel>> getAllEmployees(Pageable pageable)
	 * { logger.info("Fetching all employees"); List<EmployeeDetailsModel> empList =
	 * empService.getAllEmployees(); if (empList.isEmpty()) {
	 * logger.error("No Employee found."); return new
	 * ResponseEntity(HttpStatus.NO_CONTENT); } return new
	 * ResponseEntity<List<EmployeeDetailsModel>>(empList, HttpStatus.OK); }
	 */

	@GetMapping(value = "/ugdnsync", params = { "page", "size" })
	public List<EmployeeDetailsModel> findPaginated(@RequestParam("page") int page, @RequestParam("size") int size,
			UriComponentsBuilder uriBuilder, HttpServletResponse response,
			@QueryParam("fullList") String fullList, @QueryParam("updateOnly") String updateOnly,
			@QueryParam("newOnly") String newOnly, @QueryParam("deleteOnly") String deleteOnly) {
		
		logger.info("In controller");

		List<EmployeeDetailsModel> resultPage = empService.getAllEmployees(fullList,updateOnly,newOnly,deleteOnly);
		logger.info("result list size: {}", resultPage.size());

		Pageable pageable = new PageRequest(page, size);

		int start = pageable.getOffset();
		int end = (start + size) > resultPage.size() ? resultPage.size() : (start + size);
		Page<EmployeeDetailsModel> pages = new PageImpl<EmployeeDetailsModel>(resultPage.subList(start, end), pageable,
				resultPage.size());

		logger.info("start: {}, end: {}", start, end);
		logger.info("pages: {}", pages);
		logger.info("pages.getTotalPages: {}", pages.getTotalPages());

		if (page > pages.getTotalPages()) {
			// throw new MyResourceNotFoundException();
		}

		PaginatedResultsRetrievedEvent event = new PaginatedResultsRetrievedEvent(EmployeeDetailsModel.class,
				uriBuilder, response, page, pages.getTotalPages(), size);
		applicationEventPublisher.publishEvent(event);

		logger.debug("pages.getContent: {}", pages.getContent());

		return pages.getContent();
	}

	@GetMapping(value = "/test")
	public String Test() {
		logger.info("In controller");
		return "Hello";
	}

	/*
	 * @GetMapping(value = "/products") public ResponseEntity < PagedResources <
	 * EmployeeDetailsModel >> AllProducts(Pageable pageable,
	 * PageableArgumentResolver assembler) { Page < EmployeeDetailsModel > products
	 * = empService.findAllProducts(pageable); PagedResources < EmployeeDetailsModel
	 * > pr = assembler.toResource(products,
	 * linkTo(UgdnSyncSolusRestController.class).slash("/products").withSelfRel());
	 * HttpHeaders responseHeaders = new HttpHeaders(); responseHeaders.add("Link",
	 * createLinkHeader(pr)); return new ResponseEntity < >
	 * (assembler.toResource(products,
	 * linkTo(UgdnSyncSolusRestController.class).slash("/products").withSelfRel()),
	 * responseHeaders, HttpStatus.OK); }
	 * 
	 * private String createLinkHeader(PagedResources < EmployeeDetailsModel > pr) {
	 * final StringBuilder linkHeader = new StringBuilder();
	 * linkHeader.append(buildLinkHeader(pr.getLinks("first").get(0).getHref(),
	 * "first")); linkHeader.append(", ");
	 * linkHeader.append(buildLinkHeader(pr.getLinks("next").get(0).getHref(),
	 * "next")); return linkHeader.toString(); }
	 * 
	 * public static String buildLinkHeader(final String uri, final String rel) {
	 * return "<" + uri + ">; rel=\"" + rel + "\""; } }
	 */

}
