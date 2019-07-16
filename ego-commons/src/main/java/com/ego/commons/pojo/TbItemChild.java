package com.ego.commons.pojo;

import java.util.Arrays;

import com.ego.pojo.TbItem;

/**
 * 为了向前台传输【商品是否有货】及【商品的图片列表】而创建的一个表示【商品】的类，继承自【商品】类
 * @author 王以诚
 */
@SuppressWarnings("serial")
public class TbItemChild extends TbItem {

	private Boolean enough;

	private String[] images;

	public Boolean getEnough() {
		return enough;
	}

	public void setEnough(Boolean enough) {
		this.enough = enough;
	}

	public String[] getImages() {
		return images;
	}

	public void setImages(String[] images) {
		this.images = images;
	}

	@Override
	public String toString() {
		return "TbItemChild [enough=" + enough + ", images=" + Arrays.toString(images) + "]";
	}

	

}
