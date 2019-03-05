package com.upl.ugdnsyncsolus.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.upl.ugdnsyncsolus.event.PaginatedResultsRetrievedEvent;
import com.upl.ugdnsyncsolus.mapper.EmployeeMapper;
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
	EmployeeMapper empMapper;

	@Autowired
	ApplicationEventPublisher applicationEventPublisher;

	@Value("${pageNumber}")
	int page;

	@Value("${pageSize}")
	int size;

	String filterQuery;

	@GetMapping(value = "/ugdnsync")
	public List<EmployeeDetailsModel> findPaginated(UriComponentsBuilder uriBuilder, HttpServletResponse response) {

		logger.info("In controller");
		PageHelper.startPage(page, size);
		Page<EmployeeDetailsModel> resultPage = empMapper.getAllEmployeesPaginated();

		logger.info("page ={}, size= {}, resultPage - {}, content size = {}", page, size, resultPage.getPages(),
				resultPage.getResult().size());

		PaginatedResultsRetrievedEvent event = new PaginatedResultsRetrievedEvent(EmployeeDetailsModel.class,
				uriBuilder, response, page, resultPage.getPages(), size);
		applicationEventPublisher.publishEvent(event);

		logger.trace("pages.getContent: {}", resultPage.getResult());

		return resultPage.getResult();
	}

	@GetMapping(value = "/ugdnsync/pages", params = { "page", "size" })
	public List<EmployeeDetailsModel> findNextPages(@RequestParam("page") int page, @RequestParam("size") int size,
			UriComponentsBuilder uriBuilder, HttpServletResponse response) {

		logger.info("In controller - findNextPages");

		PageHelper.startPage(page, size);
		Page<EmployeeDetailsModel> resultPage = empMapper.getAllEmployeesPaginated();

		logger.info("page ={}, size= {}, resultPage - {}, content size = {}", page, size, resultPage.getPages(),
				resultPage.getResult().size());

		PaginatedResultsRetrievedEvent event = new PaginatedResultsRetrievedEvent(EmployeeDetailsModel.class,
				uriBuilder, response, page, resultPage.getPages(), size);
		applicationEventPublisher.publishEvent(event);

		logger.trace("pages.getContent: {}", resultPage.getResult());

		return resultPage.getResult();
	}

	// code with query param

	/*
	 * @GetMapping(value = "/ugdnsync") public List<EmployeeDetailsModel>
	 * findPaginated(UriComponentsBuilder uriBuilder, HttpServletResponse response,
	 * 
	 * @QueryParam("fullList") String fullList, @QueryParam("updateOnly") String
	 * updateOnly,
	 * 
	 * @QueryParam("newOnly") String newOnly, @QueryParam("deleteOnly") String
	 * deleteOnly) {
	 * 
	 * logger.info("In controller - {}", fullList); filterQuery = fullList;
	 * 
	 * if (fullList == null && updateOnly == null && newOnly == null && deleteOnly
	 * == null) { response.addHeader("Error Message",
	 * "Kindly specify one of the query parameters -[fullList, updateOnly ,newOnly, deleteOnly]"
	 * ); logger.info("response : {}", response.getHeader("Error Message")); return
	 * null;
	 * 
	 * } else {
	 * 
	 * logger.info("inside if{}", fullList); PageHelper.startPage(page, size);
	 * Page<EmployeeDetailsModel> resultPage =
	 * empMapper.getAllEmployeesPaginated(fullList, updateOnly, newOnly,
	 * deleteOnly);
	 * 
	 * logger.
	 * info("page ={}, size= {}, resultPage - {}, content size = {}, RESULT = {}",
	 * page, size, resultPage.getPages(), resultPage.getTotal(),
	 * resultPage.getResult());
	 * 
	 * PaginatedResultsRetrievedEvent event = new
	 * PaginatedResultsRetrievedEvent(EmployeeDetailsModel.class, uriBuilder,
	 * response, page, resultPage.getPages(), size);
	 * applicationEventPublisher.publishEvent(event);
	 * 
	 * logger.trace("pages.getContent: {}", resultPage.getResult());
	 * 
	 * return resultPage.getResult(); } }
	 * 
	 * @GetMapping(value = "/ugdnsync/pages", params = { "page", "size" }) public
	 * List<EmployeeDetailsModel> findNextPages(@RequestParam("page") int
	 * page, @RequestParam("size") int size, UriComponentsBuilder uriBuilder,
	 * HttpServletResponse response) {
	 * 
	 * logger.info("In controller - findNextPages");
	 * 
	 * PageHelper.startPage(page, size); Page<EmployeeDetailsModel> resultPage =
	 * empMapper.getAllEmployeesPaginated(filterQuery, null, null, null);
	 * 
	 * PaginatedResultsRetrievedEvent event = new
	 * PaginatedResultsRetrievedEvent(EmployeeDetailsModel.class, uriBuilder,
	 * response, page, resultPage.getPages(), size);
	 * applicationEventPublisher.publishEvent(event);
	 * 
	 * logger.trace("pages.getContent: {}", resultPage.getResult());
	 * 
	 * return resultPage.getResult(); }
	 */
}
