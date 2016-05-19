package by.gstu.interviewstreet.service;


import by.gstu.interviewstreet.domain.Interview;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public interface DownloadWordService {

    XWPFDocument exportInterviewToWord(Interview interview);

}
