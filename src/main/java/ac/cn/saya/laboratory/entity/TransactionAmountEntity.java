package ac.cn.saya.laboratory.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Title: TransactionAmountEntity
 * @ProjectName MyDubbo
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/8/5 21:28
 * @Description: 交易摘要表模型
 */
@NoArgsConstructor
@Getter
@Setter
public class TransactionAmountEntity extends BaseEntity{

    private static final long serialVersionUID = 4596063478097121896L;

    /**
     * 类别号
     */
    private Integer id;

    /**
     * 存入/支出标志位(1:入，2:出)
     */
    private Integer flog;

    /**
     * 摘要描述
     */
    private String tag;

    @Override
    public String toString() {
        return "交易编号：" + id + ", 摘要描述：'" + tag;
    }
}
