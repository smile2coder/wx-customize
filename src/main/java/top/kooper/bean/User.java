package top.kooper.bean;

import lombok.Data;

/**
 * @author ZxT
 * @date 2019-09-26
 * @desc
 */
@Data
public class User {
    private int Uin;
    private String UserName;
    private String NickName;
    private String HeadImgUrl;
    private String Signature;
    private String ChatSet;
    private int Sex;
}
