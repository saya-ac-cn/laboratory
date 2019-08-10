package ac.cn.saya.laboratory.service.impl;

import ac.cn.saya.laboratory.entity.GuestBookEntity;
import ac.cn.saya.laboratory.entity.NewsEntity;
import ac.cn.saya.laboratory.entity.NoteBookEntity;
import ac.cn.saya.laboratory.entity.NotesEntity;
import ac.cn.saya.laboratory.exception.MyException;
import ac.cn.saya.laboratory.persistent.service.GuestBookService;
import ac.cn.saya.laboratory.persistent.service.NewsService;
import ac.cn.saya.laboratory.persistent.service.NoteBookService;
import ac.cn.saya.laboratory.persistent.service.NotesService;
import ac.cn.saya.laboratory.tools.Paging;
import ac.cn.saya.laboratory.tools.Result;
import ac.cn.saya.laboratory.tools.ResultEnum;
import ac.cn.saya.laboratory.tools.ResultUtil;
import ac.cn.saya.laboratory.service.IMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Title: MessageServiceImpl
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2019/1/11 23:00
 * @Description: 消息存储服务
 */
@Service("messageServiceImpl")
public class MessageServiceImpl implements IMessageService {

    private static Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Resource
    @Qualifier("recordService")//日志助手表
    private RecordService recordService;

    @Resource
    @Qualifier("newsService")
    private NewsService newsService;

    @Resource
    @Qualifier("guestBookService")
    private GuestBookService guestBookService;

    @Resource
    @Qualifier("noteBookService")
    private NoteBookService noteBookService;

    @Resource
    @Qualifier("notesService")
    private NotesService notesService;

    /**
     * @param entity
     * @param request
     * @描述 发布动态
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @Override
    public Result<Object> publishNews(NewsEntity entity, HttpServletRequest request) throws Exception {
        // 校验用户输入的参数
        if (entity == null) {
            // 缺少参数
            throw new MyException(ResultEnum.NOT_PARAMETER);
        }
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        String userSession = (String) request.getSession().getAttribute("user");
        entity.setSource(userSession);
        if (newsService.publishNews(entity) > 0) {
            /**
             * 记录日志
             */
            recordService.record("OX007", request);
            return ResultUtil.success();
        } else {
            throw new MyException(ResultEnum.ERROP);
        }
    }

    /**
     * @param entity
     * @param request
     * @描述 编辑动态
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @Override
    public Result<Object> editNews(NewsEntity entity, HttpServletRequest request) throws Exception {
        // 校验用户输入的参数
        if (entity == null) {
            // 缺少参数
            throw new MyException(ResultEnum.NOT_PARAMETER);
        }
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        String userSession = (String) request.getSession().getAttribute("user");
        entity.setSource(userSession);
        if (newsService.editNews(entity) > 0) {
            /**
             * 记录日志
             */
            recordService.record("OX008", request);
            return ResultUtil.success();
        } else {
            throw new MyException(ResultEnum.ERROP);
        }
    }

    /**
     * @param entity
     * @param request
     * @描述 删除动态
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @Override
    public Result<Object> deleteNews(NewsEntity entity, HttpServletRequest request) throws Exception {
        // 校验用户输入的参数
        if (entity == null) {
            // 缺少参数
            throw new MyException(ResultEnum.NOT_PARAMETER);
        }
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        String userSession = (String) request.getSession().getAttribute("user");
        entity.setSource(userSession);
        if (newsService.deleteNews(entity) > 0) {
            /**
             * 记录日志
             */
            recordService.record("OX009", request);
            return ResultUtil.success();
        } else {
            throw new MyException(ResultEnum.ERROP);
        }
    }

    /**
     * @描述 查询一条动态
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/12
     * @修改人和其它信息
     */
    @Override
    public Result<Object> getOneNews(NewsEntity entity, HttpServletRequest request) throws Exception {
        if (entity == null || entity.getId() == null) {
            // 缺少参数
            throw new MyException(ResultEnum.NOT_PARAMETER);
        }
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        String userSession = (String) request.getSession().getAttribute("user");
        entity.setSource(userSession);
        NewsEntity result = newsService.getOneNews(entity);
        if (result == null) {
            //未找到有效记录
            throw new MyException(ResultEnum.NOT_EXIST);
        } else {
            return ResultUtil.success(result);
        }
    }

    /**
     * @param entity
     * @param request
     * @描述 获取分页的动态
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @Override
    public Result<Object> getNewsList(NewsEntity entity, HttpServletRequest request) throws Exception {
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        String userSession = (String) request.getSession().getAttribute("user");
        entity.setSource(userSession);
        Paging paging = new Paging();
        if (entity.getNowPage() == null) {
            entity.setNowPage(1);
        }
        if (entity.getPageSize() == null) {
            entity.setPageSize(20);
        }
        //每页显示记录的数量
        paging.setPageSize(entity.getPageSize());
        //获取满足条件的总记录（不分页）
        Long pageSize = newsService.getNewsCount(entity);
        if (pageSize > 0) {
            //总记录数
            paging.setDateSum(pageSize);
            //计算总页数
            paging.setTotalPage();
            //设置当前的页码-并校验是否超出页码范围
            paging.setPageNow(entity.getNowPage());
            //设置行索引
            entity.setPage((paging.getPageNow() - 1) * paging.getPageSize(), paging.getPageSize());
            //获取满足条件的记录集合
            List<NewsEntity> list = newsService.getNewsPage(entity);
            paging.setGrid(list);
            return ResultUtil.success(paging);
        } else {
            //未找到有效记录
            throw new MyException(ResultEnum.NOT_EXIST);
        }
    }

    /**
     * @param entity
     * @param request
     * @描述 审核修改
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @Override
    public Result<Object> updateGuestBook(GuestBookEntity entity, HttpServletRequest request) throws Exception {
        // 校验用户输入的参数
        if (entity == null) {
            // 缺少参数
            throw new MyException(ResultEnum.NOT_PARAMETER);
        }
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        String userSession = (String) request.getSession().getAttribute("user");
        entity.setSource(userSession);
        if (guestBookService.updateGuestBook(entity) > 0) {
            /**
             * 记录日志
             */
            recordService.record("OX010", request);
            return ResultUtil.success();
        } else {
            throw new MyException(ResultEnum.ERROP);
        }
    }

    /**
     * @param entity
     * @描述 查询一条留言
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/12
     * @修改人和其它信息
     */
    @Override
    public Result<Object> queryOneGuestBook(GuestBookEntity entity) throws Exception {
        if (entity == null || entity.getId() == null) {
            // 缺少参数
            throw new MyException(ResultEnum.NOT_PARAMETER);
        }
        GuestBookEntity result = guestBookService.queryOneGuestBook(entity);
        if (result == null) {
            //未找到有效记录
            throw new MyException(ResultEnum.NOT_EXIST);
        } else {
            return ResultUtil.success(result);
        }
    }

    /**
     * @param entity
     * @描述 获取分页的留言
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @Override
    public Result<Object> getGuestBookList(GuestBookEntity entity) throws Exception {
        Paging paging = new Paging();
        if (entity.getNowPage() == null) {
            entity.setNowPage(1);
        }
        if (entity.getPageSize() == null) {
            entity.setPageSize(20);
        }
        //每页显示记录的数量
        paging.setPageSize(entity.getPageSize());
        //获取满足条件的总记录（不分页）
        Long pageSize = guestBookService.getGuestBookCount(entity);
        if (pageSize > 0) {
            //总记录数
            paging.setDateSum(pageSize);
            //计算总页数
            paging.setTotalPage();
            //设置当前的页码-并校验是否超出页码范围
            paging.setPageNow(entity.getNowPage());
            //设置行索引
            entity.setPage((paging.getPageNow() - 1) * paging.getPageSize(), paging.getPageSize());
            //获取满足条件的记录集合
            List<GuestBookEntity> list = guestBookService.getGuestBookPage(entity);
            paging.setGrid(list);
            return ResultUtil.success(paging);
        } else {
            //未找到有效记录
            throw new MyException(ResultEnum.NOT_EXIST);
        }
    }

    /**
     * @param entity
     * @param request
     * @描述 创建笔记簿
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @Override
    public Result<Object> createNoteBook(NoteBookEntity entity, HttpServletRequest request) throws Exception {
        // 校验用户输入的参数
        if (entity == null) {
            // 缺少参数
            throw new MyException(ResultEnum.NOT_PARAMETER);
        }
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        String userSession = (String) request.getSession().getAttribute("user");
        entity.setSource(userSession);
        if (noteBookService.insertNoteBook(entity) > 0) {
            /**
             * 记录日志
             */
            recordService.record("OX016", request);
            return ResultUtil.success();
        } else {
            throw new MyException(ResultEnum.ERROP);
        }
    }

    /**
     * @param entity
     * @param request
     * @描述 修改笔记簿
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @Override
    public Result<Object> updateNoteBook(NoteBookEntity entity, HttpServletRequest request) throws Exception {
        // 校验用户输入的参数
        if (entity == null) {
            // 缺少参数
            throw new MyException(ResultEnum.NOT_PARAMETER);
        }
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        String userSession = (String) request.getSession().getAttribute("user");
        entity.setSource(userSession);
        if (noteBookService.editNoteBook(entity) > 0) {
            /**
             * 记录日志
             */
            recordService.record("OX017", request);
            return ResultUtil.success();
        } else {
            throw new MyException(ResultEnum.ERROP);
        }
    }

    /**
     * @param entity
     * @param request
     * @描述 删除笔记簿
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @Override
    public Result<Object> deleteNoteBook(NoteBookEntity entity, HttpServletRequest request) throws Exception {
        // 校验用户输入的参数
        if (entity == null) {
            // 缺少参数
            throw new MyException(ResultEnum.NOT_PARAMETER);
        }
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        String userSession = (String) request.getSession().getAttribute("user");
        entity.setSource(userSession);
        if (noteBookService.deleteNoteBook(entity) > 0) {
            /**
             * 记录日志
             */
            recordService.record("OX018", request);
            return ResultUtil.success();
        } else {
            throw new MyException(ResultEnum.ERROP);
        }
    }

    /**
     * @param entity
     * @param request
     * @描述 查询一条笔记簿
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/12
     * @修改人和其它信息
     */
    @Override
    public Result<Object> getOneNoteBook(NoteBookEntity entity, HttpServletRequest request) throws Exception {
        if (entity == null || entity.getId() == null) {
            // 缺少参数
            throw new MyException(ResultEnum.NOT_PARAMETER);
        }
        NoteBookEntity result = noteBookService.getOneNoteBook(entity);
        if (result == null) {
            //未找到有效记录
            throw new MyException(ResultEnum.NOT_EXIST);
        } else {
            return ResultUtil.success(result);
        }
    }

    /**
     * @param entity
     * @param request
     * @描述 获取分页的笔记簿
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @Override
    public Result<Object> getNoteBookList(NoteBookEntity entity, HttpServletRequest request) throws Exception {
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        String userSession = (String) request.getSession().getAttribute("user");
        entity.setSource(userSession);
        Paging paging = new Paging();
        if (entity.getNowPage() == null) {
            entity.setNowPage(1);
        }
        if (entity.getPageSize() == null) {
            entity.setPageSize(20);
        }
        //每页显示记录的数量
        paging.setPageSize(entity.getPageSize());
        //获取满足条件的总记录（不分页）
        Long pageSize = noteBookService.getNoteBookCount(entity);
        if (pageSize > 0) {
            //总记录数
            paging.setDateSum(pageSize);
            //计算总页数
            paging.setTotalPage();
            //设置当前的页码-并校验是否超出页码范围
            paging.setPageNow(entity.getNowPage());
            //设置行索引
            entity.setPage((paging.getPageNow() - 1) * paging.getPageSize(), paging.getPageSize());
            //获取满足条件的记录集合
            List<NoteBookEntity> list = noteBookService.getNoteBookPage(entity);
            paging.setGrid(list);
            return ResultUtil.success(paging);
        } else {
            //未找到有效记录
            throw new MyException(ResultEnum.NOT_EXIST);
        }
    }

    /**
     * @param entity
     * @param request
     * @描述 获取笔记簿
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @Override
    public Result<Object> getNoteBook(NoteBookEntity entity, HttpServletRequest request) throws Exception {
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        String userSession = (String) request.getSession().getAttribute("user");
        entity.setSource(userSession);
        List<NoteBookEntity> list = noteBookService.getNoteBook(entity);
        if (list.size() > 0) {
            return ResultUtil.success(list);
        } else {
            //未找到有效记录
            throw new MyException(ResultEnum.NOT_EXIST);
        }
    }

    /**
     * @param entity
     * @param request
     * @描述 创建笔记
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @Override
    public Result<Object> createNotes(NotesEntity entity, HttpServletRequest request) throws Exception {
        // 校验用户输入的参数
        if (entity == null) {
            // 缺少参数
            throw new MyException(ResultEnum.NOT_PARAMETER);
        }
        if (notesService.insertNotes(entity) > 0) {
            /**
             * 记录日志
             */
            recordService.record("OX019", request);
            return ResultUtil.success();
        } else {
            throw new MyException(ResultEnum.ERROP);
        }
    }

    /**
     * @param entity
     * @param request
     * @描述 修改笔记
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @Override
    public Result<Object> updateNotes(NotesEntity entity, HttpServletRequest request) throws Exception {
        // 校验用户输入的参数
        if (entity == null) {
            // 缺少参数
            throw new MyException(ResultEnum.NOT_PARAMETER);
        }
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        String userSession = (String) request.getSession().getAttribute("user");
        NoteBookEntity bookEntity = new NoteBookEntity();
        bookEntity.setSource(userSession);
        entity.setNotebook(bookEntity);
        if (notesService.editNotes(entity) > 0) {
            /**
             * 记录日志
             */
            recordService.record("OX020", request);
            return ResultUtil.success();
        } else {
            throw new MyException(ResultEnum.ERROP);
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
    @Override
    public Result<Object> deleteNotes(NotesEntity entity, HttpServletRequest request) throws Exception {
        // 校验用户输入的参数
        if (entity == null) {
            // 缺少参数
            throw new MyException(ResultEnum.NOT_PARAMETER);
        }
        if (notesService.deleteNotes(entity) > 0) {
            /**
             * 记录日志
             */
            recordService.record("OX021", request);
            return ResultUtil.success();
        } else {
            throw new MyException(ResultEnum.ERROP);
        }
    }

    /**
     * @param entity
     * @param request
     * @描述 查询一条笔记
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/12
     * @修改人和其它信息
     */
    @Override
    public Result<Object> getOneNotes(NotesEntity entity, HttpServletRequest request) throws Exception {
        if (entity == null || entity.getId() == null) {
            // 缺少参数
            throw new MyException(ResultEnum.NOT_PARAMETER);
        }
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        String userSession = (String) request.getSession().getAttribute("user");
        NoteBookEntity bookEntity = entity.getNotebook();
        if (bookEntity == null) {
            bookEntity = new NoteBookEntity();
        }
        bookEntity.setSource(userSession);
        entity.setNotebook(bookEntity);
        NotesEntity result = notesService.getOneNotes(entity);
        if (result == null) {
            //未找到有效记录
            throw new MyException(ResultEnum.NOT_EXIST);
        } else {
            return ResultUtil.success(result);
        }
    }

    /**
     * @param entity
     * @param request
     * @描述 获取分页的笔记
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @Override
    public Result<Object> getNotesList(NotesEntity entity, HttpServletRequest request) throws Exception {
        Paging paging = new Paging();
        if (entity.getNowPage() == null) {
            entity.setNowPage(1);
        }
        if (entity.getPageSize() == null) {
            entity.setPageSize(20);
        }
        //每页显示记录的数量
        paging.setPageSize(entity.getPageSize());
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        String userSession = (String) request.getSession().getAttribute("user");
        //获取满足条件的总记录（不分页）
        NoteBookEntity bookEntity = entity.getNotebook();
        if (bookEntity == null) {
            bookEntity = new NoteBookEntity();
        }
        bookEntity.setSource(userSession);
        entity.setNotebook(bookEntity);
        Long pageSize = notesService.getNotesCount(entity);
        if (pageSize > 0) {
            //总记录数
            paging.setDateSum(pageSize);
            //计算总页数
            paging.setTotalPage();
            //设置当前的页码-并校验是否超出页码范围
            paging.setPageNow(entity.getNowPage());
            //设置行索引
            entity.setPage((paging.getPageNow() - 1) * paging.getPageSize(), paging.getPageSize());
            //获取满足条件的记录集合
            List<NotesEntity> list = notesService.getNotesPage(entity);
            paging.setGrid(list);
            return ResultUtil.success(paging);
        } else {
            //未找到有效记录
            throw new MyException(ResultEnum.NOT_EXIST);
        }
    }
}
