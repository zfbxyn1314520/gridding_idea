package com.eollse.bo;

import java.util.List;
import java.util.Map;

import com.eollse.po.Event;
import com.eollse.po.EventLogApp;
import com.eollse.po.Event_level;
import com.eollse.po.Event_log;
import com.eollse.po.Event_solve_status;
import com.eollse.po.Event_source_type;
import com.eollse.po.Event_type;

public interface EventBo {

	public Map<String, Object> getAllEventByAreaId(List<Integer> areaIds,
			Integer pageSize, Integer pageCurrent);

	public List<Event_level> getAllEventLevel();

	public List<Event_source_type> getAllType();

	public List<Event_solve_status> getAllStatus();

	public Integer getAllEventCount(List<Integer> areaIds);

	public Integer saveEvent(Event event);

	public Integer saveEventLog(Event_log eventLog);

	public Integer getMaxId();

	public List<Event_log> getEventLogById(Integer eventId);

	public Event getEventById(Integer eventId);

	public Integer editEventById(Event event);

	public Integer editEventLogById(Event_log eventLog);

	public List<Event> getOneEventById(Integer eventId);

	public Integer deleteEvent(List<Integer> eventIds);

	public List<Integer> getEventLogIdById(List<Integer> eventIds);

	public Integer deleteEventLog(List<Integer> eventLogIds);

	public List<Event_type> getAllEventType();

	public void editIsFinished(Integer eventId);

	public Map<String, Object> getAllEventInfoById(List<Integer> gridStaffIds,String field, Integer pageSize, Integer pageCurrent);

	public List<EventLogApp> getOneEventLogInfoById(Integer eventId);

	public void editFinished(Integer eventId);

}
