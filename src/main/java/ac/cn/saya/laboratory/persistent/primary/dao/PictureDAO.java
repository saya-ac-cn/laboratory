package ac.cn.saya.laboratory.persistent.primary.dao;

import ac.cn.saya.laboratory.entity.PictureEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Title: PictureDAO
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2019/1/10 23:06
 * @Description:
 * 图片上传DAO
 */

@Mapper
@Repository("pictureDAO")
public interface PictureDAO {

    /**
     * @描述 上传base64类型的图片
     * @参数  [entity]
     * @返回值  java.lang.Integer
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/10
     * @修改人和其它信息
     */
    public Integer insertPictuBase64(PictureEntity entity);

    /**
     * @描述 删除base64类型的图片
     * @参数
     * @返回值  
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/12
     * @修改人和其它信息
     */
    public Integer deletePictuBase64(PictureEntity entity);

    /**
     * @描述 查询分页后的图片
     * @参数
     * @返回值  
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/12
     * @修改人和其它信息
     */
    public List<PictureEntity> getPictuBase64Page(PictureEntity entity);

    /**
     * @描述 查询图片的总数
     * @参数
     * @返回值  
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/12
     * @修改人和其它信息
     */
    public Long getPictuBase64Count(PictureEntity entity);

    /**
     * @描述 查询一张图片
     * @参数
     * @返回值  
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/12
     * @修改人和其它信息
     */
    public PictureEntity getOnePictuBase64(PictureEntity entity);

}
