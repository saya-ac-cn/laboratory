package ac.cn.saya.laboratory.controller;

import ac.cn.saya.laboratory.entity.*;
import ac.cn.saya.laboratory.service.ICoreService;
import ac.cn.saya.laboratory.service.IFrontendService;
import ac.cn.saya.laboratory.tools.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Title: ExposeController
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/9/20 23:13
 * @Description: 主动抛出外漏的接口和方法，无需授权认证（注销除外）
 */

@RestController
public class ExposeController {

    @Autowired
    @Qualifier("coreServiceImpl")
    private ICoreService coreServiceImpl;


    @Autowired
    @Qualifier("frontendServiceImpl")
    private IFrontendService frontendServiceImpl;


    /**
     * 登录
     *
     * @return
     */
    @PostMapping("/backend/login/{platform}")
    public Result<Object> login(@PathVariable(value = "platform", required = false) String platform, @RequestBody UserEntity user, HttpServletRequest request) throws Exception {
        return coreServiceImpl.login(platform, user, request);
    }

    /**
     * 下载文件（通过路由）
     *
     * @param url
     * @param request
     * @param response
     */
    @GetMapping("/backend/download/channel-1")
    public void fileDownload(@RequestParam(value = "url") String url, HttpServletRequest request, HttpServletResponse response) {
        /// System.out.println(ClassUtils.getDefaultClassLoader().getResource("").getPath());
        coreServiceImpl.fileDownload(url, request, response);
    }

    /**
     * 用户退出
     *
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/backend/logout")
    public Result<Object> logout(HttpServletRequest httpServletRequest) throws Exception {
        return coreServiceImpl.logout(httpServletRequest);
    }

    /**
     * @描述 查询动态列表
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-03-20
     * @修改人和其它信息
     */
    @GetMapping(value = "/frontend/{user}/news")
    public Result<Object> getNewsList(@PathVariable("user") String user, NewsEntity entity) throws Exception {
        entity.setSource(user);
        return frontendServiceImpl.getNewsList(entity);
    }

    /**
     * @描述 查询单条动态详情
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-03-20
     * @修改人和其它信息
     */
    @GetMapping(value = "/frontend/{user}/news/info")
    public Result<Object> getNewsInfo(@PathVariable("user") String user, NewsEntity entity) throws Exception {
        entity.setSource(user);
        return frontendServiceImpl.getOneNews(entity);
    }

    /**
     * @描述 下载文件
     * @参数 [user, id, request, response]
     * @返回值 ac.cn.saya.laboratory.tools.Result<java.lang.Object>
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-03-20
     * @修改人和其它信息
     */
    @GetMapping(value = "/frontend/{user}/files/download/{id}")
    public Result<Object> downloadFile(@PathVariable("user") String user, @PathVariable("id") int id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return frontendServiceImpl.downloadFile(user, id, request, response);
    }

    /**
     * @描述 获取文件列表
     * @参数 [user, entity]
     * @返回值 ac.cn.saya.laboratory.tools.Result<java.lang.Object>
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-03-20
     * @修改人和其它信息
     */
    @GetMapping(value = "/frontend/{user}/file")
    public Result<Object> getFileList(@PathVariable("user") String user, FilesEntity entity) throws Exception {
        entity.setSource(user);
        entity.setStatus("1");
        return frontendServiceImpl.getFileList(entity);
    }

    /**
     * @描述 查看计划安排
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-03-22
     * @修改人和其它信息
     */
    @GetMapping(value = "/frontend/{user}/plan")
    public Result<Object> getPlan(@PathVariable("user") String user, @RequestParam(value = "date") String date) throws Exception {
        return frontendServiceImpl.getPlan(date, user);
    }

    /**
     * @描述 获取笔记簿
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/12
     * @修改人和其它信息
     */
    @GetMapping(value = "/frontend/{user}/notebook")
    public Result<Object> getNoteBook(@PathVariable("user") String user, NoteBookEntity entity) throws Exception {
        entity.setSource(user);
        return frontendServiceImpl.getNoteBook(entity);
    }

    /**
     * @描述 获取分页的笔记[公开]
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/12
     * @修改人和其它信息
     */
    @GetMapping(value = "/frontend/{user}/notes")
    public Result<Object> getNotesList(@PathVariable("user") String user, NotesEntity entity) throws Exception {
        NoteBookEntity bookEntity = entity.getNotebook();
        if (bookEntity == null) {
            bookEntity = new NoteBookEntity();
        }
        bookEntity.setStatus(1);
        bookEntity.setSource(user);
        entity.setNotebook(bookEntity);
        return frontendServiceImpl.getNotesList(entity);
    }

    /**
     * @描述 查询笔记详情
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-04-02
     * @修改人和其它信息
     */
    @GetMapping(value = "/frontend/{user}/notes/info")
    public Result<Object> getNotesInfo(@PathVariable("user") String user, NotesEntity entity) throws Exception {
        return frontendServiceImpl.getOneNotes(user, entity);
    }

}
