package com.eollse.util;

import org.apache.xmlbeans.impl.regex.REUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by eollse on 2017/7/17.
 */
public class SMSSendUtil {


    public String sendPhoneCode(String mobileTel) {

        String captcha;
        Random random = new Random();
        HttpClientUtil httpClient = new HttpClientUtil();
        Map<String, String> map = new HashMap<String, String>();
        map.put("CorpID", "CQLKY00729");
        map.put("Pwd", "zxkj@666");
        String result = httpClient.post("http://yzm.mb345.com/ws/NotSend.aspx", "gb2312", map);
        String msg = SMSNotSend(result, mobileTel);
        if (msg.equals("")) {
            map.clear();
            captcha = String.valueOf(random.nextInt(999999) % (900000) + 100000);
            System.out.println("captcha=========" + captcha);
//            map.put("CorpID", "CQLKY00729");
//            map.put("Pwd", "zxkj@666");
//            map.put("Mobile", mobileTel);
//            map.put("Content", "您的手机验证码为：" + captcha + "，有效时间为3分钟。请勿向任何单位及个人泄露。如非本人操作，请忽略本消息。");
//            String str = httpClient.post("http://yzm.mb345.com/ws/BatchSend2.aspx", "gb2312", map);
//            Integer code = Integer.parseInt(str);
            Integer code = Integer.parseInt(captcha);
            if (code > 0) {
                return "{\"status\":\"200\",\"msg\":\"验证码已发送至你的手机，请注意查收！\",\"captcha\":\"" + captcha + "\"}";
            } else {
                return "{\"status\":\"201\",\"msg\":\"" + this.getCaptchaStatus(code) + "\",\"captcha\":\"000000\"}";
            }
        } else {
            return "{\"status\":\"300\",\"msg\":\"" + msg + "\",\"captcha\":\"000000\"}";
        }
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
     * 发送短信验证返回消息判断
     *
     * @param code 返回状态值信息
     * @return
     */
    public String SMSNotSend(String code, String mobileTel) {
        String message = "";
        switch (code) {
            case "-1":
                message = "账号未注册！";
                break;
            case "-2":
                message = "其他错误！";
                break;
            case "-3":
                message = "密码错误！";
                break;
            case "-100":
                message = "IP黑名单！";
                break;
            case "-101":
                message = "调用接口速度太快（大于30s）！";
                break;
            case "-102":
                message = "账号黑名单!";
                break;
            case "-103":
                message = "IP未导白!";
                break;
            default:
                if (code != null && !code.equals("")) {
                    String[] nums = code.split(",");
                    for (int i = 0; i < nums.length; i++) {
                        if (nums[i].equals(mobileTel)) {
                            message = "你的操作次数过于频繁，请1小时后再试！";
                        }
                    }
                } else {
                    message = "";
                }
        }
        return message;
    }


}
