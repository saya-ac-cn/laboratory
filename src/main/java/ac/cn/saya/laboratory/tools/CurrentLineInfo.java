package ac.cn.saya.laboratory.tools;

/**
 * @Title: CurrentLineInfo
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/10/20 21:59
 * @Description:
 */

public class CurrentLineInfo {

    private static int originStackIndex = 2;

    public static String getFileName() {
        return Thread.currentThread().getStackTrace()[originStackIndex].getFileName();
    }

    public static String getClassName() {
        return Thread.currentThread().getStackTrace()[originStackIndex].getClassName();
    }

    public static String getMethodName() {
        return Thread.currentThread().getStackTrace()[originStackIndex].getMethodName();
    }

    public static int getLineNumber() {
        return Thread.currentThread().getStackTrace()[originStackIndex].getLineNumber();
    }

    public static String printCurrentLineInfo()
    {
        String errorInfo = "异常栈轨迹信息(Stack Trace)" + '\n'
                + CurrentLineInfo.getFileName() + '\n'
                + CurrentLineInfo.getClassName() + '\n'
                + CurrentLineInfo.getMethodName() + '\n'
                + CurrentLineInfo.getLineNumber() + '\n';
        return errorInfo;
    }

}
