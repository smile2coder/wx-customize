package top.kooper.service;

import top.kooper.enums.WxMsgTypeEnum;

/**
 * @author ZxT
 * @date 2019-09-25
 * @desc
 */
public interface WxService {

    void loginUUID();

    void qrcode();

    void monitor();

    void comfirm();

    void webLoginPage();

    void init();

    void syncCheck() throws InterruptedException;

    void webwxsync() throws InterruptedException;

    void sendMsg(String toUserName, WxMsgTypeEnum msgType, String content);
}
