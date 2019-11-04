package ac.cn.saya.laboratory.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Title: PlanEntity
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2019/1/21 22:49
 * @Description:
 * 日程计划实体类
 */
@NoArgsConstructor
@Getter
@Setter
public class PlanEntity extends BaseEntity{

    private static final long serialVersionUID = 6740035219118171437L;
    /**
     * 计划编号
     */
    private Integer id;

    /**
     * 创建者
     */
    private String source;

    /**
     * 描述
     */
    private String describe;

    /**
     * 施行日期
     */
    private String plandate;

    /**
     * 创建时间
     */
    private String createtime;

    /**
     * 修改时间
     */
    private String updatetime;

    /**
     * 该条记录在本月的序号
     */
    private Integer number;
}
