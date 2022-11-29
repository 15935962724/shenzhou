/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.shenzhou.Config;
import net.shenzhou.Principal;
import net.shenzhou.dao.DoctorDao;
import net.shenzhou.dao.DoctorMechanismRelationDao;
import net.shenzhou.dao.MechanismDao;
import net.shenzhou.dao.ProjectDao;
import net.shenzhou.dao.ServerProjectCategoryDao;
import net.shenzhou.dao.UserDao;
import net.shenzhou.entity.Doctor;
import net.shenzhou.entity.DoctorMechanismRelation;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Project;
import net.shenzhou.entity.User;
import net.shenzhou.service.MechanismService;
import net.shenzhou.util.JsonUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 
 * 机构
 * @author wsr
 *
 */
@Service("mechanismServiceImpl")
public class MechanismServiceImpl extends BaseServiceImpl<Mechanism, Long> implements MechanismService {

	@Resource(name = "mechanismDaoImpl")
	private MechanismDao mechanismDao;
	@Resource(name = "doctorDaoImpl")
	private DoctorDao doctorDao;
	@Resource(name = "projectDaoImpl")
	private ProjectDao projectDao;
	@Resource(name = "serverProjectCategoryDaoImpl")
	private ServerProjectCategoryDao serverProjectCategoryDao;
	@Resource(name = "doctorMechanismRelationDaoImpl")
	private DoctorMechanismRelationDao doctorMechanismRelationDao;
	@Resource(name = "userDaoImpl")
	private UserDao userDao;
	
	
	@Resource(name = "mechanismDaoImpl")
	public void setBaseDao(MechanismDao mechanismDao) {
		super.setBaseDao(mechanismDao);
	}

	@Override
	public List<Mechanism> findList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mechanismDao.findList(map);
	}

	@Override
	public Map<String, Object> findList(String file) {
		// TODO Auto-generated method stub
		return mechanismDao.findList(file);
	}

	@Override
	public Double findObject(Mechanism mechanism) {
		// TODO Auto-generated method stub
		return mechanismDao.findObject(mechanism);
	}

	@Override
	public List<Mechanism> searchByName(String mechanismName) {
		// TODO Auto-generated method stub
		return mechanismDao.searchByName(mechanismName);
	}

	@Override
	public List<Mechanism> findMechanismList(Integer page) {
		// TODO Auto-generated method stub
		return mechanismDao.findMechanismList(page);
	}

	@Override
	public Map<String, Object> mechanismFiltrate(String file) {
		Map<String ,Object> map = new HashMap<String ,Object>();
		Map<String,Object> data_map = new HashMap<String, Object>();
		JSONObject json = JSONObject.fromObject(file);
		String doctorName = json.getString("doctorName");
		List<Project> project_list = projectDao.projectFiltrate(file);
		List<Mechanism> mechanism_list = new ArrayList<Mechanism>();
		
		for(Project project : project_list){
			if(!mechanism_list.contains(project.getMechanism())){
				mechanism_list.add(project.getMechanism());
			}
		}
		
		if(!StringUtils.isEmpty(doctorName)){
			Doctor doctor = doctorDao.findByName(doctorName);
			if(doctor!=null){
				List<DoctorMechanismRelation> list = doctor.getDoctorMechanismRelations(net.shenzhou.entity.DoctorMechanismRelation.Audit.succeed);
					for(DoctorMechanismRelation doctorMechanismRelation : list){
						if(!mechanism_list.contains(doctorMechanismRelation.getMechanism())){
								mechanism_list.add(doctorMechanismRelation.getMechanism());
						}
					}
			}
		}
		
		Integer pageNumber = json.getInt("pageNumber");//页码
		Integer pageSize = Config.pageSize;
		
		String status = "200";
		String message = "第"+pageNumber+"页数据加载成功";
		
		if(mechanism_list.size()==0){
			map.put("status", "500");
			map.put("message", "暂无项目数据");
			map.put("data", "{}");
			return map;
		}
		
		//总页数
		Integer pagecount = (mechanism_list.size()+pageSize-1)/pageSize;
		//页数
		Integer pagenumber = pageNumber>=pagecount?pagecount:pageNumber;
		
		if (pageNumber>pagecount) {
			message = "无更多数据";
			status = "500";
		}
		
		if(pageNumber>=pagecount){
			data_map.put("mechanisms",mechanism_list.subList((pagenumber-1)*pageSize, mechanism_list.size()));
		}else{
			data_map.put("mechanisms", mechanism_list.subList((pagenumber-1)*pageSize, pageSize*pagenumber));
		}
		
		map.put("status", status);
		map.put("message",message);
		map.put("data", JsonUtils.toJson(data_map));
		return map;
		
	}

	@Override
	public Map<String, Object> findMechanismList(String file) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		Map<String, Object> map_list = new HashMap<String, Object>();
		Map<String, Object> mechanism_list = new HashMap<String, Object>();
		List<Map<String,Object>> mechanism_data = new ArrayList<Map<String,Object>>();
		try {
			map1 = mechanismDao.findMechanismList(file);
			List<Mechanism> list = (List<Mechanism>) map1.get("mechanismsList");
			for(Mechanism m : list){
				map.put("id", m.getId());
				map.put("name", m.getName());
				map.put("logo", m.getLogo());
				map.put("phone", m.getPhone());
				map.put("mobile", m.getMobile());
				map.put("contacts", m.getContacts());
				map.put("scoreSort", m.getScoreSort());
				map.put("second", m.getSecond());
				map.put("categoryName", m.getMechanismCategory().getName());
				map.put("latitude", m.getLatitude());
				map.put("longitude", m.getLongitude());
				map.put("address", m.getAddress());
				mechanism_data.add(map);			}
			mechanism_list.put("mechanisms", mechanism_data);
			map_list.put("data", JsonUtils.toJson(mechanism_list));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return map_list;
	}

	
	@Override
	public Map<String, Object> findMechanismLists(String file) {
		// TODO Auto-generated method stub
		Map<String, Object> map = mechanismDao.findMechanismList(file);
		List<Mechanism> list = (List<Mechanism>) map.get("mechanismsList");
		map.put("mechanisms", list);
		return  map;
	}
	
	
	@Override
	public List<Mechanism> screenMechanismLists(String file) {
		// TODO Auto-generated method stub
		Map<String, Object> map = mechanismDao.screenMechanismLists(file);
		List<Mechanism> list = (List<Mechanism>) map.get("mechanismsList");
		return list;
	}
//	@Override
//	public Mechanism getCurrent() {
//		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
//		if (requestAttributes != null) {
//			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
//			Principal principal = (Principal) request.getSession().getAttribute(User.PRINCIPAL_ATTRIBUTE_NAME);
//			User user = principal != null ? userDao.find(principal.getId()) : null;
//			if (user != null) {
//				Mechanism mechanism = user.getMechanism();
//				if (mechanism != null) {
//					return mechanism;
//				}
//			} 
//		}
//		return null;
//	}
	
	@Override
	public Mechanism getCurrent() {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		if (requestAttributes != null) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
			Principal principal = (Principal) request.getSession().getAttribute(Doctor.PRINCIPAL_ATTRIBUTE_NAME);
			Doctor doctor = principal != null ? doctorDao.find(principal.getId()) : null;
			if (doctor != null) {
				Mechanism mechanism = doctor.getDefaultDoctorMechanismRelation().getMechanism();
				if (mechanism != null) {
					return mechanism;
				}
			} 
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> webMechanismList(String scoreSort,
			String second, String longitude, String latitude, String distance,
			Integer pageNumber,String flag) {
		// TODO Auto-generated method stub
		return mechanismDao.webMechanismList(scoreSort,second,longitude,latitude,distance,pageNumber,flag);
	}

	@Override
	public List<Mechanism> getMechanism() {
		// TODO Auto-generated method stub
		return mechanismDao.getMechanism();
	}

	@Override
	public List<Mechanism> find(String file) {
		// TODO Auto-generated method stub
		return mechanismDao.find(file);
	}

	@Override
	public Mechanism getMechanismInfo(Long mechanismid) {
		// TODO Auto-generated method stub
		return mechanismDao.find(mechanismid);
	}


}