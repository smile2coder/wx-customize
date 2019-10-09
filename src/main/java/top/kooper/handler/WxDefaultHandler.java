package top.kooper.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.tokenizer.Result;
import cn.hutool.extra.tokenizer.TokenizerEngine;
import cn.hutool.extra.tokenizer.TokenizerUtil;
import cn.hutool.extra.tokenizer.Word;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import top.kooper.bean.MsgInfo;
import top.kooper.bean.SendMsg;
import top.kooper.bean.User;
import top.kooper.enums.WxMsgTypeEnum;
import top.kooper.util.StringUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

/**
 * @author ZxT
 * @date 2019-09-28
 * @desc
 */
@Log
public class WxDefaultHandler implements WxHandler {
    @Override
    public void afterLogin(User user, List contactList) {
        log.info("欢迎您，" + user.getNickName());
    }

    @Override
    public SendMsg msgHandler(MsgInfo msgInfo) {
        String fromUserName = msgInfo.getFromUserName();
        String content = msgInfo.getContent();
        log.info("新消息：["+ fromUserName+"]  "+content);
        if(fromUserName.startsWith("@@")) {
            return null;
        }

        if(content.contains("是什么垃圾")) {
            String extract = StringUtil.extract(content, "(.*?)是什么垃圾$");
            StringBuilder sb = new StringBuilder();
            String url = null;
            try {
                String encode = URLEncoder.encode(extract, "utf-8");
                url = String.format("http://gs.choviwu.top/garbage/getGarbage?garbageName=%s", encode);
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
            String body = HttpRequest.get(url).execute().body();
            JSONObject jsonObject = JSON.parseObject(body);
            if(jsonObject.getIntValue("code") == 200) {
                JSONArray data = jsonObject.getJSONArray("data");
                for (int i = 0; i < data.size(); i++) {
                    JSONObject jsonObject1 = data.getJSONObject(i);
                    String gname = jsonObject1.getString("gname");
                    String gtype = jsonObject1.getString("gtype");
                    sb.append("<").append(gname).append(">").append("是").append(gtype).append("\n");
                }
            }
            content = sb.toString();
        } else{
            content = content.replace("吗", "");
            content = content.replace("?", "!");
            content = content.replace("?", "!");
        }

        SendMsg sendMsg = new SendMsg();
        sendMsg.setContent(StringUtils.isEmpty(content)? "1":content);
        sendMsg.setMsgType(WxMsgTypeEnum.TEXT);
        return sendMsg;
    }

    @Override
    public void afterLogout() {
        log.info("退出");
    }
}
