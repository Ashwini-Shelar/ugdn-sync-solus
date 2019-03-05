package com.upl.ugdnsyncsolus.mapper;

import com.github.pagehelper.Page;
import com.upl.ugdnsyncsolus.model.EmployeeDetailsModel;

public interface EmployeeMapper {

	Page<EmployeeDetailsModel> getAllEmployeesPaginated();

	// Page<EmployeeDetailsModel> getAllEmployeesPaginated(String fullList, String
	// updateOnly, String newOnly, String deleteOnly);

}
