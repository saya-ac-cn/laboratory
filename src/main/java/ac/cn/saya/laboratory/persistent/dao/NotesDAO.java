package ac.cn.saya.laboratory.persistent.dao;

import ac.cn.saya.laboratory.entity.NoteBookEntity;
import ac.cn.saya.laboratory.entity.NotesEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Title: NotesDAO
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2019/1/10 23:06
 * @Description:
 * 动态DAO
 */

@Mapper
@Repository("notesDAO")
public interface NotesDAO {

    /**
     * @描述 新增笔记
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/11
     * @修改人和其它信息
     */
    public Integer insertNotes(NotesEntity entity);

    /**
     * @描述 编辑笔记
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/11
     * @修改人和其它信息
     */
    public Integer updateNotes(NotesEntity entity);

    /**
     * @描述  删除笔记
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/11
     * @修改人和其它信息
     */
    public Integer deleteNotes(NotesEntity entity);

    /**
     * @描述 查询一条笔记
     * @参数  
     * @返回值  
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/12
     * @修改人和其它信息
     */
    public NotesEntity getOneNotes(NotesEntity entity);

    /**
     * @描述 获取分页后的笔记
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/11
     * @修改人和其它信息
     */
    public List<NotesEntity> getNotesPage(NotesEntity entity);

    /**
     * @描述 获取笔记总数
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/11
     * @修改人和其它信息
     */
    public Long getNotesCount(NotesEntity entity);

}
