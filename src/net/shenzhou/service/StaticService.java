/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import java.util.Map;

import net.shenzhou.entity.Article;
import net.shenzhou.entity.Journalism;
import net.shenzhou.entity.Product;

/**
 * Service - 静态化
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface StaticService {

	/**
	 * 生成静态
	 * 
	 * @param templatePath
	 *            模板文件路径
	 * @param staticPath
	 *            静态文件路径
	 * @param model
	 *            数据
	 * @return 生成数量
	 */
	int build(String templatePath, String staticPath, Map<String, Object> model);

	/**
	 * 生成静态
	 * 
	 * @param templatePath
	 *            模板文件路径
	 * @param staticPath
	 *            静态文件路径
	 * @return 生成数量
	 */
	int build(String templatePath, String staticPath);

	/**
	 * 生成静态
	 * 
	 * @param article
	 *            文章
	 * @return 生成数量
	 */
	int build(Article article);
	
	/**
	 * 生成静态
	 * 
	 * @param journalism
	 *            新闻
	 * @return 生成数量
	 */
	int build(Journalism journalism);

	/**
	 * 生成静态
	 * 
	 * @param product
	 *            商品
	 * @return 生成数量
	 */
	int build(Product product);

	/**
	 * 生成首页静态
	 * 
	 * @return 生成数量
	 */
	int buildIndex();

	/**
	 * 生成Sitemap
	 * 
	 * @return 生成数量
	 */
	int buildSitemap();

	/**
	 * 生成其它静态
	 * 
	 * @return 生成数量
	 */
	int buildOther();

	/**
	 * 生成所有静态
	 * 
	 * @return 生成数量
	 */
	int buildAll();

	/**
	 * 删除静态
	 * 
	 * @param staticPath
	 *            静态文件路径
	 * @return 删除数量
	 */
	int delete(String staticPath);

	/**
	 * 删除静态
	 * 
	 * @param article
	 *            文章
	 * @return 删除数量
	 */
	int delete(Article article);
	
	/**
	 * 删除静态
	 * 
	 * @param journalism
	 *            新闻
	 * @return 删除数量
	 */
	int delete(Journalism journalism);

	/**
	 * 删除静态
	 * 
	 * @param product
	 *            商品
	 * @return 删除数量
	 */
	int delete(Product product);

	/**
	 * 删除首页静态
	 * 
	 * @return 删除数量
	 */
	int deleteIndex();

	/**
	 * 删除其它静态
	 * 
	 * @return 删除数量
	 */
	int deleteOther();

}