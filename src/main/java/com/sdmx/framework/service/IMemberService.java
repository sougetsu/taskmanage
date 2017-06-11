package com.sdmx.framework.service;

import com.sdmx.framework.entity.Member;
import com.sdmx.framework.vo.DataGrid;
import com.sdmx.framework.vo.MemberVO;

public interface IMemberService extends IService {

	/**
	 * 增加用户
	 * 
	 * @param member
	 * @return
	 */
	public MemberVO create(MemberVO member);

	/**
	 * 删除用户
	 * 
	 * @param memberIds
	 */
	public void remove(String memberIds);
	
	/**
	 * 恢复已删除用户
	 * 
	 * @param memberIds
	 */
	public void resume(String memberIds);

	/**
	 * 修改用户
	 * 
	 * @param membervo
	 * @return
	 */
	public MemberVO modify(MemberVO membervo);

	/**
	 * 修改用户密码
	 * 
	 * @param membervo
	 */
	public void modifyPwd(MemberVO membervo);

	/**
	 * 查询用户
	 * 
	 * @param membervo
	 * @return
	 */
	public DataGrid getMembers(MemberVO membervo);
	
	/**
	 * 根据id查询用户
	 * 
	 * @param membervo
	 * @return
	 */
	public MemberVO getMemberById(String memberId);
	
	/**
	 * 用户登录
	 * 
	 * @param member
	 * @return
	 */
	public Member login(Member member);
	
	/**
	 * 判断用户是否已存在
	 * @param membervo
	 * @return
	 */
	public boolean checkExist(MemberVO membervo);
	
	public boolean checkLicense(Member member);
}
