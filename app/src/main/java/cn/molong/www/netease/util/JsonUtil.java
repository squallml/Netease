package cn.molong.www.netease.util;

import android.text.TextUtils;

import com.google.gson.Gson;

/**
 * Created by 胡锦龙_Squall on 2018/1/15.
 */

public class JsonUtil {
    static Gson mGson;

    /**
     *
     * @param json 传入的json数据
     * @param tClass 请求类型.class
     * @param <T>  泛型T
     * @return 返回转换的数据
     */
    public static <T> T parseJson(String json, Class<T> tClass){
        if (mGson == null) {
            mGson = new Gson();
        }

        if (TextUtils.isEmpty(json)){
            return null;
        }

        return mGson.fromJson(json, tClass);
    }
}
