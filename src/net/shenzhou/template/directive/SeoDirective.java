/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.template.directive;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import net.shenzhou.entity.Seo;
import net.shenzhou.entity.Seo.Type;
import net.shenzhou.service.SeoService;
import net.shenzhou.util.FreemarkerUtils;

import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - SEO设置
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Component("seoDirective")
public class SeoDirective extends BaseDirective {

	/** "类型"参数名称 */
	private static final String TYPE_PARAMETER_NAME = "type";

	/** 变量名称 */
	private static final String VARIABLE_NAME = "seo";

	@Resource(name = "seoServiceImpl")
	private SeoService seoService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Type type = FreemarkerUtils.getParameter(TYPE_PARAMETER_NAME, Type.class, params);

		Seo seo;
		boolean useCache = useCache(env, params);
		String cacheRegion = getCacheRegion(env, params);
		if (useCache) {
			seo = seoService.find(type, cacheRegion);
		} else {
			seo = seoService.find(type);
		}
		setLocalVariable(VARIABLE_NAME, seo, env, body);
	}

}