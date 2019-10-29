package com.example.demo.user.service;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.user.model.UserVO;

@Service("com.example.demo.user.service.UserService")
public class UserServiceImpl implements UserService{

	@Autowired
	UserDAO mUserDAO;
	
	// 로그인
	@Override
	public UserVO login(UserVO member) throws Exception {


		if(mUserDAO.check_id(member.getUser_id()) == 0) {
			return null;
		} else {
			String pw = member.getUser_password();
			member = mUserDAO.userLogin(member.getUser_id());
			if(!member.getUser_password().equals(pw)) {
				return null;
			}
		return member;
		}
	}
	
	@Override
	public int check_id(String user_id) throws Exception {
		return mUserDAO.check_id(user_id);
	}
	
	public List<UserVO> userListService() throws Exception {

		return mUserDAO.userList();
	}


	public void userSignUpService(UserVO user) throws Exception {

		mUserDAO.userInsert(user);
	}
}