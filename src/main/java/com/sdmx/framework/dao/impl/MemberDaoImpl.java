package com.sdmx.framework.dao.impl;

import org.springframework.stereotype.Repository;

import com.sdmx.framework.dao.IMemberDao;
import com.sdmx.framework.entity.Member;

@Repository("memberDao")
public class MemberDaoImpl extends BaseDaoImpl<Member> implements IMemberDao {

}
