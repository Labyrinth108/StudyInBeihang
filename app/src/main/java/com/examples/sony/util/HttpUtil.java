package com.examples.sony.util;
import android.text.TextUtils;
import android.util.Log;

import com.database.Classroom;
import com.database.Courseinfo;
import com.database.DB;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by SONY on 2016/2/19.
 */
public class HttpUtil {
    public static void sendHttpRequest(final String address,final HttpCallbackListener listener){
        //开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                try{
                    URL url=new URL(address);
                    connection=(HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");//设置HTTp请求所使用的方法，get表示希望从服务器获取数据，
                    //post表示希望提交数据给服务器
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);

                    InputStream in =connection.getInputStream();
                    //对获取到的输入流进行读取
                    BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                    StringBuilder response=new StringBuilder();
                    String line;
                    while((line=reader.readLine())!=null)
                      response.append(line);
                   if(listener!=null){
                       listener.onFinish(response.toString());
                   }
                }catch(Exception e){
                    if(listener!=null)
                        listener.onError(e);
                }finally {
                    if(connection!=null)
                        connection.disconnect(); //将HTTP连接关闭掉
                }
            }
        }).start();
    }
    public static void submitPostData(final Map<String, String> params, final String encode) {
        new Thread(new Runnable() {
           @Override
           public void run() {
               try {
                   byte[] data = getRequestData(params, encode).toString().getBytes();//获得请求体
                   Log.d("Long",data.toString());
                   URL url=new URL("http://chaopengz.nat123.net:19870/feedback/");
                   HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                   httpURLConnection.setConnectTimeout(3000);       //设置连接超时时间
                   httpURLConnection.setDoInput(true);                  //打开输入流，以便从服务器获取数据
                   httpURLConnection.setDoOutput(true);                 //打开输出流，以便向服务器提交数据
                   httpURLConnection.setRequestMethod("POST");    //设置以Post方式提交数据
                   httpURLConnection.setUseCaches(false);               //使用Post方式不能使用缓存
                   //设置请求体的类型是文本类型
                   httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                   //设置请求体的长度
                   httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
                   //获得输出流，向服务器写入数据
                   OutputStream outputStream = httpURLConnection.getOutputStream();
                   outputStream.write(data);
                   int response = httpURLConnection.getResponseCode();            //获得服务器的响应码
                    if(response == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    Log.d("Long", "Yeah!" + inputStream.toString());
                  //Log.d("Longresponse",dealResponseResult(inputStream));                     //处理服务器的响应结果
               }
           } catch (IOException e) {
                e.printStackTrace();
            }
           }
       }).start();
    }
    public static StringBuffer getRequestData(Map<String, String> params, String encode) {
        StringBuffer stringBuffer = new StringBuffer();        //存储封装好的请求体信息
        try {
            for(Map.Entry<String, String> entry : params.entrySet()) {
                stringBuffer.append(entry.getKey())
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), encode))
                        .append("&");
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);    //删除最后的一个"&"
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer;
    }
    /*
     * Function  :   处理服务器的响应结果（将输入流转化成字符串）
     * Param     :   inputStream服务器的响应输入流
     * Author    :   博客园-依旧淡然
     */
    public static String dealResponseResult(InputStream inputStream) {
        String resultData = null;      //存储处理结果
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        try {
            while((len = inputStream.read(data)) != -1) {
                byteArrayOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultData = new String(byteArrayOutputStream.toByteArray());
        return resultData;
    }
    public static boolean GetCourseinfoWithJSONObject(DB db, String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if(jsonObject.getString("course").equals("1")){
                        Courseinfo ci=new Courseinfo();
                        String s=jsonObject.getString("date");
                        String date[]=s.split("_");
                        int week=0;
                        switch (date[0].toString()){
                            case "Mon": week=1;break;
                            case "Tue": week=2;break;
                            case "Wed":week=3;break;
                            case "Thu":week=4;break;
                            case "Fri":week=5;break;
                            case "Sat":week=6;break;
                            case "Sun":week=7;break;
                            default:break;
                        }
                        ci.setWeek(week);
                        String num[]=date[1].split(",");
                        ci.setStartnum(Integer.parseInt(num[0]));
                        ci.setEndnum(Integer.parseInt(num[1]));
                        ci.setRoom(jsonObject.getString("room"));
                        db.saveCourse(ci);
                        Log.d("LongRunning", ci.getRoom() + ci.getWeek() + "Create");
                    }
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }

        }
        return false;
    }
    public static boolean parseJSONWithJSONObject(DB db, String response,boolean First){
        if(!TextUtils.isEmpty(response)){
            String location="";
            try{
                JSONArray jsonArray=new JSONArray(response);
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    Classroom cr=new Classroom();
                    switch (jsonObject.getString("location")){
                        case "新主楼": location="NMB";break;
                        case "主M":location="ZhuM";break;
                        case "教三":location="J3";break;
                        case "教四":location="J4";break;
                        case "教五":location="J5";break;
                        case "学院图书馆":location="XYLib";break;
                        case "沙河图书馆":location="SheheLib";break;
                        default:break;
                    }
                    cr.setLocation(location);
                    cr.setRoom(jsonObject.getString("room"));
                    cr.setIdnum(Integer.parseInt(jsonObject.getString("idnum")));
                    cr.setPercent(jsonObject.getString("percent"));
                    if(First){
                        db.saveClassroom(cr);
                        Log.d("LongRunning", cr.getLocation() + cr.getRoom() + "Create");
                    }
                    else{
                        db.updateClassroom(cr);
                        Log.d("LongRunning", cr.getLocation() + cr.getRoom() + "Update");
                    }
                }
                return true;
            }catch (Exception e){
                e.printStackTrace();
            }finally{

            }
        }
    return false;
    }
}
