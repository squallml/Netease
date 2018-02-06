package cn.molong.www.netease.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 胡锦龙_Squall on 2018/1/23.
 */

public class SharePrenceUtil {
    public static final String XML_FILE_NAME="cache";

    /**
     * 缓存json
     * @param context
     * @param title
     * @param content
     */
    public static void savaString(Context context, String title, String content){
        SharedPreferences share = context.getSharedPreferences(XML_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = share.edit();
        edit.putString(title,content);
        edit.apply();
    }

    public static String getString(Context context, String title) {
        String content;
        SharedPreferences share = context.getSharedPreferences(XML_FILE_NAME,Context.MODE_PRIVATE);
        content = share.getString(title,"");
        return content;
    }

    /**
     * 缓存超时时间
     * @param context
     * @param title
     * @param content
     */
    public static void savaInt(Context context, String title, int content){
        SharedPreferences share = context.getSharedPreferences(XML_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = share.edit();
        edit.putInt(title,content);
        edit.apply();
    }

    public static int getInt(Context context, String title) {
        int content;
        SharedPreferences share = context.getSharedPreferences(XML_FILE_NAME,Context.MODE_PRIVATE);
        content = share.getInt(title,0);
        return content;
    }

    /**
     * 缓存超时时间
     * @param context
     * @param title
     * @param content
     */
    public static void savaLong(Context context, String title, long content){
        SharedPreferences share = context.getSharedPreferences(XML_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = share.edit();
        edit.putLong(title,content);
        edit.apply();
    }

    public static long getLong(Context context, String title) {
        long content;
        SharedPreferences share = context.getSharedPreferences(XML_FILE_NAME,Context.MODE_PRIVATE);
        content = share.getLong(title,0);
        return content;
    }

}
