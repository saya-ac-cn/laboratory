package ac.cn.saya.laboratory.entity;

/**
 * @Title: TransactionTypeEntity
 * @ProjectName MyDubbo
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/8/5 21:28
 * @Description:交易类别表模型
 */

public class TransactionTypeEntity extends BaseEntity{

    /**
     * 类别号
     */
    private Integer id;
    /**
     * 类别描述
     */
    private String transactionType;

    public TransactionTypeEntity() {
    }

    public TransactionTypeEntity(Integer id, String transactionType) {
        this.id = id;
        this.transactionType = transactionType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return "交易编号：" + id + ", 类别描述：'" + transactionType;
    }
}
