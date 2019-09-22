package ac.cn.saya.laboratory.persistent.service;

import ac.cn.saya.laboratory.entity.PlanEntity;
import ac.cn.saya.laboratory.exception.MyException;
import ac.cn.saya.laboratory.persistent.dao.PlanDAO;
import ac.cn.saya.laboratory.tools.CurrentLineInfo;
import ac.cn.saya.laboratory.tools.Log4jUtils;
import ac.cn.saya.laboratory.tools.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: PlanService
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2019/1/21 23:13
 * @Description: 行程计划对外服务接口
 */

@Service("planService")
public class PlanService {

    private static Logger logger = LoggerFactory.getLogger(PlanService.class);

    @Resource
    @Qualifier("planDAO")
    private PlanDAO planDAO;

    /**
     * @描述 发布计划安排
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    public Integer insertPlan(PlanEntity entity) {
        Integer flog = null;
        try {
            List<PlanEntity> list = planDAO.getPlanList(entity);
            if (list.size() <= 0) {
                flog = planDAO.insertPlan(entity);
            } else {
                // 该天的计划已经存在
                flog = -2;
            }
            return flog;
        } catch (Exception e) {
            logger.error("发布计划安排异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
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
        try {
            PlanEntity query = new PlanEntity();
            // 判断用户的计划日期是否发生变更
            query.setId(entity.getId());
            PlanEntity oldEntity = planDAO.getOnePlan(query);
            if (oldEntity == null) {
                // 未找到原值，不允许修改
                flog = -1;
            } else {
                if (oldEntity.getPlandate().trim().equals(entity.getPlandate().trim())) {
                    // 用户未修改日期
                    flog = planDAO.updatePlan(entity);
                } else {
                    // 用户已经修改日期
                    // 必须清除上一步的查询条件
                    query.setId(null);
                    query.setPlandate(entity.getPlandate());
                    List<PlanEntity> list = planDAO.getPlanList(entity);
                    if (list.size() > 0) {
                        // 该天已存在计划
                        flog = -2;
                    } else {
                        flog = planDAO.updatePlan(entity);
                    }
                }
            }
            return flog;
        } catch (Exception e) {
            logger.error("编辑修改计划安排异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
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
        try {
            return planDAO.deletePlan(entity);
        } catch (Exception e) {
            logger.error("删除计划安排异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
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
    public PlanEntity getOnePlan(PlanEntity entity) {
        try {
            return planDAO.getOnePlan(entity);
        } catch (Exception e) {
            logger.error("查询一条计划安排异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
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
    public List<PlanEntity> getPlanList(PlanEntity entity) {
        try {
            List<PlanEntity> list = planDAO.getPlanList(entity);
            if (list.size() <= 0) {
                list = null;
            }
            return list;
        } catch (Exception e) {
            logger.error("获取计划安排列表异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 统计计划总数
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-03-03
     * @修改人和其它信息
     */
    public Long getPlanCount(PlanEntity entity) {
        try {
            return planDAO.getPlanCount(entity);
        } catch (Exception e) {
            logger.error("统计计划总数时发生异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 获取当天的计划内容
     * @参数 []
     * @返回值 java.util.List<ac.cn.saya.laboratory.entity.PlanEntity>
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-06-03
     * @修改人和其它信息
     */
    public List<PlanEntity> getTodayPlanList() {
        List<PlanEntity> list = new ArrayList<>();
        try {
            list = planDAO.getTodayPlanList();
            if (list.size() <= 0) {
                list = null;
            }
            return list;
        } catch (Exception e) {
            logger.error("获取当天的计划内容异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

}
