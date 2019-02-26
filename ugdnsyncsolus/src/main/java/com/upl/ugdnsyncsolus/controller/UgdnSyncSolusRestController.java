package com.upl.ugdnsyncsolus.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.upl.ugdnsyncsolus.event.PaginatedEventPublisher;
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

	@Autowired
	PaginatedEventPublisher applicationEventPublisher;
	
	@Value("${pageNumber}")
	int pageNumber;
	
	@Value("${pageSize}")
	int pageSize;

	/*
	 * @RequestMapping(value = "/ugdn", method = RequestMethod.GET) public
	 * ResponseEntity<List<EmployeeDetailsModel>> getAllEmployees(Pageable pageable)
	 * { logger.info("Fetching all employees"); List<EmployeeDetailsModel> empList =
	 * empService.getAllEmployees(); if (empList.isEmpty()) {
	 * logger.error("No Employee found."); return new
	 * ResponseEntity(HttpStatus.NO_CONTENT); } return new
	 * ResponseEntity<List<EmployeeDetailsModel>>(empList, HttpStatus.OK); }
	 */

	@GetMapping(value = "/ugdnsync")
	public List<EmployeeDetailsModel> findPaginated(UriComponentsBuilder uriBuilder, HttpServletResponse response) {
		logger.info("In controller");

		List<EmployeeDetailsModel> resultPage = empService.getAllEmployees();
		logger.info("result list size: {}", resultPage.size());
		
		Pageable pageable  = new PageRequest(pageNumber, pageSize);

		int start = pageable.getOffset();
		int end = (start + pageNumber) > resultPage.size() ? resultPage.size() : (start + pageNumber);
		// int endSize = (start + pageSize) > resultPage.size() ? resultPage.size() : (start + pageSize);
		Page<EmployeeDetailsModel> pages = new PageImpl<EmployeeDetailsModel>(resultPage.subList(start, end), pageable,
				resultPage.size());

		logger.info("start: {}, end: {}", start, end);
		logger.info("pages: {}", pages);
		logger.info("pages.getTotalPages: {}", pages.getTotalPages());
		
		// Page<EmployeeDetailsModel> resultPage = empService.getAllEmployees(page,
		// size);
		if (pageNumber > pages.getTotalPages()) {
			// throw new MyResourceNotFoundException();
		}

		applicationEventPublisher.publish(new PaginatedResultsRetrievedEvent<EmployeeDetailsModel>(
				EmployeeDetailsModel.class, uriBuilder, response, pageNumber, pages.getTotalPages(), pageSize));

		logger.info("pages.getContent: {}", pages.getContent());
		logger.info("response: {}", response);
		
		return pages.getContent();
	}

	@GetMapping(value = "/test")
	public String Test() {
		logger.info("In controller");
		return "Hello";
	}

}
