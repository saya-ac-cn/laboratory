package ac.cn.saya.laboratory.tools;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * AES-128-ECB加密
 *
 * @Title: AesUtil
 * @ProjectName laboratory
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2019-09-21 17:55
 * @Description:
 */

public class AesUtil {

    /**
     * 加解/密算法模式
     */
    private final static String AES = "AES/ECB/PKCS5Padding";
    private final static String ENCODE = "utf-8";

    /**
     * 密钥，必须为16位，不要随意更改
     */
    private final static String DEFAULTKEY = "3xs51T6cRiu0oCk4";

    /**
     * @描述 加密
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019-09-21
     * @修改人和其它信息
     */
    public static String Encrypt(String content) throws Exception {
        if (!AesUtil.CheckingKey()) {
            // 密钥不合法
            return null;
        }
        byte[] raw = (AesUtil.DEFAULTKEY).getBytes(AesUtil.ENCODE);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        //"算法/模式/补码方式"
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(content.getBytes(AesUtil.ENCODE));
        //此处使用BASE64做转码功能，同时能起到2次加密的作用。
        return new Base64().encodeToString(encrypted);
    }

    /**
     * @描述 解密算法
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019-09-21
     * @修改人和其它信息
     */
    public static String Decrypt(String content) throws Exception {
        if (!AesUtil.CheckingKey()) {
            // 密钥不合法
            return null;
        }
        try {
            byte[] raw = (AesUtil.DEFAULTKEY).getBytes(AesUtil.ENCODE);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance(AesUtil.AES);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            //先用base64解密
            byte[] encrypted1 = new Base64().decode(content);
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original,AesUtil.ENCODE);
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    /**
     * @描述 判断密钥是否合法
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019-09-21
     * @修改人和其它信息
     */
    public static Boolean CheckingKey(){
        // 判断Key是否正确
        if (AesUtil.DEFAULTKEY == null) {
            System.out.print("Key为空null");
            return false;
        }
        // 判断Key是否为16位
        if ((AesUtil.DEFAULTKEY).length() != 16) {
            System.out.print("Key长度不是16位");
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        // 需要加密的字串
        String cSrc = "测试的文本加密方式";
        System.out.println(cSrc);
        try {
            // 加密
            String enString = AesUtil.Encrypt(cSrc);
            System.out.println("加密后的字串是：" + enString);

            // 解密
            String DeString = AesUtil.Decrypt(enString);
            System.out.println("解密后的字串是：" + DeString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
