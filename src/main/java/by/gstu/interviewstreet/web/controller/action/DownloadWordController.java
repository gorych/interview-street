package by.gstu.interviewstreet.web.controller.action;

import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.security.UserRoleConstants;
import by.gstu.interviewstreet.service.InterviewService;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@RequestMapping("/download")
@Secured(UserRoleConstants.EDITOR)
public class DownloadWordController {

    @Autowired
    InterviewService interviewService;

    @RequestMapping(value = {"/download/{hash}"}, method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
    public void downloadInterview(@PathVariable String hash, HttpServletResponse response) {
        Interview interview = interviewService.get(hash);

        XWPFDocument document = new XWPFDocument();
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText("At tutorialspoint.com, we strive hard to " +
                "provide quality tutorials for self-learning " +
                "purpose in the domains of Academics, Information " +
                "Technology, Management and Computer Programming languages.");

        try (FileOutputStream out = new FileOutputStream(new File(interview.getName() + ".docx"))) {
            document.write(out);

            response.setContentType("application/msword");
            response.setHeader("Content-Disposition", "attachment;filename=" + interview.getName() + ".docx");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
