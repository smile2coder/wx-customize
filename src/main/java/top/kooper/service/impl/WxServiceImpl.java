package top.kooper.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.java.Log;
import top.kooper.bean.MsgInfo;
import top.kooper.bean.SendMsg;
import top.kooper.bean.SyncKey;
import top.kooper.bean.User;
import top.kooper.cache.Cache;
import top.kooper.cache.DefaultCache;
import top.kooper.enums.WxMsgTypeEnum;
import top.kooper.enums.WxReturnCodeEnum;
import top.kooper.enums.WxUrlEnum;
import top.kooper.handler.WxHandler;
import top.kooper.service.WxService;
import top.kooper.util.ImageUtil;
import top.kooper.util.StringUtil;

import java.net.HttpCookie;
import java.util.*;

/**
 * @author ZxT
 * @date 2019-09-25
 * @desc
 */
@Log
public class WxServiceImpl implements WxService {

    private Cache cache;
    private WxHandler wxHandler;

    public WxServiceImpl(WxHandler wxHandler) {
        this(new DefaultCache(), wxHandler);
    }

    public WxServiceImpl(Cache cache, WxHandler wxHandler) {
        this.cache = cache;
        this.wxHandler = wxHandler;
    }

    @Override
    public void loginUUID() {
        String url = WxUrlEnum.GET_UUID.getUrl();
        Map<String, Object> params = new HashMap<>();
        params.put("appid", "wx782c26e4c19acffb");
        params.put("fun", "new");
        params.put("lang", "zh_CN");
        params.put("_", String.valueOf(System.currentTimeMillis()));

        String body = HttpRequest.post(url).form(params).execute().body();
        String uuid = StringUtil.extract(body, "\"(.*?)\"");

        cache.setUUID(uuid);
    }

    @Override
    public void qrcode() {
        String path = this.getClass().getClassLoader().getResource("") + cache.getUUID() + ".jpg";

        String url = String.format(WxUrlEnum.QRCODE.getUrl(), cache.getUUID());
        log.info("下载登录二维码");
        HttpUtil.downloadFile(url, FileUtil.file(path));
        log.info("打开二维码");
        ImageUtil.openQrcode(path);
    }

    @Override
    public void monitor() {
        String url = WxUrlEnum.MONITOR.getUrl();
        Map<String, Object> params = new HashMap<>();
        params.put("tip", 0);
        params.put("uuid", cache.getUUID());
        params.put("_", String.valueOf(System.currentTimeMillis()));
        params.put("loginicon", true);

        int code;
        do {
            String body = HttpRequest.get(url).form(params).execute().body();
            log.info("body:" + body);
            code = Integer.valueOf(StringUtil.extract(body, "window.code=(.*?);")).intValue();
        } while (code != WxReturnCodeEnum.SCAN_SUCCESS.getCode() && code != WxReturnCodeEnum.SUCCESS.getCode());

        log.info("扫描成功，等待确认");
    }

    @Override
    public void comfirm() {
        String url = WxUrlEnum.MONITOR.getUrl();
        Map<String, Object> params = new HashMap<>();
        params.put("tip", 1);
        params.put("uuid", cache.getUUID());
        params.put("_", String.valueOf(System.currentTimeMillis()));
        params.put("loginicon", true);

        while (true) {
            String body = HttpRequest.get(url).form(params).execute().body();
            int code = Integer.valueOf(StringUtil.extract(body, "window.code=(.*?);")).intValue();
            if(code == WxReturnCodeEnum.LOGIN_TIMEOUT.getCode()) {
                log.info(WxReturnCodeEnum.LOGIN_TIMEOUT.getDesc());
                break;
            } else if(code == WxReturnCodeEnum.SUCCESS.getCode()) {
                log.info("扫描成功");
                // https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxnewloginpage?ticket=xxx&uuid=xxx&lang=xxx&scan=xxx
                String redirect_uri = StringUtil.extract(body, "window.redirect_uri=(.*?);");

                String ticket = StringUtil.extract(redirect_uri, "ticket=(.*?)&");
                String scan = StringUtil.extract(redirect_uri, "scan=(.*?)$");
                String host = StringUtil.extract(redirect_uri, "https://(.*?)/");

                this.cache.setTicket(ticket);
                this.cache.setScan(scan);
                this.cache.setHost("webpush." + host);
                break;
            }
        }
    }

    @Override
    public void webLoginPage() {
        String url = WxUrlEnum.WEB_LOGIN_PAGE.getUrl();
        Map<String, Object> params = new HashMap<>();
        params.put("ticket", this.cache.getTicket());
        params.put("uuid", this.cache.getUUID());
        params.put("lang", "zh_CN");
        params.put("scan", this.cache.getScan());
        params.put("fun", "new");

        String body = HttpRequest.get(url).form(params).execute().body();
        log.info("body :" + body);

        Map<String, Object> stringObjectMap = XmlUtil.xmlToMap(body);

        String skey = stringObjectMap.get("skey").toString();
        String wxsid = stringObjectMap.get("wxsid").toString();
        String wxuin = stringObjectMap.get("wxuin").toString();
        String passTicket = stringObjectMap.get("pass_ticket").toString();

        this.cache.setSkey(skey);
        this.cache.setWxsid(wxsid);
        this.cache.setWxuin(wxuin);
        this.cache.setPassTicket(passTicket);
    }

    @Override
    public void init() {
        String url = String.format(WxUrlEnum.INIT.getUrl(), this.cache.getPassTicket(), this.cache.getSkey(), ~(System.currentTimeMillis()));

        String deviceID = UUID.randomUUID().toString().replace("-", "");
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> BaseRequest = new HashMap<>();
        BaseRequest.put("DeviceID", deviceID);
        BaseRequest.put("Sid", this.cache.getWxsid());
        BaseRequest.put("Uin", this.cache.getWxuin());
        BaseRequest.put("Skey", this.cache.getSkey());
        params.put("BaseRequest", BaseRequest);

        String json = JSON.toJSONString(params);

        HttpResponse execute = HttpRequest.post(url).body(json).execute();

        List<HttpCookie> cookies = execute.getCookies();
        String body = execute.body();
//        log.info("body:" + body);

        JSONObject result = JSON.parseObject(body);
        User user = result.getObject("User", User.class);
        List contactList = result.getObject("ContactList", List.class);
        SyncKey syncKey = result.getObject("SyncKey", SyncKey.class);

        this.wxHandler.afterLogin(user, contactList);

        this.cache.setLive(true);
        this.cache.setDeviceID(deviceID);
        this.cache.setUser(user);
        this.cache.setContactList(contactList);
        this.cache.setSyncKey(syncKey);
        this.cache.setCookies(cookies);
    }

    @Override
    public void syncCheck() throws InterruptedException {
        while (this.cache.isLive()) {
            String url = String.format(WxUrlEnum.SYNCCHECK.getUrl(), this.cache.getHost());

            HttpCookie[] cookies = new HttpCookie[10];
            cookies = this.cache.getCookies().toArray(cookies);

            SyncKey syncKey = this.cache.getSyncKey();
            List<SyncKey.ListBean> list = syncKey.getList();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                SyncKey.ListBean listBean = list.get(i);
                sb.append(listBean.getKey()).append("_").append(listBean.getVal()).append("|");
                if(i < list.size()-1) {
                    sb.append("|");
                }
            }

            String syncKeyStr = sb.toString();

            Map<String, Object> params = new HashMap<>();
            params.put("sid", this.cache.getWxsid());
            params.put("uin", this.cache.getWxuin());
            params.put("skey", this.cache.getSkey());
            params.put("deviceid", this.cache.getDeviceID());
            params.put("synckey", syncKeyStr);
            params.put("r", String.valueOf(System.currentTimeMillis()));
            params.put("_", String.valueOf(System.currentTimeMillis()));

            String body = HttpRequest.get(url).form(params).cookie(cookies).execute().body();

            String extract = StringUtil.extract(body, "window.synccheck=(.*?)$");

            JSONObject jsonObject = JSON.parseObject(extract);
            int retcode = jsonObject.getIntValue("retcode");
            if(retcode == WxReturnCodeEnum.RETCODE_LOGOUT.getCode()) {
                this.cache.setLive(false);
                this.wxHandler.afterLogout();
                break;
            }

            int selector = jsonObject.getIntValue("selector");
            // 0 正常、2 新的消息、7 进入/离开聊天界面
            if(selector == 2 || selector == 7) {
                // 有新消息
                this.cache.setNewMsg(true);
            }

            Thread.sleep(5 * 1000);
        }
    }

    @Override
    public void webwxsync() throws InterruptedException {
        while (true) {
            while (this.cache.isNewMsg()) {
                this.cache.setNewMsg(false);

                String url = String.format(WxUrlEnum.WEBWXSYNC.getUrl(), this.cache.getWxsid(), this.cache.getSkey(), this.cache.getPassTicket());

                HttpCookie[] cookies = new HttpCookie[10];
                cookies = this.cache.getCookies().toArray(cookies);

                Map<String, Object> params = new HashMap<>();
                Map<String, Object> BaseRequest = new HashMap<>();
                BaseRequest.put("Uin", this.cache.getWxuin());
                BaseRequest.put("Sid", this.cache.getWxsid());
                BaseRequest.put("Skey", this.cache.getSkey());
                BaseRequest.put("DeviceID", this.cache.getDeviceID());
                params.put("BaseRequest", BaseRequest);
                params.put("SyncKey", this.cache.getSyncKey());
                params.put("rr", ~(System.currentTimeMillis()));

                String json = JSON.toJSONString(params);

                String body = HttpRequest.post(url).body(json).execute().body();

                JSONObject jsonObject = JSON.parseObject(body);
                SyncKey syncKey = jsonObject.getObject("SyncCheckKey", SyncKey.class);

                List<MsgInfo> msgInfoList = jsonObject.getJSONArray("AddMsgList").toJavaList(MsgInfo.class);

                msgInfoList.parallelStream().forEach(e -> {
                    SendMsg sendMsg = this.wxHandler.msgHandler(e);
                    if(Objects.nonNull(sendMsg)) {
                        this.sendMsg(e.getFromUserName(), sendMsg.getMsgType(), sendMsg.getContent());
                    }
                });
                this.cache.setSyncKey(syncKey);
            }
            Thread.sleep(2*1000);
        }
    }

    @Override
    public void sendMsg(String toUserName, WxMsgTypeEnum msgType, String content) {
        String url = String.format(WxUrlEnum.SEND_MSG.getUrl(), this.cache.getPassTicket());

        HttpCookie[] cookies = new HttpCookie[10];
        cookies = this.cache.getCookies().toArray(cookies);

        Map<String, Object> params = new HashMap<>();
        Map<String, Object> BaseRequest = new HashMap<>();
        BaseRequest.put("Uin", this.cache.getWxuin());
        BaseRequest.put("Sid", this.cache.getWxsid());
        BaseRequest.put("Skey", this.cache.getSkey());
        BaseRequest.put("DeviceID", this.cache.getDeviceID());
        params.put("BaseRequest", BaseRequest);

        Map<String, Object> Msg = new HashMap<>();
        Msg.put("Type", msgType.getCode());
        Msg.put("Content", content);
        Msg.put("FromUserName", this.cache.getUser().getUserName());
        Msg.put("ToUserName", toUserName);

        String random = String.valueOf(System.currentTimeMillis());
        Msg.put("ClientMsgId", random);
        Msg.put("LocalID", random);
        params.put("Msg", Msg);

        String body = HttpRequest.post(url).body(JSON.toJSONString(params)).cookie(cookies).execute().body();
        log.info(body);
    }

}
