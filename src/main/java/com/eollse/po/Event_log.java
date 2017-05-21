package com.eollse.po;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Event_log implements Serializable {
	private Integer eventLogId;
	private Integer eventId;
	private String eventLogContent;
	private String editEventLogName;
	@DateTimeFormat(pattern = "yyyy年MM月dd日 HH:mm:ss")
	private Date editEventLogDate;
	private Event event;
	private Integer solveStatusId;
	private Event_solve_status solveStatus;

	public Event_log() {
		super();
		// TODO Auto-generated constructor stub
		this.event = new Event();
		this.solveStatus = new Event_solve_status();
	}

	@Override
	public String toString() {
		return "Event_log [eventLogId=" + eventLogId + ", eventId=" + eventId
				+ ", eventLogContent=" + eventLogContent
				+ ", editEventLogName=" + editEventLogName
				+ ", editEventLogDate=" + editEventLogDate + ", event=" + event
				+ ", solveStatusId=" + solveStatusId + ", solveStatus="
				+ solveStatus + "]";
	}

	public Event_log(Integer eventLogId, Integer eventId,
			String eventLogContent, String editEventLogName,
			Date editEventLogDate, Event event, Integer solveStatusId,
			Event_solve_status solveStatus) {
		super();
		this.eventLogId = eventLogId;
		this.eventId = eventId;
		this.eventLogContent = eventLogContent;
		this.editEventLogName = editEventLogName;
		this.editEventLogDate = editEventLogDate;
		this.event = event;
		this.solveStatusId = solveStatusId;
		this.solveStatus = solveStatus;
	}

	public Integer getSolveStatusId() {
		return solveStatusId;
	}

	public void setSolveStatusId(Integer solveStatusId) {
		this.solveStatusId = solveStatusId;
	}

	public Event_solve_status getSolveStatus() {
		return solveStatus;
	}

	public void setSolveStatus(Event_solve_status solveStatus) {
		this.solveStatus = solveStatus;
	}

	public Integer getEventLogId() {
		return eventLogId;
	}

	public void setEventLogId(Integer eventLogId) {
		this.eventLogId = eventLogId;
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public String getEventLogContent() {
		return eventLogContent;
	}

	public void setEventLogContent(String eventLogContent) {
		this.eventLogContent = eventLogContent;
	}

	public String getEditEventLogName() {
		return editEventLogName;
	}

	public void setEditEventLogName(String editEventLogName) {
		this.editEventLogName = editEventLogName;
	}

	public Date getEditEventLogDate() {
		return editEventLogDate;
	}

	public void setEditEventLogDate(Date editEventLogDate) {
		this.editEventLogDate = editEventLogDate;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

}
