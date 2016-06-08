package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.domain.Answer;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.Question;
import by.gstu.interviewstreet.service.ExportToWordService;
import by.gstu.interviewstreet.util.DateUtils;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class ExportToWordServiceImpl implements ExportToWordService {

    private final static String FONT_STYLE = "Times New Roman";
    private final static int HEADER_FONT_SIZE = 14;
    private final static int MAIN_FONT_SIZE = 12;

    @Override
    public XWPFDocument exportInterviewToWord(Interview interview) {
        XWPFDocument document = new XWPFDocument();

        addHeader(interview, document);
        addSubHeader(interview, document);
        addRespondentData(document);

        /*Add interview questions and answers for their*/
        List<Question> questions = interview.getSortedQuestions();
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            XWPFRun mainRun = createParagraph(document);

            int questionNumber = i + 1;
            mainRun.setText(questionNumber + ". " + question.getText());

            if (question.getType().getName().equals("radio")) {
                XWPFRun p1 = createParagraph(document);

                p1.addTab();
                p1.setItalic(true);
                p1.setText("Выберите один наиболее подходящий вариант.");
            }

            if (question.getType().getName().equals("checkbox")) {
                XWPFRun p1 = createParagraph(document);

                p1.addTab();
                p1.setItalic(true);
                p1.setText("Выберите один или несколько вариантов.");
            }

            XWPFParagraph answersParagraph = document.createParagraph();
            XWPFRun answersParagraphRun = answersParagraph.createRun();
            answersParagraphRun.setFontSize(MAIN_FONT_SIZE);
            answersParagraphRun.setFontFamily(FONT_STYLE);

            List<Answer> answers = question.getSortedAnswers();
            for (int j = 0; j < answers.size(); j++) {
                answersParagraphRun.addTab();
                Answer answer = answers.get(j);

                if (answer.getType().getName().equals("text") && question.getType().getName().equals("text")) {
                    answersParagraphRun.setText("_____________________________________________________________________");
                    continue;
                }

                if (answer.getType().getName().equals("rating") && question.getType().isRateType()) {
                    int rateCount = Integer.parseInt(answer.getText());
                    for (int k = 0; k < rateCount; k++) {
                        answersParagraph.setAlignment(ParagraphAlignment.CENTER);
                        answersParagraphRun.setBold(true);
                        answersParagraphRun.setText((k + 1) + "");
                        answersParagraphRun.addTab();
                    }
                    continue;
                }

                int answerNumber = j + 1;
                answersParagraphRun.setText(answerNumber + ") " + answer.getText());
                if (answer.getType().getName().equals("text")) {
                    answersParagraphRun.setText("____________________________________");
                }
                answersParagraphRun.addCarriageReturn();
            }
        }

        XWPFRun footerRun = createParagraph(document);
        footerRun.setItalic(true);
        footerRun.setBold(true);
        footerRun.setText("Спасибо за прохождение анкеты.");
        footerRun.addCarriageReturn();
        footerRun.addBreak();
        footerRun.setText("Interview Street, " + DateUtils.YYYY.format(DateUtils.getToday()));

        return document;
    }

    private XWPFRun createParagraph(XWPFDocument document) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setFontSize(MAIN_FONT_SIZE);
        run.setFontFamily(FONT_STYLE);
        return run;
    }

    private void addRespondentData(XWPFDocument document) {
        XWPFRun respondentDataRun = createParagraph(document);

        respondentDataRun.addBreak();

        respondentDataRun.setText("Ваша фамилия: ________________________________________________");
        respondentDataRun.addCarriageReturn();
        respondentDataRun.setText("Ваше имя: ____________________________________________________");
        respondentDataRun.addCarriageReturn();
        respondentDataRun.setText("Дата заполнения анкеты: ________________________________________");
        respondentDataRun.addCarriageReturn();

    }

    private void addSubHeader(Interview interview, XWPFDocument document) {
        XWPFRun subHeaderRun = createParagraph(document);
        subHeaderRun.addTab();
        subHeaderRun.setText(interview.getIntroductoryText());
    }

    private void addHeader(Interview interview, XWPFDocument document) {
        XWPFRun headerRun = createParagraph(document);
        headerRun.setFontSize(HEADER_FONT_SIZE);
        headerRun.setBold(true);
        headerRun.addTab();
        headerRun.setText(interview.getName() + " (" + interview.getType().getRusName() + ")");
    }
}


