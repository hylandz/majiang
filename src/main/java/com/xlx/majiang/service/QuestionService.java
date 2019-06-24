package com.xlx.majiang.service;

import com.xlx.majiang.dto.PaginationDTO;
import com.xlx.majiang.dto.QuestionDTO;
import com.xlx.majiang.exception.CustomizeErrorCodeEnum;
import com.xlx.majiang.exception.CustomizeException;
import com.xlx.majiang.mapper.QuestionExtraMapper;
import com.xlx.majiang.mapper.QuestionMapper;
import com.xlx.majiang.mapper.UserMapper;
import com.xlx.majiang.model.Question;
import com.xlx.majiang.model.QuestionExample;
import com.xlx.majiang.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service层-question
 *
 * @author xielx on 2019/6/23
 */
@Service
public class QuestionService {


  @Resource
  private QuestionMapper questionMapper;
  @Resource
  private QuestionExtraMapper questionExtraMapper;
  @Resource
  private UserMapper userMapper;


  /**
   * 随机的分页
   *
   * @param page currPage
   * @param size pageSize
   * @return object
   */
  public PaginationDTO<QuestionDTO> list(Integer page, Integer size) {

    QuestionExample questionExample = new QuestionExample();
    questionExample.setOrderByClause("gmt_create desc");

    return PaginationDTOHelper(questionExample, page, size);
  }

  /**
   * 属于userId的分页
   *
   * @param userId 用户id
   * @param page   pageSize
   * @param size   pageSize
   * @return object
   */
  public PaginationDTO list(Long userId, Integer page, Integer size) {

    /// 查询属于userId的分页数据
    QuestionExample questionExample = new QuestionExample();

    questionExample.setOrderByClause("gmt_create desc");

    questionExample.createCriteria().andCreatorEqualTo(userId);

    return PaginationDTOHelper(questionExample, page, size);
  }


  public void createOrUpdate(Question question) {
    if (question.getId() == null) {
      //插入
      question.setGmtCreate(System.currentTimeMillis());
      /*question.setGmtModified(question.getGmtCreate());
      question.setViewCount(0);
      question.setCommentCount(0);
      question.setLikeCount(0);*/

      questionMapper.insertSelective(question);
    } else {
      // 更新
      Question updateQuestion = new Question();
      updateQuestion.setGmtModified(System.currentTimeMillis());
      updateQuestion.setTitle(question.getTitle());
      updateQuestion.setDescription(question.getDescription());
      updateQuestion.setTag(question.getTag());
      QuestionExample questionExample = new QuestionExample();
      questionExample.createCriteria().andIdEqualTo(question.getId());

      int n = questionMapper.updateByExampleSelective(updateQuestion, questionExample);
      if (n != 1) {
        throw new CustomizeException(CustomizeErrorCodeEnum.QUESTION_NOT_FOUND);
      }
    }
  }


  /**
   * 依据questionId获取QuestionDTO对象
   *
   * @param qId questionId
   * @return object
   */
  public QuestionDTO getByQuestionId(Long qId) {
    Question question = questionMapper.selectByPrimaryKey(qId);
    if (question == null) {
      throw new CustomizeException(CustomizeErrorCodeEnum.QUESTION_NOT_FOUND);
    }

    QuestionDTO questionDTO = new QuestionDTO();
    // BeanUtils.copyProperties(source,target),将A,赋值给B
    BeanUtils.copyProperties(question, questionDTO);
    User user = userMapper.selectByPrimaryKey(question.getCreator());

    questionDTO.setUser(user);
    return questionDTO;
  }


  /**
   * 问题浏览
   *
   * @param id qid
   */
  public void incView(Long id) {
    Question question = new Question();
    question.setId(id);
    question.setViewCount(1);
    questionExtraMapper.incView(question);
  }

  /**
   * 查询相关问题
   * @param queryDTO 相关问题
   * @return List
   */
  public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
    if (StringUtils.isBlank(queryDTO.getTag())) {
      return new ArrayList<>();
    }

    //切割 如 a,b,c,d
    String[] tagArray = StringUtils.split(queryDTO.getTag(), ",");
    //转换 如 a|b|c|d
    String regex = Arrays.stream(tagArray).collect(Collectors.joining("|"));
    Question question = new Question();
    question.setId(queryDTO.getId());
    question.setTag(regex);
    List<Question> questionList = questionExtraMapper.selectRelated(question);

    // 将查询出的question集合封装到QuestionDTO集合
    List<QuestionDTO> questionDTOList = questionList.stream().map(q -> {
      QuestionDTO questionDTO = new QuestionDTO();
      BeanUtils.copyProperties(q, questionDTO);
      return questionDTO;
    }).collect(Collectors.toList());
    return questionDTOList;
  }


  /**
   * 设置PaginationDTO
   *
   * @param example Question
   * @param page    currPage
   * @param size    pageSize
   * @return object
   */
  private PaginationDTO<QuestionDTO> PaginationDTOHelper(QuestionExample example, Integer page, Integer size) {

    PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();

    // 设置总记录
    Integer totalCount = (int) questionMapper.countByExample(example);
    // 设置总页数
    Integer totalPage = (totalCount + size - 1) / size;

    //设置当前页
    if (page < 1) {
      page = 1;
    }

    if (page > totalPage) {
      page = totalPage;
    }

    paginationDTO.setPagination(page, totalPage);

    // 偏移量
    int offSet = (page - 1) * size;

    // 数据库分页数据
    List<Question> questionList = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offSet, size));

    // 前台需要是QuestionDTO数据集合
    LinkedList<QuestionDTO> questionDTOList = new LinkedList<>();

    // 设置每个QuestionDTO的User对象
    for (Question question : questionList) {
      User user = userMapper.selectByPrimaryKey(question.getCreator());
      QuestionDTO questionDTO = new QuestionDTO();
      BeanUtils.copyProperties(question, questionDTO);
      questionDTO.setUser(user);
      questionDTOList.add(questionDTO);
    }

    paginationDTO.setData(questionDTOList);
    return paginationDTO;
  }
}
