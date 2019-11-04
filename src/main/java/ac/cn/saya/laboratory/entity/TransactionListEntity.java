package ac.cn.saya.laboratory.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @Title: TransactionListEntity
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/10/27 22:36
 * @Description:
 * 交易表
 */
@NoArgsConstructor
@Getter
@Setter
public class TransactionListEntity extends BaseEntity {

    private static final long serialVersionUID = -2361499569268952309L;
    /**
     * 流水编号
     */
    private Integer tradeId;
    /**
     * 存入
     */
    private Double deposited;
    /**
     * 所属用户
     */
    private String source;
    /**
     * 支出
     */
    private Double expenditure;
    /**
     * 交易日
     */
    private String tradeDate;
    /**
     * 交易类别
     */
    private Integer tradeType;
    /**
     * 交易金额
     */
    private Double currencyNumber;
    /**
     * 摘要
     */
    private String transactionAmount;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 修改时间
     */
    private String updateTime;
    /**
     * 交易类别
     */
    private TransactionTypeEntity tradeTypeEntity;
    /**
     * 明细表
     */
    private List<TransactionInfoEntity> infoList;

    public TransactionListEntity(String tradeDate, Double deposited, Double expenditure, Double currencyNumber) {
        this.tradeDate = tradeDate;
        this.deposited = deposited;
        this.expenditure = expenditure;
        this.currencyNumber = currencyNumber;
    }


}
