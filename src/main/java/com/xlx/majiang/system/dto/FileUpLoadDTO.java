package com.xlx.majiang.system.dto;

import lombok.Data;

/**
 * fileupload
 *
 * @author xielx on 2019/6/29
 */
@Data
public class FileUpLoadDTO {

  //0:上传失败;1:上传成功
  private Integer success;

  //提示信息,上传成功或失败的信息
  private String message;

  //图片地址,上传成功时才返回
  private String url;
}
