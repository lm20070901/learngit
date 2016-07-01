package com.tianwen.dcdp.pojo;

public class FocusPicture {
    private Integer picId;

    private String title;

    private String linkDir;

    private Byte isVisible;

    private String relatedLink;

    private Integer relatedArticleId;

    private Byte contentType;
    
    private String articleTitle; //关联的资讯的标题

    
    public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public Integer getPicId() {
        return picId;
    }

    public void setPicId(Integer picId) {
        this.picId = picId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getLinkDir() {
        return linkDir;
    }

    public void setLinkDir(String linkDir) {
        this.linkDir = linkDir == null ? null : linkDir.trim();
    }

    public Byte getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Byte isVisible) {
        this.isVisible = isVisible;
    }

    public String getRelatedLink() {
        return relatedLink;
    }

    public void setRelatedLink(String relatedLink) {
        this.relatedLink = relatedLink == null ? null : relatedLink.trim();
    }

    public Integer getRelatedArticleId() {
        return relatedArticleId;
    }

    public void setRelatedArticleId(Integer relatedArticleId) {
        this.relatedArticleId = relatedArticleId;
    }

    public Byte getContentType() {
        return contentType;
    }

    public void setContentType(Byte contentType) {
        this.contentType = contentType;
    }
}