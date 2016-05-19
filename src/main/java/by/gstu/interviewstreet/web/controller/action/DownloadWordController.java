package by.gstu.interviewstreet.web.controller.action;

import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.security.UserRoleConstants;
import by.gstu.interviewstreet.service.InterviewService;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
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
    private static final String MIME_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    @Autowired
    InterviewService interviewService;

    @RequestMapping(value = {"/word/{hash}"}, method = RequestMethod.GET)
    public void downloadInterview(@PathVariable String hash, HttpServletResponse response) {
        System.out.println("ya tut");
        Interview interview = interviewService.get(hash);

        XWPFDocument document = new XWPFDocument();
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText("Русски текстAt tutorialspoint.com, we strive hard to " +
                "provide quality tutorials for self-learning " +
                "purpose in the domains of Academics, Information " +
                "Technology, Management and Computer Programming languages.");

        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream()) {
            document.write(byteOut);

            byte[] content = byteOut.toByteArray();
            String filename = interview.getName() + ".docx";

            response.setContentType(MIME_TYPE);
            response.addHeader(CONTENT_DISPOSITION, ATTACHMENT_FILE + filename);
            response.setContentLength(content.length);

            ServletOutputStream out = response.getOutputStream();
            out.write(content);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
