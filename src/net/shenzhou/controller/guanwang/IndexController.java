/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.guanwang;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.shenzhou.Setting;
import net.shenzhou.entity.ArticleCategory;
import net.shenzhou.entity.Expert;
import net.shenzhou.entity.FriendLink;
import net.shenzhou.entity.Journalism;
import net.shenzhou.entity.Tag;
import net.shenzhou.entity.Tag.Type;
import net.shenzhou.service.ArticleCategoryService;
import net.shenzhou.service.ExpertService;
import net.shenzhou.service.FriendLinkService;
import net.shenzhou.service.JournalismService;
import net.shenzhou.service.TagService;
import net.shenzhou.util.SettingUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 官网首页
 * @date 2017-10-12 14:04:45
 * @author wsr
 *
 */
@Controller("guanwangIndexController")
@RequestMapping("/guanwang/index")
public class IndexController extends BaseController {

	@Resource(name = "articleCategoryServiceImpl")
	private ArticleCategoryService articleCategoryService;
	@Resource(name = "friendLinkServiceImpl")
	private FriendLinkService friendLinkService;
	@Resource(name = "tagServiceImpl")
	private TagService tagService;
	@Resource(name = "journalismServiceImpl")
	private JournalismService journalismService;
	@Resource(name = "expertServiceImpl")
	private ExpertService expertService;
	
	
	/**
	 * 北京神州儿女健康管理有限责任公司官网首页
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/shenzhou", method = RequestMethod.GET)
	public String shenzhou(ModelMap model) {
		System.out.println("进入北京神州儿女健康管理有限责任公司官网首页");
		Setting setting = SettingUtils.get();
		List<ArticleCategory> articleCategorys = articleCategoryService.findTree();//文章分类
		List<FriendLink> friendLinks = friendLinkService.findAll();//友情链接
		List<Tag> tags = tagService.findList(Type.article);//文章标签
		Tag tag = tagService.find(6l);
		List<Tag> tagList = new ArrayList<Tag>();
		tagList.add(tag);
		List<Journalism> journalismList = journalismService.findAll();//取出所有的新闻
		List<Journalism> journalisms = journalismService.findList(null, tagList, null, null, null);//取出标签为推荐的新闻
		List<Expert> experts = expertService.findAll();//所有专家
		
		for (Journalism journalism : journalismList) {
			if (journalism.getIsTop()&&journalism.getIsPublication()) {
				model.addAttribute("journalismTop", journalism);//取出置顶的新闻
				break;
			}
		}
		
		model.addAttribute("experts", experts);
		model.addAttribute("journalisms", journalisms);
		model.addAttribute("journalismList", journalismList);
		model.addAttribute("tags", tags);
		model.addAttribute("friendLinks", friendLinks);
		model.addAttribute("articleCategorys", articleCategorys);
		model.addAttribute("setting", setting);
		return "/guanwang/shenzhou/index";
	}
	


	/**
	 * 好康护让健康简单一些官网首页
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/haokanghu", method = RequestMethod.GET)
	public String haokanghu(ModelMap model) {
		System.out.println("进入好康护让健康简单一些官网首页");
		return "/guanwang/haokanghu/index";
	}
	
	
	
	
}