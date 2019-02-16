package ac.cn.saya.laboratory.tools;


import com.alibaba.fastjson.JSONObject;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import java.util.List;

/**
 * @Title: OutExcelUtils
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/10/21 22:13
 * @Description:
 * 导出到Excel工具类
 */

public class OutExcelUtils {

    private static Logger logger = LoggerFactory.getLogger(OutExcelUtils.class);

    /**
     * 导出到xlsx文件，这里是单行标题不含嵌套
     * @param titles
     * @param out
     * @param jsonObjectList
     */
    public static void outExcelTemplateSimple(String[] keys,String[] titles, List<JSONObject> jsonObjectList, ServletOutputStream out)
    {
        try{
            // 第一步，创建一个workbook，对应一个Excel文件
            SXSSFWorkbook workbook=new SXSSFWorkbook ();
            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
            SXSSFSheet sheet = workbook.createSheet("sheet1");
            // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
            SXSSFRow row = sheet.createRow(0);
            //第四步，把字段名放置到第一行中去
            SXSSFCell cell = null;
            for (int i = 0; i < titles.length; i++) {
                //列索引从0开始
                cell = row.createCell(i);
                //列名1
                cell.setCellValue(titles[i]);
            }
            //第五步，插入数据
            for(int i = 1;i <= jsonObjectList.size();i++)
            {
                // 建立新行
                SXSSFRow nextrow = sheet.createRow(i);
                JSONObject job = (JSONObject) jsonObjectList.get(i-1);
                for (int j = 0; j < job.size(); j++) {
                    // 新建一列
                    SXSSFCell nestCell = nextrow.createCell(j);
                    Object t = job.getString(keys[j]);
                    if(t!=null){
                        nestCell.setCellValue(t.toString());
                    }else{
                        nestCell.setCellValue("");
                    }
                }
            }
            // 第六步，将文件输出到客户端浏览器
            workbook.write(out);
            out.flush();
            out.close();
        }catch(Exception e){
            logger.error("导出Excel失败"+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
    }

}
