package com.tianwen.dcdp.pojo;

public class QuestionCategory {
    private Integer categoryId;

    private String categoryName;

    private Integer questionCount;

    private Byte isVisible;

    private String icon;
    
    private byte modelId;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    public Integer getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Integer questionCount) {
        this.questionCount = questionCount;
    }

    public Byte getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Byte isVisible) {
        this.isVisible = isVisible;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

	public byte getModelId() {
		return modelId;
	}

	public void setModelId(byte modelId) {
		this.modelId = modelId;
	}
    
    
}