package top.kooper.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ZxT
 * @date 2019-09-27
 * @desc
 */
@Getter
@AllArgsConstructor
public enum WxMsgTypeEnum {

    TEXT(1, "文本消息"),
    IMAGE(3, "图片消息"),
    VOICE(34, "语音消息"),
    FRIEND_CONFIRM(37, "好友确认消息"),
    POSSIBLEFRIEND_MSG(40, "POSSIBLEFRIEND_MSG"),
    BUSINESS_CARD(42, "共享名片"),
    VIDEO(43, "视频消息"),
    ANIMATION(47, "动画表情"),
    LOCATION(48, "位置消息"),
    SHARE(49, "分享链接"),
    VOIPMSG(50, "VOIPMSG"),
    WX_INIT(51, "微信初始化消息"),
    VOIPNOTIFY(52, "VOIPNOTIFY"),
    VOIPINVITE(53, "VOIPINVITE"),
    SMALL_VIDEO(62, "小视频"),
    SYSNOTICE(9999, "SYSNOTICE"),
    SYSTEM(10000, "系统消息"),
    WITHDRAW(10002, "撤回消息");

    private int code;
    private String msg;
}
