package ac.cn.saya.laboratory.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Title: LogTypeEntity
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/10/14 20:16
 * @Description:
 */
@NoArgsConstructor
@Getter
@Setter
public class LogTypeEntity extends BaseEntity{

    private static final long serialVersionUID = -2877783115199424724L;
    /**
     * 操作码
     */
    private String type;
    /**
     * 操作描述
     */
    private String describe;
}
