/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.template.directive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shenzhou.Filter;
import net.shenzhou.Order;
import net.shenzhou.entity.Journalism;
import net.shenzhou.entity.JournalismCategory;
import net.shenzhou.entity.Tag;
import net.shenzhou.service.JournalismCategoryService;
import net.shenzhou.service.JournalismService;
import net.shenzhou.service.TagService;
import net.shenzhou.util.FreemarkerUtils;

import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 新闻列表
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Component("journalismListDirective")
public class JournalismListDirective extends BaseDirective {

	/** "新闻分类ID"参数名称 */
	private static final String ARTICLE_CATEGORY_ID_PARAMETER_NAME = "journalismCategoryId";

	/** "标签ID"参数名称 */
	private static final String TAG_IDS_PARAMETER_NAME = "tagIds";

	/** 变量名称 */
	private static final String VARIABLE_NAME = "journalisms";

	@Resource(name = "journalismServiceImpl")
	private JournalismService journalismService;
	@Resource(name = "journalismCategoryServiceImpl")
	private JournalismCategoryService journalismCategoryService;
	@Resource(name = "tagServiceImpl")
	private TagService tagService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Long journalismCategoryId = FreemarkerUtils.getParameter(ARTICLE_CATEGORY_ID_PARAMETER_NAME, Long.class, params);
		Long[] tagIds = FreemarkerUtils.getParameter(TAG_IDS_PARAMETER_NAME, Long[].class, params);

		JournalismCategory journalismCategory = journalismCategoryService.find(journalismCategoryId);
		List<Tag> tags = tagService.findList(tagIds);

		List<Journalism> journalisms;
		if ((journalismCategoryId != null && journalismCategory == null) || (tagIds != null && tags.isEmpty())) {
			journalisms = new ArrayList<Journalism>();
		} else {
			boolean useCache = useCache(env, params);
			String cacheRegion = getCacheRegion(env, params);
			Integer count = getCount(params);
			List<Filter> filters = getFilters(params, Journalism.class);
			List<Order> orders = getOrders(params);
			if (useCache) {
				journalisms = journalismService.findList(journalismCategory, tags, count, filters, orders, cacheRegion);
			} else {
				journalisms = journalismService.findList(journalismCategory, tags, count, filters, orders);
			}
		}
		setLocalVariable(VARIABLE_NAME, journalisms, env, body);
	}

}