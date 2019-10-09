package top.kooper.exception;

/**
 * @author ZxT
 * @date 2019-09-25
 * @desc 统一异常类
 */
public class WxException extends RuntimeException {

    public WxException(String message) {
        super(message);
    }

    public WxException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
