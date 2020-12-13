package ac.cn.saya.laboratory;

import ac.cn.saya.laboratory.service.impl.MailService;
import ac.cn.saya.laboratory.service.impl.SystemServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @Title: MailTest
 * @ProjectName laboratory
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2019-03-01 21:44
 * @Description:
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LaboratoryApplication.class)
public class MailTest {

    //@Autowired
    private MailService mailService;

    //@Resource
    private TemplateEngine templateEngine;

    //@Resource
    private SystemServiceImpl service;

    @Test
    public void mysqlDump() {
        service.backupDatabase();
    }


    @Test
    public void testSimpleMail() throws Exception {
        //创建邮件正文
        Context context = new Context();
        context.setVariable("executeTime", "2019-03-01 22:36:21");
        context.setVariable("executeResult", "成功");
        context.setVariable("saveUrl", "/324/35/346/");
        context.setVariable("sendTime", "2019-03-01 22:36:41");
        String emailContent = templateEngine.process("mail/backUpDB", context);
        //mailService.sendHtmlMail("pandoras@189.cn","数据库备份结果报告",emailContent);
    }


}
