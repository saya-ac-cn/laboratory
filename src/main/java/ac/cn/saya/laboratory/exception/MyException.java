package ac.cn.saya.laboratory.exception;


import ac.cn.saya.laboratory.tools.ResultEnum;

/**
 * @描述 自定义的异常处理
 * @参数
 * @返回值  
 * @创建人  saya.ac.cn-刘能凯
 * @创建时间  2018/12/27
 * @修改人和其它信息
 */
public class MyException extends RuntimeException {

    private int code;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public MyException() {
    }

    public MyException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public MyException(int code,String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
