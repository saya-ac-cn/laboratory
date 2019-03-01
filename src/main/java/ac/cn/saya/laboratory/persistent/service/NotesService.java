package ac.cn.saya.laboratory.persistent.service;

import ac.cn.saya.laboratory.entity.NotesEntity;
import ac.cn.saya.laboratory.persistent.dao.NotesDAO;
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
 * @Title: NotesService
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2019/1/19 18:52
 * @Description:
 * 笔记接口实现类
 */
@Service("notesService")
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = false, rollbackFor=Exception.class)
public class NotesService {

    private static Logger logger = LoggerFactory.getLogger(NotesService.class);

    @Resource
    @Qualifier("notesDAO")
    private NotesDAO notesDAO;

    /**
     * @param entity
     * @描述 添加笔记
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    public Integer insertNotes(NotesEntity entity) {
        Integer flog = null;
        try
        {
            flog = notesDAO.insertNotes(entity);
        }catch (Exception e) {
            flog = ResultEnum.UNKONW_ERROR.getCode();
            logger.error("创建笔记异常："+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return flog;
    }

    /**
     * @param entity
     * @描述 编辑修改笔记
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    public Integer editNotes(NotesEntity entity) {
        Integer flog = null;
        try
        {
            flog = notesDAO.updateNotes(entity);
        }catch (Exception e) {
            flog = ResultEnum.UNKONW_ERROR.getCode();
            logger.error("编辑修改笔记异常："+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return flog;
    }

    /**
     * @param entity
     * @描述 删除笔记
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    public Integer deleteNotes(NotesEntity entity) {
        Integer flog = null;
        try
        {
            flog = notesDAO.deleteNotes(entity);
        }catch (Exception e) {
            flog = ResultEnum.UNKONW_ERROR.getCode();
            logger.error("删除笔记异常："+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return flog;
    }

    /**
     * @param entity
     * @描述 查询一条笔记
     * @参数 [entity]
     * @返回值 ac.cn.saya.datacenter.entity.NotesEntity
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/12
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    public NotesEntity getOneNotes(NotesEntity entity) {
        NotesEntity result = null;
        try
        {
            result = notesDAO.getOneNotes(entity);
        }catch (Exception e) {
            result = null;
            logger.error("查询笔记异常："+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return result;
    }

    /**
     * @param entity
     * @描述 获取分页后的笔记
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    public List<NotesEntity> getNotesPage(NotesEntity entity) {
        List<NotesEntity> list = new ArrayList<>();
        try
        {
            list = notesDAO.getNotesPage(entity);
            if(list.size() <= 0) {
                list = null;
            }
        }catch (Exception e) {
            list = null;
            logger.error("获取分页后的笔记发生异常："+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return list;
    }

    /**
     * @param entity
     * @描述 获取笔记总数
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    public Long getNotesCount(NotesEntity entity) {
        Long total = null;
        try
        {
            total = notesDAO.getNotesCount(entity);
        }catch (Exception e) {
            total = Long.valueOf(ResultEnum.ERROP.getCode());
            logger.error("获取笔记总数时发生异常："+Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return total;
    }
}
