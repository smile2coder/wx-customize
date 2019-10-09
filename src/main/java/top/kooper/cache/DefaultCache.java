package top.kooper.cache;

import top.kooper.bean.MsgInfo;
import top.kooper.bean.SyncKey;
import top.kooper.bean.User;

import java.net.HttpCookie;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author ZxT
 * @date 2019-09-25
 * @desc
 */
public class DefaultCache implements Cache {

    private boolean live;
    private String uuid;
    private String ticket;
    private String scan;
    private String skey;
    private String wxsid;
    private String wxuin;
    private String passTicket;
    private String deviceID;
    private SyncKey syncKey;
    private User user;
    private List<User> contactList;
    private boolean newMsg;
    private String host;
    private List<HttpCookie> cookies;

    private LinkedBlockingQueue<MsgInfo> msgInfoQueue;

    @Override
    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getUUID() {
        return this.uuid;
    }

    @Override
    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    @Override
    public String getTicket() {
        return this.ticket;
    }

    @Override
    public void setScan(String scan) {
        this.scan = scan;
    }

    @Override
    public String getScan() {
        return this.scan;
    }

    @Override
    public String getSkey() {
        return skey;
    }

    @Override
    public void setSkey(String skey) {
        this.skey = skey;
    }

    @Override
    public String getWxsid() {
        return wxsid;
    }

    @Override
    public void setWxsid(String wxsid) {
        this.wxsid = wxsid;
    }

    @Override
    public String getWxuin() {
        return wxuin;
    }

    @Override
    public void setWxuin(String wxuin) {
        this.wxuin = wxuin;
    }

    @Override
    public String getPassTicket() {
        return passTicket;
    }

    @Override
    public void setPassTicket(String passTicket) {
        this.passTicket = passTicket;
    }

    @Override
    public String getDeviceID() {
        return deviceID;
    }

    @Override
    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    @Override
    public SyncKey getSyncKey() {
        return syncKey;
    }

    @Override
    public void setSyncKey(SyncKey syncKey) {
        this.syncKey = syncKey;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public List<User> getContactList() {
        return contactList;
    }

    @Override
    public void setContactList(List<User> contactList) {
        this.contactList = contactList;
    }

    @Override
    public boolean isLive() {
        return live;
    }

    @Override
    public void setLive(boolean live) {
        this.live = live;
    }

    @Override
    public boolean isNewMsg() {
        return newMsg;
    }

    @Override
    public void setNewMsg(boolean newMsg) {
        this.newMsg = newMsg;
    }

    @Override
    public MsgInfo getNewMsg() {
        return this.msgInfoQueue.poll();
    }

    @Override
    public void setNewMsg(MsgInfo msgInfo) {
        this.msgInfoQueue.offer(msgInfo);
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public List<HttpCookie> getCookies() {
        return cookies;
    }

    @Override
    public void setCookies(List<HttpCookie> cookies) {
        this.cookies = cookies;
    }
}
