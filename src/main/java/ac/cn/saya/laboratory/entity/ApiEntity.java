package ac.cn.saya.laboratory.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Title: ApiEntity
 * @ProjectName laboratory
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2019-02-27 21:56
 * @Description:
 * 对外接口开发权限管理表
 */
@NoArgsConstructor
@Getter
@Setter
public class ApiEntity extends BaseEntity{

    private static final long serialVersionUID = 8929396730382874154L;
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
}
