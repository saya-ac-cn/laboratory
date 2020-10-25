package ac.cn.saya.laboratory.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Title: TransactionTypeEntity
 * @ProjectName MyDubbo
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/8/5 21:28
 * @Description: 交易类别表模型
 */
@NoArgsConstructor
@Getter
@Setter
public class TransactionTypeEntity extends BaseEntity{

    private static final long serialVersionUID = 4596063478097121895L;
    /**
     * 类别号
     */
    private Integer id;
    /**
     * 类别描述
     */
    private String transactionType;

    @Override
    public String toString() {
        return "交易编号：" + id + ", 类别描述：'" + transactionType;
    }
}
