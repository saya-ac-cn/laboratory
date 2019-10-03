package ac.cn.saya.laboratory.entity;

/**
 * 用户的内存记录信息
 * @Title: UserMemory
 * @ProjectName laboratory
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2019-10-02 23:32
 * @Description:
 */

public class UserMemory {

    /**
     * 用户名
     */
    private String user;

    /**
     * ip
     */
    private String ip;

    /**
     * ip所在城市
     */
    private String city;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public UserMemory() {
    }
}
