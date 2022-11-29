/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import net.shenzhou.dao.FeedbackDao;
import net.shenzhou.entity.Feedback;

import org.springframework.stereotype.Repository;

/**
 * 意见反馈
 * @date 2017-11-17 10:47:44
 * @author wsr
 *
 */
@Repository("feedbackDaoImpl")
public class FeedbackDaoImpl extends BaseDaoImpl<Feedback, Long> implements FeedbackDao {

}