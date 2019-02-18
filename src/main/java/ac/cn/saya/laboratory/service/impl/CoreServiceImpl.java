package ac.cn.saya.laboratory.service.impl;

import ac.cn.saya.laboratory.entity.LogEntity;
import ac.cn.saya.laboratory.entity.LogTypeEntity;
import ac.cn.saya.laboratory.entity.PlanEntity;
import ac.cn.saya.laboratory.entity.UserEntity;
import ac.cn.saya.laboratory.exception.MyException;
import ac.cn.saya.laboratory.handle.RepeatLogin;
import ac.cn.saya.laboratory.persistent.service.LogService;
import ac.cn.saya.laboratory.persistent.service.PlanService;
import ac.cn.saya.laboratory.persistent.service.UserService;
import ac.cn.saya.laboratory.service.ICoreService;
import ac.cn.saya.laboratory.tools.*;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: CoreServiceImpl
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/9/22 22:11
 * @Description:
 * 综合/核心数据库的相关服务
 */

@Service("coreServiceImpl")
public class CoreServiceImpl implements ICoreService {

    private static Logger logger = LoggerFactory.getLogger(CoreServiceImpl.class);

    @Resource
    @Qualifier("userService")
    private UserService userService;

    @Resource
    @Qualifier("logService")
    private LogService logService;

    @Resource
    @Qualifier("planService")
    private PlanService planService;

    @Resource
    @Qualifier("recordService")//日志助手表
    private RecordService recordService;


    /**
     * 用户登录
     * @param user
     * @param request
     * @return
     */
    @Override
    public Result<Object> login(UserEntity user, HttpServletRequest request) throws Exception {
        // 校验用户输入的参数
        if(StringUtils.isEmpty(user.getUser()) || StringUtils.isEmpty(user.getPassword())) {
            // 缺少参数
            throw new MyException(ResultEnum.NOT_PARAMETER);
        }
        HttpSession session = request.getSession();
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        String userSession = (String) session.getAttribute("user");
        UserEntity entity = userService.getUser(user.getUser());
        if (entity == null) {
            // 没有该用户的信息 直接中断返回
            //未找到该用户
            throw new MyException(ResultEnum.ERROP);
        }
        if(userSession != null) {
            // session 检查到已登录（同一机器设备中）
            entity.setPassword(null);
            // 转换成浏览器可以直接识别的url
            entity.setLogo(UploadUtils.descUrl(entity.getLogo()));
            return ResultUtil.success(entity);
        } else {
            // 未登录（当前设备）
            // 在系统中查询该用户是否存在
            // 在服务器全局中检查
            // 由于登录时 可以用用户名 和 邮箱登录 所以 这里用用户查找
            ///if(redisUtils.hmExists("DataCenter:SessionMap",entity.getUser())) {
            if(RepeatLogin.sessionMap.get(user.getUser()) != null){
                /**
                 * 已经登录
                 * 将已经登陆的信息拿掉,踢下线
                 */
                RepeatLogin.forceUserLogout(user.getUser());
            }
            // 比对密码
            //加密后用户的密码
            user.setPassword(DesUtil.encrypt(user.getPassword()));
            if(entity.getPassword().equals(user.getPassword())) {
                //设置session
                //为之注入用户名
                session.setAttribute("user", entity.getUser());
                //放入用户的session 到数据库中
                ///redisUtils.hmSet("DataCenter:SessionMap",user.getUser(),session.getId());
                RepeatLogin.sessionMap.put(user.getUser(),session);
                // 登录
                recordService.record("OX001",request);
                // 对密码脱敏处理
                entity.setPassword(null);
                // 转换成浏览器可以直接识别的url
                entity.setLogo(UploadUtils.descUrl(entity.getLogo()));
                //返回登录成功
                return ResultUtil.success(entity);
            } else{
                //密码错误
                throw new MyException(ResultEnum.ERROP);
            }

        }

    }

    /**
     * 用户退出
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public Result<Object> logout(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        String userSession = (String) session.getAttribute("user");
        if(userSession != null) {
            // 删除数据库里面的登录信息
            RepeatLogin.delSession(session);
            session.invalidate();
        }
        return ResultUtil.success();
    }

    /**
     * 获取用户的信息
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public Result<Object> getUserInfo(HttpServletRequest request) throws Exception {
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        String userSession = (String) request.getSession().getAttribute("user");
        // 在系统中查询该用户是否存在
        UserEntity entity = userService.getUser(userSession);
        if (entity == null)
        {
            // 没有该用户的信息 直接中断返回
            //未找到该用户
            throw new MyException(ResultEnum.ERROP);
        } else {
            //脱敏用户密码
            entity.setPassword("");
            return  ResultUtil.success(entity);
        }
    }

    /**
     * 修改用户信息
     *
     * @param user
     * @param request
     * @return
     */
    @Override
    public Result<Object> setUserInfo(UserEntity user, HttpServletRequest request) throws Exception{
        // 校验用户输入的参数
        if(user == null) {
            // 缺少参数
            throw new MyException(ResultEnum.NOT_PARAMETER);
        }
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        String userSession = (String) request.getSession().getAttribute("user");
        user.setUser(userSession);
        if(userService.setUser(user) > 0 ) {
            /**
             * 记录日志
             */
            // 修改个人信息
            recordService.record("OX002",request);
            return ResultUtil.success();
        } else {
            throw new MyException(ResultEnum.ERROP);
        }
    }

    /**
     * 修改用户密码
     *
     * @param user
     * @param request
     * @return
     */
    @Override
    public Result<Object> setPassword(UserEntity user, HttpServletRequest request) throws Exception{
        // 校验用户输入的参数
        if(user == null || StringUtils.isEmpty(user.getPassword())) {
            // 缺少参数
            throw new MyException(ResultEnum.NOT_PARAMETER);
        }
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        String userSession = (String) request.getSession().getAttribute("user");
        user.setUser(userSession);
        //加密后用户的密码
        user.setPassword(DesUtil.encrypt(user.getPassword()));
        if(userService.setUser(user) > 0 )
        {
            /**
             * 记录日志
             */
            // 修改密码
            recordService.record("OX004",request);
            return ResultUtil.success();
        } else {
            throw new MyException(ResultEnum.ERROP);
        }
    }

    /**
     * 获取所有的日志类别
     *
     * @return
     */
    @Override
    public Result<Object> getLogType()  throws Exception{
        List<LogTypeEntity> list = logService.selectLogType();
        if (list.size() < 0) {
            // 没有该用户的信息 直接中断返回
            //未找到登录类别
            throw new MyException(ResultEnum.NOT_EXIST);
        } else {
            return  ResultUtil.success(list);
        }
    }

    /**
     * 查询日志
     * 按用户、类别、日期
     *
     * @param entity
     * @return
     */
    @Override
    public Result<Object> getLog(LogEntity entity, HttpServletRequest request)  throws Exception{
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        String userSession = (String) request.getSession().getAttribute("user");
        entity.setUser(userSession);
        Paging paging =new Paging();
        if(entity.getNowPage() == null) {
            entity.setNowPage(1);
        }
        if(entity.getPageSize() == null) {
            entity.setPageSize(20);
        }
        //每页显示记录的数量
        paging.setPageSize(entity.getPageSize());
        //获取满足条件的总记录（不分页）
        Long pageSize = logService.selectCount(entity);
        if(pageSize > 0) {
            //总记录数
            paging.setDateSum(pageSize);
            //计算总页数
            paging.setTotalPage();
            //设置当前的页码-并校验是否超出页码范围
            paging.setPageNow(entity.getNowPage());
            //设置行索引
            entity.setPage((paging.getPageNow()-1)*paging.getPageSize(),paging.getPageSize());
            //获取满足条件的记录集合
            List<LogEntity> list = logService.selectPage(entity);
            paging.setGrid(list);
            return ResultUtil.success(paging);
        } else {
            //未找到有效记录
            throw new MyException(ResultEnum.NOT_EXIST);
        }
    }

    /**
     * 导出个人日志
     *
     * @param entity
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    public Result<Object> outLogExcel(LogEntity entity, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] keys = {"user","describe","ip","city","date"};
        //放置到第一行的字段名
        String[] titles = {"用户","操作详情","IP","城市","日期"};
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        String userSession = (String) request.getSession().getAttribute("user");
        try{
            //获取满足条件的总记录（不分页）
            Long pageSize = logService.selectCount(entity);
            if(pageSize <= 0) {
                return ResultUtil.error(-1,"没有可导出的数据");
            }
            //设置行索引
            entity.setPage(0,pageSize.intValue());
            entity.setUser(userSession);
            //获取满足条件的记录集合
            List<LogEntity> entityList = logService.selectPage(entity);
            List<JSONObject> jsonObjectList = new ArrayList<>();
            for (LogEntity item : entityList) {
                JSONObject json = new JSONObject();
                json.put("user",item.getUser());
                json.put("describe",item.getLogType().getDescribe());
                json.put("ip",item.getIp());
                json.put("city",item.getCity());
                json.put("date", item.getDate());
                jsonObjectList.add(json);
            }
            // 设置contentType为excel格式
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("UTF-8");
            //设置文件头：最后一个参数是设置下载文件名
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("操作日志.xlsx", "UTF-8"));
            ServletOutputStream out=response.getOutputStream();
            response.flushBuffer();
            OutExcelUtils.outExcelTemplateSimple(keys,titles,jsonObjectList,out);
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param imgBase64
     * @描述 上传logo
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2018/11/11
     * @修改人和其它信息
     */
    @Override
    public Result<Object> updateLogo(String imgBase64, HttpServletRequest request) throws Exception {
        Result<String> upload = UploadUtils.uploadLogo(imgBase64,request);
        if(upload.getCode() == 0) {
            //logo上传成功
            //得到文件上传成功的回传地址
            String successUrl = String.valueOf(upload.getData());
            //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
            String userSession = (String) request.getSession().getAttribute("user");
            UserEntity user = new UserEntity();
            user.setUser(userSession);
            user.setLogo(successUrl);
            if(userService.setUser(user) > 0 ) {
                /**
                 * 记录日志
                 * 上传头像
                 */
                recordService.record("OX003",request);
                return ResultUtil.success(UploadUtils.descUrl(successUrl));
            } else {
                throw new MyException(ResultEnum.ERROP);
            }
        } else if(upload.getCode() == -2) {
            //不是有效的图片
            throw new MyException(ResultEnum.NOT_PARAMETER);
        } else {
            // 图片上传异常
            throw new MyException(ResultEnum.ERROP);
        }
    }

    /**
     * @描述 下载文件 By 文件名
     * @参数  [fileName, request, response]
     * @返回值  void
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/6
     * @修改人和其它信息
     */
    @Override
    public void fileDownload (String fileName,HttpServletRequest request, HttpServletResponse  response) {
        String path = request.getServletContext().getRealPath(fileName);
        File filepath = new File(path);
        //获取要下载的文件名
        String[] fileArray = fileName.split(File.separator);
        String newFilename = fileArray[fileArray.length-1];
        //判断文件父目录是否存在
        if(filepath.exists()) {
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            // 设置content-disposition响应头控制浏览器以下载的形式打开文件
            response.setHeader("Content-Disposition", "attachment;filename=" + newFilename);
            //创建数据缓冲区
            byte[] buff = new byte[1024];
            BufferedInputStream bis = null;
            OutputStream os = null;
            try {
                //通过response对象获取OutputStream流
                os = response.getOutputStream();
                // 根据文件路径获取要下载的文件输入流
                bis = new BufferedInputStream(new FileInputStream(filepath));
                //将FileInputStream流写入到buffer缓冲区
                int i = bis.read(buff);
                while (i != -1) {
                    // 使用将OutputStream缓冲区的数据输出到客户端浏览器
                    os.write(buff, 0, buff.length);
                    os.flush();
                    i = bis.read(buff);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * @param data
     * @param request
     * @描述
     * @参数 [data, request]
     * @返回值 ac.cn.saya.datacenter.tools.Result<java.lang.Object>
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/24
     * @修改人和其它信息 查询该月的计划
     */
    @Override
    public Result<Object> getPlan(String data, HttpServletRequest request) throws Exception {
        // 日期准备
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // 第一天
        String firstDay = DateUtil.getFirstDay(format.parse(data));
        // 最后一天
        String lastDay = DateUtil.getLastDay(format.parse(data));
        // 获取该月的总天数
        Integer monthCount = DateUtil.getDaysOfMonth(format.parse(data));
        // 获取该月第一天是星期几--星期日i==1，星期六i==7
        Integer firstDayWeek = DateUtil.getFirstDayWeek(format.parse(firstDay));
        String userSession = (String) request.getSession().getAttribute("user");
        PlanEntity query = new PlanEntity();
        // 查询该用户的计划
        query.setSource(userSession);
        // 查询该月的计划
        query.setBeginTime(firstDay);
        query.setEndTime(lastDay);
        List<PlanEntity> plan = planService.getPlanList(query);
        if(firstDayWeek < 1 || firstDayWeek > 7){
            // 获取的该月第一天是否正常
            throw new MyException(ResultEnum.ERROP);
        }else{
            // 表格中单元格个数（1号前的空单元格加上日历的单元格）
            Integer gridCount = monthCount + (firstDayWeek - 1);
            // 总行数
            Integer tableLine = 5;
            if (gridCount % 7 == 0) {
                tableLine = gridCount / 7;
            }else {
                tableLine = gridCount / 7 + 1;
            }
            // 统计有效的单元格（加上月尾的空白单元格）
            gridCount = tableLine * 7;
            List<JSONObject> jsonObjectList = new ArrayList<>();
            for(int i = 1; i <= gridCount; i++){
                JSONObject json = new JSONObject();
                if(i >= firstDayWeek && i <= (monthCount + (firstDayWeek - 1))){
                    json.put("flog", 1);
                    json.put("number", i - (firstDayWeek - 1));
                    json.put("value", 0);
                    json.put("id", -1);
                    // 输出日历
                    if(plan != null){
                        for(PlanEntity item:plan){
                            if(item.getNumber() == (i - (firstDayWeek - 1))){
                                // 该天有内容
                                json.put("value", item.getDescribe());
                                json.put("id", item.getId());
                                break;
                            }
                        }
                    }
                }else{
                    // 输出空白
                    json.put("flog", 0);
                    json.put("number", 0);
                    json.put("value", 0);
                }
                jsonObjectList.add(json);
            }
            return ResultUtil.success(jsonObjectList);
        }
    }

    /**
     * @param entity
     * @param request
     * @描述
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/24
     * @修改人和其它信息 查询计划详情
     */
    @Override
    public Result<Object> getPlanDetail(PlanEntity entity, HttpServletRequest request) throws Exception {
        String userSession = (String) request.getSession().getAttribute("user");
        entity.setSource(userSession);
        if(entity == null || entity.getId() == null){
            // 缺少参数
            throw new MyException(ResultEnum.NOT_PARAMETER);
        }
        PlanEntity result = planService.getOnePlan(entity);
        if(result == null){
            //未找到有效记录
            throw new MyException(ResultEnum.NOT_EXIST);
        }else{
            return ResultUtil.success(result);
        }
    }

    /**
     * @param entity
     * @param request
     * @描述
     * @参数 [entity, request]
     * @返回值 ac.cn.saya.datacenter.tools.Result<java.lang.Object>
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/24
     * @修改人和其它信息 创建计划
     */
    @Override
    public Result<Object> createPlan(PlanEntity entity, HttpServletRequest request) throws Exception {
        // 校验用户输入的参数
        if(entity == null ){
            // 缺少参数
            throw new MyException(ResultEnum.NOT_PARAMETER);
        }
        String userSession = (String) request.getSession().getAttribute("user");
        entity.setSource(userSession);
        Integer flog = planService.insertPlan(entity);
        if(flog > 0){
            /**
             * 记录日志
             */
            recordService.record("OX022",request);
            return ResultUtil.success();
        }else if(flog == -2){
            // 改天计划已经存在
            throw new MyException(ResultEnum.ERROP);
        } else {
            throw new MyException(ResultEnum.UNKONW_ERROR);
        }
    }

    /**
     * @param entity
     * @param request
     * @描述
     * @参数 [entity, request]
     * @返回值 ac.cn.saya.datacenter.tools.Result<java.lang.Object>
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/24
     * @修改人和其它信息 修改计划
     */
    @Override
    public Result<Object> editPlan(PlanEntity entity, HttpServletRequest request) throws Exception {
        // 校验用户输入的参数
        if(entity == null ){
            // 缺少参数
            throw new MyException(ResultEnum.NOT_PARAMETER);
        }
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        String userSession = (String) request.getSession().getAttribute("user");
        entity.setSource(userSession);
        // 日期准备
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        entity.setPlandate(format.format(format.parse(entity.getPlandate())));
        Integer flog = planService.editPlan(entity);
        if(flog > 0){
            /**
             * 记录日志
             */
            recordService.record("OX023",request);
            return ResultUtil.success();
        }else if(flog == -2){
            // 改天计划已经存在
            throw new MyException(ResultEnum.ERROP);
        } else {
            throw new MyException(ResultEnum.UNKONW_ERROR);
        }
    }

    /**
     * @param entity
     * @param request
     * @描述
     * @参数 [entity, request]
     * @返回值 ac.cn.saya.datacenter.tools.Result<java.lang.Object>
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/24
     * @修改人和其它信息 删除计划
     */
    @Override
    public Result<Object> deletePlan(PlanEntity entity, HttpServletRequest request) throws Exception {
        // 校验用户输入的参数
        if(entity == null ){
            // 缺少参数
            throw new MyException(ResultEnum.NOT_PARAMETER);
        }
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        String userSession = (String) request.getSession().getAttribute("user");
        entity.setSource(userSession);
        if(planService.deletePlan(entity) > 0){
            /**
             * 记录日志
             */
            recordService.record("OX024",request);
            return ResultUtil.success();
        } else {
            throw new MyException(ResultEnum.ERROP);
        }
    }
}
