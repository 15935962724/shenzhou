/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.controller.mechanism;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.shenzhou.Pageable;
import net.shenzhou.entity.Book;
import net.shenzhou.entity.Doctor;
import net.shenzhou.service.BookService;
import net.shenzhou.service.DoctorService;

/**
 * 机构订单
 * 2017-7-10 15:46:41
 * @author wsr
 *
 */
@Controller("mechanismBookController")
@RequestMapping("/mechanism/book")
public class BookController extends BaseController {


	@Resource(name = "bookServiceImpl")
	private BookService bookService;

	@Resource(name = "doctorServiceImpl")
	private DoctorService doctorService;
	
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Book book,
			Pageable pageable, ModelMap model) {
//		User user = userService.getCurrent();
//		Mechanism mechanism = user.getMechanism();
		Doctor doctorC = doctorService.getCurrent();
		model.addAttribute("page", bookService.findPage(book, pageable));
		return "/mechanism/book/list";
	}

	
	
	
	
}