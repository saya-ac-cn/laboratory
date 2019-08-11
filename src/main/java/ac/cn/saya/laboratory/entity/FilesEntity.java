package ac.cn.saya.laboratory.entity;

/**
 * @Title: FilesEntity
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2019/1/15 22:43
 * @Description:
 * 文件实体类
 */

public class FilesEntity extends BaseEntity{

    /**
     * 文件编号
     */
    private Integer id;

    /**
     * 文件uid（前端uid）
     */
    private String uid;

    /**
     * 文件名
     */
    private String filename;

    /**
     * 文件存储路径
     */
    private String fileurl;

    /**
     * 上传者
     */
    private String source;

    /**
     * 文件状态，1为正常显示，2为不予显示
     */
    private String status;

    /**
     * 上传日期
     */
    private String date;

    public FilesEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
