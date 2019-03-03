package ac.cn.saya.laboratory.persistent.dao;


import ac.cn.saya.laboratory.entity.PlanEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Title: PlanDAO
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2019/1/10 23:06
 * @Description:
 * 行程计划DAO
 */

@Mapper
@Repository("planDAO")
public interface PlanDAO {

    /**
     * @描述 创建安排
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/11
     * @修改人和其它信息
     */
    public Integer insertPlan(PlanEntity entity);

    /**
     * @描述 修改安排
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/11
     * @修改人和其它信息
     */
    public Integer updatePlan(PlanEntity entity);

    /**
     * @描述 删除安排
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/11
     * @修改人和其它信息
     */
    public Integer deletePlan(PlanEntity entity);

    /**
     * @描述 查询一条安排
     * @参数  
     * @返回值  
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/12
     * @修改人和其它信息
     */
    public PlanEntity getOnePlan(PlanEntity entity);

    /**
     * @描述 获取指定条件安排
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/11
     * @修改人和其它信息
     */
    public List<PlanEntity> getPlanList(PlanEntity entity);

    /**
     * @描述 统计计划总数
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019-03-03
     * @修改人和其它信息
     */
    public Long getPlanCount(PlanEntity entity);


}
