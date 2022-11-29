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

import net.shenzhou.entity.JournalismCategory;
import net.shenzhou.service.JournalismCategoryService;
import net.shenzhou.util.FreemarkerUtils;

import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 上级新闻分类列表
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Component("journalismCategoryParentListDirective")
public class JournalismCategoryParentListDirective extends BaseDirective {
	/** "新闻分类ID"参数名称 */
	private static final String ARTICLE_CATEGORY_ID_PARAMETER_NAME = "journalismCategoryId";

	/** 变量名称 */
	private static final String VARIABLE_NAME = "journalismCategories";

	@Resource(name = "journalismCategoryServiceImpl")
	private JournalismCategoryService journalismCategoryService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Long journalismCategoryId = FreemarkerUtils.getParameter(ARTICLE_CATEGORY_ID_PARAMETER_NAME, Long.class, params);

		JournalismCategory journalismCategory = journalismCategoryService.find(journalismCategoryId);

		List<JournalismCategory> journalismCategories;
		if (journalismCategoryId != null && journalismCategory == null) {
			journalismCategories = new ArrayList<JournalismCategory>();
		} else {
			boolean useCache = useCache(env, params);
			String cacheRegion = getCacheRegion(env, params);
			Integer count = getCount(params);
			if (useCache) {
				journalismCategories = journalismCategoryService.findParents(journalismCategory, count, cacheRegion);
			} else {
				journalismCategories = journalismCategoryService.findParents(journalismCategory, count);
			}
		}
		setLocalVariable(VARIABLE_NAME, journalismCategories, env, body);
	}

}