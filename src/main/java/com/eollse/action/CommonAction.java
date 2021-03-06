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
import net.coobird.thumbnailator.Thumbnails;
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

    protected Logger logger = Logger.getLogger(this.getClass());//日志文件

    public CommonAction() {
        super();
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
                    long size = mf.getSize();
                    // 文件大小限制
                    if (size > 1 * 1024 * 1024) {
                        double scale = (1024 * 1024f) / size;
                        try {
                            Thumbnails.of(mf.getInputStream()).scale(scale).outputQuality(scale).toFile(toFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            mf.transferTo(toFile); // 将当前上传文件的内容输出到toFile指向的目的地
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

                            String fileName = sdf.format(new Date()) + (random.nextInt(999) % (900) + 100);
                            String name = file.getOriginalFilename();
                            imgPath = path + fileName + name.substring(name.lastIndexOf("."), name.length());
                            imgsUrl += imgPath.substring(imgPath.indexOf("images/"), imgPath.length()) + ";";
                            long size = file.getSize();
                            // 文件大小限制
                            if (size > 1 * 1024 * 1024) {
                                double scale = (1024 * 1024f) / size;
                                Thumbnails.of(file.getInputStream()).scale(scale).outputQuality(scale).toFile(imgPath);
                            } else {
                                Streams.copy(file.getInputStream(), new FileOutputStream(imgPath), true);
                            }

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


}
