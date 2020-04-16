package com.xlx.majiang.system.dao;

import com.xlx.majiang.system.dto.QuestionQueryDTO;
import com.xlx.majiang.system.entity.Question;
import com.xlx.majiang.system.entity.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionMapper {
    long countByExample(QuestionExample example);

    //
    int selectCount(QuestionQueryDTO questionQueryDTO);

    int deleteByExample(QuestionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Question record);

    int insertSelective(Question record);

    List<Question> selectByExampleWithBLOBsWithRowbounds(QuestionExample example, RowBounds rowBounds);

    List<Question> selectByExampleWithBLOBs(QuestionExample example);

    List<Question> selectByExampleWithRowbounds(QuestionExample example, RowBounds rowBounds);

    List<Question> selectByExample(QuestionExample example);

    //
    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);

    Question selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Question record, @Param("example") QuestionExample example);

    int updateByExampleWithBLOBs(@Param("record") Question record, @Param("example") QuestionExample example);

    int updateByExample(@Param("record") Question record, @Param("example") QuestionExample example);

    int updateByPrimaryKeySelective(Question record);

    int updateByPrimaryKeyWithBLOBs(Question record);

    int updateByPrimaryKey(Question record);
}