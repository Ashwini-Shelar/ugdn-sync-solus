package com.upl.ugdnsyncsolus.service;

import java.util.List;

import com.upl.ugdnsyncsolus.model.EmployeeDetailsModel;

public interface EmployeeService {
	public List<EmployeeDetailsModel> getAllEmployees(String fullList, String updateOnly, String newOnly,
			String deleteOnly);

}
