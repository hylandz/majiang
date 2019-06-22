package com.xlx.majiang.mapper;

import com.xlx.majiang.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: xielx on 2019/6/22
 */
@Mapper
public interface UserMapper {
  @Insert("insert into user(account_id,name,token,bio,avatar_url,gmt_create)values(#{accountId},#{name},#{token},#{bio},#{avatarUrl},#{gmtCreate})")
  void insert(User user);
}
