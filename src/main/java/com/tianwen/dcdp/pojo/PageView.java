package com.tianwen.dcdp.pojo;

import com.tianwen.dcdp.dao.base.BaseEntity;

public class PageView extends BaseEntity{
    private Integer reportId;

    private String year;

    private String month;

    private String dateStr;

    private Integer pv;

    private Integer uv;

    private Integer ip;

    private Integer reg;

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year == null ? null : year.trim();
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month == null ? null : month.trim();
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr == null ? null : dateStr.trim();
    }

    public Integer getPv() {
        return pv;
    }

    public void setPv(Integer pv) {
        this.pv = pv;
    }

    public Integer getUv() {
        return uv;
    }

    public void setUv(Integer uv) {
        this.uv = uv;
    }

    public Integer getIp() {
        return ip;
    }

    public void setIp(Integer ip) {
        this.ip = ip;
    }

    public Integer getReg() {
        return reg;
    }

    public void setReg(Integer reg) {
        this.reg = reg;
    }
}