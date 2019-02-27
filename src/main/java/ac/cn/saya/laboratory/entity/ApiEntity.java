package ac.cn.saya.laboratory.entity;

/**
 * @Title: ApiEntity
 * @ProjectName laboratory
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2019-02-27 21:56
 * @Description:
 * 对外接口开发权限管理表
 */

public class ApiEntity extends BaseEntity{

    /**
     * 接口编号
     */
    private Integer id;

    /**
     * 接口名
     */
    private String name;

    /**
     * 接口状态（1：开启，2：关闭（缺省），）
     */
    private Integer status;

    /**
     * 接口描述
     */
    private String descript;

    /**
     * 接口创建
     */
    private String createtime;

    /**
     * 最后一次修改时间
     */
    private String updatetime;

    public ApiEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
}
