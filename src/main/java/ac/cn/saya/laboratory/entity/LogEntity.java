package ac.cn.saya.laboratory.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Title: LogEntity
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/9/22 22:13
 * @Description:
 * 日志实体类
 */
@NoArgsConstructor
@Getter
@Setter
public class LogEntity extends BaseEntity{


    private static final long serialVersionUID = 1669256731177965244L;
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
     * 日志类别(非数据库字段)
     */
    private LogTypeEntity logType;

    public LogEntity(String user, String type, String ip, String city, String date) {
        this.user = user;
        this.type = type;
        this.ip = ip;
        this.city = city;
        this.date = date;
    }
}
