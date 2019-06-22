package com.xlx.majiang.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author: xielx on 2019/6/22
 */
@Mapper
public interface UserMapper {
  @Insert("insert into user(account_id,name,token,bio,avatar_url,gmt_create)values(#{accountId},#{name},#{token},#{bio},#{avatarUrl},#{gmtCreate})")
  void insert(User user);

  @Select("select * from user where token = #{token}")
  User findUserByToken(String token);
}
