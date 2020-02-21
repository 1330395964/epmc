package edu.gxuwz.framework.mq;

/**
 * mq邮件
 */
public interface MqMailService {

    /**
     *发送普通文本邮件， to: 收件人， subject： 主题， content：内容
     */
    void sendSimpleMail(String to, String subject, String content);

    /**
     * 发送HTML邮件， to：收件人，subject：主题，content：内容
     * @param to
     * @param subject
     * @param content
     */
    void sendHtmlMail(String to, String subject, String content);

    /**
     * 发送带附件的邮件，to：收件人，subject：主题，content：内容，filePath： 附件内容
     * @param to
     * @param subject
     * @param content
     * @param filePath
     */
    void sendAttachmentMail(String to, String subject, String content, String filePath);

    /**
     * 发送带图片的邮件，to：收件人，subject：主题，content：内容，rscPath：图片路径，rscId：图片ID，用于在<img>标签中使用，从而显示图片
     * @param to
     * @param subject
     * @param content
     * @param rscPath
     * @param rscId
     */
    void sendInlineResourceMail(String to, String subject,String content, String rscPath, String rscId);

}
