package com.lyj.service;

import java.util.List;

import com.lyj.pojo.Bgm;

/**
 * @author 作者 lyj
 * @version 创建时间：2019年3月5日 下午5:30:32
 * 类说明
 */
public interface BgmService {

	/**
	 * 查询背景音乐列表
	 * @return
	 */
	public List<Bgm> queryBgmList();
	
}
