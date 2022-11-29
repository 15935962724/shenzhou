/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.shenzhou.dao.ProjectItemDao;
import net.shenzhou.entity.Project;
import net.shenzhou.entity.Project.Mode;
import net.shenzhou.entity.Project.ServiceGroup;
import net.shenzhou.entity.ProjectItem;

import org.springframework.stereotype.Repository;
/**
 * Dao - 项目项
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Repository("projectItemDaoImpl")
public class ProjectItemDaoImpl extends BaseDaoImpl<ProjectItem, Long> implements ProjectItemDao {

	@Override
	public List<ProjectItem> getByModeServiceGroup(Mode mode,
			ServiceGroup serviceGroup, Project project) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProjectItem> criteriaQuery = criteriaBuilder.createQuery(ProjectItem.class);
		Root<ProjectItem> root = criteriaQuery.from(ProjectItem.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("project"), project));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("mode"), mode));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("isDeleted"), false));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("serviceGroup"), serviceGroup));
		criteriaQuery.where(restrictions);
		
	    return super.findList(criteriaQuery, null, null, null, null);
	}

	@Override
	public List<ProjectItem> getProject(Project project) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProjectItem> criteriaQuery = criteriaBuilder.createQuery(ProjectItem.class);
		Root<ProjectItem> root = criteriaQuery.from(ProjectItem.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("project"), project));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("isDeleted"), false));
		
		criteriaQuery.where(restrictions);
		
	    List<ProjectItem> projectItems = super.findList(criteriaQuery, null, null, null, null);
	    
	   if(projectItems.size()>0){
		   return projectItems;
	   }
	    
	   List<ProjectItem> eProjectItems = new ArrayList<ProjectItem>();
	   
	   return eProjectItems;
	   
	}
	
	
	@Override
	public List<ProjectItem> getProjects(Project project,String startPrice, String endPrice) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProjectItem> criteriaQuery = criteriaBuilder.createQuery(ProjectItem.class);
		Root<ProjectItem> root = criteriaQuery.from(ProjectItem.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("project"), project));
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("isDeleted"), false));
		/*if(startPrice != null && !startPrice.equals("") && endPrice == null || endPrice.equals("")){
			restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("price"), startPrice));
		}else{
			 BigDecimal bd = new BigDecimal(startPrice);  
			 BigDecimal bd1 = new BigDecimal(endPrice);  
			restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.between(root.<BigDecimal>get("price"), bd, bd1));
		}*/
		
		
		criteriaQuery.where(restrictions);
		
	    List<ProjectItem> projectItems = super.findList(criteriaQuery, null, null, null, null);
	    
	   if(projectItems.size()>0){
		   return projectItems;
	   }
	    
	   List<ProjectItem> eProjectItems = new ArrayList<ProjectItem>();
	   
	   return eProjectItems;
	   
	}
}