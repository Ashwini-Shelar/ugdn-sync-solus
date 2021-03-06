package com.upl.ugdnsyncsolus.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.upl.ugdnsyncsolus.event.PaginatedResultsRetrievedEvent;
import com.upl.ugdnsyncsolus.mapper.EmployeeMapper;
import com.upl.ugdnsyncsolus.model.EmployeeDetailsModel;
import com.upl.ugdnsyncsolus.model.FilterQueryModel;
import com.upl.ugdnsyncsolus.model.SolusResponseModel;
import com.upl.ugdnsyncsolus.service.EmployeeService;
import com.upl.ugdnsyncsolus.service.EmployeeServiceImpl;

@RestController
@RequestMapping("/api")
public class UgdnSyncSolusRestController {
	public static final Logger logger = LoggerFactory.getLogger(UgdnSyncSolusRestController.class);

	@Autowired
	EmployeeMapper empMapper;

	@Autowired
	ApplicationEventPublisher applicationEventPublisher;

	@Value("${pageNumber}")
	int page;

	@Value("${pageSize}")
	int size;

	String filterQuery;

	@CrossOrigin
	@PostMapping(value = "/ugdnsync")
	public List<EmployeeDetailsModel> findPaginated(UriComponentsBuilder uriBuilder, HttpServletResponse response,
			@QueryParam("fullList") String fullList, @QueryParam("updateOnly") String updateOnly,
			@QueryParam("newOnly") String newOnly, @QueryParam("deleteOnly") String deleteOnly) throws IOException {

		FilterQueryModel filterQueryModel = new FilterQueryModel();
		filterQuery = fullList != null ? "fullList"
				: updateOnly != null ? "updateOnly"
						: newOnly != null ? "newOnly" : deleteOnly != null ? "deleteOnly" : null;

		filterQueryModel.setFilterQuery(filterQuery);

		logger.info("Started getting data for page : {} , size : {} and filter query : {} ", page, size, filterQuery);

		if (filterQuery != null) {
			PageHelper.startPage(page, size);
			Page<EmployeeDetailsModel> resultPage = empMapper.getAllEmployeesPaginated(filterQueryModel);

			logger.debug("page ={}, size= {}, resultPage - {}, content size = {}", page, size, resultPage.getPages(),
					resultPage.getResult().size());

			PaginatedResultsRetrievedEvent event = new PaginatedResultsRetrievedEvent(EmployeeDetailsModel.class,
					uriBuilder, response, page, resultPage.getPages(), size, filterQueryModel.getFilterQuery());
			applicationEventPublisher.publishEvent(event);

			logger.trace("pages.getContent: {}", resultPage.getResult());

			logger.info("Completed getting data for page : {} and size : {}", page, size);
			return resultPage.getResult();
		} else {
			response.setStatus(400);
			return null;
		}

	}

	@CrossOrigin
	@PostMapping(value = "/ugdnsync/pages", params = { "page", "size", "filterquery" })
	public List<EmployeeDetailsModel> findNextPages(@RequestParam("page") int page, @RequestParam("size") int size,
			@RequestParam("filterquery") String filterquery, UriComponentsBuilder uriBuilder,
			HttpServletResponse response) throws IOException {

		logger.info("Started getting data for page : {} , size : {} and filter query : {} ", page, size, filterquery);

		if (filterquery != null) {
			FilterQueryModel filterQueryModel = new FilterQueryModel();
			filterQueryModel.setFilterQuery(filterquery);

			PageHelper.startPage(page, size);
			Page<EmployeeDetailsModel> resultPage = empMapper.getAllEmployeesPaginated(filterQueryModel);

			logger.debug("resultPage - {}, content size = {}", resultPage.getPages(), resultPage.getResult().size());

			PaginatedResultsRetrievedEvent event = new PaginatedResultsRetrievedEvent(EmployeeDetailsModel.class,
					uriBuilder, response, page, resultPage.getPages(), size, filterQueryModel.getFilterQuery());
			applicationEventPublisher.publishEvent(event);

			logger.trace("pages.getContent: {}", resultPage.getResult());

			logger.info("Completed getting data for page : {} and size : {}", page, size);
			return resultPage.getResult();
		} else {
			response.setStatus(400);
			return null;
		}
	}

	@CrossOrigin
	@PostMapping(value = "/ugdnsync/solusresponse")
	public void getSolusResponse(@RequestBody String solusResponse) {
		logger.info("Received response from solus and sent for parsing.");
		logger.trace("solus response: {}", solusResponse);

		List<SolusResponseModel> responseList = new ArrayList<SolusResponseModel>();

		try {
			responseList = Arrays.asList(new ObjectMapper().readValue(solusResponse, SolusResponseModel[].class));
			logger.trace("responseList = {}", responseList.toString());

			if (!responseList.isEmpty()) {
				// empMapper.insertApp(); for testing
				empMapper.updateRecordsInAppSync(responseList);
				logger.info("Completed updating records in ugdn_sync_other_apps table.");

				// empMapper.insertEmpAddtnDtls(); for testing
				empMapper.deleteFromAddtnDetails(responseList);
				logger.info("Completed deleting records from emp_addition_details table.");

				for (SolusResponseModel record : responseList) {
					if (record.getSolus_id() != null && !record.getSolus_id().isEmpty()) {
						empMapper.insertInAddtnDetails(record);
					}
				}
				logger.info("Completed inserting records in emp_addition_details table.");
			} else {
				throw new IOException("Empty response received.");
			}

		} catch (MismatchedInputException e) {
			logger.error("Mismatched Input Exception occurred- {}", e.getMessage());
			e.printStackTrace();
		} catch (JsonParseException e) {
			logger.error("JSON Parse Exception occurred- {}", e.getMessage());
			e.printStackTrace();
		} catch (JsonMappingException e) {
			logger.error("JSON Mapping Exception occurred- {}", e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("IO Exception occurred- {}", e.getMessage());
			e.printStackTrace();
		}

	}

	
}
