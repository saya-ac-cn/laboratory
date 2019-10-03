package ac.cn.saya.laboratory.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * HttpRequest 工具类
 * @Title: HttpRequestUtil
 * @ProjectName spring-boot-pro
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2019-10-02 21:21
 * @Description:
 */

public class HttpRequestUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url 发送请求的URL
     * @param params  请求参数，请求参数应该是 name1=value1,name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static JSONObject sendGet(String url, String... params) {
        BufferedReader in = null;
        //请求返回的结果字符串
        StringBuilder json = new StringBuilder();
        //http请求地址
        StringBuffer http = new StringBuffer();
        //http地址主体
        http.append(url).append("?");
        int index = 0;
        //http地址参数
        for (String param : params) {
            if (!StringUtils.isEmpty(params)) {
                if (index > 0) {
                    http.append("&").append(param);
                } else {
                    http.append(param);
                }
            }
            index++;
        }
        try {
            URL realUrl = new URL(http.toString());
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "UTF8"));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (Exception e) {
            logger.error("发送GET请求出现异常:" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (null == json){
            return null;
        }else {
            return JSON.parseObject(json.toString());
        }
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param httpUrl 发送请求的 URL
     * @param paramMap 请求参数
     * @return 所代表远程资源的响应结果
     */
    public static JSONObject sendPost(String httpUrl, Map<String, String> paramMap) throws Exception {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(httpUrl);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            int index = 0;
            StringBuffer param = new StringBuffer();
            for (Map.Entry entity : paramMap.entrySet()) {
                if (index > 0) {
                    param.append("&");
                }
                param.append(entity.getKey()).append("=").append(entity.getValue());
                index++;
            }

            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            logger.error("发送 POST 请求出现异常:" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (null == result){
            return null;
        }else {
            return JSON.parseObject(result.toString());
        }
    }

    public static JSONObject httpUrlConnetionGet(String httpUrl, String... params) {
        StringBuffer stringBuffer = null;
        try {
            //创建URL对象
            //http请求地址
            StringBuffer http = new StringBuffer();
            //http地址主体
            http.append(httpUrl).append("?");
            int index = 0;
            //http地址参数
            for (String param : params) {
                if (!StringUtils.isEmpty(param)) {
                    if (index > 0) {
                        http.append("&").append(param);
                    } else {
                        http.append(param);
                    }
                }
                index++;
            }
            URL url = new URL(http.toString().replace(" ", "%20"));
            //返回一个URLConnection对象，它表示到URL所引用的远程对象的连接
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //在这里设置一些属性，详细见UrlConnection文档，HttpURLConnection是UrlConnection的子类
            //设置连接超时为5秒
            httpURLConnection.setConnectTimeout(5000);
            //设定请求方式(默认为get)
            httpURLConnection.setRequestMethod("GET");
            //建立到远程对象的实际连接
            httpURLConnection.connect();
            //返回打开连接读取的输入流，输入流转化为StringBuffer类型，这一套流程要记住，常用
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF8"));
            String line = null;
            stringBuffer = new StringBuffer();
            while ((line = bufferedReader.readLine()) != null) {
                //转化为UTF-8的编码格式
                //line = new String(line.getBytes("UTF-8"));
                stringBuffer.append(line);
            }
            bufferedReader.close();
            httpURLConnection.disconnect();
        } catch (Exception e) {
            logger.error("发送GET请求出现异常:" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        if (stringBuffer == null) {
            return null;
        } else {
            return JSON.parseObject(stringBuffer.toString());
        }
    }

    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
     *
     * @param request
     * @return
     * @throws
     */
    public final static String getIpAddress(HttpServletRequest request) {
        if (null == request) {
            return "获取失败";
        }

        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
        String ip = request.getHeader("X-Forwarded-For");
        try {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("Proxy-Client-IP");
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("WL-Proxy-Client-IP");
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("HTTP_CLIENT_IP");
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getRemoteAddr();
                }
            } else if (ip.length() > 15) {
                String[] ips = ip.split(",");
                for (String ip1 : ips) {
                    String strIp = (String) ip1;
                    if (!("unknown".equalsIgnoreCase(strIp))) {
                        ip = strIp;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("获取客户端IP异常:" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            ip = "获取失败";
        }
        if (null == ip){
            return "获取失败";
        }else {
            return ip;
        }
    }


}
