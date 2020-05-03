package ac.cn.saya.laboratory.tools;

/**
 * 统一返回的外包装类
 */
public class ResultUtil {

    public ResultUtil() {
    }

    /**
     * 用于查询，添加，修改等方法返回值
     * @param object
     * @return
     */
    public static Result success(Object object) {
        Result result = new Result();
        result.setCode(0);
        result.setMsg("成功");
        //返回执行成功后的模型
        result.setData(object);
        return result;
    }

    /**
     * 用于修改、删除等方法返回的值，只返回操作的结果
     * @return
     */
    public static Result success() {
        Result result = new Result();
        result.setCode(0);
        result.setMsg("成功");
        return result;
    }

    /**
     * 用于特殊场景下的返回值（eg：查询学生注册状态）
     * @param code
     * @param msg
     * @param object
     * @return
     */
    public static Result success(int code,String msg,Object object)
    {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        //返回执行成功后的模型
        result.setData(object);
        return result;
    }

    /**
     * 用于错误，异常等方法返回值
     * @param code
     * @param msg
     * @return
     */
    public static Result error(int code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    /**
     * 用于错误，异常等方法返回值
     * @param value
     * @return
     */
    public static Result error(ResultEnum value) {
        Result result = new Result();
        result.setCode(value.getCode());
        result.setMsg(value.getMsg());
        return result;
    }

}