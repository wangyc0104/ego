package com.ego.item.pojo;

/**
 * 【子参数节点】对象
 * @author 王以诚
 */
public class ParamNode {
	
	// 表示对象的键
	private String k;
	// 表示对象的值
	private String v;

	public String getK() {
		return k;
	}

	public void setK(String k) {
		this.k = k;
	}

	public String getV() {
		return v;
	}

	public void setV(String v) {
		this.v = v;
	}

	@Override
	public String toString() {
		return "ParamNode [k=" + k + ", v=" + v + "]";
	}

}
