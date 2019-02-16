package ac.cn.saya.laboratory.entity;

/**
 * @Title: TransactionInfoEntity
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/10/27 22:32
 * @Description:
 * 交易明细
 */

public class TransactionInfoEntity extends BaseEntity{

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
    private Double currencyNumber;

    /**
     * 交易明细
     */
    private String currencyDetails;

    private TransactionListEntity transactionListEntity;

    public TransactionInfoEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTradeId() {
        return tradeId;
    }

    public void setTradeId(Integer tradeId) {
        this.tradeId = tradeId;
    }

    public Integer getFlog() {
        return flog;
    }

    public void setFlog(Integer flog) {
        this.flog = flog;
    }

    public Double getCurrencyNumber() {
        return currencyNumber;
    }

    public void setCurrencyNumber(Double currencyNumber) {
        this.currencyNumber = currencyNumber;
    }

    public String getCurrencyDetails() {
        return currencyDetails;
    }

    public void setCurrencyDetails(String currencyDetails) {
        this.currencyDetails = currencyDetails;
    }

    public TransactionListEntity getTransactionListEntity() {
        return transactionListEntity;
    }

    public void setTransactionListEntity(TransactionListEntity transactionListEntity) {
        this.transactionListEntity = transactionListEntity;
    }
}