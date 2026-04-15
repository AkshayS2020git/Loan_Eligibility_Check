package com.loancheck.model;

public class RiskFactor {

    private String label;
    private int weight;
    private int score;
    private int contribution;
    private String description;

    public RiskFactor() {
    }

    public RiskFactor(String label, int weight, int score, int contribution, String description) {
        this.label = label;
        this.weight = weight;
        this.score = score;
        this.contribution = contribution;
        this.description = description;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getContribution() {
        return contribution;
    }

    public void setContribution(int contribution) {
        this.contribution = contribution;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
