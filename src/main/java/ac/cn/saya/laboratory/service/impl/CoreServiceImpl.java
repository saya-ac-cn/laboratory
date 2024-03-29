package ac.cn.saya.laboratory.service.impl;

import ac.cn.saya.laboratory.entity.*;
import ac.cn.saya.laboratory.exception.MyException;
import ac.cn.saya.laboratory.handle.RepeatLogin;
import ac.cn.saya.laboratory.persistent.business.service.*;
import ac.cn.saya.laboratory.persistent.primary.service.LogService;
import ac.cn.saya.laboratory.persistent.primary.service.UserService;
import ac.cn.saya.laboratory.service.ICoreService;
import ac.cn.saya.laboratory.tools.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @Title: CoreServiceImpl
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/9/22 22:11
 * @Description: 综合/核心数据库的相关服务
 */

@Service("coreServiceImpl")
public class CoreServiceImpl implements ICoreService {

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
    @Qualifier("pictureStorageService")
    private PictureStorageService pictureStorageService;

    @Resource
    @Qualifier("filesService")
    private FilesService filesService;

    @Resource
    @Qualifier("newsService")
    private NewsService newsService;

    @Resource
    @Qualifier("systemServiceImpl")
    private SystemServiceImpl systemServiceImpl;

    @Resource
    @Qualifier("noteBookService")
    private NoteBookService noteBookService;

    @Resource
    @Qualifier("notesService")
    private NotesService notesService;

    @Resource
    @Qualifier("recordService")//日志助手表
    private RecordService recordService;

    @Resource
    @Qualifier("amapLocateUtils")
    private AmapLocateUtils amapLocateUtils;

    @Resource
    @Qualifier("uploadUtils")
    private UploadUtils uploadUtils;

    /**
     * 用户登录
     *
     * @param platform 登录平台
     * @param user
     * @param request
     * @return
     */
    @Override
    public Result<Object> login(String platform,UserEntity user, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        // 校验用户输入的参数
        if (StringUtils.isEmpty(user.getUser()) || StringUtils.isEmpty(user.getPassword())) {
            // 缺少参数
            throw new MyException(ResultEnum.NOT_PARAMETER);
        }
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        UserMemory userSession = HttpRequestUtil.getUserMemory(request);
        UserEntity entity = userService.getUser(user.getUser());
        if (entity == null) {
            // 没有该用户的信息 直接中断返回
            //未找到该用户
            throw new MyException(ResultEnum.ERROP);
        }

        Map<String, Object> result = new HashMap<>();
        if (userSession != null) {
            // 读取该用户最近的登录及安排信息
            List<PlanEntity> todayPlan = planService.queryTodayPlan(userSession.getUser());
            result.put("plan",todayPlan.isEmpty()?null:todayPlan);
            result.put("log",logService.queryRecentlyLog(userSession.getUser()));
            // session 检查到已登录（同一机器设备中）
            entity.setPassword(null);
            // 转换成浏览器可以直接识别的url
            // 用户logo，及背景图片的返回
            entity.setLogo(uploadUtils.descUrl(entity.getLogo()));
            String pictureUrl = null;
            if (null != entity.getBackground() && !(StringUtils.isEmpty(pictureUrl = pictureStorageService.getPictureUrl(entity.getBackground())))){
                entity.setBackgroundUrl(uploadUtils.descUrl(pictureUrl));
            }
            // 注入用户个人信息
            result.put("user", entity);
            return ResultUtil.success(result);
        } else {
            // 未登录（当前设备）
            // 在系统中查询该用户是否存在
            // 在服务器全局中检查
            // 由于登录时 可以用用户名 和 邮箱登录 所以 这里用用户查找
            ///if(redisUtils.hmExists("DataCenter:SessionMap",entity.getUser())) {
            if (RepeatLogin.sessionMap.get(user.getUser()) != null) {
                //已经登录，将已经登陆的信息拿掉,踢下线
                RepeatLogin.forceUserLogout(user.getUser());
            }
            // 比对密码
            //加密后用户的密码
            user.setPassword(DesUtil.encrypt(user.getPassword()));
            if (entity.getPassword().equals(user.getPassword())) {
                //设置session
                // 设置封装到session中的实体信息
                UserMemory memory = new UserMemory();
                String ip = HttpRequestUtil.getIpAddress(request);
                memory.setUser(entity.getUser());
                memory.setIp(ip);
                memory.setCity(amapLocateUtils.getCityByIp(ip));
                //为之注入用户信息
                session.setAttribute("user", memory);
                //放入用户的session 到数据库中（或本服务器的内存中），防止重复登录
                ///redisUtils.hmSet("DataCenter:SessionMap",user.getUser(),session.getId());
                RepeatLogin.sessionMap.put(entity.getUser(), session);
                // 对密码脱敏处理
                entity.setPassword(null);
                // 转换成浏览器可以直接识别的url
                // 用户logo，及背景图片的返回
                entity.setLogo(uploadUtils.descUrl(entity.getLogo()));
                String pictureUrl = null;
                if (null != entity.getBackground() && !(StringUtils.isEmpty(pictureUrl = pictureStorageService.getPictureUrl(entity.getBackground())))){
                    entity.setBackgroundUrl(uploadUtils.descUrl(pictureUrl));
                }
                // 返回用户的个人信息
                List<PlanEntity> todayPlan = planService.queryTodayPlan(user.getUser());
                result.put("plan",todayPlan.isEmpty()?null:todayPlan);
                result.put("log",logService.queryRecentlyLog(user.getUser()));
                result.put("user", entity);
                // 记录本次登录
                recordService.record("OX001", request);
                // 发送登录邮件
                //systemServiceImpl.sendLoginNotice(entity.getEmail(),entity.getUser(),entity.getUser(),memory.getIp(),memory.getCity(),platform,DateUtils.getCurrentDateTime(DateUtils.dateTimeFormat));
                //返回登录成功
                return ResultUtil.success(result);
            } else {
                //密码错误
                throw new MyException(ResultEnum.ERROP);
            }
        }
    }

    /**
     * 用户退出
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public Result<Object> logout(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        UserMemory userSession = (UserMemory) session.getAttribute("user");
        if (userSession != null) {
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
        UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
        // 在系统中查询该用户是否存在
        UserEntity entity = userService.getUser(userSession.getUser());
        if (entity == null) {
            // 没有该用户的信息 直接中断返回
            //未找到该用户
            throw new MyException(ResultEnum.ERROP);
        } else {
            //脱敏用户密码
            entity.setPassword("");
            return ResultUtil.success(entity);
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
    public Result<Object> setUserInfo(UserEntity user, HttpServletRequest request) throws Exception {
        // 校验用户输入的参数
        if (user == null) {
            // 缺少参数
            throw new MyException(ResultEnum.NOT_PARAMETER);
        }
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
        user.setUser(userSession.getUser());
        if (userService.setUser(user) > 0) {
            // 修改个人信息
            recordService.record("OX002", request);
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
    public Result<Object> setPassword(UserEntity user, HttpServletRequest request) throws Exception {
        // 校验用户输入的参数
        if (user == null || StringUtils.isEmpty(user.getPassword())) {
            // 缺少参数
            throw new MyException(ResultEnum.NOT_PARAMETER);
        }
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
        user.setUser(userSession.getUser());
        //加密后用户的密码
        user.setPassword(DesUtil.encrypt(user.getPassword()));
        if (userService.setUser(user) > 0) {
            /**
             * 记录日志
             */
            // 修改密码
            recordService.record("OX004", request);
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
    public Result<Object> getLogType() throws Exception {
        List<LogTypeEntity> list = logService.selectLogType();
        if (CollectionUtils.isEmpty(list)) {
            // 没有该用户的信息 直接中断返回
            //未找到登录类别
            throw new MyException(ResultEnum.NOT_EXIST);
        } else {
            return ResultUtil.success(list);
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
    public Result<Object> getLog(LogEntity entity, HttpServletRequest request) throws Exception {
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
        entity.setUser(userSession.getUser());
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
        Long pageSize = logService.selectCount(entity);
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
        String[] keys = {"user", "describe", "ip", "city", "date"};
        //放置到第一行的字段名
        String[] titles = {"用户", "操作详情", "IP", "城市", "日期"};
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
        try {
            //获取满足条件的总记录（不分页）
            Long pageSize = logService.selectCount(entity);
            if (pageSize <= 0) {
                response.setStatus(404);
                return ResultUtil.error(-1, "没有可导出的数据");
            }
            //设置行索引
            entity.setPage(0, pageSize.intValue());
            entity.setUser(userSession.getUser());
            //获取满足条件的记录集合
            List<LogEntity> entityList = logService.selectPage(entity);
            ArrayNode jsonObjectList = JackJsonUtil.createArrayNode();
            for (LogEntity item : entityList) {
                ObjectNode json = JackJsonUtil.createObjectNode();
                json.put("user", item.getUser());
                json.put("describe", item.getLogType().getDescribe());
                json.put("ip", item.getIp());
                json.put("city", item.getCity());
                json.put("date", item.getDate());
                jsonObjectList.add(json);
            }
            // 设置contentType为excel格式
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("UTF-8");
            //设置文件头：最后一个参数是设置下载文件名
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("操作日志.xlsx", "UTF-8"));
            ServletOutputStream out = response.getOutputStream();
            response.flushBuffer();
            OutExcelUtils.outExcelTemplateSimple(keys, titles, jsonObjectList, out);
        } catch (Exception e) {
            response.setStatus(500);
        }
        return null;
    }

    /**
     * @描述 上传logo
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2018/11/11
     * @修改人和其它信息
     */
    @Override
    public Result<Object> updateLogo(String imgBase64, HttpServletRequest request) throws Exception {
        Result<String> upload = uploadUtils.uploadLogo(imgBase64, request);
        if (upload.getCode() == 0) {
            //logo上传成功
            //得到文件上传成功的回传地址
            String successUrl = String.valueOf(upload.getData());
            //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
            UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
            UserEntity user = new UserEntity();
            user.setUser(userSession.getUser());
            user.setLogo(successUrl);
            if (userService.setUser(user) > 0) {
                recordService.record("OX003", request);
                return ResultUtil.success(uploadUtils.descUrl(successUrl));
            } else {
                throw new MyException(ResultEnum.ERROP);
            }
        } else if (upload.getCode() == -2) {
            //不是有效的图片
            throw new MyException(ResultEnum.NOT_PARAMETER);
        } else {
            // 图片上传异常
            throw new MyException(ResultEnum.ERROP);
        }
    }

    /**
     * @描述 下载文件 By 文件名
     * @参数 [fileName, request, response]
     * @返回值 void
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/6
     * @修改人和其它信息
     */
    @Override
    public void fileDownload(String fileName, HttpServletRequest request, HttpServletResponse response) {
        String path = request.getServletContext().getRealPath(fileName);
        File filepath = new File(path);
        //获取要下载的文件名
        String[] fileArray = fileName.split(File.separator);
        String newFilename = fileArray[fileArray.length - 1];
        //判断文件父目录是否存在
        if (filepath.exists()) {
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
                response.setStatus(500);
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
     * @描述
     * @参数 [data, request]
     * @返回值 ac.cn.saya.datacenter.tools.Result<java.lang.Object>
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/24
     * @修改人和其它信息 查询该月的计划
     */
    @Override
    public Result<Object> getPlan(String date, HttpServletRequest request) throws Exception {
        // 日期准备
        // 第一天
        String firstDay = DateUtils.getFirstDayOfMonth(date);
        // 最后一天
        String lastDay = DateUtils.getLastDayOfMonth(date);
        // 获取该月的总天数
        int monthCount = DateUtils.getLengthOfMonth(date);
        // 获取该月第一天是星期几--星期日i==1，星期六i==7
        int firstDayWeek = DateUtils.getFirstDayWeek(date);
        UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
        PlanEntity query = new PlanEntity();
        // 查询该用户的计划
        query.setSource(userSession.getUser());
        // 查询该月的计划
        query.setBeginTime(firstDay);
        query.setEndTime(lastDay);
        List<PlanEntity> plan = planService.getPlanList(query);
        if (firstDayWeek < 1 || firstDayWeek > 7) {
            // 获取的该月第一天是否正常
            throw new MyException(ResultEnum.ERROP);
        } else {
            // 表格中单元格个数（1号前的空单元格加上日历的单元格）
            Integer gridCount = monthCount + (firstDayWeek - 1);
            // 总行数
            Integer tableLine = 5;
            if (gridCount % 7 == 0) {
                tableLine = gridCount / 7;
            } else {
                tableLine = gridCount / 7 + 1;
            }
            // 统计有效的单元格（加上月尾的空白单元格）
            gridCount = tableLine * 7;
            ArrayNode jsonObjectList = JackJsonUtil.createArrayNode();
            for (int i = 1; i <= gridCount; i++) {
                ObjectNode json = JackJsonUtil.createObjectNode();
                if (i >= firstDayWeek && i <= (monthCount + (firstDayWeek - 1))) {
                    json.put("flog", 1);
                    json.put("number", i - (firstDayWeek - 1));
                    json.put("value", 0);
                    json.put("id", -1);
                    // 输出日历
                    if (plan != null) {
                        for (PlanEntity item : plan) {
                            if (item.getNumber() == (i - (firstDayWeek - 1))) {
                                // 该天有内容
                                json.put("value", item.getDescribe());
                                json.put("id", item.getId());
                                break;
                            }
                        }
                    }
                } else {
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
     * @描述
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/24
     * @修改人和其它信息 查询计划详情
     */
    @Override
    public Result<Object> getPlanDetail(PlanEntity entity, HttpServletRequest request) throws Exception {
        if (entity == null || entity.getId() == null) {
            // 缺少参数
            throw new MyException(ResultEnum.NOT_PARAMETER);
        }
        UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
        entity.setSource(userSession.getUser());
        PlanEntity result = planService.getOnePlan(entity);
        if (result == null) {
            //未找到有效记录
            throw new MyException(ResultEnum.NOT_EXIST);
        } else {
            return ResultUtil.success(result);
        }
    }

    /**
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
        if (entity == null) {
            // 缺少参数
            throw new MyException(ResultEnum.NOT_PARAMETER);
        }
        UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
        entity.setSource(userSession.getUser());
        Integer flog = planService.insertPlan(entity);
        if (flog > 0) {
            recordService.record("OX022", request);
            return ResultUtil.success();
        } else if (flog == -2) {
            // 该天计划已经存在
            throw new MyException(ResultEnum.ERROP);
        } else {
            throw new MyException(ResultEnum.UNKONW_ERROR);
        }
    }

    /**
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
        if (entity == null) {
            // 缺少参数
            throw new MyException(ResultEnum.NOT_PARAMETER);
        }
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
        entity.setSource(userSession.getUser());
        // 日期准备
        entity.setPlandate(entity.getPlandate());
        Integer flog = planService.editPlan(entity);
        if (flog > 0) {
            /**
             * 记录日志
             */
            recordService.record("OX023", request);
            return ResultUtil.success();
        } else if (flog == -2) {
            // 改天计划已经存在
            throw new MyException(ResultEnum.ERROP);
        } else {
            throw new MyException(ResultEnum.UNKONW_ERROR);
        }
    }

    /**
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
        if (entity == null) {
            // 缺少参数
            throw new MyException(ResultEnum.NOT_PARAMETER);
        }
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
        entity.setSource(userSession.getUser());
        if (planService.deletePlan(entity) > 0) {
            /**
             * 记录日志
             */
            recordService.record("OX024", request);
            return ResultUtil.success();
        } else {
            throw new MyException(ResultEnum.ERROP);
        }
    }

    /**
     * @描述 获取统计总数及笔记簿词云数据
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-03-03
     * @修改人和其它信息
     */
    @Override
    public Result<Object> countAndWordCloud(HttpServletRequest request) throws Exception {
        Map<String, Object> result = new HashMap<>();
        UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
        // 统计图片总数
        PictureEntity pictureEntity = new PictureEntity();
        pictureEntity.setSource(userSession.getUser());
        CompletableFuture<Long> pictureCountFuture = CompletableFuture.supplyAsync(()->pictureStorageService.getPictuBase64Count(pictureEntity));

        // 统计文件总数
        FilesEntity filesEntity = new FilesEntity();
        filesEntity.setSource(userSession.getUser());
        CompletableFuture<Long> fileCountFuture = CompletableFuture.supplyAsync(()->filesService.getFileCount(filesEntity));

        // 统计笔记簿总数
        NoteBookEntity bookEntity = new NoteBookEntity();
        bookEntity.setSource(userSession.getUser());
        CompletableFuture<Long> bookCountFuture = CompletableFuture.supplyAsync(()->noteBookService.getNoteBookCount(bookEntity));

        // 统计笔记总数
        NotesEntity notesEntity = new NotesEntity();
        notesEntity.setNotebook(bookEntity);
        CompletableFuture<Long> notesCountFuture = CompletableFuture.supplyAsync(()->notesService.getNotesCount(notesEntity));

        // 统计计划总数
        PlanEntity planEntity = new PlanEntity();
        planEntity.setSource(userSession.getUser());
        CompletableFuture<Long> planCountFuture = CompletableFuture.supplyAsync(()->planService.getPlanCount(planEntity));

        // 统计公告总数
        NewsEntity newsEntity = new NewsEntity();
        newsEntity.setSource(userSession.getUser());
        CompletableFuture<Long> newsCountFuture = CompletableFuture.supplyAsync(()->newsService.getNewsCount(newsEntity));

        ArrayNode roseData = JackJsonUtil.createArrayNode();

        Long pictureCount = pictureCountFuture.exceptionally(f -> 0L).get();
        ObjectNode pictureItem = JackJsonUtil.createObjectNode();
        pictureItem.put("type","图片");
        pictureItem.put("value",pictureCount);
        roseData.add(pictureItem);

        Long fileCount = fileCountFuture.exceptionally(f -> 0L).get();
        ObjectNode fileItem = JackJsonUtil.createObjectNode();
        fileItem.put("type","文件");
        fileItem.put("value",fileCount);
        roseData.add(fileItem);

        Long bookCount = bookCountFuture.exceptionally(f -> 0L).get();
        ObjectNode bookItem = JackJsonUtil.createObjectNode();
        bookItem.put("type","笔记簿");
        bookItem.put("value",bookCount);
        roseData.add(bookItem);

        Long notesCount = notesCountFuture.exceptionally(f -> 0L).get();
        ObjectNode notesItem = JackJsonUtil.createObjectNode();
        notesItem.put("type","笔记");
        notesItem.put("value",notesCount);
        roseData.add(notesItem);

        Long planCount = planCountFuture.exceptionally(f -> 0L).get();
        ObjectNode planItem = JackJsonUtil.createObjectNode();
        planItem.put("type","计划");
        planItem.put("value",planCount);
        roseData.add(planItem);

        Long newsCount = newsCountFuture.exceptionally(f -> 0L).get();
        ObjectNode newsItem = JackJsonUtil.createObjectNode();
        newsItem.put("type","动态");
        newsItem.put("value",newsCount);
        roseData.add(newsItem);
        result.put("rose", roseData);

        // 统计笔记簿
        bookEntity.setStartLine(0);
        bookEntity.setEndLine(bookCount.intValue());
        CompletableFuture<List<NoteBookEntity>> bookListFuture = CompletableFuture.supplyAsync(()->noteBookService.getNoteBook(bookEntity));
        List<NoteBookEntity> bookList = bookListFuture.exceptionally(f -> null).get();
        result.put("wordCloud", bookList);

        return ResultUtil.success(result);
    }

    /**
     * 查询活动率
     * @param queryMonth 所在月份的日期(2021-01-25格式)
     * @param request 当前用户会话信息
     * @return Result<Object> map对象
     * @throws Exception
     */
    @Override
    public Result<Object> activityRate(String queryMonth,HttpServletRequest request) throws Exception{
        Map<String, Object> result = new HashMap<>(4);
        try {
            LocalDate monthDate = LocalDate.parse(queryMonth, DateUtils.dateFormat);
            // 总天数，计算日均用
            int days;
            if(DateUtils.checkIsCurrentMonth(queryMonth)){
                // 是当前月，天数为已经过去的天数
                days = monthDate.getDayOfMonth();
            }else{
                days = monthDate.lengthOfMonth();
            }
            // 查询本月的活跃次数
            // 统计登录总数
            LogEntity logEntity = new LogEntity();
            UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
            logEntity.setUser(userSession.getUser());
            logEntity.setBeginTime(DateUtils.getFirstDayOfMonth(queryMonth));
            logEntity.setEndTime(DateUtils.getLastDayOfMonth(queryMonth));
            // 本月活跃总数
            Long currentActivityCount = logService.selectCount(logEntity);
            // 计算日均
            BigDecimal avgAccount = (BigDecimal.valueOf(currentActivityCount)).divide(new BigDecimal(days),4, RoundingMode.HALF_UP);
            // 近半年的活跃次数
            Map<String, Object> log6 = userService.countPre6Logs(userSession.getUser(), queryMonth);
            result.put("count",currentActivityCount);
            result.put("avg",avgAccount);
            result.put("log6",log6);
        } catch (Exception e) {
            result.put("count",0);
            result.put("avg",0);
            result.put("log6",null);
            CurrentLineInfo.printCurrentLineInfo("统计活动率时发生异常", e, CoreServiceImpl.class);
        }
        return ResultUtil.success(result);
    }
}
