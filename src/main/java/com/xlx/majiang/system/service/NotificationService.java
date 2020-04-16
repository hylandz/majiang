package com.xlx.majiang.system.service;

import com.xlx.majiang.system.dto.NotificationDTO;
import com.xlx.majiang.system.dto.PaginationDTO;
import com.xlx.majiang.common.enums.NotificationStatusEnum;
import com.xlx.majiang.common.enums.NotificationTypeEnum;
import com.xlx.majiang.system.enums.ErrorCodeEnum;
import com.xlx.majiang.system.exception.CustomizeException;
import com.xlx.majiang.system.dao.NotificationMapper;
import com.xlx.majiang.system.entity.Notification;
import com.xlx.majiang.system.entity.NotificationExample;
import com.xlx.majiang.system.entity.User;
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
    //NotificationExample example = new NotificationExample();

    notificationExample.setOrderByClause("gmt_modified desc");
    List<Notification> notificationList = notificationMapper.selectByExampleWithRowbounds(notificationExample,new RowBounds(offSet,size));

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
            .andReceiverEqualTo(userId)
            .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
    return notificationMapper.countByExample(notificationExample);
  }


  /**
   * 读取通知:
   *  由未读消息点击读取时的操作
   * @param id 通知id
   * @param user 当前登录用户
   * @return dto
   */
  public NotificationDTO read(Long id, User user){

    Notification notification = notificationMapper.selectByPrimaryKey(id);
    if (notification == null){
      throw new CustomizeException(ErrorCodeEnum.NOTIFICATION_NOT_FOUND);
    }

    if (!Objects.equals(notification.getReceiver(),user.getId())){
      throw new CustomizeException(ErrorCodeEnum.READ_NOTIFICATION_FAIL);
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
