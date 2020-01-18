package ac.cn.saya.laboratory.handle;

import ac.cn.saya.laboratory.exception.MyException;
import ac.cn.saya.laboratory.tools.Log4jUtils;
import ac.cn.saya.laboratory.tools.Result;
import ac.cn.saya.laboratory.tools.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局控制的拦截器，主要用于对异常的处理-除了在此要配置外，还要在dispatcher-servlet中配置，让它能正常扫描到。
 */
@ControllerAdvice
public class ExceptionHandle {


    private static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e) {
        if (e instanceof MyException) {
            MyException myException = (MyException) e;
            return ResultUtil.error(myException.getCode(), myException.getMessage());
        } else {
            //不在定义范围内的异常错误
            logger.error("处理未能捕获的异常" + Log4jUtils.getTrace(e));
            return ResultUtil.error(-1, "未知错误");
        }
    }

}