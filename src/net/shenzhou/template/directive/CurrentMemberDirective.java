/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.template.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.annotation.Resource;

import net.shenzhou.entity.Member;
import net.shenzhou.service.MemberService;

import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 当前会员
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Component("currentMemberDirective")
public class CurrentMemberDirective extends BaseDirective {

	/** 变量名称 */
	private static final String VARIABLE_NAME = "currentMember";

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;

	@SuppressWarnings("rawtypes")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Member currentMember = memberService.getCurrent();
		if (body != null) {
			setLocalVariable(VARIABLE_NAME, currentMember, env, body);
		} else {
			if (currentMember != null) {
				Writer out = env.getOut();
				out.write(currentMember.getUsername());
			}
		}
	}

}