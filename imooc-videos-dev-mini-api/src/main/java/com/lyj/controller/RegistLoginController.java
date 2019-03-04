package com.lyj.controller;

import java.util.UUID;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lyj.pojo.Users;
import com.lyj.pojo.vo.UserVO;
import com.lyj.service.UserService;
import com.lyj.utils.IMoocJSONResult;
import com.lyj.utils.MD5Utils;

/**
 * @author 作者 lyj
 * @version 创建时间：2018年11月6日 下午3:11:02
 * 注册登录
 */
@RestController
@Api(value = "用户注册登录的接口",tags = {"注册和登录的controller"})
public class RegistLoginController extends BaseController{
	
	@Autowired
	private UserService userService;
	
	@ApiOperation(value = "用户注册",notes = "用户注册接口")
	@PostMapping("/regist")
	public IMoocJSONResult regist(@RequestBody Users user) throws Exception{
		
		//1.判断用户名密码不为空
		if (StringUtils.isBlank(user.getUsername())|| 
				StringUtils.isBlank(user.getPassword())) {
			return IMoocJSONResult.errorMsg("用户名和密码不能为空!");
		}
		
		//2.判断用户是否存在
		boolean usernameIsExit = userService.queryUsernameIsExist(user.getUsername());
		
		//3.保存用户
		if (!usernameIsExit) {
			user.setNickname(user.getUsername());
			user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
			user.setFansCounts(0);
			user.setReceiveLikeCounts(0);
			user.setFollowCounts(0);
			userService.saveUser(user);
		}else {
			return IMoocJSONResult.errorMsg("用户名已经存在!");
		}
		user.setPassword("");
		UserVO userVO = setUserRedisSessionToken(user);
		return IMoocJSONResult.ok(userVO);
	}
	
	@ApiOperation(value = "用户登录",notes = "用户登录接口")
	@PostMapping("/login")
	public IMoocJSONResult login(@RequestBody Users user) throws Exception{
		String username = user.getUsername();
		String password = user.getPassword();
		
		//1.判断用户名密码不为空
		if (StringUtils.isBlank(username)|| StringUtils.isBlank(password)) {
			return IMoocJSONResult.errorMsg("用户名和密码不能为空!");
		}
		
		//2.判断用户是否存在
		Users userResult = userService.queryUserForLogin(username,
				MD5Utils.getMD5Str(password));
		
		//3.返回
		if (null != userResult) {
			user.setPassword("");
			UserVO userVO = setUserRedisSessionToken(user);
			return IMoocJSONResult.ok(userVO);
		}
		return IMoocJSONResult.errorMsg("用户名和密码不正确，请重试...");
	}
	
	public UserVO setUserRedisSessionToken(Users user){
		String uuidToken = UUID.randomUUID().toString();
		redis.set(USER_REDIS_SESSION + ":" + user.getId(), uuidToken, 1000 * 60 * 30);
		UserVO userVO = new UserVO();
		BeanUtils.copyProperties(user, userVO);
		userVO.setUserToken(uuidToken);
		return userVO;
	}
	
}
