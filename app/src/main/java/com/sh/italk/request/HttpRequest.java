package com.sh.italk.request;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.sh.italk.Utils.JsonUtil;
import com.sh.italk.constants.ConstValues;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/4.
 */

public class HttpRequest {

    private Handler handler;
    private ProgressDialog progressDialog;
    private Context context;

    public HttpRequest(Handler handler, Context context){
        this.handler = handler;
        progressDialog = new ProgressDialog(context);
        this.context = context;
    }
    public String convertStringToHex(String str){
//把字符串转换成char数组
        char[] chars = str.toCharArray();
//新建一个字符串缓存类
        StringBuffer hex = new StringBuffer();
//循环每一个char
        for(int i = 0; i < chars.length; i++){
//把每一个char都转换成16进制的，然后添加到字符串缓存对象中
            hex.append(Integer.toHexString((int)chars[i]));
        }
//最后返回字符串就是16进制的字符串
        return hex.toString();
    }
    public void postDataString(final String url, final int what, String tag, final Map<String,String> paramsMap, boolean isShowProgress){
        if (isShowProgress){
            progressDialog = progressDialog.show(context,"","加载中！");
        }
        String AppKey = "ee77a960d46fd00ff3ba7122f81a7033";
        String AppSecret = "9ce20390b267";
        String Nonce = String.valueOf((int) ((Math.random() * 9 + 1) * 10));
        String CurTime = String.valueOf(TimeUtils.getNowMills()/1000);
        String addParams = AppSecret + Nonce +CurTime;
        Log.e("addParams",addParams);
        String CheckSum = EncryptUtils.encryptSHA1ToString(addParams).toLowerCase();
        OkGo.<String>post(url)
                .headers("AppKey",AppKey)//开发者平台分配的appkey
                .headers("Nonce",Nonce)//随机数（最大长度128个字符）
                .headers("CurTime",CurTime)//当前UTC时间戳，从1970年1月1日0点0 分0 秒开始到现在的秒数(String)
                .headers("CheckSum", CheckSum)//SHA1(AppSecret + Nonce + CurTime)，三个参数拼接的字符串，进行SHA1哈希计算，转化成16进制字符(String，小写)
                .params(paramsMap,true)
                .tag(tag)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        progressDialog.dismiss();
                        Message msg = handler.obtainMessage();
                        msg.what = what;
                        //判断返回数据是否为json，如果不是先把url和传参和token写入本地文件，方便调试
                        if (JsonUtil.isJsonValid(response.body().toString().trim())){
                            msg.obj = response.body().toString().trim();
                        }else {
                            sendReloginBroadCast();
                            msg.arg1 = -1;
                            writeErrorLog(Environment.getExternalStorageDirectory().getPath()+ ConstValues.FILE_ROOT_DIRECTORY+"/log.txt"
                                    ,url,paramsMap, SPUtils.getInstance().getString(ConstValues.TOKEN),response.body());
                        }
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        writeErrorLog(Environment.getExternalStorageDirectory().getPath()+ConstValues.FILE_ROOT_DIRECTORY+"/log.txt"
                                ,url,paramsMap, SPUtils.getInstance().getString(ConstValues.TOKEN),response.body());
                        progressDialog.dismiss();
                        Message msg = handler.obtainMessage();
                        msg.what = what;
                        msg.arg1 = -1;
                        handler.sendMessage(msg);
                        super.onError(response);
                    }
                });
    }

    public void postDataStringWithFile(final String url, final int what, String tag, final Map<String,String> paramsMap, boolean isShowProgress, List<File> fileList){
        if (paramsMap!=null){
            paramsMap.put("token", SPUtils.getInstance().getString(ConstValues.TOKEN));
        }
        if (isShowProgress){
            progressDialog = progressDialog.show(context,"","加载中！");
        }
        OkGo.<String>post(url)
                .headers("token", SPUtils.getInstance().getString(ConstValues.TOKEN))
                .params(paramsMap,true)
                .addFileParams("files",fileList)
                .tag(tag)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        progressDialog.dismiss();
                        Message msg = handler.obtainMessage();
                        msg.what = what;
                        //判断返回数据是否为json，如果不是先把url和传参和token写入本地文件，方便调试
                        if (JsonUtil.isJsonValid(response.body().toString().trim())){
                            msg.obj = response.body().toString().trim();
                        }else {
                            sendReloginBroadCast();
                            msg.arg1 = -1;
                            writeErrorLog(Environment.getExternalStorageDirectory().getPath()+ConstValues.FILE_ROOT_DIRECTORY+"/log.txt"
                                    ,url,paramsMap, SPUtils.getInstance().getString(ConstValues.TOKEN),response.body());
                        }
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        writeErrorLog(Environment.getExternalStorageDirectory().getPath()+ConstValues.FILE_ROOT_DIRECTORY+"/log.txt"
                                ,url,paramsMap, SPUtils.getInstance().getString(ConstValues.TOKEN),response.body());
                        progressDialog.dismiss();
                        Message msg = handler.obtainMessage();
                        msg.what = what;
                        msg.arg1 = -1;
                        handler.sendMessage(msg);
                        super.onError(response);
                    }
                });
    }

    public void sendReloginBroadCast(){
        Intent intent = new Intent("NEED_RELOGIN_CHICKEN");
        Utils.getContext().sendBroadcast(intent);
    }

    public void downLoadFile(String url, final int what, String dir, String fileName, boolean isShowProgress){
        if (isShowProgress){
            progressDialog = progressDialog.show(context,"","加载中！");
        }
        dir = Environment.getExternalStorageDirectory().getPath()+dir;
        File file = new File(dir);
        if (!file.exists()){
            file.mkdirs();
        }
        OkGo.<File>get(url).execute(new FileCallback(dir, fileName) {
            @Override
            public void onSuccess(Response<File> response) {
                progressDialog.dismiss();
                Message msg = handler.obtainMessage();
                msg.what = what;
                msg.obj = response.body();
                handler.sendMessage(msg);
            }

            @Override
            public void onError(Response<File> response) {
                progressDialog.dismiss();
                Message msg = handler.obtainMessage();
                msg.what = what;
                msg.arg1 = -1;
                handler.sendMessage(msg);
                super.onError(response);
            }
        });
    }

    public void getData(String url, final int what, boolean isShowProgress){
        if (isShowProgress){
            progressDialog = progressDialog.show(context,"","加载中！");
        }
        OkGo.<String>get(url).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                progressDialog.dismiss();
                Message msg = handler.obtainMessage();
                msg.what = what;
                msg.obj = response.body();
                handler.sendMessage(msg);
            }

            @Override
            public void onError(Response<String> response) {
                progressDialog.dismiss();
                Message msg = handler.obtainMessage();
                msg.what = what;
                msg.arg1 = -1;
                handler.sendMessage(msg);
                super.onError(response);
            }
        });
    }

    private void writeErrorLog(String filePath, String url, Map<String,String> paramsMap, String token, String content){
        if (FileUtils.createOrExistsFile(filePath)){
            File file = new File(filePath);
            FileIOUtils.writeFileFromString(file,"\r\n",true);
            FileIOUtils.writeFileFromString(file,url,true);
            FileIOUtils.writeFileFromString(file,"\r\n",true);
            if (paramsMap!=null && paramsMap.size()>0){

                StringBuffer sb = new StringBuffer();
                for (Map.Entry<String,String> entry : paramsMap.entrySet()){
                    sb.append(entry.getKey()+"="+entry.getValue()+";");
                }
                FileIOUtils.writeFileFromString(file,sb.toString(),true);
            }
            FileIOUtils.writeFileFromString(file,"\r\n"+token+"\r\n",true);
            FileIOUtils.writeFileFromString(file,content,true);
            FileIOUtils.writeFileFromString(file,"..............................................................................................",true);
        }
    }
}
