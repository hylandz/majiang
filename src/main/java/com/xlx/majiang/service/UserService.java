package com.xlx.majiang.service;

import com.xlx.majiang.dao.UserMapper;
import com.xlx.majiang.entity.User;
import com.xlx.majiang.entity.UserExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Service层:User
 *
 * @author xielx on 2019/6/23
 */
@Service
public class UserService {


  @Resource
  private UserMapper userMapper;


  /**
   * 插入:
   *    根据gitHubUser的account_id查询数据库是否有对应的用户,没有就新增用户
   * 更新:
   *    用户存在,更新用户
   * @param user User
   * @return boolean
   */
  public boolean createOrUpdate(User user){

    UserExample userExample = new UserExample();
    // 加入查询条件accountId
    userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());

    List<User> userList =  userMapper.selectByExample(userExample);

    if (userList.size() == 0){
      // 插入
      user.setGmtCreate(System.currentTimeMillis());
      user.setGmtModified(user.getGmtCreate());
      int effectRow = userMapper.insertSelective(user);
      return effectRow > 0;
    }else {
      // 修改
      User updateUser = new User();
      updateUser.setGmtModified(System.currentTimeMillis());
      updateUser.setAvatarUrl(user.getAvatarUrl());
      updateUser.setToken(user.getToken());
      updateUser.setName(user.getName());
      UserExample example = new UserExample();
      //添加数据库查询到的user的id作为更新id
      example.createCriteria().andIdEqualTo(userList.get(0).getId());

      int effectRow = userMapper.updateByExampleSelective(updateUser,example);
      return effectRow > 0;
    }
  }


  /**
   * 依据密码获取用户
   * @param userPassword pwd
   * @return User
   */
  public User findUserByPwd(String userPassword){
    UserExample userExample = new UserExample();
    userExample.createCriteria()
            .andAccountIdEqualTo(userPassword);
    List<User> userList = userMapper.selectByExample(userExample);
    if (userList.size() == 0){
      //throw new CustomizeException(CustomizeErrorCodeEnum.PRINCIPAL_OR_CREDENTIALS_WRONG);
      return null;
    }

    return userList.get(0);
  }
  
  /**
   * 主键查询用户信息
   * @param id 主键
   * @return User
   */
  public User getUserById(Long id){
    return userMapper.selectByPrimaryKey(id);
  }


}
