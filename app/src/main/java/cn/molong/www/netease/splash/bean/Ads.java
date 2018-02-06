package cn.molong.www.netease.splash.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 胡锦龙_Squall on 2018/1/15.
 */

public class Ads implements Serializable {
    //下次更新时间
    int next_req;
    //广告详情数据
    List<AdsDetail> ads;

    public int getNext_req() {
        return next_req;
    }

    public void setNext_req(int next_req) {
        this.next_req = next_req;
    }

    public List<AdsDetail> getAds() {
        return ads;
    }

    public void setAds(List<AdsDetail> ads) {
        this.ads = ads;
    }

    @Override
    public String toString() {
        return "Ads{" +
                "next_req=" + next_req +
                ", ads=" + ads +
                '}';
    }
}
