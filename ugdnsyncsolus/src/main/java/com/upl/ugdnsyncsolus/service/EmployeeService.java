package com.upl.ugdnsyncsolus.service;


import java.util.List;


import com.upl.ugdnsyncsolus.model.EmployeeDetailsModel;

public interface EmployeeService {
	//public Page<EmployeeDetailsModel> getAllEmployees(int page, int size);
	
	public List<EmployeeDetailsModel> getAllEmployees();

}
