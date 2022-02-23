package ac.cn.saya.laboratory.persistent.business.service;

import ac.cn.saya.laboratory.entity.NotesEntity;
import ac.cn.saya.laboratory.exception.MyException;
import ac.cn.saya.laboratory.persistent.business.dao.BusinessBatchDAO;
import ac.cn.saya.laboratory.persistent.business.dao.NotesDAO;
import ac.cn.saya.laboratory.tools.CurrentLineInfo;
import ac.cn.saya.laboratory.tools.HtmlUtils;
import ac.cn.saya.laboratory.tools.ResultEnum;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Title: NotesService
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2019/1/19 18:52
 * @Description: 笔记接口实现类
 */
@Service("notesService")
@Transactional(value = "primaryTransactionManager", readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = MyException.class)
public class NotesService {

    @Resource
    private NotesDAO notesDAO;

    @Resource
    @Qualifier("businessBatchDAO")
    private BusinessBatchDAO batchDAO;

    /**
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
            CurrentLineInfo.printCurrentLineInfo("创建笔记异常", e, NotesService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
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
            CurrentLineInfo.printCurrentLineInfo("编辑修改笔记异常", e, NotesService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
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
            CurrentLineInfo.printCurrentLineInfo("删除笔记异常", e, NotesService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 查询一条笔记
     * @参数 [entity]
     * @返回值 ac.cn.saya.datacenter.entity.NotesEntity
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/12
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    public NotesEntity getOneNotes(NotesEntity entity) {
        try {
            return notesDAO.getOneNotes(entity);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("查询笔记异常", e, NotesService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 获取分页后的笔记
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    public List<NotesEntity> getNotesPage(NotesEntity entity) {
        try {
            List<NotesEntity> list = notesDAO.getNotesPage(entity);
            if (CollectionUtils.isEmpty(list)) {
                return list;
            }
            // 过滤文本
            list = list.stream().map(item -> {
                item.setContent(HtmlUtils.SplitHtmlText(item.getContent(), 150));
                return item;
            }).collect(Collectors.toList());
            return list;
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("获取分页后的笔记发生异常", e, NotesService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 获取笔记总数
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    public Long getNotesCount(NotesEntity entity) {
        try {
            return notesDAO.getNotesCount(entity);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("获取笔记总数时发生异常", e, NotesService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 查询指定id附近的笔记
     * @参数 [notesId]
     * @返回值 java.util.Map<java.lang.String, java.lang.String>
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-04-02
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    public Map<String, String> getNotesPreAndNext(Integer notesId) {
        try {
            return batchDAO.getNewsNotesPreAndNext(2, notesId);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("获取上下笔记时发生异常", e, NotesService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }
}
