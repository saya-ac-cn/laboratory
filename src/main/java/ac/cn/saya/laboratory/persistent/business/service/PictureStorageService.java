package ac.cn.saya.laboratory.persistent.business.service;

import ac.cn.saya.laboratory.entity.PictureEntity;
import ac.cn.saya.laboratory.exception.MyException;
import ac.cn.saya.laboratory.persistent.business.dao.PictureDAO;
import ac.cn.saya.laboratory.tools.CurrentLineInfo;
import ac.cn.saya.laboratory.tools.Log4jUtils;
import ac.cn.saya.laboratory.tools.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: PictureStorageService
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2019/1/10 23:03
 * @Description:
 */

@Service("pictureStorageService")
@Transactional(value = "primaryTransactionManager",readOnly = false,propagation= Propagation.REQUIRED, isolation= Isolation.REPEATABLE_READ, rollbackFor=MyException.class)
public class PictureStorageService {


    private static Logger logger = LoggerFactory.getLogger(PictureStorageService.class);

    @Resource
    private PictureDAO pictureDAO;

    /**
     * @描述 图片上传服务（Base64）
     * @参数 [entity]
     * @返回值 java.lang.Integer
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/10
     * @修改人和其它信息
     */
    public Integer uploadPictureBase64(PictureEntity entity) {
        try {
            return pictureDAO.insertPictuBase64(entity);
        } catch (Exception e) {
            logger.error("写入到图片表异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 删除base64类型的图片
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/12
     * @修改人和其它信息
     */
    public Integer deletePictuBase64(PictureEntity entity) {
        try {
            return pictureDAO.deletePictuBase64(entity);
        } catch (Exception e) {
            logger.error("删除图片表异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 查询分页后的图片
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/12
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    public List<PictureEntity> getPictuBase64Page(PictureEntity entity) {
        List<PictureEntity> list = new ArrayList<>();
        try {
            list = pictureDAO.getPictuBase64Page(entity);
            if (list.size() <= 0) {
                list = null;
            }
            return list;
        } catch (Exception e) {
            logger.error("查询分页后的图片发生异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 查询图片的总数
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/12
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    public Long getPictuBase64Count(PictureEntity entity) {
        try {
            return pictureDAO.getPictuBase64Count(entity);
        } catch (Exception e) {
            logger.error("获取动态总数时发生异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 查询一张图片
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/12
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    public PictureEntity getOnePictuBase64(PictureEntity entity) {
        try {
            return pictureDAO.getOnePictuBase64(entity);
        } catch (Exception e) {
            logger.error("查询一张图片异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

}
