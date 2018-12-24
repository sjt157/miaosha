package com.sjt.miaosha.redis;
//接口只是定义一些契约，BasePrefix抽象类用来实现共通的实现，把一些特殊的实现留给自己的子类。
public interface KeyPrefix {
	/**
	 * 获取过期时间
	 * @return
	 */
	public int expireSeconds();

	/**
	 * 获取前缀
	 * @return
	 */
	public String getPrefix();
}
