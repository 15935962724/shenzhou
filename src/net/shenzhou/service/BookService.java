package net.shenzhou.service;

import net.shenzhou.Page;
import net.shenzhou.Pageable;
import net.shenzhou.entity.Book;

public interface BookService extends BaseService<Book, Long> {
	
	
	Page<Book> findPage(Book book, Pageable pageable);
	
	
	
	
	
}

