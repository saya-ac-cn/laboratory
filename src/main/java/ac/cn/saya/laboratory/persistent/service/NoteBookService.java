package ac.cn.saya.laboratory.persistent.service;

import ac.cn.saya.laboratory.entity.NoteBookEntity;
import ac.cn.saya.laboratory.exception.MyException;
import ac.cn.saya.laboratory.persistent.dao.NoteBookDAO;
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
 * @Title: NoteBookService
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2019/1/19 18:43
 * @Description: 笔记簿接口实现类
 */
@Service("noteBookService")
public class NoteBookService {

    private static Logger logger = LoggerFactory.getLogger(NoteBookService.class);

    @Resource
    @Qualifier("noteBookDAO")
    private NoteBookDAO noteBookDAO;

    /**
     * @param entity
     * @描述 添加笔记簿
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    public Integer insertNoteBook(NoteBookEntity entity) {
        try {
            return noteBookDAO.insertNoteBook(entity);
        } catch (Exception e) {
            logger.error("创建笔记簿异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @param entity
     * @描述 编辑修改笔记簿
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    public Integer editNoteBook(NoteBookEntity entity) {
        try {
            return noteBookDAO.updateNoteBook(entity);
        } catch (Exception e) {
            logger.error("编辑修改笔记簿异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @param entity
     * @描述 删除笔记簿
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    public Integer deleteNoteBook(NoteBookEntity entity) {
        try {
            return noteBookDAO.deleteNoteBook(entity);
        } catch (Exception e) {
            logger.error("删除笔记簿异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @param entity
     * @描述 查询一条笔记簿
     * @参数 [entity]
     * @返回值 ac.cn.saya.datacenter.entity.NoteBookEntity
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/12
     * @修改人和其它信息
     */
    public NoteBookEntity getOneNoteBook(NoteBookEntity entity) {
        try {
            return noteBookDAO.getOneNoteBook(entity);
        } catch (Exception e) {
            logger.error("查询笔记簿异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @param entity
     * @描述 获取分页后的笔记簿
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    public List<NoteBookEntity> getNoteBookPage(NoteBookEntity entity) {
        List<NoteBookEntity> list = new ArrayList<>();
        try {
            list = noteBookDAO.getNoteBookPage(entity);
            if (list.size() <= 0) {
                list = null;
            }
            return list;
        } catch (Exception e) {
            logger.error("获取分页后的笔记簿发生异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @param entity
     * @描述 获取笔记簿总数
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    public Long getNoteBookCount(NoteBookEntity entity) {
        try {
            return noteBookDAO.getNoteBookCount(entity);
        } catch (Exception e) {
            logger.error("获取笔记簿总数时发生异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @param entity
     * @描述 获取笔记簿
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    public List<NoteBookEntity> getNoteBook(NoteBookEntity entity) {
        List<NoteBookEntity> list = new ArrayList<>();
        try {
            list = noteBookDAO.getNoteBook(entity);
            if (list.size() <= 0) {
                list = null;
            }
            return list;
        } catch (Exception e) {
            logger.error("获取笔记簿发生异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

}
