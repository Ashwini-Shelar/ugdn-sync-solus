package com.upl.ugdnsyncsolus.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.EventPublishingRunListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.upl.ugdnsyncsolus.event.PaginatedResultsRetrievedEvent;
import com.upl.ugdnsyncsolus.model.EmployeeDetailsModel;
import com.upl.ugdnsyncsolus.service.EmployeeService;

@RestController
@RequestMapping("/api/ugdnsync")
public class UgdnSyncSolusRestController {
	public static final Logger logger = LoggerFactory.getLogger(UgdnSyncSolusRestController.class);
	
	@Autowired
	EmployeeService empService;
	
	/*@RequestMapping(value = "/ugdn", method = RequestMethod.GET)
	public ResponseEntity<List<EmployeeDetailsModel>> getAllEmployees(Pageable pageable) {
		logger.info("Fetching all employees");
		List<EmployeeDetailsModel> empList = empService.getAllEmployees();
		if (empList.isEmpty()) {
			logger.error("No Employee found.");
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<EmployeeDetailsModel>>(empList, HttpStatus.OK);
	}*/


	
	@GetMapping(params = { "page", "size" })
	public List<EmployeeDetailsModel> findPaginated(@RequestParam("page") int page, 
	  @RequestParam("size") int size, UriComponentsBuilder uriBuilder,
	  HttpServletResponse response) {
	    Page<EmployeeDetailsModel> resultPage = empService.getAllEmployees(page, size);
	    if (page > resultPage.getTotalPages()) {
	       // throw new MyResourceNotFoundException();
	    }
	    eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<EmployeeDetailsModel>(
	    		EmployeeDetailsModel.class, uriBuilder, response, page, resultPage.getTotalPages(), size));
	 
	    return resultPage.getContent();
	}
	
}
