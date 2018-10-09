package com.sh.italk.request;

import android.content.Context;
import android.os.Handler;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Administrator on 2017/9/9.
 */

public class Api {

    private HttpRequest mHttpRequest;
    private Handler mHandler;
    private Context context;

    public Api(Handler handler, Context context){
        mHttpRequest = new HttpRequest(handler,context);
        this.mHandler = handler;
    }


    public void downLoadFile(int what, String url, String dir, String fileName){
        mHttpRequest.downLoadFile(url,what,dir,fileName,false);
    }

    /**
     * 用户注册
     * @param what
     * @param phone
     */
    public void userRegist(int what, String phone){
        String url = "https://api.netease.im/nimserver/user/create.action";
        TreeMap<String,String> paramsTreeMap = new TreeMap<>();
        paramsTreeMap.put("accid",phone);//accid 在一个app中唯一的网易云通信ID
        mHttpRequest.postDataString(url,what,"regist",paramsTreeMap,false);
    }

    /**
     * 用户更新token
     * @param what
     * @param accid
     */
    public void updateToken(int what, String accid){
        String url = "https://api.netease.im/nimserver/user/refreshToken.action";
        TreeMap<String,String> paramsTreeMap = new TreeMap<>();
        paramsTreeMap.put("accid",accid);//accid 在一个app中唯一的网易云通信ID
        mHttpRequest.postDataString(url,what,"updateToken",paramsTreeMap,false);
    }


}
