/*
 * Copyright 2005-2013 share.net. All rights reserved.
 * Support: http://www.share.net
 * License: http://www.share.net/license
 */
package net.shenzhou.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.dao.BookDao;
import net.shenzhou.entity.Balance;
import net.shenzhou.entity.Book;
import net.shenzhou.service.BookService;

/**
 * Service - 余额
 * @author wsr
 * @date 2018-3-19 16:02:46
 */
@Service("bookServiceImpl")
public class BookServiceImpl extends BaseServiceImpl<Book, Long> implements BookService {
	
	@Resource(name = "bookDaoImpl")
	public void setBaseDao(BookDao bookDao) {
		super.setBaseDao(bookDao);
	}

	@Resource(name = "bookDaoImpl")
	private BookDao bookDao;

	@Override
	public Page<Book> findPage(Book book, Pageable pageable) {
		// TODO Auto-generated method stub
		return bookDao.findPage(book, pageable);
	}

	

}