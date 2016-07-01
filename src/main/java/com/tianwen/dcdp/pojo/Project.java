package com.tianwen.dcdp.pojo;

import com.tianwen.dcdp.dao.base.BaseEntity;

public class Project extends BaseEntity{
    private Integer projectId;

    private Integer creatId;

    private Long creatTime;

    private String projectName;

    private Integer industry;

    private Integer areaId;

    private Integer projectStage;

    private Double projectValuation;

    private Short projectStatus;

    private Boolean isAdmin;

    private String projectHead;

    private String phoneNum;

    private String projcetIntroduce;

    private String teamIntroduce;

    private Boolean isRecommend;
    
    private Boolean showStatus;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getCreatId() {
        return creatId;
    }

    public void setCreatId(Integer creatId) {
        this.creatId = creatId;
    }

    public Long getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Long creatTime) {
        this.creatTime = creatTime;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public Integer getIndustry() {
        return industry;
    }

    public void setIndustry(Integer industry) {
        this.industry = industry;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getProjectStage() {
        return projectStage;
    }

    public void setProjectStage(Integer projectStage) {
        this.projectStage = projectStage;
    }

    public Double getProjectValuation() {
        return projectValuation;
    }

    public void setProjectValuation(Double projectValuation) {
        this.projectValuation = projectValuation;
    }

    public Short getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(Short projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getProjectHead() {
        return projectHead;
    }

    public void setProjectHead(String projectHead) {
        this.projectHead = projectHead == null ? null : projectHead.trim();
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum == null ? null : phoneNum.trim();
    }

    public String getProjcetIntroduce() {
        return projcetIntroduce;
    }

    public void setProjcetIntroduce(String projcetIntroduce) {
        this.projcetIntroduce = projcetIntroduce == null ? null : projcetIntroduce.trim();
    }

    public String getTeamIntroduce() {
        return teamIntroduce;
    }

    public void setTeamIntroduce(String teamIntroduce) {
        this.teamIntroduce = teamIntroduce == null ? null : teamIntroduce.trim();
    }

    public Boolean getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Boolean isRecommend) {
        this.isRecommend = isRecommend;
    }

	public Boolean getShowStatus() {
		return showStatus;
	}

	public void setShowStatus(Boolean showStatus) {
		this.showStatus = showStatus;
	}
}