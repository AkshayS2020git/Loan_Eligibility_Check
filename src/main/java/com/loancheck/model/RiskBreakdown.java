package com.loancheck.model;

public class RiskBreakdown {

    private String factor;
    private int score;
    private String band;
    private String detail;

    public RiskBreakdown() {
    }

    public RiskBreakdown(String factor, int score, String band, String detail) {
        this.factor = factor;
        this.score = score;
        this.band = band;
        this.detail = detail;
    }

    public String getFactor() {
        return factor;
    }

    public void setFactor(String factor) {
        this.factor = factor;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
