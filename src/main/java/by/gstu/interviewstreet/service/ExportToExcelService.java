package by.gstu.interviewstreet.service;

import by.gstu.interviewstreet.bean.StatisticData;
import by.gstu.interviewstreet.domain.Interview;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public interface ExportToExcelService {

    XSSFWorkbook exportAllStatistics(List<StatisticData> statistics, Interview interview, ByteArrayOutputStream out) throws IOException;

}

