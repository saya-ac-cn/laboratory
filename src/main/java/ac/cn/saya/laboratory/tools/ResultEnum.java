package ac.cn.saya.laboratory.tools;

/**
 * 枚举异常的类型
 */
public enum ResultEnum {

    DB_ERROR(-8, "数据库读写失败"),
    NOT_CHECKING(-7, "未登录"),
    NOT_PARAMETER(-6, "缺少参数"),
    FORBID_POWER(-5, "接口已关闭"),
    RollBACK(-4, "操作无效，数据回滚"),
    NOT_EXIST(-3, "记录不存在"),
    ERROP(-2, "处理失败"),
    UNKONW_ERROR(-1, "未知错误"),
    SUCCESS(0, "处理成功");

    private int code;

    private String msg;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}