package com.xlx.majiang.system.service;

/**
 * @author: xielx on 2019/8/14
 */
public interface IMailService {

	/**
	 * 发送简单文本邮件
	 * @param from 发件人
	 * @param to 收件人
	 * @param subject 邮件主题
	 * @param content 邮件内容
	 */
	Long sendSimpleMail(String from,String to,String subject,String content);

	/**
	 * 发送html格式的邮件
	 * @param from 发件人
	 * @param to 收件人
	 * @param subject 邮件主题
	 * @param content 邮件内容
	 */
	Long sendHtmlMail(String from,String to, String subject, String content);

	Long sendAttachmentsMail(String from,String to, String subject, String content, String filePath);

	Long sendInlineResourceMail(String from,String to, String subject, String content, String rscPath, String rscId);

}
