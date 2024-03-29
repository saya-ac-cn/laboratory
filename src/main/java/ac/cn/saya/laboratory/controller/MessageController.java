package ac.cn.saya.laboratory.controller;

import ac.cn.saya.laboratory.entity.*;
import ac.cn.saya.laboratory.service.impl.MessageServiceImpl;
import ac.cn.saya.laboratory.tools.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Title: MessageController
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2019/1/11 23:16
 * @Description: 消息服务接口
 */

@RestController
@RequestMapping(value = "/backend/api/message")
public class MessageController {

    @Autowired
    @Qualifier("messageServiceImpl")
    private MessageServiceImpl messageServiceImpl;

    /**
     * @描述 发布动态
     * @参数 [entity, request]
     * @返回值 ac.cn.saya.datacenter.tools.Result<java.lang.Object>
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @PostMapping(value = "news/publish")
    public Result<Object> publishNews(@RequestBody NewsEntity entity, HttpServletRequest request) throws Exception {
        return messageServiceImpl.publishNews(entity, request);
    }

    /**
     * @描述 编辑动态
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @PutMapping(value = "news/edit")
    public Result<Object> editNews(@RequestBody NewsEntity entity, HttpServletRequest request) throws Exception {
        return messageServiceImpl.editNews(entity, request);
    }

    /**
     * @描述
     * @参数 删除动态
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @DeleteMapping(value = "news/delete")
    public Result<Object> deleteNews(NewsEntity entity, HttpServletRequest request) throws Exception {
        return messageServiceImpl.deleteNews(entity, request);
    }

    /**
     * @描述 获取分页的动态
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/12
     * @修改人和其它信息
     */
    @GetMapping(value = "news")
    public Result<Object> getNewsList(NewsEntity entity, HttpServletRequest request) throws Exception {
        return messageServiceImpl.getNewsList(entity, request);
    }

    /**
     * @描述 查询一条动态
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/12
     * @修改人和其它信息
     */
    @GetMapping(value = "news/show")
    public Result<Object> getOneNews(NewsEntity entity, HttpServletRequest request) throws Exception {
        return messageServiceImpl.getOneNews(entity, request);
    }

    /**
     * @描述 创建笔记簿
     * @参数 [entity, request]
     * @返回值 ac.cn.saya.datacenter.tools.Result<java.lang.Object>
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @PostMapping(value = "notebook/create")
    public Result<Object> createNoteBook(@RequestBody NoteBookEntity entity, HttpServletRequest request) throws Exception {
        return messageServiceImpl.createNoteBook(entity, request);
    }

    /**
     * @描述 编辑笔记簿
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @PutMapping(value = "notebook/edit")
    public Result<Object> editNoteBook(@RequestBody NoteBookEntity entity, HttpServletRequest request) throws Exception {
        return messageServiceImpl.updateNoteBook(entity, request);
    }

    /**
     * @描述
     * @参数 删除笔记簿
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @DeleteMapping(value = "notebook/delete")
    public Result<Object> deleteNoteBook(NoteBookEntity entity, HttpServletRequest request) throws Exception {
        return messageServiceImpl.deleteNoteBook(entity, request);
    }

    /**
     * @描述 获取分页的笔记簿
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/12
     * @修改人和其它信息
     */
    @GetMapping(value = "notebook")
    public Result<Object> getNoteBookList(NoteBookEntity entity, HttpServletRequest request) throws Exception {
        return messageServiceImpl.getNoteBookList(entity, request);
    }

    /**
     * @描述 获取笔记簿
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/12
     * @修改人和其它信息
     */
    @GetMapping(value = "notebook/group")
    public Result<Object> getNoteBook(NoteBookEntity entity, HttpServletRequest request) throws Exception {
        return messageServiceImpl.getNoteBook(entity, request);
    }

    /**
     * @描述 查询一条笔记簿
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/12
     * @修改人和其它信息
     */
    @GetMapping(value = "notebook/show")
    public Result<Object> getOneNoteBook(NoteBookEntity entity, HttpServletRequest request) throws Exception {
        return messageServiceImpl.getOneNoteBook(entity, request);
    }

    /**
     * @描述 创建笔记
     * @参数 [entity, request]
     * @返回值 ac.cn.saya.datacenter.tools.Result<java.lang.Object>
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @PostMapping(value = "notes/create")
    public Result<Object> createNotes(@RequestBody NotesEntity entity, HttpServletRequest request) throws Exception {
        return messageServiceImpl.createNotes(entity, request);
    }

    /**
     * @描述 编辑笔记
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @PutMapping(value = "notes/edit")
    public Result<Object> editNoteBook(@RequestBody NotesEntity entity, HttpServletRequest request) throws Exception {
        return messageServiceImpl.updateNotes(entity, request);
    }

    /**
     * @描述
     * @参数 删除笔记
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @DeleteMapping(value = "notes/delete")
    public Result<Object> deleteNotes(NotesEntity entity, HttpServletRequest request) throws Exception {
        return messageServiceImpl.deleteNotes(entity, request);
    }

    /**
     * @描述 获取分页的笔记
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/12
     * @修改人和其它信息
     */
    @GetMapping(value = "notes")
    public Result<Object> getNotesList(NotesEntity entity, HttpServletRequest request) throws Exception {
        return messageServiceImpl.getNotesList(entity, request);
    }

    /**
     * @描述 查询一条笔记
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/12
     * @修改人和其它信息
     */
    @GetMapping(value = "notes/show")
    public Result<Object> getOneNotes(NotesEntity entity, HttpServletRequest request) throws Exception {
        return messageServiceImpl.getOneNotes(entity, request);
    }

    /**
     * @描述 创建便笺
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-09-21
     * @修改人和其它信息
     */
    @PostMapping(value = "memo/create")
    public Result<Object> createMemo(@RequestBody MemoEntity entity, HttpServletRequest request) throws Exception {
        return messageServiceImpl.createMemo(entity, request);
    }

    /**
     * @描述 编辑便笺
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @PutMapping(value = "memo/edit")
    public Result<Object> editMemo(@RequestBody MemoEntity entity, HttpServletRequest request) throws Exception {
        return messageServiceImpl.updateMemo(entity, request);
    }

    /**
     * @描述
     * @参数 删除便笺
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @DeleteMapping(value = "memo/delete")
    public Result<Object> deleteNoteBook(MemoEntity entity, HttpServletRequest request) throws Exception {
        return messageServiceImpl.deleteMemo(entity, request);
    }

    /**
     * @描述 获取分页的便笺
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/12
     * @修改人和其它信息
     */
    @GetMapping(value = "memo")
    public Result<Object> getNoteBookList(MemoEntity entity, HttpServletRequest request) throws Exception {
        return messageServiceImpl.getMemoList(entity, request);
    }

    /**
     * @描述 查询一条便笺
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/12
     * @修改人和其它信息
     */
    @GetMapping(value = "memo/show")
    public Result<Object> getOneMemo(MemoEntity entity, HttpServletRequest request) throws Exception {
        return messageServiceImpl.getOneMemo(entity, request);
    }

    /**
     * 统计动态发布
     *
     * @param queryMonth 所在月份的日期(2021-01-25格式)
     * @param request    当前用户会话信息
     * @return
     * @throws Exception
     */
    @GetMapping(value = "newsRate/{queryMonth}")
    public Result<Object> newsRate(@PathVariable("queryMonth") String queryMonth, HttpServletRequest request) throws Exception {
        return messageServiceImpl.newsRate(queryMonth, request);
    }

}
