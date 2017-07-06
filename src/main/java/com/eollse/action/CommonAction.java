package com.eollse.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import com.eollse.util.DateJsonValueProcessor;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.util.Streams;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.eollse.bo.AreaBo;
import com.eollse.po.Area;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

public class CommonAction {

    @Autowired
    private AreaBo areaBo;

    private List<Integer> areaIds;

    protected Logger logger = Logger.getLogger(this.getClass());//日志文件

    public CommonAction() {
        super();
        this.areaIds = new ArrayList<Integer>();
    }

    /**
     * @param @return
     * @return String
     * @throws
     * @Title: getIpAddr
     * @author kaka www.zuidaima.com
     * @Description: 获取客户端IP地址
     */
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1")) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }

    /**
     * 生成datagrid分页需要返回的json字符串信息
     *
     * @param map 分页查询获取的结果集合
     */
    public String createPageJSONString(Map<String, Object> map) {
        JsonConfig jf = new JsonConfig();
        DateJsonValueProcessor djvp =
                new DateJsonValueProcessor();
        djvp.setFormate("yyyy-MM-dd HH:mm:ss");
        jf.registerJsonValueProcessor(java.util.Date.class, djvp);
        String content = "{\"totalRow\":" + map.get("totalRow")
                + ",\"pageCurrent\":" + map.get("pageCurrent") + ",\"list\":";
        String liststr = JSONArray.fromObject(map.get("list"), jf).toString();
        content += liststr + "}";
        return content;
    }

    public JSONArray treeMenuList(JSONArray menuList, Long areaParentCode) {
        JSONArray childMenu = new JSONArray();
        for (Object object : menuList) {
            JSONObject  jsonMenu = JSONObject.fromObject(object);
            Long code = jsonMenu.getLong("areaCode");
            Long parentCode = jsonMenu.getLong("areaParentCode");
            if (areaParentCode == parentCode) {
                JSONArray c_node = treeMenuList(menuList, code);
                jsonMenu.put("childNode", c_node);
                childMenu.add(jsonMenu);
            }
        }
        return childMenu;
    }

    /**
     * 根据传入的areaCode获取该区域所有子节点
     *
     * @param areaCode 区域行政代码
     * @return
     */
    public Area getAllAreaIdById(Long areaCode) {
        Area area = this.areaBo.getAreaByAreaCode(areaCode);
        List<Area> childAreaNodes = this.areaBo.getSubAreaByAreaId(areaCode);
        // 遍历子节点
        for (Area child : childAreaNodes) {
            // 递归
            Area a = getAllAreaIdById(child.getAreaCode());
            a.getNodes().add(a);
        }
        areaIds.add(area.getAreaId());
        return area;
    }


    /**
     * 发送短信验证返回消息判断
     *
     * @param code 返回状态值信息
     * @return
     */
    public String getCaptchaStatus(Integer code) {
        String message = "";
        switch (code) {
            case -1:
                message = "账号未注册！";
                break;
            case -2:
                message = "网络访问超时，请稍后再试！";
                break;
            case -3:
                message = "帐号或密码错误！";
                break;
            case -4:
                message = "只支持单发！";
                break;
            case -5:
                message = "余额不足，请充值！";
                break;
            case -6:
                message = "定时发送时间不是有效的时间格式！";
                break;
            case -7:
                message = "提交信息末尾未签名，请添加中文的企业签名【 】或未采用gb2312编码！";
                break;
            case -8:
                message = "发送内容需在1到300字之间！";
                break;
            case -9:
                message = "发送号码为空！";
                break;
            case -10:
                message = "定时时间不能小于系统当前时间！";
                break;
            case -11:
                message = "屏蔽手机号码！";
                break;
            case -100:
                message = "限制IP访问！";
                break;
        }
        return message;
    }

    /**
     * 上传单张图片方法
     *
     * @param request
     * @param mainName
     * @param picName
     * @return
     */
    public String fileUpload(HttpServletRequest request, String mainName, String picName) {
        String pathStr = "";
        if (picName == null) {
            pathStr = mainName;
        } else {
            pathStr = mainName + "/" + picName;
        }
        String newPath = null;
        Random random = new Random();

        CommonsMultipartResolver cmr = new CommonsMultipartResolver(
                request.getServletContext());
        if (cmr.isMultipart(request)) {// 判断是否有文件上传
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            // 获取所有的上传控件的name属性值
            Iterator<String> upFileNames = multipartRequest.getFileNames();
            while (upFileNames.hasNext()) {
                // 获取上传控件的name属性值，并根据该控件的name属性值提取对应的上传文件信息
                MultipartFile mf = multipartRequest.getFile(upFileNames.next());
                // 获取真正的上传文件的名称
                String fileName = mf.getOriginalFilename();
                if (!fileName.trim().equals("")) {// 判断上传文件是否存在，不存在为""字符串
                    // 获取web项目根目录下的images文件夹路径，作为上传文件的存放路径
                    String path = this.getClass().getResource("/").toString()
                            .replace("WEB-INF/classes/", "")
                            .replace("file:/", "")
                            .concat("images/" + pathStr + "/");
                    try {
                        path = URLDecoder.decode(path, "utf-8");
                        File f = new File(path);
                        if (!f.exists() && !f.isDirectory()) {
                            f.mkdirs();
                        }
                    } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    //将文件名称替换为当前时间字符串，以防止同名文件发生覆盖
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String str = sdf.format(new Date()) + (random.nextInt(999) % (900) + 100);
                    str += fileName.substring(fileName.lastIndexOf("."), fileName.length());
                    // 创建文件对象，指向上传目录下的与上传文件同名的文件对象，此时文件对象还不存在
                    File toFile = new File(path + str);
                    newPath = (path + str);
                    newPath = newPath.substring(newPath.indexOf("images/"), newPath.length());
                    // 将当前上传文件的内容输出到toFile指向的目的地
                    try {
                        mf.transferTo(toFile);
                    } catch (IllegalStateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
        return newPath;
    }

    /**
     * 上传多张图片
     *
     * @param files    多张图片文件
     * @param mainName 文件夹名称
     * @param picName  用户文件夹名称
     * @return
     */
    public String mutilFileUpload(MultipartFile[] files, String mainName, String picName) {
        String imgsUrl = "";
        String errorImg = "";
        if (files.length > 0) {
            Random random = new Random();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String path = this.getClass().getResource("/").toString()
                    .replace("WEB-INF/classes/", "")
                    .replace("file:/", "")
                    .concat("images/" + mainName + picName);
            try {
                path = URLDecoder.decode(path, "utf-8");
                File f = new File(path);
                if (!f.exists() && !f.isDirectory()) {
                    f.mkdirs();
                }
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            String imgPath = "";
            if (files.length > 20) {
                return "{\"statusCode\":203,\"message\":\"上传图片大于20张！\"}";
            } else {
                for (MultipartFile file : files) {
                    try {
                        if (!file.isEmpty()) {
                            boolean result = true;
                            String[] allowedType = {"image/bmp", "image/gif", "image/jpg", "image/jpeg", "image/png", "image/webp"};
                            for (int i = 0; i < allowedType.length; i++) {
                                if (file.getContentType().equals(allowedType[i])) {
                                    result = false;
                                }
                            }
                            if (result) {
                                errorImg += file.getOriginalFilename() + ";";
                                continue;
                            }
                            // 文件大小限制
                            if (file.getSize() > 1 * 1024 * 1024) {
                                errorImg += file.getOriginalFilename() + ";";
                                continue;
                            }
                            String fileName = sdf.format(new Date()) + (random.nextInt(999) % (900) + 100);
                            String name = file.getOriginalFilename();
                            imgPath = path + fileName + name.substring(name.lastIndexOf("."), name.length());
                            imgsUrl += imgPath.substring(imgPath.indexOf("images/"), imgPath.length()) + ";";
                            Streams.copy(file.getInputStream(), new FileOutputStream(imgPath), true);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        return "{\"statusCode\":201,\"message\":\"上传失败，请重新选择上传！\"}";
                    }
                }
                String content = "{\"statusCode\":200,\"message\":\"上传成功！\",\"path\":\"" + imgsUrl + "\"";
                if (errorImg != "") {
                    content += ",\"errorMsg\":\"" + errorImg + "\"";
                }
                content += "}";
                return content;
            }
        } else {
            return "{\"statusCode\":202,\"message\":\"未选择上传图片！\"}";
        }

    }


    public List<Integer> getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(List<Integer> areaIds) {
        this.areaIds = areaIds;
    }




}
