package com.eollse.po;

import java.io.Serializable;

public class Event_type implements Serializable {
	private Integer eventTypeId;
	private String eventTypeName;
	

	public Event_type() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Event_type(Integer eventTypeId, String eventTypeName) {
		super();
		this.eventTypeId = eventTypeId;
		this.eventTypeName = eventTypeName;
	}

	@Override
	public String toString() {
		return "Event_type [eventTypeId=" + eventTypeId + ", eventTypeName="
				+ eventTypeName + "]";
	}

	public Integer getEventTypeId() {
		return eventTypeId;
	}

	public void setEventTypeId(Integer eventTypeId) {
		this.eventTypeId = eventTypeId;
	}

	public String getEventTypeName() {
		return eventTypeName;
	}

	public void setEventTypeName(String eventTypeName) {
		this.eventTypeName = eventTypeName;
	}
	
	
}
