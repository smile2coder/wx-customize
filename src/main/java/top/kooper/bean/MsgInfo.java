package top.kooper.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author ZxT
 * @date 2019-09-27
 * @desc
 */
@Data
public class MsgInfo {
    /**
     * MsgId : 6940355097161859484
     * FromUserName : @e6cc455ebb069acdb4439b1ae9faa07fdd4b5a293f96d4cf62e54b49208510ff
     * ToUserName : @e6cc455ebb069acdb4439b1ae9faa07fdd4b5a293f96d4cf62e54b49208510ff
     * MsgType : 1
     * Content : 18513030338
     * Status : 3
     * ImgStatus : 1
     * CreateTime : 1569575017
     * VoiceLength : 0
     * PlayLength : 0
     * FileName :
     * FileSize :
     * MediaId :
     * Url :
     * AppMsgType : 0
     * StatusNotifyCode : 0
     * StatusNotifyUserName :
     * ForwardFlag : 0
     * AppInfo : {"AppID":"","Type":0}
     * HasProductId : 0
     * Ticket :
     * ImgHeight : 0
     * ImgWidth : 0
     * SubMsgType : 0
     * NewMsgId : 6940355097161859484
     * OriContent :
     * EncryFileName :
     */

    @JSONField(name = "MsgId")
    private String MsgId;
    @JSONField(name = "FromUserName")
    private String FromUserName;
    @JSONField(name = "ToUserName")
    private String ToUserName;
    @JSONField(name = "MsgType")
    private int MsgType;
    @JSONField(name = "Content")
    private String Content;
    @JSONField(name = "Status")
    private int Status;
    @JSONField(name = "ImgStatus")
    private int ImgStatus;
    @JSONField(name = "CreateTime")
    private int CreateTime;
    @JSONField(name = "VoiceLength")
    private int VoiceLength;
    @JSONField(name = "PlayLength")
    private int PlayLength;
    @JSONField(name = "FileName")
    private String FileName;
    @JSONField(name = "FileSize")
    private String FileSize;
    @JSONField(name = "MediaId")
    private String MediaId;
    @JSONField(name = "Url")
    private String Url;
    @JSONField(name = "AppMsgType")
    private int AppMsgType;
    @JSONField(name = "StatusNotifyCode")
    private int StatusNotifyCode;
    @JSONField(name = "StatusNotifyUserName")
    private String StatusNotifyUserName;
    @JSONField(name = "ForwardFlag")
    private int ForwardFlag;
    @JSONField(name = "AppInfo")
    private AppInfoBean AppInfo;
    @JSONField(name = "HasProductId")
    private int HasProductId;
    @JSONField(name = "Ticket")
    private String Ticket;
    @JSONField(name = "ImgHeight")
    private int ImgHeight;
    @JSONField(name = "ImgWidth")
    private int ImgWidth;
    @JSONField(name = "SubMsgType")
    private int SubMsgType;
    @JSONField(name = "NewMsgId")
    private long NewMsgId;
    @JSONField(name = "OriContent")
    private String OriContent;
    @JSONField(name = "EncryFileName")
    private String EncryFileName;


    public static class AppInfoBean {
        /**
         * AppID :
         * Type : 0
         */

        @JSONField(name = "AppID")
        private String AppID;
        @JSONField(name = "Type")
        private int Type;

        public String getAppID() {
            return AppID;
        }

        public void setAppID(String AppID) {
            this.AppID = AppID;
        }

        public int getType() {
            return Type;
        }

        public void setType(int Type) {
            this.Type = Type;
        }
    }

}
