package com.cl.common.framework.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author BoSongsh
 * @create 2018-03-12 10:36
 **/
public class ExcelUtil {

    private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * 判断excel文件后缀名，生成不同的workbook
     */
    private static Workbook createWorkbook(InputStream is, String excelFileName) throws IOException {
        if (excelFileName.endsWith(".xls")) {
            return new HSSFWorkbook(is);
        } else if (excelFileName.endsWith(".xlsx")) {
            return new XSSFWorkbook(is);
        }
        return null;
    }

    /**
     * 根据sheet索引号获取对应的sheet
     */
    private static Sheet getSheet(Workbook workbook, int sheetIndex) {
        return workbook.getSheetAt(0);
    }


    public  static <V> List<V> importDataFromExcel(Class<V> cls, MultipartFile file) throws IOException, IllegalAccessException, InstantiationException {
        List<V> list = new ArrayList<V>(1000);
        InputStream is = file.getInputStream();
        String excelFileName = file.getOriginalFilename();
        try {
            //创建工作簿
            Workbook workbook = createWorkbook(is, excelFileName);
            //创建工作表sheet
            Sheet sheet = getSheet(workbook, 0);
            //获取sheet中数据的行数
            int rows = sheet.getPhysicalNumberOfRows();
            Row header = sheet.getRow(0);
            //获取表头单元格个数
            int cells = header.getPhysicalNumberOfCells();
            //利用反射，给JavaBean的属性进行赋值
            Field[] fields = cls.getDeclaredFields();

            Row row = null;
            int index;
            Cell cell;
            String value;
            Field field;
            V t = null;

//            Method setMethod;
            for (int i = 1; i < rows; i++) {//第一行为标题栏，从第二行开始取数据
                row = sheet.getRow(i);
                index = 0;
                t = cls.newInstance();
                while (index < cells) {
                    cell = row.getCell(index);
                    if (null == cell) {
                        cell = row.createCell(index);
                    }

                    cell.setCellType(CellType.STRING);
                    value = (null == cell.getStringCellValue()) ? "" : cell.getStringCellValue();

                    field = fields[index];
                    field.setAccessible(true);
                    field.set(t, value);
                    index++;
                }
                if (isHasValues(t)) {//判断对象属性是否有值
                    list.add(t);
                }

            }
//            logger.error("ExcelUtil.importDataFromExcel()异常：", e);
        } finally {
            try {
                is.close();//关闭流
            } catch (Exception e2) {
                logger.error("ExcelUtil.importDataFromExcel()关闭stream异常：", e2);
            }
        }
        return list;
    }

    /**
     * 将sheet中的数据保存到list中，
     * 1、调用此方法时，vo的属性个数必须和excel文件每行数据的列数相同且一一对应，vo的所有属性都为String
     * 2、在action调用此方法时，需声明
     * private File excelFile;上传的文件
     * private String excelFileName;原始文件的文件名
     * 3、页面的file控件name需对应File的文件名
     */
    public static List<Object> importDataFromExcel(Object vo, InputStream is, String excelFileName) {
        List<Object> list = new ArrayList<Object>(1000);
        try {
            //创建工作簿
            Workbook workbook = createWorkbook(is, excelFileName);
            //创建工作表sheet
            Sheet sheet = getSheet(workbook, 0);
            //获取sheet中数据的行数
            int rows = sheet.getPhysicalNumberOfRows();
            //获取表头单元格个数
            int cells = sheet.getRow(0).getPhysicalNumberOfCells();
            //利用反射，给JavaBean的属性进行赋值
            Field[] fields = vo.getClass().getDeclaredFields();
            Row row = null;
            int index;
            Cell cell;
            String value, fieldName, methodName;
            Field field;
            Method setMethod;
            for (int i = 1; i < rows; i++) {//第一行为标题栏，从第二行开始取数据
                row = sheet.getRow(i);
                index = 0;
                while (index < cells) {
                    cell = row.getCell(index);
                    if (null == cell) {
                        cell = row.createCell(index);
                    }
                    cell.setCellType(CellType.STRING);
                    value = null == cell.getStringCellValue() ? "" : cell.getStringCellValue();

                    field = fields[index];
                    fieldName = field.getName();
                    methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    setMethod = vo.getClass().getMethod(methodName, new Class[]{String.class});
                    setMethod.invoke(vo, new Object[]{value});
                    index++;
                }
                if (isHasValues(vo)) {//判断对象属性是否有值
                    list.add(vo);
                    vo = vo.getClass().getConstructor(new Class[]{}).newInstance(new Object[]{});//重新创建一个vo对象
                }

            }
        } catch (Exception e) {
            logger.error("ExcelUtil.importDataFromExcel()异常：", e);
        } finally {
            try {
                is.close();//关闭流
            } catch (Exception e2) {
                logger.error("ExcelUtil.importDataFromExcel()关闭stream异常：", e2);
            }
        }
        return list;

    }

    /**
     * 判断一个对象所有属性是否有值，如果一个属性有值(分空)，则返回true
     */
    private static boolean isHasValues(Object object) throws IllegalAccessException {
        Field[] fields = object.getClass().getDeclaredFields();
        boolean flag = false;
//        String fieldName, methodName;
//        Method getMethod;
        Field field;
        Object obj;
        for (int i = 0; i < fields.length; i++) {
            field = fields[i];
//            fieldName = field.getName();
            field.setAccessible(true);
            obj = field.get(object);
//            methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
//                getMethod = object.getClass().getMethod(methodName);
//                obj = getMethod.invoke(object);
            if (null != obj && !"".equals(obj)) {
                flag = true;
                break;
            }

        }
        return flag;

    }


    /**
     * 导出excel
     *
     * @param list
     * @param headers 标题，例：[{"name":"姓名"},{"age":"年龄"}]
     * @param title   sheet的名称
     * @param os
     * @param <T>
     */
    public static <T> void exportDataToExcel(List<T> list, Map<String, String> headers, String title, OutputStream os) throws IOException, NoSuchFieldException, IllegalAccessException {


        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        new HSSFWorkbook();
        new XSSFWorkbook();

        // 在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = workbook.createSheet(title);
        //设置表格默认列宽15个字节
        sheet.setDefaultColumnWidth(15);
        //生成一个样式
        HSSFCellStyle style = getCellStyle(workbook);
        //生成一个字体
        HSSFFont font = getFont(workbook);
        //把字体应用到当前样式
        style.setFont(font);

        // 创建第0行 也就是标题
        HSSFRow row = sheet.createRow(0);

        row.setHeight((short) 300); // 设置标题的高度


        // 表头 bengin
        HSSFCell cell = null;
        int headerSeize = headers.size();
        Iterator<String> h = headers.values().iterator();
        headers.values();
        HSSFRichTextString text;
        for (int i = 0; i < headerSeize; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(style);
            text = new HSSFRichTextString(h.next());
            cell.setCellValue(text);
        }
        // 表头 end

        Object value = null;
//        Method getMethod = null;
        Field field = null;
//        String fieldName, methodName;
        Object[] fieldNames = headers.keySet().toArray();
        Class cls = null;
        T t;
        int dataSize = list.size();
        //将数据放入sheet中
        for (int i = 0; i < dataSize; i++) {
            row = sheet.createRow(i + 1);
            t = list.get(i);
            cls = t.getClass();
            //利用反射，根据JavaBean属性的先后顺序，动态调用get方法得到属性的值
            for (int j = 0; j < headerSeize; j++) {
                field = cls.getDeclaredField(fieldNames[j].toString());
                field.setAccessible(true);
                cell = row.createCell(j);
                value = field.get(t);
                if (null == value) {
                    value = "";
                }
                cell.setCellValue(value.toString());
            }
        }
        try {
            workbook.write(os);
        } finally {
            os.flush();
//            try {
//                os.flush();
//                os.close();
//            } catch (IOException e) {
//                logger.error("", e);
//            }
        }

    }

    /**
     * 获取单元格格式
     */

    private static HSSFCellStyle getCellStyle(HSSFWorkbook workbook) {
        HSSFCellStyle style = workbook.createCellStyle();
//        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        // 背景颜色
//        style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.DARK_BLUE.getIndex());


//        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        style.setBorderBottom(BorderStyle.THIN);
//        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
//        style.setBorderTop(BorderStyle.THIN);
//        style.setLeftBorderColor(HSSFCellStyle.BORDER_THIN);
        style.setLeftBorderColor(BorderStyle.THIN.getCode());
//        style.setRightBorderColor(HSSFCellStyle.BORDER_THIN);
        style.setRightBorderColor(BorderStyle.THIN.getCode());
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 文本居中
        style.setAlignment(HorizontalAlignment.CENTER);

        return style;
    }

    /**
     * 生成字体样式
     */
    private static HSSFFont getFont(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
//        font.setColor(HSSFColor.WHITE.index);
//        font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
//        font.setFontHeightInPoints((short) 12);
//        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setBold(true);
        return font;
    }

   /* public boolean isIE(HttpServletRequest request){
        return request.getHeader("USER-AGENT").toLowerCase().indexOf("msie")>0?true:false;
    }*/

    public static void main(String[] args) {

     /*   List<FollowUpReport> list = new ArrayList<FollowUpReport>();
        FollowUpReport e1 = new FollowUpReport("No001", "15821870000", "周嘻嘻", "8011", "关机", new Date(), "不和你完了");
        FollowUpReport e2 = new FollowUpReport("No002", "15821870001", "wahaha", "8012", "关机", new Date(), "无UUUu");
        list.add(e1);
        list.add(e2);

        Map<String, String> headers = new HashMap<String, String>(10);
        headers.put("userNo", "客户ID");
        headers.put("mobile", "手机");
        headers.put("customerName", "客户姓名");
        headers.put("seatNo", "坐席工号");
        headers.put("status", "装填");
        headers.put("createDate", "跟进时间");
        headers.put("content", "跟进内容");


        String title = "测试excel生成";
        try (OutputStream os = new FileOutputStream("test.xls")) {

            new ExcelUtil().exportDataToExcel(list, headers, title, os);

        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
