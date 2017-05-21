package com.eollse.po;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Event implements Serializable {
	private Integer eventId;
	private Integer areaId;
	private Integer gridId;
	private Integer gridStaffId;
	private Integer eventLevelId;
	private Integer sourceTypeId;
	private String eventTitle;
	private String eventContent;
	private Integer eventTypeId;
	private String eventPic;
	@DateTimeFormat(pattern = "yyyy年MM月dd日 HH:mm:ss")
	private Date editEventDate;
	private Grid grid;
	private Area area;
	private Grid_staff gridStaff;
	private Event_level eventLevel;
	private Event_source_type sourceType;
	private Integer isImportant;
	private Event_type eventType;
	private Integer isFinished;

	public Event() {
		super();
		// TODO Auto-generated constructor stub
		this.grid = new Grid();
		this.area = new Area();
		this.gridStaff = new Grid_staff();
		this.eventLevel = new Event_level();
		this.sourceType = new Event_source_type();
		this.eventType = new Event_type();
	}

	@Override
	public String toString() {
		return "Event [eventId=" + eventId + ", areaId=" + areaId + ", gridId="
				+ gridId + ", gridStaffId=" + gridStaffId + ", eventLevelId="
				+ eventLevelId + ", sourceTypeId=" + sourceTypeId
				+ ", eventTitle=" + eventTitle + ", eventContent="
				+ eventContent + ", eventTypeId=" + eventTypeId + ", eventPic="
				+ eventPic + ", editEventDate=" + editEventDate + ", grid="
				+ grid + ", area=" + area + ", gridStaff=" + gridStaff
				+ ", eventLevel=" + eventLevel + ", sourceType=" + sourceType
				+ ", isImportant=" + isImportant + ", eventType=" + eventType
				+ ", isFinished=" + isFinished + "]";
	}

	public Event(Integer eventId, Integer areaId, Integer gridId,
			Integer gridStaffId, Integer eventLevelId, Integer sourceTypeId,
			String eventTitle, String eventContent, Integer eventTypeId,
			String eventPic, Date editEventDate, Grid grid, Area area,
			Grid_staff gridStaff, Event_level eventLevel,
			Event_source_type sourceType, Integer isImportant,
			Event_type eventType, Integer isFinished) {
		super();
		this.eventId = eventId;
		this.areaId = areaId;
		this.gridId = gridId;
		this.gridStaffId = gridStaffId;
		this.eventLevelId = eventLevelId;
		this.sourceTypeId = sourceTypeId;
		this.eventTitle = eventTitle;
		this.eventContent = eventContent;
		this.eventTypeId = eventTypeId;
		this.eventPic = eventPic;
		this.editEventDate = editEventDate;
		this.grid = grid;
		this.area = area;
		this.gridStaff = gridStaff;
		this.eventLevel = eventLevel;
		this.sourceType = sourceType;
		this.isImportant = isImportant;
		this.eventType = eventType;
		this.isFinished = isFinished;
	}

	public Integer getIsFinished() {
		return isFinished;
	}

	public void setIsFinished(Integer isFinished) {
		this.isFinished = isFinished;
	}

	public String getEventPic() {
		return eventPic;
	}

	public void setEventPic(String eventPic) {
		this.eventPic = eventPic;
	}

	public Event_type getEventType() {
		return eventType;
	}

	public void setEventType(Event_type eventType) {
		this.eventType = eventType;
	}

	public Integer getEventTypeId() {
		return eventTypeId;
	}

	public void setEventTypeId(Integer eventTypeId) {
		this.eventTypeId = eventTypeId;
	}

	public Integer getIsImportant() {
		return isImportant;
	}

	public void setIsImportant(Integer isImportant) {
		this.isImportant = isImportant;
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getGridId() {
		return gridId;
	}

	public void setGridId(Integer gridId) {
		this.gridId = gridId;
	}

	public Integer getGridStaffId() {
		return gridStaffId;
	}

	public void setGridStaffId(Integer gridStaffId) {
		this.gridStaffId = gridStaffId;
	}

	public Integer getEventLevelId() {
		return eventLevelId;
	}

	public void setEventLevelId(Integer eventLevelId) {
		this.eventLevelId = eventLevelId;
	}

	public Integer getSourceTypeId() {
		return sourceTypeId;
	}

	public void setSourceTypeId(Integer sourceTypeId) {
		this.sourceTypeId = sourceTypeId;
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

	public Date getEditEventDate() {
		return editEventDate;
	}

	public void setEditEventDate(Date editEventDate) {
		this.editEventDate = editEventDate;
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Grid_staff getGridStaff() {
		return gridStaff;
	}

	public void setGridStaff(Grid_staff gridStaff) {
		this.gridStaff = gridStaff;
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

}
