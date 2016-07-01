package com.tianwen.dcdp.common;

import java.util.HashMap;
import java.util.Map;

public enum GroupsState {

	PUBLISHED(1, "已审核"),PENDING(2, "待审核"),NOT_PASSED(3, "被拒绝");
	
	private int value;
	
	private String desc;
	
	GroupsState(int value, String desc) {
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
		for(GroupsState state : values()) {
			map.put(state.getValue(), state.getDesc());
		}
	}
	
	public static Map<Integer, String> getAllState() {
		return map;
	}
	/*public static Map<Integer, String> getAllState() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		for(ArticleState state : values()) {
			map.put(state.getValue(), state.getDesc());
		}
		return map;
	}*/
}
