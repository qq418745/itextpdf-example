package com.itextpdf;


import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.font.FontProvider;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.itextpdf.kernel.pdf.PdfName.BaseFont;


@SpringBootTest
@Slf4j
public class ItextpdfApplicationTests {

    @Autowired
    private ResourceLoader resourceLoader;

    @Test
    void test() {
        // 输入HTML文件路径
        String htmlSource = "pdf_template.html";
        // 输出PDF文件路径
        String pdfDest = "output.pdf";
        convertHtmlToPdf(htmlSource, pdfDest);
        System.out.println("test is OK!");
    }

    public void convertHtmlToPdf(String htmlFileName, String pdfFileName) {
        try {
            // 读取resources文件夹中的HTML文件并转换为字符串
            var resource = resourceLoader.getResource("classpath:" + htmlFileName);
            var htmlBytes = Files.readAllBytes(Paths.get(resource.getURI()));
            String htmlContent = new String(htmlBytes, StandardCharsets.UTF_8);

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
