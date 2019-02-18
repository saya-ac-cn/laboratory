package ac.cn.saya.laboratory.persistent.service;

import ac.cn.saya.laboratory.entity.PlanEntity;
import ac.cn.saya.laboratory.persistent.dao.PlanDAO;
import ac.cn.saya.laboratory.tools.CurrentLineInfo;
import ac.cn.saya.laboratory.tools.Log4jUtils;
import ac.cn.saya.laboratory.tools.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: PlanService
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2019/1/21 23:13
 * @Description:
 * 行程计划对外服务接口
 */

@Service("planService")
@Transactional(propagation= Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED,rollbackFor=Exception.class,readOnly = false)
public class PlanService {

    private static Logger logger = LoggerFactory.getLogger(PlanService.class);

    @Resource
    @Qualifier("planDAO")
    private PlanDAO planDAO;

    /**
     * @param entity
     * @描述 发布计划安排
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    public Integer insertPlan(PlanEntity entity) {
        Integer flog = null;
        try
        {
            List<PlanEntity> list = planDAO.getPlanList(entity);
            if(list.size() <= 0){
                flog = planDAO.insertPlan(entity);
            }else{
                // 该天的计划已经存在
                flog = -2;
            }
        }catch (Exception e) {
            flog = ResultEnum.UNKONW_ERROR.getCode();
            logger.error("发布计划安排："+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return flog;
    }

    /**
     * @param entity
     * @描述 编辑修改计划安排
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    public Integer editPlan(PlanEntity entity) {
        Integer flog = null;
        try
        {
            PlanEntity query = new PlanEntity();
            // 判断用户的计划日期是否发生变更
            query.setId(entity.getId());
            PlanEntity oldEntity = planDAO.getOnePlan(query);
            if(oldEntity == null){
                // 未找到原值，不允许修改
                flog = -1;
            }else{
                if(oldEntity.getPlandate().trim().equals(entity.getPlandate().trim())){
                    // 用户未修改日期
                    flog = planDAO.updatePlan(entity);
                }else{
                    // 用户已经修改日期
                    // 必须清除上一步的查询条件
                    query.setId(null);
                    query.setPlandate(entity.getPlandate());
                    List<PlanEntity> list = planDAO.getPlanList(entity);
                    if(list.size() > 0){
                        // 该天已存在计划
                        flog = -2;
                    }else{
                        flog = planDAO.updatePlan(entity);
                    }
                }
            }
        }catch (Exception e) {
            flog = ResultEnum.UNKONW_ERROR.getCode();
            logger.error("编辑修改计划安排异常："+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return flog;
    }

    /**
     * @param entity
     * @描述 删除计划安排
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    public Integer deletePlan(PlanEntity entity) {
        Integer flog = null;
        try
        {
            flog = planDAO.deletePlan(entity);
        }catch (Exception e) {
            flog = ResultEnum.UNKONW_ERROR.getCode();
            logger.error("删除计划安排异常："+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return flog;
    }

    /**
     * @param entity
     * @描述 查询一条计划安排
     * @参数 [entity]
     * @返回值 ac.cn.saya.datacenter.entity.PlanEntity
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/12
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    public PlanEntity getOnePlan(PlanEntity entity) {
        PlanEntity result = null;
        try
        {
            result = planDAO.getOnePlan(entity);
        }catch (Exception e) {
            result = null;
            logger.error("查询一条计划安排异常："+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return result;
    }

    /**
     * @param entity
     * @描述 获取计划安排列表
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    public List<PlanEntity> getPlanList(PlanEntity entity) {
        List<PlanEntity> list = new ArrayList<>();
        try
        {
            list = planDAO.getPlanList(entity);
            if(list.size() <= 0) {
                list = null;
            }
        }catch (Exception e) {
            list = null;
            logger.error("获取计划安排列表异常："+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return list;
    }
}
