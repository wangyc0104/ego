package com.ego.item.pojo;

import java.util.List;

/**
 * 【商品总参数】对象
 * @author 王以诚
 */
public class ParamItem {

	// 对象所属的组
	private String group;
	// 对象所拥有的参数对象列表
	private List<ParamNode> params;

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public List<ParamNode> getParams() {
		return params;
	}

	public void setParams(List<ParamNode> params) {
		this.params = params;
	}

	@Override
	public String toString() {
		return "ParamItem [group=" + group + ", params=" + params + "]";
	}

}
