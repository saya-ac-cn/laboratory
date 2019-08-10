package ac.cn.saya.laboratory.service.impl;

import ac.cn.saya.laboratory.entity.BackupLogEntity;
import ac.cn.saya.laboratory.entity.FilesEntity;
import ac.cn.saya.laboratory.entity.PictureEntity;
import ac.cn.saya.laboratory.exception.MyException;
import ac.cn.saya.laboratory.persistent.service.BackupLogService;
import ac.cn.saya.laboratory.persistent.service.FilesService;
import ac.cn.saya.laboratory.persistent.service.PictureStorageService;
import ac.cn.saya.laboratory.tools.Paging;
import ac.cn.saya.laboratory.tools.Result;
import ac.cn.saya.laboratory.tools.ResultEnum;
import ac.cn.saya.laboratory.tools.ResultUtil;
import ac.cn.saya.laboratory.service.IObjectStorageService;
import ac.cn.saya.laboratory.tools.UploadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * 对象存储服务实现类
 *
 * @Title: ObjectStorageServiceImpl
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2019/1/9 22:19
 * @Description:
 */
@Service("objectStorageServiceImpl")
public class ObjectStorageServiceImpl implements IObjectStorageService {

    private static Logger logger = LoggerFactory.getLogger(ObjectStorageServiceImpl.class);

    @Resource
    @Qualifier("recordService")//日志助手表
    private RecordService recordService;

    @Resource
    @Qualifier("pictureStorageService")
    private PictureStorageService pictureStorageService;

    @Resource
    @Qualifier("filesService")
    private FilesService filesService;

    @Resource
    @Qualifier("backupLogService")
    private BackupLogService backupLogService;

    /**
     * 上传动态、笔记（插图）图片服务
     *
     * @param entity
     * @param request
     * @return
     */
    @Override
    public Result<Object> updateNewsPicture(PictureEntity entity, HttpServletRequest request) throws Exception {
        Result<String> upload = UploadUtils.uploadPicture(entity.getFileurl(), "illustrated", request);
        if (upload.getCode() == 0) {
            //logo上传成功
            //得到文件上传成功的回传地址
            String successUrl = String.valueOf(upload.getData());
            //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
            String userSession = (String) request.getSession().getAttribute("user");
            entity.setSource(userSession);
            entity.setType(2);
            // 文件在服务器的存放目录
            entity.setFileurl(successUrl);
            // 浏览器可访问的url
            entity.setWeburl(UploadUtils.descUrl(successUrl));
            if (pictureStorageService.uploadPictureBase64(entity) > 0) {
                /**
                 * 记录日志
                 * 上传插图
                 */
                recordService.record("OX005", request);
                return ResultUtil.success(entity.getWeburl());
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
     * @描述 上传壁纸
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/13
     * @修改人和其它信息
     */
    @Override
    public Result<Object> updateWallpaperPicture(MultipartFile file, HttpServletRequest request) throws Exception {
        Result<String> upload = UploadUtils.uploadWallpaper(file, request);
        if (upload.getCode() == 0) {
            //logo上传成功
            //得到文件上传成功的回传地址
            String successUrl = String.valueOf(upload.getData());
            //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
            String userSession = (String) request.getSession().getAttribute("user");
            PictureEntity entity = new PictureEntity();
            // 原文件名称
            entity.setFilename(file.getOriginalFilename());
            entity.setSource(userSession);
            entity.setType(1);
            // 文件在服务器的存放目录
            entity.setFileurl(successUrl);
            // 浏览器可访问的url
            entity.setWeburl(UploadUtils.descUrl(successUrl));
            if (pictureStorageService.uploadPictureBase64(entity) > 0) {
                /**
                 * 记录日志
                 * 上传壁纸
                 */
                recordService.record("OX011", request);
                return ResultUtil.success(entity.getWeburl());
            } else {
                throw new MyException(ResultEnum.ERROP);
            }
        } else if (upload.getCode() == -2) {
            //不是有效的图片
            throw new MyException(ResultEnum.NOT_PARAMETER);
        } else if (upload.getCode() == -3) {
            //空文件
            throw new MyException(ResultEnum.NOT_PARAMETER);
        } else {
            // 图片上传异常
            throw new MyException(ResultEnum.ERROP);
        }
    }

    /**
     * @描述 删除base64类型的图片
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/12
     * @修改人和其它信息
     */
    @Override
    public Result<Object> deletePictuBase64(PictureEntity entity, HttpServletRequest request) throws Exception {
        // 校验用户输入的参数
        if (entity == null) {
            // 缺少参数
            throw new MyException(ResultEnum.NOT_PARAMETER);
        }
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        String userSession = (String) request.getSession().getAttribute("user");
        entity.setSource(userSession);
        PictureEntity result = pictureStorageService.getOnePictuBase64(entity);
        if (result == null || StringUtils.isEmpty(result.getFileurl())) {
            //未找到有效记录
            throw new MyException(ResultEnum.NOT_EXIST);
        } else {
            // 删除文件
            UploadUtils.deleteFile(result.getFileurl());
            if (pictureStorageService.deletePictuBase64(result) > 0) {
                /**
                 * 记录日志
                 */
                recordService.record("OX012", request);
                return ResultUtil.success();
            } else {
                throw new MyException(ResultEnum.ERROP);
            }
        }
    }

    /**
     * @描述 获取分页后的图片
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/13
     * @修改人和其它信息
     */
    @Override
    public Result<Object> getPictuBase64List(PictureEntity entity, HttpServletRequest request) throws Exception {
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
        Long pageSize = pictureStorageService.getPictuBase64Count(entity);
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
            List<PictureEntity> list = pictureStorageService.getPictuBase64Page(entity);
            paging.setGrid(list);
            return ResultUtil.success(paging);
        } else {
            //未找到有效记录
            throw new MyException(ResultEnum.NOT_EXIST);
        }
    }

    /**
     * @描述 获取单张图片信息
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/13
     * @修改人和其它信息
     */
    @Override
    public Result<Object> getOnePictuBase64(PictureEntity entity, HttpServletRequest request) throws Exception {
        if (entity == null || entity.getId() == null) {
            // 缺少参数
            throw new MyException(ResultEnum.NOT_PARAMETER);
        }
        PictureEntity result = pictureStorageService.getOnePictuBase64(entity);
        if (result == null) {
            //未找到有效记录
            throw new MyException(ResultEnum.NOT_EXIST);
        } else {
            return ResultUtil.success(result);
        }
    }

    /**
     * @param file
     * @param request
     * @描述 上传文件
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/15
     * @修改人和其它信息
     */
    @Override
    public Result<Object> uploadFile(MultipartFile file, HttpServletRequest request) throws Exception {
        Result<String> upload = UploadUtils.uploadFile(file, request);
        if (upload.getCode() == 0) {
            //logo上传成功
            //得到文件上传成功的回传地址
            String successUrl = String.valueOf(upload.getData());
            //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
            String userSession = (String) request.getSession().getAttribute("user");
            FilesEntity entity = new FilesEntity();
            // 原文件名称
            entity.setFilename(file.getOriginalFilename());
            entity.setSource(userSession);
            entity.setStatus("2");
            // 文件在服务器的存放目录
            entity.setFileurl(successUrl);
            if (filesService.insertFile(entity) > 0) {
                /**
                 * 记录日志
                 * 上传文件
                 */
                recordService.record("OX013", request);
                return ResultUtil.success();
            } else {
                throw new MyException(ResultEnum.ERROP);
            }
        } else if (upload.getCode() == -2 || upload.getCode() == -3) {
            // 不是有效的文件
            throw new MyException(ResultEnum.NOT_PARAMETER);
        } else {
            // 文件上传异常
            throw new MyException(ResultEnum.ERROP);
        }
    }

    /**
     * @param entity
     * @param request
     * @描述 修改文件信息
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/15
     * @修改人和其它信息
     */
    @Override
    public Result<Object> editFileInfo(FilesEntity entity, HttpServletRequest request) throws Exception {
        // 校验用户输入的参数
        if (entity == null) {
            // 缺少参数
            throw new MyException(ResultEnum.NOT_PARAMETER);
        }
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        String userSession = (String) request.getSession().getAttribute("user");
        entity.setSource(userSession);
        if (filesService.updateFile(entity) > 0) {
            /**
             * 记录日志
             */
            recordService.record("OX015", request);
            return ResultUtil.success();
        } else {
            throw new MyException(ResultEnum.ERROP);
        }
    }

    /**
     * @param entity
     * @param request
     * @描述 删除文件
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/15
     * @修改人和其它信息
     */
    @Override
    public Result<Object> deleteFile(FilesEntity entity, HttpServletRequest request) throws Exception {
        // 校验用户输入的参数
        if (entity == null) {
            // 缺少参数
            throw new MyException(ResultEnum.NOT_PARAMETER);
        }
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        String userSession = (String) request.getSession().getAttribute("user");
        entity.setSource(userSession);
        FilesEntity result = filesService.getOneFile(entity);
        if (result == null || StringUtils.isEmpty(result.getFileurl())) {
            // 未找到有效记录
            throw new MyException(ResultEnum.NOT_EXIST);
        } else {
            // 删除文件
            UploadUtils.deleteFile(result.getFileurl());
            if (filesService.deleteFile(result) > 0) {
                /**
                 * 记录日志
                 */
                recordService.record("OX014", request);
                return ResultUtil.success();
            } else {
                throw new MyException(ResultEnum.ERROP);
            }
        }
    }

    /**
     * @param entity
     * @param request
     * @描述 获取分页文件列表
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/15
     * @修改人和其它信息
     */
    @Override
    public Result<Object> getFileList(FilesEntity entity, HttpServletRequest request) throws Exception {
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
        Long pageSize = filesService.getFileCount(entity);
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
            List<FilesEntity> list = filesService.getFilePage(entity);
            paging.setGrid(list);
            return ResultUtil.success(paging);
        } else {
            //未找到有效记录
            throw new MyException(ResultEnum.NOT_EXIST);
        }
    }

    /**
     * @param id
     * @描述 管理员专用下载文件方法
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/15
     * @修改人和其它信息
     */
    @Override
    public Result<Object> downloadFileForAdmin(Integer id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (id == null) {
            // 缺少参数
            throw new MyException(ResultEnum.NOT_PARAMETER);
        }
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        String userSession = (String) request.getSession().getAttribute("user");
        FilesEntity queryEntity = new FilesEntity();
        queryEntity.setSource(userSession);
        queryEntity.setId(id);
        FilesEntity resultEntity = filesService.getOneFile(queryEntity);
        if (resultEntity == null || StringUtils.isEmpty(resultEntity.getFileurl())) {
            throw new MyException(ResultEnum.NOT_EXIST);
        } else {
            File thisFile = UploadUtils.getFilePath(resultEntity.getFileurl());
            if (thisFile == null) {
                // 文件不存在
                throw new MyException(ResultEnum.NOT_EXIST);
            }
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            OutputStream os = null;
            //且仅当此对象抽象路径名表示的文件或目录存在时，返回true
            response.setContentType("application/x-download");
            //控制下载文件的名字
            response.addHeader("Content-Disposition", "attachment;filename=" + resultEntity.getFilename());
            byte buf[] = new byte[1024];
            fis = new FileInputStream(thisFile);
            bis = new BufferedInputStream(fis);
            os = response.getOutputStream();
            int i = bis.read(buf);
            while (i != -1) {
                os.write(buf, 0, i);
                i = bis.read(buf);
            }
            os.close();
            bis.close();
            fis.close();
            return ResultUtil.success();
        }
    }

    /**
     * @param archiveDate
     * @param response
     * @描述 下载备份文件
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-03-02
     * @修改人和其它信息
     */
    @Override
    public Result<Object> downloadBackUpDB(String archiveDate, HttpServletResponse response) throws Exception {
        if (StringUtils.isEmpty(archiveDate)) {
            // 缺少参数
            throw new MyException(ResultEnum.NOT_PARAMETER);
        }
        BackupLogEntity queryEntity = new BackupLogEntity();
        queryEntity.setArchiveDate(archiveDate);
        BackupLogEntity resultEntity = backupLogService.getOneBackup(queryEntity);
        if (resultEntity == null || StringUtils.isEmpty(resultEntity.getUrl())) {
            throw new MyException(ResultEnum.NOT_EXIST);
        } else {
            File thisFile = UploadUtils.getFilePath(resultEntity.getUrl());
            if (thisFile == null) {
                // 文件不存在
                throw new MyException(ResultEnum.NOT_EXIST);
            }
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            OutputStream os = null;
            //且仅当此对象抽象路径名表示的文件或目录存在时，返回true
            response.setContentType("application/x-download");
            //控制下载文件的名字
            response.addHeader("Content-Disposition", "attachment;filename=" + archiveDate + ".sql");
            byte buf[] = new byte[1024];
            fis = new FileInputStream(thisFile);
            bis = new BufferedInputStream(fis);
            os = response.getOutputStream();
            int i = bis.read(buf);
            while (i != -1) {
                os.write(buf, 0, i);
                i = bis.read(buf);
            }
            os.close();
            bis.close();
            fis.close();
            return ResultUtil.success();
        }
    }

    /**
     * @param entity
     * @描述 获取分页的备份数据库列表
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-03-02
     * @修改人和其它信息
     */
    @Override
    public Result<Object> getBackUpDBList(BackupLogEntity entity) throws Exception {
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
        Long pageSize = backupLogService.getBackupCount(entity);
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
            List<BackupLogEntity> list = backupLogService.getBackupPagin(entity);
            paging.setGrid(list);
            return ResultUtil.success(paging);
        } else {
            //未找到有效记录
            throw new MyException(ResultEnum.NOT_EXIST);
        }
    }
}
