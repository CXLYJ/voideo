package com.lyj.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyj.service.BgmService;
import com.lyj.utils.IMoocJSONResult;

/**
 * @author 作者 lyj
 * @version 创建时间：2019年3月5日 下午5:31:52
 * 音乐列表
 */
@RestController
@Api(value = "背景音乐业务的接口", tags = {"背景音乐业务的controller"})
@RequestMapping("/bgm")
public class BgmController {
	
	@Autowired
	private BgmService bgmService;
	
	/**
	 * 音乐列表
	 * @return
	 */
	@ApiOperation(value = "获取背景音乐列表", notes = "用户上传头像的接口")
	@PostMapping("/list")
	public IMoocJSONResult bmgList() {
		return IMoocJSONResult.ok(bgmService.queryBgmList());
	}
	
}
