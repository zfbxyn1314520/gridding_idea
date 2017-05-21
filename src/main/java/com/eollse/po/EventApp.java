package com.eollse.po;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class EventApp implements Serializable {

	private Integer eventId;
	private String eventTitle;
	private String eventContent;
	private String eventPic;
	@DateTimeFormat(pattern = "yyyy年MM月dd日 HH:mm:ss")
	private Date editEventDate;
	private Integer isImportant;
	private Integer isFinished;
	private GridApp gridApp;
	private AreaApp areaApp;
	private GridStaffApp gridStaffApp;
	private Event_level eventLevel;
	private Event_source_type sourceType;
	private Event_type eventType;

	public EventApp() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "EventApp [eventId=" + eventId + ", eventTitle=" + eventTitle
				+ ", eventContent=" + eventContent + ", eventPic=" + eventPic
				+ ", editEventDate=" + editEventDate + ", isImportant="
				+ isImportant + ", isFinished=" + isFinished + ", gridApp="
				+ gridApp + ", areaApp=" + areaApp + ", gridStaffApp="
				+ gridStaffApp + ", eventLevel=" + eventLevel + ", sourceType="
				+ sourceType + ", eventType=" + eventType + "]";
	}

	public EventApp(Integer eventId, String eventTitle, String eventContent,
			String eventPic, Date editEventDate, Integer isImportant,
			Integer isFinished, GridApp gridApp, AreaApp areaApp,
			GridStaffApp gridStaffApp, Event_level eventLevel,
			Event_source_type sourceType, Event_type eventType) {
		super();
		this.eventId = eventId;
		this.eventTitle = eventTitle;
		this.eventContent = eventContent;
		this.eventPic = eventPic;
		this.editEventDate = editEventDate;
		this.isImportant = isImportant;
		this.isFinished = isFinished;
		this.gridApp = gridApp;
		this.areaApp = areaApp;
		this.gridStaffApp = gridStaffApp;
		this.eventLevel = eventLevel;
		this.sourceType = sourceType;
		this.eventType = eventType;
	}

	public GridApp getGridApp() {
		return gridApp;
	}

	public void setGridApp(GridApp gridApp) {
		this.gridApp = gridApp;
	}

	public AreaApp getAreaApp() {
		return areaApp;
	}

	public void setAreaApp(AreaApp areaApp) {
		this.areaApp = areaApp;
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public String getEventTitle() {
		return eventTitle;
	}

	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

	public String getEventContent() {
		return eventContent;
	}

	public void setEventContent(String eventContent) {
		this.eventContent = eventContent;
	}

	public String getEventPic() {
		return eventPic;
	}

	public void setEventPic(String eventPic) {
		this.eventPic = eventPic;
	}

	public Date getEditEventDate() {
		return editEventDate;
	}

	public void setEditEventDate(Date editEventDate) {
		this.editEventDate = editEventDate;
	}

	public Integer getIsImportant() {
		return isImportant;
	}

	public void setIsImportant(Integer isImportant) {
		this.isImportant = isImportant;
	}

	public Integer getIsFinished() {
		return isFinished;
	}

	public void setIsFinished(Integer isFinished) {
		this.isFinished = isFinished;
	}

	public GridStaffApp getGridStaffApp() {
		return gridStaffApp;
	}

	public void setGridStaffApp(GridStaffApp gridStaffApp) {
		this.gridStaffApp = gridStaffApp;
	}

	public Event_level getEventLevel() {
		return eventLevel;
	}

	public void setEventLevel(Event_level eventLevel) {
		this.eventLevel = eventLevel;
	}

	public Event_source_type getSourceType() {
		return sourceType;
	}

	public void setSourceType(Event_source_type sourceType) {
		this.sourceType = sourceType;
	}

	public Event_type getEventType() {
		return eventType;
	}

	public void setEventType(Event_type eventType) {
		this.eventType = eventType;
	}

}
