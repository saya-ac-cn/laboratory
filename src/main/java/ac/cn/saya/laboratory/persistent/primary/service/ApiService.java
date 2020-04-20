package ac.cn.saya.laboratory.persistent.primary.service;

import ac.cn.saya.laboratory.entity.ApiEntity;
import ac.cn.saya.laboratory.exception.MyException;
import ac.cn.saya.laboratory.persistent.primary.dao.ApiDAO;
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
 * @Title: NotesService
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2019/1/19 18:52
 * @Description: 对外接口实现类
 */
@Service("apiService")
public class ApiService {

    private static Logger logger = LoggerFactory.getLogger(ApiService.class);

    @Resource
    @Qualifier("notesDAO")
    private ApiDAO apiDAO;

    /**
     * @描述 添加接口
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    public Integer insertApi(ApiEntity entity) {
        try {
            return apiDAO.insertApi(entity);
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
    public Integer editApi(ApiEntity entity) {
        try {
            return apiDAO.updateApi(entity);
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
    public Integer deleteApi(ApiEntity entity) {
        try {
            return apiDAO.deleteApi(entity);
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
    public List<ApiEntity> getApiPage(ApiEntity entity) {
        List<ApiEntity> list = new ArrayList<>();
        try {
            list = apiDAO.getApiPage(entity);
            if (list.size() <= 0) {
                list = null;
            }
            return list;
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
