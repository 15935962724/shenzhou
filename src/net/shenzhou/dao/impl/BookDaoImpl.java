/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import java.math.BigDecimal;
import java.util.Collections;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.mchange.lang.StringUtils;

import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.dao.BookDao;
import net.shenzhou.entity.Book;
import net.shenzhou.entity.Order;
import net.shenzhou.util.StringUtil;

/**
 * Dao - 地区
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Repository("bookDaoImpl")
public class BookDaoImpl extends BaseDaoImpl<Book, Long> implements BookDao {

	public Page<Book> findPage(Book book, Pageable pageable) {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
		Root<Book> root = criteriaQuery.from(Book.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		

//		/** 名称 */
//		private String name;
//		
//		/** 类型 */
//		private String type;
//
//		/** 作者 */
//		private String author;
//		
//		/**单价*/
//		private BigDecimal price;
//		
//		/**出版社*/
//		private String press;
//		
//		/**状态*/
//		private String status;
//		
//		/**数量*/
//		private Integer number;
		
        if(StringUtil.isNull(book.getSn())) {
	    restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("sn"), book.getSn()));	
		}
        if(StringUtil.isNull(book.getName())) {
    	    restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("sn"), book.getSn()));	
    	}
        if(StringUtil.isNull(book.getAuthor())) {
    	    restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("sn"), book.getSn()));	
    	}
        if(StringUtil.isNull(book.getType())) {
    	    restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("sn"), book.getSn()));	
    	}
        if(StringUtil.isNull(book.getPress())) {
    	    restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("sn"), book.getSn()));	
    	}
        
        if(StringUtil.isNull(book.getStatus())) {
    	    restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("sn"), book.getSn()));	
    	}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}
	
	
	
}