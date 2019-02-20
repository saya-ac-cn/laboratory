package ac.cn.saya.laboratory.service;

import ac.cn.saya.laboratory.entity.FilesEntity;
import ac.cn.saya.laboratory.entity.PictureEntity;
import ac.cn.saya.laboratory.tools.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 对象存储服务
 * @Title: IObjectStorageService
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2019/1/9 22:14
 * @Description:
 */

public interface IObjectStorageService {

    /**
     * 上传动态、笔记图片服务
     * @param entity
     * @param request
     * @return
     */
    public Result<Object> updateNewsPicture(PictureEntity entity, HttpServletRequest request) throws Exception;

    /**
     * @描述 上传壁纸
     * @参数
     * @返回值  
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/13
     * @修改人和其它信息
     */
    public Result<Object> updateWallpaperPicture(MultipartFile file, HttpServletRequest request) throws Exception;


    /**
     * @描述 删除base64类型的图片
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/12
     * @修改人和其它信息
     */
    public Result<Object> deletePictuBase64(PictureEntity entity, HttpServletRequest request) throws Exception;

    /**
     * @描述 获取分页后的图片
     * @参数  
     * @返回值  
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/13
     * @修改人和其它信息
     */
    public Result<Object> getPictuBase64List(PictureEntity entity, HttpServletRequest request) throws Exception;

    /**
     * @描述 获取单张图片信息
     * @参数
     * @返回值  
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/13
     * @修改人和其它信息
     */
    public Result<Object> getOnePictuBase64(PictureEntity entity, HttpServletRequest request) throws Exception;

    /**
     * @描述 上传文件
     * @参数  
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/15
     * @修改人和其它信息
     */
    public Result<Object> uploadFile(MultipartFile file, HttpServletRequest request) throws  Exception;

    /**
     * @描述 修改文件信息
     * @参数
     * @返回值  
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/15
     * @修改人和其它信息
     */
    public Result<Object> editFileInfo(FilesEntity entity, HttpServletRequest request) throws  Exception;

    /**
     * @描述 删除文件
     * @参数
     * @返回值  
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/15
     * @修改人和其它信息
     */
    public Result<Object> deleteFile(FilesEntity entity, HttpServletRequest request) throws  Exception;

    /**
     * @描述 获取分页文件列表
     * @参数  
     * @返回值  
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/15
     * @修改人和其它信息
     */
    public Result<Object> getFileList(FilesEntity entity, HttpServletRequest request) throws  Exception;

    /**
     * @描述 下载文件
     * @参数
     * @返回值  
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/15
     * @修改人和其它信息
     */
    public Result<Object> downloadFileForAdmin(Integer id, HttpServletRequest request, HttpServletResponse response) throws  Exception;

}
