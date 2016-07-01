package com.tianwen.dcdp.pojo;

public class Content {
    private Integer contentId;

    private Integer userId;

	private Long postTime;

    private String type;

    private Integer filetype;

    private Integer retid;

    private Integer replyid;

    private Short replytimes;

    private Short zftimes;

    private Boolean pinbi;

    private Boolean zhiding;

    private Short praisetimes;

    private String contentBody;
    
    private String nickName;
    
    private Integer showStatus;
    
    private String userName ;
    
    
    private String imageUrls ;
    
    public String getImageUrls() {
		return imageUrls;
	}

	public void setImageUrls(String imageUrls) {
		this.imageUrls = imageUrls;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	

    public Long getPostTime() {
		return postTime;
	}

	public void setPostTime(Long postTime) {
		this.postTime = postTime;
	}

    public Integer getShowStatus() {
		return showStatus;
	}

	public void setShowStatus(Integer showStatus) {
		this.showStatus = showStatus;
	}

	

	public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getFiletype() {
        return filetype;
    }

    public void setFiletype(Integer filetype) {
        this.filetype = filetype;
    }

    public Integer getRetid() {
        return retid;
    }

    public void setRetid(Integer retid) {
        this.retid = retid;
    }

    public Integer getReplyid() {
        return replyid;
    }

    public void setReplyid(Integer replyid) {
        this.replyid = replyid;
    }

    public Short getReplytimes() {
        return replytimes;
    }

    public void setReplytimes(Short replytimes) {
        this.replytimes = replytimes;
    }

    public Short getZftimes() {
        return zftimes;
    }

    public void setZftimes(Short zftimes) {
        this.zftimes = zftimes;
    }

    public Boolean getPinbi() {
        return pinbi;
    }

    public void setPinbi(Boolean pinbi) {
        this.pinbi = pinbi;
    }

    public Boolean getZhiding() {
        return zhiding;
    }

    public void setZhiding(Boolean zhiding) {
        this.zhiding = zhiding;
    }

    public Short getPraisetimes() {
        return praisetimes;
    }

    public void setPraisetimes(Short praisetimes) {
        this.praisetimes = praisetimes;
    }

    public String getContentBody() {
        return contentBody;
    }

    public void setContentBody(String contentBody) {
        this.contentBody = contentBody == null ? null : contentBody.trim();
    }
}