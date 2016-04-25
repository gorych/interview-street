package by.gstu.interviewstreet.bean;

import by.gstu.interviewstreet.domain.Question;

import java.io.Serializable;
import java.util.Map;

public class StatisticData implements Serializable {

    private String questionText;
    private String questionType;

    private Map<Object, Object[]> answerData;

    private int total;

    /*Use on jsp for rating questions*/
    private int maxEstimate;

    public StatisticData(Question question, Map<Object, Object[]> answerData, int maxEstimate, int total) {
        this.questionText = question.getText();
        this.questionType = question.getType().getName();
        this.answerData = answerData;
        this.maxEstimate = maxEstimate;
        this.total = total;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public Map<Object, Object[]> getAnswerData() {
        return answerData;
    }

    public void setAnswerData(Map<Object, Object[]> answerData) {
        this.answerData = answerData;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getMaxEstimate() {
        return maxEstimate;
    }

    public void setMaxEstimate(int maxEstimate) {
        this.maxEstimate = maxEstimate;
    }

    @Override
    public String toString() {
        return "StatisticData{" +
                "questionText='" + questionText + '\'' +
                ", questionType='" + questionType + '\'' +
                ", answerData=" + answerData +
                ", total=" + total +
                '}';
    }
}
