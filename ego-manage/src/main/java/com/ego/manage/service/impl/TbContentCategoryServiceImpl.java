package com.ego.manage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUiTree;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.IDUtils;
import com.ego.dubbo.service.TbContentCategoryDubboService;
import com.ego.manage.service.TbContentCategoryService;
import com.ego.pojo.TbContentCategory;

@Service
public class TbContentCategoryServiceImpl implements TbContentCategoryService {

	@Reference
	private TbContentCategoryDubboService tbContentCategoryDubboServiceImpl;

	@Override
	public List<EasyUiTree> showCategory(long id) {
		List<EasyUiTree> listTree = new ArrayList<EasyUiTree>();
		List<TbContentCategory> list = tbContentCategoryDubboServiceImpl.selByPid(id);
		for (TbContentCategory cat : list) {
			EasyUiTree tree = new EasyUiTree();
			tree.setId(cat.getId());
			tree.setText(cat.getName());
			tree.setState(cat.getIsParent() ? "closed" : "open");
			listTree.add(tree);
		}
		return listTree;
	}

	@Override
	public EgoResult create(TbContentCategory cat) {
		EgoResult er = new EgoResult();
		// 判断当前节点名称是否已经存在
		List<TbContentCategory> children = tbContentCategoryDubboServiceImpl.selByPid(cat.getParentId());
		for (TbContentCategory child : children) {
			if (child.getName().equals(cat.getName())) {
				er.setData("该分类名称已存在！");
				return er;
			}
		}
		Date date = new Date();
		cat.setCreated(date);
		cat.setUpdated(date);
		cat.setStatus(1);
		cat.setSortOrder(1);
		cat.setIsParent(false);
		long id = IDUtils.genItemId();
		cat.setId(id);
		int index = tbContentCategoryDubboServiceImpl.insTbContentCategory(cat);
		if (index > 0) {
			TbContentCategory parent = new TbContentCategory();
			parent.setId(cat.getParentId());
			parent.setIsParent(true);
			tbContentCategoryDubboServiceImpl.updIsParentById(parent);
		}
		er.setStatus(200);
		Map<String, Long> map = new HashMap<>();
		map.put("id", id);
		er.setData(map);
		return er;
	}

	@Override
	public EgoResult update(TbContentCategory cat) {
		EgoResult er = new EgoResult();
		// 查询当前节点的信息
		TbContentCategory catSelect = tbContentCategoryDubboServiceImpl.selById(cat.getId());
		// 查询当前节点的所有同级节点（包括自己）
		List<TbContentCategory> catSibList = tbContentCategoryDubboServiceImpl.selByPid(catSelect.getParentId());
		for (TbContentCategory sib : catSibList) {
			if (sib.getName().equals(cat.getName())) {
				er.setData("该分类名称已存在！");
				return er;
			}
		}
		int index = tbContentCategoryDubboServiceImpl.updIsParentById(cat);
		if (index > 0) {
			er.setStatus(200);
		}
		return er;
	}

	@Override
	public EgoResult delete(TbContentCategory cat) {
		EgoResult er = new EgoResult();
		cat.setStatus(0);
		int index = tbContentCategoryDubboServiceImpl.updIsParentById(cat);
		if (index > 0) {
			TbContentCategory fullCat = tbContentCategoryDubboServiceImpl.selById(cat.getId());
			List<TbContentCategory> catSibList = tbContentCategoryDubboServiceImpl.selByPid(fullCat.getParentId());
			if (catSibList == null || catSibList.size() == 0) {
				TbContentCategory parent = new TbContentCategory();
				parent.setId(cat.getParentId());
				parent.setIsParent(false);
				int result = tbContentCategoryDubboServiceImpl.updIsParentById(parent);
				if (result > 0) {
					er.setStatus(200);
				}
			} else {
				er.setStatus(200);
			}
		}
		return er;
	}

}
