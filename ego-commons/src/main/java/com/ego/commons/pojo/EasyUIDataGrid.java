package com.ego.commons.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 用于EasyUI表格显示的数据格式（详细说明请参考EasyUI的文档）
 * @author 王以诚
 */
public class EasyUIDataGrid implements Serializable {
	
	// 当前页显示数据
	private List<?> rows;
	// 总条数
	private long total;

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "EasyUIDataGrid [rows=" + rows + ", total=" + total + "]";
	}

}
