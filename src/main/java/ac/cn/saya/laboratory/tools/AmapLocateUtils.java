package ac.cn.saya.laboratory.tools;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 高德ip定位
 *
 * @Title: AmapLocateUtils
 * @ProjectName spring-boot-pro
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2019-10-01 22:21
 * @Description:
 * 参考官方文档：https://lbs.amap.com/api/webservice/guide/api/ipconfig
 * 实例：https://blog.csdn.net/weixin_34293902/article/details/93369596
 */
@Component(value = "amapLocateUtils")
public class AmapLocateUtils {

    /**
     * 高德ip定位的服务的url
     */
    @Value("${amap.url}")
    private String amapUrl;

    /**
     * 高德ip定位的服务的key
     */
    @Value("${amap.key}")
    private String amapKey;


    /**
     * 返回省+市
     * @param ip
     * @return
     */
    public String getCityByIp(String ip){
        if (StringUtils.isEmpty(ip) || "127.0.0.1".equals(ip) || "localhost".equals(ip)){
            return "本机地址";
        }
        JSONObject jsonObject = HttpRequestUtil.httpUrlConnetionGet(amapUrl, "key=" + amapKey, "ip=" + ip);
        if (null != jsonObject){
            if ("0".equals(String.valueOf(jsonObject.get("status")))){
                return "高德城市定位失败";
            }else {
                /**
                 * {
                 * 	"province": "四川省",
                 * 	"city": "自贡市",
                 * 	"adcode": "510300",
                 * 	"infocode": "10000",
                 * 	"rectangle": "104.7035158,29.29494196;104.8472285,29.39984268",
                 * 	"status": "1",
                 * 	"info": "OK"
                 * }
                 */
                return String.valueOf(jsonObject.get("province")) + String.valueOf(jsonObject.get("自贡市"));
            }
        }else {
            return "获取失败";
        }
    }

    public String getCityByIp(HttpServletRequest httpRequest){
        // 获取request的公网ip
        String ip = HttpRequestUtil.getIpAddress(httpRequest);
        if (StringUtils.isEmpty(ip)){
            return "获取失败";
        }else {
            return this.getCityByIp(ip);
        }

    }


}

