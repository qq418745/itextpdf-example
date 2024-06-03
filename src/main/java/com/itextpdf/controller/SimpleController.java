package com.itextpdf.controller;

import com.itextpdf.TemplateFiller;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.layout.font.FontProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


@RestController
@AllArgsConstructor
@RequestMapping("/simple")
@Slf4j
public class SimpleController {


    private final ResourceLoader resourceLoader;

    /**
     * 下载 PDF
     *
     * @return
     */
    @GetMapping(value = "/download")
    public String download(HttpServletResponse response) {

        ConverterProperties properties = new ConverterProperties();
        FontProvider fontProvider = new FontProvider();
        // 常用字体程序及对应编码
        fontProvider.addFont("STSongStd-Light", "UniGB-UCS2-H");
        properties.setFontProvider(fontProvider);

        try {
            // 直接写入response的输出流
            HtmlConverter.convertToPdf(getHtmlContent(), response.getOutputStream(), properties);
            System.out.println("PDF created and sent for download successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            try {
                // 若转换或下载中发生错误，发送错误信息
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "生成PDF文件失败");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return null;
    }

    private  String getHtmlContent() throws IOException {
        // 输入HTML文件路径
        String htmlSource = "pdf_template.html";

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
        return TemplateFiller.replacePlaceholders(htmlContent, data);
    }


}