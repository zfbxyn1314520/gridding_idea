package com.eollse.bo.impl;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eollse.bo.EventBo;
import com.eollse.dao.EventDao;
import com.eollse.po.Event;
import com.eollse.po.EventApp;
import com.eollse.po.EventLogApp;
import com.eollse.po.Event_level;
import com.eollse.po.Event_log;
import com.eollse.po.Event_solve_status;
import com.eollse.po.Event_source_type;
import com.eollse.po.Event_type;
import com.eollse.util.HtmlConvertTextUtil;

@Service
public class EventBoImpl implements EventBo {
    @Autowired
    private EventDao eventDao;

    @Override
    public Map<String, Object> getAllEventByAreaId(List<Integer> areaIds,
                                                   Integer pageSize, Integer pageCurrent) {
        // TODO Auto-generated method stub
        Integer x = (pageCurrent - 1) * pageSize;
        Integer y = pageCurrent * pageSize;
        Map<String, Object> map = new HashMap<String, Object>();
        List<Event> events = this.eventDao.getAllEventByAreaId(areaIds, x, y);
        int totalRow = this.eventDao.getAllEventCount(areaIds);
        map.put("totalRow", totalRow);
        map.put("pageCurrent", pageCurrent);
        map.put("list", events);
        return map;
    }

    @Override
    public List<Event_level> getAllEventLevel() {
        // TODO Auto-generated method stub
        return this.eventDao.getAllEventLevel();
    }

    @Override
    public List<Event_source_type> getAllType() {
        // TODO Auto-generated method stub
        return this.eventDao.getAllType();
    }

    @Override
    public List<Event_solve_status> getAllStatus() {
        // TODO Auto-generated method stub
        return this.eventDao.getAllStatus();
    }

    @Override
    public Integer getAllEventCount(List<Integer> areaIds) {
        // TODO Auto-generated method stub
        return this.eventDao.getAllEventCount(areaIds);
    }

    @Override
    public Integer saveEvent(Event event) {
        // TODO Auto-generated method stub
        return this.eventDao.saveEvent(event);
    }

    @Override
    public Integer saveEventLog(Event_log eventLog) {
        // TODO Auto-generated method stub
        return this.eventDao.saveEventLog(eventLog);
    }

    @Override
    public Integer getMaxId() {
        // TODO Auto-generated method stub
        return this.eventDao.getMaxId();
    }

    @Override
    public List<Event_log> getEventLogById(Integer eventId) {
        // TODO Auto-generated method stub
        return this.eventDao.getEventLogById(eventId);
    }

    @Override
    public Event getEventById(Integer eventId) {
        // TODO Auto-generated method stub
        return this.eventDao.getEventById(eventId);
    }

    @Override
    public Integer editEventById(Event event) {
        // TODO Auto-generated method stub
        return this.eventDao.editEventById(event);
    }

    @Override
    public Integer editEventLogById(Event_log eventLog) {
        // TODO Auto-generated method stub
        return this.eventDao.editEventLogById(eventLog);
    }

    @Override
    public List<Event> getOneEventById(Integer eventId) {
        // TODO Auto-generated method stub
        return this.eventDao.getOneEventById(eventId);
    }

    @Override
    public Integer deleteEvent(List<Integer> eventIds) {
        // TODO Auto-generated method stub
        return this.eventDao.deleteEvent(eventIds);
    }

    @Override
    public List<Integer> getEventLogIdById(List<Integer> eventIds) {
        // TODO Auto-generated method stub
        return this.eventDao.getEventLogIdById(eventIds);
    }

    @Override
    public Integer deleteEventLog(List<Integer> eventLogIds) {
        // TODO Auto-generated method stub
        return this.eventDao.deleteEventLog(eventLogIds);
    }

    @Override
    public List<Event_type> getAllEventType() {
        // TODO Auto-generated method stub
        return this.eventDao.getAllEventType();
    }

    @Override
    public void editIsFinished(Integer eventId) {
        // TODO Auto-generated method stub
        this.eventDao.editIsFinished(eventId);
    }

    @Override
    public Map<String, Object> getAllEventInfoById(List<Integer> gridStaffIds, String field, Integer type, Integer pageSize, Integer pageCurrent) {
        // TODO Auto-generated method stub
        Integer x = (pageCurrent - 1) * pageSize;
        Integer y = pageCurrent * pageSize;
        Map<String, Object> map = new HashMap<String, Object>();
        HtmlConvertTextUtil parser = new HtmlConvertTextUtil();
        Reader reader = null;
        List<EventApp> events = this.eventDao.getAllEventInfoById(gridStaffIds, field, type, x, y);
        Integer length = events.size();
        for (int i = 0; i < length; i++) {
            reader = new StringReader(events.get(i).getEventContent());
            try {
                parser.parse(reader);
                reader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            events.get(i).setEventContent(parser.getText());
        }
        Integer totalRow = this.eventDao.getAllEventInfoCount(gridStaffIds, field, type);
        map.put("totalRow", totalRow);
        map.put("pageCurrent", pageCurrent);
        map.put("list", events);
        return map;
    }

    @Override
    public List<EventLogApp> getOneEventLogInfoById(Integer eventId) {
        // TODO Auto-generated method stub
        return this.eventDao.getOneEventLogInfoById(eventId);
    }

    @Override
    public void editFinished(Integer eventId) {
        // TODO Auto-generated method stub
        this.eventDao.editFinished(eventId);
    }

}
