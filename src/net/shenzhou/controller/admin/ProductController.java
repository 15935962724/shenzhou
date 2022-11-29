/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.shenzhou.FileInfo.FileType;
import net.shenzhou.Message;
import net.shenzhou.Pageable;
import net.shenzhou.Setting;
import net.shenzhou.entity.Attribute;
import net.shenzhou.entity.Brand;
import net.shenzhou.entity.Coupon;
import net.shenzhou.entity.Coupon.GrantType;
import net.shenzhou.entity.Goods;
import net.shenzhou.entity.MemberRank;
import net.shenzhou.entity.Parameter;
import net.shenzhou.entity.ParameterGroup;
import net.shenzhou.entity.Product;
import net.shenzhou.entity.Product.OrderType;
import net.shenzhou.entity.ProductCategory;
import net.shenzhou.entity.ProductCoupon;
import net.shenzhou.entity.ProductImage;
import net.shenzhou.entity.Promotion;
import net.shenzhou.entity.Specification;
import net.shenzhou.entity.SpecificationValue;
import net.shenzhou.entity.Tag;
import net.shenzhou.entity.Tag.Type;
import net.shenzhou.service.BrandService;
import net.shenzhou.service.CouponService;
import net.shenzhou.service.FileService;
import net.shenzhou.service.GoodsService;
import net.shenzhou.service.MemberRankService;
import net.shenzhou.service.ProductCategoryService;
import net.shenzhou.service.ProductCouponService;
import net.shenzhou.service.ProductImageService;
import net.shenzhou.service.ProductService;
import net.shenzhou.service.PromotionService;
import net.shenzhou.service.SpecificationService;
import net.shenzhou.service.SpecificationValueService;
import net.shenzhou.service.TagService;
import net.shenzhou.util.SettingUtils;

/**
 * Controller - 商品
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Controller("adminProductController")
@RequestMapping("/admin/product")
public class ProductController extends BaseController {

	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;
	@Resource(name = "goodsServiceImpl")
	private GoodsService goodsService;
	@Resource(name = "brandServiceImpl")
	private BrandService brandService;
	@Resource(name = "promotionServiceImpl")
	private PromotionService promotionService;
	@Resource(name = "tagServiceImpl")
	private TagService tagService;
	@Resource(name = "memberRankServiceImpl")
	private MemberRankService memberRankService;
	@Resource(name = "productImageServiceImpl")
	private ProductImageService productImageService;
	@Resource(name = "specificationServiceImpl")
	private SpecificationService specificationService;
	@Resource(name = "specificationValueServiceImpl")
	private SpecificationValueService specificationValueService;
	@Resource(name = "fileServiceImpl")
	private FileService fileService;
	@Resource(name = "couponServiceImpl")
	private CouponService couponService;
	@Resource(name = "productCouponServiceImpl")
	private ProductCouponService productCouponService;
	
	
	/**
	 * 检查编号是否唯一
	 */
	@RequestMapping(value = "/check_sn", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkSn(String previousSn, String sn) {
		if (StringUtils.isEmpty(sn)) {
			return false;
		}
		if (productService.snUnique(previousSn, sn)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取参数组
	 */
	@RequestMapping(value = "/parameter_groups", method = RequestMethod.GET)
	public @ResponseBody
	Set<ParameterGroup> parameterGroups(Long id) {
		ProductCategory productCategory = productCategoryService.find(id);
		return productCategory.getParameterGroups();
	}

	/**
	 * 获取属性
	 */
	@RequestMapping(value = "/attributes", method = RequestMethod.GET)
	public @ResponseBody
	Set<Attribute> attributes(Long id) {
		ProductCategory productCategory = productCategoryService.find(id);
		return productCategory.getAttributes();
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("productCategoryTree", productCategoryService.findTree());
		model.addAttribute("brands", brandService.findAll());
		model.addAttribute("tags", tagService.findList(Type.product));
		model.addAttribute("memberRanks", memberRankService.findAll());
		model.addAttribute("specifications", specificationService.findAll());
		Map<String ,Object> query_map= new HashMap<String, Object>();
		query_map.put("isConduct", true);
		query_map.put("isEnd", false);
		query_map.put("isEnabled", true);
		query_map.put("grantType", GrantType.platform);
		List<Coupon> coupons = couponService.getCoupons(query_map);
		model.addAttribute("coupons", coupons);
		System.out.println(coupons.size());
		return "/admin/product/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Product product, Long productCategoryId, Long brandId, Long[] tagIds, Long[] specificationIds, HttpServletRequest request, RedirectAttributes redirectAttributes,
			Long [] couponIds,String safeguard) {
		String prices[] = request.getParameterValues("prices");
		String costs[] = request.getParameterValues("costs");
		String quantity[] = request.getParameterValues("quantity");
		String isMarketable = request.getParameter("isMarketable");//上架
		String stock = request.getParameter("stocks"); //库存
		String points = request.getParameter("points");//销售方式
		String sellOut = request.getParameter("sellOut");//售罄状态
		String safeguards = request.getParameter("safeguards");
		for (Iterator<ProductImage> iterator = product.getProductImages().iterator(); iterator.hasNext();) {
			ProductImage productImage = iterator.next();
			if (productImage == null || productImage.isEmpty()) {
				iterator.remove();
				continue;
			}
			if (productImage.getFile() != null && !productImage.getFile().isEmpty()) {
				if (!fileService.isValid(FileType.image, productImage.getFile())) {
					addFlashMessage(redirectAttributes, Message.error("admin.upload.invalid"));
					return "redirect:add.jhtml";
				}
			}
		}
		product.setProductCategory(productCategoryService.find(productCategoryId));
		product.setBrand(brandService.find(brandId));
		product.setTags(new HashSet<Tag>(tagService.findList(tagIds)));
		if (!isValid(product)) {
			return ERROR_VIEW;
		}
		if (StringUtils.isNotEmpty(product.getSn()) && productService.snExists(product.getSn())) {
			return ERROR_VIEW;
		}
		if (product.getMarketPrice() == null) {
			BigDecimal defaultMarketPrice = calculateDefaultMarketPrice(product.getPrice());
			product.setMarketPrice(defaultMarketPrice);
		}
		if (product.getPoint() == null) {
			long point = calculateDefaultPoint(product.getPrice());
			product.setPoint(point);
		}
		product.setFullName(null);
		product.setAllocatedStock(0);
		product.setScore(0F);
		product.setTotalScore(0L);
		product.setScoreCount(0L);
		product.setHits(0L);
		product.setWeekHits(0L);
		product.setMonthHits(0L);
		product.setSales(0L);
		product.setWeekSales(0L);
		product.setMonthSales(0L);
		product.setWeekHitsDate(new Date());
		product.setMonthHitsDate(new Date());
		product.setWeekSalesDate(new Date());
		product.setMonthSalesDate(new Date());
		product.setReviews(null);
		product.setConsultations(null);
		product.setFavoriteMembers(null);
		product.setPromotions(null);
		product.setCartItems(null);
		product.setOrderItems(null);
		product.setGiftItems(null);
		product.setProductNotifies(null);
		product.setStock(Integer.parseInt(stock));
		if(sellOut == null){
			product.setSellOut(false);
		}else{
			product.setSellOut(Boolean.getBoolean(sellOut));
		}
		
		product.setIsMarketable(Boolean.getBoolean(isMarketable));
		product.setSalesMethod(Boolean.getBoolean(points));
		product.setSafeguards(safeguards);
		for (MemberRank memberRank : memberRankService.findAll()) {
			String price = request.getParameter("memberPrice_" + memberRank.getId());
			if (StringUtils.isNotEmpty(price) && new BigDecimal(price).compareTo(new BigDecimal(0)) >= 0) {
				product.getMemberPrice().put(memberRank, new BigDecimal(price));
			} else {
				product.getMemberPrice().remove(memberRank);
			}
		}

		for (ProductImage productImage : product.getProductImages()) {
			productImageService.build(productImage);
		}
		Collections.sort(product.getProductImages());
		if (product.getImage() == null && product.getThumbnail() != null) {
			product.setImage(product.getThumbnail());
		}

		for (ParameterGroup parameterGroup : product.getProductCategory().getParameterGroups()) {
			for (Parameter parameter : parameterGroup.getParameters()) {
				String parameterValue = request.getParameter("parameter_" + parameter.getId());
				if (StringUtils.isNotEmpty(parameterValue)) {
					product.getParameterValue().put(parameter, parameterValue);
				} else {
					product.getParameterValue().remove(parameter);
				}
			}
		}

		for (Attribute attribute : product.getProductCategory().getAttributes()) {
			String attributeValue = request.getParameter("attribute_" + attribute.getId());
			if (StringUtils.isNotEmpty(attributeValue)) {
				product.setAttributeValue(attribute, attributeValue);
			} else {
				product.setAttributeValue(attribute, null);
			}
		}

		Goods goods = new Goods();
		List<Product> products = new ArrayList<Product>();
		if (specificationIds != null && specificationIds.length > 0) {
			for (int i = 0; i < specificationIds.length; i++) {
				Specification specification = specificationService.find(specificationIds[i]);
				String[] specificationValueIds = request.getParameterValues("specification_" + specification.getId());
				if (specificationValueIds != null && specificationValueIds.length > 0) {
					for (int j = 0; j < specificationValueIds.length; j++) {
						if (i == 0) {
							if (j == 0) {
								product.setGoods(goods);
								product.setSpecifications(new HashSet<Specification>());
								product.setSpecificationValues(new HashSet<SpecificationValue>());
								products.add(product);
							} else {
								Product specificationProduct = new Product();
								BeanUtils.copyProperties(product, specificationProduct);
								specificationProduct.setId(null);
								specificationProduct.setCreateDate(null);
								specificationProduct.setModifyDate(null);
								specificationProduct.setSn(null);
								specificationProduct.setFullName(null);
								specificationProduct.setAllocatedStock(0);
								specificationProduct.setIsList(false);
								specificationProduct.setScore(0F);
								specificationProduct.setTotalScore(0L);
								specificationProduct.setScoreCount(0L);
								specificationProduct.setHits(0L);
								specificationProduct.setWeekHits(0L);
								specificationProduct.setMonthHits(0L);
								specificationProduct.setSales(0L);
								specificationProduct.setWeekSales(0L);
								specificationProduct.setMonthSales(0L);
								specificationProduct.setWeekHitsDate(new Date());
								specificationProduct.setMonthHitsDate(new Date());
								specificationProduct.setWeekSalesDate(new Date());
								specificationProduct.setMonthSalesDate(new Date());
								specificationProduct.setGoods(goods);
								specificationProduct.setReviews(null);
								specificationProduct.setConsultations(null);
								specificationProduct.setFavoriteMembers(null);
								specificationProduct.setSpecifications(new HashSet<Specification>());
								specificationProduct.setSpecificationValues(new HashSet<SpecificationValue>());
								specificationProduct.setPromotions(null);
								specificationProduct.setCartItems(null);
								specificationProduct.setOrderItems(null);
								specificationProduct.setGiftItems(null);
								specificationProduct.setProductNotifies(null);
								products.add(specificationProduct);
							}
						}
						Product specificationProduct = products.get(j);
						SpecificationValue specificationValue = specificationValueService.find(Long.valueOf(specificationValueIds[j]));
						specificationProduct.getSpecifications().add(specification);
						specificationProduct.getSpecificationValues().add(specificationValue);
					}
				}
			}
		} else {
			product.setGoods(goods);
			product.setSpecifications(null);
			product.setSpecificationValues(null);
			products.add(product);
		}
		goods.getProducts().clear();
		goods.getProducts().addAll(products);
		goodsService.save(goods);
		
		//保存商品包含的优惠券
		for(int i=0;i<couponIds.length;i++){
			ProductCoupon pc = new ProductCoupon();
			pc.setCoupon(couponService.find(couponIds[i]));
			pc.setCreateDate(new Date());
			if(couponIds.length == 1){
				System.out.println(costs.length);
				pc.setCost(couponService.find(couponIds[i]).getReductionPrice());
			}else{
				pc.setCost(new BigDecimal(costs[i]));
				
			}
			for(int t=0;t<prices.length;t++){
				if(!prices[t].equals("")){
					pc.setPrice(new BigDecimal(prices[t]));
				}
			}
			for(int j=0;j<quantity.length;j++){
				if(!quantity[j].equals("")){
					pc.setQuantity(Integer.parseInt(quantity[j]));
				}
			}
			pc.setModifyDate(new Date());
			pc.setProduct(product);
			pc.setIsDeleted(false);
			productCouponService.save(pc);
			
		}
		
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("productCategoryTree", productCategoryService.findTree());
		model.addAttribute("brands", brandService.findAll());
		model.addAttribute("tags", tagService.findList(Type.product));
		model.addAttribute("memberRanks", memberRankService.findAll());
		model.addAttribute("specifications", specificationService.findAll());
		model.addAttribute("product", productService.find(id));
		Map<String ,Object> query_map= new HashMap<String, Object>();
		query_map.put("isConduct", true);
		query_map.put("isEnd", false);
		query_map.put("isEnabled", true);
		query_map.put("grantType", GrantType.platform);
		List<Coupon> coupons = couponService.getCoupons(query_map);
		model.addAttribute("coupons", coupons);
		List<ProductCoupon> productCoupons = productCouponService.findByProductId(productService.find(id));
		model.addAttribute("productCoupons", productCoupons);
		return "/admin/product/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Product product, Long productCategoryId, Long brandId, Long[] tagIds, Long[] specificationIds, Long[] specificationProductIds, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		for (Iterator<ProductImage> iterator = product.getProductImages().iterator(); iterator.hasNext();) {
			ProductImage productImage = iterator.next();
			if (productImage == null || productImage.isEmpty()) {
				iterator.remove();
				continue;
			}
			if (productImage.getFile() != null && !productImage.getFile().isEmpty()) {
				if (!fileService.isValid(FileType.image, productImage.getFile())) {
					addFlashMessage(redirectAttributes, Message.error("admin.upload.invalid"));
					return "redirect:edit.jhtml?id=" + product.getId();
				}
			}
		}
		product.setProductCategory(productCategoryService.find(productCategoryId));
		product.setBrand(brandService.find(brandId));
		product.setTags(new HashSet<Tag>(tagService.findList(tagIds)));
		if (!isValid(product)) {
			return ERROR_VIEW;
		}
		Product pProduct = productService.find(product.getId());
		if (pProduct == null) {
			return ERROR_VIEW;
		}
		if (StringUtils.isNotEmpty(product.getSn()) && !productService.snUnique(pProduct.getSn(), product.getSn())) {
			return ERROR_VIEW;
		}
		if (product.getMarketPrice() == null) {
			BigDecimal defaultMarketPrice = calculateDefaultMarketPrice(product.getPrice());
			product.setMarketPrice(defaultMarketPrice);
		}
		if (product.getPoint() == null) {
			long point = calculateDefaultPoint(product.getPrice());
			product.setPoint(point);
		}

		for (MemberRank memberRank : memberRankService.findAll()) {
			String price = request.getParameter("memberPrice_" + memberRank.getId());
			if (StringUtils.isNotEmpty(price) && new BigDecimal(price).compareTo(new BigDecimal(0)) >= 0) {
				product.getMemberPrice().put(memberRank, new BigDecimal(price));
			} else {
				product.getMemberPrice().remove(memberRank);
			}
		}

		for (ProductImage productImage : product.getProductImages()) {
			productImageService.build(productImage);
		}
		Collections.sort(product.getProductImages());
		if (product.getImage() == null && product.getThumbnail() != null) {
			product.setImage(product.getThumbnail());
		}

		for (ParameterGroup parameterGroup : product.getProductCategory().getParameterGroups()) {
			for (Parameter parameter : parameterGroup.getParameters()) {
				String parameterValue = request.getParameter("parameter_" + parameter.getId());
				if (StringUtils.isNotEmpty(parameterValue)) {
					product.getParameterValue().put(parameter, parameterValue);
				} else {
					product.getParameterValue().remove(parameter);
				}
			}
		}

		for (Attribute attribute : product.getProductCategory().getAttributes()) {
			String attributeValue = request.getParameter("attribute_" + attribute.getId());
			if (StringUtils.isNotEmpty(attributeValue)) {
				product.setAttributeValue(attribute, attributeValue);
			} else {
				product.setAttributeValue(attribute, null);
			}
		}

		Goods goods = pProduct.getGoods();
		List<Product> products = new ArrayList<Product>();
		if (specificationIds != null && specificationIds.length > 0) {
			for (int i = 0; i < specificationIds.length; i++) {
				Specification specification = specificationService.find(specificationIds[i]);
				String[] specificationValueIds = request.getParameterValues("specification_" + specification.getId());
				if (specificationValueIds != null && specificationValueIds.length > 0) {
					for (int j = 0; j < specificationValueIds.length; j++) {
						if (i == 0) {
							if (j == 0) {
								BeanUtils.copyProperties(product, pProduct, new String[] { "id", "createDate", "modifyDate", "fullName", "allocatedStock", "score", "totalScore", "scoreCount", "hits", "weekHits", "monthHits", "sales", "weekSales", "monthSales", "weekHitsDate", "monthHitsDate", "weekSalesDate", "monthSalesDate", "goods", "reviews", "consultations", "favoriteMembers",
										"specifications", "specificationValues", "promotions", "cartItems", "orderItems", "giftItems", "productNotifies" });
								pProduct.setSpecifications(new HashSet<Specification>());
								pProduct.setSpecificationValues(new HashSet<SpecificationValue>());
								products.add(pProduct);
							} else {
								if (specificationProductIds != null && j < specificationProductIds.length) {
									Product specificationProduct = productService.find(specificationProductIds[j]);
									if (specificationProduct == null || (specificationProduct.getGoods() != null && !specificationProduct.getGoods().equals(goods))) {
										return ERROR_VIEW;
									}
									specificationProduct.setSpecifications(new HashSet<Specification>());
									specificationProduct.setSpecificationValues(new HashSet<SpecificationValue>());
									products.add(specificationProduct);
								} else {
									Product specificationProduct = new Product();
									BeanUtils.copyProperties(product, specificationProduct);
									specificationProduct.setId(null);
									specificationProduct.setCreateDate(null);
									specificationProduct.setModifyDate(null);
									specificationProduct.setSn(null);
									specificationProduct.setFullName(null);
									specificationProduct.setAllocatedStock(0);
									specificationProduct.setIsList(false);
									specificationProduct.setScore(0F);
									specificationProduct.setTotalScore(0L);
									specificationProduct.setScoreCount(0L);
									specificationProduct.setHits(0L);
									specificationProduct.setWeekHits(0L);
									specificationProduct.setMonthHits(0L);
									specificationProduct.setSales(0L);
									specificationProduct.setWeekSales(0L);
									specificationProduct.setMonthSales(0L);
									specificationProduct.setWeekHitsDate(new Date());
									specificationProduct.setMonthHitsDate(new Date());
									specificationProduct.setWeekSalesDate(new Date());
									specificationProduct.setMonthSalesDate(new Date());
									specificationProduct.setGoods(goods);
									specificationProduct.setReviews(null);
									specificationProduct.setConsultations(null);
									specificationProduct.setFavoriteMembers(null);
									specificationProduct.setSpecifications(new HashSet<Specification>());
									specificationProduct.setSpecificationValues(new HashSet<SpecificationValue>());
									specificationProduct.setPromotions(null);
									specificationProduct.setCartItems(null);
									specificationProduct.setOrderItems(null);
									specificationProduct.setGiftItems(null);
									specificationProduct.setProductNotifies(null);
									products.add(specificationProduct);
								}
							}
						}
						Product specificationProduct = products.get(j);
						SpecificationValue specificationValue = specificationValueService.find(Long.valueOf(specificationValueIds[j]));
						specificationProduct.getSpecifications().add(specification);
						specificationProduct.getSpecificationValues().add(specificationValue);
					}
				}
			}
		} else {
			product.setSpecifications(null);
			product.setSpecificationValues(null);
			BeanUtils.copyProperties(product, pProduct, new String[] { "id", "createDate", "modifyDate", "fullName", "allocatedStock", "score", "totalScore", "scoreCount", "hits", "weekHits", "monthHits", "sales", "weekSales", "monthSales", "weekHitsDate", "monthHitsDate", "weekSalesDate", "monthSalesDate", "goods", "reviews", "consultations", "favoriteMembers", "promotions", "cartItems",
					"orderItems", "giftItems", "productNotifies" });
			products.add(pProduct);
		}
		goods.getProducts().clear();
		goods.getProducts().addAll(products);
		goodsService.update(goods);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Long productCategoryId, Long brandId, Long promotionId, Long tagId, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isGift, Boolean isOutOfStock, Boolean isStockAlert, Pageable pageable, ModelMap model) {
		ProductCategory productCategory = productCategoryService.find(productCategoryId);
		Brand brand = brandService.find(brandId);
		Promotion promotion = promotionService.find(promotionId);
		List<Tag> tags = tagService.findList(tagId);
		model.addAttribute("productCategoryTree", productCategoryService.findTree());
		model.addAttribute("brands", brandService.findAll());
		model.addAttribute("promotions", promotionService.findAll());
		model.addAttribute("tags", tagService.findList(Type.product));
		model.addAttribute("productCategoryId", productCategoryId);
		model.addAttribute("brandId", brandId);
		model.addAttribute("promotionId", promotionId);
		model.addAttribute("tagId", tagId);
		model.addAttribute("isMarketable", isMarketable);
		model.addAttribute("isList", isList);
		model.addAttribute("isTop", isTop);
		model.addAttribute("isGift", isGift);
		model.addAttribute("isOutOfStock", isOutOfStock);
		model.addAttribute("isStockAlert", isStockAlert);
		model.addAttribute("page", productService.findPage(productCategory, brand, promotion, tags, null, null, null, isMarketable, isList, isTop, isGift, isOutOfStock, isStockAlert, OrderType.dateDesc, pageable));
		return "/admin/product/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		productService.delete(ids);
		return SUCCESS_MESSAGE;
	}

	/**
	 * 计算默认市场价
	 * 
	 * @param price
	 *            价格
	 */
	private BigDecimal calculateDefaultMarketPrice(BigDecimal price) {
		Setting setting = SettingUtils.get();
//		Double defaultMarketPriceScale = setting.getDefaultMarketPriceScale();
		return setting.setScale(price);
	}

	/**
	 * 计算默认积分
	 * 
	 * @param price
	 *            价格
	 */
	private long calculateDefaultPoint(BigDecimal price) {
		Setting setting = SettingUtils.get();
		Double defaultPointScale = setting.getDefaultPointScale();
		return price.multiply(new BigDecimal(defaultPointScale.toString())).longValue();
	}

}