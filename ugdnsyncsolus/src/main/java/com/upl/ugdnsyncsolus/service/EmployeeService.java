package com.upl.ugdnsyncsolus.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.upl.ugdnsyncsolus.model.EmployeeDetailsModel;


public interface EmployeeService{
	

	public Page<EmployeeDetailsModel> getAllEmployees(int page, int size);



	
}
