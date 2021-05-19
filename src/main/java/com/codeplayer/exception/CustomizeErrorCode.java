package com.codeplayer.exception;
/**
 * 自定义异常
 *
 * @author codeplayer
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {
    ARTICLE_NOT_FOUND(2001,"你找到的文章不在了，要不要换个试试？"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何文章或评论进行回复"),
    NO_LOGIN(2003,"未登录不能进行评论，请先登录"),
    SYS_ERROR(2004,"服务冒烟了，要不然你稍后再试试！！"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在！！"),
    COMMENT_NOT_FOUND(2006,"回复的评论不存在了，要不要换个试试？"),
    CONTENT_IS_EMPTY(2007,"内容不能为空"),
    READ_NOTIFICATION_FAIL(2008,"兄弟你这是读别人的消息呢？"),
    NOTIFICATION_NOT_FOUND(2009,"消息莫非不翼而飞了？"),
    VERIFICATION_CODE_ERROR(2010,"验证码错误，请重试！！"),
    ;


    @Override
    public Integer getCode() {
        return code;
    }

    private Integer code;
    private String message;


    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
