package ac.cn.saya.laboratory.tools;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @Title: RandomUtil
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/11/11 20:22
 * @Description:
 * 随机文件命名
 */

public class RandomUtil {

    /**
     * 生成随机文件名：当前年月日时分秒+五位随机数
     *
     * @return
     */
    public static String getRandomFileName() {
        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String str = simpleDateFormat.format(date);
        Random random = new Random();
        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
        return str + rannum;// 当前时间
    }
    public static void main(String[] args) {
        String fileName = RandomUtil.getRandomFileName();
        System.out.println(fileName);//2014030788359
    }

}
