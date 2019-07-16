package com.ego.manage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUiTree;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.manage.service.TbItemCatService;
import com.ego.pojo.TbItemCat;

@Service
public class TbItemCatServiceImpl implements TbItemCatService {

	@Reference
	private TbItemCatDubboService tbItemCatDubboServiceImpl;
	
	@Override
	public List<EasyUiTree> show(long pid) {
		List<TbItemCat> list = tbItemCatDubboServiceImpl.show(pid);		// 拿到所有的菜单
		List<EasyUiTree> listTree = new ArrayList<>();					// 新建一个用于展示的listTree
		for (TbItemCat cat : list) {									// 对于每个菜单
			EasyUiTree tree = new EasyUiTree();							// 都生成一个用于展示此菜单的tree项
			tree.setId(cat.getId());									// 设置tree项的id
			tree.setText(cat.getName());								// 设置tree项的文本为菜单名
			tree.setState(cat.getIsParent() ? "closed" : "open");		// 菜单为父菜单，就设置为closed，反之设置为open
			listTree.add(tree);											// 把tree项加到listTree中
		}
		return listTree;
	}

}
