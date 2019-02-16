package ac.cn.saya.laboratory.entity;

/**
 * @Title: LogEntity
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/9/22 22:13
 * @Description:
 * 日志实体类
 */

public class LogEntity extends BaseEntity{


    /**
     * 编号
     */
    private Integer id;
    /**
     * 用户
     */
    private String user;
    /**
     * 操作类别
     */
    private String type;
    /**
     * ip
     */
    private String ip;
    /**
     * 城市
     */
    private String city;
    /**
     * 日期
     */
    private String date;
    /**
     * 日志类别
     */
    private LogTypeEntity logType;

    public LogEntity(String user, String type, String ip, String city, String date) {
        this.user = user;
        this.type = type;
        this.ip = ip;
        this.city = city;
        this.date = date;
    }

    public LogEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public LogTypeEntity getLogType() {
        return logType;
    }

    public void setLogType(LogTypeEntity logType) {
        this.logType = logType;
    }

}
