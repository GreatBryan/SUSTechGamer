package com.sdk.api;

import com.sdk.api.Util.ArrayInfo;
import jdk.nashorn.internal.parser.JSONParser;

import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SDKClient {
    private int uid;
    private int gid;
    private static SDKClient sdkClient;

    public static SDKClient getInstance() throws IOException {
        synchronized (SDKClient.class) {
            if (sdkClient == null) sdkClient = new SDKClient();
        }
        return sdkClient;
    }

    private SDKClient() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File("info.txt")));
        uid = Integer.parseInt(br.readLine());
        gid = Integer.parseInt(br.readLine());
    }

    private String sendGet(String url, String param) throws IOException {
        String result = "";
        String urlName = url + "?" + param;
        URL realUrl = new URL(urlName);
        //打开和URL之间的连接
        URLConnection conn = realUrl.openConnection();
        //设置通用的请求属性
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        //建立实际的连接
        conn.connect();
        //获取所有的响应头字段
        Map<String, List<String>> map = conn.getHeaderFields();
        //遍历所有的响应头字段
//            for (String key : map.keySet()) {
//                System.out.println(key + "-->" + map.get(key));
//            }
        // 定义 BufferedReader输入流来读取URL的响应
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        return result;
    }

    public String getUserName() {
        String para = "uid=" + uid;
        String result = "";
        try {
            result = sendGet("http://36058s3d36.zicp.vip:55374/user/findNameByUid", para);
        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    private String getById(int uid) {
        String para = "uid=" + uid;
        String result = "";
        try {
            sendGet("http://36058s3d36.zicp.vip:55374/user/findNameByUid", para);
        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    public boolean cloudSave(Object obj) {
        String para = "uid=" + uid + "&gid=" + gid + "&info=";
        Field[] fields = obj.getClass().getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        for (Field field : fields) {
            String info = "";
            info += field.getName();
            info += ":";
            field.setAccessible(true);
            try {
                info += getString(field.get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
            field.setAccessible(false);
            info += ";";
            sb.append(info);
        }
        System.out.println(sb.toString());
        para = para + sb.toString();
        System.out.println(para);
        //TODO: add URL
        try {
            sendGet("http://36058s3d36.zicp.vip:55374/uploadSave", para);
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private String getString(Object object) {
        if (canToString(object))
            return object.toString();
        else {
            String result = "(";
            for (int i = 0; i < Array.getLength(object); i++) {
                Object component = Array.get(object, i);
                if (canToString(component)) {
                    result += component.toString();
                    result += "-";
                } else {
                    result += getString(component);
                    result += ",";
                }
            }
            result = result.substring(0, result.length() - 1);
            return result + ")";
        }
    }

    private boolean canToString(Object object) {
        if (object.getClass().getName().equals("java.lang.Integer") |
                object.getClass().getName().equals("java.lang.Double") |
                object.getClass().getName().equals("java.lang.Float") |
                object.getClass().getName().equals("java.lang.Short") |
                object.getClass().getName().equals("java.lang.Long") |
                object.getClass().getName().equals("java.lang.Boolean") |
                object.getClass().getName().equals("java.lang.Byte") |
                object.getClass().getName().equals("java.lang.Character") |
                object.getClass().getName().equals("java.lang.String"))
            return true;
        else return false;
    }

    public <T> List<T> getSaves(Class<T> clz) throws NoSuchMethodException, IllegalAccessException, InstantiationException, ClassNotFoundException, InvocationTargetException, NoSuchFieldException {
        String para = "uid=" + uid + "&gid=" + gid;
        String origin = "";
        try{
            origin = sendGet("http://36058s3d36.zicp.vip:55374/getSave", para);
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
        String[] infos = origin.split("\\|");
        List<T> list = new ArrayList<>();
        for (String response : infos) {
            T obj = loadFromCloud(clz, response);
            list.add(obj);
        }
        return list;
    }

    private  <T> T loadFromCloud(Class<T> clz, String response) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException, ClassNotFoundException {
        System.out.println(response);
        Constructor constructor = clz.getConstructor();
        Object result = constructor.newInstance();
        for (String info : response.split(";")) {
            String[] nameValue = info.split(":");
            Field field = result.getClass().getDeclaredField(nameValue[0]);
            field.setAccessible(true);
            Object val;
            if (nameValue[1].charAt(0) != '(')
                val = getSingleVal(field.getType(), nameValue[1]);
            else {
                ArrayInfo arrayInfo = field.getAnnotation(ArrayInfo.class);
                val = getArr(nameValue[1].substring(1, nameValue[1].length() - 1), arrayInfo.componentType());
            }
            field.set(result, val);
            field.setAccessible(false);
        }
        return (T) result;
    }

    private Object getSingleVal(Class<?> p, String val) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Object parameterObject;
        if (p == int.class)
            parameterObject = Integer.parseInt(val);
        else if (p == double.class)
            parameterObject = Double.parseDouble(val);
        else if (p == float.class)
            parameterObject = Float.parseFloat(val);
        else if (p == char.class)
            parameterObject = val.charAt(0);
        else if (p == boolean.class)
            parameterObject = Boolean.parseBoolean(val);
        else if (p == byte.class)
            parameterObject = Byte.parseByte(val);
        else if (p == long.class)
            parameterObject = Long.parseLong(val);
        else if (p == short.class)
            parameterObject = Short.parseShort(val);
        else
            parameterObject = p.getDeclaredConstructor(String.class).newInstance(val);
        return parameterObject;
    }

    private Object getArr(String value, String type) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String[] content;
        Object result;
        if (type.charAt(0) != '[') {
            content = value.split("-");
            result = Array.newInstance(getPrimitiveType(type), content.length);
            for (int i = 0; i < content.length; i++) {
                if (type.equals("int"))
                    Array.set(result, i, Integer.parseInt(content[i]));
                else if (type.equals("double"))
                    Array.set(result, i, Double.parseDouble(content[i]));
                else if (type.equals("float"))
                    Array.set(result, i, Float.parseFloat(content[i]));
                else if (type.equals("char"))
                    Array.set(result, i, content[i].charAt(0));
                else if (type.equals("boolean"))
                    Array.set(result, i, Boolean.parseBoolean(content[i]));
                else if (type.equals("byte"))
                    Array.set(result, i, Byte.parseByte(content[i]));
                else if (type.equals("long"))
                    Array.set(result, i, Long.parseLong(content[i]));
                else if (type.equals("short"))
                    Array.set(result, i, Short.parseShort(content[i]));
                else
                    Array.set(result, i, Class.forName(type).getDeclaredConstructor(String.class).newInstance(content[i]));
            }
        } else {
            content = value.split(",");
            result = Array.newInstance(Class.forName(type), content.length);
            for (int i = 0; i < content.length; i++) {
                if (type.equals(int[].class.getName()))
                    Array.set(result, i, getArr(content[i].substring(1, content[i].length() - 1), "int"));
                else if (type.equals(double[].class.getName()))
                    Array.set(result, i, getArr(content[i].substring(1, content[i].length() - 1), "double"));
                else if (type.equals(short[].class.getName()))
                    Array.set(result, i, getArr(content[i].substring(1, content[i].length() - 1), "short"));
                else if (type.equals(long[].class.getName()))
                    Array.set(result, i, getArr(content[i].substring(1, content[i].length() - 1), "long"));
                else if (type.equals(float[].class.getName()))
                    Array.set(result, i, getArr(content[i].substring(1, content[i].length() - 1), "float"));
                else if (type.equals(char[].class.getName()))
                    Array.set(result, i, getArr(content[i].substring(1, content[i].length() - 1), "char"));
                else if (type.equals(byte[].class.getName()))
                    Array.set(result, i, getArr(content[i].substring(1, content[i].length() - 1), "byte"));
                else
                    Array.set(result, i, getArr(content[i].substring(1, content[i].length() - 1), "boolean"));
            }
        }
        return result;
    }

    private Class<?> getPrimitiveType(String str) throws ClassNotFoundException {
        if (str.equals("int")) return int.class;
        else if (str.equals("double")) return double.class;
        else if (str.equals("short")) return short.class;
        else if (str.equals("long")) return long.class;
        else if (str.equals("byte")) return byte.class;
        else if (str.equals("float")) return float.class;
        else if (str.equals("char")) return char.class;
        else if (str.equals("boolean")) return boolean.class;
        else return Class.forName(str);
    }

    public List<String> getRank() {
        String para = "gid=" + gid;

        //TODO: sendGet
        String response = "";
        try {
            response = sendGet("http://36058s3d36.zicp.vip:55374/getRank", para);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
//        String response = "1,3000;2,2999;3,500;";
        System.out.println(response);
        String[] rank = response.split(";");
        List<String> result = new ArrayList<>();
        for (int i = 0; i < rank.length; i++) {
            String uid = rank[i].split(",")[0];
            String content = getById(Integer.parseInt(uid)) + "," + rank[i].split(",")[1];
            result.add(content);
        }
        return result;
    }

    public boolean uploadScore(int score) {
        String para = "info=" + uid + "," + score + "&gid=" + gid;
        try {
            sendGet("http://36058s3d36.zicp.vip:55374/uploadRank", para);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String getAvatar(){
        return "http://36058s3d36.zicp.vip/static/user/" + uid + "/photo.jpg";
    }

    public List<String> getFriends(){
        try {
            String response = sendGet("http://36058s3d36.zicp.vip:55374/friend/getFriends","user1=" + uid);
            if (response.length() == 2) return null;
            List<String> result = new ArrayList<>();
            String[] info = response.substring(1, response.length() - 1).split("},");
            for(String each: info){
                String[] attributes = each.substring(1).split(",");
                String[] name_info = attributes[1].split(":");
                result.add(name_info[1].substring(1, name_info[1].length() - 1));
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
