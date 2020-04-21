package ac.cn.saya.laboratory.persistent.primary.dao;

import ac.cn.saya.laboratory.entity.FilesEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Title: FilesDAO
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2019/1/15 22:53
 * @Description:
 * 文件操作dao
 */
@Mapper
public interface FilesDAO {

    /***
     * @描述 添加文件上传记录
     * @参数  [entity]
     * @返回值  java.lang.Integer
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/15
     * @修改人和其它信息
     */
    public Integer insertFile(FilesEntity entity);

    /**
     * @描述 保存修改文件记录
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/15
     * @修改人和其它信息
     */
    public Integer updateFile(FilesEntity entity);

    /**
     * @描述 删除文件记录
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/15
     * @修改人和其它信息
     */
    public Integer deleteFile(FilesEntity entity);

    /**
     * @描述 查询分页后的文件列表
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/15
     * @修改人和其它信息
     */
    public List<FilesEntity> getFilePage(FilesEntity entity);

    /**
     * @描述 查询文件总数
     * @参数  [entity]
     * @返回值  java.lang.Long
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/15
     * @修改人和其它信息
     */
    public Long getFileCount(FilesEntity entity);

    /**
     * @描述 获取一条文件信息
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/15
     * @修改人和其它信息
     */
    public FilesEntity getOneFile(FilesEntity entity);

}
