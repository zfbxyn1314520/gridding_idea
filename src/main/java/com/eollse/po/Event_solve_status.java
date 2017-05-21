package com.eollse.po;

import java.io.Serializable;

public class Event_solve_status implements Serializable {
	private Integer solveStatusId;
	private String solveStatusName;

	public Event_solve_status() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Event_solve_status(Integer solveStatusId, String solveStatusName) {
		super();
		this.solveStatusId = solveStatusId;
		this.solveStatusName = solveStatusName;
	}

	@Override
	public String toString() {
		return "Event_solve_status [solveStatusId=" + solveStatusId
				+ ", solveStatusName=" + solveStatusName + "]";
	}

	public Integer getSolveStatusId() {
		return solveStatusId;
	}

	public void setSolveStatusId(Integer solveStatusId) {
		this.solveStatusId = solveStatusId;
	}

	public String getSolveStatusName() {
		return solveStatusName;
	}

	public void setSolveStatusName(String solveStatusName) {
		this.solveStatusName = solveStatusName;
	}

}
