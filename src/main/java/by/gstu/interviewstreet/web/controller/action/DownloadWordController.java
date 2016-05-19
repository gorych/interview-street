package by.gstu.interviewstreet.web.controller.action;

import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.security.UserRoleConstants;
import by.gstu.interviewstreet.service.DownloadWordService;
import by.gstu.interviewstreet.service.InterviewService;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Controller
@RequestMapping("/download")
@Secured(UserRoleConstants.EDITOR)
public class DownloadWordController {

    private static final String CONTENT_DISPOSITION = "Content-Disposition";
    private static final String ATTACHMENT_FILE = "attachment; filename=";
    private static final String MIME_TYPE = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";

    @Autowired
    InterviewService interviewService;

    @Autowired
    DownloadWordService downloadWordService;

    @RequestMapping(value = {"/word/{hash}"}, method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
    public void downloadInterview(@PathVariable String hash, HttpServletResponse response) {
        Interview interview = interviewService.get(hash);
        XWPFDocument document = downloadWordService.exportInterviewToWord(interview);

        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream()) {
            document.write(byteOut);

            byte[] content = byteOut.toByteArray();
            String filename = interview.getHash() + ".docx";

            response.setContentType(MIME_TYPE);
            response.addHeader(CONTENT_DISPOSITION, ATTACHMENT_FILE + filename);
            response.setContentLength(content.length);

            ServletOutputStream out = response.getOutputStream();
            out.write(content);
        } catch (IOException e) {
            //TODO add error code to response header
            e.printStackTrace();
        }
    }

}
