package ac.cn.saya.laboratory.tools;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

/**
 * @Title: Log4jUtils
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/9/29 23:05
 * @Description:
 */

public class Log4jUtils {

    /**
     * log4j日志记录
     * @param t
     * @return
     */
    public static Optional<String> getTrace(Throwable t) {
        StringBuffer buffer;
        StringWriter stringWriter = null;
        PrintWriter writer = null;
        try {
            stringWriter = new StringWriter();
            writer = new PrintWriter(stringWriter);
            t.printStackTrace(writer);
            buffer = stringWriter.getBuffer();
        } finally {
            if (null != writer){
                writer.close();
            }
            try {
                if (null != stringWriter){
                    stringWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 非空返回
        return Optional.ofNullable(buffer.toString());
    }

}
