package ac.cn.saya.laboratory.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Title: PictureEntity
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2019/1/9 21:58
 * @Description:
 * 图片实体类
 */
@NoArgsConstructor
@Getter
@Setter
public class PictureEntity extends BaseEntity{

    private static final long serialVersionUID = 1459541416903054748L;
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
}
