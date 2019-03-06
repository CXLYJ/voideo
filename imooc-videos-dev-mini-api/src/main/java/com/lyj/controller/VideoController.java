package com.lyj.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.lyj.utils.IMoocJSONResult;

/**
 * @author 作者 lyj
 * @version 创建时间：2019年3月6日 下午10:01:30
 * 上传视频
 */
@RestController
@Api(value = "视频相关的业务接口",tags = {"视频相关的业务的controller"})
@RequestMapping("/video")
public class VideoController {

	/**
	 * 上传视频
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "用户上传头像",notes = "用户上传头像的接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userId", value = "用户id", required = true,
				dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "bgmId", value = "背景音乐id", required = false,
				dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "videoSeconds", value = "背景音乐播放长度", required = true,
				dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "videoWidth", value = "视频宽度", required = true,
				dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "videoHight", value = "视频高度", required = true,
				dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "desc", value = "视频描述", required = false,
				dataType = "String", paramType = "query")
	})
	@PostMapping(value = "/upload",headers="content-type=multipart/form-data")
	public IMoocJSONResult upload(
			String bgmId,double videoSeconds,
			int videoWidth,int videoHight,String desc,
			@RequestParam(value = "userId", required = true)String userId,  
			@ApiParam(value = "短视频",required = true)
			MultipartFile file
			) throws Exception{
		
		if (StringUtils.isBlank(userId)) {
			return IMoocJSONResult.errorMsg("用户id不能为空...");
		}
		
		//文件保存的命名空间
		String fileSpace = "F:/vedios";
		//保存到数据库中的相对路径
		String uploadPathDB = "/" + userId + "/video";
		FileOutputStream fileOutputStream = null;
		InputStream inputStream = null;
		
		try {
			if (file != null) {
				
				String fileName = file.getOriginalFilename();
				//对文件进行重命名
				String b = fileName.substring(0,fileName.lastIndexOf("."));
				String newFileName = userId + "_" + System.currentTimeMillis();
				fileName = fileName.replace(b,newFileName);
				if (StringUtils.isNotBlank(fileName)) {
					// 文件上传的最终保存路径
					String finalVideoPath = fileSpace + uploadPathDB + "/" + fileName;
					//设置数据库保存路径
					uploadPathDB += ("/" + fileName);
					
					/**
					 * getParentFile 返回此抽象路径名的父，或抽象路径名 null如果此路径名没有指定父目录。 
					 * isDirectory 测试此抽象路径名表示的文件是否为目录
					 */
					File ouFile = new File(finalVideoPath);
					if (ouFile.getParentFile() != null || !ouFile.getParentFile().isDirectory()) {
						//创建父文件夹
						ouFile.getParentFile().mkdirs();
					}
					
					fileOutputStream = new FileOutputStream(ouFile);
					inputStream = file.getInputStream();
					IOUtils.copy(inputStream, fileOutputStream);
				}
			}else {
				return IMoocJSONResult.errorMsg("上传出错...");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return IMoocJSONResult.errorMsg("上传出错...");
		}finally{
			if (fileOutputStream != null) {
				fileOutputStream.flush();
				fileOutputStream.close();
			}
		}
		
		return IMoocJSONResult.ok(uploadPathDB);
	}
	
}
