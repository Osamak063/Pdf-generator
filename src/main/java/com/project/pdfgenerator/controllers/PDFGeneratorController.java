package com.project.pdfgenerator.controllers;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.project.pdfgenerator.models.PdfItem;
import com.project.pdfgenerator.models.TableData;
import com.project.pdfgenerator.utils.Data;
import com.project.pdfgenerator.utils.ResourcesUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

@RestController
public class PDFGeneratorController {

    @RequestMapping("/generatepdf")
    public String GeneratePDF(){
        List<PdfItem> pdfItemsList = Data.getPdfItemData();
        StringBuilder pdfTemplate;
        String guid = UUID.randomUUID().toString();
        for(PdfItem item : pdfItemsList)
        {
            try
            {
                pdfTemplate = new StringBuilder(ResourcesUtils.GetPDfTemplate());
            }
            catch (Exception ex)
            {
                return ex.getMessage();
            }
            int index = pdfTemplate.indexOf("{Region}");
            pdfTemplate.replace(index, index + "{Region}".length(), item.Region);
            index = pdfTemplate.indexOf("{Borrower}");
            pdfTemplate.replace(index, index + "{Borrower}".length(), item.Borrower);
            index = pdfTemplate.indexOf("{City}");
            pdfTemplate.replace(index, index +"{City}".length(), item.City);
            index = pdfTemplate.indexOf("{Relationship}");
            pdfTemplate.replace(index, index + "{Relationship}".length(), item.Relationship);
            index = pdfTemplate.indexOf("{Guarantor}");
            pdfTemplate.replace(index, index + "{Guarantor}".length(), String.valueOf(item.Guarantor));
            index = pdfTemplate.indexOf("{Bus Type}");
            pdfTemplate.replace(index, index + "{Bus Type}".length(), String.valueOf(item.BusType));
            index = pdfTemplate.indexOf("{Examiner}");
            pdfTemplate.replace(index, index + "{Examiner}".length(), item.Examiner);
            index = pdfTemplate.indexOf("{Leveraged Financing}");
            pdfTemplate.replace(index, index + "{Leveraged Financing}".length(), item.LeverageFinancing);
            index = pdfTemplate.indexOf("{Exam Date}");
            pdfTemplate.replace(index, index + "{Exam Date}".length(),
                    new SimpleDateFormat("MM-dd-yyyy").format(item.ExamDate));
            StringBuilder tableTemplate;
            try
            {
                tableTemplate = new StringBuilder(ResourcesUtils.GetTableTemplate());
            }
            catch (Exception ex)
            {
                return ex.getMessage();
            }
            StringBuilder tableDataRows = new StringBuilder();
            for(TableData tableData: item.tableDataList)
            {
                tableDataRows.append("<tr>");
                tableDataRows.append("<td>"+tableData.a+"</td>");
                tableDataRows.append("<td>"+tableData.b+"</td>");
                tableDataRows.append("<td>"+tableData.b+"</td>");
                tableDataRows.append("<td>"+tableData.d+"</td>");
                tableDataRows.append("</tr>");
            }
            index = tableTemplate.indexOf("{TableRows}");
            tableTemplate.replace(index, index + "{TableRows}".length(),tableDataRows.toString());
            index = pdfTemplate.indexOf("{Table}");
            pdfTemplate.replace(index,  index +"{Table}".length(), tableTemplate.toString());
            File pdfFile = new File(  "/RiskAssetReview"+ guid +".pdf");
            try {
                if (!pdfFile.exists()) {
                    pdfFile.createNewFile();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(pdfFile);
                PdfWriter pdfWriter = new PdfWriter(fileOutputStream);
                ConverterProperties converterProperties = new ConverterProperties();
                PdfDocument pdfDocument = new PdfDocument(pdfWriter);
                pdfDocument.setDefaultPageSize(new PageSize(PageSize.A3));
                HtmlConverter.convertToPdf(pdfTemplate.toString(), pdfDocument, converterProperties);
            }
            catch (Exception ex)
            {
                return "Error creating PDF file";
            }
            guid = UUID.randomUUID().toString();
        }
        return "PDF generated successfully.";
    }
}
