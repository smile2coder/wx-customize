package top.kooper.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ZxT
 * @date 2019-09-26
 * @desc
 */
@Getter
@AllArgsConstructor
public enum WxReturnCodeEnum {

    RETCODE_SUCCESS(0, "正常"),
    SUCCESS(200, "成功"),
    SCAN_SUCCESS(201, "扫描成功"),
    LOGIN_TIMEOUT(408, "登录超时"),
    RETCODE_LOGOUT(1100, "失败/登出微信");

    private int code;
    private String desc;

}
