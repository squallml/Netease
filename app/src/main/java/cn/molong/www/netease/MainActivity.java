package cn.molong.www.netease;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import cn.molong.www.netease.splash.TimeView;

public class MainActivity extends AppCompatActivity {

    TimeView mTimeView;

    int length = 2*1000;
    int space = 250;
    int now = 0;
    int total;

    Handler mHandler;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTimeView = findViewById(R.id.timeView);

        total = length/space;

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 0:
                        int now = msg.arg1;

                        if (now<=total){
                            mTimeView.setProgess(total,now);
                        } else {
                            mHandler.removeCallbacks(reshRing);
                        }
                        break;
                }
            }
        };

        mHandler.post(reshRing);

    }

    Runnable reshRing = new Runnable() {
        @Override
        public void run() {
            Message message = mHandler.obtainMessage();
            message.arg1 = now;
            mHandler.sendMessage(message);
            mHandler.postDelayed(this,space);
            now++;
        }
    };
}
