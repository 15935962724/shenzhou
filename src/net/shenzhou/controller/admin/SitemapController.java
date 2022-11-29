/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.admin;

import javax.annotation.Resource;

import net.shenzhou.Template;
import net.shenzhou.service.StaticService;
import net.shenzhou.service.TemplateService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - Sitemap
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("adminSitemapController")
@RequestMapping("/admin/sitemap")
public class SitemapController extends BaseController {

	@Resource(name = "templateServiceImpl")
	private TemplateService templateService;
	@Resource(name = "staticServiceImpl")
	private StaticService staticService;

	/**
	 * 生成Sitemap
	 */
	@RequestMapping(value = "/build", method = RequestMethod.GET)
	public String build(ModelMap model) {
		Template sitemapIndexTemplate = templateService.get("sitemapIndex");
		model.addAttribute("sitemapIndexPath", sitemapIndexTemplate.getStaticPath());
		return "/admin/sitemap/build";
	}

	/**
	 * 生成Sitemap
	 */
	@RequestMapping(value = "/build", method = RequestMethod.POST)
	public String build(RedirectAttributes redirectAttributes) {
		staticService.buildSitemap();
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:build.jhtml";
	}

}