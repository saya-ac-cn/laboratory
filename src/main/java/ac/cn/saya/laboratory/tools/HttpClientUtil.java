package ac.cn.saya.laboratory.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * http调用工具类
 *
 * @Title: HttpClientUtil
 * @ProjectName erp-group
 * @Description: TODO
 * @Author Administrator
 * @Date: 2019/9/26 0026 09:46
 * @Description:
 */
@Component("httpClientUtil")
public class HttpClientUtil {

    @Value("${wx.appid}")
    private String appId;

    @Value("${wx.secret}")
    private String secret;

    @Value("${wx.url.token}")
    private String requestWxTokenUrl;

    @Value("${wx.url.openid}")
    private String requestWxOpenidUrl;

    /**
     * @描述 提取access_token
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/9/26 0026
     * @修改人和其它信息
     */
    public JSONObject getToken() {
        //appid和secret是开发者分别是小程序ID和小程序密钥，开发者通过微信公众平台-》设置-》开发设置就可以直接获取，
        StringBuffer urlBuf = new StringBuffer(this.requestWxTokenUrl);
        urlBuf.append("?grant_type=client_credential&appid=").append(this.appId);
        urlBuf.append("&secret=").append(this.secret);
        return this.sendWxRequest(urlBuf.toString());
    }

    /**
     * @描述 提取用户相关信息（openid）
     * @参数 jsCode 本次请求code
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/9/26 0026
     * @修改人和其它信息
     */
    public JSONObject getOpenId(String jsCode) {
        StringBuffer urlBuf = new StringBuffer(this.requestWxOpenidUrl);
        urlBuf.append("?appid=").append(this.appId);
        urlBuf.append("&secret=").append(this.secret);
        urlBuf.append("&js_code=").append(jsCode);
        urlBuf.append("&grant_type=authorization_code");
        return this.sendWxRequest(urlBuf.toString());
    }

    /**
     * @描述 公用发送http请求(get)
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/9/26 0026
     * @修改人和其它信息
     */
    public JSONObject sendWxRequest(String url) {
        BufferedReader in = null;
        try {
            URL weChatUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = weChatUrl.openConnection();
            // 设置通用的请求属性
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            JSONObject jsonObject = JSON.parseObject(sb.toString());
            return jsonObject;
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("调用微信接口异常",e, HttpClientUtil.class);
            return null;
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /**
     * @描述 发送post请求 json格式
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/9/26 0026
     * @修改人和其它信息
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("发送 POST 请求出现异常",e, HttpClientUtil.class);
        }
        //使用finally块来关闭输出流、输入流
        finally {
            if (out != null) {
                out.close();
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
