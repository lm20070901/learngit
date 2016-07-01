package com.tianwen.dcdp.pojo;

public class Groups {
    private Integer groupId;

    private String groupName;

    private String groupIntroduce;

    private Integer createUserId;

    private Integer createTime;

    private String iconPath;
    
    private Integer forbid;

    private Byte state;

    private Integer userCount;

    private Integer articleCount;
    
    private String suggestion;
    
    private String userName;  //用户名
    
    private String strCreateTime;
    
    private Integer orderNum;
    
    private Integer module;
    
    private Integer auditorId;
    
    public Integer getAuditorId() {
		return auditorId;
	}

	public void setAuditorId(Integer auditorId) {
		this.auditorId = auditorId;
	}

	public Integer getModule() {
		return module;
	}

	public void setModule(Integer module) {
		this.module = module;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getStrCreateTime() {
		return strCreateTime;
	}

	public void setStrCreateTime(String strCreateTime) {
		this.strCreateTime = strCreateTime;
	}

	public Integer getForbid() {
		return forbid;
	}

	public void setForbid(Integer forbid) {
		this.forbid = forbid;
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public String getGroupIntroduce() {
        return groupIntroduce;
    }

    public void setGroupIntroduce(String groupIntroduce) {
        this.groupIntroduce = groupIntroduce == null ? null : groupIntroduce.trim();
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath == null ? null : iconPath.trim();
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public Integer getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(Integer articleCount) {
        this.articleCount = articleCount;
    }
}