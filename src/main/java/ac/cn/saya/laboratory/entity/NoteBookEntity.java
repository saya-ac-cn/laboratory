package ac.cn.saya.laboratory.entity;

/**
 * @Title: NoteBookEntity
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2019/1/19 17:08
 * @Description:
 * 笔记簿名
 */

public class NoteBookEntity extends BaseEntity{

    /**
     * 笔记簿编号
     */
    private Integer id;

    /**
     * 笔记簿名
     */
    private String  name;

    /**
     * 发布者
     */
    private String source;

    /**
     * 是否公开：1、公开；2、屏蔽
     */
    private Integer status;

    /**
     * 笔记簿描述
     */
    private String descript;

    /**
     * 本笔记簿下笔记总数
     */
    private Integer notesCount;

    public NoteBookEntity() {
    }

    public NoteBookEntity(String name,String source,Integer status) {
        this.name = name;
        this.source = source;
        this.status = status;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public Integer getNotesCount() {
        return notesCount;
    }

    public void setNotesCount(Integer notesCount) {
        this.notesCount = notesCount;
    }
}
