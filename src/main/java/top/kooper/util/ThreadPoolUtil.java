package top.kooper.util;

import java.util.concurrent.*;

/**
 * @author ZxT
 * @date 2019-09-27
 * @desc
 */
public class ThreadPoolUtil {

    private static final Executor executor = new ThreadPoolExecutor(10, 10, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100));

}
