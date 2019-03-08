package com.upl.ugdnsyncsolus.model;

public class SolusResponseModel {
	private long id;
	private String uid_no;
	private String status;
	private String solus_id;

	public String getUid_no() {
		return uid_no;
	}

	public void setUid_no(String uid_no) {
		this.uid_no = uid_no;
	}

	@Override
	public String toString() {
		return "SolusResponseModel [uid_no=" + uid_no + ", status=" + status + ", solus_id=" + solus_id + "]";
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSolus_id() {
		return solus_id;
	}

	public void setSolus_id(String solus_id) {
		this.solus_id = solus_id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
