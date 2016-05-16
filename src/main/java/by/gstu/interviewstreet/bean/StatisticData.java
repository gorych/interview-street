package by.gstu.interviewstreet.bean;

import by.gstu.interviewstreet.domain.Question;
import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.Map;

public class StatisticData implements Serializable {

    @Expose
    private String questionText;

    @Expose
    private String questionType;

    @Expose
    private Map<String, Object[]> answerData;

    @Expose
    private String avg;

    @Expose
    private int total;

    @Expose
    private int maxEstimate; //Use on jsp for rating questions

    public StatisticData(Question question, Map<String, Object[]> answerData, int maxEstimate, int total, String avg) {
        this.questionText = question.getText();
        this.questionType = question.getType().getName();
        this.answerData = answerData;
        this.maxEstimate = maxEstimate;
        this.total = total;
        this.avg = avg;
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

    public Map<String, Object[]> getAnswerData() {
        return answerData;
    }

    public void setAnswerData(Map<String, Object[]> answerData) {
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

    public String getAvg() {
        return avg;
    }

    public void setAvg(String avg) {
        this.avg = avg;
    }

    @Override
    public String toString() {
        return "StatisticData{" +
                "questionText='" + questionText + '\'' +
                ", questionType='" + questionType + '\'' +
                ", answerData=" + answerData +
                ", avg='" + avg + '\'' +
                ", total=" + total +
                ", maxEstimate=" + maxEstimate +
                '}';
    }
}
