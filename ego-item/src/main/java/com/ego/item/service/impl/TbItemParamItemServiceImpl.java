package com.ego.item.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemParamItemDubboService;
import com.ego.item.pojo.ParamItem;
import com.ego.item.pojo.ParamNode;
import com.ego.item.service.TbItemParamItemService;
import com.ego.pojo.TbItemParamItem;

@Service
public class TbItemParamItemServiceImpl implements TbItemParamItemService {

	@Reference
	private TbItemParamItemDubboService tbItemParamItemDubboServiceImpl;

	@Override
	public String showParam(long itemId) {
		TbItemParamItem item = tbItemParamItemDubboServiceImpl.selByItemId(itemId);
		List<ParamItem> list = JsonUtils.jsonToList(item.getParamData(), ParamItem.class);
		StringBuilder sb = new StringBuilder("");
		for (ParamItem paramItem : list) {
			sb.append("<table>");
			boolean groupSetted = false;
			for (ParamNode paramNode : paramItem.getParams()) {
				sb.append("<tr>");
				// 第一列（需要判断group是否已经填充至表格第一行，如果填充，则下一行不再输出）
				if (!groupSetted) {
					sb.append("<td width='200px' align='right'>" + paramItem.getGroup() + "</td>");
					groupSetted = true;
				} else {
					sb.append("<td></td>");
				}
				// 第二列
				sb.append("<td width='200px' align='right'>" + paramNode.getK() + "</td>");
				// 第三列
				sb.append("<td width='20px' align='right'></td>");
				// 第四列
				sb.append("<td width='200px' align='left'>" + paramNode.getV() + "</td>");
				sb.append("</tr>");
			}
			sb.append("</table>");
			sb.append("<hr/>");
		}
		return sb.toString();
	}

}
