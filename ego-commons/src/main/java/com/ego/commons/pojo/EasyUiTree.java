package com.ego.commons.pojo;

/**
 * 用于EasyUI树显示的数据格式（详细说明请参考EasyUI的文档）
 * @author 王以诚
 */
public class EasyUiTree {

	private long id;
	private String text;
	private String state;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "EasyUiTree [id=" + id + ", text=" + text + ", state=" + state + "]";
	}

}
