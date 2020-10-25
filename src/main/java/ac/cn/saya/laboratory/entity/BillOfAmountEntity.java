package ac.cn.saya.laboratory.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 账单（按摘要）
 * @Title: BillOfAmountEntity
 * @ProjectName laboratory
 * @Description: TODO
 * @Author saya
 * @Date: 2020/10/19 22:38
 * @Description:
 */

@NoArgsConstructor
@Getter
@Setter
public class BillOfAmountEntity extends BaseEntity {


    private static final long serialVersionUID = 6658557743945115675L;

    /**
     * 交易时间
     */
    private String tradeDate;

    /**
     * 所属用户
     */
    private String source;

    /**
     * 收入
     */
    private BigDecimal deposited;

    /**
     * 支出
     */
    private BigDecimal expenditure;

    /**
     * 交易金额
     */
    private BigDecimal currencyNumber;

    /**
     * 关联的摘要
     */
    private TransactionAmountEntity amountEntity;
}
