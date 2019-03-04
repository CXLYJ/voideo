package com.lyj.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lyj.pojo.Users;
import com.lyj.service.UserService;
import com.lyj.utils.IMoocJSONResult;

/**
 * @author 作者 lyj
 * @version 创建时间：2019年3月4日 下午14:46:02
 * 用户相关业务
 */
@RestController
@Api(value = "用户相关业务的接口",tags = {"用户相关业务的controller"})
@RequestMapping("/user")
public class UserController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	/**
	 * 用户上传头像
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "用户上传头像",notes = "用户上传头像的接口")
	@ApiImplicitParam(name = "userId", value = "用户id", required = true,
						dataType = "String", paramType = "query")
	@PostMapping("/uploadFace")
	public IMoocJSONResult uploadface(String userId,  
			@RequestParam(value = "file")MultipartFile[] files) throws Exception{
		
		if (StringUtils.isBlank(userId)) {
			return IMoocJSONResult.errorMsg("用户id不能为空...");
		}
		
		//文件保存的命名空间
		String fileSpace = "F:/vedios";
		//保存到数据库中的相对路径
		String uploadPathDB = "/" + userId + "/face";
		FileOutputStream fileOutputStream = null;
		InputStream inputStream = null;
		
		try {
			if (files != null && files.length > 0) {
				
				String fileName = files[0].getOriginalFilename();
				if (StringUtils.isNotBlank(fileName)) {
					// 文件上传的最终保存路径
					String finalFacePath = fileSpace + uploadPathDB + "/" + fileName;
					String deleteFacePath = fileSpace + uploadPathDB;
					//设置数据库保存路径
					uploadPathDB += ("/" + fileName);
					
					/**
					 * getParentFile 返回此抽象路径名的父，或抽象路径名 null如果此路径名没有指定父目录。 
					 * isDirectory 测试此抽象路径名表示的文件是否为目录
					 */
					File ouFile = new File(finalFacePath);
					File deleteFile = new File(deleteFacePath);
					if (ouFile.getParentFile() != null || !ouFile.getParentFile().isDirectory()) {
						//创建父文件夹
						ouFile.getParentFile().mkdirs();
					}
					
					if (deleteFile.exists()) {
						File[] tempList = deleteFile.listFiles();
						if (tempList != null) {
							for (int i = 0; i < tempList.length; i++) {
								if (tempList[i].isFile() && tempList[i].getName().contains(fileName)) {
									tempList[i].delete();
									log.info("删除文件成功...");
								}
								//存在子文件
								if (tempList[i].isDirectory()) {
									tempList[i].delete();
								}
							}
						}
					}
					
					fileOutputStream = new FileOutputStream(ouFile);
					inputStream = files[0].getInputStream();
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
		
		Users user = new Users();
		user.setId(userId);
		user.setFaceImage(uploadPathDB);
		userService.updateUserInfo(user);
		
		return IMoocJSONResult.ok();
	}
	
	
}
