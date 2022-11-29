package net.shenzhou.service;

import java.util.List;

import net.shenzhou.entity.Balance;
import net.shenzhou.entity.Mechanism;
import net.shenzhou.entity.Member;

public interface BalanceService extends BaseService<Balance, Long> {
	
	List<Balance> getBalanceList(Member member);
	
	List<Balance> getByList(Member member,Mechanism mechanism);
}

