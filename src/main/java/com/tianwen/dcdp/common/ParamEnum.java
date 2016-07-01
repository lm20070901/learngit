package com.tianwen.dcdp.common;

import java.util.HashMap;
import java.util.Map;

public abstract class ParamEnum {
	// 可视状态
	public enum VisibleState {

		VISIBLE(0, "可见"), HIDING(1, "不可见");

		private int value;
		private String desc;

		VisibleState(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public int getValue() {
			return value;
		}

		public String getDesc() {
			return desc;
		}

	}

	/**
	 * 资讯类型
	 * 
	 * 
	 */
	public enum ArticleType {

		ARTICLE(0,"文章"),MULTIPLE_PICTURE(1,"多图"),
		FOCUS_PICTURE(2,"焦点图"),VIDEO(3,"视频");

		private int value;
		private String desc;
		private static Map<Integer, String> map = new HashMap<Integer, String>();
		
		static {
			for(ArticleType type : values()) {
				map.put(type.getValue(), type.getDesc());
			}
		}
		ArticleType(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public int getValue() {
			return value;
		}

		public String getDesc() {
			return desc;
		}
		
		public static Map<Integer, String> getMap() {
			return map;
		}
	}

	/**
	 * 资讯状态
	 * 
	 * @author
	 * 
	 */
	public enum ArticleState {

		PUBLISHED(1, "已发布"), PENDING(2, "待审核"), NOT_PASSED(3, "未通过");

		private int value;

		private String desc;

		ArticleState(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public int getValue() {
			return value;
		}

		public String getDesc() {
			return desc;
		}

		private static Map<Integer, String> map = new HashMap<Integer, String>();
		static {
			for (ArticleState state : values()) {
				map.put(state.getValue(), state.getDesc());
			}
		}

		public static Map<Integer, String> getAllState() {
			return map;
		}
		/*
		 * public static Map<Integer, String> getAllState() { Map<Integer,
		 * String> map = new HashMap<Integer, String>(); for(ArticleState state
		 * : values()) { map.put(state.getValue(), state.getDesc()); } return
		 * map; }
		 */
	}
	
	/**
	 * 索引类型
	 * @author yx
	 *
	 */
	public enum IdxType {

		ARTICLE(1, "资讯列表"), DYNAMIC(2, "动态管理"), QUESTION(3, "问题管理"),
		GROUP(4, "小组列表");

		private int value;

		private String desc;

		IdxType(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public int getValue() {
			return value;
		}

		public String getDesc() {
			return desc;
		}

		private static Map<Integer, String> map = new HashMap<Integer, String>();
		static {
			for (IdxType state : values()) {
				map.put(state.getValue(), state.getDesc());
			}
		}

		public static Map<Integer, String> getAllState() {
			return map;
		}
	}

	/**
	 * 问卷类型
	 * 
	 * @author jiangyx
	 * 
	 */
	public enum VoteType {

		SYSTEM(0, "系统创建"), USER(1, "用户创建");

		private int value;
		private String desc;

		VoteType(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public int getValue() {
			return value;
		}

		public String getDesc() {
			return desc;
		}

	}

	/**
	 * 问题类型
	 * 
	 * @author jiangyx
	 * 
	 */
	public enum QuestionType {

		SINGLE_CHOICE(0, "单选题"), MULTI_CHOICE(1, "多选题"), GAP_FILLING(2, "填空题");

		private int value;
		private String desc;

		QuestionType(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public int getValue() {
			return value;
		}

		public String getDesc() {
			return desc;
		}
	}
	
	/**
	 * 积分规则
	 * @author yinz
	 *
	 */
	public enum Period{
		
		DISPOSABLE(1,"一次性"),EVERY_DAY(2,"每天 ");

		private int value;

		private String desc;

		private static Map<Integer, String> map = new HashMap<Integer, String>();

		static {
			for (Period p : values()) {
				map.put(p.getValue(), p.getDesc());
			}
		}

		Period(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public static Map<Integer, String> getMap() {
			return map;
		}

	}

	
	/**
	 * 是否
	 * @author jiangyx
	 *
	 */
	public enum IsOrNot {

		YES(1, "是"), NO(0, "否 ");
		private int value;

		private String desc;

		private static Map<Integer, String> map = new HashMap<Integer, String>();

		static {
			for (IsOrNot state : values()) {
				map.put(state.getValue(), state.getDesc());
			}
		}

		IsOrNot(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public static Map<Integer, String> getMap() {
			return map;
		}

	}
	
	/**
	 * 发布状态
	 * @author jiangyx
	 *
	 */
	public enum PublishStatus {

		PENDING_PUBLISH (0, "待发布"), PUBLISHED(1, "已发布");
		private int value;

		private String desc;

		PublishStatus(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}


	}
	
	/**
	 * 采纳状态
	 * @author jiangyx
	 *
	 */
	public enum AdoptStatus{
		
		PENDING_ADOPT (0, "待采纳"), ADOPTED(1, "已采纳") ,REJECTED(2, "未采纳");
		
		private int value;
		
		private String desc;
		
		AdoptStatus(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
	}
	
	/**
	 * 日志操作类型，对应admin_log表字段
	 * @author yinz
	 *
	 */
	public enum LogOperType {
		OTHERS(0,"其他"),CREATE(1,"新增"),DELETE(2,"删除"),UPDATE(3,"修改"),AUDIT(4,"审核发布"),SHOW_OR_HIDE(5,"显示/隐藏"),
		ENABLE_OD_DISABLE(6,"启用/禁用"),LOCK_OR_UNLOCK(7,"锁定/解锁"),RESET(8,"重置"),DISTRIBUTE_ACTION(9,"分配权限");
		private int value;
		
		private String desc;
		
		private static Map<Integer, String> map = new HashMap<Integer, String>();
		
		static {
			for(LogOperType type : values()) {
				map.put(type.getValue(), type.getDesc());
			}
		}
		
		LogOperType(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
		
		public static Map<Integer, String> getMap() {
			return map;
		}
	}
	
	/**
	 * 问答分类模块
	 */
	public enum QuestionModule{
		FINANCIAL(1,"理财问答"), INVESTMENT(2,"投资问答");
		private int value;
		private String desc;
		
		private static Map<Integer, String> map = new HashMap<Integer, String>();
		
		static {
			for(QuestionModule q : values()) {
				map.put(q.getValue(), q.getDesc());
			}
		}
		QuestionModule(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
		
		public static Map<Integer, String> getMap() {
			return map;
		}
		
		public static QuestionModule getEnum(int val) {
			for(QuestionModule q : values()) {
				if(q.getValue() == val) {
					return q;
				}
			}
			return null;
		}
	}
}
