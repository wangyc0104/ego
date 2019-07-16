package com.ego.commons.pojo;

/**
 * 返回前台的状态数据
 * @author 王以诚
 */
public class EgoResult {
	
	// 数据处理状态
	private int status;
	// 需要传输的一些数据
	private Object data;
	// 用于解释状态的文本
	private String msg;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "EgoResult [status=" + status + ", data=" + data + ", msg=" + msg + "]";
	}

}
