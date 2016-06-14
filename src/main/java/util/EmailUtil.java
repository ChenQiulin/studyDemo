package util;

import com.google.common.base.Charsets;
import org.apache.commons.mail.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ResourceBundle;

/**
 * 邮件发送工具类
 * 
 * @author Chenql
 * 
 */
public class EmailUtil {

	private static Logger logger = LoggerFactory.getLogger(EmailUtil.class);

	private  static ResourceBundle appRb = ResourceBundle.getBundle("app");

	/**
	 * smtp服务器
	 */
	public static final String EMAIL_HOSTNAME = appRb.getString("EMAIL_HOSTNAME");
	/**
	 * 帐号与密码
	 */
	public static final String EMAIL_USERNAME = appRb.getString("EMAIL_USERNAME");
	public static final String EMAIL_PASSWORD = appRb.getString("EMAIL_PASSWORD");
	/**
	 * 发件人
	 */
	public static final String EMAIL_FROM_ADDREDD = appRb.getString("EMAIL_FROM_ADDREDD");
	/**
	 * 发件人姓名
	 */
	 public static  final String FROM_NAME = appRb.getString("FROM_NAME");

//	public static void main(String[] args) throws Exception {
//		// 收件人与收件人名字
//		String toAddress = "280420640@qq.com";
//		String toName = "loadfate";
//		EmailUtil test = new EmailUtil();
//		// 所有的异常都为处理，方便浏览
//		EmailUtil.sendSimpleEmail(toAddress, toName, "subject", "sendmsg");
//		// test.sendHtmlEmail(toAddress, toName);
//		// test.sendMultiPartEmail(toAddress, toName);
//		System.out.println("发送完成");
//	}

	// 发送简单邮件，类似一条信息
	public static void sendSimpleEmail(String toAddress, String toName, String subject, String msg) throws Exception {
		SimpleEmail email = EmailUtil.newSimpleEmail();
		email.addTo(toAddress, toName, Charsets.UTF_8.name());// 设置收件人信息
		email.setSubject(subject);// 设置主题
		email.setMsg(msg);// 设置邮件内容
		email.send();// 发送邮件
	}

	// 发送Html内容的邮件
	public static void sendHtmlEmail(String toAddress, String toName, String subject, String htmlMsg) throws Exception {
		HtmlEmail email = EmailUtil.newHtmlEmail();
		email.addTo(toAddress, toName, Charsets.UTF_8.name());
		email.setSubject(subject);
		// 设置html内容，实际使用时可以从文本读入写好的html代码
		email.setHtmlMsg(htmlMsg);
		email.send();
	}

	// 发送复杂的邮件，包含附件等
	public static void sendMultiPartEmail(String toAddress, String toName, String subject, String msg,
			EmailAttachment attachment) throws Exception {
		MultiPartEmail email = EmailUtil.newMultiPartEmail();
		email.addTo(toAddress, toName, Charsets.UTF_8.name());
		email.setSubject(subject);
		email.setMsg(msg);
		// 为邮件添加附加内容
		// EmailAttachment attachment = new EmailAttachment();
		// attachment.setPath("D:\\邮件.txt");// 本地文件
		// attachment.setURL(new URL("http://xxx/a.gif"));//远程文件
		// attachment.setDisposition(EmailAttachment.ATTACHMENT);
		// attachment.setDescription("描述信息");
		// 设置附件显示名字，必须要编码，不然中文会乱码
		// attachment.setName(MimeUtility.encodeText("邮件.txt"));
		// 将附件添加到邮件中
		email.attach(attachment);
		email.send();
	}

	public static SimpleEmail newSimpleEmail() {
		SimpleEmail email = new SimpleEmail();
		EmailUtil.setDefault(email);
		return email;
	}

	public static MultiPartEmail newMultiPartEmail() {
		MultiPartEmail email = new MultiPartEmail();
		EmailUtil.setDefault(email);
		return email;
	}

	public static HtmlEmail newHtmlEmail() {
		HtmlEmail email = new HtmlEmail();
		EmailUtil.setDefault(email);
		return email;
	}

	private static void setDefault(Email email) {
		email.setHostName(EMAIL_HOSTNAME);
		email.setAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
		email.setCharset(Charsets.UTF_8.name());
		try {
			email.setFrom(EMAIL_FROM_ADDREDD, FROM_NAME, Charsets.UTF_8.name());
		} catch (EmailException e) {
			logger.error("设置发送账户异常", e);
		}
	}
}