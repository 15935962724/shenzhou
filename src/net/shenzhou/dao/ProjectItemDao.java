/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao;

import java.util.List;

import net.shenzhou.entity.Project;
import net.shenzhou.entity.ProjectItem;
import net.shenzhou.entity.Project.Mode;
import net.shenzhou.entity.Project.ServiceGroup;

/**
 * Dao - 项目项
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public interface ProjectItemDao extends BaseDao<ProjectItem, Long> {

	List<ProjectItem> getByModeServiceGroup(Mode mode,ServiceGroup serviceGroup,Project project);
	
	List<ProjectItem> getProject(Project project);
	
	List<ProjectItem> getProjects(Project project,String startPrice, String endPrice);
	
}