package by.gstu.interviewstreet.web.controller.action;

import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.service.ExportToExcelService;
import by.gstu.interviewstreet.service.ExportToWordService;
import by.gstu.interviewstreet.service.InterviewService;
import by.gstu.interviewstreet.util.DateUtils;
import by.gstu.interviewstreet.web.SecurityConstants;
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
    private InterviewService interviewService;

    @Autowired
    private ExportToWordService exportToWordService;

    @Autowired
    private ExportToExcelService exportToExcelService;

    @RequestMapping(value = {"/word/{hash}"}, method = RequestMethod.GET)
    public void downloadInterview(@PathVariable String hash, HttpServletResponse response) {
        Interview interview = interviewService.get(hash);

        try (XWPFDocument document = exportToWordService.exportInterviewToWord(interview);
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

        try (XSSFWorkbook book = exportToExcelService.exportAllStatistics(interview); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            book.write(out);

            String suffix = (EXPORT_TYPE_ALL.equals(exportType) ? SEPARATOR + EXPORT_TYPE_ALL : "") + SEPARATOR + DateUtils.YYYY_MM_DD.format(DateUtils.getToday());
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
}
