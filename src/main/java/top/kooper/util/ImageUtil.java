package top.kooper.util;

import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.system.OsInfo;
import cn.hutool.system.SystemUtil;

/**
 * @author ZxT
 * @date 2019-09-25
 * @desc
 */
public class ImageUtil {

    public static void openQrcode(String path) {
        OsInfo osInfo = SystemUtil.getOsInfo();
        if(osInfo.isWindows()) {
            RuntimeUtil.exec("cmd /c start " + path);
        } else {
            RuntimeUtil.exec("open " + path);
        }
    }
}
