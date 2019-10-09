package top.kooper;

import top.kooper.handler.WxDefaultHandler;
import top.kooper.handler.WxHandler;
import top.kooper.service.WxService;
import top.kooper.service.impl.WxServiceImpl;

/**
 * @author ZxT
 * @date 2019-09-28
 * @desc
 */
public class Start {

    public static void main(String[] args) {
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2,SSLv3");

        WxHandler wxHandler = new WxDefaultHandler();
        WxService wxService = new WxServiceImpl(wxHandler);

        wxService.loginUUID();
        wxService.qrcode();
        wxService.monitor();
        wxService.comfirm();
        wxService.webLoginPage();
        wxService.init();


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    wxService.syncCheck();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "syncCheck").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    wxService.webwxsync();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "webwxsync").start();
    }

}
