package ac.cn.saya.laboratory.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Title: BackupLogEntity
 * @ProjectName laboratory
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2019-02-27 21:53
 * @Description:
 * 数据库备份日志记录
 */
@NoArgsConstructor
@Getter
@Setter
public class BackupLogEntity extends BaseEntity{

    private static final long serialVersionUID = 2130871056548519611L;
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
    private String createtime;

    /**
     * 保留备份多少月
     */
    private Integer saveMonth;
}
