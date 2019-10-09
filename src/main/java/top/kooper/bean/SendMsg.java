package top.kooper.bean;

import lombok.Data;
import top.kooper.enums.WxMsgTypeEnum;

/**
 * @author ZxT
 * @date 2019-09-28
 * @desc
 */
@Data
public class SendMsg {

    private WxMsgTypeEnum msgType;
    private String content;
}
