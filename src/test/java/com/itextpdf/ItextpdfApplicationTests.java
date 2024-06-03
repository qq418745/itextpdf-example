package com.itextpdf;


import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.layout.font.FontProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;



@SpringBootTest
@Slf4j
public class ItextpdfApplicationTests {

    @Autowired
    private ResourceLoader resourceLoader;

    @Test
    void test() throws IOException {
        // 输入HTML文件路径
        String htmlSource = "pdf_template.html";

        // 创建一个数据实例
        TemplateFiller.PaymentData data = new TemplateFiller.PaymentData(
                "大文苑 11幢-506室 (85.23㎡)",
                "202405236500",
                "物业费",
                "微信支付",
                "2023-01 ~ 2023-12",
                2025.00,
                "贰仟零贰拾伍元整",
                "2024年05月23日",
                "2024年05月28日"
        );

        // 读取resources文件夹中的HTML文件并转换为字符串
        var resource = resourceLoader.getResource("classpath:" + htmlSource);
        var htmlBytes = Files.readAllBytes(Paths.get(resource.getURI()));
        String htmlContent = new String(htmlBytes, StandardCharsets.UTF_8);

        // 使用实际数据替换模板中的占位符
        String result = TemplateFiller.replacePlaceholders(htmlContent, data);

        // 输出PDF文件路径
        String pdfDest = "output.pdf";
        convertHtmlToPdf(result, pdfDest);
        System.out.println("test is OK!");
    }

    public void convertHtmlToPdf(String htmlContent, String pdfFileName) {
        try {
            ConverterProperties properties = new ConverterProperties();
            FontProvider fontProvider = new FontProvider();
            // 常用字体程序及对应编码
            fontProvider.addFont("STSongStd-Light", "UniGB-UCS2-H");
            properties.setFontProvider(fontProvider);
            // 将HTML字符串转换为PDF
            try (var pdfStream = new FileOutputStream(pdfFileName);) {
                HtmlConverter.convertToPdf(htmlContent, pdfStream, properties);
            }
            System.out.println("PDF created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
