package com.tianwen.dcdp.pojo;

import java.util.Date;

public class CrawlerModel {
    private Long id;

    private String modelName;

    private String modelClassPath;

    private String bz;

    private Date createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelClassPath() {
		return modelClassPath;
	}

	public void setModelClassPath(String modelClassPath) {
		this.modelClassPath = modelClassPath;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

   
}