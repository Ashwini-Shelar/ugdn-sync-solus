package com.upl.ugdnsyncsolus.service;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.upl.ugdnsyncsolus.model.EmployeeDetailsModel;

@Repository("empService")
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private JdbcTemplate jdbc;
	
	RowMapper<EmployeeDetailsModel> rowMapper;
	
	/*@Override
	public Iterable<EmployeeDetailsModel> getAllEmployees(int page, int size) {
		String sql = "SELECT E.UID_NO AS EUID,E.OLD_ECODE AS ECODE,E.BUS_NAME AS EBUSNAME,E.FIRST_NAME AS EFNAME,E.MIDDLE_NAME AS EMNAME,E.LAST_NAME AS ELNAME,E.DISPLAY_NAME AS EDNAME,E.DESIG AS EDESIG, E.DEPT AS EDEPT,E.PHONE AS EPHONE,E.MOBILE AS EMOB,E.MAIL_ID AS EMAILID,E.BLOOD_GROUP AS EBGROUP,E.DATE_BIRTH AS EBDATE,E.DATE_JOIN AS EJDATE,E.DATE_LEFT AS ELDATE,E.COUNTRY_NAME AS ECONCODE,E.COMPANY_CODE AS ECOMCODE, E.EMP_CATG AS ECATG,E.REGION_NAME AS EREGION,E.LOC_NAME AS ELOC,E.SUB_AREA AS ESUBAREA, E.FUNCTION AS EFUN,E.SUB_FUNCTION AS ESUBFUN ,E.ACTIVE_FLAG AS EFLAG,E.HOD_ID AS EHODID,H.DISPLAY_NAME AS HNAME, H.MAIL_ID AS HMAILID, H.DESIG AS HDESIG, AD.PARAM_NAME AS PARAM1,AD.PARAM_VALUE AS COSTCENTRE ,\r\n"
				+ "EAD.PARAM_NAME AS PARAM2,EAD.PARAM_VALUE AS HRBAND FROM EMP_MASTER E LEFT JOIN EMP_MASTER H ON (E.HOD_ID=H.UID_NO) LEFT JOIN EMP_ADDITION_DETAILS AD ON(AD.UID_NO=E.UID_NO AND AD.PARAM_NAME='cost_center') LEFT JOIN EMP_ADDITION_DETAILS EAD ON(EAD.UID_NO=E.UID_NO AND EAD.PARAM_NAME='hr_band')";

		try {
			// List<EmployeeDetailsModel> pagedResponse = jdbc.query(sql, rowMapper);
			return jdbc.query(sql,this.rowMapper);
			// return pagedResponse;
		} catch (EmptyResultDataAccessException exp) {
			return null;
		}
	}*/
	
	
	
	@Override
	public List<EmployeeDetailsModel> getAllEmployees() {
		String sql = "SELECT E.UID_NO AS EUID,E.OLD_ECODE AS ECODE,E.BUS_NAME AS EBUSNAME,E.FIRST_NAME AS EFNAME,E.MIDDLE_NAME AS EMNAME,E.LAST_NAME AS ELNAME,E.DISPLAY_NAME AS EDNAME,E.DESIG AS EDESIG, E.DEPT AS EDEPT,E.PHONE AS EPHONE,E.MOBILE AS EMOB,E.MAIL_ID AS EMAILID,E.BLOOD_GROUP AS EBGROUP,E.DATE_BIRTH AS EBDATE,E.DATE_JOIN AS EJDATE,E.DATE_LEFT AS ELDATE,E.COUNTRY_NAME AS ECONCODE,E.COMPANY_CODE AS ECOMCODE, E.EMP_CATG AS ECATG,E.REGION_NAME AS EREGION,E.LOC_NAME AS ELOC,E.SUB_AREA AS ESUBAREA, E.FUNCTION AS EFUN,E.SUB_FUNCTION AS ESUBFUN ,E.ACTIVE_FLAG AS EFLAG,E.HOD_ID AS EHODID,H.DISPLAY_NAME AS HNAME, H.MAIL_ID AS HMAILID, H.DESIG AS HDESIG, AD.PARAM_NAME AS PARAM1,AD.PARAM_VALUE AS COSTCENTRE ,\r\n"
				+ "EAD.PARAM_NAME AS PARAM2,EAD.PARAM_VALUE AS HRBAND FROM EMP_MASTER E LEFT JOIN EMP_MASTER H ON (E.HOD_ID=H.UID_NO) LEFT JOIN EMP_ADDITION_DETAILS AD ON(AD.UID_NO=E.UID_NO AND AD.PARAM_NAME='cost_center') LEFT JOIN EMP_ADDITION_DETAILS EAD ON(EAD.UID_NO=E.UID_NO AND EAD.PARAM_NAME='hr_band')";

		try {
			List<EmployeeDetailsModel> empList = jdbc.query(sql, new EmployeeMapper());
			return empList;
		} catch (EmptyResultDataAccessException exp) {
			return null;
		}
	}

	public static final class EmployeeMapper implements RowMapper<EmployeeDetailsModel> {

		@Override
		public EmployeeDetailsModel mapRow(ResultSet rs, int rowNum) throws SQLException {
			EmployeeDetailsModel emp = new EmployeeDetailsModel();
			SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd");

			emp.setUid_no(rs.getString("EUID"));
			emp.setOld_ecode(rs.getString("ECODE"));
			emp.setBus_name(rs.getString("EBUSNAME"));
			emp.setFirst_name(rs.getString("EFNAME"));
			emp.setDept(rs.getString("EDEPT"));

			String eMiddleName = rs.getString("EMNAME") != null ? rs.getString("EMNAME") : "";
			emp.setMiddle_name(eMiddleName);

			String eLastName = rs.getString("ELNAME") != null ? rs.getString("ELNAME") : "";
			emp.setLast_name(eLastName);

			String eDisplayName = rs.getString("EDNAME") != null ? rs.getString("EDNAME") : "";
			emp.setDisplay_name(eDisplayName);

			String eDesig = rs.getString("EDESIG") != null ? rs.getString("EDESIG") : "";
			emp.setDesig(eDesig);

			String ePhone = rs.getString("EPHONE") != null ? rs.getString("EPHONE") : "";
			emp.setPhone(ePhone);

			String eMobile = rs.getString("EMOB") != null ? rs.getString("EMOB") : "";
			emp.setMobile(eMobile);

			String eMailId = rs.getString("EMAILID") != null ? rs.getString("EMAILID") : "";
			emp.setMail_id(eMailId);

			String eBloodGroup = rs.getString("EBGROUP") != null ? rs.getString("EBGROUP") : "";
			emp.setBlood_group(eBloodGroup);

			String eBirthDate = rs.getString("EBDATE") != null ? sformat.format(rs.getDate("EBDATE")) : "";
			emp.setDate_birth(eBirthDate);

			String eJoinDate = rs.getString("EJDATE") != null ? sformat.format(rs.getDate("EJDATE")) : "";
			emp.setDate_join(eJoinDate);

			String eLeftDate = rs.getString("ELDATE") != null ? sformat.format(rs.getDate("ELDATE")) : "";
			emp.setDate_left(eLeftDate);

			String eCountryCode = rs.getString("ECONCODE") != null ? rs.getString("ECONCODE") : "";
			emp.setCountry_name(eCountryCode);

			String eCompanyCode = rs.getString("ECOMCODE") != null ? rs.getString("ECOMCODE") : "";
			emp.setCompany_code(eCompanyCode);

			String eCategory = rs.getString("ECATG") != null ? rs.getString("ECATG") : "";
			emp.setEmp_catg(eCategory);

			String eRegion = rs.getString("EREGION") != null ? rs.getString("EREGION") : "";
			emp.setRegion_name(eRegion);

			String eLocation = rs.getString("ELOC") != null ? rs.getString("ELOC") : "";
			emp.setLoc_name(eLocation);

			String eSubArea = rs.getString("ESUBAREA") != null ? rs.getString("ESUBAREA") : "";
			emp.setSub_area(eSubArea);

			String eFunction = rs.getString("EFUN") != null ? rs.getString("EFUN") : "";
			emp.setFunction(eFunction);

			String eSubfunction = rs.getString("ESUBFUN") != null ? rs.getString("ESUBFUN") : "";
			emp.setSub_function(eSubfunction);

			String eCostCentre = rs.getString("COSTCENTRE") != null ? rs.getString("COSTCENTRE") : "";
			emp.setCost_centre(eCostCentre);

			String eHrBand = rs.getString("HRBAND") != null ? rs.getString("HRBAND") : "";
			emp.setHr_band(eHrBand);

			String eActiveFlag = rs.getString("EFLAG") != null ? rs.getString("EFLAG") : "";
			emp.setActive_flag(eActiveFlag);

			String eHodId = rs.getString("EHODID") != null ? rs.getString("EHODID") : "";
			emp.setHod_id(eHodId);

			String hName = rs.getString("HNAME") != null ? rs.getString("HNAME") : "";
			emp.setHod_name(hName);

			String hMailId = rs.getString("HMAILID") != null ? rs.getString("HMAILID") : "";
			emp.setHod_mail_id(hMailId);

			String hDesig = rs.getString("HDESIG") != null ? rs.getString("HDESIG") : "";
			emp.setHod_desig(hDesig);

			return emp;
		}
	}

	
}
