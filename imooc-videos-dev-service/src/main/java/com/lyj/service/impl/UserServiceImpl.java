package com.lyj.service.impl;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.lyj.mapper.UsersMapper;
import com.lyj.pojo.Users;
import com.lyj.service.UserService;

/**
 * @author 作者 lyj
 * @version 创建时间：2018年11月6日 下午3:28:44
 * 类说明
 */
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UsersMapper usersMapper;
	
	@Autowired
	private Sid sid;

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public boolean queryUsernameIsExist(String username) {
		Users users = new Users();
		users.setUsername(username);
		Users result = usersMapper.selectOne(users);
		return result == null ? false : true;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void saveUser(Users users) {
		String id = sid.nextShort();
		users.setId(id);
		usersMapper.insert(users);
	}

	@Override
	public Users queryUserForLogin(String username, String password) {
		Example example = new Example(Users.class);
		Criteria criterion = example.createCriteria();
		criterion.andEqualTo("username",username);
		criterion.andEqualTo("password",password);
		Users result = usersMapper.selectOneByExample(example);
		return result;
	}

}
