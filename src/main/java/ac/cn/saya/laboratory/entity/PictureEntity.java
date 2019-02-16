package ac.cn.saya.laboratory.entity;

/**
 * @Title: PictureEntity
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2019/1/9 21:58
 * @Description:
 * 图片实体类
 */

public class PictureEntity extends BaseEntity{

    /**
     * 图片id
     */
    private Integer id;

    /**
     * 图片类别（1:相册、壁纸，2:对外动态、笔记图片）
     */
    private Integer type;

    /**
     * 文件名
     */
    private String filename;

    /**
     * 图片描述(当type为1时，必填)
     */
    private String descript;

    /**
     * 服务器存放文件路径
     */
    private String fileurl;

    /**
     * 浏览器访问路径
     */
    private String weburl;

    /**
     * 作者
     */
    private String source;

    /**
     * 最后一次操作日期
     */
    private String date;


    public PictureEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
