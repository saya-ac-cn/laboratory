package ac.cn.saya.laboratory.controller;

import ac.cn.saya.laboratory.entity.UserEntity;
import ac.cn.saya.laboratory.service.ICoreService;
import ac.cn.saya.laboratory.tools.Result;
import ac.cn.saya.laboratory.tools.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Title: ExposeController
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/9/20 23:13
 * @Description:
 * 主动抛出外漏的接口和方法，无需授权认证（注销除外）
 */

@RestController
public class ExposeController {

    @Autowired()
    @Qualifier("coreServiceImpl")
    private ICoreService coreServiceImpl;


    /**
     * 登录
     * @return
     */
    @PostMapping("/backend/login")
    public Result<Object> login(@RequestBody UserEntity user, HttpServletRequest request) throws Exception
    {
        return coreServiceImpl.login(user, request);
    }

    /**
     * 下载文件
     * @param url
     * @param request
     * @param response
     */
    @GetMapping("/backend/download/channel-1")
    public void fileDownload (@RequestParam(value = "url") String url,HttpServletRequest request, HttpServletResponse response){
        System.out.println(ClassUtils.getDefaultClassLoader().getResource("").getPath());
        coreServiceImpl.fileDownload(url,request,response);
    }

    /**
     * 用户退出
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/backend/logout")
    public Result<Object> logout(HttpServletRequest httpServletRequest) throws Exception
    {
        return coreServiceImpl.logout(httpServletRequest);
    }


}
