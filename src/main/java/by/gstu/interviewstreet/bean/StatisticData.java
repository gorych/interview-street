package by.gstu.interviewstreet.bean;

import java.io.Serializable;
import java.text.DecimalFormat;

public class StatisticData implements Serializable {

    private double count;
    private int total;
    private String answer;

    public StatisticData(int count, int total, String answer) {
        this.count = count;
        this.total = total;
        this.answer = answer;
    }

    public double getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getPercent() {
        return new DecimalFormat("#0.00").format(count * 100 / total);
    }

    @Override
    public String toString() {
        return "StatisticData{" +
                "count=" + count +
                ", total=" + total +
                ", answer='" + answer + '\'' +
                '}';
    }
}
