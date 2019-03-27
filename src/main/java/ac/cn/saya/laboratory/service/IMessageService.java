package ac.cn.saya.laboratory.service;

import ac.cn.saya.laboratory.entity.GuestBookEntity;
import ac.cn.saya.laboratory.entity.NewsEntity;
import ac.cn.saya.laboratory.entity.NoteBookEntity;
import ac.cn.saya.laboratory.entity.NotesEntity;
import ac.cn.saya.laboratory.tools.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * @Title: IMessageService
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2019/1/11 22:56
 * @Description:
 */

public interface IMessageService {

    /**
     * @描述 发布动态
     * @参数
     * @返回值  
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/11
     * @修改人和其它信息
     */
    public Result<Object> publishNews(NewsEntity entity, HttpServletRequest request) throws Exception;

    /**
     * @描述 编辑动态
     * @参数  
     * @返回值  
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/11
     * @修改人和其它信息
     */
    public Result<Object> editNews(NewsEntity entity, HttpServletRequest request) throws Exception;

    /**
     * @描述 删除动态
     * @参数
     * @返回值  
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/11
     * @修改人和其它信息
     */
    public Result<Object> deleteNews(NewsEntity entity, HttpServletRequest request) throws Exception;

    /**
     * @描述 查询一条动态
     * @参数
     * @返回值  
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/12
     * @修改人和其它信息
     */
    public Result<Object> getOneNews(NewsEntity entity, HttpServletRequest request) throws Exception;

    /**
     * @描述 获取分页的动态
     * @参数
     * @返回值  
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/11
     * @修改人和其它信息
     */
    public Result<Object> getNewsList(NewsEntity entity, HttpServletRequest request) throws Exception;


    /**
     * @描述 审核修改
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/11
     * @修改人和其它信息
     */
    public Result<Object> updateGuestBook(GuestBookEntity entity, HttpServletRequest request) throws Exception;

    /**
     * @描述 查询一条留言
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/12
     * @修改人和其它信息
     */
    public Result<Object> queryOneGuestBook(GuestBookEntity entity) throws Exception;

    /**
     * @描述 获取分页的留言
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/11
     * @修改人和其它信息
     */
    public Result<Object> getGuestBookList(GuestBookEntity entity) throws Exception;

    /**
     * @描述 创建笔记簿
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/11
     * @修改人和其它信息
     */
    public Result<Object> createNoteBook(NoteBookEntity entity, HttpServletRequest request) throws Exception;

    /**
     * @描述 修改笔记簿
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/11
     * @修改人和其它信息
     */
    public Result<Object> updateNoteBook(NoteBookEntity entity, HttpServletRequest request) throws Exception;

    /**
     * @描述 删除笔记簿
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/11
     * @修改人和其它信息
     */
    public Result<Object> deleteNoteBook(NoteBookEntity entity, HttpServletRequest request) throws Exception;

    /**
     * @描述 查询一条笔记簿
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/12
     * @修改人和其它信息
     */
    public Result<Object> getOneNoteBook(NoteBookEntity entity, HttpServletRequest request) throws Exception;

    /**
     * @描述 获取分页的笔记簿
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/11
     * @修改人和其它信息
     */
    public Result<Object> getNoteBookList(NoteBookEntity entity, HttpServletRequest request) throws Exception;

    /**
     * @描述 获取笔记簿
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/11
     * @修改人和其它信息
     */
    public Result<Object> getNoteBook(NoteBookEntity entity, HttpServletRequest request) throws Exception;

    /**
     * @描述 创建笔记
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/11
     * @修改人和其它信息
     */
    public Result<Object> createNotes(NotesEntity entity, HttpServletRequest request) throws Exception;

    /**
     * @描述 修改笔记
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/11
     * @修改人和其它信息
     */
    public Result<Object> updateNotes(NotesEntity entity, HttpServletRequest request) throws Exception;

    /**
     * @描述 删除笔记
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/11
     * @修改人和其它信息
     */
    public Result<Object> deleteNotes(NotesEntity entity, HttpServletRequest request) throws Exception;

    /**
     * @描述 查询一条笔记
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/12
     * @修改人和其它信息
     */
    public Result<Object> getOneNotes(NotesEntity entity, HttpServletRequest request) throws Exception;

    /**
     * @描述 获取分页的笔记
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/11
     * @修改人和其它信息
     */
    public Result<Object> getNotesList(NotesEntity entity, HttpServletRequest request) throws Exception;

}
