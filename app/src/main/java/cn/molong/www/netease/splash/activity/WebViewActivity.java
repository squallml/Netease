package cn.molong.www.netease.splash.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.Serializable;

import cn.molong.www.netease.R;
import cn.molong.www.netease.splash.bean.Action;

/**
 * Created by 胡锦龙_Squall on 2018/1/23.
 */

public class WebViewActivity extends Activity{
    public static final String ACTION_NAME="action";

    WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Action action = (Action) getIntent().getSerializableExtra(ACTION_NAME);
        setContentView(R.layout.activity_webview);

        mWebView = (WebView) findViewById(R.id.webview);

        //启用JS
        mWebView.getSettings().setJavaScriptEnabled(true);
        //加载url
        mWebView.loadUrl(action.getLink_url());
        //处理url重定向，不要抛到系统浏览器处理
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        //回退处理
        if (mWebView.canGoBack()){
            mWebView.goBack();
            return;
        }
        super.onBackPressed();
    }
}
