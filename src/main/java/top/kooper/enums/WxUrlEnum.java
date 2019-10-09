package top.kooper.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ZxT
 * @date 2019-09-25
 * @desc
 */
@Getter
@AllArgsConstructor
public enum WxUrlEnum {

    GET_UUID("https://login.weixin.qq.com/jslogin", "获取UUID"),
    QRCODE("https://wx.qq.com/qrcode/%s", "获取登录二维码"),
    MONITOR("https://wx.qq.com/cgi-bin/mmwebwx-bin/login", "监听登录"),
    WEB_LOGIN_PAGE("https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxnewloginpage", "获取微信凭证"),
    INIT("https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxinit?pass_ticket=%s&skey=%s&r=%s", "初始化微信"),
    SYNCCHECK("https://%s/cgi-bin/mmwebwx-bin/synccheck", "检测新消息"),
    WEBWXSYNC("https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxsync?sid=%s&skey=%s&lang=zh_CN&pass_ticket=%s", "获取新消息内容"),
    SEND_MSG("https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxsendmsg?pass_ticket=%s", "回复消息");

    private String url;
    private String desc;

}
