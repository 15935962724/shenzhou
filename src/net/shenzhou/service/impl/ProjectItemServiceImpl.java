/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.shenzhou.dao.ProjectItemDao;
import net.shenzhou.entity.Project;
import net.shenzhou.entity.Project.Mode;
import net.shenzhou.entity.Project.ServiceGroup;
import net.shenzhou.entity.ProjectItem;
import net.shenzhou.service.ProjectItemService;

import org.springframework.stereotype.Service;

/**
 * Service - 项目项
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Service("projectItemServiceImpl")
public class ProjectItemServiceImpl extends BaseServiceImpl<ProjectItem, Long> implements ProjectItemService {

	@Resource(name = "projectItemDaoImpl")
	private ProjectItemDao projectItemDao;

	@Resource(name = "projectItemDaoImpl")
	public void setBaseDao(ProjectItemDao projectItemDao) {
		super.setBaseDao(projectItemDao);
	}

	@Override
	public List<ProjectItem> getByModeServiceGroup(Mode mode,
			ServiceGroup serviceGroup, Project project) {
		// TODO Auto-generated method stub
		return projectItemDao.getByModeServiceGroup(mode, serviceGroup, project);
	}

	@Override
	public List<ProjectItem> getProject(Project project) {
		
		return projectItemDao.getProject(project);
	}

	@Override
	public List<ProjectItem> getProjects(Project project, String startPrice, String endPrice) {
		// TODO Auto-generated method stub
		return projectItemDao.getProjects(project,startPrice,startPrice);
	}

	
}