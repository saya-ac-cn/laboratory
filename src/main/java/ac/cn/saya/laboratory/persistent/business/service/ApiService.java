package ac.cn.saya.laboratory.persistent.business.service;

import ac.cn.saya.laboratory.entity.ApiEntity;
import ac.cn.saya.laboratory.exception.MyException;
import ac.cn.saya.laboratory.persistent.business.dao.ApiDAO;
import ac.cn.saya.laboratory.tools.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Title: NotesService
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2019/1/19 18:52
 * @Description: 对外接口实现类
 */
@Service("apiService")
@Transactional(value = "primaryTransactionManager",readOnly = false,propagation= Propagation.REQUIRED, isolation= Isolation.REPEATABLE_READ, rollbackFor=MyException.class)
public class ApiService {

    private static Logger logger = LoggerFactory.getLogger(ApiService.class);

    @Resource
    private ApiDAO apiDAO;

    /**
     * @描述 添加接口
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    public Result<Object> insertApi(ApiEntity entity) {
        try {
            // 校验用户输入的参数
            if (entity == null) {
                // 缺少参数
                return ResultUtil.error(ResultEnum.NOT_PARAMETER);
            }
            if (apiDAO.insertApi(entity)>0){
                return ResultUtil.success();
            }else {
                return ResultUtil.error(ResultEnum.DB_ERROR);
            }
        } catch (Exception e) {
            logger.error("添加接口异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 编辑接口
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    public Result<Object> editApi(ApiEntity entity) {
        try {
            // 校验用户输入的参数
            if (entity == null) {
                // 缺少参数
                return ResultUtil.error(ResultEnum.NOT_PARAMETER);
            }
            if (apiDAO.updateApi(entity) > 0) {
                return ResultUtil.success();
            } else {
                return ResultUtil.error(ResultEnum.DB_ERROR);
            }
        } catch (Exception e) {
            logger.error("编辑接口异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 删除接口
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    public Result<Object> deleteApi(ApiEntity entity) {
        try {
            // 校验用户输入的参数
            if (entity == null) {
                // 缺少参数
                return ResultUtil.error(ResultEnum.NOT_PARAMETER);
            }
            if (apiDAO.deleteApi(entity) > 0) {
                return ResultUtil.success();
            } else {
                return ResultUtil.error(ResultEnum.DB_ERROR);
            }
        } catch (Exception e) {
            logger.error("删除接口异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 查询一条接口信息
     * @参数 [entity]
     * @返回值 ac.cn.saya.datacenter.entity.ApiEntity
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/12
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    public ApiEntity getOneApi(ApiEntity entity) {
        try {
            return apiDAO.getOneApi(entity);
        } catch (Exception e) {
            logger.error("查询一条接口信息异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 获取分页后的接口
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    public List<ApiEntity> getApiPage(ApiEntity entity) {
        try {
            return apiDAO.getApiPage(entity);
        } catch (Exception e) {
            logger.error("获取分页后的接口发生异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 获取接口总数
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    public Long getApiCount(ApiEntity entity) {
        try {
            return apiDAO.getApiCount(entity);
        } catch (Exception e) {
            logger.error("获取接口总数时发生异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }
}
