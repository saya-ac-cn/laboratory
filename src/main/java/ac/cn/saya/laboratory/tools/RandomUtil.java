package ac.cn.saya.laboratory.tools;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
     * 生成随机文件名：当前年月日+五位随机数
     *
     * @return
     */
    public static String getRandomFileName() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
        String str = now.format(format);
        Random random = new Random();
        // 获取5位随机数
        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;
        return str + rannum;
    }
    public static void main(String[] args) {
        String fileName = RandomUtil.getRandomFileName();
        System.out.println(fileName);//2014030788359
    }

}
