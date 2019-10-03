package ac.cn.saya.laboratory.service;

import ac.cn.saya.laboratory.entity.*;
import ac.cn.saya.laboratory.tools.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Title: IFrontendService
 * @ProjectName laboratory
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2019-03-18 22:40
 * @Description:
 * 对外提供访问的接口，无需认证
 * 留言编号：20190318fb8ca4ca , 查询码：44a90448
 */

public interface IFrontendService {

    /**
     * @描述 查询一条动态
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/12
     * @修改人和其它信息
     */
    public Result<Object> getOneNews(NewsEntity entity) throws Exception;

    /**
     * @描述 获取分页的动态
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/11
     * @修改人和其它信息
     */
    public Result<Object> getNewsList(NewsEntity entity) throws Exception;

    /**
     * @描述 获取分页文件列表
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019-03-20
     * @修改人和其它信息
     */
    public Result<Object> getFileList(FilesEntity entity) throws Exception;

    /**
     * @描述 下载文件
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019-03-20
     * @修改人和其它信息
     */
    public Result<Object> downloadFile(String user, Integer id, HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * @描述 留言
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/11
     * @修改人和其它信息
     */
    public Result<Object> insertGuestBook(GuestBookEntity entity) throws Exception;

    /**
     * 查看行程安排
     * @描述
     * @参数 [data, request]
     * @返回值 ac.cn.saya.datacenter.tools.Result<java.lang.Object>
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/24
     * @修改人和其它信息 查询该月的计划
     */
    public Result<Object> getPlan(String date, String user) throws Exception;

    /**
     * @描述 获取笔记簿
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/11
     * @修改人和其它信息
     */
    public Result<Object> getNoteBook(NoteBookEntity entity) throws Exception;

    /**
     * @描述 获取分页的笔记
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/11
     * @修改人和其它信息
     */
    public Result<Object> getNotesList(NotesEntity entity) throws Exception;

    /**
     * @描述 查询一条笔记
     * @参数  [entity]
     * @返回值  ac.cn.saya.laboratory.tools.Result<java.lang.Object>
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019-04-02
     * @修改人和其它信息
     */
    public Result<Object> getOneNotes(String user,NotesEntity entity) throws Exception;

}
