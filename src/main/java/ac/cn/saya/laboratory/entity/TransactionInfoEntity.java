package ac.cn.saya.laboratory.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @Title: TransactionInfoEntity
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/10/27 22:32
 * @Description:
 * 交易明细
 */
@NoArgsConstructor
@Getter
@Setter
public class TransactionInfoEntity extends BaseEntity{

    private static final long serialVersionUID = -4390626752357452125L;
    /**
     * '明细编号',
     */
    private Integer id;

    /**
     * '流水号',
     */
    private Integer tradeId;

    /**
     * '存入/支出标志位(1:入，2:出)',
     */
    private Integer flog;

    /**
     * '交易额',
     */
    private BigDecimal currencyNumber;

    /**
     * 交易明细
     */
    private String currencyDetails;

    private TransactionListEntity transactionListEntity;
}