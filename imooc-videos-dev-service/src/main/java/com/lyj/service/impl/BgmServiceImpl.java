package com.lyj.service.impl;

import java.util.List;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lyj.mapper.BgmMapper;
import com.lyj.pojo.Bgm;
import com.lyj.service.BgmService;

/**
 * @author 作者 lyj
 * @version 创建时间：2019年3月5日 下午5:30:52
 * 类说明
 */
@Service
public class BgmServiceImpl implements BgmService{
	
	@Autowired
	private BgmMapper bgmMapper;
	
	@Autowired
	private Sid sid;

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public List<Bgm> queryBgmList() {
		
		return bgmMapper.selectAll();
	}

}
