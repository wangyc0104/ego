package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbItemParamDubboService;
import com.ego.mapper.TbItemParamMapper;
import com.ego.pojo.TbItemParam;
import com.ego.pojo.TbItemParamExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class TbItemParamDubboServiceImpl implements TbItemParamDubboService {

	@Resource
	private TbItemParamMapper tbItemParamMapper;
	
	@Override
	public EasyUIDataGrid showPage(int page, int rows) {
		// 先设置分页条件
		PageHelper.startPage(page, rows);
		// 查询全部
		// XXXexample设置了东西， 就相当于在sql语句的where从句中添加了内容
		
		// 如果表中有一个或一个以上的列为text类型，则生成的方法xxxWithBlobs()
		// 如果使用xxxWithBlobs() 查询结果中包含带有text类的列
		// 如果没有使用xxxWithBlobs()的方法，则不带有text类型
		List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(new TbItemParamExample());
		PageInfo<TbItemParam> pi = new PageInfo<>(list);
		// 设置方法返回结果
		EasyUIDataGrid datagrid = new EasyUIDataGrid();
		datagrid.setRows(pi.getList());
		datagrid.setTotal(pi.getTotal());
		return datagrid;
	}

	@Override
	public int delByIds(String ids) throws Exception {
		int index = 0;
		String[] idStr = ids.split(",");
		for (String id : idStr) {
			index += tbItemParamMapper.deleteByPrimaryKey(Long.parseLong(id));
		}
		if (index == idStr.length) {
			return 1;
		} else {
			throw new Exception("数据可能已经不存在！");
		}
	}

	@Override
	public TbItemParam selByCatId(long catId) {
		TbItemParamExample example = new TbItemParamExample();
		example.createCriteria().andItemCatIdEqualTo(catId);
		List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(example);
		if (list != null && list.size() > 0) {
			// 要求每个类目只能有一个模板
			return list.get(0);
		}
		return null;
	}

	@Override
	public int insParam(TbItemParam param) {
		return tbItemParamMapper.insertSelective(param);
	}

}
