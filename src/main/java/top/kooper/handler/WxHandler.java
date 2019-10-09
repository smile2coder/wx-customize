package top.kooper.handler;

import top.kooper.bean.MsgInfo;
import top.kooper.bean.SendMsg;
import top.kooper.bean.User;

import java.util.List;

/**
 * @author ZxT
 * @date 2019-09-25
 * @desc
 */
public interface WxHandler {

    /**
     * 登录成功后处理
     * @param user
     * @param contactList
     */
    void afterLogin(User user, List contactList);


    SendMsg msgHandler(MsgInfo msgInfo);


    void afterLogout();
}
