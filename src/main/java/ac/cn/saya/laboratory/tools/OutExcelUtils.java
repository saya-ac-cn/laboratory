package ac.cn.saya.laboratory.tools;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

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

    /**
     * 导出到xlsx文件，这里是单行标题不含嵌套，合并
     * @param titles
     * @param out
     * @param jsonObjectList
     */
    public static void outExcelTemplateSimple(String[] keys,String[] titles, List<JSONObject> jsonObjectList, ServletOutputStream out) {
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
            for(int i = 1;i <= jsonObjectList.size();i++){
                // 建立新行
                SXSSFRow nextRow = sheet.createRow(i);
                JSONObject job = jsonObjectList.get(i-1);
                writeCellDataForKey(nextRow,keys,job,0);
            }
            // 第六步，将文件输出到客户端浏览器
            workbook.write(out);
            out.flush();
            out.close();
        }catch(Exception e){
            CurrentLineInfo.printCurrentLineInfo("导出Excel失败",e, OutExcelUtils.class);
        }
    }

    /**
     * 导出到xlsx文件，这里是复杂的表格，含合并
     * @param rootKeys 主数据key（最外层jsonObjectList的属性key）
     * @param childKeys 子数据key（下标0表示在最外层jsonObjectList的属性key，其它的表示在这个属性下的key）
     * @param titles 表格标题
     * @param jsonObjectList 要渲染的数据集合
     * @param out 输出流
     */
    public static void outExcelTemplatePlus(String[] rootKeys,String[] childKeys,String[] titles, List<JSONObject> jsonObjectList, ServletOutputStream out) {
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
            int rowNum = 1;
            for(int i = 1;i <= jsonObjectList.size();i++){
                JSONObject rootLine = jsonObjectList.get(i-1);
                JSONArray childLine = rootLine.getJSONArray(childKeys[0]);
                int childSize = 0;
                if (null == childLine || 0 == (childSize = childLine.size())){
                    // 不存在子数据集合,直接写入rootKeys中的数据
                    SXSSFRow nextRow = sheet.createRow(rowNum);
                    writeCellDataForKey(nextRow,rootKeys,rootLine,0);
                    rowNum++;
                    continue;
                }
                if (childSize> 1) {
                    // 有多行的情况，部分单元格列rootKeys需要合并
                    for (int j = 0; j < rootKeys.length; j++) {
                        // 起始行,终止行,起始列,终止列
                        CellRangeAddress cra = new CellRangeAddress(rowNum, rowNum + childSize - 1, j, j);
                        sheet.addMergedRegion(cra);
                    }
                }
                for (int j = 0; j < childSize; j++){
                    // 建立新行
                    SXSSFRow nextRow = sheet.createRow(rowNum);
                    // 填入左边6列，固定列数据
                    if (0 == j) {
                        // 写入左边rootKeys列数据
                        writeCellDataForKey(nextRow,rootKeys,rootLine,0);
                    }
                    JSONObject child = childLine.getJSONObject(j);
                    for (int k = 1; k < childKeys.length; k++) {
                        // 新建一列
                        SXSSFCell nestCell = nextRow.createCell(rootKeys.length+k-1);
                        Object t = child.getString(childKeys[k]);
                        if(t!=null){
                            nestCell.setCellValue(t.toString());
                        }else{
                            nestCell.setCellValue("");
                        }
                    }
                    rowNum++;
                }
            }
            // 第六步，将文件输出到客户端浏览器
            workbook.write(out);
            out.flush();
            out.close();
        }catch (Exception e){
            CurrentLineInfo.printCurrentLineInfo("导出Excel失败",e, OutExcelUtils.class);
        }
    }

    /**
     * 通过给定的keys，写入单元格数据
     * @param nextRow 本行单元格操作对象
     * @param keys 要写入的键
     * @param data 要写入的数据
     * @param startIndex 开始列
     */
    private static void writeCellDataForKey(SXSSFRow nextRow,String[] keys,JSONObject data, int startIndex){
        for (int i = 0; i < keys.length; i++) {
            // 新建一列
            SXSSFCell nestCell = nextRow.createCell(startIndex+i);
            Object t = data.getString(keys[i]);
            if(t!=null){
                nestCell.setCellValue(t.toString());
            }else{
                nestCell.setCellValue("");
            }
        }
    }

}
