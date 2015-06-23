package com.xyf.net;

import org.apache.http.client.methods.HttpGet;

/**
 * Created by shxiayf on 2015/6/23.
 */
public class HttpUtils {

    private HttpUtils () {}

    private static HttpUtils instances;

    public static HttpUtils getInstances(){

        synchronized (HttpUtils.class){
            if (instances == null){
                instances = new HttpUtils();
            }
        }
        return instances;
    }

    public void requestGet(final String baseUrl,final HttpParams params,final HttpRequestListener listener){

        new Thread(new Runnable() {
            @Override
            public void run() {

                try{
                    listener.onSuccess(HttpNet.doGet(baseUrl,params));
                }catch (Exception e){
                    listener.onError(e);
                }

            }
        }).start();

    }

    public void requestPost(final String baseUrl,final HttpParams params,final HttpRequestListener listener){

        new Thread(new Runnable() {
            @Override
            public void run() {

                try{
                    listener.onSuccess(HttpNet.doPost(baseUrl,params));
                }catch (Exception e){
                    listener.onError(e);
                }

            }
        }).start();

    }

}
