package com.app.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.app.entity.User;
import com.app.service.UserService;
import com.app.util.AuthcodeUtils;
import com.app.util.ImageUtils;
import com.app.util.MD5Util;

@Controller
public class UserAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private UserService userService;
	
	
	private String picture;
	private String phone;
	private String authcode;
	private String pwd;
	
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public String getAuthcode() {
		return authcode;
	}
	public void setAuthcode(String authcode) {
		this.authcode = authcode;
	}
	//用户注册
	public String signup(){
		System.out.println(phone);
		if(phone==null || phone.length()<1){
			setDatas(false,"请填写号码","user",null);
			/*data.put("ac","register");
			data.put("code","-1");
			data.put("desc","请填写号码");
			data.put("user",null);*/
			return SUCCESS;
		}
		System.out.println(userService.isPhoneExist(phone));
		if(userService.isPhoneExist(phone)){
			//号码如果已经注册,直接返回注册失败信息
			setDatas(false,"号码已注册","user","号码已注册");
			/*data.put("ac","register");
			data.put("code",-1);
			data.put("desc","号码已注册");
			data.put("user",null);*/
			return SUCCESS;
		}else{
			//号码为注册,生成验证码,通过短信发回,同时将验证码存入数据库
			System.out.println("正在注册");
			String code=AuthcodeUtils.createAuthcode();
			User user=new User();
			user.setPhone(phone);
			
			//md5加密
			user.setPwd(pwd);
			user.setAuthcode(code);
			if(userService.isPhoneExist(phone)){
				User tmp=(User) userService.getUserByPhone(phone).get(0);
				user.setId(tmp.getId());
				tmp=null;
			}
			userService.saveUser(user);
			setDatas(true,"恭喜注册成功!","user","恭喜注册成功!");
			/*data.put("ac","register");
			data.put("code",1);
			data.put("desc","ok");
			data.put("user",null);*/
			return SUCCESS;
		}
	}
	
	/**
	 * 用户激活
	 * 当用户的号码没有被注册时,返回验证码后
	 * 用户输入验证码,密码,确认密码
	 * 密码与确认密码的一致性由Android端负责检验
	 * 如果验证码正确,更新用户信息中的密码,激活状态;否则返回错误信息
	 */
	public String verify(){
		if(userService.verify(phone, authcode)){
			User user=new User();
			if(userService.isPhoneExist(phone)){
				User tmp=(User) userService.getUserByPhone(phone).get(0);
				user.setId(tmp.getId());
				tmp=null;
			}
			user.setPhone(phone);
			user.setPwd(pwd);
			user.setActivate(1);
			userService.saveUser(user);
			setDatas(true,"ok","user","ok");
			/*data.put("ac","verify");
			data.put("code",1);
			data.put("desc","ok");
			data.put("user",user);*/
		}else{
			setDatas(false,"验证码错误","user","false");
			data.put("ac","verify");
			data.put("code",-1);
			data.put("desc","验证码错误");
			data.put("user",null);
		}
		return SUCCESS;
	}
	
	//用户登陆
	public String signin(){
		User user=userService.signin(new User(phone, pwd));
		if(user!=null){
			setDatas(true,"ok","user","ok");
			System.out.println("登陆成功");
			/*data.put("action","signin");
			data.put("state","2000");
			data.put("desc","ok");
			data.put("user",user);*/
		}else{
			setDatas(false,"登陆失败","user",false);
			System.out.println("登陆失败");
			/*data.put("action","signin");
			 * passWord
			data.put("state","-1");
			data.put("desc","failed to signin");
			data.put("user",null);*/
		}
		user=null;
		return SUCCESS;
	}
	
	//更新用户信息
	public String update(){
		
		//将用户的信息进行更新
		System.out.println("正在更新");
		//保存用户的图片
		if(picture!=null){
		try {
			ImageUtils.decoderBase64File(picture, "D://app_user_image//"+phone+".jpg");
			List<User> users=userService.getUserByPhone(phone);
			User user=new User();
			user.setId(users.get(0).getId());
			user.setPhone(phone);
			user.setUpic("D://app_user_image//"+phone+".jpg");
			userService.updateUser(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		return SUCCESS;
	}
}
