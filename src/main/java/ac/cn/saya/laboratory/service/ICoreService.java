package ac.cn.saya.laboratory.service;


import ac.cn.saya.laboratory.entity.ApiEntity;
import ac.cn.saya.laboratory.entity.LogEntity;
import ac.cn.saya.laboratory.entity.PlanEntity;
import ac.cn.saya.laboratory.entity.UserEntity;
import ac.cn.saya.laboratory.tools.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Title: ICoreService
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/9/22 22:07
 * @Description:
 * 综合/核心数据库的相关服务
 */

public interface ICoreService {

    /**
     * 用户登录
     * @param platform 登录平台
     * @param user
     * @param request
     * @return
     * @throws Exception
     */
    public Result<Object> login(String platform,UserEntity user, HttpServletRequest request) throws Exception;


    /**
     * 获取用户的信息
     * @param request
     * @return
     * @throws Exception
     */
    public Result<Object> getUserInfo(HttpServletRequest request) throws Exception;

    /**
     * 注销
     * @param request
     * @return
     * @throws Exception
     */
    public Result<Object> logout(HttpServletRequest request) throws Exception;

    /**
     * 修改用户信息
     * @param user
     * @param request
     * @return
     */
    public Result<Object> setUserInfo(UserEntity user, HttpServletRequest request) throws Exception;

    /**
     * 修改用户密码
     * @param user
     * @param request
     * @return
     */
    public Result<Object> setPassword(UserEntity user, HttpServletRequest request) throws Exception;

    /**
     * 获取所有的日志类别
     * @return
     */
    public Result<Object> getLogType() throws Exception;

    /**
     * 查询日志
     * 按用户、类别、日期
     * @param entity
     * @param request
     * @return
     */
    public Result<Object> getLog(LogEntity entity, HttpServletRequest request) throws Exception;

    /**
     * 导出个人日志
     * @param entity
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public Result<Object> outLogExcel(LogEntity entity, HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * @描述 上传logo
     * @参数
     * @返回值  
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2018/11/11
     * @修改人和其它信息
     */
    public Result<Object> updateLogo(String imgBase64, HttpServletRequest request) throws Exception;

    /**
     * @描述 下载文件 By 文件名
     * @参数  [fileName, request, response]
     * @返回值  void
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/6
     * @修改人和其它信息
     */
    public void fileDownload(String fileName, HttpServletRequest request, HttpServletResponse response);

    /**
     * @描述 
     * @参数  [date, request]
     * @返回值  ac.cn.saya.datacenter.tools.Result<java.lang.Object>
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/24
     * @修改人和其它信息
     * 查询该月的计划
     */
    public Result<Object> getPlan(String date, HttpServletRequest request) throws Exception;

    /**
     * @描述 
     * @参数
     * @返回值  
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/24
     * @修改人和其它信息
     * 查询计划详情
     */
    public Result<Object> getPlanDetail(PlanEntity entity, HttpServletRequest request) throws Exception;

    /**
     * @描述 
     * @参数  [entity, request]
     * @返回值  ac.cn.saya.datacenter.tools.Result<java.lang.Object>
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/24
     * @修改人和其它信息
     * 创建计划
     */
    public Result<Object> createPlan(PlanEntity entity, HttpServletRequest request) throws Exception;

    /**
     * @描述 
     * @参数  [entity, request]
     * @返回值  ac.cn.saya.datacenter.tools.Result<java.lang.Object>
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/24
     * @修改人和其它信息
     * 修改计划
     */
    public Result<Object> editPlan(PlanEntity entity, HttpServletRequest request) throws Exception;

    /**
     * @描述 
     * @参数  [entity, request]
     * @返回值  ac.cn.saya.datacenter.tools.Result<java.lang.Object>
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/24
     * @修改人和其它信息
     * 删除计划
     */
    public Result<Object> deletePlan(PlanEntity entity, HttpServletRequest request) throws Exception;

    /**
     * @描述
     * @参数  [entity, request]
     * @返回值  ac.cn.saya.datacenter.tools.Result<java.lang.Object>
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/24
     * @修改人和其它信息
     * 查询接口列表
     */
    public Result<Object> getApi(ApiEntity entity, HttpServletRequest request) throws Exception;


    /**
     * @描述
     * @参数  [entity, request]
     * @返回值  ac.cn.saya.datacenter.tools.Result<java.lang.Object>
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/24
     * @修改人和其它信息
     * 创建接口
     */
    public Result<Object> createApi(ApiEntity entity, HttpServletRequest request) throws Exception;

    /**
     * @描述
     * @参数  [entity, request]
     * @返回值  ac.cn.saya.datacenter.tools.Result<java.lang.Object>
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/24
     * @修改人和其它信息
     * 修改接口
     */
    public Result<Object> editApi(ApiEntity entity, HttpServletRequest request) throws Exception;

    /**
     * @描述
     * @参数  [entity, request]
     * @返回值  ac.cn.saya.datacenter.tools.Result<java.lang.Object>
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/24
     * @修改人和其它信息
     * 删除接口
     */
    public Result<Object> deleteApi(ApiEntity entity, HttpServletRequest request) throws Exception;

    /**
     * @描述 获取统计报表数据
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019-03-03
     * @修改人和其它信息
     */
    public Result<Object> dashBoard(HttpServletRequest request) throws Exception;


}
