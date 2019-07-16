package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ego.dubbo.service.TbContentCategoryDubboService;
import com.ego.mapper.TbContentCategoryMapper;
import com.ego.pojo.TbContentCategory;
import com.ego.pojo.TbContentCategoryExample;

public class TbContentCategoryDubboServiceImpl implements TbContentCategoryDubboService {

	@Resource
	private TbContentCategoryMapper tbContentCategoryMapper;
	
	@Override
	public List<TbContentCategory> selByPid(long pid) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		example.createCriteria().andParentIdEqualTo(pid).andStatusEqualTo(1);
		return tbContentCategoryMapper.selectByExample(example);
	}

	@Override
	public int insTbContentCategory(TbContentCategory cat) {
		return tbContentCategoryMapper.insertSelective(cat);
	}

	@Override
	public int updIsParentById(TbContentCategory cat) {
		return tbContentCategoryMapper.updateByPrimaryKeySelective(cat);
	}

	@Override
	public TbContentCategory selById(long id) {
		return tbContentCategoryMapper.selectByPrimaryKey(id);
	}

}
