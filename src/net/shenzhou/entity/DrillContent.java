/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package net.shenzhou.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity - 训练内容
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
@Entity
@Table(name = "xx_drillcontent")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_drillcontent_sequence")
public class DrillContent extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4115916023623157547L;

	/**项目**/
	private ServerProjectCategory serverProjectCategory;
	
	/**次数**/
	private String time;

	/**康复计划**/
	private RecoveryPlan recoveryplan;
	
	@ManyToOne(fetch = FetchType.LAZY)
	public ServerProjectCategory getServerProjectCategory() {
		return serverProjectCategory;
	}

	public void setServerProjectCategory(ServerProjectCategory serverProjectCategory) {
		this.serverProjectCategory = serverProjectCategory;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	public RecoveryPlan getRecoveryplan() {
		return recoveryplan;
	}

	public void setRecoveryplan(RecoveryPlan recoveryplan) {
		this.recoveryplan = recoveryplan;
	}
	
	
}