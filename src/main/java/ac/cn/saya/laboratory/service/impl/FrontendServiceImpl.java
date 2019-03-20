package ac.cn.saya.laboratory.service.impl;

import ac.cn.saya.laboratory.entity.FilesEntity;
import ac.cn.saya.laboratory.entity.NewsEntity;
import ac.cn.saya.laboratory.exception.MyException;
import ac.cn.saya.laboratory.persistent.service.FilesService;
import ac.cn.saya.laboratory.persistent.service.NewsService;
import ac.cn.saya.laboratory.service.IFrontendService;
import ac.cn.saya.laboratory.tools.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: FrontendServiceImpl
 * @ProjectName laboratory
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2019-03-18 22:51
 * @Description:
 */
@Service("frontendServiceImpl")
public class FrontendServiceImpl implements IFrontendService {

    private static Logger logger = LoggerFactory.getLogger(FrontendServiceImpl.class);

    @Resource
    @Qualifier("newsService")
    private NewsService newsService;

    @Resource
    @Qualifier("filesService")
    private FilesService filesService;

    /**
     * @param entity
     * @描述 查询一条动态
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/12
     * @修改人和其它信息
     */
    @Override
    public Result<Object> getOneNews(NewsEntity entity) throws Exception {
        if(entity == null || entity.getId() == null){
            // 缺少参数
            throw new MyException(ResultEnum.NOT_PARAMETER);
        }
        NewsEntity result = newsService.getOneNews(entity);
        if(result == null){
            //未找到有效记录
            throw new MyException(ResultEnum.NOT_EXIST);
        }else{
            // 寻找上一条和下一条
            Map<String,String> preAndNext = newsService.getNewsPreAndNext(entity.getId());
            Map<String,NewsEntity> out = new HashMap();
            out.put("now",result);
            if(preAndNext != null){
                for (Map.Entry<String,String> item : preAndNext.entrySet()) {
                    entity.setId(Integer.valueOf(item.getValue()));
                    result = newsService.getOneNews(entity);
                    // 取出不必要的数据传输
                    result.setContent(null);
                    result.setLabel(null);
                    out.put(item.getKey(),result);
                }
            }
            return ResultUtil.success(out);
        }
    }

    /**
     * @param entity
     * @描述 获取分页的动态
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    @Override
    public Result<Object> getNewsList(NewsEntity entity) throws Exception {
        Paging paging =new Paging();
        if(entity.getNowPage() == null){
            entity.setNowPage(1);
        }
        if(entity.getPageSize() == null){
            entity.setPageSize(20);
        }
        //每页显示记录的数量
        paging.setPageSize(entity.getPageSize());
        //获取满足条件的总记录（不分页）
        Long pageSize = newsService.getNewsCount(entity);
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
            List<NewsEntity> list = newsService.getNewsPage(entity);
            paging.setGrid(list);
            return ResultUtil.success(paging);
        }else{
            //未找到有效记录
            throw new MyException(ResultEnum.NOT_EXIST);
        }
    }

    /**
     * @param entity
     * @描述 获取分页文件列表
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-03-20
     * @修改人和其它信息
     */
    @Override
    public Result<Object> getFileList(FilesEntity entity) throws Exception {
        Paging paging =new Paging();
        if(entity.getNowPage() == null){
            entity.setNowPage(1);
        }
        if(entity.getPageSize() == null){
            entity.setPageSize(20);
        }
        //每页显示记录的数量
        paging.setPageSize(entity.getPageSize());
        //获取满足条件的总记录（不分页）
        Long pageSize = filesService.getFileCount(entity);
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
            List<FilesEntity> list = filesService.getFilePage(entity);
            paging.setGrid(list);
            return ResultUtil.success(paging);
        }else{
            //未找到有效记录
            throw new MyException(ResultEnum.NOT_EXIST);
        }
    }

    /**
     * @param id
     * @param request
     * @param response
     * @描述 下载文件
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-03-20
     * @修改人和其它信息
     */
    @Override
    public Result<Object> downloadFile(String user,Integer id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(id == null ){
            // 缺少参数
            throw new MyException(ResultEnum.NOT_PARAMETER);
        }
        FilesEntity queryEntity = new FilesEntity();
        queryEntity.setId(id);
        queryEntity.setSource(user);
        FilesEntity resultEntity = filesService.getOneFile(queryEntity);
        if(resultEntity == null || StringUtils.isEmpty(resultEntity.getFileurl())){
            throw new MyException(ResultEnum.NOT_EXIST);
        }else{
            File thisFile = UploadUtils.getFilePath(resultEntity.getFileurl());
            if(thisFile == null){
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
            while(i!=-1){
                os.write(buf,0,i);
                i = bis.read(buf);
            }
            os.close();
            bis.close();
            fis.close();
            return ResultUtil.success();
        }
    }
}
