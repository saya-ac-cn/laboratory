package ac.cn.saya.laboratory.controller;

import ac.cn.saya.laboratory.entity.BackupLogEntity;
import ac.cn.saya.laboratory.entity.FilesEntity;
import ac.cn.saya.laboratory.entity.PictureEntity;
import ac.cn.saya.laboratory.service.impl.ObjectStorageServiceImpl;
import ac.cn.saya.laboratory.tools.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 对象存储控制器
 *
 * @Title: ObjectStorageController
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2019/1/9 22:26
 * @Description:
 */

@RestController
@RequestMapping(value = "/backend/api/oss")
public class ObjectStorageController {

    @Autowired
    @Qualifier("objectStorageServiceImpl")
    private ObjectStorageServiceImpl objectStorageServiceImpl;

    /**
     * 上传笔记、动态图片(插图)
     *
     * @param entity
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping(value = "picture/illustrated")
    public Result<Object> updateNewsPicture(@RequestBody PictureEntity entity, HttpServletRequest request) throws Exception {
        return objectStorageServiceImpl.updateNewsPicture(entity, request);
    }

    /**
     * @描述 上传壁纸
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/13
     * @修改人和其它信息
     */
    @PostMapping(value = "picture/wallpaper")
    public Result<Object> updateWallpaperPicture(@RequestBody MultipartFile file, HttpServletRequest request) throws Exception {
        return objectStorageServiceImpl.updateWallpaperPicture(file, request);
    }

    /**
     * @描述 删除图片
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/13
     * @修改人和其它信息
     */
    @DeleteMapping(value = "picture/delete")
    public Result<Object> deletePictuBase64(PictureEntity entity, HttpServletRequest request) throws Exception {
        return objectStorageServiceImpl.deletePictuBase64(entity, request);
    }

    /**
     * @描述 查看分页图片
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/13
     * @修改人和其它信息
     */
    @GetMapping(value = "picture")
    public Result<Object> getPictuBase64List(PictureEntity entity, HttpServletRequest request) throws Exception {
        return objectStorageServiceImpl.getPictuBase64List(entity, request);
    }

    /**
     * @描述 查看单张图片
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/13
     * @修改人和其它信息
     */
    @GetMapping(value = "picture/show")
    public Result<Object> getOnePictuBase64(PictureEntity entity, HttpServletRequest request) throws Exception {
        return objectStorageServiceImpl.getOnePictuBase64(entity, request);
    }

    /**
     * @描述 获取文件列表
     * @参数 [entity, request]
     * @返回值 ac.cn.saya.datacenter.tools.Result<java.lang.Object>
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/18
     * @修改人和其它信息
     */
    @GetMapping(value = "files")
    public Result<Object> getFileList(FilesEntity entity, HttpServletRequest request) throws Exception {
        return objectStorageServiceImpl.getFileList(entity, request);
    }

    /**
     * @描述 上传文件
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/18
     * @修改人和其它信息
     */
    @PostMapping(value = "files/upload")
    public Result<Object> uploadFile(@RequestBody MultipartFile file, @RequestParam(value = "uid", required = false) String uid, HttpServletRequest request) throws Exception {
        return objectStorageServiceImpl.uploadFile(file, uid, request);
    }

    /**
     * @描述 修改文件
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/18
     * @修改人和其它信息
     */
    @PutMapping(value = "files/edit")
    public Result<Object> editFile(@RequestBody FilesEntity entity, HttpServletRequest request) throws Exception {
        return objectStorageServiceImpl.editFileInfo(entity, request);
    }

    /**
     * @描述 删除文件
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/18
     * @修改人和其它信息
     */
    @DeleteMapping(value = "files/delete")
    public Result<Object> deleteFile(FilesEntity entity, HttpServletRequest request) throws Exception {
        return objectStorageServiceImpl.deleteFile(entity, request);
    }

    /**
     * @描述 下载普通文件
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-03-02
     * @修改人和其它信息
     */
    @GetMapping(value = "files/download/{id}")
    public Result<Object> downloadFileForAdmin(@PathVariable("id") int id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return objectStorageServiceImpl.downloadFileForAdmin(id, request, response);
    }

    /**
     * @描述 下载数据库备份文件
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-03-02
     * @修改人和其它信息
     */
    @GetMapping(value = "db/download/")
    public Result<Object> downloadBackUpDB(@RequestParam("archiveDate") String archiveDate, HttpServletResponse response) throws Exception {
        return objectStorageServiceImpl.downloadBackUpDB(archiveDate, response);
    }

    /**
     * @描述 获取数据库备份日志列表
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-03-02
     * @修改人和其它信息
     */
    @GetMapping(value = "db")
    public Result<Object> getBackUpDBList(BackupLogEntity entity) throws Exception {
        return objectStorageServiceImpl.getBackUpDBList(entity);
    }

}
