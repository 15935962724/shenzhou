/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service;

import java.util.List;

import net.shenzhou.entity.Project;
import net.shenzhou.entity.Project.Mode;
import net.shenzhou.entity.Project.ServiceGroup;
import net.shenzhou.entity.ProjectItem;

/**
 * Service - 项目项
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface ProjectItemService extends BaseService<ProjectItem, Long> {

	List<ProjectItem> getByModeServiceGroup(Mode mode,ServiceGroup serviceGroup,Project project);
	
	List<ProjectItem> getProject(Project project);
	
	List<ProjectItem> getProjects(Project project,String startPrice,String endPrice);
	
	
}