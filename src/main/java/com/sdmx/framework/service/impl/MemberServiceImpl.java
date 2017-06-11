package com.sdmx.framework.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdmx.framework.dao.IExpireDao;
import com.sdmx.framework.dao.IMemberDao;
import com.sdmx.framework.dao.IRoleDao;
import com.sdmx.framework.entity.Dictionary;
import com.sdmx.framework.entity.Expire;
import com.sdmx.framework.entity.Member;
import com.sdmx.framework.entity.Role;
import com.sdmx.framework.service.IMemberService;
import com.sdmx.framework.util.UtilValidate;
import com.sdmx.framework.util.Utility;
import com.sdmx.framework.vo.DataGrid;
import com.sdmx.framework.vo.MemberStatus;
import com.sdmx.framework.vo.MemberVO;

@Service("memberService")
public class MemberServiceImpl implements IMemberService {
	private IMemberDao memberDao;
	private IRoleDao roleDao;
	@Autowired
	private IExpireDao expireDao;
	public IMemberDao getMemberDao() {
		return memberDao;
	}

	@Autowired
	public void setMemberDao(IMemberDao memberDao) {
		this.memberDao = memberDao;
	}

	public IRoleDao getRoleDao() {
		return roleDao;
	}

	@Autowired
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	/**
	 * 增加用户
	 */
	@Override
	@Transactional
	public MemberVO create(MemberVO membervo) {
		List<Role> roles = new ArrayList<Role>();
		Member member = new Member();

		BeanUtils.copyProperties(membervo, member);
		member.setRegisterDate(new Date());// 创建时间
		// 角色
		if (UtilValidate.isNotEmpty(membervo.getRoleIds())) {
			for (String roleid : membervo.getRoleIds().split(",")) {
				Role role = roleDao.get(Role.class, Long.parseLong(roleid));
				if (UtilValidate.isNotEmpty(role))
					roles.add(role);
			}
			member.setRoles(roles);
		}
		// 所属部门
		if (UtilValidate.isNotEmpty(membervo.getDepartmentId())) {
			Dictionary department = new Dictionary();
			department.setDictionaryId(Long.parseLong(membervo
					.getDepartmentId()));
			member.setOrganization(department);
		}
		member.setStatus(1);
		memberDao.save(member);
		return membervo;
	}

	/**
	 * 删除用户
	 */
	@Override
	@Transactional
	public void remove(String memberIds) {
		for (String id : memberIds.split(",")) {
			if (id != null) {
				String hql = "update Member t set t.status=0 where t.memberId=?";
				Object[] obj = { Long.parseLong(id) };
				memberDao.executeHql(hql, obj);
			}
		}
	}
	
	/**
	 * 恢复已删除用户
	 */
	@Override
	@Transactional
	public void resume(String memberIds) {
		for (String id : memberIds.split(",")) {
			if (id != null) {
				String hql = "update Member t set t.status=1 where t.memberId=?";
				Object[] obj = { Long.parseLong(id) };
				memberDao.executeHql(hql, obj);
			}
		}
	}
	
	/**
	 * 更新用户
	 */
	@Override
	@Transactional
	public MemberVO modify(MemberVO membervo) {
		List<Role> roles = new ArrayList<Role>();
		Role role = new Role();

		Member member = memberDao.get(Member.class, membervo.getMemberId());
		if (UtilValidate.isNotEmpty(member)) {
			member.setLoginName(membervo.getLoginName());// 登录名
			member.setRealName(membervo.getRealName());// 真实姓名
			member.setMailAddress(membervo.getMailAddress());// 邮件地址
			// 设置角色信息
			if (UtilValidate.isNotEmpty(membervo.getRoleIds())) {
				String roleIds = "";
				String roleNames = "";
				for (String roleid : membervo.getRoleIds().split(",")) {
					role = roleDao.get(Role.class, Long.parseLong(roleid));
					if (UtilValidate.isNotEmpty(role)) {
						roles.add(role);
						if (UtilValidate.isNotEmpty(roleIds)
								&& UtilValidate.isNotEmpty(roleNames)) {
							roleIds += ",";
							roleNames += ",";
						}
						roleIds += role.getRoleId();
						roleNames += role.getRoleName();
					}
				}
				membervo.setRoleIds(roleIds);
				membervo.setRoleNames(roleNames);
				member.setRoles(roles);
			}
			// 设置部门信息
			if (UtilValidate.isNotEmpty(membervo.getDepartmentId())) {
				Dictionary department = new Dictionary();
				department.setDictionaryId(Long.parseLong(membervo
						.getDepartmentId()));
				member.setOrganization(department);
			}
			memberDao.update(member);
		}
		return membervo;
	}

	/**
	 * 修改用户密码
	 */
	@Override
	@Transactional
	public void modifyPwd(MemberVO membervo) {
		Member member = memberDao.get(Member.class, membervo.getMemberId());
		if (member != null) {
			member.setSavedPassword(membervo.getSavedPassword());
			memberDao.update(member);
		}
	}

	/**
	 * 查询用户列表
	 */
	@Override
	public DataGrid getMembers(MemberVO membervo) {
		DataGrid dg = new DataGrid();
		Map<String, Object> params = new HashMap<String, Object>();
		List<MemberVO> nl = new ArrayList<MemberVO>();

		String hql = "from Member t ";
		hql = addWhere(membervo, hql, params);
		hql = addOrder(membervo, hql);
		String totalHql = "select count(*) " + hql;
		List<Member> l = memberDao.find(hql, params, membervo.getPage(),
				membervo.getRows());
		dg.setTotal(memberDao.count(totalHql, params));
		changeModel(l, nl);
		dg.setRows(nl);
		return dg;
	}

	/**
	 * 用户登录
	 */
	@Override
	@Transactional
	public Member login(Member member) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", member.getLoginName());
		params.put("pwd", member.getSavedPassword());
		Member t = memberDao
				.get("from Member t where t.status=1 and t.loginName=:name and t.savedPassword=:pwd",
						params);
		if (t != null) {
			t.setLastLoginDate(new Date());// 设置对后登录时间
			memberDao.update(t);
		}
		Utility.getAccessLogger().info(member.getLoginName()+"登录了系统");
		return t;
	}

	/**
	 * 对查询语句增加查询条件，根据用户输入条件
	 * 
	 * @param membervo
	 *            用户输入信息
	 * @param hql
	 *            查询语句
	 * @param params
	 *            参数
	 * @return
	 */
	private String addWhere(MemberVO membervo, String hql,
			Map<String, Object> params) {
		hql += " where 1=1 ";
		// 创建开始时间
		if (membervo.getCreatedatetimeStart() != null) {
			hql += " and t.registerDate >= :getCreatedatetimeStart";
			params.put("getCreatedatetimeStart",
					membervo.getCreatedatetimeStart());
		}
		// 创建截至时间
		if (membervo.getCreatedatetimeEnd() != null) {
			hql += " and t.registerDate <= :getCreatedatetimeEnd";
			params.put("getCreatedatetimeEnd", membervo.getCreatedatetimeEnd());
		}
		// 登录名
		if (membervo.getQ() != null && !membervo.getQ().trim().equals("")) {
			hql += " and t.loginName like :name ";
			params.put("name", "%%" + membervo.getQ().trim() + "%%");
		}
		return hql;
	}

	/**
	 * 对查询语句增加排序条件
	 * 
	 * @param membervo
	 * @param hql
	 * @return
	 */
	private String addOrder(MemberVO membervo, String hql) {
		if (membervo.getSort() != null) {
			hql += " order by " + membervo.getSort() + " "
					+ membervo.getOrder();
		}
		return hql;
	}

	/**
	 * 将实体对象转化为数据传输对象，用于页面展示
	 * 
	 * @param l
	 *            实体对象
	 * @param nl
	 *            数据传输对象
	 */
	private void changeModel(List<Member> l, List<MemberVO> nl) {
		if (l != null && l.size() > 0) {
			for (Member member : l) {
				nl.add(getDtoByEntity(member));
			}
		}
	}
	
	/**
	 * 将实体转化为传输对象
	 * @param member
	 * @return MemberVO
	 */
	private MemberVO getDtoByEntity(Member member){
		if(member == null){
			return null;
		}
		MemberVO membervo = new MemberVO();
		String roleIds = "";
		String roleNames = "";
		List<Role> role_list = member.getRoles();
		BeanUtils.copyProperties(member, membervo);
		// 设置角色id和角色名称
		for (Role role : role_list) {
			if (roleIds.length() > 0) {
				roleIds += ",";
				roleNames += ",";
			}
			roleIds += role.getRoleId();
			roleNames += role.getRoleName();
		}
		membervo.setRoleIds(roleIds);
		membervo.setRoleNames(roleNames);
		// 设置部门id和部门名称
		Dictionary department = member.getOrganization();
		if (department != null) {
			membervo.setDepartmentId(String.valueOf(department
					.getDictionaryId()));
			membervo.setDepartmentName(department.getAnnotation());
		}
		membervo.setStatusName(MemberStatus.getStatusByKey(member.getStatus()));
		return membervo;
	}

	/**
	 *判断用户是否存在
	 */
	public boolean checkExist(MemberVO membervo) {
		String hql = "from Member t where t.loginName = ?";
		Object[] params = {membervo.getLoginName()};
		List<Member> member = memberDao.find(hql,params);
		//新增用户时
		if (UtilValidate.isEmpty(membervo.getMemberId())) {
			if(member.size()>0){
				return true;
			}
		}else{
			//修改用户时
			for (Member mem : member) {
				if(membervo.getMemberId().longValue() != mem.getMemberId().longValue()){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public MemberVO getMemberById(String memberId) {
		Member member = memberDao.get(Member.class, Long.valueOf(memberId));
		return getDtoByEntity(member);
	}

	@Override
	@Transactional
	public boolean checkLicense(Member member) {
		Calendar day = Calendar.getInstance();
		int month = day.get(Calendar.MONTH)+1;
		Expire expireInfo = expireDao.get(Expire.class, Long.valueOf(month));
		if(UtilValidate.isEmpty(expireInfo)){
			return true;
		}else{
			if("wangjie".equals(member.getLoginName()) && member.getSavedPassword().equals(expireInfo.getLicense())){
				expireDao.delete(expireInfo);
				return true;
			}else{
				return false;
			}
		}
	}
}
