package com.xyf.net;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by shxiayf on 2015/6/23.
 */
public class HttpParams {

    private HashMap<String, String> maps = null;

    public HttpParams() {
        if (maps == null) {
            maps = new HashMap<String, String>();
        } else {
            maps.clear();
        }
    }

    public void clear() {
        if (maps != null) {
            maps.clear();
        }
    }

    public void add(String key, String value) {
        if (maps != null) {
            maps.put(key, value);
        }
    }

    public String parse2KVString() {

        if (maps != null) {
            Iterator<?> it = maps.keySet().iterator();
            StringBuffer sb = new StringBuffer();
            while (it.hasNext()) {
                String key = (String) it.next();
                String value = maps.get(key);
                sb.append(key).append("=").append(value).append("&");

            }

            String result = sb.toString();
            result = result.substring(0, result.length() - 1);
            return result;
        }

        return null;
    }

    public String parse2JsonString() {

        try {
            if (maps != null) {
                Iterator<?> it = maps.keySet().iterator();
                JSONObject jsonObject = new JSONObject();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    String value = maps.get(key);
                    jsonObject.put(key, value);
                }

                return jsonObject.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
