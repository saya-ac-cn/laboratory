package ac.cn.saya.laboratory.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

/**
 * @Title: CurrentLineInfo
 * @ProjectName DataCenter
 * @Author shmily
 * @Date: 2018/10/20 21:59
 * @Description:
 */

public class CurrentLineInfo {

    private static int originStackIndex = 0;

    /**
     * log4j日志记录
     *
     * @param t
     * @return
     */
    public static Optional<String> getStackTrace(@NotNull final Throwable t) {
        StringBuffer buffer = null;
        try (StringWriter stringWriter = new StringWriter(); PrintWriter writer = new PrintWriter(stringWriter)) {
            t.printStackTrace(writer);
            buffer = stringWriter.getBuffer();
        } catch (IOException e) {
            buffer = new StringBuffer(e.getMessage());
        }
        return Optional.ofNullable(buffer.toString());
    }

    /**
     * @Desc: 异常打印日志 ，提供给打印非正常异常
     * @Author shmily
     * @Date 2020/11/15  下午10:44
     */
    public static void printCurrentLineInfo(@NotNull final String errorTitle, @NotNull final String errorDetail, @NotNull final Class c) {
        Logger logger = LoggerFactory.getLogger(c);
        StringBuffer errorString = new StringBuffer(errorTitle);
        errorString.append("\n------------开始------------");
        errorString.append("\n根本原因：").append(errorDetail);
        errorString.append("\n------------结束------------");
        logger.error(errorString.toString());
    }

    /**
     * @Desc: 异常打印日志 ，提供给打印非正常异常
     * @Author shmily
     * @Date 2020/11/15  下午10:44
     */
    public static void printCurrentLineInfo(@NotNull final String errorTitle, @NotNull final Throwable e, @NotNull final Class c) {
        Logger logger = LoggerFactory.getLogger(c);
        StackTraceElement s = e.getStackTrace()[originStackIndex];
        StringBuffer errorString = new StringBuffer(errorTitle);
        errorString.append("\n------------开始------------");
        errorString.append("\n出错文件名：").append(s.getFileName());
        errorString.append("\n出错的类：").append(s.getClassName());
        errorString.append("\n出错方法：").append(s.getMethodName());
        errorString.append("\n出错的行：").append(s.getLineNumber());
        errorString.append("\n出错信息：").append(e.getMessage());
        errorString.append("\n错误堆栈：").append(getStackTrace(e));
        errorString.append("\n------------结束------------");
        logger.error(errorString.toString());
    }

}
