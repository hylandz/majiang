package com.xlx.majiang.mapper;

import com.xlx.majiang.model.User;
import com.xlx.majiang.model.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

/**
 * dao层
 */
public interface UserMapper {
    // 统计by Example
    long countByExample(UserExample example);

    // 删除by Example
    int deleteByExample(UserExample example);

    // 删除根据主键
    int deleteByPrimaryKey(Long id);

    //插入
    int insert(User record);

    //选择性插入
    int insertSelective(User record);

    // 分页查询by Example
    List<User> selectByExampleWithRowbounds(UserExample example, RowBounds rowBounds);

    // 查询by Example
    List<User> selectByExample(UserExample example);

    // 主键查询
    User selectByPrimaryKey(Long id);

    // 选择性更新by Example
    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    //更新 by Example
    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    // 依据主键选择性更新
    int updateByPrimaryKeySelective(User record);

    // 主键更新
    int updateByPrimaryKey(User record);
}