package cn.molong.www.netease.splash.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cn.molong.www.netease.MainActivity;
import cn.molong.www.netease.R;
import cn.molong.www.netease.service.DownloadImageService;
import cn.molong.www.netease.splash.bean.Action;
import cn.molong.www.netease.splash.bean.Ads;
import cn.molong.www.netease.splash.bean.AdsDetail;
import cn.molong.www.netease.util.Constant;
import cn.molong.www.netease.util.ImageUtil;
import cn.molong.www.netease.util.JsonUtil;
import cn.molong.www.netease.util.Md5Helper;
import cn.molong.www.netease.util.SharePrenceUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 胡锦龙_Squall on 2018/1/15.
 */

public class SplashActivity extends Activity{

    //广告图片
    ImageView ads_img;

    //json缓存标识
    static final String JSON_CHCHE="ads_json";
    //json缓存离线标识
    static final String JSON_CHCHE_TIME_OUT="ads_json_time_out";
    //json缓存最后时间标识
    static final String JSON_CHCHE_LAST_SUCCESS="ads_json_last_success";
    //上一次图片标识
    static final String LAST_IMAGE_INDEX="img_index";

    //
    Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //开启全屏的设置
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //4.4 沉浸式
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // 设置View
        setContentView(R.layout.activity_splash);

        ads_img = (ImageView) findViewById(R.id.ads);

        mHandler = new Handler();

        // 获取广告数据
        getAds();

        // 显示图片
        showImage();



    }

    /**
     * 没有图片就执行这任务
     */
    Runnable NoPhotoGotoMain = new Runnable() {
        @Override
        public void run() {
            Log.i("ml", "run: ");
            Intent intent = new Intent();
            intent.setClass(SplashActivity.this, MainActivity.class);
            startActivity(intent);
        }
    };

    /**
     * 显示图片
     */
    public void showImage() {
        //读出缓存
        String cache = SharePrenceUtil.getString(this,JSON_CHCHE);

        if (TextUtils.isEmpty(cache)) {
            // 没有缓存，显示不了图片，跳转
            mHandler.postDelayed(NoPhotoGotoMain,2*1000);

        } else {

            //读出这次显示的图片标识
            int index = SharePrenceUtil.getInt(this, LAST_IMAGE_INDEX);


            //转化成对象
            Ads ads = JsonUtil.parseJson(cache, Ads.class);

            if (ads == null) {
                return;
            }

            //防数组越界
            index = index % ads.getAds().size();

            List<AdsDetail> adsDetails = ads.getAds();
            if (adsDetails != null && adsDetails.size() > 0) {
                final AdsDetail detail = adsDetails.get(index);
                List<String> urls = detail.getRes_url();
                if (urls != null && !TextUtils.isEmpty(urls.get(0))) {
                    //取到url
                    String url = urls.get(0);
                    //计算文件名
                    String image_name = Md5Helper.toMD5(url);
                    //拼接图片地址
                    File image = ImageUtil.getFileByName(image_name);
                    if (image.exists()) {
                        Bitmap bitmap = ImageUtil.getImageBitMapByFile(image);
                        //设置图片
                        ads_img.setImageBitmap(bitmap);
                        //保存下次显示图片下标
                        index++;
                        SharePrenceUtil.savaInt(this, LAST_IMAGE_INDEX, index);

                        //添加广告点击事件
                        ads_img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Action action = detail.getAction_params();
                                if (action != null && !TextUtils.isEmpty(action.getLink_url())) {
                                    //跳转activity
                                    Intent intent = new Intent();
                                    intent.setClass(SplashActivity.this, WebViewActivity.class);
                                    intent.putExtra(WebViewActivity.ACTION_NAME, action);
                                    startActivity(intent);
                                }
                            }
                        });
                    }

                }
            }

        }
    }


    /**
     * 判断是否需要http请求
     */
    public void getAds(){
        String cache = SharePrenceUtil.getString(this,JSON_CHCHE);
        if (TextUtils.isEmpty(cache)){
            httpRequest();
        } else {
            int time_out = SharePrenceUtil.getInt(this,JSON_CHCHE_TIME_OUT);
            long nowTime = System.currentTimeMillis();
            long lastTime = SharePrenceUtil.getLong(this,JSON_CHCHE_LAST_SUCCESS);
            if ((nowTime-lastTime)>time_out*60*1000){
                httpRequest();
            }
        }
    }


        /**
         * 获取广告数据
         */
        public void httpRequest(){
            Log.i("ml", "httpRequest: 下载JSON");

            OkHttpClient client = new OkHttpClient();
            //创建一个Request
            Request request = new Request.Builder()
                .get()
                .url(Constant.SPLASH_URL)
                .build();
        //通过client发起请求
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //请求不成功
                Log.e("err", "onFailure: ",e);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String date = response.body().string();
                    Ads ads = JsonUtil.parseJson(date, Ads.class);
                    if (ads != null){
                        //请求成功

                        //数据下载成功后，缓存json
                        SharePrenceUtil.savaString(SplashActivity.this,JSON_CHCHE, date);
                        //缓存超时时间
                        SharePrenceUtil.savaInt(SplashActivity.this,JSON_CHCHE_TIME_OUT,ads.getNext_req());
                        //缓存超时时间
                        SharePrenceUtil.savaLong(SplashActivity.this,JSON_CHCHE_LAST_SUCCESS,System.currentTimeMillis());

                        Intent intent = new Intent();
                        intent.setClass(SplashActivity.this, DownloadImageService.class);
                        intent.putExtra(DownloadImageService.ADS_DATE,ads);
                        startService(intent);
                    } else {
                        //请求不成功

                    }

                }

            }
        });

    }
}
