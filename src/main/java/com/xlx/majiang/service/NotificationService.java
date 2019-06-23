package com.xlx.majiang.service;

import com.xlx.majiang.dto.NotificationDTO;
import com.xlx.majiang.dto.PaginationDTO;
import com.xlx.majiang.enums.NotificationStatusEnum;
import com.xlx.majiang.enums.NotificationTypeEnum;
import com.xlx.majiang.exception.CustomizeErrorCodeEnum;
import com.xlx.majiang.exception.CustomizeException;
import com.xlx.majiang.mapper.NotificationMapper;
import com.xlx.majiang.model.Notification;
import com.xlx.majiang.model.NotificationExample;
import com.xlx.majiang.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Service层:notification
 *
 * @author xielx on 2019/6/23
 */
@Service
public class NotificationService {

  @Resource
  private NotificationMapper notificationMapper;


  /**
   * 分页
   * @param userId ReceiverId
   * @param page currPage
   * @param size pageSize
   * @return object
   */
  public PaginationDTO list(Long userId,Integer page,Integer size){
    PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();

    NotificationExample notificationExample = new NotificationExample();
    notificationExample.createCriteria().andReceiverEqualTo(userId);
    int totalCount = (int)notificationMapper.countByExample(notificationExample);
    int totalPage = (totalCount + size - 1) / size;

    if (page <= 1){
      page = 1;
    }

    if (page >= totalPage){
      page = totalPage;
    }
    int offSet = (page - 1) * size;
    paginationDTO.setPagination(page,totalPage);
    NotificationExample example = new NotificationExample();
    notificationExample.createCriteria().andReceiverEqualTo(userId);
    example.setOrderByClause("gmt_create desc");
    List<Notification> notificationList = notificationMapper.selectByExampleWithRowbounds(example,new RowBounds(offSet,size));

    if (notificationList.size() == 0){
      return paginationDTO;
    }

    // 收集NotificationDTO数据对象集
    LinkedList<NotificationDTO> notificationDTOLinkedList = new LinkedList<>();
    for (Notification notification : notificationList) {
      NotificationDTO notificationDTO = new NotificationDTO();
      BeanUtils.copyProperties(notification,notificationDTO);
      notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
      notificationDTOLinkedList.add(notificationDTO);
    }

    paginationDTO.setData(notificationDTOLinkedList);
    return  paginationDTO;
  }


  /**
   * 统计未读通知
   * @param userId 通知者id
   * @return Long
   */
  public Long unReadCount(Long userId){
    NotificationExample notificationExample = new NotificationExample();
    notificationExample.createCriteria()
            .andIdEqualTo(userId)
            .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
    return notificationMapper.countByExample(notificationExample);
  }


  /**
   * 读取通知
   * @param id 通知id
   * @param user 用户
   * @return dto
   */
  public NotificationDTO read(Long id, User user){

    Notification notification = notificationMapper.selectByPrimaryKey(id);
    if (notification == null){
      throw new CustomizeException(CustomizeErrorCodeEnum.NOTIFICATION_NOT_FOUND);
    }

    if (Objects.equals(notification.getReceiver(),user.getId())){
      throw new CustomizeException(CustomizeErrorCodeEnum.READ_NOTIFICATION_FAIL);
    }
    //消息已读
    notification.setStatus(NotificationStatusEnum.READ.getStatus());
    notificationMapper.updateByPrimaryKey(notification);

    NotificationDTO notificationDTO = new NotificationDTO();
    BeanUtils.copyProperties(notification,notificationDTO);
    notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));

    return notificationDTO;
  }

}
