package top.kooper.cache;

import top.kooper.bean.MsgInfo;
import top.kooper.bean.SyncKey;
import top.kooper.bean.User;

import java.net.HttpCookie;
import java.util.List;

/**
 * @author ZxT
 * @date 2019-09-25
 * @desc
 */
public interface Cache {

    void setUUID(String uuid);

    String getUUID();

    void setTicket(String ticket);

    String getTicket();

    void setScan(String scan);

    String getScan();

    void setSkey(String skey);

    String getSkey();

    void setWxsid(String wxsid);

    String getWxsid();

    void setWxuin(String wxuin);

    String getWxuin();

    void setPassTicket(String passTicket);

    String getPassTicket();

    void setDeviceID(String deviceID);

    String getDeviceID();

    void setSyncKey(SyncKey syncKey);

    SyncKey getSyncKey();

    void setUser(User user);

    User getUser();

    void setContactList(List<User> contactList);

    List<User> getContactList();

    boolean isLive();

    void setLive(boolean live);

    boolean isNewMsg();

    void setNewMsg(boolean newMsg);

    MsgInfo getNewMsg();

    void setNewMsg(MsgInfo msgInfo);

    String getHost();

    void setHost(String host);

    List<HttpCookie> getCookies();

    void setCookies(List<HttpCookie> cookies);
}
