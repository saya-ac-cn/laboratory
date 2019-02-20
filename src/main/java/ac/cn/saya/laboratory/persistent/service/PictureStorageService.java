package ac.cn.saya.laboratory.persistent.service;

import ac.cn.saya.laboratory.entity.PictureEntity;
import ac.cn.saya.laboratory.persistent.dao.PictureDAO;
import ac.cn.saya.laboratory.tools.CurrentLineInfo;
import ac.cn.saya.laboratory.tools.Log4jUtils;
import ac.cn.saya.laboratory.tools.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
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
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = false, rollbackFor=Exception.class)
public class PictureStorageService{


    private static Logger logger = LoggerFactory.getLogger(PictureStorageService.class);

    @Resource
    @Qualifier("pictureDAO")
    private PictureDAO pictureDAO;

    /**
     * @描述 图片上传服务（Base64）
     * @参数  [entity]
     * @返回值  java.lang.Integer
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/10
     * @修改人和其它信息
     */
    public Integer uploadPictureBase64(PictureEntity entity){
        Integer flog = null;
        try
        {
            flog = pictureDAO.insertPictuBase64(entity);
        }catch (Exception e) {
            flog = ResultEnum.UNKONW_ERROR.getCode();
            logger.error("写入到图片表异常："+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return flog;
    }

    /**
     * @描述 删除base64类型的图片
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/12
     * @修改人和其它信息
     */
    public Integer deletePictuBase64(PictureEntity entity){
        Integer flog = null;
        try
        {
            flog = pictureDAO.deletePictuBase64(entity);
        }catch (Exception e) {
            flog = ResultEnum.UNKONW_ERROR.getCode();
            logger.error("删除图片表异常："+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return flog;
    }

    /**
     * @描述 查询分页后的图片
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/12
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    public List<PictureEntity> getPictuBase64Page(PictureEntity entity){
        List<PictureEntity> list = new ArrayList<>();
        try
        {
            list = pictureDAO.getPictuBase64Page(entity);
            if(list.size() <= 0) {
                list = null;
            }
        }catch (Exception e) {
            list = null;
            logger.error("查询分页后的图片发生异常："+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return list;
    }

    /**
     * @描述 查询图片的总数
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/12
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    public Long getPictuBase64Count(PictureEntity entity){
        Long total = null;
        try
        {
            total = pictureDAO.getPictuBase64Count(entity);
        }catch (Exception e) {
            total = Long.valueOf(ResultEnum.ERROP.getCode());
            logger.error("获取动态总数时发生异常："+Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return total;
    }

    /**
     * @描述 查询一张图片
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/12
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    public PictureEntity getOnePictuBase64(PictureEntity entity){
        PictureEntity result = null;
        try
        {
            result = pictureDAO.getOnePictuBase64(entity);
        }catch (Exception e) {
            result = null;
            logger.error("查询一张图片异常："+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return result;
    }

}
