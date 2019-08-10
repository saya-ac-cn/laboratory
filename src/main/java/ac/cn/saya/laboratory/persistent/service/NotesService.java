package ac.cn.saya.laboratory.persistent.service;

import ac.cn.saya.laboratory.entity.NotesEntity;
import ac.cn.saya.laboratory.exception.MyException;
import ac.cn.saya.laboratory.persistent.dao.BatchDAO;
import ac.cn.saya.laboratory.persistent.dao.NotesDAO;
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
import java.util.Map;

/**
 * @Title: NotesService
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2019/1/19 18:52
 * @Description: 笔记接口实现类
 */
@Service("notesService")
public class NotesService {

    private static Logger logger = LoggerFactory.getLogger(NotesService.class);

    @Resource
    @Qualifier("notesDAO")
    private NotesDAO notesDAO;

    @Resource
    @Qualifier("batchDAO")
    private BatchDAO batchDAO;

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
        try {
            return notesDAO.insertNotes(entity);
        } catch (Exception e) {
            logger.error("创建笔记异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
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
        try {
            return notesDAO.updateNotes(entity);
        } catch (Exception e) {
            logger.error("编辑修改笔记异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
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
        try {
            return notesDAO.deleteNotes(entity);
        } catch (Exception e) {
            logger.error("删除笔记异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
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
    public NotesEntity getOneNotes(NotesEntity entity) {
        try {
            return notesDAO.getOneNotes(entity);
        } catch (Exception e) {
            logger.error("查询笔记异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
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
    public List<NotesEntity> getNotesPage(NotesEntity entity) {
        List<NotesEntity> list = new ArrayList<>();
        try {
            list = notesDAO.getNotesPage(entity);
            if (list.size() <= 0) {
                list = null;
            }
            return list;
        } catch (Exception e) {
            logger.error("获取分页后的笔记发生异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
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
    public Long getNotesCount(NotesEntity entity) {
        try {
            return notesDAO.getNotesCount(entity);
        } catch (Exception e) {
            logger.error("获取笔记总数时发生异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 查询指定id附近的笔记
     * @参数 [notesId]
     * @返回值 java.util.Map<java.lang.String   ,   java.lang.String>
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-04-02
     * @修改人和其它信息
     */
    public Map<String, String> getNotesPreAndNext(Integer notesId) {
        try {
            return batchDAO.getNewsNotesPreAndNext(2, notesId);
        } catch (Exception e) {
            logger.error("获取上下笔记时发生异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }
}
