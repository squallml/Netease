package cn.molong.www.netease.splash.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 胡锦龙_Squall on 2018/1/15.
 */

public class AdsDetail implements Serializable {
    Action action_params;
    // 广告网址
    List<String> res_url;

    public Action getAction_params() {
        return action_params;
    }

    public void setAction_params(Action action_params) {
        this.action_params = action_params;
    }

    public List<String> getRes_url() {
        return res_url;
    }

    public void setRes_url(List<String> res_url) {
        this.res_url = res_url;
    }

    @Override
    public String toString() {
        return "AdsDetail{" +
                "action_params=" + action_params +
                ", res_url=" + res_url +
                '}';
    }
}
