package com.tianwen.dcdp.pojo;

public class PointRule {
    private Integer ruleId;

    private String ruleName;

    private Byte period;

    private Integer times;

    private Integer point;

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName == null ? null : ruleName.trim();
    }

    public Byte getPeriod() {
        return period;
    }

    public void setPeriod(Byte period) {
        this.period = period;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }
}