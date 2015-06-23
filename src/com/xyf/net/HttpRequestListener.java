package com.xyf.net;

/**
 * Created by shxiayf on 2015/6/23.
 */
public interface HttpRequestListener {

    public void onSuccess(Object obj);

    public void onError(Object obj);

}
