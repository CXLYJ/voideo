package com.lyj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.lyj.utils.RedisOperator;

/**
 * @author 作者 lyj
 * @version 创建时间：2018年11月6日 下午21:37:02
 * redis集成
 */

@RestController
public class BaseController {
	
	@Autowired
	public RedisOperator redis;

	public static final String USER_REDIS_SESSION = "user-redis-session";
	
}
