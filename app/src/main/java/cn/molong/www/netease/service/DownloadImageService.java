package cn.molong.www.netease.service;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import cn.molong.www.netease.splash.bean.Ads;
import cn.molong.www.netease.splash.bean.AdsDetail;
import cn.molong.www.netease.util.Constant;
import cn.molong.www.netease.util.ImageUtil;
import cn.molong.www.netease.util.Md5Helper;

/**
 * Created by 胡锦龙_Squall on 2018/1/19.
 */

public class DownloadImageService extends IntentService{

    public static final String ADS_DATE="ads";

    //默认的构造方法
    public DownloadImageService() {
        super("DownloadImageService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //接收到http请求对象
        Ads ads = (Ads) intent.getSerializableExtra(ADS_DATE);
        List<AdsDetail> list = ads.getAds();
        for (int i =0;i<list.size();i++){
            AdsDetail detail = list.get(i);
            List<String> imgs = detail.getRes_url();
            if (imgs != null){
                String img_url = imgs.get(0);
                if (!TextUtils.isEmpty(img_url)){

                    //图片地址转成唯一的md5文件名
                    String catche_name = Md5Helper.toMD5(img_url);

                    //判断图片是否存在，不存在就下载
                    if (!ImageUtil.checkImageIsDownload(catche_name)) {
                        //下载图片
                        downloadImage(img_url, catche_name);
                    }
                }
            }
        }

    }

    /**
     * 下载图片
     * @param url
     * @param MD5_name
     */
    public void downloadImage(String url, String MD5_name){
        try {
            URL uri = new URL(url);
            URLConnection urlConnection = uri.openConnection();
//            //拿到图片的边间，大小
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inJustDecodeBounds = true;

            //拿到一张图片
            Bitmap bitmap = BitmapFactory.decodeStream(urlConnection.getInputStream());
            //写入图片到SD卡
            saveToSD(bitmap, MD5_name);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载到SD卡
     * @param bitmap
     * @param MD5_name
     */
    public void saveToSD(Bitmap bitmap, String MD5_name) {
        if (bitmap == null){
            return;
        }

        //判断手机有没有SD卡
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File SD = Environment.getExternalStorageDirectory();
            File cacheFile = new File(SD, Constant.CHCHE);
            if (!cacheFile.exists()){
                cacheFile.mkdirs();
            }

            File image = new File(cacheFile, MD5_name + ".jpg");
            //图片存在
            if (image.exists()){
                return;
            }

            //压缩图片到SD卡
            try {
                FileOutputStream image_out = new FileOutputStream(image);
                /**
                 * bitmap.compress压缩图片
                 * 第一个参数 图片格式
                 * 第二个参数 压缩后的百分比，1-100。
                 * 第二个参数 输出的图片
                 */
                bitmap.compress(Bitmap.CompressFormat.JPEG,60,image_out);

                image_out.flush();
                image_out.close();

                Log.i("ml","done");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


}
