package by.gstu.interviewstreet.bean;

import java.io.Serializable;

public class StatisticDataKey implements Serializable, Comparable<StatisticDataKey>{

    private String text;
    private String type;

    public StatisticDataKey(String text, String type) {
        this.text = text;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatisticDataKey that = (StatisticDataKey) o;

        return getText().equals(that.getText());

    }

    @Override
    public int hashCode() {
        return getText().hashCode();
    }

    @Override
    public int compareTo(StatisticDataKey key) {
        return key.getText().compareTo(getText());
    }

    @Override
    public String toString() {
        return "StatisticDataKey{" +
                "text='" + text + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
