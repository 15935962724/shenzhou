/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import javax.annotation.Resource;

import net.shenzhou.dao.FeedbackDao;
import net.shenzhou.entity.Feedback;
import net.shenzhou.service.FeedbackService;

import org.springframework.stereotype.Service;

/**
 * 意见反馈
 * @date 2017-11-17 10:50:17
 * @author wsr
 *
 */
@Service("feedbackServiceImpl")
public class FeedbackServiceImpl extends BaseServiceImpl<Feedback, Long> implements FeedbackService {

	@Resource(name = "feedbackDaoImpl")
	private FeedbackDao feedbackDao;

	@Resource(name = "feedbackDaoImpl")
	public void setBaseDao(FeedbackDao feedbackDao) {
		super.setBaseDao(feedbackDao);
	}


}