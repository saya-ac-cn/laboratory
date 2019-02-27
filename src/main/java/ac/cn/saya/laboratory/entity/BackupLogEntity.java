package ac.cn.saya.laboratory.entity;

/**
 * @Title: BackupLogEntity
 * @ProjectName laboratory
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2019-02-27 21:53
 * @Description:
 * 数据库备份日志记录
 */

public class BackupLogEntity extends BaseEntity{

    /**
     * 备份数据库存放路径
     */
    private String url;

    /**
     * 归档日期
     */
    private String archiveDate;

    /**
     * 备份执行时间
     */
    private String createTime;


    public BackupLogEntity() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getArchiveDate() {
        return archiveDate;
    }

    public void setArchiveDate(String archiveDate) {
        this.archiveDate = archiveDate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
