package top.kooper.bean;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @author ZxT
 * @date 2019-09-29
 * @desc
 */
@Data
public class SyncKey {


    /**
     * Count : 8
     * List : [{"Key":1,"Val":679995188},{"Key":2,"Val":679995539},{"Key":3,"Val":679995536},{"Key":11,"Val":679995384},{"Key":201,"Val":1569725429},{"Key":206,"Val":1},{"Key":1000,"Val":1569714677},{"Key":1001,"Val":1569714683}]
     */

    @JSONField(name = "Count")
    private int Count;
    @JSONField(name = "List")
    private java.util.List<ListBean> List;

    @Data
    public static class ListBean {
        /**
         * Key : 1
         * Val : 679995188
         */

        @JSONField(name = "Key")
        private int Key;
        @JSONField(name = "Val")
        private int Val;

    }
}
