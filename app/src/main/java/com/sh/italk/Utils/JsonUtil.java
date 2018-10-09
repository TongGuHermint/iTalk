package com.sh.italk.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/10/13.
 */

public class JsonUtil {

    public static boolean isJsonValid(String str){
        try{
            new JSONObject(str);
        }catch (JSONException e) {
            try {
                new JSONArray(str);
            } catch (JSONException e1) {
                return false;
            }
        }
        return true;
    }
}
