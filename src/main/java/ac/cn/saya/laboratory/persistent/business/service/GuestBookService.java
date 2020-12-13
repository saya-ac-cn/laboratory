package ac.cn.saya.laboratory.persistent.business.service;

import ac.cn.saya.laboratory.entity.GuestBookEntity;
import ac.cn.saya.laboratory.exception.MyException;
import ac.cn.saya.laboratory.persistent.business.dao.GuestBookDAO;
import ac.cn.saya.laboratory.tools.CurrentLineInfo;
import ac.cn.saya.laboratory.tools.ResultEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: NewsService
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2019/1/10 23:03
 * @Description: 留言dubbo
 */

@Service("guestBookService")
@Transactional(value = "primaryTransactionManager", readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = MyException.class)
public class GuestBookService {

    @Resource
    private GuestBookDAO guestBookDAO;

    /**
     * @描述 留言
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    public Integer insertGuestBook(GuestBookEntity entity) {
        try {
            return guestBookDAO.insertGuestBook(entity);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("用户留言异常", e, GuestBookService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 审核修改
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    public Integer updateGuestBook(GuestBookEntity entity) {
        try {
            return guestBookDAO.updateGuestBook(entity);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("审核修改留言异常", e, GuestBookService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 查询一条留言
     * @参数 [entity]
     * @返回值 ac.cn.saya.datacenter.entity.NewsEntity
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/12
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    public GuestBookEntity queryOneGuestBook(GuestBookEntity entity) {
        try {
            return guestBookDAO.getOneGuestBook(entity);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("查询动态异常", e, GuestBookService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 获取分页后的留言
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    public List<GuestBookEntity> getGuestBookPage(GuestBookEntity entity) {
        List<GuestBookEntity> list = new ArrayList<>();
        try {
            list = guestBookDAO.getGuestBookPage(entity);
            if (list.size() <= 0) {
                list = null;
            }
            return list;
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("获取分页后的留言发生异常", e, GuestBookService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 获取留言总数
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    public Long getGuestBookCount(GuestBookEntity entity) {
        try {
            return guestBookDAO.getGuestBookCount(entity);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("获取留言总数异常", e, GuestBookService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

}
