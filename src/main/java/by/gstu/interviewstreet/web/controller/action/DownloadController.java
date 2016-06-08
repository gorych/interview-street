package by.gstu.interviewstreet.web.controller.action;

import by.gstu.interviewstreet.bean.StatisticData;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.web.SecurityConstants;
import by.gstu.interviewstreet.service.ExportToWordService;
import by.gstu.interviewstreet.service.InterviewService;
import by.gstu.interviewstreet.service.StatisticsService;
import by.gstu.interviewstreet.util.DateUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/download")
@Secured(SecurityConstants.EDITOR)
public class DownloadController {

    private static final Logger LOG = LoggerFactory.getLogger(DownloadController.class);

    private static final String CONTENT_DISPOSITION = "Content-Disposition";
    private static final String ATTACHMENT_FILE = "attachment; filename=";
    private static final String WORD_MIME_TYPE = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    private static final String EXCEL_MIME_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private static final String EXPORT_TYPE_ALL = "all";
    private static final String SEPARATOR = "_";

    @Autowired
    InterviewService interviewService;

    @Autowired
    StatisticsService statisticsService;

    @Autowired
    ExportToWordService downloadWordService;


    @RequestMapping(value = {"/word/{hash}"}, method = RequestMethod.GET)
    public void downloadInterview(@PathVariable String hash, HttpServletResponse response) {
        Interview interview = interviewService.get(hash);

        try (XWPFDocument document = downloadWordService.exportInterviewToWord(interview);
             ByteArrayOutputStream byteOut = new ByteArrayOutputStream()) {
            document.write(byteOut);

            String fileName = interview.getHash() + ".docx";
            byte[] content = byteOut.toByteArray();

            exportAction(content, fileName, WORD_MIME_TYPE, response);
        } catch (IOException e) {
            LOG.warn("Error when exporting interview to word.");
        }
    }

    @RequestMapping(value = {"/excel/{hash}"}, method = RequestMethod.GET)
    public void exportStatisticsToExcel(@PathVariable String hash, @RequestParam(required = false) String exportType,
                                        HttpServletResponse response) {
        Interview interview = interviewService.get(hash);

        /*NULL is correct value*/
        List<StatisticData> statistics = statisticsService.getInterviewStatistics(interview, null, null);
        try (XSSFWorkbook book = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            XSSFSheet sheet = book.createSheet(interview.getName());
            int rowNumber = 0;

            XSSFRow mainHeader = sheet.createRow(rowNumber++);
            XSSFCell cell = mainHeader.createCell(0);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cell.setCellValue("Статистика по анкете \"" + interview.getName() + "\"");
            makeCellAutosizeAndBold(book, mainHeader);

            rowNumber++;

            for (StatisticData statistic : statistics) {
                /*Add question text*/
                XSSFRow row = sheet.createRow(rowNumber++);
                XSSFCell questionText = row.createCell(0);
                questionText.setCellType(Cell.CELL_TYPE_STRING);
                questionText.setCellValue("Вопрос: " + statistic.getQuestionText());

                XSSFRow tableHeader = sheet.createRow(rowNumber++);
                XSSFCell c1 = tableHeader.createCell(0);
                XSSFCell c2 = tableHeader.createCell(1);
                XSSFCell c3 = tableHeader.createCell(2);

                c1.setCellType(Cell.CELL_TYPE_STRING);
                c2.setCellType(Cell.CELL_TYPE_NUMERIC);
                c3.setCellType(Cell.CELL_TYPE_NUMERIC);

                c1.setCellValue("Ответы");
                c2.setCellValue("Ответило, чел");
                c3.setCellValue("Ответило, %");

                makeCellAutosizeAndBold(book, tableHeader);

                Map<String, Object[]> answerData = statistic.getAnswerData();

                for (String key : answerData.keySet()) {
                    XSSFRow answer = sheet.createRow(rowNumber++);
                    XSSFCell answerText = answer.createCell(0);
                    XSSFCell peopleResponded = answer.createCell(1);
                    XSSFCell percentResponded = answer.createCell(2);

                    answerText.setCellType(Cell.CELL_TYPE_STRING);
                    peopleResponded.setCellType(Cell.CELL_TYPE_NUMERIC);
                    percentResponded.setCellType(Cell.CELL_TYPE_NUMERIC);

                    answerText.setCellValue(key);
                    Object[] values = answerData.get(key);

                    peopleResponded.setCellValue(values[0].toString());
                    percentResponded.setCellValue(values[1].toString().replace(",", "."));
                }

                rowNumber++;
            }

            book.write(out);

            String suffix = (EXPORT_TYPE_ALL.equals(exportType)
                    ? SEPARATOR + EXPORT_TYPE_ALL : "") +
                    SEPARATOR + DateUtils.YYYY_MM_DD.format(DateUtils.getToday());
            String fileName = interview.getHash() + suffix + ".xlsx";

            byte[] content = out.toByteArray();

            exportAction(content, fileName, EXCEL_MIME_TYPE, response);
        } catch (IOException e) {
            LOG.warn("Error when exporting interview to excel.");
        }
    }

    private void exportAction(byte[] content, String filename, String mimeType, HttpServletResponse response)
            throws IOException {
        response.addHeader(CONTENT_DISPOSITION, ATTACHMENT_FILE + filename);
        response.setContentType(mimeType);
        response.setContentLength(content.length);

        ServletOutputStream out = response.getOutputStream();
        out.write(content);
        out.flush();
    }

    private void makeCellAutosizeAndBold(Workbook wb, Row row) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);

        for (int i = 0; i < row.getLastCellNum(); i++) {
            row.getCell(i).setCellStyle(style);
            wb.getSheetAt(0).autoSizeColumn(i);
        }
    }

}
