package com.xlx.majiang.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * 邮件
 *
 * @author xielx on 2019/8/14
 */
@Service
public class MailService implements IMailService {
	private static final Logger logger = LoggerFactory.getLogger(MailService.class);

	@Resource
	private JavaMailSender javaMailSender;


	@Override
	public Long sendSimpleMail(String from,String to, String subject, String content) {
		long start = System.currentTimeMillis();
		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(content);

		try{
			javaMailSender.send(message);
		}catch (MailException e){
			logger.error("邮件发送异常:[{}]",e.getMessage());
		}
		return (System.currentTimeMillis() - start);

	}

	@Override
	public Long sendHtmlMail(String from,String to, String subject, String content) {
		long start = System.currentTimeMillis();
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true);
			messageHelper.setFrom(from);
			messageHelper.setTo(to);
			messageHelper.setSubject(subject);
			messageHelper.setText(content,true);
			javaMailSender.send(mimeMessage);
		} catch (MessagingException e) {
			logger.error("发送html邮件时发生异常:[{}]",e.getMessage());
		}
		long end = System.currentTimeMillis();
		return (end - start);

	}

	/**
	 * 附件邮件
	 * @param to 邮箱接收人
	 * @param subject 邮件主题
	 * @param content 邮件内容
	 * @param filePath 附件路径
	 */
	@Override
	public Long sendAttachmentsMail(String from,String to, String subject, String content, String filePath) {
		long start = System.currentTimeMillis();
		MimeMessage message = javaMailSender.createMimeMessage();
		//true表示需要创建一个multipart message
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(message,true);
			messageHelper.setFrom(from);
			messageHelper.setTo(to);
			messageHelper.setSubject(subject);
			messageHelper.setText(content,true);

			// 添加附件
			FileSystemResource resource = new FileSystemResource(new File(filePath));
			//文件名
			String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
			messageHelper.addAttachment(fileName,resource);


			javaMailSender.send(message);
		} catch (MessagingException e) {
			logger.error("发送附件邮件异常:[{}]",e.getMessage());
		}

		long end = System.currentTimeMillis();
		return (end - start);
	}

	/**
	 * 发送正文中有静态资源（图片）的邮件
	 * @param from 发件人
	 * @param to 邮箱接收人
	 * @param subject 邮件主题
	 * @param content 邮件内容
	 * @param rscPath 附件路径
	 * @param rscId ?
	 */
	@Override
	public Long sendInlineResourceMail(String from,String to, String subject, String content, String rscPath, String rscId) {
		long start = System.currentTimeMillis();
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(message,true);
			messageHelper.setFrom(from);
			messageHelper.setTo(to);
			messageHelper.setSubject(subject);
			messageHelper.setText(content,true);

			//添加图片
			FileSystemResource resource = new FileSystemResource(new File(rscPath));
			messageHelper.addInline(rscId,resource);

			javaMailSender.send(message);
		} catch (MessagingException e) {
			logger.error("发送图片邮件异常:[{}]",e.getMessage());
		}
		long end = System.currentTimeMillis();
		return (end - start);
	}
}
