package ac.cn.saya.laboratory.tools;


import com.alibaba.fastjson.JSONObject;
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
            for(int i = 1;i <= jsonObjectList.size();i++)
            {
                // 建立新行
                SXSSFRow nextrow = sheet.createRow(i);
                JSONObject job = jsonObjectList.get(i-1);
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
            CurrentLineInfo.printCurrentLineInfo("导出Excel失败",e, OutExcelUtils.class);
        }
    }

    /**
     *  导出稽核结果表
     * @param billList
     * @return
     */
    public XSSFWorkbook outValidationList(List<Bill> billList){
        if (billList.isEmpty()) {
            throw new RuntimeException("没有可导出的数据");
        }
        XSSFWorkbook workBook = new XSSFWorkbook();
        XSSFSheet sheet = workBook.createSheet("塔维账单稽核结果表");
        ExportStyleUtil styleUtil = new ExportStyleUtil(workBook, sheet);
        XSSFCellStyle headStyle = styleUtil.getHeadStyle();
        XSSFRow headRow = sheet.createRow(0);
        String[] titles = new String[]{"区域", "报账点名称","报账点编码","缴费期始","缴费期终","申请报账日期", "稽核规则", "稽核结果", "稽核描述", "稽核详情"};
        XSSFCell cell = null;
        for (int i = 0; i < titles.length; i++) {
            cell = headRow.createCell(i);
            cell.setCellStyle(headStyle);
            cell.setCellValue(titles[i]);
        }
        int rowNum = 1;
        Bill billItem = null;
        for (int j = 0; j < billList.size(); j++){
            billItem = billList.get(j);
            if (Objects.isNull(billItem)) {
                continue;
            }
            List<ValidationRecord> validationList = billItem.getValidationRecordList();
            int validationSize = validationList.size();
            if (validationList.isEmpty()) {
                XSSFRow bodyRow = sheet.createRow(rowNum);
                // 写入单行数据
                this.writrLeftFixedColumn(billItem,styleUtil,bodyRow);
                rowNum++;
                continue;
            }
            if (validationSize > 1) {
                // 有多行的情况，部分单元格列（左边6列，"区域", "报账点名称","报账点编码","缴费期始","缴费期终","申请报账日期"）需要合并
                for (int i = 0; i <= 5; i++) {
                    // 起始行,终止行,起始列,终止列
                    CellRangeAddress cra = new CellRangeAddress(rowNum, rowNum + validationSize - 1, i, i);
                    sheet.addMergedRegion(cra);
                }
            }
            for (int i = 0; i < validationSize; i++){
                XSSFRow bodyRow = sheet.createRow(rowNum);
                // 填入左边6列，固定列数据
                if (i == 0) {
                    // 写入左边固定列
                    this.writrLeftFixedColumn(billItem,styleUtil,bodyRow);
                }
                ValidationRecord validation = validationList.get(i);
                if (Objects.isNull(validation)) {
                    styleUtil.writeCellWithStyle(bodyRow, 6, "-");
                    styleUtil.writeCellWithStyle(bodyRow, 7, "-");
                    styleUtil.writeCellWithStyle(bodyRow, 8, "-");
                    styleUtil.writeCellWithStyle(bodyRow, 9, "-");
                }else{
                    styleUtil.writeCellWithStyle(bodyRow, 6,!Objects.isNull(validation.getRuleName())?validation.getRuleName():"-");
                    styleUtil.writeCellWithStyle(bodyRow, 7,validation.getResult()?"通过":"未通过");
                    styleUtil.writeCellWithStyle(bodyRow, 8,!Objects.isNull(validation.getDescription())?validation.getDescription():"-");
                    styleUtil.writeCellWithStyle(bodyRow, 9,!Objects.isNull(validation.getDeatil())?validation.getDeatil():"-");
                }
                rowNum++;
            }
        }
        return workBook;
    }

}
