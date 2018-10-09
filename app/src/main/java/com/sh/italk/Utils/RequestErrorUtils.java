package com.sh.italk.Utils;

import android.content.Context;
import android.widget.Toast;

import com.sh.italk.constants.RequestWhatI;


/**
 * Created by dql on 2017/11/28.
 */

public class RequestErrorUtils implements RequestWhatI {

    public static void showError(Context context, int what){
        switch (what){
            case REQUEST_WHAT_SENDCHECKNUM:
                Toast.makeText(context,"短信验证码未成功发送,请稍后再试!", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
