package ac.cn.saya.laboratory.persistent.business.service;

import ac.cn.saya.laboratory.entity.NoteBookEntity;
import ac.cn.saya.laboratory.exception.MyException;
import ac.cn.saya.laboratory.persistent.business.dao.NoteBookDAO;
import ac.cn.saya.laboratory.tools.CurrentLineInfo;
import ac.cn.saya.laboratory.tools.ResultEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional(value = "primaryTransactionManager", readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = MyException.class)
public class NoteBookService {

    @Resource
    private NoteBookDAO noteBookDAO;

    /**
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
            CurrentLineInfo.printCurrentLineInfo("创建笔记簿异常", e, NoteBookService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
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
            CurrentLineInfo.printCurrentLineInfo("编辑修改笔记簿异常", e, NoteBookService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
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
            CurrentLineInfo.printCurrentLineInfo("删除笔记簿异常", e, NoteBookService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 查询一条笔记簿
     * @参数 [entity]
     * @返回值 ac.cn.saya.datacenter.entity.NoteBookEntity
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/12
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    public NoteBookEntity getOneNoteBook(NoteBookEntity entity) {
        try {
            return noteBookDAO.getOneNoteBook(entity);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("查询笔记簿异常", e, NoteBookService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 获取分页后的笔记簿
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    public List<NoteBookEntity> getNoteBookPage(NoteBookEntity entity) {
        try {
            List<NoteBookEntity> list = noteBookDAO.getNoteBookPage(entity);
            if (list.size() <= 0) {
                list = null;
            }
            return list;
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("获取分页后的笔记簿发生异常", e, NoteBookService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 获取笔记簿总数
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    public Long getNoteBookCount(NoteBookEntity entity) {
        try {
            return noteBookDAO.getNoteBookCount(entity);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("获取笔记簿总数时发生异常", e, NoteBookService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 获取笔记簿
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    public List<NoteBookEntity> getNoteBook(NoteBookEntity entity) {
        try {
            List<NoteBookEntity> list = noteBookDAO.getNoteBook(entity);
            if (list.size() <= 0) {
                list = null;
            }
            return list;
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("获取笔记簿发生异常", e, NoteBookService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

}
