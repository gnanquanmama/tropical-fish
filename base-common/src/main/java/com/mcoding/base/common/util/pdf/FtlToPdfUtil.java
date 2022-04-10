package com.mcoding.base.common.util.pdf;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.DocumentProperties;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.Map;


/**
 * PDF 工具类
 *
 * @author wzt
 */
public class FtlToPdfUtil {

    private static Configuration configuration;

    static {
        // 创建一个Configuration对象，构造方法的参数就是FreeMarker对于的版本号。
        configuration = new Configuration(Configuration.getVersion());
    }

    /**
     * 生成 HTML 字节数组
     * <p>
     * FreeMarker 模板转换 html
     *
     * @param path
     * @param fileName
     * @param dataSource
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public static byte[] generateHtmlByteArray(String path, String fileName, Map<String, Object> dataSource)
            throws IOException, TemplateException {

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             Writer out = new OutputStreamWriter(outputStream)) {

            // 设置模板文件所在的路径
            configuration.setClassForTemplateLoading(FtlToPdfUtil.class, path);

            // 获取一个模板对象。
            Template template = configuration.getTemplate(fileName);
            // 调用模板对象的process方法输出文件
            template.process(dataSource, out);

            return outputStream.toByteArray();
        }

    }

    /**
     * 输出 PDF 输出流
     * <p>
     * HTML 转换为 PDF
     *
     * @param htmlByteArray
     * @param outputStream
     */
    public static void convertToPdf(byte[] htmlByteArray, OutputStream outputStream, PageSize pageSize) throws IOException {
        try (InputStream inputStream = new ByteArrayInputStream(htmlByteArray)) {

            ConverterProperties converterProperties = new ConverterProperties();
            converterProperties.setFontProvider(new DefaultFontProvider(true, true, true));
            converterProperties.setCharset(PdfEncodings.UTF8);

            PdfWriter pdfWriter = new PdfWriter(outputStream);
            PdfDocument pdfDocument=  new PdfDocument(pdfWriter, new DocumentProperties());
            pdfDocument.setDefaultPageSize(pageSize);
            HtmlConverter.convertToPdf(inputStream, pdfDocument, converterProperties);
        }
    }

}
