package ac.cn.saya.laboratory.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * 账单（按天）
 * @Title: BillOfDayEntity
 * @ProjectName laboratory
 * @Description: TODO
 * @Author saya
 * @Date: 2020/10/18 22:30
 * @Description:
 */

@NoArgsConstructor
@Getter
@Setter
public class BillOfDayEntity extends BaseEntity {

    private static final long serialVersionUID = -1183336932192709824L;

    /**
     * 交易日
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
     * 子账单
     */
    private List<TransactionListEntity> transactionList;
}
