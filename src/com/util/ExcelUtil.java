package com.util;


import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 导出Excel工具类
 */
public class ExcelUtil {

    /**
     * 通用的Excel文件创建方法
     * title:首行标题: 2015年度统计报表
     * sheets:sheet的tab标签页说明: 15年度报表
     * headers:表头：List存放表头  编号、姓名、备注
     * datas:数据行：list存放实体数据，map存放具体每一行数据，和headers对应。
     * rs:HttpServletResponse响应作用域，如果不为null，会直接将文件流输出到客户端，下载文件
     */
    public static void ExpExs(String title, String sheets, List headers, List<Map> datas, HttpServletResponse rs) {
        try {
            if (sheets == null || "".equals(sheets)) {
                sheets = "sheet";
            }
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet(sheets); //+workbook.getNumberOfSheets()
            HSSFRow row;
            HSSFCell cell;
            // 设置这些样式
            HSSFFont font = workbook.createFont();
            font.setFontName(HSSFFont.FONT_ARIAL);//字体
            font.setFontHeightInPoints((short) 16);//字号 
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
            //font.setColor(HSSFColor.BLUE.index);//颜色
            HSSFCellStyle cellStyle = workbook.createCellStyle(); //设置单元格样式
            cellStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
            cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            cellStyle.setFont(font);
            //产生表格标题行
            row = sheet.createRow(0);
            row.setHeightInPoints(20);
            for (int i = 0; i < headers.size(); i++) {
                HSSFRichTextString text = new HSSFRichTextString(headers.get(i).toString());
                cell = row.createCell(i);
                cell.setCellValue(text);
                cell.setCellStyle(cellStyle);
            }
            cellStyle = workbook.createCellStyle();
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            cellStyle.setDataFormat((short) 0x31);//设置显示格式，避免点击后变成科学计数法了
            cellStyle.setWrapText(true);//设置自动换行
            Map map;
            //遍历集合数据，产生数据行  
            for (int i = 0; i < datas.size(); i++) {
                row = sheet.createRow((i + 1));
                row.setHeightInPoints(20);
                map = datas.get(i);
                for (int j = 0; j < map.size(); j++) {
                    cell = row.createCell(j);
                    cell.setCellStyle(cellStyle);

                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    if (map.get(j) != null) {
                        cell.setCellValue(new HSSFRichTextString(map.get(j).toString()));
                    } else {
                        cell.setCellValue(new HSSFRichTextString(""));
                    }
                }
            }
            for (int i = 0; i < headers.size(); i++) {
                sheet.autoSizeColumn((short) i);
            }
            title = new String(title.concat(String.format("%1$tY%1$tm%1$td%1$tH%1$tM", new Date())).getBytes("gb2312"), "iso8859-1");
            rs.setHeader("Content-disposition", "attachment; filename=" + title + ".xls");
            rs.setContentType("application/vnd.ms-excel");
            ServletOutputStream outputStream = rs.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            System.out.println("#Error [" + e.getMessage() + "] ");
        }
    }
}