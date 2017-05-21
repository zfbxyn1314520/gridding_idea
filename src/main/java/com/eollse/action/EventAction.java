package com.eollse.action;

/**
 * author 刘春晓
 * content 事件控制器
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eollse.bo.AreaBo;
import com.eollse.bo.EventBo;
import com.eollse.po.Event;
import com.eollse.po.Event_level;
import com.eollse.po.Event_log;
import com.eollse.po.Event_solve_status;
import com.eollse.po.Event_source_type;
import com.eollse.po.Event_type;
import com.eollse.po.User;

@Controller
@RequestMapping(value="/event")
public class EventAction extends CommonAction {
	@Autowired
	private EventBo eventBo;
	@Autowired
	private AreaBo areaBo;
	
	/**
	 * 获取所有事件
	 * @param session 用户登录session
	 * @param pageSize 分页大小
	 * @param pageCurrent 当前页码
	 * @return
	 */
	@RequestMapping(value="/getAllEventByAreaId")
	@ResponseBody
	public String getAllEventByAreaId(HttpSession session,Integer pageSize, Integer pageCurrent){
		User user=(User)session.getAttribute("user");
		List<Integer> areaIds=(List<Integer>)session.getAttribute("areaIds");
		if(user!=null){
			Map<String,Object> map=this.eventBo.getAllEventByAreaId(areaIds,pageSize,pageCurrent);
			String content=this.createPageJSONString(map);
			return content;
		}else{
			return null;
		}
	}
	
	/**
	 * 获取所有事件等级
	 * @return 事件等级集合
	 */
	@RequestMapping(value="/getAllEventLevel")
	@ResponseBody
	public List<Event_level> getAllEventLevel(){
		List<Event_level> eventLevels=this.eventBo.getAllEventLevel();
		return eventLevels;
	}
	
	/**
	 * 事件来源
	 * @return 事件来源集合
	 */
	@RequestMapping(value="/getAllType")
	@ResponseBody
	public List<Event_source_type> getAllType(){
		List<Event_source_type> sourceType=this.eventBo.getAllType();
		return sourceType;
	}
	
	/**
	 * 事件处理状态
	 * @return 处理状态集合
	 */
	@RequestMapping(value="/getAllStatus")
	@ResponseBody
	public List<Event_solve_status> getAllStatus(){
		List<Event_solve_status> solveStatus=this.eventBo.getAllStatus();
		return solveStatus;
	}
	
	@RequestMapping(value="/getAllEventType")
	@ResponseBody
	public List<Event_type> getAllEventType(){
		List<Event_type> eventTypes=this.eventBo.getAllEventType();
		return eventTypes;
	}
	
	/**
	 * 保存方法
	 * @param event 事件实体
	 * @param eventLog 事件追踪记录实体
	 * @param session
	 * @param request 请求
	 * @return string “1”==>表示保存成功 “0”==>表示保存失败
	 */
	@RequestMapping(value="/saveEvent")
	@ResponseBody
	public String saveEvent(Event event,HttpSession session,HttpServletRequest request){
		Integer result=this.eventBo.saveEvent(event);
		if(result>0){
			logger.info("成功添加事件"+ event.getEventTitle() + "！");
		}
		return  result>0 ? "1" : "0";
	}
	
	/**
	 * 获取修改所需数据
	 * @param upELid 事件Id
	 * @return 事件追踪实体
	 */
	@RequestMapping(value="/getEventById")
	@ResponseBody
	public Event_log getEventById(String upELid){
		Integer eventId=Integer.parseInt(upELid);
		List<Event_log> eventLogs=this.eventBo.getEventLogById(eventId);
		if(eventLogs.size()==0){
			eventLogs.add(new Event_log());
		}
		Event event=this.eventBo.getEventById(eventId);
		eventLogs.get(0).setEvent(event);
		return eventLogs.get(0);
	}
	
	/**
	 * 修改方法
	 * @param event 事件实体
	 * @param eventLog 事件追踪实体
	 * @param session
	 * @param request
	 * @return string “1”==>表示修改成功 “0”==>表示修改失败
	 */
	@RequestMapping(value="editEventById")
	@ResponseBody
	public String editEventById(Event event,HttpSession session,HttpServletRequest request){
		Integer result=this.eventBo.editEventById(event);
		if(result>0){
			logger.info("成功修改事件"	+ event.getEventTitle() + "！");
		}
		return result>0 ? "1" : "0";
	}
	
	/**
	 * 查看方法
	 * @param eventId 事件Id
	 * @return 事件追踪实体
	 */
	@RequestMapping(value="/getOneEventById")
	@ResponseBody
	public Event getOneEventById(Integer eventId){
		List<Event> events=this.eventBo.getOneEventById(eventId);
		return events.get(0);
	}
	
	@RequestMapping(value="/getEventLogById")
	@ResponseBody
	public List<Event_log> getEventLogById(Integer eventId){
		List<Event_log> eventLogs=this.eventBo.getEventLogById(eventId);
		if(eventLogs.size()==0){
			Event_log eventLog=new Event_log();
			List<Event> events=this.eventBo.getOneEventById(eventId);
			eventLog.setEvent(events.get(0));
			eventLogs.add(eventLog);
		}
		return eventLogs;
	}
	
	/**
	 * 删除事件
	 * @param deElid 事件Id
	 * @param nameStr 事件名称
	 * @param session
	 * @param request
	 * @return string “1”==>表示删除成功 “0”==>表示删除失败
	 */
	@RequestMapping(value="deleteEvent")
	@ResponseBody
	public String deleteEvent(String deElid,String nameStr,HttpSession session,HttpServletRequest request){
		User user=(User)session.getAttribute("user");
		String[] str=deElid.split(",");
		List<Integer> eventIds=new ArrayList<Integer>();
		for(int i=0;i<str.length;i++){
			eventIds.add(i,Integer.parseInt(str[i]));
		}
		List<Integer> eventLogIds=this.eventBo.getEventLogIdById(eventIds);
		if(eventLogIds.size()!=0){
			Integer resultL=this.eventBo.deleteEventLog(eventLogIds);
		}
		Integer result=this.eventBo.deleteEvent(eventIds);
		if(result>0){
			logger.info("成功删除事件"+nameStr+ "信息！");
		}
		return result>0 ? "1" : "0";
	}
	
	
	/**
	 * 处理事件(添加事件追踪日志)
	 * @param eventLog 事件日志实体
	 * @param session 会话
	 * @return
	 */
	@RequestMapping(value="/saveEventLog")
	@ResponseBody
	public String saveEventLog(Event_log eventLog,HttpSession session){
		User user=(User)session.getAttribute("user");
		List<Event_log> eventLogs=this.eventBo.getEventLogById(eventLog.getEventId());
		if(eventLogs.size()>0){
			if(eventLog.getSolveStatusId()==1){
				eventLog.setEditEventLogDate(new Date());
				eventLog.setEditEventLogName(user.getUserName());
				Integer resultE=this.eventBo.editEventLogById(eventLog);
				return resultE>0 ? "1" : "0";
			}else{
				if(eventLogs.size()==1){
					eventLog.setEditEventLogDate(new Date());
					eventLog.setEditEventLogName(user.getUserName());
					this.eventBo.editFinished(eventLog.getEventId());
					Integer resultL=this.eventBo.saveEventLog(eventLog);
					return resultL>0 ? "1" : "0";
				}else{
					return "3";
				}
			}
		}else{
			eventLog.setEditEventLogDate(new Date());
			eventLog.setEditEventLogName(user.getUserName());
			this.eventBo.editIsFinished(eventLog.getEventId());
			Integer result=this.eventBo.saveEventLog(eventLog);
			return result>0 ? "1" : "0";
		}
	}
}
