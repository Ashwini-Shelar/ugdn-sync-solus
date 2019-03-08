package com.upl.ugdnsyncsolus.mapper;

import java.util.List;

import com.github.pagehelper.Page;
import com.upl.ugdnsyncsolus.model.EmployeeDetailsModel;
import com.upl.ugdnsyncsolus.model.SolusResponseModel;

public interface EmployeeMapper {

	Page<EmployeeDetailsModel> getAllEmployeesPaginated();

	void deleteRecordsFromAppSync(List<SolusResponseModel> responseList);

	void insertRecordsInAppSync(List<SolusResponseModel> responseList);

	void updateRecordsInAppSync(List<SolusResponseModel> responseList);

	void insertApp();

	void deleteFromAddtnDetails(List<SolusResponseModel> responseList);

	void insertEmpAddtnDtls();

	void insertInAddtnDetails(SolusResponseModel record);

	// Page<EmployeeDetailsModel> getAllEmployeesPaginated(String fullList, String
	// updateOnly, String newOnly, String deleteOnly);

}
