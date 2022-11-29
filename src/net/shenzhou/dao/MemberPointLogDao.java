package net.shenzhou.dao;

import java.util.List;
import java.util.Map;

import net.shenzhou.Page;
import net.shenzhou.entity.MemberPointLog;


/**
* @ClassName: MemberPointLogDao 
* @Description: TODO(会员积分日志) 
* @author wsr  
* @date 2017-1-12 下午3:40:33
 */
public interface MemberPointLogDao extends BaseDao<MemberPointLog, Long> {

	/**
	 * 
	* @Title: findPage 
	* @Description: TODO(会员积分列表明细) 
	* @param pointType
	* @param pageable
	* @return 设定文件 
	* @return Page<Member>    返回类型 
	* @throws
	 */
	Page<MemberPointLog> findPage(Map<String,Object> query_map);
	
	
	
	
	/**
	 * 用户已获取积分列表
	 * @param query_map
	 * @return
	 */
	List<MemberPointLog> findMemberAcquire(Map<String,Object> query_map);
}
