package com.xlx.majiang.mapper;

import com.xlx.majiang.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: xielx on 2019/6/22
 */
@Mapper
public interface UserMapper {
  @Insert("insert into user(account_id,login,token,bio,avator_url,gmt_create,gmt_modified)values(#{accountId},#{login},#{token},#{bio},#{avatorUrl},#{create},#{modify})")
  void insert(User user);
}
