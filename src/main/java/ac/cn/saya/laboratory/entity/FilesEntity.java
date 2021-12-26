package ac.cn.saya.laboratory.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Title: FilesEntity
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2019/1/15 22:43
 * @Description:
 * 文件实体类
 */
@NoArgsConstructor
@Getter
@Setter
public class FilesEntity extends BaseEntity{

    private static final long serialVersionUID = 3447502911280382424L;
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
     * 文件所属类别
     */
    private String filetype;

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
}
