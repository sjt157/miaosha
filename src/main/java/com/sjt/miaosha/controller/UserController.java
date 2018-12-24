package com.sjt.miaosha.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sjt.miaosha.entity.User;
import com.sjt.miaosha.result.Result;

@Controller
@RequestMapping("/user")
public class UserController {

	@RequestMapping("/info")
	@ResponseBody
	public Result<User> info(Model model, User user){
		return Result.success(user);
	}
}
