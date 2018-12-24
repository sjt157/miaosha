package com.sjt.miaosha.access;

import com.sjt.miaosha.entity.User;

public class UserContext {
    //每一个线程都对应自己的threadlocal
	private static ThreadLocal<User> userHolder = new ThreadLocal<User>();
	
	public static void setUser(User user) {
		userHolder.set(user);
	}
	
	public static User getUser() {
		return userHolder.get();
	}
}
