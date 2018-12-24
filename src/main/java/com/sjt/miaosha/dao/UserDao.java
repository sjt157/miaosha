package com.sjt.miaosha.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sjt.miaosha.entity.User;

@Mapper
public interface UserDao {

	@Select("select * from s_user where id = #{id}")
	public User getById(@Param("id") long id);
	
	@Select("update s_user set password = #{password} where id = #{id}")
	public User update(User toBeUpdate);
	
	
}
