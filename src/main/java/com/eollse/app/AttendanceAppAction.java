package com.eollse.app;

/**
 * author 李宁财
 * content App考勤控制器
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.eollse.action.CommonAction;
import com.eollse.bo.AttendanceBo;
import com.eollse.bo.UserBo;
import com.eollse.po.Attendance;
import com.eollse.po.User;

@Controller
@RequestMapping("/app/work")
public class AttendanceAppAction extends CommonAction {
	
	@Autowired
	private AttendanceBo attendanceBo;
	@Autowired
	private UserBo userBo;
	
	/**
	 * 网格员App上签到打卡
	 * @param attendance 考勤实体类
	 * @param start_time签到时间
	 * @param start_site签到地点
	 * @param start_memo签到备注
	 * @param attendance_pic打卡图片
	 * @return 信息反馈
	 */
	@RequestMapping(value="/addStaffClockInInfo", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String  addStaffClockInInfo(Attendance attendance, HttpSession session){
		User user = (User) session.getAttribute("user");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String nowTime=sdf.format(new Date());
		if(user.getRole().getRoleLevel()<6){
			return "{\"statusCode\":300,\"message\":\"你的账户不需要签到！\"}";
		}else{
			Integer gridStaffId = this.userBo.getStaffIdByUserId(user.getUserId());
			List<Attendance> list = this.attendanceBo.validateSignIn(gridStaffId,nowTime);
			if(list.size() == 0){
				if(attendance.getAttendance_pic() != ""){
					attendance.setGridStaffId(gridStaffId);
					attendance.setRecordDate(nowTime);
					Integer result=this.attendanceBo.addStaffClockInInfo(attendance);
					if(result>0){
						logger.info("签到打卡成功！");
						return "{\"statusCode\":200,\"message\":\"签到成功！\"}";
					}else{
						logger.error("签到打卡失败！");
						return "{\"statusCode\":403,\"message\":\"签到失败！\"}";
					}
				}else{
					return "{\"statusCode\":202,\"message\":\"你今日还未签到！\"}";
				}
			}else{
				return "{\"statusCode\":201,\"message\":\"你今日已签到！\"}";
			}
		}
	}
	
	/**
	 * 上传App考勤图片信息
	 * @param request
	 * @param mainName 用户上传图片文件夹名称 not null
	 * @param picName 如何是上传多张图片应该传图片名称用户生成文件夹
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="/uploadClockInImg", produces = {"application/json;charset=utf-8"})
	@ResponseBody
	public String uploadClockInImg(@RequestParam("mFile")MultipartFile file,HttpServletRequest request, HttpSession session) {
		User user=(User)session.getAttribute("user");
		if(user.getRole().getRoleLevel()<6){
			return "{\"statusCode\":300,\"message\":\"你的账户不需要签到！\"}";
		}else{
			if (!file.isEmpty()) {
	        	// 文件类型限制
	        	boolean result=true;
	        	String[] allowedType = { "image/bmp", "image/gif", "image/jpg", "image/jpeg", "image/png","image/webp" };
	        	for(int i=0;i<allowedType.length;i++){
	        		if(file.getContentType().equals(allowedType[i])){
	        			result=false;
	        		}
	        	}
	        	if(result){
	        		logger.warn("上传文件类型错误，请重新选择上传！");
	        		return "{\"statusCode\":405,\"message\":\"上传文件类型错误，请重新选择上传！\"}";
	        	}
	        	// 文件大小限制
	        	if (file.getSize() > 1 * 1024 * 1024) {
	        		logger.warn("上传文件大小超过1M，请重新选择上传！");
	            	return "{\"statusCode\":406,\"message\":\"上传文件大小超过1M，请重新选择上传！\"}";
	            }
	        	String path = this.fileUpload(request, "app", "attedence/"+user.getUserName());
	        	if(path != null){
	        		return "{\"statusCode\":200,\"message\":\"上传成功！\",\"path\":\"" + path+ "\"}";
	        	}else{
	        		logger.error("上传图片失败，请重新选择上传！");
	        		return "{\"statusCode\":201,\"message\":\"上传失败，请重新选择上传！\"}";
	        	}
	        }else{
	        	logger.error("未选择文件上传图片！");
	        	return "{\"statusCode\":202,\"message\":\"未选择上传图片！\"}";
	        }
		}
	}
	
}
