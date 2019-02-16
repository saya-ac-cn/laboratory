package ac.cn.saya.laboratory.entity;

/**
 * @Title: GuestBookEntity
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2019/1/12 19:34
 * @Description:
 * 留言簿
 */

public class GuestBookEntity extends BaseEntity{

    /**
     * 留言编号
     */
    private Integer id;

    /**
     * 留言者姓名
     */
    private String name;

    /**
     * 留言者phone
     */
    private String phone;

    /**
     * 留言者邮箱
     */
    private String email;

    /**
     * 留言正文
     */
    private String content;

    /**
     * 回复者
     */
    private String source;

    /**
     * 回复内容
     */
    private String reply;

    /**
     * 留言状态
     */
    private Integer status;

    /**
     * 留言时间
     */
    private String createtime;

    /**
     * 回复时间
     */
    private String updatetime;

    public GuestBookEntity() {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
