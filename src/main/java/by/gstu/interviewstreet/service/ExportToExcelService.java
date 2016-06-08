package by.gstu.interviewstreet.service;

import by.gstu.interviewstreet.domain.Interview;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

public interface ExportToExcelService {

    XSSFWorkbook exportAllStatistics(Interview interview) throws IOException;

}

