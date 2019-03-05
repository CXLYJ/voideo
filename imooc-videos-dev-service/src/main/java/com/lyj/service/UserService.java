package com.lyj.service;

import org.apache.catalina.User;

import com.lyj.pojo.Users;

/**
 * @author 作者 lyj
 * @version 创建时间：2018年11月6日 下午3:21:21
 * 类说明
 */
public interface UserService {

	/**
	 * 判断用户名是否存在
	 * @param username
	 * @return
	 */
	public boolean queryUsernameIsExist(String username);
	
	/**
	 * 保存用户（用户注册）
	 * @param users
	 */
	public void saveUser(Users users);

	/**
	 * 判断用户是否存在
	 * @param username
	 * @param md5Str
	 * @return
	 */
	public Users queryUserForLogin(String username, String password);
	
	/**
	 * 修改用户信息
	 * @param user
	 */
	public void updateUserInfo(Users user);
	
	/**
	 * 根据用户id查询用户信息
	 * @param userId
	 * @return
	 */
	public Users queryUserInfo(String userId);
	
}
