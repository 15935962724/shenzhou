/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao;

import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.entity.Area;
import net.shenzhou.entity.Book;

/**
 * Dao - 订单
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface BookDao extends BaseDao<Book, Long> {


	Page<Book> findPage(Book book, Pageable pageable);

	
	 
}
