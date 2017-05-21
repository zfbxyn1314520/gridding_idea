package com.eollse.util;

/**
 * Http请求工具类
 * @author dugbud
 * 
 */
import java.io.IOException;  
import java.util.ArrayList;  
import java.util.Date;
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;  
import java.util.Random;
import java.util.Set;  
  






import org.apache.http.NameValuePair;  
import org.apache.http.client.ClientProtocolException;  
import org.apache.http.client.entity.UrlEncodedFormEntity;  
import org.apache.http.client.methods.CloseableHttpResponse;  
import org.apache.http.client.methods.HttpGet;  
import org.apache.http.client.methods.HttpPost;  
import org.apache.http.impl.client.CloseableHttpClient;  
import org.apache.http.impl.client.HttpClients;  
import org.apache.http.message.BasicNameValuePair;  
import org.apache.http.util.EntityUtils;  

public class HttpClientUtil {

	 /** 
     * 处理get请求. 
     * @param url  请求路径 
     * @return  json 
     */  
    public String get(String url,String encoding){  
        //实例化httpclient  
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        //实例化get方法  
        HttpGet httpget = new HttpGet(url);   
        //请求结果  
        CloseableHttpResponse response = null;  
        String content ="";  
        try {  
            //执行get方法  
            response = httpclient.execute(httpget);  
            if(response.getStatusLine().getStatusCode()==200){  
                content = EntityUtils.toString(response.getEntity(),encoding);  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return content;  
    }  
    
    
    /** 
     * 处理post请求. 
     * @param url  请求路径 
     * @param params  参数 
     * @return  json 
     */  
    public String post(String url,String encoding,Map<String, String> params){  
        //实例化httpClient  
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        //实例化post方法  
        HttpPost httpPost = new HttpPost(url);   
        //处理参数  
        List<NameValuePair> nvps = new ArrayList <NameValuePair>();    
        Set<String> keySet = params.keySet();    
        for(String key : keySet) {    
            nvps.add(new BasicNameValuePair(key, params.get(key)));    
        }    
        //结果  
        CloseableHttpResponse response = null;  
        String content="";  
        try {  
            //提交的参数  
            UrlEncodedFormEntity uefEntity  = new UrlEncodedFormEntity(nvps, encoding);  
            //将参数给post方法  
            httpPost.setEntity(uefEntity);  
            //执行post方法  
            response = httpclient.execute(httpPost);  
            if(response.getStatusLine().getStatusCode()==200){  
                content = EntityUtils.toString(response.getEntity(),encoding);  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }   
        return content;  
    }  
    
    
    public static void main(String[] args) {  
    	Random random=new Random();
    	// 获得随机数  
    	
        long sTime=System.currentTimeMillis();
        int captcha = random.nextInt(999999)%(900000) + 100000;
        System.out.println(captcha);
        long eTime=System.currentTimeMillis();
        System.out.println("程序运行时间： "+(eTime-sTime)+"ms");
        
    	HttpClientUtil hd = new HttpClientUtil();  
////        String result=hd.get("http://www.kuaidi100.com/query?type=shentong&postid=3327110080673");  
////        System.out.println("result:"+result);
        Map<String,String> map = new HashMap<String,String>();  
////        map.put("type","shentong");  
////        map.put("postid","3327110080673"); 
////        String rows=hd.post("http://www.kuaidi100.com/query",map); 
        map.put("CorpID","CQLKY00729");  
        map.put("Pwd","zxkj@666");
        map.put("Mobile","18523322913");  
        map.put("Content","您的验证码为"+captcha+"，有效时间5分钟。");
        System.out.println(map);
//        String rows=hd.post("http://yzm.mb345.com/ws/BatchSend2.aspx","gb2312",map); 
//        System.out.println("rows:"+rows);
    }  
    

}