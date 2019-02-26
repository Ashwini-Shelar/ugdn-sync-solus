package com.upl.ugdnsyncsolus.model;

import java.io.Serializable;

public class EmployeeDetailsModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String uid_no;
	private String old_ecode;
	private String bus_name;
	private String first_name;
	private String middle_name;
	private String last_name;
	private String display_name;
	private String desig;
	private String dept;
	private String phone;
	private String mobile;
	private String mail_id;
	private String blood_group;
	private String date_birth;
	private String date_join;
	private String date_left;

	private String loc_name;
	private String country_name;
	private String company_code;
	private String emp_catg;
	private String region_name;
	private String sub_area;
	private String function;
	private String sub_function;
	private String cost_centre;
	private String hr_band;

	private String active_flag;
	private String hod_id;
	private String hod_name;
	private String hod_mail_id;
	private String hod_desig;

	public String getUid_no() {
		return uid_no;
	}

	public void setUid_no(String uid_no) {
		this.uid_no = uid_no;
	}

	public String getOld_ecode() {
		return old_ecode;
	}

	public void setOld_ecode(String old_ecode) {
		this.old_ecode = old_ecode;
	}

	public String getBus_name() {
		return bus_name;
	}

	public void setBus_name(String bus_name) {
		this.bus_name = bus_name;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getMiddle_name() {
		return middle_name;
	}

	public void setMiddle_name(String middle_name) {
		this.middle_name = middle_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getDisplay_name() {
		return display_name;
	}

	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}

	public String getDesig() {
		return desig;
	}

	public void setDesig(String desig) {
		this.desig = desig;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getHod_id() {
		return hod_id;
	}

	public void setHod_id(String hod_id) {
		this.hod_id = hod_id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMail_id() {
		return mail_id;
	}

	public void setMail_id(String mail_id) {
		this.mail_id = mail_id;
	}

	public String getBlood_group() {
		return blood_group;
	}

	public void setBlood_group(String blood_group) {
		this.blood_group = blood_group;
	}

	public String getDate_birth() {
		return date_birth;
	}

	public void setDate_birth(String date_birth) {
		this.date_birth = date_birth;
	}

	public String getDate_join() {
		return date_join;
	}

	public void setDate_join(String date_join) {
		this.date_join = date_join;
	}

	public String getDate_left() {
		return date_left;
	}

	public void setDate_left(String date_left) {
		this.date_left = date_left;
	}

	public String getLoc_name() {
		return loc_name;
	}

	public void setLoc_name(String loc_name) {
		this.loc_name = loc_name;
	}

	public String getCountry_name() {
		return country_name;
	}

	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}

	public String getCompany_code() {
		return company_code;
	}

	public void setCompany_code(String company_code) {
		this.company_code = company_code;
	}

	public String getEmp_catg() {
		return emp_catg;
	}

	public void setEmp_catg(String emp_catg) {
		this.emp_catg = emp_catg;
	}

	public String getRegion_name() {
		return region_name;
	}

	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}

	public String getSub_area() {
		return sub_area;
	}

	public void setSub_area(String sub_area) {
		this.sub_area = sub_area;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getSub_function() {
		return sub_function;
	}

	public void setSub_function(String sub_function) {
		this.sub_function = sub_function;
	}

	public String getCost_centre() {
		return cost_centre;
	}

	public void setCost_centre(String cost_centre) {
		this.cost_centre = cost_centre;
	}

	public String getActive_flag() {
		return active_flag;
	}

	public void setActive_flag(String active_flag) {
		this.active_flag = active_flag;
	}

	public String getHod_name() {
		return hod_name;
	}

	public void setHod_name(String hod_name) {
		this.hod_name = hod_name;
	}

	public String getHod_mail_id() {
		return hod_mail_id;
	}

	public void setHod_mail_id(String hod_mail_id) {
		this.hod_mail_id = hod_mail_id;
	}

	public String getHod_desig() {
		return hod_desig;
	}

	public void setHod_desig(String hod_desig) {
		this.hod_desig = hod_desig;
	}

	@Override
	public String toString() {
		return "EmployeeDetailsModel [uid_no=" + uid_no + ", old_ecode=" + old_ecode + ", bus_name=" + bus_name
				+ ", first_name=" + first_name + ", middle_name=" + middle_name + ", last_name=" + last_name
				+ ", display_name=" + display_name + ", desig=" + desig + ", dept=" + dept + ", phone=" + phone
				+ ", mobile=" + mobile + ", mail_id=" + mail_id + ", blood_group=" + blood_group + ", date_birth="
				+ date_birth + ", date_join=" + date_join + ", date_left=" + date_left + ", loc_name=" + loc_name
				+ ", country_name=" + country_name + ", company_code=" + company_code + ", emp_catg=" + emp_catg
				+ ", region_name=" + region_name + ", sub_area=" + sub_area + ", function=" + function
				+ ", sub_function=" + sub_function + ", cost_centre=" + cost_centre + ", hr_band=" + hr_band
				+ ", active_flag=" + active_flag + ", hod_id=" + hod_id + ", hod_name=" + hod_name + ", hod_mail_id="
				+ hod_mail_id + ", hod_desig=" + hod_desig + "]";
	}

	public String getHr_band() {
		return hr_band;
	}

	public void setHr_band(String hr_band) {
		this.hr_band = hr_band;
	}

}
