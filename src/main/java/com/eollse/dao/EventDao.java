package com.eollse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eollse.po.Event;
import com.eollse.po.EventApp;
import com.eollse.po.EventLogApp;
import com.eollse.po.Event_level;
import com.eollse.po.Event_log;
import com.eollse.po.Event_solve_status;
import com.eollse.po.Event_source_type;
import com.eollse.po.Event_type;

public interface EventDao {

	public List<Event> getAllEventByAreaId(@Param("list")List<Integer> areaIds, @Param("x")Integer x, @Param("y")Integer y);

	public int getAllEventCount(@Param("list")List<Integer> areaIds);

	public List<Event_level> getAllEventLevel();

	public List<Event_source_type> getAllType();

	public List<Event_solve_status> getAllStatus();

	public Integer saveEvent(Event event);

	public Integer saveEventLog(Event_log eventLog);

	public Integer getMaxId();

	public List<Event_log> getEventLogById(@Param("eventId")Integer eventId);

	public Event getEventById(Integer eventId);

	public Integer editEventById(Event event);

	public Integer editEventLogById(Event_log eventLog);

	public List<Event> getOneEventById(Integer eventId);

	public Integer deleteEvent(@Param("list")List<Integer> eventIds);

	public List<Integer> getEventLogIdById(@Param("list")List<Integer> eventIds);

	public Integer deleteEventLog(@Param("list")List<Integer> eventLogIds);

	public List<Event_type> getAllEventType();

	public void editIsFinished(Integer eventId);

	public List<EventApp> getAllEventInfoById(
			@Param("list")List<Integer> gridStaffIds,
			@Param("field")String field, 
			@Param("x")Integer x, 
			@Param("y")Integer y
	);

	public int getAllEventInfoCount(
			@Param("list")List<Integer> gridStaffIds, 
			@Param("field")String field
	);

	public List<EventLogApp> getOneEventLogInfoById(@Param("eventId")Integer eventId);

	public void editFinished(Integer eventId);

}
