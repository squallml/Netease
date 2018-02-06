package cn.molong.www.netease.splash.bean;

import java.io.Serializable;

/**
 * Created by 胡锦龙_Squall on 2018/1/15.
 */

public class Action implements Serializable{
    // 链接url
    public String link_url;

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }

    @Override
    public String toString() {
        return "Action{" +
                "link_url='" + link_url + '\'' +
                '}';
    }
}
