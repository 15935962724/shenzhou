/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.template.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shenzhou.entity.JournalismCategory;
import net.shenzhou.service.JournalismCategoryService;

import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 顶级新闻分类列表
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Component("journalismCategoryRootListDirective")
public class JournalismCategoryRootListDirective extends BaseDirective {

	/** 变量名称 */
	private static final String VARIABLE_NAME = "journalismCategories";

	@Resource(name = "journalismCategoryServiceImpl")
	private JournalismCategoryService journalismCategoryService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		List<JournalismCategory> journalismCategories;
		boolean useCache = useCache(env, params);
		String cacheRegion = getCacheRegion(env, params);
		Integer count = getCount(params);
		if (useCache) {
			journalismCategories = journalismCategoryService.findRoots(count, cacheRegion);
		} else {
			journalismCategories = journalismCategoryService.findRoots(count);
		}
		setLocalVariable(VARIABLE_NAME, journalismCategories, env, body);
	}

}