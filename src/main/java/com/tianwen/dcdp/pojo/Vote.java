package com.tianwen.dcdp.pojo;

public class Vote {
    private Integer voteId;

    private Integer voteUid;

    private String voteTitle;

    private Byte voteType;

    private Long voteTime;

    private Byte resultVisible;

    private Long lastTime;

    private Integer voteTimes;

	private Integer replyNums;

    private Byte state;

    private String voteInfo;
    
    private Byte isInvite;
    
    private Integer inviteNums;
    
    private Integer showStatus;
    
    public Integer getShowStatus() {
		return showStatus;
	}

	public void setShowStatus(Integer showStatus) {
		this.showStatus = showStatus;
	}

	public Integer getInviteNums() {
		return inviteNums;
	}

	public void setInviteNums(Integer inviteNums) {
		this.inviteNums = inviteNums;
	}

	public Byte getIsInvite() {
		return isInvite;
	}

	public void setIsInvite(Byte isInvite) {
		this.isInvite = isInvite;
	}

	public Integer getVoteId() {
        return voteId;
    }

    public void setVoteId(Integer voteId) {
        this.voteId = voteId;
    }

    public Integer getVoteUid() {
        return voteUid;
    }

    public void setVoteUid(Integer voteUid) {
        this.voteUid = voteUid;
    }

    public String getVoteTitle() {
        return voteTitle;
    }

    public void setVoteTitle(String voteTitle) {
        this.voteTitle = voteTitle == null ? null : voteTitle.trim();
    }

    public Byte getVoteType() {
        return voteType;
    }

    public void setVoteType(Byte voteType) {
        this.voteType = voteType;
    }

    public Long getVoteTime() {
        return voteTime;
    }

    public void setVoteTime(Long voteTime) {
        this.voteTime = voteTime;
    }

    public Byte getResultVisible() {
        return resultVisible;
    }

    public void setResultVisible(Byte resultVisible) {
        this.resultVisible = resultVisible;
    }

    public Long getLastTime() {
        return lastTime;
    }

    public void setLastTime(Long lastTime) {
        this.lastTime = lastTime;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }
    
    public Integer getVoteTimes() {
		return voteTimes;
	}
    
    public String getVoteInfo() {
        return voteInfo;
    }
    
	public void setVoteTimes(Integer voteTimes) {
		this.voteTimes = voteTimes;
	}

	public Integer getReplyNums() {
		return replyNums;
	}

	public void setReplyNums(Integer replyNums) {
		this.replyNums = replyNums;
	}


    public void setVoteInfo(String voteInfo) {
        this.voteInfo = voteInfo == null ? null : voteInfo.trim();
    }
}