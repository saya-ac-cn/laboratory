package ac.cn.saya.laboratory.entity;

/**
 * @Title: LogTypeEntity
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/10/14 20:16
 * @Description:
 */

public class LogTypeEntity extends BaseEntity{

    /**
     * 操作码
     */
    private String type;
    /**
     * 操作描述
     */
    private String describe;

    public LogTypeEntity() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
