package ac.cn.saya.laboratory.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Title: DateUtil
 * @ProjectName jvm
 * @Description: TODO
 * @Author Saya
 * @Date: 2019/1/23 22:28
 * @Description:
 * 日期工具类
 *
 */

public class DateUtil {

    /**
     * 设置静态日期格式
     */
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * @描述 获取该月的天数
     * @参数  
     * @返回值  
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/23
     * @修改人和其它信息
     */
    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * @描述 获取该月第一天是星期几
     * @参数  [date]
     * @返回值  void
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/23
     * @修改人和其它信息
     */
    public static int getFirstDayWeek(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //星期日i==1，星期六i==7
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * @描述 获取指定月份的第一天
     * @参数  
     * @返回值  
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/23
     * @修改人和其它信息
     */
    public static String getFirstDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return format.format(calendar.getTime());
    }

    /**
     * @描述 获取指定月份的最后一天
     * @参数
     * @返回值  
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/23
     * @修改人和其它信息
     */
    public static String getLastDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return format.format(calendar.getTime());
    }

    public static void main(String[] args) throws ParseException {
        // 第一天
        System.out.println(getFirstDay(format.parse("2019-01-04")));
        // 最后一天
        System.out.println(getLastDay(format.parse("2019-01-04")));
        // 获取该月的总天数
        System.out.println(getDaysOfMonth(format.parse("2019-01-01")));
        // 获取该月第一天是星期几
        System.out.println(getFirstDayWeek(format.parse("2019-01-01")));
    }

}
