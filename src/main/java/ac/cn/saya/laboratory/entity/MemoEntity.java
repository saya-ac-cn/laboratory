package ac.cn.saya.laboratory.entity;

/**
 * 便笺、便利贴
 * @Title: MemoEntity
 * @ProjectName laboratory
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2019-09-18 23:03
 * @Description:
 */

public class MemoEntity extends BaseEntity{

    /**
     * 便笺编号
     */
    private Integer id;

    /**
     * 创建者
     */
    private String source;

    /**
     * 便笺内容
     */
    private String content;

    /**
     * 创建时间
     */
    private String createtime;

    /**
     * 修改时间
     */
    private String updatetime;

    public MemoEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
