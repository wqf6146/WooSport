package com.yhrjkf.woyundong.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;


//import com.amap.api.maps2d.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yhrjkf.woyundong.App;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferencesUtil {
          
        //存储的sharedpreferences文件名  
        private static final String FILE_NAME = "WooSports";
          
        /** 
         * 保存数据到文件
         * @param key 
         * @param data 
         */  
        public static void saveData(String key,Object data){
      
            String type = data.getClass().getSimpleName();  
            SharedPreferences sharedPreferences = App.newInstance()
                    .getSharedPreferences(FILE_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            if ("Integer".equals(type)){  
                editor.putInt(key, (Integer)data);  
            }else if ("Boolean".equals(type)){  
                editor.putBoolean(key, (Boolean)data);  
            }else if ("String".equals(type)){  
                editor.putString(key, (String)data);  
            }else if ("Float".equals(type)){  
                editor.putFloat(key, (Float)data);  
            }else if ("Long".equals(type)){  
                editor.putLong(key, (Long)data);  
            }  
              
            editor.commit();  
        }  
          
        /** 
         * 从文件中读取数据
         * @param key 
         * @param defValue 
         * @return 
         */  
        public static Object getData(String key, Object defValue){
              
            String type = defValue.getClass().getSimpleName();  
            SharedPreferences sharedPreferences = App.newInstance().getSharedPreferences
                    (FILE_NAME, MODE_PRIVATE);
              
            //defValue为为默认值，如果当前获取不到数据就返回它  
            if ("Integer".equals(type)){  
                return sharedPreferences.getInt(key, (Integer)defValue);  
            }else if ("Boolean".equals(type)){  
                return sharedPreferences.getBoolean(key, (Boolean)defValue);  
            }else if ("String".equals(type)){  
                return sharedPreferences.getString(key, (String)defValue);  
            }else if ("Float".equals(type)){  
                return sharedPreferences.getFloat(key, (Float)defValue);  
            }else if ("Long".equals(type)){  
                return sharedPreferences.getLong(key, (Long)defValue);  
            }  
              
            return null;  
        }



        //
        public static void saveListObject(Context context,String key,Vector data){
            SharedPreferences.Editor editor = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE).edit();
            Gson gson = new Gson();
            String json = gson.toJson(data);
            editor.putString(key, json);
            editor.commit();
        }

//        public static Vector getListObject(Context context,String key){
//            SharedPreferences preferences = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE);
//            String json = preferences.getString(key, null);
//            Vector<LatLng> data = new Vector<LatLng>();
//            if (json != null && !json.equals(""))
//            {
//                Gson gson = new Gson();
//                Type type = new TypeToken<LinkedList<LatLng>>(){}.getType();
//                data = gson.fromJson(json, type);
//            }
//            return data;
//        }

    /**
     * writeObject 方法负责写入特定类的对象的状态，以便相应的 readObject 方法可以还原它
     * 最后，用Base64.encode将字节文件转换成Base64编码保存在String中
     *
     * @param object 待加密的转换为String的对象
     * @return String   加密后的String
     */
    public static String Object2String(Object object) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            String string = new String(Base64.encode(byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
            objectOutputStream.close();
            return string;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用Base64解密String，返回Object对象
     *
     * @param objectString 待解密的String
     * @return object      解密后的object
     */
    public static Object String2Object(String objectString) {
        byte[] mobileBytes = Base64.decode(objectString.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mobileBytes);
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Object object = objectInputStream.readObject();
            objectInputStream.close();
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 使用SharedPreference保存对象
     *
     * @param key        储存对象的key
     * @param saveObject 储存的对象
     */
    public static void saveObject(Context context, String key, Object saveObject) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String string = Object2String(saveObject);
        editor.putString(key, string);
        editor.commit();
    }

    /**
     * 获取SharedPreference保存的对象
     *
     * @param key     储存对象的key
     * @return object 返回根据key得到的对象
     */
    public static Object getObject(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        String string = sharedPreferences.getString(key, null);
        if (string != null) {
            Object object = String2Object(string);
            return object;
        } else {
            return null;
        }
    }

    /**
     * 根据key和预期的value类型获取value的值
     *
     * @param key
     * @param clazz
     * @return
     */
    public <T> T getValue(Context context,String key, Class<T> clazz) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return getValue(key, clazz, sp);
    }

    /**
     * 针对复杂类型存储<对象>
     *
     * @param key
     */
    public void setObject(Context context, String key, Object object) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {

            out = new ObjectOutputStream(baos);
            out.writeObject(object);
            String objectVal = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(key, objectVal);
            editor.commit();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getObject(Context context,String key, Class<T> clazz) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        if (sp.contains(key)) {
            String objectVal = sp.getString(key, null);
            byte[] buffer = Base64.decode(objectVal, Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(bais);
                T t = (T) ois.readObject();
                return t;
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bais != null) {
                        bais.close();
                    }
                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 对于外部不可见的过渡方法
     *
     * @param key
     * @param clazz
     * @param sp
     * @return
     */
    @SuppressWarnings("unchecked")
    private <T> T getValue(String key, Class<T> clazz, SharedPreferences sp) {
        T t;
        try {

            t = clazz.newInstance();

            if (t instanceof Integer) {
                return (T) Integer.valueOf(sp.getInt(key, 0));
            } else if (t instanceof String) {
                return (T) sp.getString(key, "");
            } else if (t instanceof Boolean) {
                return (T) Boolean.valueOf(sp.getBoolean(key, false));
            } else if (t instanceof Long) {
                return (T) Long.valueOf(sp.getLong(key, 0L));
            } else if (t instanceof Float) {
                return (T) Float.valueOf(sp.getFloat(key, 0L));
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
            Log.e("system", "类型输入错误或者复杂类型无法解析[" + e.getMessage() + "]");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Log.e("system", "类型输入错误或者复杂类型无法解析[" + e.getMessage() + "]");
        }
        Log.e("system", "无法找到" + key + "对应的值");
        return null;
    }
}