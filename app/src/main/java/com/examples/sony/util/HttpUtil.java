package com.examples.sony.util;
import android.text.TextUtils;
import android.util.Log;

import com.database.Classroom;
import com.database.DB;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
                        default:break;
                    }
                    cr.setLocation(location);
                    cr.setRoom(jsonObject.getString("room"));
                    cr.setIdnum(Integer.parseInt(jsonObject.getString("idnum")));
                    cr.setPercent(jsonObject.getString("percent"));
                    if(First){
                        db.saveClassroom(cr);
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