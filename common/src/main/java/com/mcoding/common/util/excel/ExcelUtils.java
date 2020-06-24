package com.mcoding.common.util.excel;

import cn.hutool.core.util.ReflectUtil;
import com.mcoding.common.util.reflect.ReflectUtils;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * excel导入导出工具
 *
 * @author hzy
 */
public class ExcelUtils {

    /**
     * 把数据导出到excel表
     *
     * @param os                excel表的导出 流
     * @param titleAndModelKeys 表头与数据的关联,不能为空。例如：{ {"序号", "id"}}， “序号”是导出的excel表的表头，“id”是导入data数据的key
     * @param data              导出的数据
     * @param sheetTitle        title名
     * @param sheetIndex        title的索引 表示在第几行
     * @return
     * @throws IOException
     * @throws WriteException
     * @throws RowsExceededException
     * @throws ParseException
     */
    public static WritableWorkbook exportDataToExcel(OutputStream os,
                                                     List<TitleAndModelKey> titleAndModelKeys, List<? extends Object> data, String sheetTitle,
                                                     String headTitle, int sheetIndex) throws Exception {
        return exportDataToExcel(os, titleAndModelKeys, data, sheetTitle, headTitle, sheetIndex, null);
    }

    /**
     * 把数据导出到excel表
     *
     * @param os          excel表的导出 流
     * @param recordClass 拥有元数据信息的类
     * @param data        导出的数据
     * @param sheetTitle  title名
     * @param sheetIndex  title的索引 表示在第几行
     * @return
     * @throws IOException
     * @throws WriteException
     * @throws RowsExceededException
     * @throws ParseException
     */
    public static WritableWorkbook exportDataToExcel(OutputStream os,
                                                     Class<?> recordClass, List<? extends Object> data, String sheetTitle,
                                                     String headTitle, int sheetIndex) throws Exception {

        List<TitleAndModelKey> titleAndModelKeys = createTitleAndModelKeyList(recordClass);

        return exportDataToExcel(os, titleAndModelKeys, data, sheetTitle, headTitle, sheetIndex, null);
    }

    /**
     * 把数据导出到excel表
     *
     * @param os                excel表的导出 流
     * @param titleAndModelKeys 表头与数据的关联,不能为空。例如：{ {"序号", "id"}}， “序号”是导出的excel表的表头，“id”是导入data数据的key
     * @param data              导出的数据
     * @param sheetTitle        title名
     * @param sheetIndex        sheet的索引
     * @param writeablebook     工作表
     * @return
     * @throws IOException
     * @throws WriteException
     * @throws RowsExceededException
     * @throws ParseException
     */
    @SuppressWarnings("unchecked")
    public static WritableWorkbook exportDataToExcel(OutputStream os,
                                                     List<TitleAndModelKey> titleAndModelKeys, List<? extends Object> data, String sheetTitle,
                                                     String headTitle, int sheetIndex, WritableWorkbook writeablebook) throws Exception {
        if (CollectionUtils.isEmpty(titleAndModelKeys)) {
            throw new NullPointerException("export setting 'titleAndModelKeys' can not be null");
        }

        // 准备设置excel工作表的标题
        if (writeablebook == null) {
            writeablebook = Workbook.createWorkbook(os);
        }

        // 添加第一个工作表并设置第一个Sheet的名字
        WritableSheet sheet = writeablebook.createSheet(sheetTitle, sheetIndex);

        int headTitleRowIndex = 0;
        int titleRowIndex = 1;
        WritableCellFormat defaultHeadTitleFormat = new WritableCellFormat(new WritableFont(WritableFont.createFont("微软雅黑"), 12, WritableFont.BOLD));
        if (StringUtils.isNotBlank(headTitle)) {
            /**默认的标题格式**/
            defaultHeadTitleFormat.setAlignment(Alignment.CENTRE); // 设置水平居中对齐

            Label headLabel = new Label(0, headTitleRowIndex, headTitle, defaultHeadTitleFormat); // 标题1列0行
            headLabel.setCellFormat(new WritableCellFormat(NumberFormats.TEXT));
            sheet.mergeCells(0, headTitleRowIndex, titleAndModelKeys.size() - 1, headTitleRowIndex); // 标题头合并单元格 0列0行10列0行
            sheet.addCell(headLabel);
        } else {
            titleRowIndex = 0;
        }

        // 设置字体

        for (int i = 0; i < titleAndModelKeys.size(); i++) {
            WritableCellFormat titleFormat = titleAndModelKeys.get(i).getTitleFormat();
            if (titleFormat == null) {
                titleFormat = new WritableCellFormat(new WritableFont(WritableFont.createFont("微软雅黑"), 12));
                titleFormat.setBackground(jxl.format.Colour.GRAY_25); // 设置背景颜色
                titleFormat.setBorder(Border.ALL, BorderLineStyle.THIN, jxl.format.Colour.BLACK); // 边框
            }
            Label label = new Label(i, titleRowIndex, titleAndModelKeys.get(i).getTitle(), titleFormat);
            sheet.setColumnView(i, 20); // 设置列宽度
            sheet.addCell(label);
        }

        if (CollectionUtils.isEmpty(data)) {
            return writeablebook;
        }

        // 内容字体设置
        for (int i = 0; data != null && i < data.size(); i++) {
            // 将data的类型放到metaObject对象里，根据字段key来获取值value

            for (int j = 0; j < titleAndModelKeys.size(); j++) {

                // 获取列表属性
                TitleAndModelKey titleAndModelKey = titleAndModelKeys.get(j);
                String key = titleAndModelKey.getModelKey();
                if (StringUtils.isBlank(key)) {
                    throw new IllegalArgumentException(MessageFormat.format(
                            "导入的excel参数异常，titleAndModelKeys中, title{0}, key{1}", titleAndModelKey.getTitle(),
                            titleAndModelKey.getModelKey()));
                }

                Object value = getFieldValue(data.get(i), key);
                String content = null;
                if (value == null) {
                    content = titleAndModelKey.getDefaultValue();

                } else if (titleAndModelKey.getToStrConverter() != null) {
                    content = titleAndModelKey.getToStrConverter().convert(value, data.get(i), i);

                } else if (ConverterFactory.getDefaultToStrConverter(value.getClass()) != null) {
                    content = ConverterFactory.getDefaultToStrConverter(value.getClass()).convert(value,
                            data.get(i), i);

                } else {
                    content = String.valueOf(value);
                }

                WritableCellFormat contentFormate = titleAndModelKey.getContentFormat();
                if (contentFormate == null) {
                    contentFormate = new WritableCellFormat(new WritableFont(WritableFont.createFont("微软雅黑"), 10));
                    contentFormate.setBorder(Border.ALL, BorderLineStyle.THIN, jxl.format.Colour.BLACK);
                }

                int dataRow = i + titleRowIndex + 1;
                Label tmpLabel = new Label(j, dataRow, String.valueOf(content), contentFormate);
                sheet.addCell(tmpLabel);
            }
        }
        return writeablebook;
    }

    /**
     * @param in                导入的excel表
     * @param sheetIndex        导入的excel的sheet的索引
     * @param dataStartRowIndex 导入excel的首行数据。从首行数据一直向下查找数据，直至找不到。
     * @param headRowIndex      导入的excel表的表头的索引
     * @param clazz
     * @return
     * @throws Exception
     */
    public static <T> List<T> importExcelDataToList(InputStream in, int sheetIndex, int dataStartRowIndex,
                                                    int headRowIndex, Class<T> clazz) throws Exception {
        List<TitleAndModelKey> titleAndModelKeys = createTitleAndModelKeyList(clazz);

        if (CollectionUtils.isEmpty(titleAndModelKeys)) {
            throw new NullPointerException("export setting 'titleAndModelKeys' can not be null");
        }

        Workbook workbook = Workbook.getWorkbook(in);
        Sheet sheet = workbook.getSheet(sheetIndex);

        // 1、设置每一列的数据，存入map时候，对应的key
        Cell[] headRow = sheet.getRow(headRowIndex);

        checkExcel(titleAndModelKeys, headRow);
        List<List<Cell>> allRows = getAllRows(sheet, dataStartRowIndex, headRow.length);

        List<T> dataList = new ArrayList<T>();

        // 2、查出excel的数据，并导入到map里面
        int rowCount = allRows.size();

        for (int i = 0; sheet != null && i < rowCount; i++) {
            List<Cell> row = allRows.get(i);

            dataList.add(converteRowToObject(sheet, headRow, row, titleAndModelKeys, clazz));
        }

        return dataList;
    }

    private static <T> T converteRowToObject(Sheet sheet, Cell[] headRow, List<Cell> row,
                                             List<TitleAndModelKey> titleAndModelKeys, Class<T> clazz) throws Exception {

        T object = clazz.newInstance();
        for (int j = 0; j < titleAndModelKeys.size(); j++) {
            // 2.1 、找到标题object的属性与对应的列
            TitleAndModelKey titleAndModelKey = titleAndModelKeys.get(j);

            String title = titleAndModelKey.getTitle();
            Integer index = titleAndModelKey.getColumIndex();
            String key = titleAndModelKey.getModelKey();
            String content = row.get(index).getContents();

            if (StringUtils.isBlank(content)) {
                if (titleAndModelKey.isRequired()) {
                    throw new NullPointerException(String.format("[%s] 不能为空值。", title));
                }

                if (StringUtils.isBlank(titleAndModelKey.getDefaultValue())) {
                    continue;
                }
                content = titleAndModelKey.getDefaultValue();
            }

            try {
                // 2.2 根据属性值反射获取类型
                ReflectUtils.setValue(object, key, convertStrToObject(object, sheet, row, titleAndModelKey));

            } catch (Exception e) {
                throw new RuntimeException(String.format("导入[%s]失败，原因:%s", title, e.getMessage()), e);
            }
        }
        return object;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static Object convertStrToObject(Object object, Sheet sheet, List<Cell> row, TitleAndModelKey titleAndModelKey) throws Exception {
        Integer index = titleAndModelKey.getColumIndex();
        String key = titleAndModelKey.getModelKey();
        String content = row.get(index).getContents();

        StrToObjConverter converter = titleAndModelKey.getToObjConverter();
        if (converter != null) {
            return converter.convert(content, row, sheet);
        }

        Class<?> cla = FieldUtils.getField(object.getClass(), key, true).getType();
        if (cla.equals(String.class)) {
            // 如果是string类型，就直接转换
            return content;
        }

        converter = ConverterFactory.getDefaultToObjConverter(cla);
        if (converter == null) {
            throw new RuntimeException("找不到合适转换器 class[" + cla + "]");
        }

        return converter.convert(content, row, sheet);
    }

    private static List<List<Cell>> getAllRows(Sheet sheet, int dataStartRowIndex, int length) {
        List<List<Cell>> allRows = new ArrayList<List<Cell>>();

        boolean isEnd = false; //是否要结束遍历

        int readIndex = dataStartRowIndex; //读数据的索引
        while (!isEnd) {

            if (readIndex == (sheet.getRows() + dataStartRowIndex - 1)) {
                isEnd = true;
                break;
            }

            //记录每一行的数据
            List<Cell> row = new ArrayList<Cell>(length);
            for (int i = 0; i < length; i++) {
                Cell cell = sheet.getCell(i, readIndex);
                row.add(cell);
            }

            //检查当前行的数据是不是全都是空
            boolean isAllBlank = true;
            for (Cell cell : row) {
                if (StringUtils.isNotBlank(cell.getContents())) {
                    isAllBlank = false;
                    break;
                }
            }

            if (!isAllBlank) {
                //如果不是最后一行，记录下当前行
                allRows.add(row);
                readIndex++;

            } else {
                isEnd = true;
                break;
            }

        }

        return allRows;
    }

    @SuppressWarnings("unused")
    private static Cell[] getFromSheet(Sheet sheet, int startRowIndex, int length) {
        Cell[] cells = new Cell[length];
        for (int i = 0; i < length; i++) {
            cells[i] = sheet.getCell(i, startRowIndex);
        }
        return cells;
    }

    private static void checkExcel(List<TitleAndModelKey> titleAndModelKeys, Cell[] headRow) {
        for (int j = 0; j < titleAndModelKeys.size(); j++) {

            String title = titleAndModelKeys.get(j).getTitle();
            Integer titleIndex = titleAndModelKeys.get(j).getColumIndex();

            if (StringUtils.isBlank(title) && titleIndex == null) {
                throw new IllegalArgumentException(
                        "excel 导入导出的操作中，titleAndModelKey配置异常， title 与 columIndex 不能同时为空");
            }

            if (titleIndex == null) {
                titleIndex = getTitleIndexInRow(headRow, title);
                titleAndModelKeys.get(j).setColumIndex(titleIndex);
            }

            if (titleIndex < 0) {
                // 找不到对应的列的数据
                throw new IllegalArgumentException("excel表格式异常，找不到列[" + title + "]");
            }
        }
    }

    public static int getTitleIndexInRow(Cell[] headRow, String title) {
        if (StringUtils.isBlank(title)) {
            throw new NullPointerException("title can not be null");
        }
        int index = -1;
        for (int i = 0; i < headRow.length; i++) {
            String content = headRow[i].getContents();
            if (StringUtils.equals(content.trim(), title.trim())) {
                index = i;
                return i;
            }
        }

        return index;
    }

    public static TitleAndModelKey createTitleAndModelKey(String title, String modelKey) {
        return new TitleAndModelKey(title, modelKey);
    }

    public static TitleAndModelKey createTitleAndModelKey(String title, String modelKey, boolean isRequired) {
        TitleAndModelKey t = new TitleAndModelKey(title, modelKey);
        t.setRequired(isRequired);
        return t;
    }

    public static TitleAndModelKey createTitleAndModelKey(int columIndex, String modelKey) {
        return new TitleAndModelKey(columIndex, modelKey);
    }

    @SuppressWarnings("rawtypes")
    public static TitleAndModelKey createTitleAndModelKey(String title, String modelKey,
                                                          StrToObjConverter toObjConverter) {
        return new TitleAndModelKey(title, modelKey, toObjConverter);
    }

    @SuppressWarnings("rawtypes")
    public static TitleAndModelKey createTitleAndModelKey(String title, String modelKey,
                                                          ObjToStrConverter toStrConverter) {
        return new TitleAndModelKey(title, modelKey, toStrConverter);
    }

    public static Object getFieldValue(Object obj, String key) throws IllegalAccessException {
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            if (fieldName.equals(key)) {
                return field.get(obj);
            }
        }
        return null;
    }

    public static List<TitleAndModelKey> createTitleAndModelKeyList(Class<?> clazz) throws IllegalAccessException, InstantiationException {
        List<TitleAndModelKey> list = new ArrayList<>();

        Field[] fields = ReflectUtil.getFields(clazz);

        for (Field field : fields) {
            Excel excel = field.getAnnotation(Excel.class);
            if (excel == null) {
                continue;
            }

            String title = excel.title();
            String modelKey = field.getName();
            Class<? extends ObjToStrConverter> converter = excel.objToStrConverter();
            if (converter == ObjToStrConverter.class) {
                list.add(new TitleAndModelKey(title, modelKey));
            } else {
                list.add(new TitleAndModelKey(title, modelKey, converter.newInstance()));
            }
        }

        return list;
    }
}
